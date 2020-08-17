package com.eventmanager.pachanga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eventmanager.pachanga.domains.ItemEstoque;
import com.eventmanager.pachanga.domains.Produto;
import com.eventmanager.pachanga.dtos.ItemEstoqueTO;
import com.eventmanager.pachanga.dtos.ProdutoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.ItemEstoqueFactory;
import com.eventmanager.pachanga.factory.ProdutoFactory;
import com.eventmanager.pachanga.services.ProdutoService;

@Controller
@RequestMapping("/produto")
@CrossOrigin
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;
	
//add_____________________________________________________________________________________________________	
	@ResponseBody
	@PostMapping(path="/addProduto")
	public ResponseEntity<Object> addProduto(@RequestBody ProdutoTO produtoTO, @RequestParam (required = true) Integer codFesta, @RequestParam (required = true) Integer idUsuarioPermissao){	
		try {
			produtoService.validarUsuarioPorFesta(idUsuarioPermissao, codFesta);
			Produto produto = produtoService.addProduto(produtoTO, codFesta);
			return ResponseEntity.ok(ProdutoFactory.getProdutoTO(produto));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@PostMapping(path="/addProdutoEstoque")
	public ResponseEntity<Object> addProdutoEstoque(@RequestBody ItemEstoqueTO itemEstoqueTO, @RequestParam (required = true) Integer codProduto, @RequestParam (required = true) Integer codEstoque, @RequestParam (required = true) Integer idUsuarioPermissao){	
		try {
			produtoService.validarUsuarioPorEstoque(idUsuarioPermissao, codEstoque);
			ItemEstoque itemEstoque = produtoService.addProdutoEstoque(itemEstoqueTO, codProduto, codEstoque);
			return ResponseEntity.ok(ItemEstoqueFactory.getItemEstoqueTO(itemEstoque));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	
//remover_____________________________________________________________________________________________________
	@ResponseBody
	@DeleteMapping(path="/removerProduto")
	public ResponseEntity<Object> removerProduto(@RequestParam (required = true) Integer codProduto, @RequestParam (required = true) Integer codFesta, @RequestParam (required = true) Integer idUsuarioPermissao){	
		try {
			produtoService.validarUsuarioPorFesta(idUsuarioPermissao, codFesta);
			Produto produto = produtoService.removerProduto(codProduto);
			return ResponseEntity.ok(ProdutoFactory.getProdutoTO(produto));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@DeleteMapping(path="/removerProdutoEstoque")
	public ResponseEntity<Object> removerProdutoEstoque(@RequestParam (required = true) Integer codProduto, @RequestParam (required = true) Integer codEstoque, @RequestParam (required = true) Integer idUsuarioPermissao){	
		try {
			produtoService.validarUsuarioPorEstoque(idUsuarioPermissao, codEstoque);
			ItemEstoque itemEstoque = produtoService.removerProdutoEstoque(codProduto, codEstoque);
			return ResponseEntity.ok(ItemEstoqueFactory.getItemEstoqueTO(itemEstoque));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	
//editar_____________________________________________________________________________________________________	
	@ResponseBody
	@PutMapping(path="/editarProduto")
	public ResponseEntity<Object> editarProduto(@RequestBody ProdutoTO produtoTO, @RequestParam (required = true) Integer idUsuarioPermissao){	
		try {
			produtoService.validarUsuarioPorFesta(idUsuarioPermissao, produtoTO.getCodFesta());
			Produto produto = produtoService.editarProduto(produtoTO);
			return ResponseEntity.ok(ProdutoFactory.getProdutoTO(produto));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@PutMapping(path="/editarProdutoEstoque")
	public ResponseEntity<Object> editarProdutoEstoque(@RequestBody ItemEstoqueTO itemEstoqueTO, @RequestParam (required = true) Integer idUsuarioPermissao){	
		try {
			produtoService.validarUsuarioPorEstoque(idUsuarioPermissao, itemEstoqueTO.getCodEstoque());
			ItemEstoque itemEstoque = produtoService.editarProdutoEstoque(itemEstoqueTO);
			return ResponseEntity.ok(ItemEstoqueFactory.getItemEstoqueTO(itemEstoque));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
}
