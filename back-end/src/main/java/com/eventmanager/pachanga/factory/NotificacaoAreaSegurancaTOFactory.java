package com.eventmanager.pachanga.factory;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.NotificacaoAreaSegurancaTOBuilder;
import com.eventmanager.pachanga.domains.AreaSeguranca;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Problema;
import com.eventmanager.pachanga.dtos.NotificacaoAreaSegurancaTO;

@Component(value = "notificacaoAreaSegurancaTOFactory")
public class NotificacaoAreaSegurancaTOFactory {
	
	public NotificacaoAreaSegurancaTO getNotificacaoArea(Festa festa, Problema problema, AreaSeguranca area) {
		return NotificacaoAreaSegurancaTOBuilder.getInstance().codArea(area.getCodArea()).codFesta(festa.getCodFesta()).
				codProblema(problema.getCodProblema()).descProblema(problema.getDescProblema()).nomeArea(area.getNomeArea()).
				nomeFesta(festa.getNomeFesta()).build();
	}

}
