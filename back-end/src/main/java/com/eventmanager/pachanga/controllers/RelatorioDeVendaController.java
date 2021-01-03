package com.eventmanager.pachanga.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.RelatorioDeVendaFactory;
import com.eventmanager.pachanga.services.RelatorioDeVendaService;
import com.eventmanager.pachanga.tipo.TipoStatusCompra;
import com.eventmanager.pachanga.tipo.TipoStatusIngresso;

@RestController
@RequestMapping("/relatorioVenda")
@CrossOrigin
public class RelatorioDeVendaController {
	
	@Autowired
	private RelatorioDeVendaService relatorioDeVendaService;
	
	@Autowired
	private RelatorioDeVendaFactory relatorioDeVendaFactory;
	
	@ResponseBody
	@GetMapping(path = "/ingressosFesta")
	public ResponseEntity<Object> relatorioIngressosFesta(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			Map<String, Integer> retorno = relatorioDeVendaService.relatorioDeIngressos(codFesta, codUsuario);
			return ResponseEntity.ok(relatorioDeVendaFactory.getRelatorioDeVenda(retorno));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@ResponseBody
	@GetMapping(path = "/ingressosFestaPagos")
	public ResponseEntity<Object> relatorioIngressosFestaPagos(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			Map<String, Integer> retorno = relatorioDeVendaService.relatorioDeIngressos(codFesta, codUsuario, TipoStatusCompra.PAGO.getDescricao());
			return ResponseEntity.ok(relatorioDeVendaFactory.getRelatorioDeVenda(retorno));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@ResponseBody
	@GetMapping(path = "/ingressosFestaChecked")
	public ResponseEntity<Object> relatorioIngressosFestaChecked(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			Map<String, Integer> retorno = relatorioDeVendaService.relatorioDeIngressosCheckIn(codFesta, codUsuario, TipoStatusIngresso.CHECKED.getDescricao());
			return ResponseEntity.ok(relatorioDeVendaFactory.getRelatorioDeVenda(retorno));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@ResponseBody
	@GetMapping(path = "/ingressosFestaUnchecked")
	public ResponseEntity<Object> relatorioIngressosFestaUnchecked(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			Map<String, Integer> retorno = relatorioDeVendaService.relatorioDeIngressosCheckIn(codFesta, codUsuario, TipoStatusIngresso.UNCHECKED.getDescricao());
			return ResponseEntity.ok(relatorioDeVendaFactory.getRelatorioDeVenda(retorno));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@ResponseBody
	@GetMapping(path = "/ingressosFestaComprados")
	public ResponseEntity<Object> relatorioIngressosFestaComprados(@RequestParam(required = true) int codFesta,
			@RequestParam(required = true) int codUsuario) {
		try {
			Map<String, Integer> retorno = relatorioDeVendaService.relatorioDeIngressos(codFesta, codUsuario,  TipoStatusCompra.COMPRADO.getDescricao());
			return ResponseEntity.ok(relatorioDeVendaFactory.getRelatorioDeVenda(retorno));
		} catch (ValidacaoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
}
