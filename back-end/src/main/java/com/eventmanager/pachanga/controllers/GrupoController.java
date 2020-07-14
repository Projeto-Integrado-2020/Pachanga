package com.eventmanager.pachanga.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/grupo")
@CrossOrigin
public class GrupoController {
	
	@PostMapping("/addUserGrupo")
	public ResponseEntity<Object> adicionarUsuarioGrupo(){
		return null;
	}

}
