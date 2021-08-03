package com.tsystems.simulator.service.delegate;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("TroubleService")
@RequiredArgsConstructor
public class TroubleService implements JavaDelegate {

    @Autowired
    private final HistoryService historyService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("Start exception");
        String instanceId = execution.getProcessInstanceId();

        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().list();

        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(instanceId).list();
        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery().processInstanceId(instanceId).list();



        Optional<String> optionalS = historicActivityInstances.stream()
                .filter(activityInstance -> activityInstance.getActivityId().equals(execution.getCurrentActivityId()))
                .map(HistoricActivityInstance::getId).findAny();
        List<HistoricActivityInstance> collection = historicActivityInstances.stream()
                .filter(activityInstance -> BooleanUtils.isTrue(activityInstance.isCanceled()) &&
                        optionalS.get().equals(activityInstance.getParentActivityInstanceId()))
                .collect(Collectors.toList());

        collection.forEach(System.out::println);
    }
}
