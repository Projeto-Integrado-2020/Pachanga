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

import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.services.ArquivosBancoService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ArquivosBancoController.class)
class ArquivosBancoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	@MockBean
	private ArquivosBancoService arquivosBancoService;

	@Test
	@WithMockUser
	void criaArquivoBancoSucesso() throws Exception {
		String uri = "/arqBanco/cria";

		Mockito.doNothing().when(arquivosBancoService).criacaoRemessa();

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	
	@Test
	@WithMockUser
	void criaArquivoBancoErro() throws Exception {
		String uri = "/arqBanco/cria";
		
		String expected = "teste";

		Mockito.doThrow(new ValidacaoException(expected)).when(arquivosBancoService).criacaoRemessa();

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());

	}

}
