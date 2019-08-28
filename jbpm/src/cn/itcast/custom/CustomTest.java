package cn.itcast.custom;

import org.jbpm.api.Configuration;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.junit.Test;

/**
 * 自定义活动测试
 * @author zhaoqx
 *
 */
public class CustomTest {
	ProcessEngine pe = Configuration.getProcessEngine();
	
	/**
	 * 部署流程定义
	 */
	@Test
	public void deploy(){
		NewDeployment deployment = pe.getRepositoryService().createDeployment();
		deployment.addResourceFromClasspath("cn/itcast/custom/custom.jpdl.xml");
		deployment.addResourceFromClasspath("cn/itcast/custom/custom.png");
		String id = deployment.deploy();
		System.out.println(id);
	}
	
	/**
	 * 启动一个流程
	 */
	@Test
	public void startProcess(){
		ProcessInstance pi = pe.getExecutionService().startProcessInstanceByKey("custom");
		System.out.println(pi.getId());
	}
	
	/**
	 * 直接向后跳一步
	 */
	@Test
	public void test1(){
		String executionId = "custom.320001";
		pe.getExecutionService().signalExecutionById(executionId, "to end1");
	}
}
