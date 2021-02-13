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

import com.eventmanager.pachanga.domains.Convidado;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Permissao;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.GrupoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.ConvidadoFactory;
import com.eventmanager.pachanga.factory.GrupoFactory;
import com.eventmanager.pachanga.factory.PermissaoFactory;
import com.eventmanager.pachanga.factory.UsuarioFactory;
import com.eventmanager.pachanga.services.ConvidadoService;
import com.eventmanager.pachanga.services.GrupoService;
import com.eventmanager.pachanga.tipo.TipoPermissao;

@Controller
@RequestMapping("/grupo")
@CrossOrigin
public class GrupoController {

	@Autowired
	private GrupoService grupoService;

	@Autowired 
	private ConvidadoService convidadoService;

	@Autowired
	private ConvidadoFactory convidadoFactory;

	//Permissao____________________________________________________________________________________________________________	
	@ResponseBody
	@GetMapping(path = "/addPermissaoGrupo")
	public ResponseEntity<Object> addPermissaoGrupo(@RequestParam(required = true)int idGrupo, @RequestParam(required = true)int idPermissao, @RequestParam(required = true) int idUsuario){
		try {
			grupoService.addPermissaoGrupo(idPermissao, idGrupo, idUsuario);
			return ResponseEntity.ok("SUCESSO");
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@DeleteMapping(path = "/removePermissaoGrupo")
	public ResponseEntity<Object> removePermissaoGrupo(@RequestParam(required = true)int idGrupo, @RequestParam(required = true)int idPermissao, @RequestParam(required = true) int idUsuario){
		try {
			

			grupoService.deletePermissaoGrupo(idPermissao, idGrupo, idUsuario);
			return ResponseEntity.ok("SUCESSO");
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}


	@ResponseBody
	@PostMapping(path = "/addGrupo")
	public ResponseEntity<Object> addGrupoFesta(@RequestBody GrupoTO grupoTO, @RequestParam (required = true) Integer idUsuario){
		try {

			Grupo grupo = grupoService.addGrupoFesta(grupoTO, idUsuario);
			GrupoTO retorno = GrupoFactory.getGrupoTO(grupo);
			return ResponseEntity.ok(retorno);

		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@DeleteMapping(path = "/deleteGrupo")
	public ResponseEntity<Object> deleteGrupoFesta(@RequestParam Integer codGrupo, @RequestParam Integer idUsuario){
		try {
			Grupo grupo = grupoService.deleteGrupo(codGrupo, idUsuario);
			return ResponseEntity.ok(GrupoFactory.getGrupoTO(grupo));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@PutMapping(path = "/updateGrupo")
	public ResponseEntity<Object> updateGrupoFesta(@RequestBody GrupoTO grupoTO, @RequestParam Integer idUsuario){
		try {
			Grupo grupo = grupoService.atualizar(grupoTO, idUsuario);
			GrupoTO retorno = GrupoFactory.getGrupoTO(grupo);
			return ResponseEntity.ok(retorno);

		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}


	@ResponseBody
	@PutMapping(path="/updateUser")
	public ResponseEntity<Object> editUsuario(@RequestBody List<Integer> gruposId, @RequestParam (required = true) Integer grupoIdAtual, @RequestParam (required = true) Integer idUsuario, @RequestParam (required = true) Integer idUsuarioPermissao){	
		try {

			grupoService.editUsuario(gruposId, grupoIdAtual, idUsuario, idUsuarioPermissao);
			return ResponseEntity.ok().build();

		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@PutMapping(path="/updateUsers")
	public ResponseEntity<Object> editUsuarios(@RequestBody List<Integer> usuariosId, @RequestParam (required = true) Integer idGrupo, @RequestParam (required = true) Integer idUsuarioPermissao){	
		try {

			grupoService.editUsuarios(usuariosId, idGrupo, idUsuarioPermissao);
			return ResponseEntity.ok().build();

		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}


	@ResponseBody
	@DeleteMapping(path="/deleteConvidado")
	public ResponseEntity<Object> deleteConvidado(@RequestParam(required = true) Integer idConvidado, @RequestParam(required = true) Integer idGrupo, @RequestParam(required = true) Integer idUsuarioPermissao){
		try {
			grupoService.deleteConvidado(idConvidado, idGrupo, idUsuarioPermissao);
			return ResponseEntity.ok().build();

		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}

	}

	@ResponseBody
	@DeleteMapping(path="/deleteMembro")
	public ResponseEntity<Object> deleteMembro(@RequestParam(required = true) Integer idMembro, @RequestParam(required = true) Integer idGrupo, @RequestParam(required = true) Integer idUsuarioPermissao){
		try {
			grupoService.deleteMembro(idMembro, idGrupo, idUsuarioPermissao);
			return ResponseEntity.ok().build();

		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}

	}

	//listagem__________________________________________________________________________________________________________________________________

	@ResponseBody
	@GetMapping(path = "/getAllGruposFesta")
	public ResponseEntity<Object> getAllGruposFesta(@RequestParam(required = true)int codFesta, @RequestParam(required = true) int idUsuario){
		try {
			GrupoTO grupoTO;
			List<Usuario> usuarios = null;
			List<Grupo> grupos = grupoService.procurarGruposPorFesta(codFesta, idUsuario);
			List<GrupoTO> retorno = new ArrayList<>();
			List<Convidado> convidados = null;


			for(Grupo grupo : grupos) {
				grupoTO = GrupoFactory.getGrupoTO(grupo);
				convidados = convidadoService.pegarConvidadosGrupo(grupo.getCodGrupo());
				usuarios = grupoService.procurarUsuariosPorGrupo(grupo.getCodGrupo());
				grupoTO.setUsuariosTO(UsuarioFactory.getUsuariosTO(usuarios));
				grupoTO.setConvidadosTO(convidadoFactory.getConvidadosTO(convidados));
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
			Grupo grupo = grupoService.validarGrupo(codGrupo);
			grupoService.validarPermissaoUsuario(grupo.getFesta().getCodFesta(), idUsuario, TipoPermissao.VISGRPER.getCodigo());

			GrupoTO grupoTO = null;
			List<Permissao> permissoes = null;
			List<Usuario> usuarios = null;
			List<Convidado> convidados = null;

			grupo = grupoService.validarGrupo(codGrupo);

			grupoTO = GrupoFactory.getGrupoTO(grupo);

			permissoes = grupoService.procurarPermissoesPorGrupo(codGrupo);
			usuarios = grupoService.procurarUsuariosPorGrupo(codGrupo);
			convidados = convidadoService.pegarConvidadosGrupo(codGrupo);

			grupoTO.setPermissoesTO(PermissaoFactory.getPermissoesTO(permissoes));
			grupoTO.setUsuariosTO(UsuarioFactory.getUsuariosTO(usuarios));
			grupoTO.setConvidadosTO(convidadoFactory.getConvidadosTO(convidados));

			return ResponseEntity.ok(grupoTO);
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}


}