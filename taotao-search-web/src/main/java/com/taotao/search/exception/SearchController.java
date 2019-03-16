package com.taotao.search.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.pojo.SearchResult;
import com.taotao.search.service.SearchService;

/**
* @Title SearchController
* @Description 本类主要功能是查询商品Controller
* @Company null
* @author 曾敏
* @date 2017年9月24日上午10:47:15
*/
@Controller
public class SearchController {

	@Autowired
	private SearchService service;

	@Value("${rows}")
	private Integer rows;
	
	@RequestMapping("/search")
	public String search(@RequestParam("q") String query,
			@RequestParam(value = "page", defaultValue = "1") Integer page,Model model) throws Exception {
		System.out.println(1);
//			int a = 1/0;
			query = new String(query.getBytes("iso8859-1"), "utf-8");
//			System.out.println(query);
			SearchResult search = service.search(query, page, rows);
			// 把结果传递给页面
			model.addAttribute("query", query);
			model.addAttribute("totalPages", search.getTotalpage());
			model.addAttribute("itemList", search.getList());
			model.addAttribute("page", page);			
		return "search";
	}
}
