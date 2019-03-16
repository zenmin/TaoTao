package com.taotao.pojo;

import java.io.Serializable;


/**
* @Title SearchItem
* @Description 本类主要功能是搜索商品列表   索引库中
* @Company null
* @author 曾敏
* @date 2017年9月23日上午9:50:03
*/
public class SearchItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String title;
	private String sell_point;
	private long price;
	private String image;
	private String cat_name;
	private String item_desc;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSell_point() {
		return sell_point;
	}
	public void setSell_point(String sell_point) {
		this.sell_point = sell_point;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getCat_name() {
		return cat_name;
	}
	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}
	public String getItem_desc() {
		return item_desc;
	}
	public void setItem_desc(String item_desc) {
		this.item_desc = item_desc;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "SearchItem [id=" + id + ", title=" + title + ", sell_point=" + sell_point + ", price=" + price
				+ ", imgae=" + image + ", cat_name=" + cat_name + ", item_desc=" + item_desc + "]";
	}
 
}
