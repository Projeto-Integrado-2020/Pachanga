package com.eventmanager.pachanga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.Problema;
import com.eventmanager.pachanga.repositories.ProblemaRepository;

@Service
@Transactional
public class ProblemaSevice {
	
	@Autowired
	ProblemaRepository problemaRepository;
	
	public List<Problema> listarProblemas() {
		return problemaRepository.findAllProblemas();
	}
}
