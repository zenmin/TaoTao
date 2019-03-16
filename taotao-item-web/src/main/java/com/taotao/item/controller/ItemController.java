package com.taotao.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;

/**
* @Title ItemController
* @Description 本类主要功能是根据id查询出商品的属性和商品的详细信息
* @Company null
* @author 曾敏
* @date 2017年9月30日下午6:20:14
*/
@Controller
public class ItemController {

	@Autowired
	private ItemService service;
	
	@RequestMapping("/item/{id}")
	public String showItem(@PathVariable Long id,Model model) {
		//查商品
		TbItem item = service.getItemById(id);
		//可以拆分图片的pojo
		Item item2 = new Item(item);		
		//查详细信息
		TbItemDesc desc = service.getItemDescById(id);
		
		//添加Model
		model.addAttribute("item",item2);		//商品信息
		model.addAttribute("itemDesc",desc);	//详细信息
		
		//返回视图
		return "item";
		
	}
}
