package com.eventmanager.pachanga.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.pachanga.domains.Convidado;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.repositories.ConvidadoRepository;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.tipo.TipoNotificacao;
import com.eventmanager.pachanga.tipo.TipoPermissao;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;
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
	
	@Autowired
	private NotificacaoService notificacaoService;

	public StringBuilder addUsuariosFesta(List<String> emails, int codFesta, int idUsuario, int idGrupo) {
		StringBuilder mensagemRetorno = new StringBuilder();
		this.validarUsuario(idUsuario);
		Grupo grupo = this.validarGrupoFesta(idGrupo, codFesta, idUsuario);
		Festa festa = this.validarFesta(codFesta);
		for(String email : emails) {
			Convidado convidadoBanco = convidadoRepository.findByEmailByGrupo(email, idGrupo);
			if(convidadoBanco == null) {
				Usuario usuario = usuarioRepository.findByEmail(email);
				if(usuario == null) {
					emailMensagem.enviarEmail(email, grupo.getNomeGrupo(), festa);				
					mensagemRetorno.append(email);
					mensagemRetorno.append(" ");
				}
				Usuario usuarioFesta = usuarioRepository.findBycodFestaAndEmail(codFesta, email);
				if(usuarioFesta == null) {
					Convidado convidado = new Convidado(convidadoRepository.getNextValMySequence(), email);
					convidadoRepository.save(convidado);
					convidadoRepository.saveConvidadoGrupo(convidado.getCodConvidado(), idGrupo);
					notificacaoService.inserirNotificacaoConvidado(convidado.getCodConvidado(), TipoNotificacao.CONVFEST.getCodigo(), TipoNotificacao.CONVFEST.getValor() + "?" + idGrupo + "&" + convidado.getCodConvidado());
				}
			}
		}
		return mensagemRetorno;
	}
	
	public void aceitarConvite(Integer codConvidado, Integer idGrupo, String mensagem) {
		Convidado convidado = this.validarGrupoConvidado(codConvidado, idGrupo);
		Usuario usuario = this.validarUsuarioConvidado(convidado.getEmail());
		convidado.getGrupos().stream().forEach(g ->
			this.validarFesta(g.getFesta().getCodFesta())
		);
		grupoRepository.saveUsuarioGrupo(usuario.getCodUsuario(), idGrupo);
		convidadoRepository.deleteConvidadoGrupo(convidado.getCodConvidado(), idGrupo);
		notificacaoService.deletarNotificacaoConvidado(convidado.getCodConvidado(), TipoNotificacao.CONVFEST.getCodigo(), mensagem);
		convidadoRepository.deleteConvidado(convidado.getCodConvidado());
	}
	
	public void recusarConvite(Integer codConvidado, Integer idGrupo, String mensagem) {
		Convidado convidado = this.validarGrupoConvidado(codConvidado, idGrupo);
		Usuario usuario = this.validarUsuarioConvidado(convidado.getEmail());
		convidadoRepository.deleteConvidadoGrupo(convidado.getCodConvidado(), idGrupo);
		notificacaoService.deletarNotificacaoConvidado(codConvidado, TipoNotificacao.CONVFEST.getCodigo(), mensagem);
		convidadoRepository.deleteConvidado(convidado.getCodConvidado());
		notificacaoService.deleteNotificacao(usuario.getCodUsuario(), TipoNotificacao.CONVFEST.getCodigo(), mensagem);
	}
	
	private Usuario validarUsuarioConvidado(String email) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		if(usuario == null) {
			throw new ValidacaoException("USERNFOU");
		}
		return usuario;
	}

	private Convidado validarGrupoConvidado(int codConvidado, int idGrupo) {
		Convidado convidado = convidadoRepository.findConvidadoGrupo(codConvidado, idGrupo);
		if(convidado == null) {
			throw new ValidacaoException("CONVNGRU");// convidado nn relacionado ao grupo
		}
		return convidado;
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
	
	public List<Convidado> pegarConvidadosFesta(int codFesta){
		return convidadoRepository.findConvidadosByCodFesta(codFesta);
	}
	
	public List<Convidado> pegarConvidadosGrupo(int codGrupo){
		return convidadoRepository.findConvidadosNoGrupo(codGrupo);
	}

	private Usuario validarUsuario(int idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario);
		if(usuario == null) {
			throw new ValidacaoException("USERNFOU");
		}
		return usuario;
	}

	private Grupo validarGrupoFesta(int codGrupo, int codFesta, int idUsuario) {
		Grupo grupo = grupoRepository.findById(codGrupo);
		if(grupo == null) {
			throw new ValidacaoException("GRUPNFOU");// grupo não encontrado
		}
		if(grupo.getOrganizador()) {
			throw new ValidacaoException("GRUPORGN");//Grupo organizador não pode ser adicionado ninguém
		}
		this.validarPermissaoUsuario(codFesta, idUsuario);
		return grupo;
	}

	private Festa validarFesta(int codFesta) {
		Festa festa = festaRepository.findById(codFesta);
		if(festa == null) {
			throw new ValidacaoException("FESTNFOU");// festa não encontrado
		}
		if(!TipoStatusFesta.PREPARACAO.getValor().equals(festa.getStatusFesta())) {
			throw new ValidacaoException("FESTNPRE");// festa tem que estar em preparação para realizar essa ação
		}
		return festa;
	}

	private void validarPermissaoUsuario(int codFesta, int idUsuario) {
		Grupo grupo = grupoRepository.findGrupoPermissaoUsuario(codFesta, idUsuario, TipoPermissao.ADDMEMBE.getCodigo());
		if(grupo == null) {
			throw new ValidacaoException("USESPERM");// usuário sem permissão
		}
	}

}
