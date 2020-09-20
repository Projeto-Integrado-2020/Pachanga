package com.eventmanager.pachanga.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eventmanager.pachanga.domains.Categoria;
import com.eventmanager.pachanga.dtos.CategoriaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.CategoriaFactory;
import com.eventmanager.pachanga.services.CategoriaService;

@RestController
@RequestMapping("/categoria")
@CrossOrigin
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@ResponseBody
	@GetMapping(path = "/lista")
	public ResponseEntity<Object> listaCategorias(){
		try {
			List<Categoria> categorias = categoriaService.procurarCategorias();
			List<CategoriaTO> categoriasTo = categorias.stream().map(c -> CategoriaFactory.getCategoriaTO(c)).collect(Collectors.toList());
			return ResponseEntity.ok(categoriasTo);
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

}
