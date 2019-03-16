package com.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.pojo.SearchResult;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao dao;

	@Override
	public SearchResult search(String query, int page, int rows) throws Exception {
		
		
		// TODO Auto-generated method stub
		// 创建SolrQuery
		SolrQuery q = new SolrQuery();
		// 设置条件
		q.setQuery(query);
		// 分页
		if (page < 1)
			page = 1;
		q.setStart((page - 1) * rows);
		if (rows < 1)
			rows = 10;
		q.setRows(rows);
		// 设置默认搜索域
		q.set("df", "item_title");
		// 设置高亮显示
		q.setHighlight(true);
		q.addHighlightField("item_title");
		q.setHighlightSimplePre("<font color='red'>");
		q.setHighlightSimplePost("</font>");
		// 执行查询
		SearchResult result = dao.search(q);
		// 获取总条数
		long count = result.getCount();
		// 算出总页码
		long pages = count / rows;
		// 如果总条数%总页码 有余数 那还要在加一页哦
		if (count % rows > 0) {
			pages++;
		}
		// 设置总页码
		result.setTotalpage(pages);
		return result;
	}

}
