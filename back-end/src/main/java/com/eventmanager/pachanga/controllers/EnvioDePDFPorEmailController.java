package com.eventmanager.pachanga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eventmanager.pachanga.dtos.PDFPorEmailTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.services.EnvioDePDFPorEmailService;

@RestController
@RequestMapping("/envioDePDFPorEmail")
@CrossOrigin
public class EnvioDePDFPorEmailController {
	
	@Autowired
	private EnvioDePDFPorEmailService envioDePDFPorEmailService;
	
	@ResponseBody
	@PostMapping(path = "/enviarRelatorio")
	public ResponseEntity<Object> enviarRelatorio(@RequestBody PDFPorEmailTO pdfPorEmailTO,
			@RequestParam(required = true) int codUsuario, @RequestParam(required = true) int codFesta) {
		try {
			Boolean result = envioDePDFPorEmailService.enviarRelatorio(pdfPorEmailTO, codUsuario, codFesta);
			return ResponseEntity.ok(result);
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
