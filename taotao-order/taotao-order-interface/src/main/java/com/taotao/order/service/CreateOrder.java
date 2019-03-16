package com.taotao.order.service;

import com.taotao.order.pojo.OrderInfo;
import com.taotao.pojo.TaotaoResult;

public interface CreateOrder {

	TaotaoResult createOrder(OrderInfo info);
}
