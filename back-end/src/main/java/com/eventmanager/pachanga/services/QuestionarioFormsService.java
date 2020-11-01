package com.eventmanager.pachanga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.QuestionarioForms;
import com.eventmanager.pachanga.dtos.QuestionarioFormsTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.QuestionarioFormsFactory;
import com.eventmanager.pachanga.repositories.QuestionarioFormsRepository;
import com.eventmanager.pachanga.tipo.TipoPermissao;

@Service
@Transactional
public class QuestionarioFormsService {
	
	@Autowired
	private QuestionarioFormsRepository questionarioFormsRepository;

	@Autowired
	private FestaService festaService;

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private QuestionarioFormsFactory questionarioFormsFactory;
	
	public List<QuestionarioForms> listaQuestionariosForms(int codFesta, int codUsuario) {
		festaService.validarFestaExistente(codFesta);
		festaService.validarUsuarioFesta(codUsuario, codFesta);
		grupoService.validarPermissaoUsuarioGrupo(codFesta, codUsuario, TipoPermissao.VISUFORM.getCodigo());
		return questionarioFormsRepository.listaQuestionarioFormsFesta(codFesta);
	}
	
	public QuestionarioForms adicionarQuestionario(QuestionarioFormsTO questionarioFormsTO, int codUsuario) {
		Festa festa = festaService.validarFestaExistente(questionarioFormsTO.getCodFesta());
		festaService.validarUsuarioFesta(codUsuario, festa.getCodFesta());
		grupoService.validarPermissaoUsuarioGrupo(festa.getCodFesta(), codUsuario, TipoPermissao.ADDFORMS.getCodigo());
		if (questionarioFormsRepository.findByNome(questionarioFormsTO.getNomeQuestionario(), questionarioFormsTO.getCodFesta()) != null) {
			throw new ValidacaoException("NOMEFORM");// nome do questionario já usado por outro questionario da festa
		}
		if (questionarioFormsRepository.findByUrl(questionarioFormsTO.getUrlQuestionario(), questionarioFormsTO.getCodFesta()) != null) {
			throw new ValidacaoException("URLFORMS");// url do questionario já usada por outro questionario da festa
		}
		QuestionarioForms questionarioForms = questionarioFormsFactory.getQuestionarioForms(questionarioFormsTO, festa);
		questionarioForms.setCodQuestionario(questionarioFormsRepository.getNextValMySequence());
		questionarioFormsRepository.save(questionarioForms);
		return questionarioForms;
	}
	
	public QuestionarioForms atualizarQuestionario(QuestionarioFormsTO questionarioFormsTO, int codUsuario) {
		Festa festa = festaService.validarFestaExistente(questionarioFormsTO.getCodFesta());
		festaService.validarUsuarioFesta(codUsuario, festa.getCodFesta());
		grupoService.validarPermissaoUsuarioGrupo(festa.getCodFesta(), codUsuario, TipoPermissao.EDITFORM.getCodigo());
		QuestionarioForms questionarioForms = this.validarQuestionarioExistente(questionarioFormsTO.getCodQuestionario());
		QuestionarioForms nomeExistente = questionarioFormsRepository.findByNome(questionarioFormsTO.getNomeQuestionario(), questionarioFormsTO.getCodFesta());
		if (nomeExistente != null && nomeExistente.getCodQuestionario() != questionarioFormsTO.getCodQuestionario()) {
			throw new ValidacaoException("NOMEFORM");// nome do questionario já usado por outro questionario da festa
		}
		QuestionarioForms urlExistente = questionarioFormsRepository.findByUrl(questionarioFormsTO.getUrlQuestionario(), questionarioFormsTO.getCodFesta());
		if (urlExistente != null && urlExistente.getCodQuestionario() != questionarioFormsTO.getCodQuestionario()) {
			throw new ValidacaoException("URLFORMS");// nome do questionario já usado por outro questionario da festa
		}
		questionarioForms.setNomeQuestionario(questionarioFormsTO.getNomeQuestionario());
		questionarioForms.setUrlQuestionario(questionarioFormsTO.getUrlQuestionario());
		questionarioFormsRepository.save(questionarioForms);
		return questionarioForms;
	}
	
	public void removerQuestionario(int codQuestionario, int codUsuario) {
		QuestionarioForms questionarioForms = this.validarQuestionarioExistente(codQuestionario);
		grupoService.validarPermissaoUsuarioGrupo(questionarioForms.getFesta().getCodFesta(), codUsuario,
				TipoPermissao.DELEFORM.getCodigo());
		questionarioFormsRepository.delete(questionarioForms);
	}
	
	public QuestionarioForms validarQuestionarioExistente(int codQuestionario) {
		QuestionarioForms questionarioForms = questionarioFormsRepository.findByCodQuestionario(codQuestionario);
		if (questionarioForms == null) {
			throw new ValidacaoException("FORMNFOU");// questionario não encontrada
		}
		return questionarioForms;
	}

}
