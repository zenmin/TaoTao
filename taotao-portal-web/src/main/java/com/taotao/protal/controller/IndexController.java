package com.taotao.protal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.protal.pojo.ADImage;
import com.taotao.utils.JsonUtils;


/**
 * @Title IndexController
 * @Description 本类主要功能是首页跳转
 * @Company null
 * @author 曾敏
 * @date 2017年9月5日下午4:07:03
 */
@Controller
public class IndexController {

	//从配置文件中取出属性
	@Value("${AD1_CateGoryId}")
	private Long AdCid;			//首页广告位分类id
	@Value("${AD1_WIDTH}")
	private Integer width;
	@Value("${AD1_WIDTH_B}")
	private Integer widthB;
	@Value("${AD1_HEIGHT}")
	private Integer height;
	@Value("${AD1_HEIGHT_B}")
	private Integer heightB;
	
	@Autowired
	private ContentService service;
	
	//web.xml配置的index.html  这里首页加载即会访问这个controller
	@RequestMapping("index")
	public String showIndex(Model model) {
		
		//根据分类id查询出轮播广告
		List<TbContent> list = service.getConetentByCId(AdCid);
		//把多个TBcontent对象放入list中
		List<ADImage> list2 = new ArrayList<>();
		for (TbContent t : list) {
			ADImage ad = new ADImage();
			ad.setAlt(t.getTitle());
			ad.setHref(t.getUrl());
			ad.setHeight(height);
			ad.setHeightB(heightB);
			ad.setWidth(width);
			ad.setWidthB(widthB);
			ad.setSrc(t.getPic());
			ad.setSrcB(t.getPic2());
//			System.out.println(ad);
			list2.add(ad);
		}
		
		//转换为json  响应到页面上
		String json = JsonUtils.objectToJson(list2);
		model.addAttribute("ad1", json);
		return "index";
	}
}
