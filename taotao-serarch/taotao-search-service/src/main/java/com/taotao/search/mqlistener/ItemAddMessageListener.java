package com.taotao.search.mqlistener;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.pojo.SearchItem;
import com.taotao.search.dao.SearchItemMapper;

/**
 * @Title MessageListener
 * @Description 本类主要功能是接收消息 同步索引库
 * @Company null
 * @author 曾敏
 * @date 2017年9月28日下午6:55:41
 */
public class ItemAddMessageListener implements javax.jms.MessageListener {

	@Autowired
	private SearchItemMapper mapper;
	@Autowired
	private SolrServer server;
	
	@Override
	public void onMessage(Message arg0) {
		// TODO Auto-generated method stub
		TextMessage message = (TextMessage) arg0;
		try {
			// 取出商品id
			long id = Long.parseLong(message.getText());
			// 从数据库获取商品信息
			List<SearchItem> list = mapper.getItemListById(id);

			// 由于我们是在事务没提交之前 发送的信息 所以这里需要等一段时间
			Thread.sleep(10000);
			SolrInputDocument document = null;
			for (SearchItem s : list) {
				// 创建文档对象
				document = new SolrInputDocument();
				// 添加域
				document.addField("id", s.getId());
				document.addField("item_title", s.getTitle());
				document.addField("item_sell_point", s.getSell_point());
				document.addField("item_price", s.getPrice());
				document.addField("item_image", s.getImage());
				document.addField("item_category_name", s.getCat_name());
				document.addField("item_desc", s.getItem_desc());
			}
			// 添加进索引库
			server.add(document);
			// 提交	
			server.commit();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
