package com.taotao.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.pojo.SearchItem;
import com.taotao.pojo.SearchResult;

/**
* @Title SearchDao
* @Description 本类主要功能是搜索服务dao层
* @Company null
* @author 曾敏
* @date 2017年9月23日下午6:45:30
*/
@Repository
public class SearchDao {

	@Autowired
	private SolrServer server;
	
	public SearchResult search(SolrQuery query) throws SolrServerException {
		//执行查询
		QueryResponse response = server.query(query);
		//取得结果
		SolrDocumentList solrDocumentList = response.getResults();
		//取结果总条数
		long numFound = solrDocumentList.getNumFound();
		//创建结果集
		SearchResult result = new SearchResult();
		result.setCount(numFound);
		
		List<SearchItem> items = new ArrayList<>();
		for (SolrDocument s : solrDocumentList) {
			//设置域
			SearchItem item = new SearchItem();
			item.setId((String) s.get("id"));
			item.setCat_name((String) s.get("item_category_name"));
			item.setImage((String) s.get("item_image"));
			item.setItem_desc("item_desc");
			item.setSell_point("item_sell_point");
			item.setPrice((long) s.get("item_price"));
			//由于所有图片地址在一个String中   所以取出第一张图片
			String img = (String) s.get("item_image");
			if(StringUtils.isNotBlank(img)) {
				img = img.split(",")[0];
			}
			item.setImage(img);
				
			//取所有高亮显示
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			//根据id查询对象  再从对象中获取item_title
			List<String> list = highlighting.get(s.get("id")).get("item_title");
			String title = "";
			if(list != null && list.size()>0) {
				title = list.get(0);
			}else {
				title = (String) s.get("item_title");
			}
			
			item.setTitle(title);

			//添加此item
			items.add(item);
		}
		
		//封装list进result
		result.setList(items);
		
		return result;
		
	}
	
	
}
