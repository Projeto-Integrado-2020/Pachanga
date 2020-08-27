package com.eventmanager.pachanga.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.NotificacaoConvidadoTOBuilder;
import com.eventmanager.pachanga.builder.NotificacaoGrupoTOBuilder;
import com.eventmanager.pachanga.builder.NotificacaoUsuarioTOBuilder;
import com.eventmanager.pachanga.domains.NotificacaoConvidado;
import com.eventmanager.pachanga.domains.NotificacaoGrupo;
import com.eventmanager.pachanga.domains.NotificacaoUsuario;
import com.eventmanager.pachanga.dtos.NotificacaoConvidadoTO;
import com.eventmanager.pachanga.dtos.NotificacaoGrupoTO;
import com.eventmanager.pachanga.dtos.NotificacaoTO;
import com.eventmanager.pachanga.dtos.NotificacaoUsuarioTO;

@Component(value = "notificacaoFactory")
public class NotificacaoFactory {

	public NotificacaoConvidadoTO getNotificacaoTO(NotificacaoConvidado notificacaoConvidado) {
		return NotificacaoConvidadoTOBuilder.getInstance().codNotificacao(notificacaoConvidado.getCodConvidadoNotificacao()).codConvidado(notificacaoConvidado.getConvidado().getCodConvidado())
				.mensagem(notificacaoConvidado.getMensagem()).dataEmissao(notificacaoConvidado.getDataEmissao()).build();
	}
	
	public NotificacaoTO getNotificacaoTO(List<NotificacaoUsuario> notificacoesUsuario, List<NotificacaoGrupo> notificacoesGrupo, List<NotificacaoConvidado> notificacoesConvidado){
		NotificacaoTO notificacaoTO = new NotificacaoTO();
		notificacaoTO.setNotificacoesUsuario(notificacoesUsuario.stream().map(nu -> this.getNotificacaoUsuarioTO(nu)).collect(Collectors.toList()));
		notificacaoTO.setNotificacoesGrupo(notificacoesGrupo.stream().map(ng -> this.getNotificacaoGrupoTO(ng)).collect(Collectors.toList()));
		notificacaoTO.setNotificacaoConvidado(notificacoesConvidado.stream().map(n -> this.getNotificacaoTO(n)).collect(Collectors.toList()));
		return notificacaoTO;
	}
	
	public NotificacaoGrupoTO getNotificacaoGrupoTO(NotificacaoGrupo notificacaoGrupo) {
		return NotificacaoGrupoTOBuilder.getInstance().codGrupo(notificacaoGrupo.getGrupo().getCodGrupo()).codNotificacao(notificacaoGrupo.getCodNotificacaoGrupo())
				.mensagem(notificacaoGrupo.getMensagem()).dataEmissao(notificacaoGrupo.getDataEmissao()).build();
	}
	
	public NotificacaoUsuarioTO getNotificacaoUsuarioTO(NotificacaoUsuario notificacaoUsuario) {
		return NotificacaoUsuarioTOBuilder.getInstance().codNotificacao(notificacaoUsuario.getCodNotificacaoUsuario())
				.codUsuario(notificacaoUsuario.getUsuario().getCodUsuario()).destaque(notificacaoUsuario.isDestaque()).mensagem(notificacaoUsuario.getMensagem())
				.status(notificacaoUsuario.getStatus()).dataEmissao(notificacaoUsuario.getDataEmissao()).build();
	}
	
}
