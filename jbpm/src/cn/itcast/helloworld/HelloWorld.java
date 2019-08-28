package cn.itcast.helloworld;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipInputStream;

import org.jbpm.api.Configuration;
import org.jbpm.api.Deployment;
import org.jbpm.api.DeploymentQuery;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.ProcessInstanceQuery;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Task;
import org.junit.Test;

/**
 * JBPM操作流程
 * @author zhaoqx
 *
 */
public class HelloWorld {
	ProcessEngine processEngine = Configuration.getProcessEngine();
	/**
	 * 生成18张表
	 */
	@Test
	public void test1(){
		Configuration conf = new Configuration();//获取配置对象
		conf.setResource("jbpm.cfg.xml");//加载配置文件
		ProcessEngine processEngine = conf.buildProcessEngine();//创建流程引擎对象
	}
	
	/**
	 * 创建流程引擎对象的方法
	 */
	@Test
	public void test2(){
		//方式一
		/*Configuration conf = new Configuration();
		conf.setResource("jbpm.cfg.xml");
		ProcessEngine processEngine = conf.buildProcessEngine();*/
		
		//方式二  获得的是单例对象
		ProcessEngine processEngine = Configuration.getProcessEngine();
	}
	
	/**
	 * 部署流程定义
	 * @throws Exception 
	 */
	@Test
	public void test3() throws Exception{
		// 方式一----从类路径下读取文件
		/*NewDeployment deployment = processEngine.getRepositoryService().createDeployment();//获取部署对象;
		deployment.addResourceFromClasspath("helloworld2.jpdl.xml");//读取xml配置文件
		deployment.addResourceFromClasspath("helloworld2.png");//读取图片文件
		String id = deployment.deploy();//完成部署
		System.out.println(id);*/
		
		//方式二----读取压缩文件流
		NewDeployment deployment = processEngine.getRepositoryService().createDeployment();
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(new File("E:\\it\\java\\workspace\\jbpm\\bin\\hello.zip")));
		deployment.addResourcesFromZipInputStream(zipInputStream);
		String id = deployment.deploy();
		System.out.println(id);
	}
	
	/**
	 * 删除流程定义
	 */
	@Test
	public void test4(){
		String deploymentId = "90001";
		//processEngine.getRepositoryService().deleteDeployment(deploymentId);
	}
	
	/**
	 * 查询流程定义
	 */
	@Test
	public void test5(){
		//获得流程定义查询对象
		ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
		long count = query.count();
		System.out.println("当前系统流程定义的数量 = " + count);
		List<ProcessDefinition> list = query.list();
		for(ProcessDefinition pd : list){
			String id = pd.getId();
			String name = pd.getName();
			String key = pd.getKey();
			int version = pd.getVersion();
			System.out.println("id = " + id + ",name = " + name + ",key = " + key + ",version = " + version);
		}
	}
	
	/**
	 * 获取流程定义的文件
	 * @throws Exception 
	 */
	@Test
	public void test6() throws Exception{
		String deploymentId = "90001";
		//获得一次部署对应的文件名称
		Set<String> names = processEngine.getRepositoryService().getResourceNames(deploymentId);
		for(String name:names){
			System.out.println(name);
		}
		
		//获得一次部署对应的文件输入流
		String resourceName = "helloworld.jpdl.xml";
		//获得部署对应的输入流
		InputStream in = processEngine.getRepositoryService().getResourceAsStream(deploymentId, resourceName);
		
		//通过此输出流将文件保存到本地磁盘
		OutputStream out = new FileOutputStream(new File("e:\\" + resourceName));
		
		byte[] b = new byte[1024];
		int len = 0;
		try{
		while((len = in.read(b)) != -1){
			out.write(b, 0, len);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		out.close();
		in.close();
	}
	
	/**
	 * 启动一个流程
	 */
	@Test
	public void test7(){
		String processDefinitionId = "请假流程-2";
		//ProcessInstance processInstance = processEngine.getExecutionService().startProcessInstanceById(processDefinitionId);
		
		String key = "请假流程";
		ProcessInstance processInstance = processEngine.getExecutionService().startProcessInstanceByKey(key);
		
		String id = processInstance.getId();
		String name = processInstance.getName();
		//String key = processInstance.getKey();
	}
	
	/**
	 * 查询我的任务列表
	 */
	@Test
	public void test8(){
		String userId = "李四";
		
		//获取任务查询对象
		TaskQuery query = processEngine.getTaskService().createTaskQuery();
		query.assignee(userId);//添加过滤条件
		List<Task> list = query.list();
		for(Task task : list){
			String executionId = task.getExecutionId();
			System.out.println(executionId);
			System.out.println(task.getId() + " ," + task.getName()) ;
		}
	}
	
	/**
	 * 办理任务
	 */
	@Test
	public void test9(){
		String taskId = "90009";
		//processEngine.getTaskService().completeTask(taskId);
	}
	
	/**
	 * 直接向后跳一步
	 */
	@Test
	public void test10(){
		String executionId = "请假流程.50008";
		//processEngine.getExecutionService().signalExecutionById(executionId,"to task3");
		
		/*ProcessInstanceQuery query = processEngine.getExecutionService().createProcessInstanceQuery();
		List<ProcessInstance> list = query.list();*/
		//TaskQuery createTaskQuery = processEngine.getTaskService().createTaskQuery();
		
		/*DeploymentQuery query = processEngine.getRepositoryService().createDeploymentQuery();
		List<Deployment> list = query.list();
		for(Deployment d:list){
			System.out.println(d.getId() + " " + d.getName() + " " + d.getState());
		}*/
	}
	
	/**
	 * 查询对象的使用方式
	 */
	@Test
	public void test11(){
		ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
		
		//添加过滤条件
		query.processDefinitionKey("请假流程");
		
		//添加排序条件
		query.orderDesc(ProcessDefinitionQuery.PROPERTY_VERSION);
		
		//分页查询
		query.page(0, 10);
		
		List<ProcessDefinition> list = query.list();
		for(ProcessDefinition pd : list){
			String id = pd.getId();
			String key = pd.getKey();
			int version = pd.getVersion();
			System.out.println("id = " + id + ",key = " + key + ",version = " + version);
		}
		
		//返回唯一结果
		//query.uniqueResult();
		
		//统计数量
		long count = query.count();
		System.out.println("流程定义的数量  = "+count);
	}
	
	/**
	 * 获取最新版本的流程定义
	 */
	@Test
	public void test12(){
		//获取所有流程定义
		ProcessDefinitionQuery query = processEngine.getRepositoryService().createProcessDefinitionQuery();
		query.orderAsc(ProcessDefinitionQuery.PROPERTY_VERSION);
		List<ProcessDefinition> list = query.list();
		
		Map<String,ProcessDefinition> map = new HashMap<String, ProcessDefinition>();
		
		for(ProcessDefinition pd : list){
			String id = pd.getId();
			String key = pd.getKey();
			int version = pd.getVersion();
			System.out.println("id = " + id + ",key = " + key + ",version = " + version);
			
			map.put(key, pd);
		}
		System.out.println("-------------------------");
		
		List<ProcessDefinition> pdList = new ArrayList<ProcessDefinition>(map.values());
		for(ProcessDefinition pd : pdList){
			String id = pd.getId();
			String key = pd.getKey();
			int version = pd.getVersion();
			System.out.println("id = " + id + ",key = " + key + ",version = " + version);
		}
	}
	
}
