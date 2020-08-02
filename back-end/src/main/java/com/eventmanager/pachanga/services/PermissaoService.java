package com.eventmanager.pachanga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Permissao;
import com.eventmanager.pachanga.dtos.PermissaoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.PermissaoFactory;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.PermissaoRepository;

@Service
@Transactional
public class PermissaoService {
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	
//CRUD________________________________________________________________________________________________	
	public Permissao addPermissao(PermissaoTO permissaoTO) {	
		Permissao permissao = PermissaoFactory.getPermissao(permissaoTO);
		permissao.setCodPermissao(permissaoRepository.getNextValMySequence());
		if(this.validarTipoPermissao(permissaoTO.getTipPermissao())) {
			permissaoRepository.save(permissao);
		}
		return permissao;
	}
	
	public Permissao getByIdPermissao(int codPermissao){
		return permissaoRepository.findById(codPermissao);
	}
	
	
	public Permissao deletarPermissao(int codPermissao) {	
		Permissao permissao = validarPermissao(codPermissao);
		List<Grupo> grupos = grupoRepository.findGruposPorPermissao(codPermissao);
		if(grupos == null || grupos.size() == 0) {
			permissaoRepository.delete(permissao);
		}else {
			throw new ValidacaoException("PERMEMUSO");
		}
		
		return permissao;
	}
	
	public Permissao alterar(PermissaoTO permissaoTO) {	
		Permissao permissao = validarPermissao(permissaoTO.getCodPermissao());
		
		if(permissao.getDescPermissao() != null && !permissao.getDescPermissao().isEmpty()) {
			permissao.setDescPermissao(permissaoTO.getDescPermissao());	
		}
		if(this.validarTipoPermissao(permissaoTO.getTipPermissao())) {
			permissao.setTipPermissao(permissaoTO.getTipPermissao());
		}
		
		permissaoRepository.save(permissao);
		return permissao;
	}
	
//Validadores________________________________________________________________________________
	private Permissao validarPermissao(int codPermissao) {
		Permissao permissao = permissaoRepository.findById(codPermissao);
		if(permissao == null){
			throw new ValidacaoException("PERMNFOU");
		}
		return permissao;
	}
	
	private boolean validarTipoPermissao(String tipo) {
		if(tipo.equals("E") || tipo.equals("S") || tipo.equals("C") || tipo.equals("F") || tipo.equals("R")) {
			return true;
		}
		throw new ValidacaoException("PERMIINVALID");
	}
	
}
