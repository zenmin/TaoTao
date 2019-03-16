package redis.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.content.util.JedisClient;
import com.taotao.content.util.JedisClientPool;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class TestJedis {

	//连接单机版Redis
	@Test
	public void test1() {
		Jedis jedis = new Jedis("192.168.25.133", 6379);
		System.out.println(jedis.ping());
	}
	
	//使用Redis连接池连接
	@Test
	public void testRedisPool() {
		//创建Redis连接池(单例)
		JedisPool jedisPool = new JedisPool("192.168.25.133", 6379);
		//获取连接
		Jedis jedis = jedisPool.getResource();
		String s = jedis.ping();
		System.out.println(s);
		//关闭连接
		jedis.close();
	}
	
	//连接集群
	@Test
	public void testJedeisCluster() {
		//创建JedisCluster对象  构造参数是Set类型  HostAndPort
		Set<HostAndPort> nodes = new HashSet<>();
		//把所有节点加入Set
		nodes.add(new HostAndPort("192.168.25.133", 6379));
		nodes.add(new HostAndPort("192.168.25.133", 6380));
		nodes.add(new HostAndPort("192.168.25.133", 6381));
		nodes.add(new HostAndPort("192.168.25.133", 6389));
		nodes.add(new HostAndPort("192.168.25.133", 6390));
		nodes.add(new HostAndPort("192.168.25.133", 6391));
		JedisCluster cluster = new JedisCluster(nodes );
		//使用jedisCluster操作Redis  自带连接池  jedisCluster对象可以是单例的
		cluster.set("jedisCluster", "success");
		String string = cluster.get("jedisCluster");
		System.out.println(string);
		
		cluster.close();
	}
	
	
	//整合spring  连接集群/单机   直接在applicationContext-redis.xml中配置即可  由于不是使用的同一个接口
	@Test
	public void testJedisSpring1() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		JedisClient pool = applicationContext.getBean(JedisClient.class);
		pool.hset("hset1", "id", "123");
		String s1 = pool.hget("hset1", "id");
		System.out.println(s1);
	
	}
	
}
