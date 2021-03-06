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
import com.eventmanager.pachanga.tipo.TipoNotificacao;
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
	private NotificacaoService notificacaoService;

	@Autowired
	private PermissaoRepository permissaoRepository;

	@Autowired
	private ConvidadoRepository convidadoRepository;

	// usuarios______**********************************____________________________________________________________________________________________________

	public Grupo addGrupoFesta(GrupoTO grupoTO, int idUsuario) {
		this.validarPermissaoUsuario(grupoTO.getCodFesta(), idUsuario, TipoPermissao.CREGRPER.getCodigo());
		this.verificarDuplicidadeGrupo(grupoTO.getCodFesta(), grupoTO.getNomeGrupo());
	
		return this.addGrupo(grupoTO.getCodFesta(), grupoTO.getNomeGrupo(), false, grupoTO.getPermissoes());
	}

	public Grupo addGrupo(int codFesta, String nomeGrupo, boolean organizador, List<Integer> permissoes) {
		Festa festa = this.validarFesta(codFesta);
		Grupo grupo = GrupoFactory.getGrupo(nomeGrupo, grupoRepository.getNextValMySequence(), festa, organizador);
		grupoRepository.save(grupo);
		if (organizador) {
			permissaoRepository.findAll()
					.forEach(p -> grupoRepository.saveGrupoPermissao(grupo.getCodGrupo(), p.getCodPermissao()));
		} else {
			permissaoRepository.findPermissoes(permissoes).stream().forEach(
					permissao -> grupoRepository.saveGrupoPermissao(grupo.getCodGrupo(), permissao.getCodPermissao()));
		}
		return grupo;
	}

	public Grupo getByIdGrupo(int idGrupo) {
		return validarGrupo(idGrupo);
	}

	public Grupo deleteGrupo(int idGrupo, int idUsuario) {

		Grupo grupo = this.validarGrupo(idGrupo);
		this.validarPermissaoUsuario(grupo.getFesta().getCodFesta(), idUsuario, TipoPermissao.DELGRPER.getCodigo());
		this.validarFesta(grupo.getFesta().getCodFesta());
		this.validarOrganizador(grupo);
		this.validarGrupoVazio(grupo);

		grupoRepository.deleteGrupo(grupo.getCodGrupo());
		permissaoRepository.deletePermissoesGrupo(grupo.getCodGrupo());
		return grupo;
	}
	
	public void deleteCascade(int idFesta, int idUser) {
		List<Integer> codGrupos = grupoRepository.findCodGruposFesta(idFesta);
		codGrupos.stream().forEach(g -> {
			List<Integer> codConvidados = convidadoRepository.findCodConvidadosNoGrupo(g);
			convidadoRepository.deleteAllConvidadosNotificacao(codConvidados);
			convidadoRepository.deleteAllConvidadosGrupo(g);
			convidadoRepository.deleteConvidados(codConvidados);
			List<Usuario> usuarios = usuarioRepository.findUsuariosPorGrupo(g);
			usuarios.stream().forEach(u -> {
				notificacaoService.deleteNotificacao(u.getCodUsuario(),
						notificacaoService.criacaoMensagemNotificacaoUsuarioConvidado(g, u.getCodUsuario(),
								TipoNotificacao.CONVACEI.getValor()));
				notificacaoService.deleteNotificacao(idUser, TipoNotificacao.ESTBAIXO.getValor() + "?" + idFesta);
				notificacaoService.deleteNotificacao(idUser, TipoNotificacao.STAALTER.getValor() + "?" + idFesta);
			});
			grupoRepository.deleteUsuariosGrupo(g);
		});
		grupoRepository.deletePermissoesGrupos(codGrupos);
		notificacaoService.deleteNotificacoesGrupos(codGrupos);
		grupoRepository.deleteByCodFesta(idFesta);
	}

	public Grupo atualizar(GrupoTO grupoTO, int idUsuario) {

		Festa festa = this.validarFesta(grupoTO.getCodFesta());
		Grupo grupo = validarGrupo(grupoTO.getCodGrupo());
		this.validarPermissaoUsuario(festa.getCodFesta(), idUsuario, TipoPermissao.EDIGRPER.getCodigo());
		this.verificarDuplicidadeGrupo(grupoTO.getCodFesta(), grupoTO.getNomeGrupo(), grupo.getCodGrupo());

		if (grupo.getOrganizador()) {
			throw new ValidacaoException("EDITORGN");
		}

		if (grupoTO.getNomeGrupo() != null && grupoTO.getNomeGrupo().length() >= 1) {
			grupo.setNomeGrupo(grupoTO.getNomeGrupo());
		}

		permissaoRepository.deletePermissoesGrupo(grupoTO.getCodGrupo());

		List<Integer> permissoes = grupoTO.getPermissoes();
		for (Integer permissao : permissoes) {
			grupoRepository.saveGrupoPermissao(grupoTO.getCodGrupo(), permissao);
		}

		grupoRepository.save(grupo);
		return grupo;
	}

	public void editUsuario(List<Integer> gruposId, Integer grupoIdAtual, Integer idUsuario,
			Integer idUsuarioPermissao) {
		Grupo grupo = grupoRepository.findByCod(grupoIdAtual);
		Festa festa = grupo.getFesta();
		this.validarPermissaoUsuarioGrupo(festa.getCodFesta(), idUsuarioPermissao, TipoPermissao.DISMEMBE.getCodigo());
		grupoRepository.deleteMembroGrupo(grupoIdAtual, idUsuario);
		List<Integer> grupos = grupoRepository.findGruposFestaIn(gruposId, festa.getCodFesta());
		grupos.removeAll(grupoRepository.findGruposUsuarioByGrupos(grupos, idUsuario));
		for (Integer grup : grupos) {
			grupoRepository.saveUsuarioGrupo(idUsuario, grup);
		}

	}

	public void editUsuarios(List<Integer> idUsuarios, Integer grupoId, Integer idUsuarioPermissao) {
		Grupo grupo = grupoRepository.findByCod(grupoId);
		Festa festa = grupo.getFesta();
		this.validarPermissaoUsuarioGrupo(festa.getCodFesta(), idUsuarioPermissao, TipoPermissao.DISMEMBE.getCodigo());
		grupoRepository.deleteAllMembrosGrupo(grupoId);

		for (Integer user : idUsuarios) {
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
		notificacaoService.deletarNotificacoesConvidado(idConvidado);
		grupoRepository.deleteConvidadoGrupo(idConvidado, idGrupo);
		convidadoRepository.deleteConvidado(idConvidado);
	}

	// permissão__________________________________________________________________________________________________________
	public void addPermissaoGrupo(int codPermissao, int codGrupo, int idUsuario) {
		Grupo grupo = this.validarGrupo(codGrupo);
		this.validarPermissaoUsuario(grupo.getFesta().getCodFesta(), idUsuario, TipoPermissao.EDIGRPER.getCodigo());
		this.validarPermissao(codPermissao);
		this.validarGrupo(codGrupo);
		if (this.grupoTemPermissao(codPermissao, codGrupo)) {
			throw new ValidacaoException("PERMDUPL");// permissao duplicada
		} else {
			grupoRepository.saveGrupoPermissao(codGrupo, codPermissao);
		}
	}

	public void deletePermissaoGrupo(int codPermissao, int codGrupo, int idUsuario) {
		Grupo grupo = this.validarGrupo(codGrupo);
		this.validarPermissaoUsuario(grupo.getFesta().getCodFesta(), idUsuario, TipoPermissao.EDIGRPER.getCodigo());
		this.validarPermissao(codPermissao);
		this.validarGrupo(codGrupo);

		if (this.grupoTemPermissao(codPermissao, codGrupo)) {
			grupoRepository.deleteGrupoPermissao(codGrupo, codPermissao);
		} else {
			throw new ValidacaoException("PERMINVA");// permissao invalida
		}
	}

	// listagens________________________________________________________________________________________________________

	public List<Grupo> procurarGruposPorUsuario(int idUser) {
		this.validarUsuario(idUser);
		return grupoRepository.findGruposUsuario(idUser);
	}

	public List<Grupo> procurarGruposPorFesta(int idFesta, int idUsuario) {
		this.validarPermissaoUsuario(idFesta, idUsuario, TipoPermissao.VISGRPER.getCodigo());
		this.validarFesta(idFesta);
		return grupoRepository.findGruposFesta(idFesta);
	}

	public List<Permissao> procurarPermissoesPorGrupo(int codGrupo) {
		return grupoRepository.findPermissoesPorGrupo(codGrupo);
	}

	public List<Usuario> procurarUsuariosPorGrupo(int codGrupo) {
		this.validarGrupo(codGrupo);

		return usuarioRepository.findUsuariosPorGrupo(codGrupo);
	}

	public Grupo procurarGrupoPorId(int idGrupo) {
		return grupoRepository.findById(idGrupo);
	}

	// validadores__________________________________________________________________________________________________________

	public boolean validarPermissaoUsuario(int codFesta, int idUsuario, int codPermissao) {
		List<Grupo> grupos = grupoRepository.findGrupoPermissaoUsuario(codFesta, idUsuario,
				codPermissao);
		if (grupos.isEmpty()) {
			throw new ValidacaoException("USESPERM");// usuário sem permissão
		} else {
			return true;
		}
	}

	public boolean validarPermissaoUsuarioGrupo(int codFesta, int idUsuario, int tipoPermissao) {
		List<Grupo> grupos = grupoRepository.findGrupoPermissaoUsuario(codFesta, idUsuario, tipoPermissao);
		if (grupos.isEmpty()) {
			throw new ValidacaoException("USESPERM");// usuário sem permissão
		} else {
			return true;
		}
	}

	private Usuario validarUsuario(int idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario);
		if (usuario == null) {
			throw new ValidacaoException("USERNFOU");
		}
		return usuario;
	}

	public Grupo validarGrupo(int codGrupo) {
		Grupo grupo = grupoRepository.findById(codGrupo);
		if (grupo == null) {
			throw new ValidacaoException("GRUPNFOU");
		}
		return grupo;
	}
	
	public void verificarDuplicidadeGrupo(int codFesta, String nomeGrupo) {
		List<Grupo> gruposPreExistentes = grupoRepository.findGruposDuplicados(codFesta, nomeGrupo);
		if (gruposPreExistentes != null && gruposPreExistentes.size() > 0) {
			throw new ValidacaoException("GRPEXIST");
		}
	}
	
	public void verificarDuplicidadeGrupo(int codFesta, String nomeGrupo, int codGrupo) {
		List<Grupo> gruposPreExistentes = grupoRepository.findGruposDuplicados(codFesta, nomeGrupo);
		if (gruposPreExistentes != null && gruposPreExistentes.size() > 0) {
			if(gruposPreExistentes.get(0).getCodGrupo() != codGrupo) {
				throw new ValidacaoException("GRPEXIST");	
			}
		}
	}

	public Festa validarFesta(int idFesta) {
		Festa festa = festaRepository.findById(idFesta);
		if (festa == null) {
			throw new ValidacaoException("FESTNFOU");
		}
		return festa;
	}

	private Permissao validarPermissao(int codPermissao) {
		Permissao permissao = permissaoRepository.findById(codPermissao);
		if (permissao == null) {
			throw new ValidacaoException("PERMNFOU");
		}
		return permissao;
	}

	private Grupo validarOrganizador(Grupo grupo) {
		if (grupo.getOrganizador()) {
			throw new ValidacaoException("GRPOORGN"); // grupo é organizador
		}
		return grupo;
	}

	private Grupo validarGrupoVazio(Grupo grupo) {
		if (grupo.getUsuarios().isEmpty()) {
			return grupo;
		} else {
			throw new ValidacaoException("GRPONVAZ"); // grupo não vazio
		}
	}

	private boolean grupoTemPermissao(int codPermissao, int codGrupo) {
		boolean retorno = false;
		List<Permissao> permissoes = grupoRepository.findPermissoesPorGrupo(codGrupo);

		for (Permissao permissao : permissoes) {
			if (permissao.getCodPermissao() == codPermissao) {
				retorno = true;
				break;
			}
		}
		return retorno;
	}

}
