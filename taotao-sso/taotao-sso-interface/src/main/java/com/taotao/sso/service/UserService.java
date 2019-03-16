package com.taotao.sso.service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface UserService {
	public TaotaoResult checkData(String data,int type);
	TaotaoResult register(TbUser user);
	TaotaoResult login(String username,String password);
	TaotaoResult getUserByToken(String token);
	TaotaoResult logOut(String token);
}
