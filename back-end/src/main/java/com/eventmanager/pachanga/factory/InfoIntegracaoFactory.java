package com.eventmanager.pachanga.factory;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.InfoIntegracaoBuilder;
import com.eventmanager.pachanga.builder.InfoIntegracaoTOBuilder;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.InfoIntegracao;
import com.eventmanager.pachanga.dtos.InfoIntegracaoTO;

@Component(value = "infoIntegracaoFactory")
public class InfoIntegracaoFactory {
	
	public InfoIntegracao getInfoIntegracao(InfoIntegracaoTO infoTo, Festa festa) {
		return InfoIntegracaoBuilder.getInstance().codInfo(infoTo.getCodInfo()).festa(festa).terceiroInt(infoTo.getTerceiroInt())
		.token(infoTo.getToken()).codEvent(infoTo.getCodEvent()).build();
	}
	
	public InfoIntegracaoTO getInfoIntegracaoTO(InfoIntegracao info) {
		return InfoIntegracaoTOBuilder.getInstance().codInfo(info.getCodInfo()).codFesta(info.getFesta().getCodFesta()).terceiroInt(info.getTerceiroInt())
		.token(info.getToken()).codEvent(info.getCodEvent()).build();
	}

}
