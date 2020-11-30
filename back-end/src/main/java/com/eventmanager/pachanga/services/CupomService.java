package com.eventmanager.pachanga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.Cupom;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.dtos.CupomTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.CupomFactory;
import com.eventmanager.pachanga.repositories.CupomRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.tipo.TipoPermissao;

@Service
@Transactional
public class CupomService {
	
	@Autowired
	private CupomRepository cupomRepository;
	
	@Autowired
	private GrupoRepository grupoRepository;
	 
	@Autowired
	private FestaService festaService;
	
	public Cupom getCupom(int codCupom, int idUser) {
		Cupom cupom = cupomRepository.findCupomByCod(codCupom); 
		this.validarPermissaoUsuario(cupom.getFesta().getCodFesta(), idUser, TipoPermissao.VISUCUPM.getCodigo());
		return cupom;
	}
	
	public List<Cupom> getCuponsFesta(int codFesta, int idUser) {
		List<Cupom> cupons = cupomRepository.findCuponsFesta(codFesta);
		this.validarPermissaoUsuario(codFesta, idUser, TipoPermissao.VISUCUPM.getCodigo());
		return cupons;
	}
	
	public Cupom gerarCupom(CupomTO cupomTO, int idUser) {
		Festa festa = festaService.procurarFesta(cupomTO.getCodFesta(), idUser);
		this.validarPermissaoUsuario(festa.getCodFesta(), idUser, TipoPermissao.ADDCUPOM.getCodigo());
		Cupom cupom = CupomFactory.getCupom(cupomTO, festa);
		this.validarCupom(cupom);
		cupom.setCodCupom(cupomRepository.getNextValMySequence());
		cupomRepository.save(cupom);
		return cupom;
	}
	
	public Cupom removeCupom(int codCupom, int idUser) {
		Cupom cupom = this.cupomExistente(codCupom);
		this.validarPermissaoUsuario(cupom.getFesta().getCodFesta(), idUser, TipoPermissao.DELECUPM.getCodigo());
		cupomRepository.delete(cupom);
		return cupom;
	}
	
	public Cupom atualizarCupom(CupomTO cupomTO, int idUser) {
		Cupom cupom = this.cupomExistente(cupomTO.getCodCupom());
		Festa festa = cupom.getFesta();
		this.validarPermissaoUsuario(festa.getCodFesta(), idUser, TipoPermissao.EDITCUPM.getCodigo());
		cupomRepository.save(cupom);
		return cupom;
	}
	
	private Cupom cupomExistente(int codCupom) {
		Cupom cupom = cupomRepository.findCupomByCod(codCupom);
		if(cupom == null) {
			throw new ValidacaoException("CUPMNFOU");// cupom não encontrada
		}
		return cupom;
	}
	
	private boolean validarPermissaoUsuario(int codFesta, int idUsuario, int codPermissao) {
		List<Grupo> grupos = grupoRepository.findGrupoPermissaoUsuario(codFesta, idUsuario, codPermissao);
		if (grupos.isEmpty()) {
			throw new ValidacaoException("USESPERM");// usuário sem permissão
		} else {
			return true;
		}
	}
	
	private boolean validarCupom(Cupom cupom) {
		String nomeCupom = cupom.getNomeCupom();
		int codFesta = cupom.getFesta().getCodFesta();
		List<Cupom> cupons = cupomRepository.findCuponsByNomeAndFesta(nomeCupom, codFesta);
		if(cupons != null && cupons.size() > 0) throw new ValidacaoException("CUPMDUPL");// cupom duplicado
		return true;
	}
	

}
