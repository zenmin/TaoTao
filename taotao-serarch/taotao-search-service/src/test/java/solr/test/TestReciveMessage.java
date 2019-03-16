package solr.test;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
* @Title TestReciveMessage
* @Description 本类主要功能是测试spring整合ActiveMQ的接收消息
* @Company null
* @author 曾敏
* @date 2017年9月28日下午7:13:03
*/
public class TestReciveMessage {

	@Test
	public void testActiveMQRecive() throws IOException {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-mq.xml");
		System.in.read();
	}
}
