package com.eventmanager.pachanga.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.pachanga.domains.Convidado;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Notificacao;
import com.eventmanager.pachanga.domains.NotificacaoGrupo;
import com.eventmanager.pachanga.domains.NotificacaoUsuario;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.ConviteFestaTO;
import com.eventmanager.pachanga.dtos.NotificacaoAreaSegurancaTO;
import com.eventmanager.pachanga.dtos.NotificacaoConvidadoTO;
import com.eventmanager.pachanga.dtos.NotificacaoEstoqueTO;
import com.eventmanager.pachanga.dtos.NotificacaoGrupoTO;
import com.eventmanager.pachanga.dtos.NotificacaoMudancaStatusTO;
import com.eventmanager.pachanga.dtos.NotificacaoTO;
import com.eventmanager.pachanga.dtos.NotificacaoUsuarioTO;
import com.eventmanager.pachanga.dtos.UsuarioFestaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.NotificacaoFactory;
import com.eventmanager.pachanga.repositories.ConvidadoRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.NotificacaoConvidadoRepository;
import com.eventmanager.pachanga.repositories.NotificacaoGrupoRepository;
import com.eventmanager.pachanga.repositories.NotificacaoRepository;
import com.eventmanager.pachanga.repositories.NotificacaoUsuarioRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.tipo.TipoNotificacao;
import com.eventmanager.pachanga.tipo.TipoStatusNotificacao;

@Transactional
@Service
public class NotificacaoService {

	@Autowired
	private NotificacaoRepository notificacaoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ConvidadoRepository convidadoRepository;

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private NotificacaoUsuarioRepository notificacaoUsuarioRepository;

	@Autowired
	private NotificacaoGrupoRepository notificacaoGrupoRepository;
	
	@Autowired
	private NotificacaoFactory notificacaoFactory;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private FestaService festaService;
	
	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private NotificacaoConvidadoRepository notificacaoConvidadoRepository;
	
	@Autowired
	private AreaSegurancaService areaService;

	public NotificacaoTO procurarNotificacaoUsuario(int codUsuario) {
		Usuario usuario = this.validacaoUsuario(codUsuario, null);
		
		NotificacaoTO notificacaoTo = notificacaoFactory.getNotificacaoTO(notificacaoUsuarioRepository.getNotificacoesUsuario(codUsuario), notificacaoGrupoRepository.getNotificacoesGrupo(codUsuario), notificacaoConvidadoRepository.findConvidadoNotificacaoByEmail(usuario.getEmail()));
		
		notificacaoTo.getNotificacoesUsuario().stream().forEach(nu -> this.criacoaObjetoNotificacao(nu, nu.getMensagem()));
		notificacaoTo.getNotificacaoConvidado().stream().forEach(ng -> this.criacoaObjetoNotificacao(ng, ng.getMensagem()));
		notificacaoTo.getNotificacoesGrupo().stream().forEach(nc -> this.criacoaObjetoNotificacao(nc, nc.getMensagem()));
		return notificacaoTo;
	}
	
