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
import com.eventmanager.pachanga.repositories.ConvidadoRepository;
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

	@Autowired
	private ConvidadoRepository convidadoRepository;

	//usuarios______**********************************____________________________________________________________________________________________________

	public Grupo addGrupoFesta(GrupoTO grupoTO, int idUsuario) {
		this.validarPermissaoUsuario(grupoTO.getCodFesta(), idUsuario);
		List<Grupo> gruposPreExistentes = grupoRepository.findGruposDuplicados(grupoTO.getCodFesta(), grupoTO.getNomeGrupo());

		if(gruposPreExistentes.isEmpty()) {
			return this.addGrupo(grupoTO.getCodFesta(), grupoTO.getNomeGrupo(), false, grupoTO.getPermissoes());
		}else {
			throw new ValidacaoException("GRPEXIST");
		}
	}

	public Grupo addGrupo(int codFesta, String nomeGrupo, boolean organizador, List<Integer> permissoes) {
		Festa festa = this.validarFesta(codFesta);
		Grupo grupo = GrupoFactory.getGrupo(nomeGrupo,grupoRepository.getNextValMySequence(), festa, organizador);
		grupoRepository.save(grupo);
		if(organizador) {
			permissaoRepository.findAll().forEach(p -> grupoRepository.saveGrupoPermissao(grupo.getCodGrupo(), p.getCodPermissao()));
		} else {
			permissaoRepository.findPermissoes(permissoes).stream().forEach(permissao -> 
			grupoRepository.saveGrupoPermissao(grupo.getCodGrupo(), permissao.getCodPermissao())				
					);
		}
		return grupo;
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

		if(grupo.getOrganizador()) {
			throw new ValidacaoException("EDITORGN");
		}

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

	public void editUsuario(List<Integer> gruposId, Integer grupoIdAtual, Integer idUsuario, Integer idUsuarioPermissao) {
		Grupo grupo = grupoRepository.findByCod(grupoIdAtual);
		Festa festa = grupo.getFesta();
		this.validarPermissaoUsuarioGrupo(festa.getCodFesta(), idUsuarioPermissao, TipoPermissao.DISMEMBE.getCodigo());
		grupoRepository.deleteMembroGrupo(grupoIdAtual, idUsuario);
		List<Integer> grupos = grupoRepository.findGruposFestaIn(gruposId, festa.getCodFesta());
		grupos.removeAll(grupoRepository.findGruposUsuarioByGrupos(grupos, idUsuario));
		for(Integer grup : grupos) {
			grupoRepository.saveUsuarioGrupo(idUsuario, grup);
		}

	}

	public void editUsuarios(List<Integer> idUsuarios, Integer grupoId, Integer idUsuarioPermissao) {
		Grupo grupo = grupoRepository.findByCod(grupoId);
		Festa festa = grupo.getFesta();
		this.validarPermissaoUsuarioGrupo(festa.getCodFesta(), idUsuarioPermissao, TipoPermissao.DISMEMBE.getCodigo());
		grupoRepository.deleteAllMembrosGrupo(grupoId);

		for(Integer user : idUsuarios) {
			grupoRepository.saveUsuarioGrupo(user, grupoId);
		}
	}

	public void deleteMembro(Integer idMembro, Integer idGrupo, Integer idUsuarioPermissao) {
		Grupo grupo = grupoRepository.findByCod(idGrupo);
		Festa festa = grupo.getFesta();
		this.validarPermissaoUsuarioGrupo(festa.getCodFesta(), idUsuarioPermissao, TipoPermissao.DELMEMBE.getCodigo());
		grupoRepository.deleteUsuarioGrupo(idMembro, idGrupo);
	}

	public void deleteConvidado(Integer idConvidado, Integer idGrupo, Integer idUsuarioPermissao) {
		Grupo grupo = grupoRepository.findByCod(idGrupo);
		Festa festa = grupo.getFesta();
		this.validarPermissaoUsuarioGrupo(festa.getCodFesta(), idUsuarioPermissao, TipoPermissao.DELMEMBE.getCodigo());
		grupoRepository.deleteConvidadoGrupo(idConvidado, idGrupo);
		Integer convxgrup = grupoRepository.existsConvidadoGrupo(idConvidado);
		if(convxgrup == null) {
			convidadoRepository.deleteConvidado(idConvidado);
		}		
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
	
	public boolean validarPermissaoUsuarioGrupo(int codFesta, int idUsuario, int tipoPermissao) {
		Grupo grupo = grupoRepository.findGrupoPermissaoUsuario(codFesta, idUsuario, tipoPermissao);
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
			throw new ValidacaoException("GRPOORGN"); // grupo é organizador
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
