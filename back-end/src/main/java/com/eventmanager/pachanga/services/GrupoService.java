package com.eventmanager.pachanga.services;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.eventmanager.pachanga.repositories.GrupoRepository;

@Service
public class GrupoService {
	
	@Autowired
	private GrupoRepository grupoRepository;
	

}
