package cn.itcast.event;

import org.jbpm.api.listener.EventListener;
import org.jbpm.api.listener.EventListenerExecution;

public class EventImpl implements EventListener{

	public void notify(EventListenerExecution execution) throws Exception {
		System.out.println("notify方法执行了");
	}
}
