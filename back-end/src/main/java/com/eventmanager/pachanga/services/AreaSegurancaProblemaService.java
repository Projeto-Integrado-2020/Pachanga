package com.eventmanager.pachanga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.AreaSeguranca;
import com.eventmanager.pachanga.domains.AreaSegurancaProblema;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Problema;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.AreaSegurancaProblemaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.AreaSegurancaProblemaFactory;
import com.eventmanager.pachanga.repositories.AreaSegurancaProblemaRepository;
import com.eventmanager.pachanga.repositories.AreaSegurancaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.ProblemaRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.tipo.TipoAreaSeguranca;
import com.eventmanager.pachanga.tipo.TipoNotificacao;
import com.eventmanager.pachanga.tipo.TipoPermissao;
import com.eventmanager.pachanga.tipo.TipoStatusProblema;

@Service
@Transactional
public class AreaSegurancaProblemaService {

	@Autowired
	private ProblemaRepository problemaRepository;

	@Autowired
	private AreaSegurancaRepository areaSegurancaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	NotificacaoService notificacaoService;

	@Autowired
	AreaSegurancaProblemaFactory areaSegurancaProblemaFactory;

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private AreaSegurancaProblemaRepository areaSegurancaProblemaRepository;

	@Autowired
	private FestaService festaService;

	@Autowired
	private UsuarioService usuarioService;

	public AreaSegurancaProblema addProblemaSeguranca(AreaSegurancaProblemaTO problemaSegurancaTO, int idUsuario) {
		grupoService.validarPermissaoUsuarioGrupo(problemaSegurancaTO.getCodFesta(), idUsuario,
				TipoPermissao.ADDPSEGU.getCodigo());

		problemaSegurancaTO.setStatusProblema(TipoStatusProblema.ANDAMENTO.getValor()); // só se cria em andamento
		problemaSegurancaTO.setHorarioInicio(notificacaoService.getDataAtual());

		Usuario usuarioEmissor = festaService.validarUsuarioFesta(problemaSegurancaTO.getCodUsuarioEmissor(),
				problemaSegurancaTO.getCodFesta());
		Problema problema = this.validarProblema(problemaSegurancaTO.getCodProblema());
		AreaSeguranca areaSeguranca = areaSegurancaRepository.findAreaByCodFestaAndCodArea(
				problemaSegurancaTO.getCodFesta(), problemaSegurancaTO.getCodAreaSeguranca());
		Festa festa = festaService.validarFestaExistente(problemaSegurancaTO.getCodFesta());

		AreaSegurancaProblema problemaSeguranca = areaSegurancaProblemaFactory.getProblemaSeguranca(problemaSegurancaTO,
				festa, areaSeguranca, problema, usuarioEmissor, null);
		problemaSeguranca.setCodAreaProblema(areaSegurancaProblemaRepository.getNextValMySequence());
		this.validarAreaSegurancaProblema(problemaSeguranca);
		areaSegurancaProblemaRepository.save(problemaSeguranca);
		areaSeguranca.setStatusSeguranca(TipoAreaSeguranca.INSEGURO.getDescricao());
		areaSegurancaRepository.save(areaSeguranca);
		this.disparaNotificacao(problemaSeguranca);
		return problemaSeguranca;
	}

	public AreaSegurancaProblema updateProblemaSeguranca(AreaSegurancaProblemaTO problemaSegurancaTO, int idUsuario) {
		grupoService.validarPermissaoUsuarioGrupo(problemaSegurancaTO.getCodFesta(), idUsuario,
				TipoPermissao.EDITPSEG.getCodigo());
		AreaSegurancaProblema problemaSeguranca = this
				.validarProblemaSeguranca(problemaSegurancaTO.getCodAreaProblema());
		Problema problema = this.validarProblema(problemaSegurancaTO.getCodProblema());

		problemaSeguranca.setDescProblema(problemaSegurancaTO.getDescProblema());
		problemaSeguranca.setProblema(problema);

		this.validarAreaSegurancaProblema(problemaSeguranca);
		areaSegurancaProblemaRepository.save(problemaSeguranca);

		return problemaSeguranca;
	}

	public AreaSegurancaProblema findProblemaSeguranca(int codAreaSeguranca, int codProblema, int codFesta,
			int idUsuario) {
		grupoService.validarPermissaoUsuarioGrupo(codFesta, idUsuario, TipoPermissao.VISUPSEG.getCodigo());
		return areaSegurancaProblemaRepository.findAreaSegurancaProblema(codAreaSeguranca, codProblema);
	}

	public List<AreaSegurancaProblema> findAllProblemasSegurancaArea(int codAreaProblema, int codFesta, int idUsuario) {
		grupoService.validarPermissaoUsuarioGrupo(codFesta, idUsuario, TipoPermissao.VISUPSEG.getCodigo());
		return areaSegurancaProblemaRepository.findAllAreaSegurancaProblemaArea(codAreaProblema, codFesta);
	}

	public List<AreaSegurancaProblema> findAllProblemasSegurancaFesta(int codFesta, int idUsuario) {
		grupoService.validarPermissaoUsuarioGrupo(codFesta, idUsuario, TipoPermissao.VISUPSEG.getCodigo());
		return areaSegurancaProblemaRepository.findAllAreaSegurancaProblemaFesta(codFesta);
	}