	private void criacoaObjetoNotificacao(Object notificacaoTO,String mensagem) {
		String codigo = mensagem.substring(0,mensagem.indexOf("?"));
		String[] valores = mensagem.substring(mensagem.indexOf("?") + 1).split("&");
		if(TipoNotificacao.CONVACEI.getValor().equals(codigo)) {
			UsuarioFestaTO usuarioFestaTo = usuarioService.getInfoUserFesta(Integer.parseInt(valores[0]), Integer.parseInt(valores[1]));
			((NotificacaoUsuarioTO) notificacaoTO).setUsuarioFesta(usuarioFestaTo);
		}else if(TipoNotificacao.CONVFEST.getValor().equals(codigo)) {
			ConviteFestaTO conviteFestaTO = festaService.procurarFestaConvidado(Integer.parseInt(valores[0]), Integer.parseInt(valores[1]));
			((NotificacaoConvidadoTO) notificacaoTO).setConviteFesta(conviteFestaTO);
		}else if(TipoNotificacao.ESTBAIXO.getValor().equals(codigo)) {
			NotificacaoEstoqueTO notificacaoEstoqueTO = produtoService.getInfoEstoqueProduto(Integer.parseInt(valores[0]), Integer.parseInt(valores[1]), Integer.parseInt(valores[2]));
			if(NotificacaoGrupoTO.class == notificacaoTO.getClass()) {
				((NotificacaoGrupoTO) notificacaoTO).setNotificacaoEstoque(notificacaoEstoqueTO);				
			} else if(NotificacaoUsuarioTO.class == notificacaoTO.getClass()) {
				((NotificacaoUsuarioTO) notificacaoTO).setNotificacaoEstoque(notificacaoEstoqueTO);
			}
		} else if(TipoNotificacao.STAALTER.getValor().equals(codigo)) {
			NotificacaoMudancaStatusTO notificacaoMudancaStatus = festaService.getNotificacaoMudancaStatus(Integer.parseInt(valores[0]));
			((NotificacaoUsuarioTO) notificacaoTO).setNotificacaoMudancaStatus(notificacaoMudancaStatus);
		} else if(TipoNotificacao.AREAPROB.getValor().equals(codigo)) {
			NotificacaoAreaSegurancaTO notificacaoArea = areaService.getNotificacaoProblemaArea(Integer.parseInt(valores[0]), Integer.parseInt(valores[1]));
			if(NotificacaoGrupoTO.class == notificacaoTO.getClass()) {
				((NotificacaoGrupoTO) notificacaoTO).setNotificacaoArea(notificacaoArea);	
			}else if(NotificacaoUsuarioTO.class == notificacaoTO.getClass()) {
				((NotificacaoUsuarioTO) notificacaoTO).setNotificacaoArea(notificacaoArea);				
			}
		}
	}
	
	public void deleteNotificacoesGrupos(List<Integer> codGrupo){
		List<NotificacaoGrupo> notificacoesGrupo = notificacaoGrupoRepository.findNotificacoesGrupos(codGrupo);
		notificacaoGrupoRepository.deleteAll(notificacoesGrupo);
	}
	
	public void deletarNotificacoesConvidado(Integer codConvidado) {
		this.validarConvidado(codConvidado);
		notificacaoConvidadoRepository.deleteNotificacoesConvidado(codConvidado);
	}

	public void deletarNotificacaoConvidado(Integer codConvidado, int codNotificacao, String mensagem) {
		this.validarNotificacao(codNotificacao);
		Convidado convidado = this.validarConvidado(codConvidado);
		this.validacaoUsuario(0, convidado.getEmail());
		notificacaoRepository.deleteConvidadoNotificacao(convidado.getCodConvidado(), codNotificacao, mensagem);
	}

	public void deletarNotificacaoGrupo(int codGrupo, String mensagem) {
		this.validarGrupo(codGrupo);
		notificacaoRepository.deleteNotificacaoGrupo(codGrupo, mensagem);
	}
	
	public void deletarNotificacaoGrupo(String mensagem) {
		notificacaoRepository.deleteNotificacaoGrupo(mensagem);
	}

	public void inserirNotificacaoGrupo(int codGrupo, int codNotificacao, String mensagem) {
		this.validarNotificacao(codNotificacao);
		this.validarGrupo(codGrupo);
		notificacaoGrupoRepository.insertNotificacaoGrupo(notificacaoGrupoRepository.getNextValMySequence(), codGrupo, codNotificacao, mensagem, this.getDataAtual());
	}

	public void inserirNotificacaoConvidado(Integer codConvidado, int codNotificacao, String mensagem) {
		this.validarNotificacao(codNotificacao);
		Convidado convidado = this.validarConvidado(codConvidado);
		notificacaoConvidadoRepository.insertConvidadoNotificacao(notificacaoConvidadoRepository.getNextValMySequence(), convidado.getCodConvidado(), codNotificacao, mensagem, this.getDataAtual());
	}

	public void inserirNotificacaoUsuario(Integer codUsuario, Integer codNotificacao, String mensagem) {
		this.validarNotificacao(codNotificacao);
		this.validacaoUsuario(codUsuario, null);
		notificacaoUsuarioRepository.insertNotificacaoUsuario(notificacaoUsuarioRepository.getNextValMySequence(), codUsuario, codNotificacao, false, TipoStatusNotificacao.NAOLIDA.getDescricao(), mensagem, this.getDataAtual());
	}

