package com.eventmanager.pachanga.services;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Ingresso;
import com.eventmanager.pachanga.domains.Lote;
import com.eventmanager.pachanga.dtos.LoteTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.LoteFactory;
import com.eventmanager.pachanga.repositories.IngressoRepository;
import com.eventmanager.pachanga.repositories.LoteRepository;
import com.eventmanager.pachanga.tipo.TipoPermissao;

@Service
@Transactional
public class LoteService {

	@Autowired
	private LoteRepository loteRepository;

	@Autowired
	private FestaService festaService;

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private LoteFactory loteFactory;

	@Autowired
	private IngressoRepository ingressoRepository;

	public List<Lote> listaLoteFesta(int codFesta, int codUsuario) {
		festaService.validarFestaExistente(codFesta);
		festaService.validarUsuarioFesta(codUsuario, codFesta);
		grupoService.validarPermissaoUsuarioGrupo(codFesta, codUsuario, TipoPermissao.VISULOTE.getCodigo());
		return loteRepository.listaLoteFesta(codFesta);
	}
	
	public List<Lote> listarLotesFestaDadosPublicos(int codFesta) {
		festaService.validarFestaExistente(codFesta);
		return loteRepository.findAllCompraveisFesta(codFesta);
	} 
	
	public Lote encontrarLote(int codLote, int codUsuario) {
		Lote lote = this.validarLoteExistente(codLote);
		festaService.validarFestaExistente(lote.getFesta().getCodFesta());
		festaService.validarUsuarioFesta(codUsuario, lote.getFesta().getCodFesta());
		grupoService.validarPermissaoUsuarioGrupo(lote.getFesta().getCodFesta(), codUsuario, TipoPermissao.VISULOTE.getCodigo());
		return lote;
	}
	
	public Lote encontrarLoteDadosPublicos(int codLote) {
		Lote lote = this.validarLoteExistente(codLote);
		return lote;
	}
	
	public List<Lote> encontrarLotesCompraveisFesta(int codFesta) {
		List<Lote> lotes = loteRepository.findAllCompraveisFesta(codFesta);
		return lotes;
	}

	public Lote adicionarLote(LoteTO loteTo, int codUsuario) {
		Festa festa = festaService.validarFestaExistente(loteTo.getCodFesta());
		festaService.validarUsuarioFesta(codUsuario, festa.getCodFesta());
		grupoService.validarPermissaoUsuarioGrupo(festa.getCodFesta(), codUsuario, TipoPermissao.ADDLOTES.getCodigo());
		if (loteRepository.findByNomeLote(loteTo.getNomeLote(), loteTo.getCodFesta()) != null) {
			throw new ValidacaoException("NOMELOTE");// nome do lote já usado por outro lote da festa
		}
		this.validarLote(loteTo);
		Lote lote = loteFactory.getLote(loteTo, festa);
		lote.setCodLote(loteRepository.getNextValMySequence());
		loteRepository.save(lote);
		return lote;
	}

	public Lote atualizarLote(LoteTO loteTo, int codUsuario) {
		Festa festa = festaService.validarFestaExistente(loteTo.getCodFesta());
		festaService.validarUsuarioFesta(codUsuario, festa.getCodFesta());
		grupoService.validarPermissaoUsuarioGrupo(festa.getCodFesta(), codUsuario, TipoPermissao.EDITLOTE.getCodigo());
		Lote lote = this.validarLoteExistente(loteTo.getCodLote());
		Lote loteNome = loteRepository.findByNomeLote(loteTo.getNomeLote(), loteTo.getCodFesta());
		if ( loteNome != null && loteNome.getCodLote() != loteTo.getCodLote()) {
			throw new ValidacaoException("NOMELOTE");// nome do lote já usado por outro lote da festa
		}
		this.validarLote(loteTo);
		lote.setDescLote(loteTo.getDescLote());
		lote.setNomeLote(loteTo.getNomeLote());
		lote.setPreco(loteTo.getPreco());
		lote.setQuantidade(loteTo.getQuantidade());
		lote.setNumeroLote(loteTo.getNumeroLote());
		lote.setHorarioInicio(loteTo.getHorarioInicio());
		lote.setHorarioFim(loteTo.getHorarioFim());
		loteRepository.save(lote);
		return lote;
	}

	public void removerLote(int codLote, int codUsuario) {
		Lote lote = this.validarLoteExistente(codLote);
		festaService.validarUsuarioFesta(codUsuario, lote.getFesta().getCodFesta());
		grupoService.validarPermissaoUsuarioGrupo(lote.getFesta().getCodFesta(), codUsuario,
				TipoPermissao.DELELOTE.getCodigo());
		List<Ingresso> ingressos = ingressoRepository.findIngressosLote(codLote);
		if (!ingressos.isEmpty()) {
			throw new ValidacaoException("LOTEVING");// lote já vendeu alguns ingressos
		}
		loteRepository.delete(lote);
	}

	private void validarLote(LoteTO loteTo) {
		if (loteTo.getPreco() < 0.0f) {
			throw new ValidacaoException("PREILOTE");// preço incorreto
		} else if (loteTo.getQuantidade() <= 0) {
			throw new ValidacaoException("QUAILOTE");// quantidade do lote incorreta
		}
		
		if (loteTo.getHorarioFim().isBefore(loteTo.getHorarioInicio())
				|| Duration.between(loteTo.getHorarioInicio(), loteTo.getHorarioFim()).isZero()) {
			throw new ValidacaoException("DATEINFE");// data final menor que inicial
		}
	}

	public Lote validarLoteExistente(int codLote) {
		Lote lote = loteRepository.findByCodLote(codLote);
		if (lote == null) {
			throw new ValidacaoException("LOTENFOU");// lote não encontrada
		}
		return lote;
	}

}
