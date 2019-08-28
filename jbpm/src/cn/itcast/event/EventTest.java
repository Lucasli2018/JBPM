package cn.itcast.event;

import org.jbpm.api.Configuration;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.junit.Test;

/**
 * 事件测试
 * @author zhaoqx
 *
 */
public class EventTest {
ProcessEngine pe = Configuration.getProcessEngine();
	
	/**
	 * 部署流程定义
	 */
	@Test
	public void deploy(){
		NewDeployment deployment = pe.getRepositoryService().createDeployment();
		deployment.addResourceFromClasspath("cn/itcast/event/test.jpdl.xml");
		deployment.addResourceFromClasspath("cn/itcast/event/test.png");
		String id = deployment.deploy();
		System.out.println(id);
	}
	
	/**
	 * 启动一个流程
	 */
	@Test
	public void startProcess(){
		ProcessInstance pi = pe.getExecutionService().startProcessInstanceByKey("报销流程 ");
		System.out.println(pi.getId());
	}
	
	/**
	 * 办理任务
	 */
	@Test
	public void test1(){
		String taskId = "350001";
		pe.getTaskService().completeTask(taskId);
	}
	
}
