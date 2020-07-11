package com.eventmanager.pachanga.services;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.builder.FestaBuilder;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.FestaGrupoUsuario;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.repositories.FestaGrupoUsuarioRepository;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;

@Service
@Transactional
public class FestaService {
	
	@Autowired
	private FestaRepository festaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private FestaGrupoUsuarioRepository festaGrupoUsuarioRepository;

	public List<Festa> procurarFestas(){
		return festaRepository.findFesta();
	}
	
	public List<Festa> procurarFestasPorUsuario(int idUser){
		Usuario usuario = usuarioRepository.findById(idUser);
		validarUsuarioFesta(usuario);
		return festaRepository.findByUsuarios(usuario);
	}
	
	public Festa addFesta(FestaTO festaTo, int idUser) {
		if(validacaoCriacaoFesta(festaTo)) {
			throw new ValidationException("FESTNOME");//outra festa está usando o msm nome
		}
		Usuario usuario = usuarioRepository.findById(idUser);
		validarUsuarioFesta(usuario);
		Grupo grupo = grupoRepository.findById(1);
		festaTo.setCodFesta(festaRepository.getNextValMySequence());
		Festa festa = criacaoFesta(festaTo);
		FestaGrupoUsuario festaGrupoUsuario = new FestaGrupoUsuario(grupo, festa, usuario);
		festaRepository.save(festa);
		festaGrupoUsuarioRepository.save(festaGrupoUsuario);
		return festa;
	}
	
	private void validarUsuarioFesta(Usuario usuario) {
		if(usuario == null) {
			throw new ValidationException("USERNFOU");
		}
	}
	
	private boolean validacaoCriacaoFesta(FestaTO festaTo) {
		Festa festa = festaRepository.findByNomeFesta(festaTo.getNomeFesta());
		return festa != null;
	}
	
	public void deleteFesta(int festaId) {
		festaRepository.deleteById(festaId);
	}
	
	private Festa criacaoFesta(FestaTO festaTo) {		
		return FestaBuilder.getInstance().codEnderecoFesta(festaTo.getCodEnderecoFesta()).codFesta(festaTo.getCodFesta()).
			   descOrganizador(festaTo.getDescOrganizador()).descricaoFesta(festaTo.getDescricaoFesta()).
			   horarioFimFesta(festaTo.getHorarioFimFesta()).horarioInicioFesta(festaTo.getHorarioInicioFesta()).
			   nomeFesta(festaTo.getNomeFesta()).organizador(festaTo.getOrganizador()).statusFesta(festaTo.getStatusFesta()).build();
	}
}
