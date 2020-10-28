package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.Problema;

@Repository
public interface ProblemaRepository extends JpaRepository<Problema, Integer> {
	
	@Query(value = "SELECT p FROM Problema p WHERE p.codProblema = :codProblema")
	public Problema findProblemaByCodProblema(int codProblema);

	@Query(value = "SELECT p FROM Problema p")
	public List<Problema> findAllProblemas();
}
