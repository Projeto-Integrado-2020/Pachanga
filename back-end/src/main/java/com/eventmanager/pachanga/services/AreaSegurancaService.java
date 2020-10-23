package com.eventmanager.pachanga.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.AreaSeguranca;
import com.eventmanager.pachanga.domains.AreaSegurancaProblema;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.repositories.AreaSegurancaProblemaRepository;
import com.eventmanager.pachanga.repositories.AreaSegurancaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.tipo.TipoAreaSeguranca;
import com.eventmanager.pachanga.tipo.TipoPermissao;

@Service
@Transactional
public class AreaSegurancaService {

	@Autowired
	private AreaSegurancaRepository areaSegurancaRepository;

	@Autowired
	private AreaSegurancaProblemaRepository areaSegurancaProblemaRepository;

	@Autowired
	private GrupoRepository grupoRepository;

	@Autowired
	private NotificacaoService notificacaoService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<AreaSeguranca> listaAreaSegurancaFesta(int codFesta, int codUsuario) {
		this.validarPermissaoUsuario(codUsuario, codFesta, TipoPermissao.VISUAREA.getCodigo());
		return areaSegurancaRepository.findAllAreasByCodFesta(codFesta);

	}

	public AreaSeguranca areaSegurancaFesta(int codFesta, int codArea, int codUsuario) {
		this.validarPermissaoUsuario(codUsuario, codFesta, TipoPermissao.VISUAREA.getCodigo());
		return areaSegurancaRepository.findAreaByCodFestaAndCodArea(codFesta, codArea);
	}

	public AreaSeguranca criacaoAreSegurancaFesta(int codUsuario, AreaSeguranca area) {
		this.validarPermissaoUsuario(codUsuario, area.getCodFesta(), TipoPermissao.ADDAREAS.getCodigo());
		area.setStatusSeguranca(TipoAreaSeguranca.SEGURO.getDescricao());
		area.setCodArea(areaSegurancaRepository.getNextValMySequence());
		this.validarArea(area);
		areaSegurancaRepository.salvarArea(area);
		return area;
	}

	public void deletarAreSegurancaFesta(int codUsuario, int codArea) {
		AreaSeguranca areaSeguranca = areaSegurancaRepository.findAreaByCodFesta(codArea);
		this.validarPermissaoUsuario(codUsuario, areaSeguranca.getCodFesta(), TipoPermissao.DELEAREA.getCodigo());
		List<AreaSegurancaProblema> areasSegurancasProblemas = areaSegurancaProblemaRepository
				.findProblemasArea(codArea);
		areaSegurancaProblemaRepository.deleteAll(areasSegurancasProblemas);
		areaSegurancaRepository.delete(areaSeguranca);
	}


	public AreaSeguranca atualizarAreSegurancaFesta(int codUsuario, AreaSeguranca area) {
		this.validarArea(area);
		this.validarPermissaoUsuario(codUsuario, area.getCodFesta(), TipoPermissao.EDITAREA.getCodigo());
		AreaSeguranca areaBanco = areaSegurancaRepository.findAreaCodArea(area.getCodArea());
		if(areaBanco == null) {
			throw new ValidacaoException("AREANFOU");// area não encontrada
		}
		areaSegurancaRepository.updateNomeArea(areaBanco.getCodArea(), area.getNomeArea());
		areaBanco.setNomeArea(area.getNomeArea());
		return areaBanco;
	}

	private void validarArea(AreaSeguranca area) {
		AreaSeguranca areaExistente = areaSegurancaRepository.findAreaByNomeArea(area.getNomeArea(),
				area.getCodFesta());
		if (areaExistente != null) {
			throw new ValidacaoException("NOMEAREA");// nome da area já está sendo usada naquela festa
		}
	}
	
	private void validarPermissaoUsuario(int idUsuario, int codFesta, int tipoPermissao) {
		List<Grupo> grupos = grupoRepository.findGrupoPermissaoUsuario(codFesta, idUsuario, tipoPermissao);
		if(grupos.isEmpty()) {
			throw new ValidacaoException("USESPERM");// usuário sem permissão
		}

	}

}
