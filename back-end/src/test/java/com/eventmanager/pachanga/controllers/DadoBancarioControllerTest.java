package com.eventmanager.pachanga.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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

import com.eventmanager.pachanga.domains.DadoBancario;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.dtos.DadoBancarioTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.DadoBancarioFactory;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.services.DadoBancarioService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = DadoBancarioController.class)
class DadoBancarioControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	@MockBean
	private DadoBancarioFactory dadoBancarioFactory;

	@MockBean
	private DadoBancarioService dadoBancarioService;

	public DadoBancario dadoBancarioTest() {
		DadoBancario dado = new DadoBancario();
		dado.setCodAgencia(1);
		dado.setCodBanco("abc");
		dado.setCodConta(1);
		dado.setCodDadosBancario(1);
		dado.setFesta(new Festa());
		return dado;
	}

	public DadoBancarioTO dadoBancarioTOTest() {
		DadoBancarioTO dado = new DadoBancarioTO();
		dado.setCodAgencia(1);
		dado.setCodBanco("abc");
		dado.setCodConta(1);
		dado.setCodDadosBancario(1);
		dado.setCodFesta(1);
		return dado;
	}

	@Test
	@WithMockUser
	void getDadoBancarioTOSucesso() throws Exception {
		String uri = "/dadoBancario/dadoUnico";

		Mockito.when(dadoBancarioService.dadoBancarioUnico(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(dadoBancarioTest());
		Mockito.when(dadoBancarioFactory.getDadoBancarioTO(Mockito.any())).thenReturn(dadoBancarioTOTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	@WithMockUser
	void getDadoBancarioTOErro() throws Exception {
		String uri = "/dadoBancario/dadoUnico";

		Mockito.when(dadoBancarioService.dadoBancarioUnico(Mockito.anyInt(), Mockito.anyInt()))
				.thenThrow(new ValidacaoException("teste"));
		Mockito.when(dadoBancarioFactory.getDadoBancarioTO(Mockito.any())).thenReturn(dadoBancarioTOTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}
	
	@Test
	@WithMockUser
	void adicionarDadoBancarioSucesso() throws Exception {
		String uri = "/dadoBancario/adicionar";
		
		String json = "{\"codDadosBancario\":1,\"codFesta\":8,\"codBanco\":\"teste\",\"codAgencia\":123,\"codConta\":12}";

		Mockito.when(dadoBancarioService.adicionarDadoBancario(Mockito.any(), Mockito.anyInt()))
				.thenReturn(dadoBancarioTest());
		Mockito.when(dadoBancarioFactory.getDadoBancarioTO(Mockito.any())).thenReturn(dadoBancarioTOTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(json).param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	@WithMockUser
	void adicionarDadoBancarioErro() throws Exception {
		String uri = "/dadoBancario/adicionar";
		
		String json = "{\"codDadosBancario\":1,\"codFesta\":8,\"codBanco\":\"teste\",\"codAgencia\":123,\"codConta\":12}";

		Mockito.when(dadoBancarioService.adicionarDadoBancario(Mockito.any(), Mockito.anyInt()))
				.thenThrow(new ValidacaoException("teste"));
		Mockito.when(dadoBancarioFactory.getDadoBancarioTO(Mockito.any())).thenReturn(dadoBancarioTOTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(json).param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}
	
	@Test
	@WithMockUser
	void atualizarDadoBancarioSucesso() throws Exception {
		String uri = "/dadoBancario/atualizar";
		
		String json = "{\"codDadosBancario\":1,\"codFesta\":8,\"codBanco\":\"teste\",\"codAgencia\":123,\"codConta\":12}";

		Mockito.when(dadoBancarioService.atualizarDadoBancario(Mockito.any(), Mockito.anyInt()))
				.thenReturn(dadoBancarioTest());
		Mockito.when(dadoBancarioFactory.getDadoBancarioTO(Mockito.any())).thenReturn(dadoBancarioTOTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON)
				.content(json).param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	@WithMockUser
	void atualizarDadoBancarioErro() throws Exception {
		String uri = "/dadoBancario/atualizar";
		
		String json = "{\"codDadosBancario\":1,\"codFesta\":8,\"codBanco\":\"teste\",\"codAgencia\":123,\"codConta\":12}";

		Mockito.when(dadoBancarioService.atualizarDadoBancario(Mockito.any(), Mockito.anyInt()))
				.thenThrow(new ValidacaoException("teste"));
		Mockito.when(dadoBancarioFactory.getDadoBancarioTO(Mockito.any())).thenReturn(dadoBancarioTOTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON)
				.content(json).param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}
	
	@Test
	@WithMockUser
	void deletarDadoBancarioSucesso() throws Exception {
		String uri = "/dadoBancario/deletar";
		
		String json = "{\"codDadosBancario\":1,\"codFesta\":8,\"codBanco\":\"teste\",\"codAgencia\":123,\"codConta\":12}";

		Mockito.doNothing().when(dadoBancarioService).deleteDadoBancario(Mockito.any(), Mockito.anyInt());
		Mockito.when(dadoBancarioFactory.getDadoBancarioTO(Mockito.any())).thenReturn(dadoBancarioTOTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(uri).accept(MediaType.APPLICATION_JSON)
				.content(json).param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	@WithMockUser
	void deletarDadoBancarioErro() throws Exception {
		String uri = "/dadoBancario/deletar";
		
		String json = "{\"codDadosBancario\":1,\"codFesta\":8,\"codBanco\":\"teste\",\"codAgencia\":123,\"codConta\":12}";

		Mockito.doThrow(new ValidacaoException("teste")).when(dadoBancarioService).deleteDadoBancario(Mockito.any(), Mockito.anyInt());
		
		Mockito.when(dadoBancarioFactory.getDadoBancarioTO(Mockito.any())).thenReturn(dadoBancarioTOTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(uri).accept(MediaType.APPLICATION_JSON)
				.content(json).param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

}
