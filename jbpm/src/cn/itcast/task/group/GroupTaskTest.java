package cn.itcast.task.group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.Configuration;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Task;
import org.junit.Test;

/**
 * 组任务测试
 * @author zhaoqx
 *
 */
public class GroupTaskTest {
	ProcessEngine processEngine = Configuration.getProcessEngine();
	/**
	 * 部署流程定义
	 */
	@Test
	public void test1(){
		//读取核心配置文件并创建表
		ProcessEngine processEngine = Configuration.getProcessEngine();
		
		//获得部署对象
		NewDeployment deployment = processEngine.getRepositoryService().createDeployment();
		
		//加载流程定义文档
		deployment.addResourceFromClasspath("cn/itcast/task/group/group.jpdl.xml");
		deployment.addResourceFromClasspath("cn/itcast/task/group/group.png");
		
		String id = deployment.deploy();
		System.out.println(id);
	}
	
	/**
	 * 启动一个流程实例
	 */
	@Test
	public void test2(){
		ProcessInstance pi = processEngine.getExecutionService().startProcessInstanceByKey("组任务流程");
		System.out.println(pi.getId());
	}
	
	/**
	 * 查询我的任务列表(个人任务)
	 */
	@Test
	public void test3(){
		String userId = "zhangsan";
		List<Task> list = processEngine.getTaskService().findPersonalTasks(userId);
		for(Task t : list){
			System.out.println(t.getName());
		}
	}
	
	/**
	 * 查询组任务列表
	 */
	@Test
	public void test4(){
		String userId = "zhangsan";
		List<Task> list = processEngine.getTaskService().findGroupTasks(userId);
		for(Task t : list){
			System.out.println(t.getName());
		}
	}
	
	/**
	 * 拾取任务
	 */
	@Test
	public void test5(){
		String userId = "lisi";
		processEngine.getTaskService().takeTask("400002", userId);
	}
	
	/**
	 * 退回任务到组任务
	 */
	@Test
	public void test6(){
		processEngine.getTaskService().assignTask("400002", null);
	}
	
	/**
	 * 分配任务
	 */
	@Test
	public void test7(){
		String userId = "wangwu";
		processEngine.getTaskService().assignTask("400002", userId);
	}
	
	/**
	 * 办理任务
	 */
	@Test
	public void test8(){
		processEngine.getTaskService().completeTask("400002");
	}
}
