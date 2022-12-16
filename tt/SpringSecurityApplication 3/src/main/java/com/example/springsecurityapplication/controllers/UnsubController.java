package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/unsub")
public class UnsubController {

	ProductRepository productRepository;

	@Autowired
	public UnsubController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@GetMapping("/index")
	public String index(Model model){
		model.addAttribute("products", productRepository.findAll());
		return "unsub/main";
	}
}
