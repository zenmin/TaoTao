package com.taotao.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.JsonUtils;

@Controller
public class UserController {

	@Autowired
	private UserService service;
	
	// 用户注册检查
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public TaotaoResult checkData(@PathVariable String param,@PathVariable int type) {
	
		TaotaoResult data = service.checkData(param, type);
		
		return data;
		
	}
	
	//注册逻辑
	@ResponseBody
	@RequestMapping(value = "/user/register",method = RequestMethod.POST)
	public TaotaoResult userReg(TbUser user) {
		
		TaotaoResult result = service.register(user);
		
		return result;
	}
	
	//登陆逻辑
	@ResponseBody
	@RequestMapping(value = "/user/login",method = RequestMethod.POST)
	public TaotaoResult login(String username,String password,HttpServletResponse response,HttpServletRequest request) {
		
		TaotaoResult taotaoResult = service.login(username, password);
		//如果登陆成功   设置Cookie
		if(taotaoResult.getStatus()==200) {
			//设置Cookie
			CookieUtils.setCookie(request, response, "TAOTAO_USERINFO", taotaoResult.getData().toString());
		}
		return taotaoResult;
	}
	
	/**
	* @Title: getUserByToken
	* @Description:jsonp第一种方法：
	* @param token
	* @param callback
	* @return
	
	//根据token查询状态														//指定相应的Content-Type为json  因为前端已经指定相应的是jsonp了
	@RequestMapping(value = "/user/token/{token}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getUserByToken(@PathVariable String token,String callback) {

		//取用户信息
		TaotaoResult result = service.getUserByToken(token);
		//判断是否为jsonp请求
		if(StringUtils.isNotBlank(callback)) {
			//处理jsonp  相当于给客户端返回一个js方法  callback是方法名  括号内是参数
			return callback + "(" + JsonUtils.objectToJson(result) + ")";
		}
		
		//如果不是jsonp
		return JsonUtils.objectToJson(result);
		
	}
	*/
	
	
	//根据token查询状态														
		/**
		* @Title: getUserByToken
		* @Description:jsonp第二种方法   Spring4.1以上版本
		* @param token
		* @param callback
		* @return
		*/
		@RequestMapping(value = "/user/token/{token}",method = RequestMethod.GET)
		@ResponseBody
		public Object getUserByToken(@PathVariable String token,String callback) {

			//取用户信息
			TaotaoResult result = service.getUserByToken(token);
			//判断是否为jsonp请求
			if(StringUtils.isNotBlank(callback)) {
				//包装pojo并且序列化
				MappingJacksonValue value = new MappingJacksonValue(result);
				//设置jsonp回调方法
				value.setJsonpFunction(callback);
				//返回此jsonp包装类
				return value;
			}
			//如果不是jsonp
			return JsonUtils.objectToJson(result);
		}
	
		
	//登出逻辑
	@RequestMapping(value = "/user/logout/{token}")
	public String getUserByToken(@PathVariable String token,HttpServletResponse response,HttpServletRequest request) {
	
		//执行登出
		service.logOut(token);
		
		//移除Cookie
		CookieUtils.deleteCookie(request, response, "TAOTAO_USERINFO");
		return "redirect:/page/login";
	}		
			
	
}
