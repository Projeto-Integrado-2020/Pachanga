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

import com.eventmanager.pachanga.dtos.RelatorioCheckInTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.services.RelatorioCheckInService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RelatorioCheckInController.class)
class RelatorioCheckInControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RelatorioCheckInService relatorioCheckInService;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	
	@Test
	@WithMockUser
	void ingressosCompradosEntradasSucesso() throws Exception {
		
		String uri = "/relatorioCheckIn/ingressosCompradosEntradas";
		
		Mockito.when(relatorioCheckInService.ingressosCompradosEntradas(Mockito.anyInt(), Mockito.anyInt()))
		.thenReturn(new RelatorioCheckInTO());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
	
	@Test
	@WithMockUser
	void ingressosCompradosEntradasErro() throws Exception {
		
		String uri = "/relatorioCheckIn/ingressosCompradosEntradas";
		
		String expected = "teste";
		
		Mockito.when(relatorioCheckInService.ingressosCompradosEntradas(Mockito.anyInt(), Mockito.anyInt()))
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
	void listaFaixaEtariaSucesso() throws Exception {
		
		String uri = "/relatorioCheckIn/faixaEtaria";
		
		Mockito.when(relatorioCheckInService.faixaEtariaFesta(Mockito.anyInt(), Mockito.anyInt()))
		.thenReturn(new RelatorioCheckInTO());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
	
	@Test
	@WithMockUser
	void listaFaixaEtariaErro() throws Exception {
		
		String uri = "/relatorioCheckIn/faixaEtaria";
		
		String expected = "teste";
		
		Mockito.when(relatorioCheckInService.faixaEtariaFesta(Mockito.anyInt(), Mockito.anyInt()))
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
	void listaGenerosFestaSucesso() throws Exception {
		
		String uri = "/relatorioCheckIn/generos";
		
		Mockito.when(relatorioCheckInService.quantidadeGeneroFesta(Mockito.anyInt(), Mockito.anyInt()))
		.thenReturn(new RelatorioCheckInTO());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
	
	@Test
	@WithMockUser
	void listaGenerosFestaErro() throws Exception {
		
		String uri = "/relatorioCheckIn/generos";
		
		String expected = "teste";
		
		Mockito.when(relatorioCheckInService.quantidadeGeneroFesta(Mockito.anyInt(), Mockito.anyInt()))
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
	void quantidadeEntradasHoraSucesso() throws Exception {
		
		String uri = "/relatorioCheckIn/quantidadeEntradasHora";
		
		Mockito.when(relatorioCheckInService.quantidadeEntradaHoras(Mockito.anyInt(), Mockito.anyInt()))
		.thenReturn(new RelatorioCheckInTO());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
	
	@Test
	@WithMockUser
	void quantidadeEntradasHoraErro() throws Exception {
		
		String uri = "/relatorioCheckIn/quantidadeEntradasHora";
		
		String expected = "teste";
		
		Mockito.when(relatorioCheckInService.quantidadeEntradaHoras(Mockito.anyInt(), Mockito.anyInt()))
		.thenThrow(new ValidacaoException("teste"));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());
		
	}

}
