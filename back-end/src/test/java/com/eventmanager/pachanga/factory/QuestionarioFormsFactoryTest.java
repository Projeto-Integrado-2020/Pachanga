package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.QuestionarioForms;
import com.eventmanager.pachanga.dtos.QuestionarioFormsTO;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=QuestionarioFormsFactory.class)
public class QuestionarioFormsFactoryTest {
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@Autowired
	private QuestionarioFormsFactory questionarioFormsFactory;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	private QuestionarioForms questionarioFormsTest() {
		QuestionarioForms questionarioForms = new QuestionarioForms();
		questionarioForms.setCodQuestionario(1);
		questionarioForms.setNomeQuestionario("nomeTeste");
		questionarioForms.setUrlQuestionario("urlTeste");
		Festa festa = new Festa();
		festa.setCodFesta(87);
		questionarioForms.setFesta(festa);
		return questionarioForms;
	}

	private QuestionarioFormsTO questionarioFormsTOTest() {
		QuestionarioFormsTO questionarioFormsTO = new QuestionarioFormsTO();
		questionarioFormsTO.setCodQuestionario(1);
		questionarioFormsTO.setCodFesta(87);
		questionarioFormsTO.setNomeQuestionario("nomeTeste");
		questionarioFormsTO.setUrlQuestionario("urlTeste");
		return questionarioFormsTO;
	}
	
	@Test
	void getQuestionarioFormsToTest() {
		
		QuestionarioForms questionarioForms = questionarioFormsTest();
		
		QuestionarioFormsTO questionarioFormsTO = questionarioFormsFactory.getQuestionarioFormsTO(questionarioForms);
		
		assertEquals( questionarioFormsTO.getCodQuestionario(), questionarioForms.getCodQuestionario());
		assertEquals( questionarioFormsTO.getCodFesta(), questionarioForms.getFesta().getCodFesta());
		
	}
	
	@Test
	void getQuestionarioFormsTest() {
		
		QuestionarioFormsTO questionarioFormsTO = questionarioFormsTOTest();
		
		Festa festa = new Festa();
		festa.setCodFesta(87);
		
		QuestionarioForms questionarioForms = questionarioFormsFactory.getQuestionarioForms(questionarioFormsTO, festa);
		
		assertEquals( questionarioFormsTO.getCodQuestionario(), questionarioForms.getCodQuestionario());
		assertEquals( questionarioFormsTO.getCodFesta(), questionarioForms.getFesta().getCodFesta());
		
	}

}
