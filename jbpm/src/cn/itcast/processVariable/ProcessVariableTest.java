package cn.itcast.processVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jbpm.api.Configuration;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.junit.Test;

/**
 * 流程变量测试
 * @author zhaoqx
 *
 */
public class ProcessVariableTest {

	ProcessEngine pe = Configuration.getProcessEngine();
	
	/**
	 * 部署流程定义
	 */
	@Test
	public void deploy(){
		NewDeployment deployment = pe.getRepositoryService().createDeployment();
		deployment.addResourceFromClasspath("cn/itcast/processVariable/test.jpdl.xml");
		deployment.addResourceFromClasspath("cn/itcast/processVariable/test.png");
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
	 * 设置流程变量
	 */
	@Test
	public void test1(){
		//1 使用ExecutionService
		/*String name = "报销金额";
		Double value = 100d;
		pe.getExecutionService().setVariable("报销流程.60001", name, value);//一次设置一个流程变量
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		pe.getExecutionService().setVariables("报销流程.60001", map);//一次设置多个流程变量
		 */		 
		
		//2 使用TaskService
		/*String taskId = "60002";
		Map<String,Object> variables = new HashMap<String, Object>();
		variables.put("key3", "value3");
		pe.getTaskService().setVariables(taskId, variables);*/
		
		//3 在启动流程实例时设置
		/*Map<String, Object> map = new HashMap<String, Object>();
		map.put("key4", "value4");
		map.put("key5", "value5");
		pe.getExecutionService().startProcessInstanceByKey("报销流程", map);*/
		
		//4 在办理任务时设置
		String taskId = "100004";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key6", "value6");
		pe.getTaskService().completeTask(taskId,"to task2" ,map);
	}
	
	/**
	 * 获取流程变量
	 */
	@Test
	public void test2(){
		// 1使用ExecutionService获取流程变量
		/*Set<String> names = pe.getExecutionService().getVariableNames("报销流程.60001");
		for(String name : names){
			Object value = pe.getExecutionService().getVariable("报销流程.60001", name);
			System.out.println(name + " = " + value);
		}*/
		
		//2 使用TaskService获取流程变量
		String taskId = "130002";
		Set<String> names = pe.getTaskService().getVariableNames(taskId);
		Map<String, Object> map = pe.getTaskService().getVariables(taskId,names);
		for(String name : names){
			System.out.println(name);
		}
	}
	
	/**
	 * 查询我的任务列表
	 */
	@Test
	public void findPersonalTasks(){
		List<Task> list = pe.getTaskService().findPersonalTasks("张三");
		for(Task t : list){
			System.out.println(t.getId());
		}
	}
}
