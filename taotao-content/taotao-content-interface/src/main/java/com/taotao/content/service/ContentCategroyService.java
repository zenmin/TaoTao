package com.taotao.content.service;

import java.util.List;

import com.taotao.pojo.EasyUITreeNode;
import com.taotao.pojo.TaotaoResult;

public interface ContentCategroyService {

	List<EasyUITreeNode> getCategroyList(long id);
	TaotaoResult addContentCategory(long parentId,String name);
	TaotaoResult delContentCategory(long id);
	TaotaoResult updateContentCategory(long id,String name);
}
