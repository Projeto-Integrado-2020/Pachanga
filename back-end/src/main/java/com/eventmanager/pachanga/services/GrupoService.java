package com.eventmanager.pachanga.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Permissao;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.GrupoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.GrupoFactory;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.PermissaoRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.tipo.TipoPermissao;

@Service
@Transactional
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private FestaRepository festaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	//@Autowired
	//private EmailMensagem emailMensagem;

//usuarios__________________________________________________________________________________________________________
	public Usuario addUsuarioFesta(int codGrupo, int idUsuario) {
		this.validarGrupo(codGrupo);
		Usuario usuario = this.validarUsuario(idUsuario);
		
		if(grupoRepository.findUsuarioNoGrupo(codGrupo, idUsuario) == null) {
			grupoRepository.saveUsuarioGrupo(idUsuario, codGrupo);
		}else {
			throw new ValidacaoException("USERATRB");
		}
		
		return usuario;
	}
	
	public Usuario removeUsuarioFesta(int codGrupo, int idUsuario) {
		this.validarGrupo(codGrupo);
		Usuario usuario = this.validarUsuario(idUsuario);
		
		if(grupoRepository.findUsuarioNoGrupo(codGrupo, idUsuario) != null) {
			grupoRepository.deleteUsuarioGrupo(idUsuario, codGrupo);
		}else {
			throw new ValidacaoException("USERNFOU");
		}
		
		return usuario;
	}

//grupo CRUD__________________________________________________________________________________________________________
	public Grupo addGrupoFesta(GrupoTO grupoTO, int idUsuario) {
		Festa festa = this.validarFesta(grupoTO.getCodFesta());
		this.validarPermissaoUsuario(grupoTO.getCodFesta(), idUsuario);
		List<Grupo> gruposPreExistentes = grupoRepository.findGruposDuplicados(grupoTO.getCodFesta(), grupoTO.getNomeGrupo());
		
		if(gruposPreExistentes == null || gruposPreExistentes .size() == 0) {
			grupoTO.setCodGrupo(grupoRepository.getNextValMySequence() + 1);
			Grupo grupoNew = GrupoFactory.getGrupo(grupoTO, festa);
			grupoRepository.save(grupoNew);
			return grupoNew;
		}
		return null;
	}
	
	public Grupo getByIdGrupo(int idGrupo) {
		Grupo grupo = validarGrupo(idGrupo);   
		return grupo;
	}
	
	public Grupo deleteGrupo(int idGrupo, int idUsuario) {
		Grupo grupo = validarGrupo(idGrupo);   
		Festa festa = this.validarFesta(grupo.getFesta().getCodFesta());
		this.validarPermissaoUsuario(festa.getCodFesta(), idUsuario);
		
		List<Usuario> usuarios = procurarUsuariosPorGrupo(idGrupo);
		for(Usuario usuario : usuarios) {
			grupoRepository.deleteUsuarioGrupo(usuario.getCodUsuario(), idGrupo);
		}

		grupoRepository.delete(grupo);
		return grupo;
	}
	
	public Grupo atualizar(GrupoTO grupoTO, int idUsuario) {
		Festa festa = this.validarFesta(grupoTO.getCodFesta());
		Grupo grupo = validarGrupo(grupoTO.getCodGrupo());
		this.validarPermissaoUsuario(festa.getCodFesta(), idUsuario);
		
		if(grupo != null) {
			if(grupoTO.getNomeGrupo() != null || grupoTO.getNomeGrupo().length() >= 1) {
				grupo.setNomeGrupo(grupoTO.getNomeGrupo());
			}
			if(grupoTO.getQuantMaxPessoas() >= 1 && grupoTO.getQuantMaxPessoas() >= this.procurarUsuariosPorGrupo(grupoTO.getCodGrupo()).size()){
				grupo.setQuantMaxPessoas(grupoTO.getQuantMaxPessoas());
			}
			//if(grupoTO.getCodFesta() > 0 && festaTO.getCodFesta() != grupo.getFesta().getCodFesta()){
			//	grupo.setFesta(festaTO);
			//}
			
			grupoRepository.save(grupo);
			return grupo;
		}
		return null;
	}
	