	public void alterarStatusProblema(AreaSegurancaProblemaTO problemaSegurancaTO, int idUsuario, Boolean finaliza) {
		grupoService.validarPermissaoUsuarioGrupo(problemaSegurancaTO.getCodFesta(), idUsuario,
				TipoPermissao.EDITPSEG.getCodigo());
		AreaSegurancaProblema areaSegurancaProblema = this
				.validarProblemaSeguranca(problemaSegurancaTO.getCodAreaProblema());
		AreaSeguranca area = areaSegurancaRepository.findAreaByCodFestaAndCodArea(
				areaSegurancaProblema.getFesta().getCodFesta(), areaSegurancaProblema.getArea().getCodArea());
		if (finaliza.booleanValue()) {
			areaSegurancaProblema.setStatusProblema(problemaSegurancaTO.getStatusProblema());
			Usuario usuarioResolv = usuarioService.validarUsuario(problemaSegurancaTO.getCodUsuarioResolv());
			areaSegurancaProblema.setCodUsuarioResolv(usuarioResolv);
			areaSegurancaProblema.setHorarioFim(notificacaoService.getDataAtual());
			this.validarAreaSegurancaProblema(areaSegurancaProblema);
			this.deletarNotificacoes(areaSegurancaProblema.getArea(), areaSegurancaProblema.getProblema());
			area.setStatusSeguranca(TipoAreaSeguranca.SEGURO.getDescricao());
		} else {
			areaSegurancaProblema.setStatusProblema(TipoStatusProblema.ANDAMENTO.getValor());
			areaSegurancaProblema.setCodUsuarioResolv(null);
			areaSegurancaProblema.setHorarioFim(null);
			this.disparaNotificacao(areaSegurancaProblema);
			area.setStatusSeguranca(TipoAreaSeguranca.INSEGURO.getDescricao());
		}
		areaSegurancaRepository.save(area);
		areaSegurancaProblemaRepository.save(areaSegurancaProblema);
	}

//validadores______________________________________________________________________________________________________________

	private void validarAreaSegurancaProblema(AreaSegurancaProblema problemaSeguranca) {
		if (problemaSeguranca.getHorarioFim() != null
				&& problemaSeguranca.getHorarioFim().isBefore(problemaSeguranca.getHorarioInicio())) {
			throw new ValidacaoException("DATEINFE");
		}
		if ((problemaSeguranca.getStatusProblema().equals(TipoStatusProblema.FINALIZADO.getValor())
				|| problemaSeguranca.getStatusProblema().equals(TipoStatusProblema.ENGANO.getValor()))
				&& problemaSeguranca.getCodUsuarioResolv() == null) {

			throw new ValidacaoException("STSINVL"); // STATUS INVALIDO
		}
	}

	private AreaSegurancaProblema validarProblemaSeguranca(int codProblemaSeguranca) {
		AreaSegurancaProblema problemaSeguranca = areaSegurancaProblemaRepository
				.findByCodProblema(codProblemaSeguranca);
		if (problemaSeguranca == null) {
			throw new ValidacaoException("PRSENFOU"); // ProblemaSeguranca nao existe
		}
		return problemaSeguranca;
	}

	private Problema validarProblema(int codProblema) {
		Problema problema = problemaRepository.findProblemaByCodProblema(codProblema);
		if (problema == null) {
			throw new ValidacaoException("PROBNFOU");
		}
		return problema;
	}

	private void disparaNotificacao(AreaSegurancaProblema problemaSeguranca) {
		List<Grupo> grupos = grupoRepository
				.findGruposPermissaoAreaSegurancaProblema(problemaSeguranca.getFesta().getCodFesta());
		String mensagem = notificacaoService.criarMensagemAreaProblema(problemaSeguranca.getArea().getCodArea(),
				problemaSeguranca.getProblema().getCodProblema());
		for (Grupo grupo : grupos) {
			if (notificacaoService.verificarNotificacaoGrupo(grupo.getCodGrupo(), mensagem)) {
				notificacaoService.inserirNotificacaoGrupo(grupo.getCodGrupo(), TipoNotificacao.AREAPROB.getCodigo(),
						mensagem);
				List<Usuario> usuarios = usuarioRepository.findUsuariosPorGrupo(grupo.getCodGrupo());
				for (Usuario usuario : usuarios) {
					notificacaoService.inserirNotificacaoUsuario(usuario.getCodUsuario(),
							TipoNotificacao.AREAPROB.getCodigo(), mensagem);
				}
			}
		}
	}

	public void deletarNotificacoes(AreaSeguranca area, Problema problema) {
		List<Grupo> grupos = grupoRepository.findGruposFesta(area.getCodFesta());
		grupos.stream().forEach(g -> {
			List<Usuario> usuarios = usuarioRepository.findUsuariosPorGrupo(g.getCodGrupo());
			usuarios.stream().forEach(u -> notificacaoService.deleteNotificacao(u.getCodUsuario(),
					TipoNotificacao.AREAPROB.getValor() + "?" + area.getCodArea() + "&" + (problema == null ? "" : problema.getCodProblema()))

			);
			notificacaoService.deletarNotificacaoGrupo(
					TipoNotificacao.AREAPROB.getValor() + "?" + area.getCodArea() + "&" + (problema == null ? "" : problema.getCodProblema()));
		});

	}
}
