package org.pills.java.service;

import java.util.List;

import org.pills.java.model.Pill;
import org.pills.java.repo.PillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PillService {

	@Autowired
	private PillRepository repo;

	public List<Pill> getAll() {

		return repo.findAll();

	}

}
