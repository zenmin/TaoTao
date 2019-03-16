package com.taotao.item.listener;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
* @Title ItemAddMessageListener
* @Description 本类主要功能是从ACtiveMQ消息中获取id  查询出数据   生成静态页面
* @Company null
* @author 曾敏
* @date 2017年10月1日下午3:45:38
*/
public class ItemAddMessageListener implements MessageListener{

	@Autowired
	private ItemService service;
	@Autowired
	private FreeMarkerConfigurer configure;
	@Value("${OUT_HTML_PATH}")
	private String OUT_HTML_PATH;
	
	@Override
	public void onMessage(Message arg0) {
		// TODO Auto-generated method stub
		//从消息中获取id
		try {
		TextMessage message = (TextMessage) arg0;
			String text = message.getText();
			long id = Long.parseLong(text);
			
			//等待事务提交
			Thread.sleep(1000);
			
			//查询商品的信息
			TbItem itemById = service.getItemById(id);
			//带图片分隔的pojo
			Item item = new Item(itemById);		
			//查询详细信息
			TbItemDesc itemDesc = service.getItemDescById(id);
			
			//生成静态页面
			Configuration configuration = configure.getConfiguration();
			//创建模板
			Template template = configuration.getTemplate("item.ftl");
			//创建一个map
			Map dataModel = new HashMap<>();
			//给模板中参数赋值
			dataModel.put("item", item);
			dataModel.put("itemDesc", itemDesc);
			Writer out = new FileWriter(OUT_HTML_PATH + id + ".html");
			System.out.println(OUT_HTML_PATH + id + ".html");
			//执行生成
			template.process(dataModel, out);
			out.close();
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 }