//permissão__________________________________________________________________________________________________________
	public void addPermissaoGrupo(int codPermissao, int codGrupo) {
		this.validarPermissao(codPermissao);
		this.validarGrupo(codGrupo);
		if(this.grupoTemPermissao(codPermissao, codGrupo)) {
			throw new ValidacaoException("PERMDUPL");// permissao duplicada
		}else {
			grupoRepository.saveGrupoPermissao(codGrupo, codPermissao);
		}
	}
	
	public void deletePermissaoGrupo(int codPermissao, int codGrupo) {
		this.validarPermissao(codPermissao);
		this.validarGrupo(codGrupo);
		
		if(this.grupoTemPermissao(codPermissao, codGrupo)) {
			grupoRepository.deleteGrupoPermissao(codGrupo, codPermissao);
		}else {
			throw new ValidacaoException("PERMINVALD");// permissao invalida
		}
	}
	
//listagens________________________________________________________________________________________________________
	
	public List<Grupo> procurarGruposPorUsuario(int idUser){
		this.validarUsuario(idUser);
		return grupoRepository.findGruposUsuario(idUser);
	}
	
	public List<Grupo> procurarGruposPorFesta(int idFesta){
		this.validarFesta(idFesta);
		return grupoRepository.findGruposFesta(idFesta);
	}
	
	//public List<Grupo> procurarGruposPorPermissao(int codPermissao){
	//	return grupoRepository.findGruposPorPermissao(codPermissao);
	//}
	
	public List<Permissao> procurarPermissoesPorGrupo(int codGrupo){
		return grupoRepository.findPermissoesPorGrupo(codGrupo);
	}
	
	public List<Usuario> procurarUsuariosPorGrupo(int codGrupo){
		this.validarGrupo(codGrupo);
		List<Usuario> usuarios = grupoRepository.findUsuariosPorGrupo(codGrupo);
		
		return usuarios;
	}
	
	public Grupo procurarGrupoPorId(int idGrupo){
		return grupoRepository.findById(idGrupo);
	}
	
	
//validadores__________________________________________________________________________________________________________	

	
//	public List<Grupo> procurarGruposPorUsuario(int idUser){
//		this.validarUsuario(idUser);
//		return grupoRepository.findGruposUsuario(idUser);
//	}
	
	private Grupo validarGrupoFesta(int codGrupo, int codFesta, int idUsuario) {
		Festa festa = festaRepository.findById(codFesta);
		if(festa == null) {
			throw new ValidacaoException("FESTNFOU");// festa não encontrado
		}
		Grupo grupo = grupoRepository.findById(codGrupo);
		if(grupo == null) {
			throw new ValidacaoException("GRUPNFOU");// grupo não encontrado
		}
		grupo = this.validarPermissaoUsuarioG(codFesta, idUsuario);
		return grupo;
	}
	
	public Grupo validarPermissaoUsuarioG(int codFesta, int idUsuario) {
		Grupo grupo = grupoRepository.findGrupoPermissaoUsuario(codFesta, idUsuario, TipoPermissao.ADDMEMBE.getCodigo());
		if(grupo == null) {
			throw new ValidacaoException("USESPERM");// usuário sem permissão
		}
		return grupo;
	}
	
	public boolean validarPermissaoUsuario(int codFesta, int idUsuario) {
		Grupo grupo = grupoRepository.findGrupoPermissaoUsuario(codFesta, idUsuario, TipoPermissao.ADDMEMBE.getCodigo());
		if(grupo == null) {
			throw new ValidacaoException("USESPERM");// usuário sem permissão
		}else {
			return true;
		}
	}
	
	private Usuario validarUsuario(int idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario);
		if(usuario == null) {
			throw new ValidacaoException("USERNFOU");
		}
		return usuario;
	}
	
	private Grupo validarGrupo(int codGrupo) {
		Grupo grupo = grupoRepository.findById(codGrupo);
		if(grupo == null) {
			throw new ValidacaoException("GRUPNFOU");
		}
		return grupo;
	}
	
	public Festa validarFesta(int idFesta) {
		Festa festa = festaRepository.findById(idFesta);
		if(festa == null) {
			throw new ValidacaoException("FESTNFOU");
		}
		return festa;
	}
	
	private Permissao validarPermissao(int codPermissao) {
		Permissao permissao = permissaoRepository.findById(codPermissao);
		if(permissao == null) {
			throw new ValidacaoException("PERMNFOU");
		}
		return permissao;
	}
	
	private boolean grupoTemPermissao(int codPermissao, int codGrupo) {
		boolean retorno = false;
		List<Permissao> permissoes = grupoRepository.findPermissoesPorGrupo(codGrupo);
		
		for(Permissao permissao : permissoes) {
			if(permissao.getCodPermissao() == codPermissao) {
				retorno = true;
				break;
			}
		}
		return retorno;
	}

}
