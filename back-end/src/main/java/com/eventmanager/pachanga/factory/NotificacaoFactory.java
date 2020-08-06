package com.eventmanager.pachanga.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.NotificacaoTOBuilder;
import com.eventmanager.pachanga.domains.Notificacao;
import com.eventmanager.pachanga.dtos.NotificacaoTO;

@Component(value = "notificacaoFactory")
public class NotificacaoFactory {

	public NotificacaoTO getNotificacaoTO(Notificacao notificacao) {
		return NotificacaoTOBuilder.getInstance().codNotificacao(notificacao.getCodNotificacao()).descNotificacao(notificacao.getDescNotificacao()).build();
	}
	
	public List<NotificacaoTO> getListNotificacaoTO(List<Notificacao> notificacoes){
		return notificacoes.stream().map(n -> this.getNotificacaoTO(n)).collect(Collectors.toList());
	}
	
}
