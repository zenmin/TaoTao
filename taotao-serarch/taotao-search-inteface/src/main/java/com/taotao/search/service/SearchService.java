package com.taotao.search.service;

import com.taotao.pojo.SearchResult;

public interface SearchService {

	public SearchResult search(String query,int page,int rows)  throws Exception ;
	
}
