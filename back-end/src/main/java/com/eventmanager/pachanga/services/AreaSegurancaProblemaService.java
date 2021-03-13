package com.eventmanager.pachanga.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eventmanager.pachanga.domains.AreaSeguranca;
import com.eventmanager.pachanga.domains.AreaSegurancaProblema;
import com.eventmanager.pachanga.domains.AreaSegurancaProblemaFluxo;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Problema;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.AreaSegurancaProblemaHistorico;
import com.eventmanager.pachanga.dtos.AreaSegurancaProblemaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.AreaSegurancaProblemaFactory;
import com.eventmanager.pachanga.repositories.AreaSegurancaProblemaFluxoRepository;
import com.eventmanager.pachanga.repositories.AreaSegurancaProblemaRepository;
import com.eventmanager.pachanga.repositories.AreaSegurancaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.ProblemaRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.tipo.TipoAreaSeguranca;
import com.eventmanager.pachanga.tipo.TipoNotificacao;
import com.eventmanager.pachanga.tipo.TipoPermissao;
import com.eventmanager.pachanga.tipo.TipoProblema;
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
	private NotificacaoService notificacaoService;

	@Autowired
	private AreaSegurancaProblemaFactory areaSegurancaProblemaFactory;

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

	@Autowired
	private AreaSegurancaProblemaFluxoRepository areaSegurancaProblemaFluxoRepository;

	public AreaSegurancaProblema addProblemaSeguranca(AreaSegurancaProblemaTO problemaSegurancaTO, int idUsuario,
			MultipartFile imagem) throws IOException {
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
				imagem, festa, areaSeguranca, problema, usuarioEmissor, null);
		problemaSeguranca.setCodAreaProblema(areaSegurancaProblemaRepository.getNextValMySequence());
		this.validarAreaSegurancaProblema(problemaSeguranca);
		areaSegurancaProblemaRepository.save(problemaSeguranca);
		areaSeguranca.setStatusSeguranca(TipoAreaSeguranca.INSEGURO.getDescricao());
		areaSegurancaRepository.save(areaSeguranca);
		this.disparaNotificacao(problemaSeguranca);
		this.salvarAreaProblemaHistorico(problemaSeguranca);
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

	public AreaSegurancaProblema findProblemaSeguranca(int codAreaSegurancaProblema, int codFesta, int idUsuario) {
		grupoService.validarPermissaoUsuarioGrupo(codFesta, idUsuario, TipoPermissao.VISUPSEG.getCodigo());
		return areaSegurancaProblemaRepository.findAreaSegurancaProblema(codAreaSegurancaProblema);
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
			if ((areaSegurancaProblemaRepository.quantidadeProblemasAreaFesta(area.getCodArea(),
					areaSegurancaProblema.getFesta().getCodFesta()) - 1) == 0)
				area.setStatusSeguranca(TipoAreaSeguranca.SEGURO.getDescricao());
			areaSegurancaProblema.setStatusProblema(problemaSegurancaTO.getStatusProblema());
			Usuario usuarioResolv = usuarioService.validarUsuario(problemaSegurancaTO.getCodUsuarioResolv());
			areaSegurancaProblema.setCodUsuarioResolv(usuarioResolv);
			areaSegurancaProblema.setHorarioFim(notificacaoService.getDataAtual());
			areaSegurancaProblema.setObservacaoSolucao(problemaSegurancaTO.getObservacaoSolucao());
			this.validarAreaSegurancaProblema(areaSegurancaProblema);
			this.deletarNotificacoes(areaSegurancaProblema);
		} else {
			areaSegurancaProblema.setStatusProblema(TipoStatusProblema.ANDAMENTO.getValor());
			areaSegurancaProblema.setCodUsuarioResolv(null);
			areaSegurancaProblema.setHorarioFim(null);
			areaSegurancaProblema.setObservacaoSolucao(null);
			this.disparaNotificacao(areaSegurancaProblema);
			area.setStatusSeguranca(TipoAreaSeguranca.INSEGURO.getDescricao());
		}
		this.salvarAreaProblemaHistorico(areaSegurancaProblema);
		areaSegurancaRepository.save(area);
		areaSegurancaProblemaRepository.save(areaSegurancaProblema);
	}

	public void deleteByFesta(int idFesta) {
		List<AreaSegurancaProblema> areas = areaSegurancaProblemaRepository.findAllAreaSegurancaProblemaFesta(idFesta);
		areas.stream().forEach(asp -> this.deletarNotificacoes(asp));
		areaSegurancaProblemaRepository.deleteByCodFesta(idFesta);
	}

	private void salvarAreaProblemaHistorico(AreaSegurancaProblema problemaSeguranca) {
		AreaSegurancaProblemaFluxo areaFluxo = new AreaSegurancaProblemaFluxo(problemaSeguranca,
				problemaSeguranca.getCodUsuarioEmissor(), problemaSeguranca.getCodUsuarioResolv());
		areaFluxo.setDataHorario(notificacaoService.getDataAtual());
		areaFluxo.setCodHistorico(areaSegurancaProblemaFluxoRepository.getNextValMySequence());
		areaSegurancaProblemaFluxoRepository.save(areaFluxo);
	}

	public List<AreaSegurancaProblemaHistorico> getHistoricosAreaFesta(int codFesta) {
		List<AreaSegurancaProblemaHistorico> areasHistorico = new ArrayList<>();
		List<Object[]> problemasFestaUsuario = areaSegurancaProblemaFluxoRepository
				.findAreaProblemaFestaPorUsuarioStatus(codFesta);
		for (int contador = 0; contador < problemasFestaUsuario.size(); contador++) {
			areasHistorico.add(areaSegurancaProblemaFactory
					.getProblemaHistorico(areaSegurancaProblemaFluxoRepository.findProblemaAreaHistorico(
							(int) problemasFestaUsuario.get(contador)[0], (int) problemasFestaUsuario.get(contador)[1],
							(String) problemasFestaUsuario.get(contador)[2])));
		}
		return areasHistorico;
	}

