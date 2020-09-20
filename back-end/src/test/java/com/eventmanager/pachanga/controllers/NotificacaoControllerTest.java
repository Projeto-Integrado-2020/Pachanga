package com.eventmanager.pachanga.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.eventmanager.pachanga.PachangaApplication;
import com.eventmanager.pachanga.domains.Notificacao;
import com.eventmanager.pachanga.dtos.NotificacaoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.securingweb.OAuthHelper;
import com.eventmanager.pachanga.services.NotificacaoService;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = PachangaApplication.class)
@AutoConfigureMockMvc
class NotificacaoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private NotificacaoService notificacaoService;
	
	@Autowired
	private OAuthHelper authHelper;
	
	
	private Notificacao NotificacaoTest() {
		Notificacao notificacao = new Notificacao();
		notificacao.setCodNotificacao(1);
		notificacao.setDescNotificacao("teste");
		return notificacao;
	}
	
	@Test
	void listaNotificacaoSucesso() throws Exception {
		
		String uri = "/notificacao/lista";
		
		NotificacaoTO notificacaoTo = new NotificacaoTO();
		
		Mockito.when(notificacaoService.procurarNotificacaoUsuario(Mockito.anyInt())).thenReturn(notificacaoTo);
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.param("idUser", "1")
				.accept(MediaType.APPLICATION_JSON)
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	void listaNotificacaoError() throws Exception {
		
		String uri = "/notificacao/lista";
		
		List<Notificacao> notificacoes = new ArrayList<Notificacao>();
		notificacoes.add(NotificacaoTest());
		
		Mockito.when(notificacaoService.procurarNotificacaoUsuario(Mockito.anyInt())).thenThrow(new ValidacaoException("teste"));
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.param("idUser", "1")
				.accept(MediaType.APPLICATION_JSON)
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(400, response.getStatus());
		
		assertEquals("teste", result.getResponse().getContentAsString());
	}
	
	@Test
	void mudarStatusSucesso() throws Exception {
		
		String uri = "/notificacao/mudarStatus";
		
		String jsonContent = "[1]";
		
		Mockito.doNothing().when(notificacaoService).alterarStatus(Mockito.anyInt(),Mockito.anyInt());
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
				
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.param("idUser", "1")
				.content(jsonContent)
				.accept(MediaType.APPLICATION_JSON)
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	void mudarStatusError() throws Exception {
		
		String uri = "/notificacao/mudarStatus";
		
		String jsonContent = "[1]";
			
		Mockito.doThrow(new ValidacaoException("teste")).when(notificacaoService).alterarStatus(Mockito.anyInt(),Mockito.anyInt());
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
				
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.param("idUser", "1")
				.content(jsonContent)
				.accept(MediaType.APPLICATION_JSON)
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(400, response.getStatus());
		
		assertEquals("teste", result.getResponse().getContentAsString());
	}
	
	@Test
	void destaqueSucesso() throws Exception {
		
		String uri = "/notificacao/destaque";
		
		Mockito.doNothing().when(notificacaoService).destaqueNotificacao(Mockito.anyInt(),Mockito.anyInt());
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
				
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.param("idUser", "1")
				.param("idNotificacao", "1")
				.accept(MediaType.APPLICATION_JSON)
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	void destaqueError() throws Exception {
		
		String uri = "/notificacao/destaque";
		
			
		Mockito.doThrow(new ValidacaoException("teste")).when(notificacaoService).destaqueNotificacao(Mockito.anyInt(),Mockito.anyInt());
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
				
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.param("idUser", "1")
				.param("idNotificacao", "1")
				.accept(MediaType.APPLICATION_JSON)
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(400, response.getStatus());
		
		assertEquals("teste", result.getResponse().getContentAsString());
	}
	
	@Test
	void deleteNotificacoesSucesso() throws Exception {
		
		String uri = "/notificacao/delete";
		
		Mockito.doNothing().when(notificacaoService).deleteNotificacao(Mockito.anyInt(), Mockito.anyString());
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
				
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.param("idUser", "1")
				.param("mensagem", "teste")
				.accept(MediaType.APPLICATION_JSON)
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	void deleteNotificacoesError() throws Exception {
		
		String uri = "/notificacao/delete";
		
			
		Mockito.doThrow(new ValidacaoException("teste")).when(notificacaoService).deleteNotificacao(Mockito.anyInt(), Mockito.anyString());
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
				
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.param("idUser", "1")
				.param("mensagem", "teste")
				.accept(MediaType.APPLICATION_JSON)
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(400, response.getStatus());
		
		assertEquals("teste", result.getResponse().getContentAsString());
	}

}
