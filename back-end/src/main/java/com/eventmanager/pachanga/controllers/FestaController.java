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

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.dtos.UsuarioTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.FestaFactory;
import com.eventmanager.pachanga.factory.UsuarioFactory;
import com.eventmanager.pachanga.services.FestaService;
import com.eventmanager.pachanga.services.UsuarioService;

@Controller
@RequestMapping("/festa")
@CrossOrigin
public class FestaController {

	@Autowired
	private FestaService festaService;
	
	@Autowired
	private UsuarioService usuarioService;

	@ResponseBody
	@GetMapping(path = "/lista")
	public ResponseEntity<Object> listaFesta(@RequestParam(required = false) String idUser){
		List<Festa> festas = new ArrayList<>();
		try {
			if(idUser == null ) {
				festas =  festaService.procurarFestas();
			}else {
				festas =  festaService.procurarFestasPorUsuario(Integer.parseInt(idUser));
			}
			List<FestaTO> festasTo = festas.stream().map(f -> {
				List<UsuarioTO> usuarios = listUsuarioTO(f);
				FestaTO festaTo = FestaFactory.getFestaTO(f, usuarios, true);
				if(idUser != null) {
					festaTo.setFuncionalidade(festaService.funcionalidadeFesta(festaTo.getCodFesta(), Integer.parseInt(idUser)));
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
			return ResponseEntity.ok(FestaFactory.getFestaTO(festa, null, false));
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
			return ResponseEntity.ok(FestaFactory.getFestaTO(festa, null, false));
		}catch(ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@GetMapping(path = "/festaUnica")
	public ResponseEntity<Object> getFesta(@RequestParam(required = true)int idFesta){
		
		try {			
			Festa festa = festaService.procurarFesta(idFesta);
			List<UsuarioTO> usuarios = listUsuarioTO(festa);
			return ResponseEntity.ok(festa == null ? null : FestaFactory.getFestaTO(festa, usuarios, false));
		}catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	private List<UsuarioTO> listUsuarioTO(Festa festa){
		return usuarioService.getUsuariosFesta(festa.getCodFesta()).stream().map(u -> {
			String funcionalidade = usuarioService.funcionalidadeUsuarioFesta(festa.getCodFesta(), u.getCodUsuario());
			return UsuarioFactory.getUsuarioTO(u, funcionalidade);
			}).collect(Collectors.toList());
	}
}
