package com.eventmanager.pachanga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.DadoBancario;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.dtos.DadoBancarioTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.DadoBancarioFactory;
import com.eventmanager.pachanga.repositories.DadoBancarioRepository;
import com.eventmanager.pachanga.tipo.TipoPermissao;

@Service
@Transactional
public class DadoBancarioService {

	@Autowired
	private DadoBancarioRepository dadoBancarioRepository;

	@Autowired
	private DadoBancarioFactory dadoBancarioFactory;

	@Autowired
	private FestaService festaService;

	@Autowired
	private GrupoService grupoService;

	public DadoBancario dadoBancarioUnico(int codFesta, int codUsuario) {
		festaService.validarFestaExistente(codFesta);
		festaService.validarUsuarioFesta(codUsuario, codFesta);
		grupoService.validarPermissaoUsuarioGrupo(codFesta, codUsuario, TipoPermissao.VISUDADO.getCodigo());

		return dadoBancarioRepository.findDadosBancariosFesta(codFesta);
	}

	public DadoBancario adicionarDadoBancario(DadoBancarioTO dadoBancarioTo, int codUsuario) {
		Festa festa = festaService.validarFestaExistente(dadoBancarioTo.getCodFesta());
		festaService.validarUsuarioFesta(codUsuario, dadoBancarioTo.getCodFesta());
		grupoService.validarPermissaoUsuarioGrupo(dadoBancarioTo.getCodFesta(), codUsuario,
				TipoPermissao.ADDDADOB.getCodigo());
		
		if(dadoBancarioRepository.findDadosBancariosFesta(dadoBancarioTo.getCodFesta()) != null) {
			throw new ValidacaoException("DADBEXIS");// dado bancario já existente para essa festa
		}

		DadoBancario dadoBancario = dadoBancarioFactory.getDadoBancario(dadoBancarioTo, festa);
		dadoBancario.setCodDadosBancario(dadoBancarioRepository.getNextValMySequence());

		dadoBancarioRepository.save(dadoBancario);
		return dadoBancario;
	}

	public void deleteDadoBancario(DadoBancarioTO dadoBancarioTo, int codUsuario) {
		festaService.validarUsuarioFesta(codUsuario, dadoBancarioTo.getCodFesta());
		grupoService.validarPermissaoUsuarioGrupo(dadoBancarioTo.getCodFesta(), codUsuario,
				TipoPermissao.DELEDADO.getCodigo());
		DadoBancario dadoBancario = this.validarDadoBancarioExistente(dadoBancarioTo.getCodDadosBancario());

		dadoBancarioRepository.delete(dadoBancario);
	}

	public DadoBancario validarDadoBancarioExistente(int codDadoBancario) {
		DadoBancario dadoBancario = dadoBancarioRepository.findDadoByCodBancario(codDadoBancario);
		if (dadoBancario == null) {
			throw new ValidacaoException("DADBNFOU");// dado bancário não encontrado
		}
		return dadoBancario;
	}

	public DadoBancario atualizarDadoBancario(DadoBancarioTO dadoBancarioTo, int codUsuario) {
		festaService.validarUsuarioFesta(codUsuario, dadoBancarioTo.getCodFesta());
		grupoService.validarPermissaoUsuarioGrupo(dadoBancarioTo.getCodFesta(), codUsuario,
				TipoPermissao.EDITDADO.getCodigo());
		DadoBancario dadoBancario = this.validarDadoBancarioExistente(dadoBancarioTo.getCodDadosBancario());
		dadoBancario.setCodAgencia(dadoBancarioTo.getCodAgencia());
		dadoBancario.setCodBanco(dadoBancarioTo.getCodBanco());
		dadoBancario.setCodConta(dadoBancarioTo.getCodConta());
		return dadoBancario;
	}
	
}
