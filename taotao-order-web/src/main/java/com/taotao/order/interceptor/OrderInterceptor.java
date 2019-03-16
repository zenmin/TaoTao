package com.taotao.order.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import com.taotao.utils.CookieUtils;

/**
* @Title ItemController
* @Description 本类主要功能是拦截器  判断是否登录
* @Company null
* @author 曾敏
*/

public class OrderInterceptor implements HandlerInterceptor{
	
	@Autowired 
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//执行Handler  先执行此方法
		
		//从cookie中  取token
		String value = CookieUtils.getCookieValue(request, "TAOTAO_USERINFO");
		//如果没登录  跳到sso  需要把当前前的url传给sso  登录成功之后  在跳转回来
		if(StringUtils.isBlank(value)) {
			//跳转登录页面
			response.sendRedirect("http://localhost:8088/page/login?url=" + request.getRequestURL().toString());
			return false;
		}
		//取到token  调用sso判断用户是否登录
		
		//如果用户未登录即没取到登录信息  跳转到sso登录页面
		TaotaoResult taotaoResult = userService.getUserByToken(value);
		if(taotaoResult.getStatus() != 200) {
			//跳转登录页面
			response.sendRedirect("http://localhost:8088/page/login?url=" + request.getRequestURL().toString());
			return false;
		}
		//如果取到  把用户信息放在request中
		 TbUser user  = (TbUser) taotaoResult.getData();
		request.setAttribute("user",user);
		
		//返回true  放行   返回false  拦截
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//handler  执行之后  ModelAndView返回之前   处理ModelAndView
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//在ModelAndView返回之后
	}
	
}
