package org.pills.java.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	
	// search fields default attributes
	
	private String searchName = "";
	
	private String searchCheckExpired = null;
	
	private String searchStartDate = "2024-01-01T00:00";
	
	private String searchEndDate = "2026-01-01T00:00";
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

	// INDEX
	@GetMapping()
	public String index(Model model) {

		// parsing the dates from String to LocalDateTimes
		LocalDateTime localStartDate = LocalDateTime.parse(searchStartDate, formatter);
		LocalDateTime localEndDate = LocalDateTime.parse(searchEndDate, formatter);
				
		model.addAttribute("pills", pillService.getBySearchFilters(searchName, searchCheckExpired, localStartDate, localEndDate));
				
		// return the current parameters to the search inputs
		model.addAttribute("inputName", searchName);
		if (searchCheckExpired != null) model.addAttribute("inputCheckExpired", searchCheckExpired);
		model.addAttribute("inputStartDate", searchStartDate);
		model.addAttribute("inputEndDate", searchEndDate);

		return "pills/index";
	}

	// SEARCH
	@GetMapping("/search")
	public String search(@RequestParam String name, @RequestParam(required = false) String checkExpired, 
			@RequestParam String startDate, @RequestParam String endDate, Model model) {
		
		// assign the current parameters values to the related search attribues	
		searchName = name;
		searchCheckExpired = checkExpired;
		searchStartDate = startDate;
		searchEndDate = endDate;
		
		// parsing the dates from String to LocalDateTimes
		LocalDateTime localStartDate = LocalDateTime.parse(searchStartDate, formatter);
		LocalDateTime localEndDate = LocalDateTime.parse(searchEndDate, formatter);
		
		model.addAttribute("pills", pillService.getBySearchFilters(searchName, searchCheckExpired, localStartDate, localEndDate));
		
		// return the current parameters to the search inputs
		model.addAttribute("inputName", searchName);
		if (searchCheckExpired != null) model.addAttribute("inputCheckExpired", searchCheckExpired);
		model.addAttribute("inputStartDate", searchStartDate);
		model.addAttribute("inputEndDate", searchEndDate);

		return "pills/index";
	}

	// CREATE
	@GetMapping("/create")
	public String create(Model model) {

		Pill newPill = new Pill();

		// set the starting date for the form, as actual date plus one day
		newPill.setExpDate(LocalDateTime.now().plusDays(1));

		model.addAttribute(newPill);

		return "pills/create";
	}

	// STORE
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("pill") Pill pillForm, BindingResult bindingResult, Model model,
			RedirectAttributes attributes) {

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
	public String update(@Valid @ModelAttribute("pill") Pill pillForm, BindingResult bindingResult, Model model,
			RedirectAttributes attributes) {

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