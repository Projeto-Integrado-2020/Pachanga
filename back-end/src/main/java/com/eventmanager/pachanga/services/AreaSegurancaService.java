package com.eventmanager.pachanga.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.AreaSeguranca;
import com.eventmanager.pachanga.domains.AreaSegurancaProblema;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.dtos.NotificacaoAreaSegurancaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.NotificacaoAreaSegurancaTOFactory;
import com.eventmanager.pachanga.repositories.AreaSegurancaProblemaRepository;
import com.eventmanager.pachanga.repositories.AreaSegurancaRepository;
import com.eventmanager.pachanga.repositories.ProblemaRepository;
import com.eventmanager.pachanga.tipo.TipoAreaSeguranca;
import com.eventmanager.pachanga.tipo.TipoPermissao;

@Service
@Transactional
public class AreaSegurancaService {

	@Autowired
	private AreaSegurancaRepository areaSegurancaRepository;

	@Autowired
	private AreaSegurancaProblemaRepository areaSegurancaProblemaRepository;

	@Autowired
	private FestaService festaService;

	@Autowired
	private ProblemaRepository problemaRepository;

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private NotificacaoAreaSegurancaTOFactory notificacaoAreaFactory;
	
	@Autowired
	private AreaSegurancaProblemaService areaSegurancaProblemaService;

	public Map<AreaSeguranca, List<AreaSegurancaProblema>> listaAreaSegurancaFesta(int codFesta, int codUsuario) {
		grupoService.validarPermissaoUsuarioGrupo(codFesta, codUsuario, TipoPermissao.VISUAREA.getCodigo());
		List<AreaSeguranca> areas = areaSegurancaRepository.findAllAreasByCodFesta(codFesta);
		HashMap<AreaSeguranca, List<AreaSegurancaProblema>> hashAreaProblema = new HashMap<>();
		for (AreaSeguranca areaSeguranca : areas) {
			List<AreaSegurancaProblema> areasprob = areaSegurancaProblemaRepository.findAllAreaSegurancaProblemaArea(areaSeguranca.getCodArea(), areaSeguranca.getCodFesta());
			hashAreaProblema.put(areaSeguranca, areasprob);
		}
		return hashAreaProblema;
	}

	public AreaSeguranca criacaoAreSegurancaFesta(int codUsuario, AreaSeguranca area) {
		grupoService.validarPermissaoUsuarioGrupo(area.getCodFesta(), codUsuario, TipoPermissao.ADDAREAS.getCodigo());
		area.setStatusSeguranca(TipoAreaSeguranca.SEGURO.getDescricao());
		area.setCodArea(areaSegurancaRepository.getNextValMySequence());
		this.validarArea(area);
		areaSegurancaRepository.save(area);
		return area;
	}

	public void deletarAreSegurancaFesta(int codUsuario, int codArea) {
		AreaSeguranca areaSeguranca = this.validarAreaExistente(codArea);
		grupoService.validarPermissaoUsuarioGrupo(areaSeguranca.getCodFesta(), codUsuario, TipoPermissao.DELEAREA.getCodigo());
		List<AreaSegurancaProblema> problemasArea = areaSegurancaProblemaRepository.findProblemasArea(codArea);
		problemasArea.forEach(pa->
			areaSegurancaProblemaService.deletarNotificacoes(pa)
		);
		List<AreaSegurancaProblema> areasSegurancasProblemas = areaSegurancaProblemaRepository
				.findProblemasArea(codArea);
		areaSegurancaProblemaRepository.deleteAll(problemasArea);
		areaSegurancaProblemaRepository.deleteAll(areasSegurancasProblemas);
		areaSegurancaRepository.delete(areaSeguranca);
	}

	public AreaSeguranca atualizarAreSegurancaFesta(int codUsuario, AreaSeguranca area) {
		this.validarArea(area);
		grupoService.validarPermissaoUsuarioGrupo(area.getCodFesta(), codUsuario, TipoPermissao.EDITAREA.getCodigo());
		AreaSeguranca areaBanco = this.validarAreaExistente(area.getCodArea());
		areaBanco.setNomeArea(area.getNomeArea());
		areaSegurancaRepository.save(areaBanco);
		return areaBanco;
	}

	public NotificacaoAreaSegurancaTO getNotificacaoProblemaArea(int codAreaProblema) {
		AreaSegurancaProblema areaProblema = areaSegurancaProblemaService.validarProblemaSeguranca(codAreaProblema);
		AreaSeguranca area = this.validarAreaExistente(areaProblema.getArea().getCodArea());
		problemaRepository.findProblemaByCodProblema(areaProblema.getProblema().getCodProblema());
		Festa festa = festaService.validarFestaExistente(area.getCodFesta());
		return notificacaoAreaFactory.getNotificacaoArea(festa, areaProblema);
	}

	private void validarArea(AreaSeguranca area) {
		AreaSeguranca areaExistente = areaSegurancaRepository.findAreaByNomeArea(area.getNomeArea(),
				area.getCodFesta());
		if (areaExistente != null) {
			throw new ValidacaoException("NOMEAREA");// nome da area já está sendo usada naquela festa
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
