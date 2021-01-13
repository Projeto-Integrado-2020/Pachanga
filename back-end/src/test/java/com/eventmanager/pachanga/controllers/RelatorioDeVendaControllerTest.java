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

import com.eventmanager.pachanga.dtos.RelatorioDeVendaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.services.RelatorioDeVendaService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RelatorioDeVendaController.class)
class RelatorioDeVendaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RelatorioDeVendaService relatorioDeVendaService;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	@Test
	@WithMockUser
	void relatorioIngressosFestaSucesso() throws Exception {

		String uri = "/relatorioVenda/ingressosFesta";

		Mockito.when(relatorioDeVendaService.relatorioDeIngressos(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(new RelatorioDeVendaTO());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	@WithMockUser
	void relatorioIngressosFestaErro() throws Exception {

		String uri = "/relatorioVenda/ingressosFesta";

		String expected = "teste";

		Mockito.when(relatorioDeVendaService.relatorioDeIngressos(Mockito.anyInt(), Mockito.anyInt()))
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
	void relatorioIngressosFestaPagosSucesso() throws Exception {

		String uri = "/relatorioVenda/ingressosFestaCompradosPagos";

		Mockito.when(relatorioDeVendaService.relatorioDeIngressosPagosComprados(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(new RelatorioDeVendaTO());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	@WithMockUser
	void relatorioIngressosFestaPagosErro() throws Exception {

		String uri = "/relatorioVenda/ingressosFestaCompradosPagos";

		String expected = "teste";

		Mockito.when(relatorioDeVendaService.relatorioDeIngressosPagosComprados(Mockito.anyInt(), Mockito.anyInt()))
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
	void relatorioLucroEsperadoFestaSucesso() throws Exception {

		String uri = "/relatorioVenda/lucroFesta";

		Mockito.when(relatorioDeVendaService.relatorioLucroFesta(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(new RelatorioDeVendaTO());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	@WithMockUser
	void relatorioLucroEsperadoFestaErro() throws Exception {

		String uri = "/relatorioVenda/lucroFesta";

		String expected = "teste";

		Mockito.when(relatorioDeVendaService.relatorioLucroFesta(Mockito.anyInt(), Mockito.anyInt()))
				.thenThrow(new ValidacaoException("teste"));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());

	}

}
