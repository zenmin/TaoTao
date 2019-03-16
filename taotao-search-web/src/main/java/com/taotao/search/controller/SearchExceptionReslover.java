package com.taotao.search.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
* @Title SearchExceptionReslover
* @Description 本类主要功能是全局异常处理器
* @Company null
* @author 曾敏
* @date 2017年9月26日下午7:10:53
*/
public class SearchExceptionReslover implements HandlerExceptionResolver{

	//创建本类Logger工厂
	private static final Logger logger = LoggerFactory.getLogger(SearchExceptionReslover.class);
	
	
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		// TODO Auto-generated method stub
		
		logger.info("进入异常处理");
		logger.debug("类型：",handler.getClass());
		//打印异常信息
		ex.printStackTrace();
		//想日志文件写入异常
		logger.error("发生异常",ex);
		
		ModelAndView andView = new ModelAndView();
		andView.addObject("message","服务器繁忙，请稍后再试！");
		//设置响应页面
		andView.setViewName("error/exception");
		
		return null;
		
		
	}

}
