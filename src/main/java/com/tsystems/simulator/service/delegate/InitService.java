package com.tsystems.simulator.service.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("InitService")
public class InitService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("init");
        String variable = (String) execution.getVariable("message");
        System.out.println(this.getClass().getName() + " " + variable);
        execution.setVariable("SignatureValidity", false);
        //TODO: add if condition about checker reaction if retry set value to execution
//        if(RETRY){
//            execution.setVariable("SignatureValidity", true);
//        }

    }
}
