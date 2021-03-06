package com.eventmanager.pachanga.controllers;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
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
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eventmanager.pachanga.domains.ItemEstoque;
import com.eventmanager.pachanga.domains.Produto;
import com.eventmanager.pachanga.dtos.ItemEstoqueTO;
import com.eventmanager.pachanga.dtos.ProdutoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.ItemEstoqueFactory;
import com.eventmanager.pachanga.factory.ProdutoFactory;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.services.ProdutoService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=ProdutoController.class)
class ProdutoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProdutoService produtoService;
	
	@MockBean
	private ProdutoFactory produtoFactory;
	
	@MockBean
	private ItemEstoqueFactory itemEstoqueFactory;
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	private Produto produtoTest() {
		Produto produto = new Produto();
		produto.setCodProduto(1);
		produto.setCodFesta(2); //o mesmo do festaTest() 
		produto.setMarca("capsula");
		produto.setPrecoMedio(new BigDecimal("23.90"));
		return produto;
	}
	
	private ItemEstoqueTO itemEstoqueToTest() {
		ItemEstoqueTO itemEstoqueTo = new ItemEstoqueTO();
		itemEstoqueTo.setCodEstoque(1);
		itemEstoqueTo.setCodFesta(2); //o mesmo do festaTest() 
		itemEstoqueTo.setQuantidadeMax(100);
		itemEstoqueTo.setQuantidadeAtual(23);
		itemEstoqueTo.setPorcentagemMin(15);
		return itemEstoqueTo;
	}
	
	private ProdutoTO produtoToTest() {
		ProdutoTO produtoTo = new ProdutoTO();
		produtoTo.setCodProduto(1);
		produtoTo.setCodFesta(2); //o mesmo do festaTest() 
		produtoTo.setMarca("capsula");
		produtoTo.setPrecoMedio(new BigDecimal("23.90"));
		return produtoTo;
		
	}
	
	private ItemEstoque ItemEstoqueTest() {
		ItemEstoque itemEstoque = new ItemEstoque();
		itemEstoque.setCodFesta(2); //o mesmo do festaTest() 
		itemEstoque.setQuantidadeMax(100);
		itemEstoque.setQuantidadeAtual(23);
		itemEstoque.setPorcentagemMin(15);
		return itemEstoque;
	}
	
