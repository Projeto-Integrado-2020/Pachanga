package com.eventmanager.pachanga.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import com.eventmanager.pachanga.domains.Categoria;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.dtos.CategoriaTO;
import com.eventmanager.pachanga.dtos.ConvidadoTO;
import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.dtos.UsuarioTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.CategoriaFactory;
import com.eventmanager.pachanga.factory.ConvidadoFactory;
import com.eventmanager.pachanga.factory.FestaFactory;
import com.eventmanager.pachanga.factory.UsuarioFactory;
import com.eventmanager.pachanga.services.CategoriaService;
import com.eventmanager.pachanga.services.ConvidadoService;
import com.eventmanager.pachanga.services.FestaService;
import com.eventmanager.pachanga.services.UsuarioService;
import com.eventmanager.pachanga.tipo.TipoCategoria;

@Controller
@RequestMapping("/festa")
@CrossOrigin
public class FestaController {

	@Autowired
	private FestaService festaService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private ConvidadoService convidadoService;
	
	@Autowired
	private ConvidadoFactory convidadoFactory;
	
	@Autowired
	private FestaFactory festaFactory;
	

	@ResponseBody
	@GetMapping(path = "/lista")
	public ResponseEntity<Object> listaFesta(@RequestParam(required = true) int idUser){
		List<Festa> festas = new ArrayList<>();
		try {
			if(idUser == 0 ) {
				festas =  festaService.procurarFestas();
			}else {
				festas =  festaService.procurarFestasPorUsuario(idUser);
			}
			List<FestaTO> festasTo = festas.stream().map(f -> {
				List<UsuarioTO> usuarios = listUsuarioTO(f);
				List<ConvidadoTO> convidados = convidadoFactory.getConvidadosTO(convidadoService.pegarConvidadosFesta(f.getCodFesta()));
				CategoriaTO categoriaPrimaria = categoriaFesta(f.getCodFesta(), TipoCategoria.PRIMARIO.getDescricao());
				CategoriaTO categoriaSecundario = categoriaFesta(f.getCodFesta(), TipoCategoria.SECUNDARIO.getDescricao());
				FestaTO festaTo = festaFactory.getFestaTO(f, usuarios, true, categoriaPrimaria, categoriaSecundario, convidados, idUser);
				if(idUser != 0) {
					festaTo.setFuncionalidade(festaService.funcionalidadeFesta(festaTo.getCodFesta(), idUser));
				}
				return festaTo;
			}).collect(Collectors.toList());
			return ResponseEntity.ok(festasTo);
		}catch(ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@PostMapping(path = "/adicionar")
	public ResponseEntity<Object> addFesta(@RequestBody FestaTO festaTo, @RequestParam(required = true) int idUser){
		try {
			Festa festa = festaService.addFesta(festaTo, idUser);
			CategoriaTO categoriaPrimaria = categoriaFesta(festa.getCodFesta(), TipoCategoria.PRIMARIO.getDescricao());
			CategoriaTO categoriaSecundario = categoriaFesta(festa.getCodFesta(), TipoCategoria.SECUNDARIO.getDescricao());
			return ResponseEntity.ok(festaFactory.getFestaTO(festa, null, false, categoriaPrimaria, categoriaSecundario, null));
		}catch(ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@DeleteMapping(path = "/delete")
	public ResponseEntity<Object> deleteFesta(@RequestParam(required = true) int idFesta, @RequestParam(required = true) int idUser){
		try {
			festaService.deleteFesta(idFesta, idUser);
			return ResponseEntity.ok("FESTDELE");//ver se precisa colocar alguma coisa aqui ou pode mandar somente um ok
		}catch(ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@PutMapping(path = "/atualizar")
	public ResponseEntity<Object> atualizaFesta(@RequestBody FestaTO festaTo, @RequestParam(required = true) int idUser){
		try {
			Festa festa = festaService.updateFesta(festaTo, idUser);
			CategoriaTO categoriaPrimaria = categoriaFesta(festa.getCodFesta(), TipoCategoria.PRIMARIO.getDescricao());
			CategoriaTO categoriaSecundario = categoriaFesta(festa.getCodFesta(), TipoCategoria.SECUNDARIO.getDescricao());
			return ResponseEntity.ok(festaFactory.getFestaTO(festa, null, false, categoriaPrimaria, categoriaSecundario, null));
		}catch(ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@GetMapping(path = "/festaUnica")
	public ResponseEntity<Object> getFesta(@RequestParam(required = true)int idFesta, @RequestParam(required = true)int idUsuario){
		try {			
			FestaTO festaTo = null;
			Festa festa = festaService.procurarFesta(idFesta, idUsuario);
			if(festa != null) {
				List<UsuarioTO> usuarios = listUsuarioTO(festa);
				CategoriaTO categoriaPrimaria = categoriaFesta(festa.getCodFesta(), TipoCategoria.PRIMARIO.getDescricao());
				CategoriaTO categoriaSecundario = categoriaFesta(festa.getCodFesta(), TipoCategoria.SECUNDARIO.getDescricao());
				List<ConvidadoTO> convidados = convidadoFactory.getConvidadosTO(convidadoService.pegarConvidadosFesta(festa.getCodFesta()));
				return ResponseEntity.ok(festaFactory.getFestaTO(festa, usuarios, false, categoriaPrimaria, categoriaSecundario, convidados, idUsuario));
			}
			return ResponseEntity.status(200).body(festaTo);
		}catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@GetMapping(path = "/festaUnicaConvidado")
	public ResponseEntity<Object> getFestaConvidado(@RequestParam(required = true)int codGrupo, @RequestParam(required = true)int codConvidado){
		try {			
			Festa festa = festaService.procurarFestaConvidado(codConvidado, codGrupo);
			if(festa != null) {
				return ResponseEntity.ok(festaFactory.getFestaTO(festa));
			}
			return ResponseEntity.ok().build();
		}catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@PutMapping(path = "/festaMudancaStatus")
	public ResponseEntity<Object> alterarStatusFesta(@RequestParam(required = true)int idFesta, @RequestParam(required = true)String statusFesta, @RequestParam(required = true)int idUsuario) {
		try {
			Festa festa = festaService.mudarStatusFesta(idFesta, statusFesta, idUsuario);
			CategoriaTO categoriaPrimaria = categoriaFesta(festa.getCodFesta(), TipoCategoria.PRIMARIO.getDescricao());
			CategoriaTO categoriaSecundario = categoriaFesta(festa.getCodFesta(), TipoCategoria.SECUNDARIO.getDescricao());
			return ResponseEntity.ok(festaFactory.getFestaTO(festa, null, false, categoriaPrimaria, categoriaSecundario, null));//festa mudada com sucesso
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	private List<UsuarioTO> listUsuarioTO(Festa festa){
		return usuarioService.getUsuariosFesta(festa.getCodFesta()).stream().map(u -> {
			String funcionalidade = usuarioService.funcionalidadeUsuarioFesta(festa.getCodFesta(), u.getCodUsuario());
			return UsuarioFactory.getUsuarioTO(u, funcionalidade);
		}).collect(Collectors.toList());
	}

	private CategoriaTO categoriaFesta(int codFesta, String tipoCategoria) {
		Categoria categoria = categoriaService.procurarCategoriaFesta(codFesta, tipoCategoria);
		if(categoria != null) {
			return CategoriaFactory.getCategoriaTO(categoria);
		}else {
			return null;
		}
	}
}
