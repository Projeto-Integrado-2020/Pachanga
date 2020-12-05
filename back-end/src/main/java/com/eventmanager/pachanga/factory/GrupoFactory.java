package com.eventmanager.pachanga.factory;

import java.util.List;

import com.eventmanager.pachanga.builder.GrupoBuilder;
import com.eventmanager.pachanga.builder.GrupoTOBuilder;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.dtos.ConvidadoTO;
import com.eventmanager.pachanga.dtos.GrupoTO;
import com.eventmanager.pachanga.dtos.PermissaoTO;
import com.eventmanager.pachanga.dtos.UsuarioTO;


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
				.organizador(organizador)
				.build();
	}
	
	public static GrupoTO getGrupoTO(Grupo grupo, List<UsuarioTO> usuarios, List<ConvidadoTO> convidados, List<PermissaoTO> permissoes) {
		return GrupoTOBuilder.getInstance()
				.codGrupo(grupo.getCodGrupo())
				.codFesta(grupo.getFesta().getCodFesta())
				.nomeGrupo(grupo.getNomeGrupo())
				.isOrganizador(grupo.getOrganizador())
				.usuarios(usuarios)
				.convidados(convidados)
				.permissoes(permissoes)
				.build();
	}

}
