package com.eventmanager.pachanga.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eventmanager.pachanga.domains.QuestionarioForms;
import com.eventmanager.pachanga.dtos.QuestionarioFormsTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.QuestionarioFormsFactory;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.services.QuestionarioFormsService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = QuestionarioFormsController.class)
class QuestionarioFormsControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private QuestionarioFormsService questionarioFormsService;

	@MockBean
	private QuestionarioFormsFactory questionarioFormsFactory;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	private QuestionarioForms questionarioFormsTest() {
		QuestionarioForms questionario = new QuestionarioForms();
		questionario.setCodQuestionario(87);
		questionario.setNomeQuestionario("nomeTeste");
		questionario.setUrlQuestionario("urlTeste");
		return questionario;
	}

	private QuestionarioFormsTO questionarioFormsTOTest() {
		QuestionarioFormsTO questionarioTO = new QuestionarioFormsTO();
		questionarioTO.setCodQuestionario(87);
		questionarioTO.setCodFesta(3);
		questionarioTO.setNomeQuestionario("nomeTeste");
		questionarioTO.setUrlQuestionario("urlTeste");
		return questionarioTO;
	}
	
	@Test
	@WithMockUser
	void listaQuestionarioFormsSucesso() throws Exception {
		String uri = "/questionario/lista";

		List<QuestionarioForms> questionarios = new ArrayList<QuestionarioForms>();
		questionarios.add(questionarioFormsTest());

		String expected = "[{\"codQuestionario\":87,\"codFesta\":3,\"nomeQuestionario\":\"nomeTeste\",\"urlQuestionario\":\"urlTeste\"}]";

		Mockito.when(questionarioFormsService.listaQuestionariosForms(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(questionarios);
		Mockito.when(questionarioFormsFactory.getQuestionarioFormsTO(Mockito.any())).thenReturn(questionarioFormsTOTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	@WithMockUser
	void listaQuestionarioFormsErro() throws Exception {
		String uri = "/questionario/lista";

		String expected = "teste";

		Mockito.when(questionarioFormsService.listaQuestionariosForms(Mockito.anyInt(), Mockito.anyInt()))
				.thenThrow(new ValidacaoException("teste"));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser
	void adicionarQuestionarioFormsSucesso() throws Exception {
		String uri = "/questionario/adicionar";

		String expected = "{\"codQuestionario\":87,\"codFesta\":3,\"nomeQuestionario\":\"nomeTeste\",\"urlQuestionario\":\"urlTeste\"}";
		String json = "{\"codQuestionario\":87,\"codFesta\":3,\"nomeQuestionario\":\"nomeTeste\",\"urlQuestionario\":\"urlTeste\"}";

		Mockito.when(questionarioFormsService.adicionarQuestionario(Mockito.any(), Mockito.anyInt()))
				.thenReturn(questionarioFormsTest());
		Mockito.when(questionarioFormsFactory.getQuestionarioFormsTO(Mockito.any())).thenReturn(questionarioFormsTOTest());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri).param("codUsuario", "1").content(json)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}

	@Test
	@WithMockUser
	void adicionarQuestionarioFormsErro() throws Exception {
		String uri = "/questionario/adicionar";

		String expected = "teste";
		String json = "{\"codQuestionario\":1,\"codFesta\":2,\"nomeQuestionario\":\"nomeTeste\",\"urlQuestionario\":\"urlTest\"}";

		Mockito.when(questionarioFormsService.adicionarQuestionario(Mockito.any(), Mockito.anyInt()))
				.thenThrow(new ValidacaoException("teste"));
		Mockito.when(questionarioFormsFactory.getQuestionarioFormsTO(Mockito.any())).thenReturn(questionarioFormsTOTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(json).param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());
	}

	@Test
	@WithMockUser
	void atualizarQuestionarioFormsSucesso() throws Exception {
		String uri = "/questionario/atualizar";

		String expected = "{\"codQuestionario\":87,\"codFesta\":3,\"nomeQuestionario\":\"nomeTeste\",\"urlQuestionario\":\"urlTeste\"}";
		String json = "{\"codQuestionario\":87,\"codFesta\":3,\"nomeQuestionario\":\"nomeTeste\",\"urlQuestionario\":\"urlTeste\"}";

		Mockito.when(questionarioFormsService.atualizarQuestionario(Mockito.any(), Mockito.anyInt()))
				.thenReturn(questionarioFormsTest());
		Mockito.when(questionarioFormsFactory.getQuestionarioForms(Mockito.any(), Mockito.any())).thenReturn(questionarioFormsTest());
		Mockito.when(questionarioFormsFactory.getQuestionarioFormsTO(Mockito.any())).thenReturn(questionarioFormsTOTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON).content(json)
				.param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}

	@Test
	@WithMockUser
	void atualizarQuestionarioFormsErro() throws Exception {
		String uri = "/questionario/atualizar";

		String expected = "teste";
		String json = "{\"codQuestionario\":1,\"codFesta\":2,\"nomeQuestionario\":\"nomeTeste\",\"urlQuestionario\":\"urlTest\"}";

		Mockito.when(questionarioFormsService.atualizarQuestionario(Mockito.any(), Mockito.anyInt()))
				.thenThrow(new ValidacaoException("teste"));
		Mockito.when(questionarioFormsFactory.getQuestionarioForms(Mockito.any(), Mockito.any())).thenReturn(questionarioFormsTest());
		Mockito.when(questionarioFormsFactory.getQuestionarioFormsTO(Mockito.any())).thenReturn(questionarioFormsTOTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON).content(json)
				.param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser
	void removerQuestionarioFormsSucesso() throws Exception {
		String uri = "/questionario/remover";

		Mockito.doNothing().when(questionarioFormsService).removerQuestionario(Mockito.anyInt(), Mockito.anyInt());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(uri).accept(MediaType.APPLICATION_JSON)
				.param("codUsuario", "1").param("codQuestionario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	
	@Test
	@WithMockUser
	void removerQuestionarioFormsErro() throws Exception {
		String uri = "/questionario/remover";

		doThrow(new ValidacaoException("teste")).when(questionarioFormsService).removerQuestionario(Mockito.anyInt(), Mockito.anyInt());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(uri).accept(MediaType.APPLICATION_JSON)
				.param("codUsuario", "1").param("codQuestionario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
		assertEquals("teste", result.getResponse().getContentAsString());

	}

}
