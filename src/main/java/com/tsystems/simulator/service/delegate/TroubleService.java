package com.tsystems.simulator.service.delegate;

import de.telekom.blwmsa.exceptionhandlerstarter.service.ModificationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.rest.impl.CamundaRestResources;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.spring.boot.starter.rest.CamundaBpmRestInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import de.telekom.blwmsa.exceptionhandlerstarter.service.ModificationService;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("TroubleService")
@RequiredArgsConstructor
public class TroubleService implements JavaDelegate {

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected HistoryService historyService;

    @Autowired
    protected ModificationService modificationService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("Start exception");
        String instanceId = execution.getProcessInstanceId();
        String scopeActivityId = "Activity_1frg2hj";

        //modificationService.doContinue(instanceId, scopeActivityId);
        doContinue(instanceId, scopeActivityId);

    }

    public void doContinue(String processInstanceId, String scopeActivityId) {

        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).list();

        List<HistoricActivityInstance> activitiesToStart = getCancelledActivityInstances(historicActivityInstances,
                scopeActivityId);


        for (HistoricActivityInstance activityInstance : activitiesToStart) {
            runtimeService.createProcessInstanceModification(processInstanceId)
                    .startAfterActivity(activityInstance.getActivityId())
                    .execute();
        }

    }

    public List<HistoricActivityInstance> getCancelledActivityInstances(List<HistoricActivityInstance> historicActivityInstances,
                                                                        String scopeActivityId) {

        Optional<String> exceptionHandlingScopeId = getActivityInstanceId(historicActivityInstances, scopeActivityId);

        return historicActivityInstances.stream()
                .filter(activityInstance -> BooleanUtils.isTrue(activityInstance.isCanceled()) &&
                        exceptionHandlingScopeId.get().equals(activityInstance.getParentActivityInstanceId()))
                .collect(Collectors.toList());
    }

    private Optional<String> getActivityInstanceId(List<HistoricActivityInstance> activityInstances,
                                                   String activityId) {
        return activityInstances.stream()
                .filter(activityInstance -> activityInstance.getActivityId().equals(activityId))
                .map(HistoricActivityInstance::getId).findAny();
    }
}
