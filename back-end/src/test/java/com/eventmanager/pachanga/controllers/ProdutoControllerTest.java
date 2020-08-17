package com.eventmanager.pachanga.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;

import java.math.BigDecimal;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eventmanager.pachanga.domains.ItemEstoque;
import com.eventmanager.pachanga.domains.Produto;
import com.eventmanager.pachanga.dtos.ProdutoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.dtos.ItemEstoqueTO;
import com.eventmanager.pachanga.services.ProdutoService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=ProdutoController.class)
public class ProdutoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProdutoService produtoService;
	
	private Produto produtoTest() {
		Produto produto = new Produto();
		produto.setCodProduto(1);
		produto.setCodFesta(2); //o mesmo do festaTest() 
		produto.setMarca("capsula");
		produto.setPrecoMedio(new BigDecimal("23.90"));
		return produto;
		
	}
	
	private ItemEstoque ItemEstoqueTest() {
		ItemEstoque itemEstoque = new ItemEstoque();
		itemEstoque.setCodEstoque(1);
		itemEstoque.setCodFesta(2); //o mesmo do festaTest() 
		itemEstoque.setCodProduto(1);
		itemEstoque.setQuantidadeMax(100);
		itemEstoque.setQuantiadadeAtual(23);
		itemEstoque.setPorcentagemMin(15);
		return itemEstoque;
	}
	
