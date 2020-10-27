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
import com.eventmanager.pachanga.dtos.NotificacaoAreaSegurancaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.NotificacaoAreaSegurancaTOFactory;
import com.eventmanager.pachanga.repositories.AreaSegurancaProblemaRepository;
import com.eventmanager.pachanga.repositories.AreaSegurancaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.ProblemaRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.tipo.TipoAreaSeguranca;
import com.eventmanager.pachanga.tipo.TipoNotificacao;
import com.eventmanager.pachanga.tipo.TipoPermissao;

@Service
@Transactional
public class AreaSegurancaService {

	@Autowired
	private AreaSegurancaRepository areaSegurancaRepository;

	@Autowired
	private AreaSegurancaProblemaRepository areaSegurancaProblemaRepository;

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private FestaService festaService;

	@Autowired
	private ProblemaRepository problemaRepository;

	@Autowired
	private NotificacaoService notificacaoService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private NotificacaoAreaSegurancaTOFactory notificacaoAreaFactory;

	public List<AreaSeguranca> listaAreaSegurancaFesta(int codFesta, int codUsuario) {
		this.validarPermissaoUsuario(codUsuario, codFesta, TipoPermissao.VISUAREA.getCodigo());
		return areaSegurancaRepository.findAllAreasByCodFesta(codFesta);

	}

	public AreaSeguranca areaSegurancaFesta(int codFesta, int codArea, int codUsuario) {
		this.validarPermissaoUsuario(codUsuario, codFesta, TipoPermissao.VISUAREA.getCodigo());
		return this.validarAreaExistente(codArea);
	}

	public AreaSeguranca criacaoAreSegurancaFesta(int codUsuario, AreaSeguranca area) {
		this.validarPermissaoUsuario(codUsuario, area.getCodFesta(), TipoPermissao.ADDAREAS.getCodigo());
		area.setStatusSeguranca(TipoAreaSeguranca.SEGURO.getDescricao());
		area.setCodArea(areaSegurancaRepository.getNextValMySequence());
		this.validarArea(area);
		areaSegurancaRepository.save(area);
		return area;
	}

	public void deletarAreSegurancaFesta(int codUsuario, int codArea) {
		AreaSeguranca areaSeguranca = this.validarAreaExistente(codArea);
		this.validarPermissaoUsuario(codUsuario, areaSeguranca.getCodFesta(), TipoPermissao.DELEAREA.getCodigo());
		this.deletarNotificacoes(areaSeguranca);
		List<AreaSegurancaProblema> areasSegurancasProblemas = areaSegurancaProblemaRepository
				.findProblemasArea(codArea);
		areaSegurancaProblemaRepository.deleteAll(areasSegurancasProblemas);
		areaSegurancaRepository.delete(areaSeguranca);
	}

	public AreaSeguranca atualizarAreSegurancaFesta(int codUsuario, AreaSeguranca area) {
		this.validarArea(area);
		this.validarPermissaoUsuario(codUsuario, area.getCodFesta(), TipoPermissao.EDITAREA.getCodigo());
		AreaSeguranca areaBanco = this.validarAreaExistente(area.getCodArea());
		areaBanco.setNomeArea(area.getNomeArea());
		areaSegurancaRepository.save(areaBanco);
		return areaBanco;
	}

	public NotificacaoAreaSegurancaTO getNotificacaoProblemaArea(int codArea, int codProblema) {
		AreaSeguranca area = this.validarAreaExistente(codArea);
		Problema problema = problemaRepository.findProblemaByCodProblema(codProblema);
		Festa festa = festaService.validarFestaExistente(area.getCodFesta());
		return notificacaoAreaFactory.getNotificacaoArea(festa, problema, area);
	}

	private void deletarNotificacoes(AreaSeguranca area) {
		List<Grupo> grupos = grupoRepository.findGruposFesta(area.getCodFesta());
		grupos.stream().forEach(g -> {
			List<Usuario> usuarios = usuarioRepository.findUsuariosPorGrupo(g.getCodGrupo());
			usuarios.stream().forEach(u -> notificacaoService.deleteNotificacao(u.getCodUsuario(),
					TipoNotificacao.AREAPROB.getValor() + "?" + area.getCodArea() + "&")

			);
			notificacaoService.deletarNotificacaoGrupo(g.getCodGrupo(),
					TipoNotificacao.AREAPROB.getValor() + "?" + area.getCodArea() + "&");
		});

	}

	private void validarArea(AreaSeguranca area) {
		AreaSeguranca areaExistente = areaSegurancaRepository.findAreaByNomeArea(area.getNomeArea(),
				area.getCodFesta());
		if (areaExistente != null) {
			throw new ValidacaoException("NOMEAREA");// nome da area já está sendo usada naquela festa
		}
	}

	private void validarPermissaoUsuario(int idUsuario, int codFesta, int tipoPermissao) {
		List<Grupo> grupos = grupoRepository.findGrupoPermissaoUsuario(codFesta, idUsuario, tipoPermissao);
		if (grupos.isEmpty()) {
			throw new ValidacaoException("USESPERM");// usuário sem permissão
		}

	}

	private AreaSeguranca validarAreaExistente(int codArea) {
		AreaSeguranca areaBanco = areaSegurancaRepository.findAreaCodArea(codArea);
		if (areaBanco == null) {
			throw new ValidacaoException("AREANFOU");// area não encontrada
		}
		return areaBanco;
	}

}
