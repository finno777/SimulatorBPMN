package com.tsystems.simulator.service.delegate;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("TroubleService")
@RequiredArgsConstructor
public class TroubleService implements JavaDelegate {

    @Autowired
    private final HistoryService historyService;
    @Autowired
    private final RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        try {

        }catch (BpmnError bpmnError){
            System.out.println(bpmnError.getErrorCode());
        }
        System.out.println("Start exception");
        String instanceId = execution.getProcessInstanceId();
        String definitionId = execution.getProcessDefinitionId();
    }
}
