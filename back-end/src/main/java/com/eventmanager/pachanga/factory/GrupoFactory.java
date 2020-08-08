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
				.codFesta(festa.getCodFesta())
				.nomeGrupo(grupo.getNomeGrupo())
				.quantMaxPessoas(grupo.getQuantMaxPessoas());
		if(grupo.getQuantMaxPessoas() >= 1) {
			return grupoToBuilder.build();
		}else {
			throw new ValidacaoException("GRUPINVALID");	
		}
	}			
				
	public static Grupo getGrupo(GrupoTO grupoTo, Festa festa) {
		return GrupoBuilder.getInstance()
				.codGrupo(grupoTo.getCodGrupo())
				.codFesta(festa)
				.nomeGrupo(grupoTo.getNomeGrupo())
				.quantMaxPessoas(grupoTo.getQuantMaxPessoas())
				.build();
	}			
	
}
