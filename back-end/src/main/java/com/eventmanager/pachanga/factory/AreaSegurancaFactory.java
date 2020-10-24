package com.eventmanager.pachanga.factory;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.AreaSegurancaBuilder;
import com.eventmanager.pachanga.builder.AreaSegurancaTOBuilder;
import com.eventmanager.pachanga.domains.AreaSeguranca;
import com.eventmanager.pachanga.dtos.AreaSegurancaTO;

@Component(value = "areaSegurancaFactory")
public class AreaSegurancaFactory {
	
	private AreaSegurancaFactory() {
	}
	
	public AreaSegurancaTO getAreaTo(AreaSeguranca areaSeguranca) {
		return AreaSegurancaTOBuilder.getInstance().codArea(areaSeguranca.getCodArea()).
				codFesta(areaSeguranca.getCodFesta()).nomeArea(areaSeguranca.getNomeArea()).
				statusSeguranca(areaSeguranca.getStatusSeguranca()).build();
	}
	
	public AreaSeguranca getArea(AreaSegurancaTO areaSegurancaTo) {
		return AreaSegurancaBuilder.getInstance().codArea(areaSegurancaTo.getCodArea()).
				codFesta(areaSegurancaTo.getCodFesta()).nomeArea(areaSegurancaTo.getNomeArea()).
				statusSeguranca(areaSegurancaTo.getStatusSeguranca()).build();
	}

}
