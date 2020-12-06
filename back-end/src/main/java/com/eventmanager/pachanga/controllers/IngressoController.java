package com.eventmanager.pachanga.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.eventmanager.pachanga.domains.Ingresso;
import com.eventmanager.pachanga.dtos.CategoriaTO;
import com.eventmanager.pachanga.dtos.IngressoTO;
import com.eventmanager.pachanga.dtos.InsercaoIngresso;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.FestaFactory;
import com.eventmanager.pachanga.factory.IngressoFactory;
import com.eventmanager.pachanga.services.IngressoService;
import com.eventmanager.pachanga.tipo.TipoCategoria;
import com.fasterxml.jackson.databind.JsonNode;

@Controller
@RequestMapping("/ingresso")
@CrossOrigin
public class IngressoController {

	@Autowired
	private IngressoService ingressoService;

	@Autowired
	private IngressoFactory ingressoFactory;

	@Autowired
	private FestaController festaController;

	@Autowired
	private FestaFactory festaFactory;

	@ResponseBody
	@GetMapping(path = "/listaUser")
	public ResponseEntity<Object> getIngressosUser(@RequestParam(required = true) int idUser) {
		try {
			List<IngressoTO> ingressosTO = new ArrayList<>();
			for (Ingresso ingresso : ingressoService.getIngressosUser(idUser)) {
				IngressoTO ingressoTO = ingressoFactory.getIngressoTO(ingresso, null);

				CategoriaTO categoriaPrimaria = festaController.categoriaFesta(ingresso.getFesta().getCodFesta(),
						TipoCategoria.PRIMARIO.getDescricao());
				CategoriaTO categoriaSecundario = festaController.categoriaFesta(ingresso.getFesta().getCodFesta(),
						TipoCategoria.SECUNDARIO.getDescricao());

				ingressoTO.setFesta(festaFactory.getFestaTO(ingresso.getFesta(), null, false, categoriaPrimaria,
						categoriaSecundario, null));

				ingressosTO.add(ingressoTO);
			}
			return ResponseEntity.ok(ingressosTO);
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@GetMapping(path = "/listaFesta")
	public ResponseEntity<Object> getIngressosFesta(@RequestParam(required = true) int idFesta) {
		try {
			List<Ingresso> ingressos = ingressoService.getIngressosFesta(idFesta);
			List<IngressoTO> ingressosTO = new ArrayList<>();
			for (Ingresso ingresso : ingressos) {
				IngressoTO ingressoTO = ingressoFactory.getIngressoTO(ingresso, null);
				ingressosTO.add(ingressoTO);
			}
			return ResponseEntity.ok(ingressosTO);
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@PostMapping(path = "/addIngresso")
	public ResponseEntity<Object> addIngresso(@RequestBody IngressoTO ingressoTO) {
		try {
			return ResponseEntity.ok(ingressoService.addIngresso(ingressoTO, null));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@PostMapping(path = "/addIngressoLista")
	public ResponseEntity<Object> addIngressoLista(@RequestBody InsercaoIngresso insercaoIngresso) {
		try {
			return ResponseEntity.ok(ingressoService.addListaIngresso(insercaoIngresso.getListaIngresso()));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@PutMapping(path = "/updateCheckin")
	public ResponseEntity<Object> updateChekin(@RequestBody IngressoTO ingressoTO) {
		try {
			return ResponseEntity.ok(ingressoFactory.getIngressoTO(ingressoService.updateCheckin(ingressoTO), null));
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@ResponseBody
	@PostMapping(path = "/updateStatusCompra")
	public ResponseEntity<Object> updateStatusCompra(@RequestBody JsonNode infoPagamento,
			@RequestParam(required = true) String codBoleto) {
		try {
			ingressoService.updateStatusCompra(codBoleto, infoPagamento.get("status").asText());
			return ResponseEntity.ok().build();
		} catch (ValidacaoException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
}
