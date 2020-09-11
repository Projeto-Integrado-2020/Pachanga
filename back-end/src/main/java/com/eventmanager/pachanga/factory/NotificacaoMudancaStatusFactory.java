package com.eventmanager.pachanga.factory;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.NotificacaoMudancaStatusTOBuilder;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.dtos.NotificacaoMudancaStatusTO;

@Component(value = "notificacaoMudancaStatusFactory")
public class NotificacaoMudancaStatusFactory {

	public NotificacaoMudancaStatusTO getNotificacaoMudancaStatus(Festa festa) {
		return NotificacaoMudancaStatusTOBuilder.getInstance().nomeFesta(festa.getNomeFesta())
				.tipoAlteracao(festa.getStatusFesta()).build();
	}

}
