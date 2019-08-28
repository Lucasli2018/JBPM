package cn.itcast.state;

import org.jbpm.api.Configuration;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.junit.Test;

/**
 * 状态活动测试
 * 
 * @author zhaoqx
 * 
 */
public class StateTest {
	ProcessEngine pe = Configuration.getProcessEngine();

	/**
	 * 部署流程定义
	 */
	@Test
	public void deploy() {
		NewDeployment deployment = pe.getRepositoryService().createDeployment();
		deployment.addResourceFromClasspath("cn/itcast/state/state.jpdl.xml");
		deployment.addResourceFromClasspath("cn/itcast/state/state.png");
		String id = deployment.deploy();
		System.out.println(id);
	}
	
	/**
	 * 启动一个流程
	 */
	@Test
	public void startProcess(){
		ProcessInstance pi = pe.getExecutionService().startProcessInstanceByKey("状态活动流程");
		System.out.println(pi.getId());
	}
	
	/**
	 * 执行状态活动节点(直接跳到下一个节点)
	 */
	@Test
	public void test1(){
		String executionId = "状态活动流程.160001";
		pe.getExecutionService().signalExecutionById(executionId,"to end1");
	}

}
