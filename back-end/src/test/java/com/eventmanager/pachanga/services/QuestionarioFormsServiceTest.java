package com.eventmanager.pachanga.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.QuestionarioForms;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.QuestionarioFormsTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.QuestionarioFormsFactory;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.QuestionarioFormsRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=QuestionarioFormsService.class)
public class QuestionarioFormsServiceTest {
	
	@Autowired
	private QuestionarioFormsService questionarioFormsService;
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	@MockBean
	private QuestionarioFormsRepository questionarioFormsRepository;

	@MockBean
	private FestaRepository festaRepository;

	@MockBean
	private FestaService festaService;
	
	@MockBean
	private GrupoService grupoService;
	
	@MockBean
	private UsuarioRepository usuarioRepository;
	
	@MockBean
	private GrupoRepository grupoRepository;
	
	@MockBean
	private QuestionarioFormsFactory questionarioFormsFactory;
	
	private QuestionarioForms questionarioForms() {
		QuestionarioForms questionario = new QuestionarioForms();
		questionario.setCodQuestionario(87);
		Festa festa = new Festa();
		festa.setCodFesta(87);
		questionario.setFesta(festa);
		return questionario;
	}
	
	private QuestionarioFormsTO questionarioFormsTO() {
		QuestionarioFormsTO questionarioTO = new QuestionarioFormsTO();
		questionarioTO.setCodQuestionario(87);
		questionarioTO.setCodFesta(3);
		questionarioTO.setNomeQuestionario("nomeTeste");
		questionarioTO.setUrlQuestionario("urlTeste");
		return questionarioTO;
	}
	
	private Festa festa() {
		Festa festa = new Festa();
		festa.setCodFesta(3);
		return festa;
	}
	
	@Test
	void listaQuestionariosForms() {
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(new Festa());
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(new Usuario());
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new Usuario());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(new ArrayList<Grupo>());
		Mockito.when(questionarioFormsRepository.listaQuestionarioFormsFesta(Mockito.anyInt())).thenReturn(new ArrayList<QuestionarioForms>());
		
		List<QuestionarioForms> questionarios = questionarioFormsService.listaQuestionariosForms(1, 1);
		
