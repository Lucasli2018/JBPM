package cn.itcast.test;

import java.util.List;

import org.jbpm.api.Configuration;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Task;
import org.junit.Test;

/**
 * 流程操作
 * @author zhaoqx
 *
 */
public class JbpmTest {
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
		deployment.addResourceFromClasspath("cn/itcast/test/test.jpdl.xml");
		deployment.addResourceFromClasspath("cn/itcast/test/test.png");
		
		String id = deployment.deploy();
		System.out.println(id);
	}
	
	/**
	 * 启动一个流程实例
	 */
	@Test
	public void test2(){
		ProcessInstance pi = processEngine.getExecutionService().startProcessInstanceByKey("报销流程");
		System.out.println(pi.getId());
	}
	
	/**
	 * 查询我的任务列表
	 */
	@Test
	public void test3(){
		/*TaskQuery query = processEngine.getTaskService().createTaskQuery();
		query.assignee("张三");
		List<Task> list = query.list();*/
		
		String userId = "李四";
		List<Task> list2 = processEngine.getTaskService().findPersonalTasks(userId);
		
		for(Task t : list2){
			System.out.println(t.getId() + " " + t.getName() + " " + t.getExecutionId());
		}
	}
	
	/**
	 * 办理任务
	 */
	@Test
	public void test4(){
		String taskId = "30001";
		processEngine.getTaskService().completeTask(taskId);
	}
	
	/**
	 * 直接向后跳一步
	 */
	@Test
	public void test5(){
		processEngine.getExecutionService().signalExecutionById("报销流程.20001", "to end1");
	}
}
