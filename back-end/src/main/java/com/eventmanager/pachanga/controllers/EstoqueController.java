package com.eventmanager.pachanga.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventmanager.pachanga.dtos.EstoqueTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.EstoqueFactory;
import com.eventmanager.pachanga.services.EstoqueService;

@Controller
@RequestMapping("/estoque")
@CrossOrigin
public class EstoqueController {
	
	@Autowired
	private EstoqueService estoqueService;
	
	@Autowired
	private EstoqueFactory estoqueFactory;
	
	@ResponseBody
	@GetMapping(path = "/lista")
	public ResponseEntity<Object> listaEstoques(@RequestParam(required = true) int codFesta, @RequestParam(required = true) int codUsuario){
		try {
			List<EstoqueTO> estoques = estoqueFactory.getListEstoqueTO(estoqueService.estoquesFesta(codFesta, codUsuario));
			return ResponseEntity.ok().body(estoques);
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@PostMapping(path = "/adicionar")
	public ResponseEntity<Object> addEstoque(@RequestBody EstoqueTO estoque, @RequestParam(required = true) int codFesta, @RequestParam(required = true) int codUsuario){
		try {
			EstoqueTO estoqueTo = estoqueFactory.getEstoqueTO(estoqueService.addEstoque(estoqueFactory.getEstoque(estoque, false), codFesta, codUsuario));
			return ResponseEntity.ok().body(estoqueTo);
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@DeleteMapping(path = "/delete")
	public ResponseEntity<Object> deleteEstoque(@RequestParam(required = true) int codEstoque, @RequestParam(required = true) int codFesta, @RequestParam(required = true) int codUsuario){
		try {
			estoqueService.deleteEstoque(codEstoque, codFesta, codUsuario);
			return ResponseEntity.ok().body("DELEESTO");
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@PutMapping(path = "/atualizar")
	public ResponseEntity<Object> deleteEstoque(@RequestBody EstoqueTO estoque, @RequestParam(required = true) int codFesta, @RequestParam(required = true) int codUsuario){
		try {
			
			EstoqueTO estoqueTo = estoqueFactory.getEstoqueTO(estoqueService.updateEstoque(estoque, codFesta, codUsuario));
			return ResponseEntity.ok().body(estoqueTo);
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

}
