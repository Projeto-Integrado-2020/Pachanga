package com.eventmanager.pachanga.controllers;

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

import com.eventmanager.pachanga.domains.QuestionarioForms;
import com.eventmanager.pachanga.dtos.QuestionarioFormsTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.QuestionarioFormsFactory;
import com.eventmanager.pachanga.services.QuestionarioFormsService;

@Controller
@RequestMapping("/questionario")
@CrossOrigin
public class QuestionarioFormsController {
	
	@Autowired
	private QuestionarioFormsService questionarioFormsService;

	@Autowired
	private QuestionarioFormsFactory questionarioFormsFactory;
	
	@ResponseBody
	@GetMapping(path = "/lista")
	public ResponseEntity<Object> listaQuestionarioForms(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			return ResponseEntity.ok(questionarioFormsService.listaQuestionariosForms(codFesta, codUsuario).stream()
					.map(qf -> questionarioFormsFactory.getQuestionarioFormsTO(qf)).collect(Collectors.toList()));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@ResponseBody
	@PostMapping(path = "/adicionar")
	public ResponseEntity<Object> adicionarQuestionarioForms(@RequestBody QuestionarioFormsTO questionarioFormsTO,
			@RequestParam(required = true) int codUsuario) {
		try {
			QuestionarioForms questionarioForms = questionarioFormsService.adicionarQuestionario(questionarioFormsTO, codUsuario);
			return ResponseEntity.ok(questionarioFormsFactory.getQuestionarioFormsTO(questionarioForms));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@ResponseBody
	@PutMapping(path = "/atualizar")
	public ResponseEntity<Object> atualizarQuestionarioForms(@RequestBody QuestionarioFormsTO questionarioFormsTO,
			@RequestParam(required = true) int codUsuario) {
		try {
			QuestionarioForms questionarioForms = questionarioFormsService.atualizarQuestionario(questionarioFormsTO, codUsuario);
			return ResponseEntity.ok(questionarioFormsFactory.getQuestionarioFormsTO(questionarioForms));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@ResponseBody
	@DeleteMapping(path = "/remover")
	public ResponseEntity<Object> removerQuestionarioForms(@RequestParam(required = true) int codQuestionario,
			@RequestParam(required = true) int codUsuario) {
		try {
			questionarioFormsService.removerQuestionario(codQuestionario, codUsuario);
			return ResponseEntity.ok().build();
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}
}
