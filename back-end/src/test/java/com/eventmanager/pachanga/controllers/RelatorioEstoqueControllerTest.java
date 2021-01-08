package com.eventmanager.pachanga.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

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

import com.eventmanager.pachanga.dtos.InformacoesRelatorioEstoqueTO;
import com.eventmanager.pachanga.dtos.RelatorioEstoqueTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.services.RelatorioEstoqueService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RelatorioEstoqueController.class)
class RelatorioEstoqueControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RelatorioEstoqueService relatorioEstoqueService;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	@Test
	@WithMockUser
	void relatorioProblemasAreaSucesso() throws Exception {
		String uri = "/relatorioEstoque/consumoItemEstoque";

		Mockito.when(relatorioEstoqueService.relatoriosEstoque(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(new ArrayList<RelatorioEstoqueTO>());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	
	@Test
	@WithMockUser
	void relatorioProblemasAreaErro() throws Exception {
		String uri = "/relatorioEstoque/consumoItemEstoque";
		
		String expected = "teste";

		Mockito.when(relatorioEstoqueService.relatoriosEstoque(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
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
	void relatorioPerdaItemEstoqueSucesso() throws Exception {
		String uri = "/relatorioEstoque/perdaItemEstoque";

		Mockito.when(relatorioEstoqueService.relatoriosEstoque(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(new ArrayList<RelatorioEstoqueTO>());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	
	@Test
	@WithMockUser
	void relatorioPerdaItemEstoqueErro() throws Exception {
		String uri = "/relatorioEstoque/perdaItemEstoque";
		
		String expected = "teste";

		Mockito.when(relatorioEstoqueService.relatoriosEstoque(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
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
	void relatorioQuantidadeItemEstoqueSucesso() throws Exception {
		String uri = "/relatorioEstoque/quantidadeItemEstoque";

		Mockito.when(relatorioEstoqueService.relatoriosEstoque(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(new ArrayList<RelatorioEstoqueTO>());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	
	@Test
	@WithMockUser
	void relatorioQuantidadeItemEstoqueErro() throws Exception {
		String uri = "/relatorioEstoque/quantidadeItemEstoque";
		
		String expected = "teste";

		Mockito.when(relatorioEstoqueService.relatoriosEstoque(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
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
	void relatorioQuantidadeConsumoProdutoSucesso() throws Exception {
		String uri = "/relatorioEstoque/quantidadeConsumoProduto";

		Mockito.when(relatorioEstoqueService.relatorioQuantidadeConsumoProduto(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(new ArrayList<InformacoesRelatorioEstoqueTO>());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	
	@Test
	@WithMockUser
	void relatorioQuantidadeConsumoProdutoErro() throws Exception {
		String uri = "/relatorioEstoque/quantidadeConsumoProduto";
		
		String expected = "teste";

		Mockito.when(relatorioEstoqueService.relatorioQuantidadeConsumoProduto(Mockito.anyInt(), Mockito.anyInt()))
				.thenThrow(new ValidacaoException("teste"));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());

	}

}
