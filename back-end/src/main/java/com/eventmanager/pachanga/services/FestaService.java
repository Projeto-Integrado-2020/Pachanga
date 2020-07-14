package com.eventmanager.pachanga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.pachanga.builder.FestaBuilder;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;

@Service
public class FestaService {

	@Autowired
	private FestaRepository festaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private GrupoRepository grupoRepository;

	public List<Festa> procurarFestas(){
		return festaRepository.findAll();
	}

	public List<Festa> procurarFestasPorUsuario(int idUser){
		Usuario usuario = usuarioRepository.findById(idUser);
		validarUsuarioFesta(usuario);
		return festaRepository.findByUsuarios(usuario.getCodUsuario());
	}

	public Festa addFesta(FestaTO festaTo, int idUser) {
		Usuario usuario = usuarioRepository.findById(idUser);
		validarUsuarioFesta(usuario);
		festaTo.setCodFesta(festaRepository.getNextValMySequence());
		validarFesta(festaTo);
		Festa festa = criacaoFesta(festaTo);
		Grupo grupo = new Grupo(grupoRepository.getNextValMySequence(), "ORGANIZADOR", 1);
		festaRepository.save(festa);
		grupoRepository.save(grupo);
		grupoRepository.saveUsuarioGrupo(usuario.getCodUsuario(), grupo.getCodGrupo());
		return festa;
	}

	private void validarUsuarioFesta(Usuario usuario) {
		if(usuario == null) {
			throw new ValidacaoException("USERNFOU");
		}
	}

	public void deleteFesta(int idFesta, int idUser) {
		validarPermissaoUsuario(idUser, idFesta);
		List<Grupo> grupos = grupoRepository.findGruposFesta(idFesta);
		for(Grupo grupo : grupos) {
			grupoRepository.deleteUsuarioGrupo(grupo.getCodGrupo());
		}
		grupoRepository.deleteAll(grupos);
		festaRepository.deleteById(idFesta);
	}

	public Festa updateFesta(FestaTO festaTo, int idUser) {
		validarPermissaoUsuario(idUser, festaTo.getCodFesta());
		Festa festa = festaRepository.findById(festaTo.getCodFesta());
		if(festa == null) {
			throw new ValidacaoException("FESTNFOU");//festa nn encontrada
		}
		validarFesta(festaTo);
		festa = criacaoFesta(festaTo);
		festaRepository.save(festa);
		return festa;
	}

	private void validarPermissaoUsuario(int idUser, int idFesta) {
		Festa festa = festaRepository.findFestaByUsuarioResponsavel(idUser, idFesta);
		if(festa == null) {
			throw new ValidacaoException("USERSPER");//Usuário sem permissão de fazer essa ação
		}
	}

	private void validarFesta(FestaTO festaTo) {
		if(festaTo.getHorarioFimFesta().isBefore(festaTo.getHorarioInicioFesta())) {
			throw new ValidacaoException("DATEINFE");//data inicial ou final incorreta
		}
		Festa festa = festaRepository.findByNomeFesta(festaTo.getNomeFesta());
		if(festa != null && festa.getCodFesta() != festaTo.getCodFesta() && festa.getNomeFesta().equals(festaTo.getNomeFesta())) {
			throw new ValidacaoException("FESTNOME");//outra festa está usando o msm nome 
		}
		if(festaTo.getCodEnderecoFesta() == null) {
			throw new ValidacaoException("FESTNEND");//Festa sem código de endereço
		}
	}
	
	public Festa procurarFesta(int idFesta) {
		Festa festa = festaRepository.findByCodFesta(idFesta);
		if(festa == null) {
			throw new ValidacaoException("FESTNFOU");//festa nn encontrada
		}
		return festa;
	}

	private Festa criacaoFesta(FestaTO festaTo) {		
		return FestaBuilder.getInstance().codEnderecoFesta(festaTo.getCodEnderecoFesta()).codFesta(festaTo.getCodFesta()).
				descOrganizador(festaTo.getDescOrganizador()).descricaoFesta(festaTo.getDescricaoFesta()).
				horarioFimFesta(festaTo.getHorarioFimFesta()).horarioInicioFesta(festaTo.getHorarioInicioFesta()).
				nomeFesta(festaTo.getNomeFesta()).organizador(festaTo.getOrganizador()).statusFesta(festaTo.getStatusFesta()).build();
	}
}
