package com.eventmanager.pachanga.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.Ingresso;
import com.eventmanager.pachanga.dtos.IngressoTO;
import com.eventmanager.pachanga.factory.IngressoFactory;
import com.eventmanager.pachanga.repositories.IngressoRepository;

@Service
@Transactional
public class IngressoService {
	
	@Autowired
	private IngressoRepository ingressoRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private FestaService festaService;
	
	@Autowired
	private IngressoFactory ingressoFactory;
	
	public List<Ingresso> getIngressosUser(int codUsuario){
		usuarioService.validarUsuario(codUsuario);
		return ingressoRepository.findIngressosUser(codUsuario);
	}
	
	public List<Ingresso> getIngressosFesta(int codFesta){
		festaService.validarFestaExistente(codFesta);
		return ingressoRepository.findIngressosFesta(codFesta);
	}
	
	public Ingresso addIngresso(IngressoTO ingressoTO) {
		Ingresso ingresso = ingressoFactory.getIngresso(ingressoTO);
		ingressoRepository.save(ingresso);
		return ingresso;
	}

}
