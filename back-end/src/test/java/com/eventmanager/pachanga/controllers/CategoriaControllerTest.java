package com.eventmanager.pachanga.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.eventmanager.pachanga.PachangaApplication;
import com.eventmanager.pachanga.domains.Categoria;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.securingweb.OAuthHelper;
import com.eventmanager.pachanga.services.CategoriaService;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = PachangaApplication.class)
@AutoConfigureMockMvc
class CategoriaControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private OAuthHelper authHelper;
	
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
	@WithMockUser
	void listaCategoriasSucesso() throws Exception {
		String uri = "/categoria/lista";
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
		
		List<Categoria> categorias = new ArrayList<Categoria>();
		categorias.add(categoriaTest());
		
		String expected = "[{\"codCategoria\":1,\"nomeCategoria\":\"teste\"}]";

		Mockito.when(categoriaService.procurarCategorias()).thenReturn(categorias);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.with(bearerToken)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	void listaCategoriasErro() throws Exception {
		String uri = "/categoria/lista";
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
		
		List<Categoria> categorias = new ArrayList<Categoria>();
		categorias.add(categoriaTest());
		
		Mockito.when(categoriaService.procurarCategorias()).thenThrow(new ValidacaoException("teste"));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.with(bearerToken)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
		assertEquals("teste", result.getResponse().getContentAsString());
	}

}
