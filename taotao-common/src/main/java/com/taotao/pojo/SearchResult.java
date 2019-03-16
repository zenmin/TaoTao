package com.taotao.pojo;

import java.io.Serializable;
import java.util.List;

/**
* @Title SearchResult
* @Description 本类主要功能是搜索结果Bean
* @Company null
* @author 曾敏
* @date 2017年9月23日上午10:01:30
*/
public class SearchResult implements Serializable{
	private static final long serialVersionUID = 1L;
	private long totalpage;
	private long count;
	private List<SearchItem> list;
	public long getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(long totalpage) {
		this.totalpage = totalpage;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public List<SearchItem> getList() {
		return list;
	}
	public void setList(List<SearchItem> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "SearchResult [totalpage=" + totalpage + ", count=" + count + ", list=" + list + "]";
	}
	
	
}
