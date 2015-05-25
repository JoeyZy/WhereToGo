package com.luxoft.wheretogo.controller;

import entity.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @RequestMapping("/hello")
    public String hello(@RequestParam(value = "name", required = false, defaultValue = "HelloWorld") String name, Model model) {
        model.addAttribute("name", name);
        return "/WEB-INF/views/helloworld.jsp";
    }

    @RequestMapping("/categories")
    public String categories(Model model) {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(0, "Nature"));
        categoryList.add(new Category(1, "Movie"));
        categoryList.add(new Category(2, "Theatre"));
        categoryList.add(new Category(2, "Sport"));
        model.addAttribute("categories", categoryList);
        return "/WEB-INF/views/categories.jsp";
    }

}
