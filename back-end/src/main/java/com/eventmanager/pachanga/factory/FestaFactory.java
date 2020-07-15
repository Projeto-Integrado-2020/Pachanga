package com.eventmanager.pachanga.factory;

import com.eventmanager.pachanga.builder.FestaBuilder;
import com.eventmanager.pachanga.builder.FestaTOBuilder;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.dtos.FestaTO;

public class FestaFactory {
	
	private FestaFactory() {
	}
	
	public static FestaTO getFestaTO(Festa festa) {
		return FestaTOBuilder.getInstance().codEnderecoFesta(festa.getCodEnderecoFesta()).codFesta(festa.getCodFesta()).
				descOrganizador(festa.getDescOrganizador()).descricaoFesta(festa.getDescricaoFesta()).horarioFimFesta(festa.getHorarioFimFesta()).
				horarioFimFestaReal(festa.getHorarioFimFestaReal()).horarioInicioFesta(festa.getHorarioInicioFesta()).
				nomeFesta(festa.getNomeFesta()).organizador(festa.getOrganizador()).statusFesta(festa.getStatusFesta()).build();
	}
	
	public static Festa getFesta(FestaTO festaTo) {
		return FestaBuilder.getInstance().codEnderecoFesta(festaTo.getCodEnderecoFesta()).codFesta(festaTo.getCodFesta()).
				descOrganizador(festaTo.getDescOrganizador()).descricaoFesta(festaTo.getDescricaoFesta()).
				horarioFimFesta(festaTo.getHorarioFimFesta()).horarioInicioFesta(festaTo.getHorarioInicioFesta()).
				nomeFesta(festaTo.getNomeFesta()).organizador(festaTo.getOrganizador()).statusFesta(festaTo.getStatusFesta()).build();
	}

}
