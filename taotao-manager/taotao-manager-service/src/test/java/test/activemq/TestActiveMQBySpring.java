package test.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
* @Title TestActiveMQBySpring
* @Description 本类主要功能是测试ActiveMQ整合Spring
* @Company null
* @author 曾敏
* @date 2017年9月28日下午6:35:37
*/
public class TestActiveMQBySpring {

	
	@Test
	public void testActiveMQ() {
		//初始化Spring容器
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext-mq.xml");
		//获取jmsTempd的bean
		JmsTemplate jms = (JmsTemplate) context.getBean("jmsTemplete");
		//获取Destination
		//使用Queue模式
		Destination queue = (Destination) context.getBean("test-queue");
		//发送消息
		jms.send(queue,new MessageCreator() {
			@Override
			public Message createMessage(Session s) throws JMSException {
				// TODO Auto-generated method stub
				TextMessage message = s.createTextMessage("Sring ActiveMQ ....");
				return message;
			}
		});
	}
	
}
