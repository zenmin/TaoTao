package com.taotao.item.controller;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
public class TestFreemarkerController {

	@Autowired
	private FreeMarkerConfigurer config;

	@RequestMapping("/html")
	@ResponseBody
	public String test1() throws Exception {
		// 从注入的configuration中获取模板
		Configuration configuration = config.getConfiguration();
		Template template = configuration.getTemplate("helloworld.ftl");
		Map data = new HashMap<>();
		data.put("hello", "spring freemarker");
		// 生成
		Writer out = new FileWriter("C:\\Users\\fx\\Desktop\\test1.html");
		template.process(data, out);
		// 关流
		out.close();
		return "ok";
	}
}
