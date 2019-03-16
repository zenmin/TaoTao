package solr.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrCloud {

	@Test
	public void testSolrCloud() throws SolrServerException, IOException {
		// 创建CloudSolrServer 包含Zookeeper的节点ip 注意 这是不是直接连接Solr了 而是连接的Zookeeper节点
		CloudSolrServer cloudSolrServer = new CloudSolrServer(
				"192.168.25.133:2182,192.168.25.133:2183,192.168.25.133:2184");
		// 设置默认Collection
		cloudSolrServer.setDefaultCollection("collection2");
		// 创建一个文档对象
		SolrInputDocument document = new SolrInputDocument();
		// 添加域
		document.addField("id", "test11111");
		// 添加文档
		cloudSolrServer.add(document);
		// 提交
		cloudSolrServer.commit();

	}
}