		assertEquals(true, questionarios.isEmpty());
	}
	
	@Test
	void adicionarQuestionarioSucesso() {
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa());
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festa());
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(new Usuario());
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new Usuario());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(new ArrayList<Grupo>());
		Mockito.when(questionarioFormsRepository.findByNome(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(questionarioFormsRepository.findByUrl(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(questionarioFormsRepository.getNextValMySequence()).thenReturn(87);
		Mockito.when(questionarioFormsFactory.getQuestionarioForms(Mockito.any(), Mockito.any())).thenReturn(questionarioForms());
		
		QuestionarioForms questionario = questionarioFormsService.adicionarQuestionario(questionarioFormsTO(), 1);
		
		assertEquals(true, questionario.getCodQuestionario() == 87);
	}
	
	@Test
	void adicionarQuestionarioErroNome() {
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa());
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festa());
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(new Usuario());
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new Usuario());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(new ArrayList<Grupo>());
		Mockito.when(questionarioFormsRepository.findByNome(Mockito.anyString(), Mockito.anyInt())).thenReturn(new QuestionarioForms());
		Mockito.when(questionarioFormsRepository.findByUrl(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(questionarioFormsRepository.getNextValMySequence()).thenReturn(87);
		Mockito.when(questionarioFormsFactory.getQuestionarioForms(Mockito.any(), Mockito.any())).thenReturn(questionarioForms());
		
		String expect = "";
		
		try {
			questionarioFormsService.adicionarQuestionario(questionarioFormsTO(), 1);			
		} catch (ValidacaoException e) {
			expect = e.getMessage();
		}
		
		assertEquals("NOMEFORM",expect);
	}
	
	@Test
	void adicionarQuestionarioErroUrl() {
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa());
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festa());
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(new Usuario());
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new Usuario());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(new ArrayList<Grupo>());
		Mockito.when(questionarioFormsRepository.findByNome(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(questionarioFormsRepository.findByUrl(Mockito.anyString(), Mockito.anyInt())).thenReturn(new QuestionarioForms());
		Mockito.when(questionarioFormsRepository.getNextValMySequence()).thenReturn(87);
		Mockito.when(questionarioFormsFactory.getQuestionarioForms(Mockito.any(), Mockito.any())).thenReturn(questionarioForms());
		
		String expect = "";
		
		try {
			questionarioFormsService.adicionarQuestionario(questionarioFormsTO(), 1);			
		} catch (ValidacaoException e) {
			expect = e.getMessage();
		}
		
		assertEquals("URLFORMS",expect);
	}
	
	@Test
	void atualizarQuestionarioSucesso() {
		QuestionarioForms questionario = questionarioForms();
		
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa());
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festa());
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(new Usuario());
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new Usuario());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(new ArrayList<Grupo>());
		Mockito.when(questionarioFormsRepository.findByNome(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(questionarioFormsRepository.findByUrl(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(questionarioFormsRepository.findByCodQuestionario(Mockito.anyInt())).thenReturn(questionario);
		Mockito.when(questionarioFormsFactory.getQuestionarioForms(Mockito.any(), Mockito.any())).thenReturn(questionarioForms());
		
		QuestionarioForms questionarioRetorno = questionarioFormsService.atualizarQuestionario(questionarioFormsTO(), 1);
		
		assertEquals(true, questionarioRetorno.getNomeQuestionario().equals(questionario.getNomeQuestionario()));
	}
	
	@Test
	void atualizarQuestionarioErroNome() {
		QuestionarioForms questionario = questionarioForms();
		
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa());
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festa());
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(new Usuario());
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new Usuario());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(new ArrayList<Grupo>());
		Mockito.when(questionarioFormsRepository.findByNome(Mockito.anyString(), Mockito.anyInt())).thenReturn(new QuestionarioForms());
		Mockito.when(questionarioFormsRepository.findByUrl(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(questionarioFormsRepository.findByCodQuestionario(Mockito.anyInt())).thenReturn(questionario);
		Mockito.when(questionarioFormsFactory.getQuestionarioForms(Mockito.any(), Mockito.any())).thenReturn(questionarioForms());
		
		String expect = "";
		
		try {
			questionarioFormsService.atualizarQuestionario(questionarioFormsTO(), 1);			
		} catch (ValidacaoException e) {
			expect = e.getMessage();
		}
		
		assertEquals("NOMEFORM",expect);
	}
	
	@Test
	void atualizarQuestionarioErroUrl() {
		QuestionarioForms questionario = questionarioForms();
		
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa());
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festa());
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(new Usuario());
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new Usuario());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(new ArrayList<Grupo>());
		Mockito.when(questionarioFormsRepository.findByNome(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(questionarioFormsRepository.findByUrl(Mockito.anyString(), Mockito.anyInt())).thenReturn(new QuestionarioForms());
		Mockito.when(questionarioFormsRepository.findByCodQuestionario(Mockito.anyInt())).thenReturn(questionario);
		Mockito.when(questionarioFormsFactory.getQuestionarioForms(Mockito.any(), Mockito.any())).thenReturn(questionarioForms());
		
		String expect = "";
		
		try {
			questionarioFormsService.atualizarQuestionario(questionarioFormsTO(), 1);			
		} catch (ValidacaoException e) {
			expect = e.getMessage();
		}
		
		assertEquals("URLFORMS",expect);
	}
	
	@Test
	void removerQuestionario() {
		QuestionarioForms questionario = questionarioForms();
		
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(new ArrayList<Grupo>());
		Mockito.when(questionarioFormsRepository.findByCodQuestionario(Mockito.anyInt())).thenReturn(questionario);
		
		questionarioFormsService.removerQuestionario(1, 1);
	}
	
	@Test
	void validarQuestionarioExistente() {
		Mockito.when(questionarioFormsRepository.findByCodQuestionario(Mockito.anyInt())).thenReturn(null);
		
		String expect = "";
		
		try {
			questionarioFormsService.validarQuestionarioExistente(1);
		} catch (ValidacaoException e) {
			expect = e.getMessage();
		}
		
		assertEquals("FORMNFOU",expect);
	}
	
}
