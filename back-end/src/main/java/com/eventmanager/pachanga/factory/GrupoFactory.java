package com.eventmanager.pachanga.factory;

import com.eventmanager.pachanga.builder.GrupoBuilder;
import com.eventmanager.pachanga.builder.GrupoTOBuilder;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.dtos.GrupoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;


public class GrupoFactory {
	
	private GrupoFactory() {
	}
	
	public static GrupoTO getGrupoTO(Grupo grupo, Festa festa) {
		GrupoTOBuilder grupoToBuilder =  GrupoTOBuilder.getInstance()
				.codGrupo(grupo.getCodGrupo())
				.codFesta(festa)
				.nomeGrupo(grupo.getNomeGrupo())
				.quantMaxPessoas(grupo.getQuantMaxPessoas());
		if(festa != null && grupo.getQuantMaxPessoas() >= 1) {
			return grupoToBuilder.build();
		}else {
			throw new ValidacaoException("GRUPINVALID");	
		}
	}
	
	public static Grupo getGrupo(GrupoTO GrupoTo, Festa festa) {
		return GrupoBuilder.getInstance()
				.codGrupo(GrupoTo.getCodGrupo())
				.codFesta(festa)
				.nomeGrupo(GrupoTo.getNomeGrupo())
				.quantMaxPessoas(GrupoTo.getQuantMaxPessoas())
				.build();
	}
}
