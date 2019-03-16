package com.taotao.item.pojo;

import com.taotao.pojo.TbItem;

/**
* @Title Item
* @Description 本类主要功能是写词POjo是为了把图片拆分
* @Company null
* @author 曾敏
* @date 2017年9月29日下午5:14:57
*/
public class Item extends TbItem{
	
	public Item(TbItem tbItem) {
		// TODO Auto-generated constructor stub
		this.setId(tbItem.getId());
		this.setBarcode(tbItem.getBarcode());
		this.setCid(tbItem.getCid());
		this.setCreated(tbItem.getCreated());
		this.setImage(tbItem.getImage());
		this.setNum(tbItem.getNum());
		this.setPrice(tbItem.getPrice());
		this.setSellPoint(tbItem.getSellPoint());
		this.setStatus(tbItem.getStatus());
		this.setTitle(tbItem.getTitle());
		this.setUpdated(tbItem.getUpdated());
	}
	
	//使用item，images[0]即会调用此方法
	public String[] getImages() {
		if(this.getImage()!=null && this.getImage().equals("")==false) {
			String[] strings = this.getImage().split(",");
			return strings;
		}
		return null;
	}
	
}
