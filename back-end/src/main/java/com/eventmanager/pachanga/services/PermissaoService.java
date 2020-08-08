package com.eventmanager.pachanga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.Permissao;
import com.eventmanager.pachanga.repositories.PermissaoRepository;

@Service
@Transactional
public class PermissaoService {
	@Autowired
	private PermissaoRepository permissaoRepository;	
	
	public List<Permissao> getAllPermissao(){
		return permissaoRepository.findAllPermissao();
	}
	
}
