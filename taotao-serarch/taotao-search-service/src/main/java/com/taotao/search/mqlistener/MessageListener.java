package com.taotao.search.mqlistener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;


/**
* @Title MessageListener
* @Description 本类主要功能是接收消息的Listener
* @Company null
* @author 曾敏
* @date 2017年9月28日下午6:55:41
*/
public class MessageListener implements javax.jms.MessageListener {

	@Override
	public void onMessage(Message arg0) {
		// TODO Auto-generated method stub
		TextMessage message = (TextMessage) arg0;
		try {
			String text = message.getText();
			System.out.println(text);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
