package com.eventmanager.pachanga.factory;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.UsuarioFestaTOBuilder;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.UsuarioFestaTO;

@Component(value = "usuarioFestaTOFactory")
public class UsuarioFestaTOFactory {
	
	public UsuarioFestaTO getUsuarioFestaTO(Usuario usuario, Grupo grupo) {
		return UsuarioFestaTOBuilder.getInstance().nomeFesta(grupo.getFesta().getNomeFesta()).nomeGrupo(grupo.getNomeGrupo()).nomeUsuario(usuario.getNomeUser()).build();
	}

}
