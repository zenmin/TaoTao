package com.taotao.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.swing.border.TitledBorder;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.CreateOrder;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbUser;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;

/**
* @Title ItemController
* @Description 本类主要功能是展示订单确认页面
* @Company null
* @author 曾敏
*/
@Controller
public class OrderController {

	@Autowired
	private CreateOrder service;
	
	@RequestMapping("/order/order-cart")
	public String showOrder(HttpServletRequest req) {
		//用户是否登录
		
		//取用户id
		TbUser user = (TbUser) req.getAttribute("user");
		//取收货地址列表
		System.out.println(user.getUsername());
		//Cookie中取购物车商品列表展示到页面
		List<TbItem> cartList = getItemCart(req);
		req.setAttribute("cartList", cartList);
		return "order-cart";
	}
	
	//从cookie中取购物车信息
	private List<TbItem> getItemCart(HttpServletRequest req) {
		String json = CookieUtils.getCookieValue(req, "CART_KEY", true);
		List<TbItem> list = new ArrayList<>();
		if (StringUtils.isBlank(json)) {
			return list;
		}else {
			list = JsonUtils.jsonToList(json, TbItem.class);
		}
	return list;
	}
	
	@RequestMapping(value = "/order/create" , method =RequestMethod.POST )
	public String createOrder(OrderInfo orderInfo,Model model){
		TaotaoResult order = service.createOrder(orderInfo);
		model.addAttribute("orderId", order.getData().toString());
		model.addAttribute("payment", orderInfo.getPayment());
		DateTime time = new DateTime();
		model.addAttribute("date", time.plusDays(3).toString("yyyy-MM-dd"));
		return "success";
	}
	
	
}
