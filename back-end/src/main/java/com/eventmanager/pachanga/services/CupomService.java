package com.eventmanager.pachanga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.Cupom;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.dtos.CupomTO;
import com.eventmanager.pachanga.factory.CupomFactory;
import com.eventmanager.pachanga.repositories.CupomRepository;

@Service
@Transactional
public class CupomService {
	
	@Autowired
	private CupomRepository CupomRepository;
	
	@Autowired
	private FestaService festaService;
	
	public Cupom getCupom(String codCupom, int idUser) {
		Cupom cupom = CupomRepository.findCupomByCod(codCupom); 
		festaService.validarUsuarioFesta(idUser, cupom.getFesta().getCodFesta());
		return cupom;
	}
	
	public List<Cupom> getCuponsFesta(int codFesta, int idUser) {
		List<Cupom> cupons = CupomRepository.findCuponsFesta(codFesta);
		festaService.validarUsuarioFesta(idUser, codFesta);
		return cupons;
	}
	
	public Cupom gerarCupom(CupomTO cupomTO, int idUser) {
		Festa festa = festaService.procurarFesta(cupomTO.getCodFesta(), idUser);
		festaService.validarUsuarioFesta(idUser, festa.getCodFesta());
		Cupom cupom = CupomFactory.getCupom(cupomTO, festa);
		cupom.setCodCupom(CupomRepository.getNextValMySequence());
		CupomRepository.save(cupom);
		return cupom;
	}
	
	public Cupom removeCupom(String codCupom, int idUser) {
		Cupom cupom = CupomRepository.findCupomByCod(codCupom);
		festaService.validarUsuarioFesta(idUser, cupom.getFesta().getCodFesta());
		CupomRepository.delete(cupom);
		return cupom;
	}
	
	public Cupom atualizarCupom(CupomTO cupomTO, int idUser) {
		Cupom cupom = CupomRepository.findCupomByCod(cupomTO.getCodCupom());
		Festa festa = cupom.getFesta();
		festaService.validarUsuarioFesta(idUser, festa.getCodFesta());
		cupom = CupomFactory.getCupom(cupomTO, festa);
		CupomRepository.save(cupom);
		return cupom;
	}
	
	
	

}
