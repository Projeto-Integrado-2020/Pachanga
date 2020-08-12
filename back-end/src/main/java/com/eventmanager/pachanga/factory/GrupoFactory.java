package com.eventmanager.pachanga.factory;

import com.eventmanager.pachanga.builder.GrupoBuilder;
import com.eventmanager.pachanga.builder.GrupoTOBuilder;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.dtos.GrupoTO;


public class GrupoFactory {

	private GrupoFactory() {
	}

	public static GrupoTO getGrupoTO(Grupo grupo) {
		return GrupoTOBuilder.getInstance()
				.codGrupo(grupo.getCodGrupo())
				.codFesta(grupo.getFesta().getCodFesta())
				.nomeGrupo(grupo.getNomeGrupo())
				.isOrganizador(grupo.getOrganizador())
				.build();
	}			

	public static Grupo getGrupo(String nomeGrupo, int codGrupo, Festa festa, boolean organizador) {
		return GrupoBuilder.getInstance()
				.codGrupo(codGrupo)
				.codFesta(festa)
				.nomeGrupo(nomeGrupo)
//				.quantMaxPessoas(grupoTo.getQuantMaxPessoas())
				.organizador(organizador)
				.build();
	}			

}
