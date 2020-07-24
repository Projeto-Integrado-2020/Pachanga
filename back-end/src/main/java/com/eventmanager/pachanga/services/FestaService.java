package com.eventmanager.pachanga.services;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Permissao;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.FestaFactory;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.PermissaoRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.tipo.TipoGrupo;
import com.eventmanager.pachanga.tipo.TipoPermissao;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;

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
	private PermissaoRepository permissaoRepository;

	public List<Festa> procurarFestas(){
		return festaRepository.findAll();
	}

	public List<Festa> procurarFestasPorUsuario(int idUser){
		this.validarUsuarioFesta(idUser, 0);
		Usuario usuario = usuarioRepository.findById(idUser);
		return festaRepository.findByUsuarios(usuario.getCodUsuario());
	}

	public Festa addFesta(FestaTO festaTo, int idUser) {
		this.validarUsuarioFesta(idUser, 0);
		Usuario usuario = usuarioRepository.findById(idUser);
		festaTo.setCodFesta(festaRepository.getNextValMySequence());
		festaTo.setStatusFesta(TipoStatusFesta.PREPARACAO.getValor());
		validarFesta(festaTo);
		Festa festa =  FestaFactory.getFesta(festaTo);
		Grupo grupo = new Grupo(grupoRepository.getNextValMySequence(), festa, TipoGrupo.ORGANIZADOR.getValor(), 1);
		festaRepository.save(festa);
		grupoRepository.save(grupo);
		grupoRepository.saveUsuarioGrupo(usuario.getCodUsuario(), grupo.getCodGrupo());
		List<Permissao> permissoes = (List<Permissao>) permissaoRepository.findAll();
		permissoes.stream().forEach(p -> grupoRepository.saveGrupoPermissao(grupo.getCodGrupo(), p.getCodPermissao()));
		return festa;
	}

	private void validarUsuarioFesta(int idUsuario, int idFesta) {
		Usuario usuario = usuarioRepository.findById(idUsuario);
		if(usuario == null) {
			throw new ValidacaoException("USERNFOU");
		}
		if(idFesta != 0) {
			usuario = usuarioRepository.findBycodFestaAndUsuario(idFesta, idUsuario);
			if(usuario == null) {
				throw new ValidacaoException("USERNFES");// usuário não relacionado a festa
			}
		}
	}

	public void deleteFesta(int idFesta, int idUser) {
		validarPermissaoUsuario(idUser, idFesta, TipoPermissao.DELEFEST.getCodigo());
		List<Grupo> grupos = grupoRepository.findGruposFesta(idFesta);
		for(Grupo grupo : grupos) {
			grupoRepository.deleteUsuarioGrupo(grupo.getCodGrupo());
		}
		grupoRepository.deleteAll(grupos);
		festaRepository.deleteById(idFesta);
	}

	public Festa updateFesta(FestaTO festaTo, int idUser) {
		validarPermissaoUsuario(idUser, festaTo.getCodFesta(), TipoPermissao.EDITDFES.getCodigo());
		Festa festa = festaRepository.findById(festaTo.getCodFesta());
		if(festa == null) {
			throw new ValidacaoException("FESTNFOU");//festa nn encontrada
		}
		validarFesta(festaTo);
		festa = FestaFactory.getFesta(festaTo);
		festaRepository.save(festa);
		return festa;
	}

	private void validarPermissaoUsuario(int idUser, int idFesta, int codPermissao) {
		Grupo grupo = grupoRepository.findGrupoPermissaoUsuario(idFesta, idUser, codPermissao);
		if(grupo == null) {
			throw new ValidacaoException("USERSPER");//Usuário sem permissão de fazer essa ação
		}
	}

	private void validarFesta(FestaTO festaTo) {
		if(festaTo.getHorarioFimFesta().isBefore(festaTo.getHorarioInicioFesta()) || 
				Duration.between(festaTo.getHorarioInicioFesta(), festaTo.getHorarioFimFesta()).isZero()) {
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

	public Festa procurarFesta(int idFesta, int idUsuario) {
		this.validarUsuarioFesta(idUsuario, idFesta);
		return festaRepository.findByCodFesta(idFesta);
	}

	public String funcionalidadeFesta(int codFesta, int codUsuario) {
		return grupoRepository.findFuncionalidade(codFesta, codUsuario);
	}

	public Festa mudarStatusFesta(int idFesta, String statusFesta, int idUsuario) {
		String statusFestaMaiusculo = statusFesta.toUpperCase();
		if(!TipoStatusFesta.INICIADO.getValor().equals(statusFestaMaiusculo) && !TipoStatusFesta.FINALIZADO.getValor().equals(statusFestaMaiusculo) && !TipoStatusFesta.PREPARACAO.getValor().equals(statusFestaMaiusculo)) {
			throw new ValidacaoException("STATERRA"); //status errado
		}
		this.validarUsuarioFesta(idUsuario, idFesta);
		Festa festa = festaRepository.findByCodFesta(idFesta);
		if(statusFestaMaiusculo.equals(festa.getStatusFesta())) {
			throw new ValidacaoException("STANMUDA");// status não foi alterado
		}
		festaRepository.updateStatusFesta(statusFestaMaiusculo, idFesta);// para adicionar null no banco de dados
		festa.setStatusFesta(statusFestaMaiusculo);
		return festa;
	}
}
