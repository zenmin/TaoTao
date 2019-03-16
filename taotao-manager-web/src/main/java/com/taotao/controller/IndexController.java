package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TaotaoResult;
import com.taotao.search.service.ImportItemToSolrService;



/**
* @Title IndexController
* @Description 本类主要功能是索引库管理
* @Company null
* @author 曾敏
* @date 2017年9月22日下午4:45:52
*/
@Controller
public class IndexController {

	@Autowired
	private ImportItemToSolrService service;
	
	@RequestMapping("/index/import")
	@ResponseBody
	public TaotaoResult importIndex() {
		TaotaoResult result = service.importItemToSolr();
		
		return result;
	}
}
