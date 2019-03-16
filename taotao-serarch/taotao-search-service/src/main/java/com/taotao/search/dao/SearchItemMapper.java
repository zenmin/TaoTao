package com.taotao.search.dao;

import java.util.List;

import com.taotao.pojo.SearchItem;

public interface SearchItemMapper {

	public List<SearchItem> getItemList(); 
	public List<SearchItem> getItemListById(long id); 
	
}