//validadores______________________________________________________________________________________________________________

	private void validarAreaSegurancaProblema(AreaSegurancaProblema problemaSeguranca) {
		if (problemaSeguranca.getHorarioFim() != null
				&& problemaSeguranca.getHorarioFim().isBefore(problemaSeguranca.getHorarioInicio())) {
			throw new ValidacaoException("DATEINFE");
		}
		if ((problemaSeguranca.getStatusProblema().equals(TipoStatusProblema.FINALIZADO.getValor())
				|| problemaSeguranca.getStatusProblema().equals(TipoStatusProblema.ENGANO.getValor()))
				&& (problemaSeguranca.getCodUsuarioResolv() == null
						|| problemaSeguranca.getObservacaoSolucao() == null)) {

			throw new ValidacaoException("STSPINVL"); // STATUS INVALIDO
		}
		if (TipoProblema.OUTRPROB.getCodigo() == problemaSeguranca.getProblema().getCodProblema()
				&& problemaSeguranca.getDescProblema() == null) {
			throw new ValidacaoException("PROBNDES");// problema sem descrição
		}
	}

	public AreaSegurancaProblema validarProblemaSeguranca(int codProblemaSeguranca) {
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
		String mensagem = notificacaoService.criarMensagemAreaProblema(problemaSeguranca.getCodAreaProblema());
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

	public void deletarNotificacoes(AreaSegurancaProblema areaSegurancaProblema) {
		List<Grupo> grupos = grupoRepository.findGruposFesta(areaSegurancaProblema.getArea().getCodFesta());
		grupos.stream().forEach(g -> {
			List<Usuario> usuarios = usuarioRepository.findUsuariosPorGrupo(g.getCodGrupo());
			usuarios.stream().forEach(u -> notificacaoService.deleteNotificacao(u.getCodUsuario(),
					TipoNotificacao.AREAPROB.getValor() + "?" + areaSegurancaProblema.getCodAreaProblema())

			);
			notificacaoService.deletarNotificacaoGrupo(
					TipoNotificacao.AREAPROB.getValor() + "?" + areaSegurancaProblema.getCodAreaProblema());
		});

	}
}
