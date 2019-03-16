package com.taotao.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;
@Controller
public class CartController {

	@Autowired 
	private ItemService service;
	@Value("${CART_EXPIER}")
	private Integer cart_expier;
	
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId,@RequestParam(defaultValue = "1") Integer num,HttpServletResponse rep,HttpServletRequest req) {
	
		//判断要添加的商品是否已经存在cookie中
		boolean flag = false;
		List<TbItem> list = getItemCart(req);
		for (TbItem t : list) {
			if(t.getId() == itemId.longValue()) {
				t.setNum(t.getNum()+num);
				flag = true;
			}
		}
		//如果不存在 往cookie里面添加一个新的商品
		if(flag == false) {
			TbItem item = service.getItemById(itemId);
			item.setNum(num);
			//取一张图片
			String image = item.getImage();
			if(image!="") {
				String[] split = image.split(",");
				item.setImage(split[0]);
			}
			//添加到购物车中
			list.add(item);
		}
		
		CookieUtils.setCookie(req, rep, "CART_KEY", JsonUtils.objectToJson(list), cart_expier, true);
		
		return "cartSuccess";
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
	
	
	@RequestMapping("/cart/cart")
	public String showCart(HttpServletRequest request) {
		List<TbItem> list = getItemCart(request);
		request.setAttribute("cartList", list);
		return "cart";
	}
	
	@RequestMapping("/cart/update/num/{itemid}/{num}")
	@ResponseBody
	public TaotaoResult updateNum(@PathVariable Long itemid,Integer num,HttpServletRequest req,HttpServletResponse rep) {
		
		//遍历Cookie
		List<TbItem> itemCart = getItemCart(req);
		for (TbItem t : itemCart) {
			if(t.getId() == itemid.longValue()) {
				t.setNum(num);
			}
		}
		//写入COokie
		CookieUtils.setCookie(req, rep, "CART_KEY", JsonUtils.objectToJson(itemCart), cart_expier, true);
		return TaotaoResult.ok();
	}
	
	@RequestMapping("/cart/delete/num/{itemid}")
	public String deleteNum(@PathVariable Long itemid,HttpServletRequest req,HttpServletResponse rep) {
		
		List<TbItem> itemCart = getItemCart(req);
		for (TbItem tbItem : itemCart) {
			if(tbItem.getId() == itemid.longValue()) {
				itemCart.remove(tbItem);
				break;
			}
		}
		//写入COokie
		CookieUtils.setCookie(req, rep, "CART_KEY", JsonUtils.objectToJson(itemCart), cart_expier, true);
		return "redirect:/cart/cart.html";
	}
}


















