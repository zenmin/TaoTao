package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	@RequestMapping("/page/login")
	public String showlogin(String url,Model model) {
		model.addAttribute("redirect", url);
		return "login";
	}
	
	@RequestMapping("/page/reg")
	public String showReg() {
		return "register";
	}
	
	
}
