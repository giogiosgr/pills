package org.pills.java.service;

import java.util.List;

import org.pills.java.model.Pill;
import org.pills.java.repo.PillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class PillService {

	@Autowired
	private PillRepository repo;

	public List<Pill> getAll() {

		return repo.findAll();
	}
	
	public Pill getById(Integer id) {

		return repo.findById(id).get();
	}
	
	public void save(@Valid Pill pill) {

		repo.save(pill);
	}
	
	public void deleteById(int id) {

		repo.deleteById(id);
	}

}