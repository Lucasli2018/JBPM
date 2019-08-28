package cn.itcast.decision;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.api.Configuration;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.junit.Test;

/**
 * 判断活动测试
 * @author zhaoqx
 *
 */
public class DesisionTest {
	ProcessEngine pe = Configuration.getProcessEngine();
	
	/**
	 * 部署流程定义
	 */
	@Test
	public void deploy(){
		NewDeployment deployment = pe.getRepositoryService().createDeployment();
		deployment.addResourceFromClasspath("cn/itcast/decision/decision.jpdl.xml");
		deployment.addResourceFromClasspath("cn/itcast/decision/decision.png");
		String id = deployment.deploy();
		System.out.println(id);
	}
	
	/**
	 * 启动一个流程
	 */
	@Test
	public void startProcess(){
		ProcessInstance pi = pe.getExecutionService().startProcessInstanceByKey("报销流程");
		System.out.println(pi.getId());
	}
	
	/**
	 * 办理任务，同时设置流程变量
	 */
	@Test
	public void test1(){
		String taskId = "140002";
		String outcome = "to exclusive1";
		Map<String ,Object> variables = new HashMap<String, Object>();
		variables.put("money", new Double(600));
		pe.getTaskService().completeTask(taskId, outcome, variables);
	}
}
