package com.eventmanager.pachanga.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.eventmanager.pachanga.domains.AreaSegurancaProblema;
import com.eventmanager.pachanga.dtos.AreaSegurancaProblemaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.AreaSegurancaProblemaFactory;
import com.eventmanager.pachanga.services.AreaSegurancaProblemaService;
import com.eventmanager.pachanga.utils.TransformadorJsonObjeto;

@Controller
@RequestMapping("/areaSegurancaProblema")
@CrossOrigin
public class AreaSegurancaProblemaController {

	@Autowired
	private AreaSegurancaProblemaService areaSegurancaProblemaService;

	@Autowired
	private AreaSegurancaProblemaFactory areaSegurancaProblemaFactory;

	@ResponseBody
	@PostMapping(path = "/adicionar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> addProblemaSeguranca(@RequestParam String problemaSegurancaTO,
			@RequestParam(required = false) MultipartFile imagem, @RequestParam(required = true) int idUsuario)
			throws IOException {
		try {
			AreaSegurancaProblema problemaSeguranca = areaSegurancaProblemaService.addProblemaSeguranca(
					(AreaSegurancaProblemaTO) TransformadorJsonObjeto.criarFestaTOByString(problemaSegurancaTO,
							new AreaSegurancaProblemaTO()),
					idUsuario, imagem);
			return ResponseEntity.ok(areaSegurancaProblemaFactory.getAreaSegurancaProblemaTO(problemaSeguranca, false));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@PutMapping(path = "/atualizar")
	public ResponseEntity<Object> updateProblemaSeguranca(@RequestBody AreaSegurancaProblemaTO problemaSegurancaTO,
			@RequestParam(required = true) int idUsuario) {
		try {
			AreaSegurancaProblema problemaSeguranca = areaSegurancaProblemaService
					.updateProblemaSeguranca(problemaSegurancaTO, idUsuario);
			return ResponseEntity.ok(areaSegurancaProblemaFactory.getAreaSegurancaProblemaTO(problemaSeguranca, false));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@PutMapping(path = "/alterarStatus")
	public ResponseEntity<Object> alterarStatusProblema(@RequestBody AreaSegurancaProblemaTO problemaSegurancaTO,
			@RequestParam(required = true) int codUsuario, @RequestParam(required = true) Boolean finaliza) {
		try {
			areaSegurancaProblemaService.alterarStatusProblema(problemaSegurancaTO, codUsuario, finaliza);
			return ResponseEntity.ok().build();
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@GetMapping(path = "/findProblemaSeguranca")
	public ResponseEntity<Object> findProblemaSeguranca(@RequestParam(required = true) int codAreaSegurancaProblema,
			@RequestParam(required = true) int codFesta, @RequestParam(required = true) int idUsuario,
			@RequestParam(required = true) Boolean retornoImagem) {
		try {
			AreaSegurancaProblema problemaSeguranca = areaSegurancaProblemaService
					.findProblemaSeguranca(codAreaSegurancaProblema, codFesta, idUsuario);
			return ResponseEntity.ok(areaSegurancaProblemaFactory.getAreaSegurancaProblemaTO(problemaSeguranca,
					retornoImagem.booleanValue()));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@GetMapping(path = "/findAllProblemasSegurancaArea")
	public ResponseEntity<Object> findAllProblemasSegurancaArea(@RequestParam(required = true) int codAreaSeguranca,
			@RequestParam(required = true) int codFesta, @RequestParam(required = true) int idUsuario) {
		try {
			List<AreaSegurancaProblema> problemasSeguranca = areaSegurancaProblemaService
					.findAllProblemasSegurancaArea(codAreaSeguranca, codFesta, idUsuario);
			return ResponseEntity.ok(areaSegurancaProblemaFactory.getProblemasSegurancaTO(problemasSeguranca));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@GetMapping(path = "/findAllProblemasSegurancaFesta")
	public ResponseEntity<Object> findAllProblemasSegurancaFesta(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int idUsuario) {
		try {
			List<AreaSegurancaProblema> problemasSeguranca = areaSegurancaProblemaService
					.findAllProblemasSegurancaFesta(codFesta, idUsuario);
			return ResponseEntity.ok(areaSegurancaProblemaFactory.getProblemasSegurancaTO(problemasSeguranca));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
	
	@ResponseBody
	@GetMapping(path = "/listaHistorico")
	public ResponseEntity<Object> listaHistorioAreaSegurancaFesta(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			return ResponseEntity.ok(areaSegurancaProblemaService.getHistoricosAreaFesta(codFesta));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
