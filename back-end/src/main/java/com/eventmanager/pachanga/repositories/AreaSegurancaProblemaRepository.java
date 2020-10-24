package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eventmanager.pachanga.domains.AreaSegurancaProblema;

public interface AreaSegurancaProblemaRepository extends JpaRepository<AreaSegurancaProblema, Integer> {

	@Query(value = "SELECT a FROM AreaSegurancaProblema asp JOIN asp.area a JOIN asp.problema p WHERE a.codArea = :codArea")
	List<AreaSegurancaProblema> findProblemasArea(int codArea);

}
