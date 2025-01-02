package org.pills.java.repo;

import java.util.List;

import org.pills.java.model.Pill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PillRepository extends JpaRepository<Pill, Integer>{
	
	List<Pill> findByNameContainingOrderByCreatedAt(String name);

}
