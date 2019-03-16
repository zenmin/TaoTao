package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.EasyUITreeNode;
import com.taotao.service.ItemCatService;


/**
* @Title ItemCatController
* @Description 本类主要功能是商品分类管理
* @Company null
* @author 曾敏
* @date 2017年9月2日上午10:28:31
*/
@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService service;
	

	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(@RequestParam(name="id",defaultValue="0")long parentId){
		
		//若url不带参数   直接查询所有父节点   若带参数   就查询父节点下的所有子节点
		
		List<EasyUITreeNode> list = service.getItemCatList(parentId);
		return list;
	}
	
}
