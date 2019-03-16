package com.taotao.search.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.taotao.pojo.SearchItem;
import com.taotao.pojo.TaotaoResult;
import com.taotao.search.dao.SearchItemMapper;
import com.taotao.search.service.ImportItemToSolrService;

/**
* @Title ImportToSolrServiceImpl
* @Description 本类主要功能是一键导入索引库
* @Company null
* @author 曾敏
* @date 2017年10月1日上午9:18:20
*/
@Service
public class ImportToSolrServiceImpl implements ImportItemToSolrService {

	@Autowired
	private SearchItemMapper mapper;
	@Autowired
	private SolrServer solrServer;

	@Override
	public TaotaoResult importItemToSolr() {
		// TODO Auto-generated method stub
		// 查询所有商品数据
		List<SearchItem> list = mapper.getItemList();
		try {
			for (SearchItem s : list) {
				// 创建solr文档对象
				SolrInputDocument document = new SolrInputDocument();
				// 添加字段 域
				document.addField("id", s.getId());
				document.addField("item_title", s.getTitle());
				document.addField("item_sell_point", s.getSell_point());
				document.addField("item_price", s.getPrice());
				document.addField("item_image", s.getImage());
				document.addField("item_category_name", s.getCat_name());
				document.addField("item_desc", s.getItem_desc());
				// 添加此文档对象
				solrServer.add(document);
			}
			// 提交
			solrServer.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return TaotaoResult.build(500, "导入失败！");
		}
		return TaotaoResult.ok();
	}

}
