package com.taotao.service;

import com.taotao.pojo.EasyUIDataResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface ItemService {

	TbItem getItemById(long id);
	EasyUIDataResult getItemList(int page,int rows);
	TaotaoResult addItem(TbItem item,String desc);
	TbItemDesc getItemDescById(long id);
}
