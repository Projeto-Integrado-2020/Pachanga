package com.eventmanager.pachanga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eventmanager.pachanga.domains.Problema;

public interface ProblemaRepository extends JpaRepository<Problema, Integer> {
	
	@Query(value = "SELECT p FROM Problema p WHERE p.codProblema = :codProblema")
	public Problema findProblemaByCodProblema(int codProblema);

}
