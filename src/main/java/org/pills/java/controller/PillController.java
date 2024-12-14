package org.pills.java.controller;

import java.util.ArrayList;
import java.util.List;

import org.pills.java.model.Pill;
import org.pills.java.service.PillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@SuppressWarnings("unused")

@Controller
@RequestMapping("/pills")
public class PillController {
	
	@Autowired
	PillService pillService;
	
	// INDEX
	@GetMapping()
	public String index(Model model) {

		model.addAttribute("pills", pillService.getAllNotExpired());

		return "pills/index";
	}
	
	// CREATE
	@GetMapping("/create")
	public String create(Model model) {
		
		model.addAttribute(new Pill());
		
		return "pills/create";	
	}
	
	// STORE
	@PostMapping("/create") 
	public String store(@Valid @ModelAttribute("pill") Pill pillForm, BindingResult bindingResult, Model model, RedirectAttributes attributes) {
		
		if (bindingResult.hasErrors()) {
			return "pills/create";
		}
		
		pillService.save(pillForm);
		
		attributes.addFlashAttribute("successMessage", "pill successfully created");
		
		return ("redirect:/pills");	
	}
	
	// EDIT
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
	
	    Pill pillToEdit = pillService.getById(id);
	    
	    model.addAttribute(pillToEdit);
	
	    return "pills/edit";
	}
	
	// UPDATE
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("pill") Pill pillForm, BindingResult bindingResult, Model model, RedirectAttributes attributes) {
		
		if (bindingResult.hasErrors()) {
			return "pills/edit";
		}
		
        pillService.save(pillForm);
		
		attributes.addFlashAttribute("successMessage", "pill successfully edited");
		
		return ("redirect:/pills");
	}

	// DELETE
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable int id, Model model, RedirectAttributes attributes) {
		
        pillService.deleteById(id);
		
		attributes.addFlashAttribute("successMessage", "pill successfully deleted");
		
		return ("redirect:/pills");
	}
}