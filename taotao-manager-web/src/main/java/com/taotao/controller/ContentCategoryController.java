package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.content.service.ContentCategroyService;
import com.taotao.pojo.EasyUITreeNode;
import com.taotao.pojo.TaotaoResult;



/**
* @Title ContentCategoryController
* @Description 本类主要功能是内容分类管理
* @Company null
* @author 曾敏
* @date 2017年9月5日下午5:20:38
*/
@Controller
public class ContentCategoryController {

	@Autowired
	private ContentCategroyService service;
	
	//查询全部
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCategoryList(@RequestParam(defaultValue = "0") long id){
		
		List<EasyUITreeNode> categroyList = service.getCategroyList(id);
		
		return categroyList;
	}
	//创建节点
	@RequestMapping("/content/category/create")
	@ResponseBody
	public TaotaoResult addCategory(long parentId,String name){
		TaotaoResult result = service.addContentCategory(parentId, name);
		
		return result;
	}
	
	//删除
	@RequestMapping("/content/category/delete")
	@ResponseBody
	public TaotaoResult delCategory(long id) {
		service.delContentCategory(id);
		return TaotaoResult.ok();
	}
	
	//更新
	@RequestMapping("/content/category/update")
	@ResponseBody
	public TaotaoResult updateCategory(long id,String name) {
		service.updateContentCategory(id, name);
		return TaotaoResult.ok();
	}
}
