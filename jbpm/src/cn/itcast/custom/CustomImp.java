package cn.itcast.custom;

import java.util.Map;

import org.jbpm.api.activity.ActivityExecution;
import org.jbpm.api.activity.ExternalActivityBehaviour;

public class CustomImp implements ExternalActivityBehaviour{
	/**
	 * 这个方法任何情况下都会执行
	 */
	public void execute(ActivityExecution execution) throws Exception {
		execution.waitForSignal();
		System.out.println("execute方法执行了");
	}

	/**
	 * 在自定义活动停留在此节点时，并且离开此节点时执行
	 */
	public void signal(ActivityExecution execution, String signalName,
			Map<String, ?> parameters) throws Exception {
		System.out.println("signal方法执行了");
	}
}
