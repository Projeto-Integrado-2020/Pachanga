package com.eventmanager.pachanga.factory;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.ConviteFestaTOBuilder;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.dtos.ConviteFestaTO;

@Component(value = "conviteFestaFactory")
public class ConviteFestaFactory {
	
	public ConviteFestaTO getConviteFestaTO(Festa festa, Grupo grupo) {
		return ConviteFestaTOBuilder.getInstance().enderecoFesta(festa.getCodEnderecoFesta()).horarioFinal(festa.getHorarioFimFesta()).horarioInicial(festa.getHorarioInicioFesta())
				.nomeFesta(festa.getNomeFesta()).nomeGrupo(grupo.getNomeGrupo()).build();
	}

}
