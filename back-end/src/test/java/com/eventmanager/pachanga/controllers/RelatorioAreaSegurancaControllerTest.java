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

import com.eventmanager.pachanga.dtos.RelatorioAreaSegurancaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.services.RelatorioAreaSegurancaService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RelatorioAreaSegurancaController.class)
class RelatorioAreaSegurancaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RelatorioAreaSegurancaService relatorioAreaSegurancaService;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	@Test
	@WithMockUser
	void relatorioProblemasAreaSucesso() throws Exception {
		String uri = "/relatorioAreaSeguranca/problemasArea";

		Mockito.when(relatorioAreaSegurancaService.relatorioProblemasArea(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(new RelatorioAreaSegurancaTO());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	
	@Test
	@WithMockUser
	void relatorioProblemasAreaErro() throws Exception {
		String uri = "/relatorioAreaSeguranca/problemasArea";
		
		String expected = "teste";

		Mockito.when(relatorioAreaSegurancaService.relatorioProblemasArea(Mockito.anyInt(), Mockito.anyInt()))
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
	void relatorioChamadasUsuarioSucesso() throws Exception {
		String uri = "/relatorioAreaSeguranca/chamadasUsuario";

		Mockito.when(relatorioAreaSegurancaService.relatorioChamadasUsuario(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(new RelatorioAreaSegurancaTO());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	
	@Test
	@WithMockUser
	void relatorioChamadasUsuarioErro() throws Exception {
		String uri = "/relatorioAreaSeguranca/chamadasUsuario";
		
		String expected = "teste";

		Mockito.when(relatorioAreaSegurancaService.relatorioChamadasUsuario(Mockito.anyInt(), Mockito.anyInt()))
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
	void relatorioUsuarioSolucionadorSucesso() throws Exception {
		String uri = "/relatorioAreaSeguranca/usuarioSolucionador";

		Mockito.when(relatorioAreaSegurancaService.relatorioUsuarioSolucionador(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(new RelatorioAreaSegurancaTO());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	
	@Test
	@WithMockUser
	void relatorioUsuarioSolucionadorErro() throws Exception {
		String uri = "/relatorioAreaSeguranca/usuarioSolucionador";
		
		String expected = "teste";

		Mockito.when(relatorioAreaSegurancaService.relatorioUsuarioSolucionador(Mockito.anyInt(), Mockito.anyInt()))
				.thenThrow(new ValidacaoException("teste"));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());

	}

}
