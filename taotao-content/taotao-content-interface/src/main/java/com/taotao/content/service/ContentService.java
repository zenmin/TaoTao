package com.taotao.content.service;

import java.util.List;

import com.taotao.pojo.EasyUIDataResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	TaotaoResult addContent(TbContent content);
	EasyUIDataResult getContentById(long id,int page, int rows);
	TaotaoResult updateContent(TbContent content);
	TaotaoResult delContent(String ids);
	List<TbContent> getConetentByCId(long id);
	
	
}
