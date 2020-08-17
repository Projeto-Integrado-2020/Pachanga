package com.eventmanager.pachanga.dtos;

import java.util.List;

public class NotificacaoTO {
	
	private List<NotificacaoUsuarioTO> notificacoesUsuario;
	
	private List<NotificacaoGrupoTO> notificacoesGrupo;
	
	private List<NotificacaoConvidadoTO> notificacaoConvidado;
	
	
	public List<NotificacaoUsuarioTO> getNotificacoesUsuario() {
		return notificacoesUsuario;
	}

	public void setNotificacoesUsuario(List<NotificacaoUsuarioTO> notificacoesUsuario) {
		this.notificacoesUsuario = notificacoesUsuario;
	}

	public List<NotificacaoGrupoTO> getNotificacoesGrupo() {
		return notificacoesGrupo;
	}

	public void setNotificacoesGrupo(List<NotificacaoGrupoTO> notificacoesGrupo) {
		this.notificacoesGrupo = notificacoesGrupo;
	}

	public List<NotificacaoConvidadoTO> getNotificacaoConvidado() {
		return notificacaoConvidado;
	}

	public void setNotificacaoConvidado(List<NotificacaoConvidadoTO> notificacaoConvidado) {
		this.notificacaoConvidado = notificacaoConvidado;
	}

}
