package com.eventmanager.pachanga.factory;

import java.util.List;

import com.eventmanager.pachanga.builder.FestaBuilder;
import com.eventmanager.pachanga.builder.FestaTOBuilder;
import com.eventmanager.pachanga.builder.GrupoBuilder;
import com.eventmanager.pachanga.builder.GrupoTOBuilder;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.dtos.GrupoTO;

public class GrupoFactory {
	
	
	private GrupoFactory() {
	}
	
//	public static GrupoTO getGrupoTO(Festa festa, List<UsuarioTO> usuarios, boolean enviarQuantidade) {
//		GrupoTOBuilder grupoToBuilder =  GrupoTOBuilder.getInstance().
//	}
	
	
	/*
	public static GrupoTO getGrupoTO(Festa festa, List<UsuarioTO> usuarios, boolean enviarQuantidade) {
		FestaTOBuilder festaToBuilder = FestaTOBuilder.getInstance().codEnderecoFesta(festa.getCodEnderecoFesta()).codFesta(festa.getCodFesta()).
				descOrganizador(festa.getDescOrganizador()).descricaoFesta(festa.getDescricaoFesta()).horarioFimFesta(festa.getHorarioFimFesta()).
				horarioFimFestaReal(festa.getHorarioFimFestaReal()).horarioInicioFesta(festa.getHorarioInicioFesta()).
				nomeFesta(festa.getNomeFesta()).organizador(festa.getOrganizador()).statusFesta(festa.getStatusFesta());
		if(usuarios != null) {
			if(enviarQuantidade) {
				festaToBuilder.quantidadeParticipantes(usuarios.size());
			}else {
				festaToBuilder.usuarios(usuarios);
			}
		}
		return festaToBuilder.build();
	}
	*/
	
	
	public static Grupo getGrupo(GrupoTO GrupoTo) {
		return GrupoBuilder.getInstance()
				.codGrupo(GrupoTo.getCodGrupo())
				.codFesta(GrupoTo.getCodFesta())
				.nomeGrupo(GrupoTo.getNomeGrupo())
				.quantMaxPessoas(GrupoTo.getQuantMaxPessoas())
				.build();
	}
}
