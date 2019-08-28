package cn.itcast.decision;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

public class DesisionHandlerImpl implements DecisionHandler{

	@Override
	public String decide(OpenExecution execution) {
		String name = "to task2";
		
		Double money = (Double) execution.getVariable("money");
		if(money > 500d){
			name = "to task3";
		}
		return name;
	}
}
