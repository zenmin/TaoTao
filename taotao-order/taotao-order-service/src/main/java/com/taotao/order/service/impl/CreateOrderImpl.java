package com.taotao.order.service.impl;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.content.util.JedisClient;
import com.taotao.dao.TbOrderItemMapper;
import com.taotao.dao.TbOrderMapper;
import com.taotao.dao.TbOrderShippingMapper;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.CreateOrder;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
@Service
public class CreateOrderImpl implements CreateOrder{

	@Autowired
	private TbOrderMapper ordermapper;	//订单信息表
	@Autowired
	private TbOrderShippingMapper ordershipmapper; 		//物流信息
	@Autowired
	private TbOrderItemMapper orderitemmapper;	//商品信息
	@Value("${ORDER_KEY}")
	private String ORDER_KEY;	//Redis中的key
	@Value("${DEFAULT_VALUE}")
	private String DEFAULT_VALUE;	//Redis中的key
	@Value("${DEFAULT_ITEM_VALUE}")
	private String DEFAULT_ITEM_VALUE;	//Redis中的key
	@Autowired
	private JedisClient jedisClient;

	public TaotaoResult createOrder(OrderInfo info) {
		// 生成订单号  使用redis的incr生成
		if(jedisClient.exists(ORDER_KEY)) {
			jedisClient.set(ORDER_KEY, DEFAULT_VALUE);
		}
		String orderid =jedisClient.incr(ORDER_KEY).toString();
		//向订单表插入数据  需要补全时间等属性
		info.setOrderId(orderid);
		info.setPostFee("0");
		//设置状态
		info.setStatus(1);
		info.setUpdateTime(new Date());
	
		//订单明细 商品表插入数据
		ordermapper.insert(info);
		List<TbOrderItem> items = info.getOrderItems();
		for (TbOrderItem i : items) {
			String oid = jedisClient.incr(DEFAULT_ITEM_VALUE).toString();
			i.setId(oid);
			i.setOrderId(orderid);
			orderitemmapper.insert(i);
		}
		TbOrderShipping record = new TbOrderShipping();
		record.setCreated(new Date());
		record.setOrderId(orderid);
		record.setUpdated(new Date());
		ordershipmapper.insert(record);
		return TaotaoResult.ok(orderid);
	}
	
}
