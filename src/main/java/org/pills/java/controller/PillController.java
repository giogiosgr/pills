package org.pills.java.controller;

import java.util.ArrayList;
import java.util.List;

import org.pills.java.service.PillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SuppressWarnings("unused")

@Controller
@RequestMapping("/tickets")
public class PillController {
	
	@Autowired
	PillService pillService;
	
	// INDEX
	@GetMapping()
	public String index(Model model) {

		model.addAttribute("pills", pillService.getAll());

		return "pills/index";
	}

}
