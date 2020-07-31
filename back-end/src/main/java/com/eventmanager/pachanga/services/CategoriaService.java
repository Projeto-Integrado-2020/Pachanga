package com.eventmanager.pachanga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.Categoria;
import com.eventmanager.pachanga.repositories.CategoriaRepository;

@Service
@Transactional
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<Categoria> procurarCategorias(){
		return categoriaRepository.findAll();
	}
	
}
