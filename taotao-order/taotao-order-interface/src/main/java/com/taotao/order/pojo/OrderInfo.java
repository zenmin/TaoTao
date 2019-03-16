package com.taotao.order.pojo;

import java.io.Serializable;
import java.util.List;

import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

public class OrderInfo extends TbOrder implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<TbOrderItem> orderItems;
	private List<TbOrderShipping> orderShipping;
	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public List<TbOrderShipping> getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(List<TbOrderShipping> orderShipping) {
		this.orderShipping = orderShipping;
	}
	
}
