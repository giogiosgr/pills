package org.pills.java.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

	/**
	 * Method to retrieve only the not expired pills (ExpDate is after the actual date)
	 * 
	 * @return List<Pill>
	 */
	public List<Pill> getAllNotExpired() {

		List<Pill> list = new ArrayList<>();

		// in the loop, we also assign the value for the pill color attribute
		int count = 0;
		for (Pill pill : repo.findAll()) {
			if (pill.getExpDate().isAfter(LocalDateTime.now())) {
				pill.setColorNumber(count % 2);
				list.add(pill);
				count++;
			}
		}

		return list;
	}
	
	/**
	 * Method to retrieve the list filtered by word, ordered by creation date
	 * depending on the checkExpired value, retrieves or not the expired pills
	 * 
	 * @return List<Pill>
	 */
    public List<Pill> getByNameContainingOrderByCreatedAt(String name, String checkExpired) {
		
		List<Pill> list = new ArrayList<>();

		// in the loop, we also assign the value for the pill color attribute
		int count = 0;
		for (Pill pill : repo.findByNameContainingOrderByCreatedAt(name)) {
			if (checkExpired != null || pill.getExpDate().isAfter(LocalDateTime.now())) {
				pill.setColorNumber(count % 2);
				list.add(pill);
				count++;
			}
		}

		return list;
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