	private Convidado validarConvidado(Integer codConvidado) {
		Convidado convidado = convidadoRepository.findByIdConvidado(codConvidado);
		if(convidado == null) {
			throw new ValidacaoException("CONVNFOU");
		}
		return convidado;
	}

	private void validarGrupo(int codGrupo) {
		Grupo grupo = grupoRepository.findByCod(codGrupo);
		if(grupo == null) {
			throw new ValidacaoException("GRUPNFOU");
		}
	}

	private Usuario validacaoUsuario(int idUser, String emailConvidado) {
		Usuario usuario = null;
		if(idUser != 0) {
			usuario = usuarioRepository.findById(idUser);
		} else {
			usuario = usuarioRepository.findByEmail(emailConvidado);	
		}
		if(usuario == null) {
			throw new ValidacaoException("USERNFOU");
		}
		return usuario;
	}

	private void validarNotificacao(int codNotificacao) {
		Notificacao notificacao = notificacaoRepository.findByCodNotificacao(codNotificacao);
		if(notificacao == null) {
			throw new ValidacaoException("NOTINFOU");
		}
	}

	public void alterarStatus(int idUser, Integer codNotificacao) {
		this.validacaoUsuario(idUser, null);
		NotificacaoUsuario notificacaoUsuario = notificacaoUsuarioRepository.getNotificacaoUsuario(idUser, codNotificacao);
		if(notificacaoUsuario != null && !notificacaoUsuario.isDestaque()) {
			notificacaoUsuario.setStatus(TipoStatusNotificacao.LIDA.getDescricao());
			notificacaoUsuarioRepository.save(notificacaoUsuario);
		}
	}
	
	public void deleteNotificacao(int idUser, String mensagem) {
		this.validacaoUsuario(idUser, null);
		List<NotificacaoUsuario> notificacaoUsuario = notificacaoUsuarioRepository.getNotificacaoUsuarioByMensagem(idUser, mensagem);
		if(!notificacaoUsuario.isEmpty()) {
			notificacaoUsuarioRepository.deleteAll(notificacaoUsuario);
		}

	}

	public void destaqueNotificacao(int idUser, int codNotificacao) {
		this.validacaoUsuario(idUser, null);
		NotificacaoUsuario notificacaoUsuario = notificacaoUsuarioRepository.getNotificacaoUsuario(idUser, codNotificacao);
		if(notificacaoUsuario != null) {
			notificacaoUsuario.setDestaque(!notificacaoUsuario.isDestaque());
			notificacaoUsuarioRepository.save(notificacaoUsuario);
		}
	}
	
	public boolean verificarNotificacaoGrupo(int codGrupo, String mensagem) {	
		this.validarGrupo(codGrupo);	
		List<NotificacaoGrupo> notificacoesGrupo = notificacaoGrupoRepository.getNotificacoesGrupoByMensagem(codGrupo, mensagem);	
		return notificacoesGrupo.isEmpty();
	}
	
	public String criacaoMensagemNotificacaoUsuarioConvidado(int idGrupo, int idConvidado, String descNotificacao) { //o idConvidado pode ser tanto o id do convidado ou do usuário depende do tipo da mensagem
		return descNotificacao + "?" + idGrupo + "&" + idConvidado;
	}
	
	public String criarMensagemEstoqueBaixo(int codFesta, int codEstoque, int codProduto) {
		return TipoNotificacao.ESTBAIXO.getValor() + "?" + codFesta + "&" + codEstoque + "&" + codProduto;
	}
	
	public String criarMensagemAlteracaoStatusFesta(int codFesta, String status) {
		return TipoNotificacao.STAALTER.getValor() + "?" + codFesta + "&" + status;
	}
	
	public String criarMensagemAreaProblema(int codArea, int codProblema) {
		return TipoNotificacao.AREAPROB.getValor() + "?" + codArea + "&" + codProblema;
	}
	
	public LocalDateTime getDataAtual() {
		return LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
	}


}
