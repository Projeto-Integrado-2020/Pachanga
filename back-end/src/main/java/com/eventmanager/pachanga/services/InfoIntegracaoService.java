package com.eventmanager.pachanga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.InfoIntegracao;
import com.eventmanager.pachanga.dtos.InfoIntegracaoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.InfoIntegracaoFactory;
import com.eventmanager.pachanga.repositories.InfoIntegracaoRepository;
import com.eventmanager.pachanga.tipo.TipoPermissao;
import com.eventmanager.pachanga.tipo.TipoTerceiroIntegracao;

@Service
@Transactional
public class InfoIntegracaoService {

	@Autowired
	private InfoIntegracaoRepository infoIntegracaoRepository;

	@Autowired
	private InfoIntegracaoFactory infoIntegracaoFactory;

	@Autowired
	private FestaService festaService;

	@Autowired
	private GrupoService grupoService;

	public List<InfoIntegracao> listaInfoIntegracaoFesta(int codFesta, int codUsuario) {
		festaService.validarFestaExistente(codFesta);
		festaService.validarUsuarioFesta(codUsuario, codFesta);
		grupoService.validarPermissaoUsuarioGrupo(codFesta, codUsuario, TipoPermissao.VISUINTE.getCodigo());
		return infoIntegracaoRepository.findAllInfosFesta(codFesta);
	}

	public InfoIntegracao infoIntegracaoFesta(InfoIntegracaoTO infoTo, int codUsuario) {
		festaService.validarFestaExistente(infoTo.getCodFesta());
		festaService.validarUsuarioFesta(codUsuario, infoTo.getCodFesta());
		grupoService.validarPermissaoUsuarioGrupo(infoTo.getCodFesta(), codUsuario, TipoPermissao.VISUINTE.getCodigo());
		this.validarTerceiroInt(infoTo.getTerceiroInt());
		return this.validaInfoExistente(infoTo);
	}

	private void validarTerceiroInt(String terceiroInt) {
		if (!TipoTerceiroIntegracao.SYMPLA.getValor().equals(terceiroInt)
				&& !TipoTerceiroIntegracao.EVENTIBRITE.getValor().equals(terceiroInt)) {
			throw new ValidacaoException("TERCINCO");// terceiro selecionado incorreto
		}
	}

	public InfoIntegracao adicionarinfoIntegracaoFesta(InfoIntegracaoTO infoTo, int codUsuario) {
		Festa festa = festaService.validarFestaExistente(infoTo.getCodFesta());
		festaService.validarUsuarioFesta(codUsuario, infoTo.getCodFesta());
		grupoService.validarPermissaoUsuarioGrupo(infoTo.getCodFesta(), codUsuario, TipoPermissao.ADDINTEG.getCodigo());
		this.validarTerceiroInt(infoTo.getTerceiroInt());
		this.validarInfo(infoTo);
		InfoIntegracao info = infoIntegracaoFactory.getInfoIntegracao(infoTo, festa);
		info.setCodInfo(infoIntegracaoRepository.getNextValMySequence());
		infoIntegracaoRepository.save(info);
		return info;
	}

	private void validarInfo(InfoIntegracaoTO infoTo) {
		if(infoIntegracaoRepository.findInfoFesta(infoTo.getCodFesta(), infoTo.getTerceiroInt()) != null) {
			throw new ValidacaoException("INFOTCAD");// info terceiro já cadastrada
		}
	}

	public InfoIntegracao atualizarinfoIntegracaoFesta(InfoIntegracaoTO infoTo, int codUsuario) {
		festaService.validarFestaExistente(infoTo.getCodFesta());
		festaService.validarUsuarioFesta(codUsuario, infoTo.getCodFesta());
		grupoService.validarPermissaoUsuarioGrupo(infoTo.getCodFesta(), codUsuario, TipoPermissao.EDITINTE.getCodigo());
		this.validarTerceiroInt(infoTo.getTerceiroInt());
		InfoIntegracao info = this.validaInfoExistente(infoTo);
		info.setToken(infoTo.getToken());
		info.setCodEvent(infoTo.getCodEvent());
		infoIntegracaoRepository.save(info);
		return info;
	}

	public void deleteinfoIntegracaoFesta(int codInfo, int codUsuario) {
		InfoIntegracao info = infoIntegracaoRepository.findByCodInfo(codInfo);
		festaService.validarFestaExistente(info.getFesta().getCodFesta());
		festaService.validarUsuarioFesta(codUsuario, info.getFesta().getCodFesta());
		grupoService.validarPermissaoUsuarioGrupo(info.getFesta().getCodFesta(), codUsuario, TipoPermissao.DELEINTE.getCodigo());
		infoIntegracaoRepository.delete(info);
	}
	
	public InfoIntegracao validaInfoExistente(InfoIntegracaoTO infoTo) {
		InfoIntegracao info = infoIntegracaoRepository.findInfoFesta(infoTo.getCodFesta(), infoTo.getTerceiroInt());
		if(info == null) {
			throw new ValidacaoException("INFONFOU");// informação da integração não encontrada
		}
		return info;
	}

}