//addProduto___________________________________________________________________________________________________
	@Test
	@WithMockUser
	void addProdutoSucessoTest() throws Exception {
		String uri = "/produto/addProduto";
		String json = "{\"codFesta\":2,\"precoMedio\":23.90,\"marca\":\"capsula\"}";
		Produto produto = produtoTest();
		ProdutoTO produtoTo = produtoToTest();
		
		Mockito.when(produtoService.addProduto(Mockito.any(ProdutoTO.class), Mockito.anyInt(), Mockito.anyInt())).thenReturn(produto);
		Mockito.when(produtoFactory.getProdutoTO(Mockito.any(Produto.class))).thenReturn(produtoTo);
		

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("codFesta", "1")
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		String expected = "{\"codProduto\":1,\"codFesta\":2,\"precoMedio\":23.90,\"marca\":\"capsula\",\"dose\":false,\"quantDoses\":0}";

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	@WithMockUser
	void addProdutoExceptionTest() throws Exception {
		String uri = "/produto/addProduto";
		String json = "{\"codFesta\":2,\"precoMedio\":23.90,\"marca\":\"capsula\"}";
		String erro = "Exception";
		
		Mockito.when(produtoService.addProduto(Mockito.any(ProdutoTO.class), Mockito.anyInt(), Mockito.anyInt())).thenThrow(new ValidacaoException(erro));
		

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("codFesta", "1")
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(400, response.getStatus());
		assertEquals(erro, result.getResponse().getContentAsString());
	}
	
//addProdutoEstoque____________________________________________________________________________________________
	@Test
	@WithMockUser
	void addProdutoEstoqueSucessoTest() throws Exception {
		String uri = "/produto/addProdutoEstoque";
		String json = "{\"codFesta\":2,\"quantidadeMax\":100,\"quantiadadeAtual\":23,\"porcentagemMin\":15}";
		ItemEstoque itemEstoque = ItemEstoqueTest();
		
		Mockito.when(produtoService.addProdutoEstoque(Mockito.any(ItemEstoqueTO.class), Mockito.anyInt(), Mockito.anyInt())).thenReturn(itemEstoque);
		Mockito.when(itemEstoqueFactory.getItemEstoqueTO(Mockito.any(ItemEstoque.class))).thenReturn(itemEstoqueToTest());
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("codProduto", "1")
				.param("codEstoque", "1")
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		String expected = "{\"codProduto\":0,\"codEstoque\":1,\"codFesta\":2,\"quantidadeMax\":100,\"quantidadeAtual\":23,\"porcentagemMin\":15,\"produto\":null}";

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	@WithMockUser
	void addProdutoExceptionSucessoTest() throws Exception {
		String uri = "/produto/addProdutoEstoque";
		String json = "{\"codFesta\":2,\"quantidadeMax\":100,\"quantiadadeAtual\":23,\"porcentagemMin\":15}";
		String erro = "Exception";
		
		Mockito.when(produtoService.addProdutoEstoque(Mockito.any(ItemEstoqueTO.class), Mockito.anyInt(), Mockito.anyInt())).thenThrow(new ValidacaoException(erro));
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("codProduto", "1")
				.param("codEstoque", "1")
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(400, response.getStatus());
		assertEquals(erro, result.getResponse().getContentAsString());
	}
	
//removerProduto___________________________________________________________________________________________________
	@Test
	@WithMockUser
	void removerProdutoSucessoTest() throws Exception {
		String uri = "/produto/removerProduto";
		
		Mockito.doNothing().when(produtoService).removerProduto(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());
		

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codProduto", "1")
				.param("codFesta", "1")
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
			
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	@WithMockUser
	void removerProdutoExceptionTest() throws Exception {
		String uri = "/produto/removerProduto";
		String erro = "Exception";
		
		Mockito.doThrow(new ValidacaoException(erro)).when(produtoService).removerProduto(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codProduto", "1")
				.param("codFesta", "1")
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
			
		assertEquals(400, response.getStatus());
		assertEquals(erro, result.getResponse().getContentAsString());
	}
		
//removerProdutoEstoque____________________________________________________________________________________________
	@Test
	@WithMockUser
	void removerProdutoEstoqueSucessoTest() throws Exception {
		String uri = "/produto/removerProdutoEstoque";
		
		Mockito.doNothing().when(produtoService).removerProdutoEstoque(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());
		
			
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codProduto", "1")
				.param("codEstoque", "1")
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
			
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	@WithMockUser
	void removerProdutoEstoqueExceptionTest() throws Exception {
		String uri = "/produto/removerProdutoEstoque";
		String erro = "Exception";
		
		Mockito.doThrow(new ValidacaoException(erro)).when(produtoService).removerProdutoEstoque(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codProduto", "1")
				.param("codEstoque", "1")
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
			
		assertEquals(400, response.getStatus());
		assertEquals(erro, result.getResponse().getContentAsString());
	}
		
//editarProduto_________________________________________________________________________________________________	
	@Test
	@WithMockUser
	void editarProdutoSucessoTest() throws Exception {
		String uri = "/produto/editarProduto";
		String json = "{\"codProduto\":1,\"codFesta\":2,\"precoMedio\":23.90,\"marca\":\"capsula\"}";
		Produto produto = produtoTest();
		ProdutoTO produtoTo = produtoToTest();
			
		Mockito.when(produtoService.editarProduto(Mockito.any(ProdutoTO.class), Mockito.anyInt())).thenReturn(produto);
		Mockito.when(produtoFactory.getProdutoTO(Mockito.any(Produto.class))).thenReturn(produtoTo);
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
			
		String expected = "{\"codProduto\":1,\"codFesta\":2,\"precoMedio\":23.90,\"marca\":\"capsula\",\"dose\":false,\"quantDoses\":0}";

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	@WithMockUser
	void editarProdutoExceptionTest() throws Exception {
		String uri = "/produto/editarProduto";
		String json = "{\"codProduto\":1,\"codFesta\":2,\"precoMedio\":23.90,\"marca\":\"capsula\"}";
		String erro = "Exception";
			
		Mockito.when(produtoService.editarProduto(Mockito.any(ProdutoTO.class), Mockito.anyInt())).thenThrow(new ValidacaoException(erro));
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
			
		assertEquals(400, response.getStatus());
		assertEquals(erro, result.getResponse().getContentAsString());
	}
		
//addProdutoEstoque____________________________________________________________________________________________
	@Test
	@WithMockUser
	void editarProdutoEstoqueSucessoTest() throws Exception {
		String uri = "/produto/editarProdutoEstoque";
		String json = "{\"codProduto\":1,\"codEstoque\":1,\"codFesta\":2,\"quantidadeMax\":100,\"quantiadadeAtual\":23,\"porcentagemMin\":15}";
		ItemEstoque itemEstoque = ItemEstoqueTest();
			
		Mockito.when(produtoService.editarProdutoEstoque(Mockito.any(ItemEstoqueTO.class), Mockito.anyInt())).thenReturn(itemEstoque);
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
			
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	@WithMockUser
	void editarProdutoEstoqueExceptionTest() throws Exception {
		String uri = "/produto/editarProdutoEstoque";
		String json = "{\"codProduto\":1,\"codEstoque\":1,\"codFesta\":2,\"quantidadeMax\":100,\"quantiadadeAtual\":23,\"porcentagemMin\":15}";
		String erro = "Exception";
			
		Mockito.when(produtoService.editarProdutoEstoque(Mockito.any(ItemEstoqueTO.class), Mockito.anyInt())).thenThrow(new ValidacaoException(erro));
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
	
		assertEquals(400, response.getStatus());
		assertEquals(erro, result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser
	void listaProdutoExceptionTest() throws Exception {
		String uri = "/produto/lista";
		String erro = "Exception";
		
		List<ProdutoTO> produtosTo = new ArrayList<>();
		produtosTo.add(produtoToTest());
			
		Mockito.when(produtoService.listaProduto(Mockito.anyInt(), Mockito.anyInt())).thenThrow(new ValidacaoException(erro));
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1")
				.param("codUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
	
		assertEquals(400, response.getStatus());
		assertEquals(erro, result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser
	void listaProdutoSucessoTest() throws Exception {
		String uri = "/produto/lista";
		
		List<ProdutoTO> produtosTo = new ArrayList<>();
		produtosTo.add(produtoToTest());
		List<Produto> produtos = new ArrayList<>();
		produtos.add(produtoTest());
			
		Mockito.when(produtoService.listaProduto(Mockito.anyInt(), Mockito.anyInt())).thenReturn(produtos);
		Mockito.when(produtoFactory.getProdutosTO(Mockito.anyList())).thenReturn(produtosTo);
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1")
				.param("codUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		String expected = "[{\"codProduto\":1,\"codFesta\":2,\"precoMedio\":23.90,\"marca\":\"capsula\",\"dose\":false,\"quantDoses\":0}]";
	
		assertEquals(200, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser
	void getProdutoExceptionTest() throws Exception {
		String uri = "/produto/produtoUnico";
		String erro = "Exception";
		
		List<ProdutoTO> produtosTo = new ArrayList<>();
		produtosTo.add(produtoToTest());
			
		Mockito.when(produtoService.getProduto(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenThrow(new ValidacaoException(erro));
		Mockito.when(produtoFactory.getProdutoTO(Mockito.any())).thenReturn(produtoToTest());
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1")
				.param("codUsuario", "1")
				.param("codProduto", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
	
		assertEquals(400, response.getStatus());
		assertEquals(erro, result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser
	void getProdutoSucessoTest() throws Exception {
		String uri = "/produto/produtoUnico";
		
		List<ProdutoTO> produtosTo = new ArrayList<>();
		produtosTo.add(produtoToTest());
			
		Mockito.when(produtoService.getProduto(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(produtoTest());
		Mockito.when(produtoFactory.getProdutoTO(Mockito.any())).thenReturn(produtoToTest());
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1")
				.param("codUsuario", "1")
				.param("codProduto", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		String expected = "{\"codProduto\":1,\"codFesta\":2,\"precoMedio\":23.90,\"marca\":\"capsula\",\"dose\":false,\"quantDoses\":0}";
	
		assertEquals(200, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser
	void baixaProdutoEstoqueSucessoTest() throws Exception {
		String uri = "/produto/baixaProdutoEstoque";
		ItemEstoque itemEstoque = ItemEstoqueTest();
		
		Mockito.when(produtoService.baixaProduto(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(itemEstoque);
		
			
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codProduto", "1")
				.param("codEstoque", "1")
				.param("quantidade", "1")
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
			
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	@WithMockUser
	void quebraProdutoEstoqueSucessoTest() throws Exception {
		String uri = "/produto/quebraProdutoEstoque";
		ItemEstoque itemEstoque = ItemEstoqueTest();
		
		String quantidades = "[1,2]";
		
		Mockito.when(produtoService.baixaProduto(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(itemEstoque);
		
			
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codProduto", "1")
				.content(quantidades)
				.param("codEstoque", "1")
				.param("dose", "true")
				.param("quantidade", "1")
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
			
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	@WithMockUser
	void quebraProdutoEstoqueSemDoseSucessoTest() throws Exception {
		String uri = "/produto/quebraProdutoEstoque";
		ItemEstoque itemEstoque = ItemEstoqueTest();
		
		String quantidades = "[1,2]";
		
		Mockito.when(produtoService.baixaProduto(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(itemEstoque);
		
			
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codProduto", "1")
				.content(quantidades)
				.param("codEstoque", "1")
				.param("dose", "false")
				.param("quantidade", "1")
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
			
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	@WithMockUser
	void quebraProdutoEstoqueSemDoseExceptionTest() throws Exception {
		String uri = "/produto/quebraProdutoEstoque";
		String quantidades = "[1,2]";
		
		Mockito.when(produtoService.baixaProduto(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean())).thenThrow(new ValidacaoException("teste"));
		
			
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codProduto", "1")
				.content(quantidades)
				.param("codEstoque", "1")
				.param("dose", "false")
				.param("quantidade", "1")
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
			
		assertEquals(400, response.getStatus());
		assertEquals("teste", result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser
	void baixaProdutoEstoqueExceptionTest() throws Exception {
		String uri = "/produto/baixaProdutoEstoque";
		String erro = "Exception";
		
		Mockito.when(produtoService.baixaProduto(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean())).thenThrow(new ValidacaoException(erro));
		
			
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codProduto", "1")
				.param("codEstoque", "1")
				.param("quantidade", "1")
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
			
		assertEquals(400, response.getStatus());
		assertEquals(erro, result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser
	void recargaProdutoEstoqueSucessoTest() throws Exception {
		String uri = "/produto/recargaProdutoEstoque";
		ItemEstoque itemEstoque = ItemEstoqueTest();
		
		Mockito.when(produtoService.recargaProdutoComOrigem(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any())).thenReturn(itemEstoque);
		
			
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codProduto", "1")
				.param("codEstoque", "1")
				.param("quantidade", "1")
				.param("idUsuarioPermissao", "1")
				.param("codEstoqueOrigem", "48")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
			
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	@WithMockUser
	void recargaProdutoEstoqueExceptionTest() throws Exception {
		String uri = "/produto/recargaProdutoEstoque";
		String erro = "Exception";
		
		Mockito.when(produtoService.recargaProdutoComOrigem(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any())).thenThrow(new ValidacaoException(erro));
		
			
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codProduto", "1")
				.param("codEstoque", "1")
				.param("quantidade", "1")
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
			
		assertEquals(400, response.getStatus());
		assertEquals(erro, result.getResponse().getContentAsString());
	}

}
