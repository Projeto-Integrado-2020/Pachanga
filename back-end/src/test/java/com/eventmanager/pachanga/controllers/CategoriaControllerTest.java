package com.eventmanager.pachanga.controllers;

import static org.junit.Assert.assertEquals;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eventmanager.pachanga.domains.Categoria;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.services.CategoriaService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=CategoriaController.class)
public class CategoriaControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CategoriaService categoriaService;
	
	private Categoria categoriaTest() {
		Categoria categoria = new Categoria();
		categoria.setCodCategoria(1);
		categoria.setNomeCategoria("teste");
		return categoria;
	}
	
	//listaCategorias_____________________________________________________________________________________________________________
	
	@Test
	public void listaCategoriasSucesso() throws Exception {
		String uri = "/categoria/lista";
		
		List<Categoria> categorias = new ArrayList<Categoria>();
		categorias.add(categoriaTest());
		
		String expected = "[{\"codCategoria\":1,\"nomeCategoria\":\"teste\"}]";

		Mockito.when(categoriaService.procurarCategorias()).thenReturn(categorias);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	public void listaCategoriasErro() throws Exception {
		String uri = "/categoria/lista";
		
		List<Categoria> categorias = new ArrayList<Categoria>();
		categorias.add(categoriaTest());
		
		Mockito.when(categoriaService.procurarCategorias()).thenThrow(new ValidacaoException("teste"));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
		assertEquals("teste", result.getResponse().getContentAsString());
	}

}
