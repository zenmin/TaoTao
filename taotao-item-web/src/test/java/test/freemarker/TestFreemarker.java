package test.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
/**
* @Title TestFreemarker
* @Description 本类主要功能是FreeMarker使用方法
* @Company null
* @author 曾敏
* @date 2017年10月1日上午10:03:13
*/
@SuppressWarnings("rawtypes")

public class TestFreemarker {
	@SuppressWarnings("unchecked")
	@Test
	public void TestFreemarker1() throws IOException, TemplateException {
		
		//创建一个模板文件
		
		//创建Configuration对象
		Configuration configuration = new Configuration(Configuration.getVersion());
		//设置模板所在路径
		configuration.setDirectoryForTemplateLoading(new File("C:\\Java\\eclipse\\workspace\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\ftl\\"));
		//设置模板的字符编码
		configuration.setDefaultEncoding("UTF-8");
		//使用configuration加载一个模板文件并指定文件名
		Template template = configuration.getTemplate("helloworld.ftl");
		//创建一个数据集  pojo/map
		Map map = new HashMap<>();
		map.put("hello", "hello world");
		//创建一个输出路径和文件名
		Writer out = new FileWriter("C:\\Users\\fx\\Desktop\\free.html");
		//使用模板对象的process输出文件
		template.process(map, out);
		//关流
		out.close();
	}
	
	/**
	* @Title: TestFreemarker2
	* @Description:测试循环
	* @throws IOException
	* @throws TemplateException
	*/
	@SuppressWarnings("unchecked")
	@Test
	public void TestFreemarker2() throws IOException, TemplateException {
		
		//创建一个模板文件
		
		//创建Configuration对象
		Configuration configuration = new Configuration(Configuration.getVersion());
		//设置模板所在路径
		configuration.setDirectoryForTemplateLoading(new File("C:\\Java\\eclipse\\workspace\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\ftl\\"));
		//设置模板的字符编码
		configuration.setDefaultEncoding("UTF-8");
		//使用configuration加载一个模板文件并指定文件名
		Template template = configuration.getTemplate("person.ftl");
		//创建一个数据集  pojo/map
		Map map = new HashMap<>();
		List<Person> list = new ArrayList<>();
		list.add(new Person("zm", 20, "男"));
		list.add(new Person("zm1", 21, "男"));
		list.add(new Person("zm2", 22, "男"));
		list.add(new Person("zm3", 23, "男"));
		list.add(new Person("zm4", 24, "男"));
		map.put("person", list);
		map.put("date", new Date());
//		map.put("abc", "345");
		map.put("hello", "hello world");
		
		//创建一个输出路径和文件名
		Writer out = new FileWriter("C:\\Users\\fx\\Desktop\\free1.html");
		//使用模板对象的process输出文件
		template.process(map, out);
		//关流
		out.close();
	}
	
	
	@Test
	public void test3() {
		double bmoney = 3;
		double x = 0;
		for(int i=0;;i++) {
			x+=bmoney;
			bmoney-=0.06;
			System.out.println(x);
			if(bmoney<=0) {
				System.out.println(i);
				break;
			}
		}
	}
}
