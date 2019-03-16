package test.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
/**
 * @Title TestActiveMQ
 * @Description 本类主要功能是测试ActiveMQ
 * @Company null
 * @author 曾敏
 * @date 2017年9月27日下午12:11:24
 */
public class TestActiveMQ_Topic {

	// Topic模式 Producer
	@Test
	public void testTopic() throws Exception {
		// 1 创建连接工厂对象
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
		// 2 使用工厂创建连接
		Connection connection = factory.createConnection();
		// 3 开启连接
		connection.start();
		// 4 用连接获取一个Session 第一个参数 是是否使用分布式缓存 一般不用 保证事务最终一致，使用ActiveMQ可以实现
		// 第二个参数是应答模式 一般设置自动应答 如果第一个参数为true 第二个参数可以忽略
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5 使用session对象创建一个Destination 这个有两种形式 就是queue和topic
		Topic topic = session.createTopic("Topic1"); // 消息的名称
		// 6 使用session创建一个Producer对象
		MessageProducer producer = session.createProducer(topic);
		// 7 创建TextMessage对象和消息体
		TextMessage message = session.createTextMessage("Hello World Topic");
		// 8 发送消息
		producer.send(message);
		// 9 关闭资源
		session.close();
		producer.close();
		connection.close();
	}

	// 接收Topic消息 Consumer
	@Test
	public void reciveTopic() throws Exception {
		// 创建一个工厂
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
		// 从工厂获取connection
		Connection connection = factory.createConnection();
		// 开启连接
		connection.start();
		// 创建一个session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 使用session创建一个Destination
		Topic topic = session.createTopic("Topic1"); // 指定监听的消息名称
		// 使用session创建一个Consumer
		MessageConsumer consumer = session.createConsumer(topic);
		// 给consumer添加监听
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message arg0) {
				// TODO Auto-generated method stub
				// 如果此消息是TextMessage类型 直接强转
				if (arg0 instanceof TextMessage) {
					TextMessage msg = (TextMessage) arg0;
					try {
						String text = msg.getText();

						System.out.println(text);

					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		System.out.println("Consumer 2 ");
		// 让其一直监听 不停止程序
		System.in.read();
		// 关闭连接
		consumer.close();
		session.close();
		connection.close();
	}

}
