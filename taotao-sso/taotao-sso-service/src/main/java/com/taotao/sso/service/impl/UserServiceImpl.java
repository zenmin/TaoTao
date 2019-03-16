package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.alibaba.druid.support.json.JSONUtils;
import com.sun.tools.internal.xjc.model.CElement;
import com.taotao.dao.TbUserMapper;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.service.util.JedisClient;
import com.taotao.sso.service.UserService;
import com.taotao.utils.JsonUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper mapper;
	@Autowired
	private JedisClient jedis;
	// 判断用户的信息是否可用
	@Override
	public TaotaoResult checkData(String data, int type) {
		// TODO Auto-generated method stub
		// type 1:用户名 2:电话 3:邮箱
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		switch (type) {
		case 1:
			criteria.andUsernameEqualTo(data);
			break;
		case 2:
			criteria.andPhoneEqualTo(data);
			break;
		case 3:
			criteria.andEmailEqualTo(data);
			break;
		default:
			return TaotaoResult.build(500, "参数有误");
		}
		List<TbUser> list = mapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return TaotaoResult.ok(false);
		}
		return TaotaoResult.ok(true);
	}

	//用户注册处理
	@Override
	public TaotaoResult register(TbUser user) {
		// TODO Auto-generated method stub
		//补全数据
		if(StringUtils.isBlank(user.getUsername())) {
			return TaotaoResult.build(500, "用户名为空");
		}
		//如果不为true  说明用户名重复
		if(!(boolean) checkData(user.getUsername(),1).getData()) {
			return TaotaoResult.build(500, "用户名重复");
		}
		if(StringUtils.isBlank(user.getPassword())) {
			return TaotaoResult.build(500, "密码为空");
		}
		
		if(StringUtils.isNotBlank(user.getPhone())) {
			if(!(boolean) checkData(user.getPhone(),2).getData()) {
				return TaotaoResult.build(500, "手机号重复");
			}
		}
		if(StringUtils.isNotBlank(user.getEmail())) {
			if(!(boolean) checkData(user.getEmail(),3).getData()) {
				return TaotaoResult.build(500, "邮箱重复");
			}
		}
		//补全属性
		user.setCreated(new Date());
		user.setUpdated(new Date());
		//MD5加密
		String pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(pwd);
		
		//插入数据
		mapper.insertSelective(user);
		
		return TaotaoResult.ok();
	}

	//登陆流程
	@Override
	public TaotaoResult login(String username, String password) {

		TbUserExample example = new TbUserExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUsernameEqualTo(username);
		//密码需要md5加密之后再校验
		createCriteria.andPasswordEqualTo(DigestUtils.md5DigestAsHex(password.getBytes()));
		List<TbUser> list = mapper.selectByExample(example);
		if(list.size()==0) {
			return TaotaoResult.build(500, "用户名或密码不正确");
		}
		
		//登陆成功
		
		//生成token  即uuid  作为sessionID
		String token = UUID.randomUUID().toString();
		//用户信息保存到redis
		TbUser user = list.get(0);
		//清除密码  因为可能要保存到cookie中
		user.setPassword("");
		jedis.set("SESSION:" + token, JsonUtils.objectToJson(user));
		//设置过期时间	1800秒
		jedis.expire("SESSION:" + token, 1800);

		return TaotaoResult.ok(token);
	}

	@Override
	public TaotaoResult getUserByToken(String token) {
		// TODO Auto-generated method stub
		String json = jedis.get("SESSION:" + token);
		if(StringUtils.isBlank(json)) {
			return TaotaoResult.build(500, "登陆已经过期");
		}
		
		//这里查出的是字符串    要响应对象  
		TbUser jsonToPojo = JsonUtils.jsonToPojo(json, TbUser.class);
		
		//重置过期时间
		jedis.expire("SESSION:" + token, 1800);
		
		return TaotaoResult.ok(jsonToPojo);
	}

	@Override
	public TaotaoResult logOut(String token) {
		// TODO Auto-generated method stub
		//设置session已经过期
		jedis.expire("SESSION:" + token, -1);
		return TaotaoResult.ok();
	}

}
