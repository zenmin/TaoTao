package solr.test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import org.junit.Test;

/**
* @Title TestSolrJ
* @Description 本类主要功能是Solr测试
* @Company null
* @author 曾敏
* @date 2017年9月23日下午2:24:47
*/
public class TestSolrJ {

	// Solr添加
	@Test
	public void testAdd() throws SolrServerException, IOException {
		// 创建HttpSolrServer对象
		SolrServer server = new HttpSolrServer("http://192.168.25.133:8080/solr/collection1");

		// 创建文档对象
		SolrInputDocument document = new SolrInputDocument();

		// 必须要有id 并且字段名称必须在schma.xml中定义
		document.addField("id", "123");
		document.addField("item_title", "zm");
		document.addField("item_image", "345");
		// 把文档对象写入索引库
		server.add(document);
		// 提交
		server.commit();
	}
	
	
	// Solr添加
	@Test
	public void testGet() throws SolrServerException, IOException {
		// 创建HttpSolrServer对象
		SolrServer server = new HttpSolrServer("http://192.168.25.133:8080/solr/collection1");

		// 创建文档对象
		SolrInputDocument document = new SolrInputDocument();

		// 必须要有id 并且字段名称必须在schma.xml中定义
		SolrInputField field = document.get("item_title");
		
		System.out.println(field);
	}
	
	@Test
	public void delDocById() throws SolrServerException, IOException {
		// 创建HttpSolrServer对象
		SolrServer server = new HttpSolrServer("http://192.168.25.133:8080/solr/collection1");

		server.deleteById("123");

		server.commit();
	}

	// Solr删除
	@Test
	public void delDocByQuery() throws SolrServerException, IOException {
		// 创建HttpSolrServer对象
		SolrServer server = new HttpSolrServer("http://192.168.25.133:8080/solr/collection1");

		server.deleteByQuery("*:*");

		server.commit();
	}

	// 查询
	@Test
	public void searchDocument() throws SolrServerException {
		// 创建solrServer对象
		SolrServer server = new HttpSolrServer("http://192.168.25.133:8080/solr/collection1");
		// 创建SolrQuery对象
		SolrQuery query = new SolrQuery();
		// 设置查询、分页、排序、高亮等条件
		query.setQuery("手机");
		// 分页
		query.setStart(0);
		query.setRows(10);
		// 设置默认搜索域
		query.set("df", "item_keywords");
		// 开启高亮
		query.setHighlight(true);
		// 设置高亮显示的域
		query.addHighlightField("item_title");
		// 设置高亮的前缀和后缀
		query.setHighlightSimplePre("<div>");
		query.setHighlightSimplePost("</div>");
		// 执行查询
		QueryResponse response = server.query(query);
		// 得到List结果
		SolrDocumentList results = response.getResults();
		// 获取结果总条数
		System.out.println("总条数：" + results.getNumFound());
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			// 取高亮显示的所有title
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			// 再从所有高亮title中取对应id 的title
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");

			String title = "";
			// 如果没有高亮的title 就
			if (list != null && list.size() > 0) {
				title = list.get(0);
			} else {
				title = (String) solrDocument.get("item_title");
			}
			System.out.println(title);
			System.out.println(solrDocument.get("item_sell_point"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_category_name"));
			System.out.println(solrDocument.get("item_image"));
			System.out.println("----------------------------");
		}

	}

}
