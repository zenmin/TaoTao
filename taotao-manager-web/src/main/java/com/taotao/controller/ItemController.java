package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.EasyUIDataResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;


/**
* @Title ItemController
* @Description 本类主要功能是商品管理Controller
* @Company null
* @author 曾敏
* @date 2017年8月29日下午7:39:27
*/
@Controller
public class ItemController {

	@Autowired
	private ItemService service;
	
	//查询商品
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemByid(@PathVariable long itemId) {
				
		TbItem item = service.getItemById(itemId);
		
		return item;
	}
	
	//显示商品列表
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataResult getItemList(@RequestParam Integer page,@RequestParam Integer rows) {

		EasyUIDataResult list = service.getItemList(page, rows);
		
		return list;
	}
	
	//新增商品
	@RequestMapping("/item/save")
	@ResponseBody
	public TaotaoResult addItem(TbItem item,String desc) {
		TaotaoResult result = service.addItem(item, desc);
		return result;
	}
}
