package com.eventmanager.pachanga.controllers;

import java.util.ArrayList;
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

import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Permissao;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.GrupoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.GrupoFactory;
import com.eventmanager.pachanga.factory.PermissaoFactory;
import com.eventmanager.pachanga.factory.UsuarioFactory;
import com.eventmanager.pachanga.services.GrupoService;

@Controller
@RequestMapping("/grupo")
@CrossOrigin
public class GrupoController {
	
	@Autowired
	private GrupoService grupoService;
	
	@ResponseBody
	@GetMapping(path = "/addUserFesta")
	public ResponseEntity<Object> addUsuarioFesta(@RequestParam(required = true)int codGrupo, @RequestParam(required = true)int idUsuario, @RequestParam(required = true)int idUsuarioPermissao){
		try {
			Grupo grupo = grupoService.getByIdGrupo(codGrupo);
			grupoService.validarPermissaoUsuario(grupo.getFesta().getCodFesta(), idUsuarioPermissao);
			
			Usuario retorno = grupoService.addUsuarioFesta(codGrupo, idUsuario);
			return ResponseEntity.ok(UsuarioFactory.getUsuarioTO(retorno));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@DeleteMapping(path = "/removeUserFesta")
	public ResponseEntity<Object> removeUsuarioFesta(@RequestParam(required = true)int codGrupo, @RequestParam(required = true)int idUsuario, @RequestParam(required = true)int idUsuarioPermissao){
		try {
			Grupo grupo = grupoService.getByIdGrupo(codGrupo);
			grupoService.validarPermissaoUsuario(grupo.getFesta().getCodFesta(), idUsuarioPermissao);
			
			Usuario retorno = grupoService.removeUsuarioFesta(codGrupo, idUsuario);
			return ResponseEntity.ok(UsuarioFactory.getUsuarioTO(retorno));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	
//Permissao____________________________________________________________________________________________________________	
	@ResponseBody
	@GetMapping(path = "/addPermissaoGrupo")
	public ResponseEntity<Object> addPermissaoGrupo(@RequestParam(required = true)int idGrupo, @RequestParam(required = true)int idPermissao, @RequestParam(required = true) int idUsuario){
		try {
			Grupo grupo = grupoService.getByIdGrupo(idGrupo);
			grupoService.validarPermissaoUsuario(grupo.getFesta().getCodFesta(), idUsuario);
			
			grupoService.addPermissaoGrupo(idPermissao, idGrupo);
			return ResponseEntity.ok("SUCESSO");
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@DeleteMapping(path = "/removePermissaoGrupo")
	public ResponseEntity<Object> removePermissaoGrupo(@RequestParam(required = true)int idGrupo, @RequestParam(required = true)int idPermissao, @RequestParam(required = true) int idUsuario){
		try {
			Grupo grupo = grupoService.getByIdGrupo(idGrupo);
			grupoService.validarPermissaoUsuario(grupo.getFesta().getCodFesta(), idUsuario);
			
			grupoService.deletePermissaoGrupo(idPermissao, idGrupo);
			return ResponseEntity.ok("SUCESSO");
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	/*
	@ResponseBody
	@GetMapping(path = "/getPermissaoGrupo")
	public ResponseEntity<Object> getPermissaoGrupo(@RequestParam(required = true)int codFesta, @RequestBody List<String> emails, @RequestParam(required = true)int idUsuario, @RequestParam(required = true)int idGrupo){
		try {
			StringBuilder mensagem = grupoService.deleteUsuariosFesta(emails, codFesta, idUsuario, idGrupo);
			return ResponseEntity.ok(mensagem);
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	*/
//CRUD__________________________________________________________________________________________________________________________________________________________________________________________________
	
	@ResponseBody
	@PostMapping(path = "/addGrupoFesta")
	public ResponseEntity<Object> addGrupoFesta(@RequestBody GrupoTO grupoTO, @RequestParam(required = true) int idUsuario){
		try {
			grupoService.validarPermissaoUsuario(grupoTO.getCodFesta(), idUsuario);
			
			Grupo grupo = null;
			grupo = grupoService.addGrupoFesta(grupoTO, idUsuario);
			
			if(grupo != null) {
				GrupoTO retorno = GrupoFactory.getGrupoTO(grupo, grupo.getFesta());
				return ResponseEntity.ok(retorno);
			}
			return ResponseEntity.status(400).body("Grupo duplicado ou inválido");
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@GetMapping(path = "/getGrupoFestaSimple")  //retorna apenas os dados do grupo, talvez eu delete dps
	public ResponseEntity<Object> getGrupoFestaSimple(@RequestParam(required = true)int codGrupo, @RequestParam(required = true) int idUsuario){
		try {
			Grupo grupo = grupoService.getByIdGrupo(codGrupo);
			grupoService.validarPermissaoUsuario(grupo.getFesta().getCodFesta(), idUsuario);
			
			return ResponseEntity.ok(GrupoFactory.getGrupoTO(grupo, grupo.getFesta()));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@DeleteMapping(path = "/deleteGrupoFesta")
	public ResponseEntity<Object> deleteGrupoFesta(@RequestParam(required = true)int codGrupo, @RequestParam(required = true) int idUsuario){
		try {
			Grupo grupo = grupoService.getByIdGrupo(codGrupo);
			grupoService.validarPermissaoUsuario(grupo.getFesta().getCodFesta(), idUsuario);
			
			grupo = grupoService.deleteGrupo(codGrupo, idUsuario);
			return ResponseEntity.ok(GrupoFactory.getGrupoTO(grupo, grupo.getFesta()));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@PutMapping(path = "/updateGrupoFesta")
	public ResponseEntity<Object> updateGrupoFesta(@RequestBody GrupoTO grupoTO, @RequestParam(required = true) int idUsuario){
		try {
			grupoService.validarPermissaoUsuario(grupoTO.getCodFesta(), idUsuario);
			
			Grupo grupo = grupoService.atualizar(grupoTO, idUsuario);
			if(grupo != null) {
				GrupoTO retorno = GrupoFactory.getGrupoTO(grupo, grupo.getFesta());
				return ResponseEntity.ok(retorno);
			}
			return ResponseEntity.status(400).body("Grupo duplicado ou inválido");
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
//listagem__________________________________________________________________________________________________________________________________
	
	@ResponseBody
	@GetMapping(path = "/getAllGruposFesta")
	public ResponseEntity<Object> getAllGruposFesta(@RequestParam(required = true)int codFesta, @RequestParam(required = true) int idUsuario){
		try {
			grupoService.validarPermissaoUsuario(codFesta, idUsuario);
			GrupoTO grupoTO;
			List<Usuario> usuarios = null;
			List<Grupo> grupos = grupoService.procurarGruposPorFesta(codFesta);
			List<GrupoTO> retorno = new ArrayList<>();
			
			for(Grupo grupo : grupos) {
				grupoTO = GrupoFactory.getGrupoTO(grupo, grupo.getFesta());
				usuarios = grupoService.procurarUsuariosPorGrupo(grupo.getCodGrupo());
				grupoTO.setUsuariosTO(UsuarioFactory.getUsuariosTO(usuarios));
				retorno.add(grupoTO);
			}
			return ResponseEntity.ok(retorno);
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody        
	@GetMapping(path = "/getGrupoFesta")
	public ResponseEntity<Object> getGrupoFesta(@RequestParam(required = true)int codGrupo,  @RequestParam(required = true) int idUsuario){
		try {
			Grupo grupo = grupoService.getByIdGrupo(codGrupo);
			grupoService.validarPermissaoUsuario(grupo.getFesta().getCodFesta(), idUsuario);
			
			GrupoTO grupoTO = null;
			List<Permissao> permissoes = null;
			List<Usuario> usuarios = null; 
			
			grupo = grupoService.getByIdGrupo(codGrupo);
			
			if(grupo != null) {
				grupoTO = GrupoFactory.getGrupoTO(grupo, grupo.getFesta());
				
				permissoes = grupoService.procurarPermissoesPorGrupo(codGrupo);
				usuarios = grupoService.procurarUsuariosPorGrupo(codGrupo);
				
				grupoTO.setPermissoesTO(PermissaoFactory.getPermissoesTO(permissoes));
				grupoTO.setUsuariosTO(UsuarioFactory.getUsuariosTO(usuarios));
				
				return ResponseEntity.ok(grupoTO);
			}
			return ResponseEntity.ok("GRUPNFOU");
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	

}
