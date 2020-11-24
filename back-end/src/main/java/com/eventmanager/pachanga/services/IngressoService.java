package com.eventmanager.pachanga.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Ingresso;
import com.eventmanager.pachanga.dtos.IngressoTO;
import com.eventmanager.pachanga.factory.IngressoFactory;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.IngressoRepository;
import com.eventmanager.pachanga.utils.EmailMensagem;

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
	
	@Autowired
	private FestaRepository festaRespository;

	//@Autowired
	//private UsuarioRepository usuarioRepository;
	
	public List<Ingresso> getIngressosUser(int codUsuario){
		usuarioService.validarUsuario(codUsuario);
		return ingressoRepository.findIngressosUser(codUsuario);
	}
	
	public Festa getFestaIngressoUser(int codFesta) {
		return festaRespository.findByCodFesta(codFesta);
	}
	
	public List<Ingresso> getIngressosFesta(int codFesta){
		festaService.validarFestaExistente(codFesta);
		return ingressoRepository.findIngressosFesta(codFesta);
	}
	
	public Ingresso addIngresso(IngressoTO ingressoTO) {
		Ingresso ingresso = ingressoFactory.getIngresso(ingressoTO);
		ingressoRepository.save(ingresso);
		if(ingresso.getStatusCompra().equals("P")) {
			EmailMensagem emailMessage = new EmailMensagem();
			emailMessage.enviarEmailQRCode(ingresso.getUsuario().getEmail(), 
										   Integer.toString(ingresso.getFesta().getCodFesta()), 
										   ingresso.getFesta());
		}
		return ingresso;
	}
	
	public void updateCheckin(IngressoTO ingressoTO) {
		ingressoRepository.updateCheckin(ingressoTO.getCodIngresso(), ingressoTO.getStatusIngresso(), ingressoTO.getDataCheckin());
	}
	
	public void updateStatusCompra(IngressoTO ingressoTO) {
		ingressoRepository.updateStatusCompra(ingressoTO.getCodIngresso(), ingressoTO.getStatusCompra());
		if(ingressoTO.getStatusCompra().equals("P")) {
			EmailMensagem emailMessage = new EmailMensagem();
			emailMessage.enviarEmailQRCode(ingressoTO.getUsuario().getEmail(), 
										   Integer.toString(ingressoTO.getFesta().getCodFesta()), 
										   ingressoTO.getFesta());
		}
	}
	/*
	private Festa getFesta(int codFesta) {
		Festa festa = festaRespository.findByCodFesta(codFesta);
		return festa;
	}
	
	private Usuario getUsuario(int codUsuario) {
		Usuario usuario = usuarioRepository.findById(codUsuario);
		return usuario;
	}
	*/
	

}
