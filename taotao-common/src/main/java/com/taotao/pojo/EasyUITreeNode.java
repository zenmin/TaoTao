package com.taotao.pojo;

import java.io.Serializable;

/**
* @Title EasyUITreeNode
* @Description 本类主要功能是返回商品类目构建EasyUI接收的Json数据
* @Company null
* @author 曾敏
* @date 2017年9月2日上午10:24:13
*/
public class EasyUITreeNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String text;
	private String state;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	
	
}
