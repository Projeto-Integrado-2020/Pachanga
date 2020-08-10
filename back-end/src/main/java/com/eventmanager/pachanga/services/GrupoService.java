package com.eventmanager.pachanga.services;

import java.util.ArrayList;
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

	//usuarios______**********************************____________________________________________________________________________________________________

	public Usuario editUsuarioFesta(List<Integer> gruposId, Integer grupoIdAtual, Integer idUsuario, Integer idUsuarioPermissao) {

		return null;
	}

	public Usuario editUsuariosFesta(List<Integer> gruposId, Integer idUsuario, Integer idUsuarioPermissao) {

		return null;
	}

	public List<Usuario> deleteUsuariosFesta(List<String> emails, int codFesta, int idUsuario, int idGrupo) {
		List<Usuario> retorno = new ArrayList<>();
		this.validarUsuario(idUsuario);
		Grupo grupo = this.validarGrupoFesta(idGrupo, codFesta, idUsuario);
		for(String email : emails) {
			Usuario usuario = usuarioRepository.findByEmail(email);
			if(usuario != null) {
				grupoRepository.deleteUsuarioGrupo(usuario.getCodUsuario(), grupo.getCodGrupo());
				retorno.add(usuario);
			}else {
				throw new ValidacaoException("USERNFOU - G" + idGrupo);
			}
		}
		return retorno;
	}


	public Grupo addGrupoFesta(GrupoTO grupoTO, int idUsuario) {

		Festa festa = this.validarFesta(grupoTO.getCodFesta());
		this.validarPermissaoUsuario(grupoTO.getCodFesta(), idUsuario);
		List<Grupo> gruposPreExistentes = grupoRepository.findGruposDuplicados(grupoTO.getCodFesta(), grupoTO.getNomeGrupo());

		if(gruposPreExistentes.isEmpty()) {

			grupoTO.setCodGrupo(grupoRepository.getNextValMySequence() + 1);
			Grupo grupoNew = GrupoFactory.getGrupo(grupoTO, festa);
			grupoRepository.save(grupoNew);
			List<Permissao> permissoes = permissaoRepository.findPermissoes(grupoTO.getPermissoes());
			for(Permissao permissao: permissoes) {
				grupoRepository.saveGrupoPermissao(grupoNew.getCodGrupo(), permissao.getCodPermissao());
			}
			return grupoNew;
		}else {
			throw new ValidacaoException("GRPEXIST");
		}
	}

	public Grupo getByIdGrupo(int idGrupo) {
		return validarGrupo(idGrupo);
	}

	public Grupo deleteGrupo(int idGrupo, int idUsuario) {

		Grupo grupo = this.validarGrupo(idGrupo);
		this.validarPermissaoUsuario(grupo.getFesta().getCodFesta(), idUsuario);  
		this.validarFesta(grupo.getFesta().getCodFesta());
		this.validarOrganizador(grupo);
		this.validarGrupoVazio(grupo);

		grupoRepository.deleteGrupo(grupo.getCodGrupo());
		permissaoRepository.deletePermissoesGrupo(grupo.getCodGrupo());
		return grupo;
	}

	public Grupo atualizar(GrupoTO grupoTO, int idUsuario) {

		Festa festa = this.validarFesta(grupoTO.getCodFesta());
		Grupo grupo = validarGrupo(grupoTO.getCodGrupo());
		this.validarPermissaoUsuario(festa.getCodFesta(), idUsuario);

		if(grupoTO.getNomeGrupo() != null || grupoTO.getNomeGrupo().length() >= 1) {
			grupo.setNomeGrupo(grupoTO.getNomeGrupo());
		}
		if(grupoTO.getQuantMaxPessoas() >= 1 && grupoTO.getQuantMaxPessoas() >= this.procurarUsuariosPorGrupo(grupoTO.getCodGrupo()).size()){
			grupo.setQuantMaxPessoas(grupoTO.getQuantMaxPessoas());
		}

		permissaoRepository.deletePermissoesGrupo(grupoTO.getCodGrupo());

		List<Integer> permissoes = grupoTO.getPermissoes();
		for(Integer permissao: permissoes) {
			grupoRepository.saveGrupoPermissao(grupoTO.getCodGrupo(), permissao);
		}

		grupoRepository.save(grupo);
		return grupo;
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
			throw new ValidacaoException("PERMINVA");// permissao invalida
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

	public List<Permissao> procurarPermissoesPorGrupo(int codGrupo){
		return grupoRepository.findPermissoesPorGrupo(codGrupo);
	}

	public List<Usuario> procurarUsuariosPorGrupo(int codGrupo){
		this.validarGrupo(codGrupo);

		return grupoRepository.findUsuariosPorGrupo(codGrupo);
	}

	public Grupo procurarGrupoPorId(int idGrupo){
		return grupoRepository.findById(idGrupo);
	}


	//validadores__________________________________________________________________________________________________________	


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

	public Grupo validarGrupo(int codGrupo) {
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

	private Grupo validarOrganizador(Grupo grupo) {
		if(grupo.getOrganizador()) {
			throw new ValidacaoException("USERORGN"); // usuário é organizador
		}
		return grupo;
	}

	private Grupo validarGrupoVazio(Grupo grupo) {
		if(grupo.getUsuarios().isEmpty()) {
			return grupo;
		}else {
			throw new ValidacaoException("GRPONVAZ"); //grupo não vazio
		}
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
