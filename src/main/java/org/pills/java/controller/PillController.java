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

	// INDEX
	@GetMapping()
	public String index(Model model) {

		model.addAttribute("pills", pillService.getAllNotExpired());

		return "pills/index";
	}

	// SEARCH
	@GetMapping("/search")
	public String search(@RequestParam String name, @RequestParam(required = false) String checkExpired, 
			@RequestParam String startDate, @RequestParam String endDate, Model model) {
		
		// parsing the dates from String to LocalDateTimes
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		LocalDateTime localStartDate = LocalDateTime.parse(startDate, formatter);
		LocalDateTime localEndDate = LocalDateTime.parse(endDate, formatter);

		model.addAttribute("pills", pillService.getByNameContainingOrderByCreatedAt(name, checkExpired, localStartDate, localEndDate));
		
		// return the parameters to the search inputs
		model.addAttribute("inputName", name);
		if (checkExpired != null) model.addAttribute("inputCheckExpired", checkExpired);
		model.addAttribute("inputStartDate", startDate);
		model.addAttribute("inputEndDate", endDate);

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