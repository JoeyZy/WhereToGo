package com.luxoft.wheretogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luxoft.wheretogo.repositories.AbstractRepository;

@Controller
public class MainController {

	@Autowired
	@Qualifier("categories")
	private AbstractRepository categoryRepository;

	@Autowired
	@Qualifier("events")
	private AbstractRepository eventRepository;

	@RequestMapping("/hello")
	public String hello(@RequestParam(value = "name", required = false, defaultValue = "HelloWorld") String name, Model model) {
		model.addAttribute("name", name);
		return "/WEB-INF/views/helloworld.jsp";
	}

	@RequestMapping("/categories")
	public String categories(Model model) {
		model.addAttribute("categories", categoryRepository.findAll());
		return "/WEB-INF/views/categories.jsp";
	}

	@RequestMapping("/event")
	public String event(@RequestParam(value = "name", required = true) String name, Model model) {
		model.addAttribute("event", eventRepository.getByName(name));
		return "/WEB-INF/views/event.jsp";
	}

	@RequestMapping("/addCategory")
	public String event() {
		return "/WEB-INF/views/addCategory.jsp";
	}
}
