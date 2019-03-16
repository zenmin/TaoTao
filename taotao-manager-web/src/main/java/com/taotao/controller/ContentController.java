package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.EasyUIDataResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

/**
* @Title ContentController
* @Description 本类主要功能是内容管理
* @Company null
* @author 曾敏
* @date 2017年9月19日下午6:33:59
*/
@Controller
public class ContentController {

	@Autowired
	private ContentService service;
	
	//新增
	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult addContent(TbContent content) {
		
		TaotaoResult addContent = service.addContent(content);
		
		return addContent;
	}
	
	//根据分类id查询内容
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataResult getContentById(@RequestParam("categoryId") long id, int page, int rows) {
		EasyUIDataResult result = service.getContentById(id, page, rows);
//		System.out.println(result.getRows());
		return result;
	}
	
	//更新
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public TaotaoResult updateContent(TbContent content) {
	
		service.updateContent(content);
		
		return TaotaoResult.ok();
		
	}
	
	
	//删除
	@RequestMapping("/content/delete")
	@ResponseBody
	public TaotaoResult delContent(@RequestParam String ids) {
	
		service.delContent(ids);
		
		return TaotaoResult.ok();
		
	}
	
	
	
	
}
