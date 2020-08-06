package com.eventmanager.pachanga.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.repositories.ConvidadoRepository;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.tipo.TipoPermissao;
import com.eventmanager.pachanga.utils.EmailMensagem;

@Service
@Transactional
public class ConvidadoService {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ConvidadoRepository convidadoRepository;
	
	@Autowired
	private FestaRepository festaRepository;
	
	@Autowired
	private EmailMensagem emailMensagem;
	
	public StringBuilder addUsuariosFesta(List<String> emails, int codFesta, int idUsuario, int idGrupo) {
		StringBuilder mensagemRetorno = new StringBuilder();
		this.validarUsuario(idUsuario);
		Grupo grupo = this.validarGrupoFesta(idGrupo, codFesta, idUsuario);
		for(String email : emails) {
			Usuario usuario = usuarioRepository.findByEmail(email);
			if(usuario != null) {
				convidadoRepository.saveUsuarioGrupo(usuario.getCodUsuario(), grupo.getCodGrupo());
			}else {
				emailMensagem.enviarEmail(email);				
				mensagemRetorno.append(email);
				mensagemRetorno.append(" ");
			}
		}
		return mensagemRetorno;
	}
	
	private Usuario validarUsuario(int idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario);
		if(usuario == null) {
			throw new ValidacaoException("USERNFOU");
		}
		return usuario;
	}

	private Grupo validarGrupoFesta(int codGrupo, int codFesta, int idUsuario) {
		Festa festa = festaRepository.findById(codFesta);
		if(festa == null) {
			throw new ValidacaoException("FESTNFOU");// festa não encontrado
		}
		Grupo grupo = grupoRepository.findById(codGrupo);
		if(grupo == null) {
			throw new ValidacaoException("GRUPNFOU");// grupo não encontrado
		}
		grupo = this.validarPermissaoUsuarioReturnGrupo(codFesta, idUsuario);
		return grupo;
	}
	
	public Grupo validarPermissaoUsuarioReturnGrupo(int codFesta, int idUsuario) {
		Grupo grupo = grupoRepository.findGrupoPermissaoUsuario(codFesta, idUsuario, TipoPermissao.ADDMEMBE.getCodigo());
		if(grupo == null) {
			throw new ValidacaoException("USESPERM");// usuário sem permissão
		}
		return grupo;
	}
}
