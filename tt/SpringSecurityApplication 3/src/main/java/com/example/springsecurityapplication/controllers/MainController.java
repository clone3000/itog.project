package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.repositories.ProductRepository;
import com.example.springsecurityapplication.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class MainController {

	private final ProductRepository productRepository;
	private final ProductService productService;

	@Autowired
	public MainController(ProductRepository productRepository, ProductService productService) {
		this.productRepository = productRepository;
		this.productService = productService;
	}

	// Данный метод предназначен для отображении товаров без прохождения аутентификации и авторизации
	@GetMapping("")
	public String getAllProduct(Model model) {
		model.addAttribute("products", productService.getAllProduct());
		return "product/product";
	}

	@GetMapping("/info/{id}")
	public String infoProduct(@PathVariable("id") int id, Model model) {
		model.addAttribute("product", productService.getProductId(id));
		return "product/infoProduct";
	}

	private int getCodeByName(String name){
		switch (name){
			case "furniture" : return  1;
			case "appliances" : return 2;
			case "clothes" : return 3;
		}
		return 1;
	}

	@PostMapping("/search")
	public String productSearch(
		@RequestParam("search") String search,
		@RequestParam("ot") String ot,
		@RequestParam("do") String Do,
		@RequestParam(value = "price", required = false, defaultValue = "") String price,
		@RequestParam(value = "category", required = false, defaultValue = "") String category,
		Model model
	) {
		if(price.equals("sorted_by_ascending_price")){
			model.addAttribute(
				"search_product",
				productRepository.findAsc(
					search.isEmpty() ? null : search.toLowerCase(),
					category.isEmpty() ? null : getCodeByName(category),
					ot.isEmpty() ? null : Float.parseFloat(ot),
					Do.isEmpty() ? null : Float.parseFloat(Do)
				));
		} else {
			model.addAttribute(
				"search_product",
				productRepository.findDesc(
					search.isEmpty() ? null : search.toLowerCase(),
					category.isEmpty() ? null : getCodeByName(category),
					ot.isEmpty() ? null : Float.parseFloat(ot),
					Do.isEmpty() ? null : Float.parseFloat(Do)
				));
		}

		model.addAttribute("value_search", search);
		model.addAttribute("value_price_ot", ot);
		model.addAttribute("value_price_do", Do);
		model.addAttribute("products", productService.getAllProduct());
		return "/product/product";
	}
}
