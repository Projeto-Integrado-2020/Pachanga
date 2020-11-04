package com.eventmanager.pachanga.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.AreaSegurancaBuilder;
import com.eventmanager.pachanga.builder.AreaSegurancaTOBuilder;
import com.eventmanager.pachanga.domains.AreaSeguranca;
import com.eventmanager.pachanga.domains.AreaSegurancaProblema;
import com.eventmanager.pachanga.dtos.AreaSegurancaTO;

@Component(value = "areaSegurancaFactory")
public class AreaSegurancaFactory {

	@Autowired
	private AreaSegurancaProblemaFactory areaSegurancaProblemaFactory;

	private AreaSegurancaFactory() {
	}

	public AreaSegurancaTO getAreaTo(AreaSeguranca areaSeguranca) {
		return AreaSegurancaTOBuilder.getInstance().codArea(areaSeguranca.getCodArea())
				.codFesta(areaSeguranca.getCodFesta()).nomeArea(areaSeguranca.getNomeArea())
				.statusSeguranca(areaSeguranca.getStatusSeguranca()).build();
	}

	public AreaSeguranca getArea(AreaSegurancaTO areaSegurancaTo) {
		return AreaSegurancaBuilder.getInstance().codArea(areaSegurancaTo.getCodArea())
				.codFesta(areaSegurancaTo.getCodFesta()).nomeArea(areaSegurancaTo.getNomeArea())
				.statusSeguranca(areaSegurancaTo.getStatusSeguranca()).build();
	}

	public AreaSegurancaTO getAreaTo(AreaSeguranca areaSeguranca, List<AreaSegurancaProblema> problemasArea) {
		return AreaSegurancaTOBuilder.getInstance().codArea(areaSeguranca.getCodArea())
				.codFesta(areaSeguranca.getCodFesta()).nomeArea(areaSeguranca.getNomeArea())
				.statusSeguranca(areaSeguranca.getStatusSeguranca())
				.problemasArea(
						problemasArea.stream().map(p -> areaSegurancaProblemaFactory.getAreaSegurancaProblemaTO(p))
								.collect(Collectors.toList()))
				.build();
	}

}