//addProduto___________________________________________________________________________________________________
	@Test
	public void addProdutoSucessoTest() throws Exception {
		String uri = "/produto/addProduto";
		String json = "{\"codFesta\":2,\"precoMedio\":23.90,\"marca\":\"capsula\"}";
		Produto produto = produtoTest();
		
		doNothing().when(produtoService).validarUsuarioPorFesta(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(produtoService.addProduto(Mockito.any(ProdutoTO.class), Mockito.anyInt())).thenReturn(produto);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("codFesta", "1")
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		String expected = "{\"codProduto\":1,\"codFesta\":2,\"precoMedio\":23.90,\"marca\":\"capsula\"}";

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	public void addProdutoExceptionTest() throws Exception {
		String uri = "/produto/addProduto";
		String json = "{\"codFesta\":2,\"precoMedio\":23.90,\"marca\":\"capsula\"}";
		String erro = "Exception";
		
		doNothing().when(produtoService).validarUsuarioPorFesta(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(produtoService.addProduto(Mockito.any(ProdutoTO.class), Mockito.anyInt())).thenThrow(new ValidacaoException(erro));

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
	public void addProdutoEstoqueSucessoTest() throws Exception {
		String uri = "/produto/addProdutoEstoque";
		String json = "{\"codFesta\":2,\"quantidadeMax\":100,\"quantiadadeAtual\":23,\"porcentagemMin\":15}";
		ItemEstoque itemEstoque = ItemEstoqueTest();
		
		doNothing().when(produtoService).validarUsuarioPorEstoque(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(produtoService.addProdutoEstoque(Mockito.any(ItemEstoqueTO.class), Mockito.anyInt(), Mockito.anyInt())).thenReturn(itemEstoque);
		
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
		
		String expected = "{\"codProduto\":1,\"codEstoque\":1,\"codFesta\":2,\"quantidadeMax\":100,\"quantiadadeAtual\":23,\"porcentagemMin\":15}";

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	public void addProdutoExceptionSucessoTest() throws Exception {
		String uri = "/produto/addProdutoEstoque";
		String json = "{\"codFesta\":2,\"quantidadeMax\":100,\"quantiadadeAtual\":23,\"porcentagemMin\":15}";
		String erro = "Exception";
		
		doNothing().when(produtoService).validarUsuarioPorEstoque(Mockito.anyInt(), Mockito.anyInt());
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
	public void removerProdutoSucessoTest() throws Exception {
		String uri = "/produto/removerProduto";
		Produto produto = produtoTest();
		
		doNothing().when(produtoService).validarUsuarioPorFesta(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(produtoService.removerProduto(Mockito.anyInt())).thenReturn(produto);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codProduto", "1")
				.param("codFesta", "1")
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
			
		String expected = "{\"codProduto\":1,\"codFesta\":2,\"precoMedio\":23.90,\"marca\":\"capsula\"}";

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	public void removerProdutoExceptionTest() throws Exception {
		String uri = "/produto/removerProduto";
		String erro = "Exception";
		
		doNothing().when(produtoService).validarUsuarioPorFesta(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(produtoService.removerProduto(Mockito.anyInt())).thenThrow(new ValidacaoException(erro));
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
	public void removerProdutoEstoqueSucessoTest() throws Exception {
		String uri = "/produto/removerProdutoEstoque";
		ItemEstoque itemEstoque = ItemEstoqueTest();
		
		doNothing().when(produtoService).validarUsuarioPorEstoque(Mockito.anyInt(), Mockito.anyInt());	
		Mockito.when(produtoService.removerProdutoEstoque(Mockito.anyInt(), Mockito.anyInt())).thenReturn(itemEstoque);
			
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codProduto", "1")
				.param("codEstoque", "1")
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
			
		String expected = "{\"codProduto\":1,\"codEstoque\":1,\"codFesta\":2,\"quantidadeMax\":100,\"quantiadadeAtual\":23,\"porcentagemMin\":15}";

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	public void removerProdutoEstoqueExceptionTest() throws Exception {
		String uri = "/produto/removerProdutoEstoque";
		String erro = "Exception";
		
		doNothing().when(produtoService).validarUsuarioPorEstoque(Mockito.anyInt(), Mockito.anyInt());	
		Mockito.when(produtoService.removerProdutoEstoque(Mockito.anyInt(), Mockito.anyInt())).thenThrow(new ValidacaoException(erro));
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
	public void editarProdutoSucessoTest() throws Exception {
		String uri = "/produto/editarProduto";
		String json = "{\"codProduto\":1,\"codFesta\":2,\"precoMedio\":23.90,\"marca\":\"capsula\"}";
		Produto produto = produtoTest();
			
		doNothing().when(produtoService).validarUsuarioPorFesta(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(produtoService.editarProduto(Mockito.any(ProdutoTO.class))).thenReturn(produto);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
			
		String expected = "{\"codProduto\":1,\"codFesta\":2,\"precoMedio\":23.90,\"marca\":\"capsula\"}";

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	public void editarProdutoExceptionTest() throws Exception {
		String uri = "/produto/editarProduto";
		String json = "{\"codProduto\":1,\"codFesta\":2,\"precoMedio\":23.90,\"marca\":\"capsula\"}";
		String erro = "Exception";
			
		doNothing().when(produtoService).validarUsuarioPorFesta(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(produtoService.editarProduto(Mockito.any(ProdutoTO.class))).thenThrow(new ValidacaoException(erro));
		
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
	public void editarProdutoEstoqueSucessoTest() throws Exception {
		String uri = "/produto/editarProdutoEstoque";
		String json = "{\"codProduto\":1,\"codEstoque\":1,\"codFesta\":2,\"quantidadeMax\":100,\"quantiadadeAtual\":23,\"porcentagemMin\":15}";
		ItemEstoque itemEstoque = ItemEstoqueTest();
			
		doNothing().when(produtoService).validarUsuarioPorEstoque(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(produtoService.editarProdutoEstoque(Mockito.any(ItemEstoqueTO.class))).thenReturn(itemEstoque);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
			
		String expected = "{\"codProduto\":1,\"codEstoque\":1,\"codFesta\":2,\"quantidadeMax\":100,\"quantiadadeAtual\":23,\"porcentagemMin\":15}";

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	public void editarProdutoEstoqueExceptionTest() throws Exception {
		String uri = "/produto/editarProdutoEstoque";
		String json = "{\"codProduto\":1,\"codEstoque\":1,\"codFesta\":2,\"quantidadeMax\":100,\"quantiadadeAtual\":23,\"porcentagemMin\":15}";
		String erro = "Exception";
			
		doNothing().when(produtoService).validarUsuarioPorEstoque(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(produtoService.editarProdutoEstoque(Mockito.any(ItemEstoqueTO.class))).thenThrow(new ValidacaoException(erro));
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

}
