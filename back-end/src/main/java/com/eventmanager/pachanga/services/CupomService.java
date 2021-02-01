package com.eventmanager.pachanga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.Cupom;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.dtos.CupomTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.CupomFactory;
import com.eventmanager.pachanga.repositories.CupomRepository;
import com.eventmanager.pachanga.tipo.TipoDesconto;
import com.eventmanager.pachanga.tipo.TipoPermissao;

@Service
@Transactional
public class CupomService {

	@Autowired
	private CupomRepository cupomRepository;

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private FestaService festaService;

	@Autowired
	private CupomFactory cupomFactory;

	public Cupom getCupom(String nomeCupom, int codFesta) {
		return this.cupomRepository.findCuponsByNomeAndFesta(nomeCupom, codFesta);
	}

	public List<Cupom> getCuponsFesta(int codFesta, int idUser) {
		festaService.validarFestaExistente(codFesta);
		List<Cupom> cupons = cupomRepository.findCuponsFesta(codFesta);
		grupoService.validarPermissaoUsuarioGrupo(codFesta, idUser, TipoPermissao.VISUCUPM.getCodigo());
		return cupons;
	}

	public Cupom gerarCupom(CupomTO cupomTO, int idUser) {
		Festa festa = festaService.procurarFesta(cupomTO.getCodFesta(), idUser);
		grupoService.validarPermissaoUsuarioGrupo(festa.getCodFesta(), idUser, TipoPermissao.ADDCUPOM.getCodigo());
		this.validarCupom(cupomTO, false);
		Cupom cupom = cupomFactory.getCupom(cupomTO, festa);
		cupom.setCodCupom(cupomRepository.getNextValMySequence());
		cupomRepository.save(cupom);
		return cupom;
	}

	public void removeCupom(int codCupom, int idUser) {
		Cupom cupom = this.cupomExistente(codCupom);
		grupoService.validarPermissaoUsuarioGrupo(cupom.getFesta().getCodFesta(), idUser,
				TipoPermissao.DELECUPM.getCodigo());
		cupomRepository.delete(cupom);
	}

	public Cupom atualizarCupom(CupomTO cupomTO, int idUser) {
		Cupom cupom = this.cupomExistente(cupomTO.getCodCupom());
		String nomeCupomAntigo = cupom.getNomeCupom();
		grupoService.validarPermissaoUsuarioGrupo(cupom.getFesta().getCodFesta(), idUser,
				TipoPermissao.EDITCUPM.getCodigo());
		this.validarCupom(cupomTO, nomeCupomAntigo.equals(cupomTO.getNomeCupom()));
		cupom.setNomeCupom(cupomTO.getNomeCupom());
		cupom.setPrecoDesconto(cupomTO.getPrecoDesconto());
		cupom.setPorcentagemDesc(cupomTO.getPorcentagemDesc());
		cupom.setTipoDesconto(cupomTO.getTipoDesconto());
		cupomRepository.save(cupom);
		return cupom;
	}

	private Cupom cupomExistente(int codCupom) {
		Cupom cupom = cupomRepository.findCupomByCod(codCupom);
		if (cupom == null) {
			throw new ValidacaoException("CUPMNFOU");// cupom não encontrada
		}
		return cupom;
	}

	private void validarCupom(CupomTO cupom, boolean trocaNome) {
		String nomeCupom = cupom.getNomeCupom();
		Cupom cupomExistente = cupomRepository.findCuponsByNomeAndFesta(nomeCupom, cupom.getCodFesta());
		if (cupomExistente != null && !trocaNome)
			throw new ValidacaoException("CUPMDUPL");// cupom duplicado

		if (!cupom.getTipoDesconto().equals(TipoDesconto.PORCENTAGEM.getDescricao())
				&& !cupom.getTipoDesconto().equals(TipoDesconto.VALOR.getDescricao()))
			throw new ValidacaoException("TIPCUPIN");// tipo cupom incorreto

		if ((cupom.getTipoDesconto().equals(TipoDesconto.VALOR.getDescricao()) && cupom.getPrecoDesconto() <= 0)
				|| (cupom.getTipoDesconto().equals(TipoDesconto.PORCENTAGEM.getDescricao())
						&& (cupom.getPorcentagemDesc() <= 0 || cupom.getPorcentagemDesc() > 100))) {
			throw new ValidacaoException("PRECCUPI");// preço do cupom incorreto
		}
	}

}
