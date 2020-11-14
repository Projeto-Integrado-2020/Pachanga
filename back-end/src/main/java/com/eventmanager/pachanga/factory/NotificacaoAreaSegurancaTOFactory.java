package com.eventmanager.pachanga.factory;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.NotificacaoAreaSegurancaTOBuilder;
import com.eventmanager.pachanga.domains.AreaSegurancaProblema;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.dtos.NotificacaoAreaSegurancaTO;

@Component(value = "notificacaoAreaSegurancaTOFactory")
public class NotificacaoAreaSegurancaTOFactory {
	
	public NotificacaoAreaSegurancaTO getNotificacaoArea(Festa festa, AreaSegurancaProblema areaProblema) {
		return NotificacaoAreaSegurancaTOBuilder.getInstance().codArea(areaProblema.getArea().getCodArea()).codFesta(festa.getCodFesta()).
				codProblema(areaProblema.getProblema().getCodProblema()).descProblema(areaProblema.getProblema().getDescProblema()).nomeArea(areaProblema.getArea().getNomeArea()).
				nomeFesta(festa.getNomeFesta()).descProblemaRelatado(areaProblema.getDescProblema()).build();
	}

}
