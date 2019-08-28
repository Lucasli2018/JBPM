package cn.itcast.forkjoin;

import java.util.List;

import org.jbpm.api.Configuration;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.junit.Test;

/**
 * 分支合并活动测试
 * @author zhaoqx
 *
 */
public class ForkJoinTest {

	ProcessEngine pe = Configuration.getProcessEngine();
	
	/**
	 * 部署流程定义
	 */
	@Test
	public void deploy(){
		NewDeployment deployment = pe.getRepositoryService().createDeployment();
		deployment.addResourceFromClasspath("cn/itcast/forkjoin/forkjoin.jpdl.xml");
		deployment.addResourceFromClasspath("cn/itcast/forkjoin/forkjoin.png");
		String id = deployment.deploy();
		System.out.println(id);
	}
	
	/**
	 * 启动一个流程
	 */
	@Test
	public void startProcess(){
		ProcessInstance pi = pe.getExecutionService().startProcessInstanceByKey("购物流程");
		System.out.println(pi.getId());
	}
	
	/**
	 * 查询任务列表
	 */
	@Test
	public void test1(){
		String name = "客户";
		List<Task> list = pe.getTaskService().findPersonalTasks(name);
		for(Task t : list){
			System.out.println(t.getId() + " " + t.getName());
		}
	}
	
	/**
	 * 办理任务
	 */
	@Test
	public void test2(){
		String taskId = "280001";
		pe.getTaskService().completeTask(taskId);
	}
}
