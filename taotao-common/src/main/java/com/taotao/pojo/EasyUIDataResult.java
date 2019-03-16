package com.taotao.pojo;

import java.io.Serializable;
import java.util.List;


/**
* @Title EasyUIDataResult
* @Description 本类主要功能是响应EasyUI的结果   必须要有下面两个属性
* @Company null
* @author 曾敏
* @date 2017年8月31日上午11:40:59
*/
@SuppressWarnings("rawtypes")
public class EasyUIDataResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long total;
	private List rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	
	
}
