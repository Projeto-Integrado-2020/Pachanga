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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.eventmanager.pachanga.PachangaApplication;
import com.eventmanager.pachanga.domains.Permissao;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.securingweb.OAuthHelper;
import com.eventmanager.pachanga.services.PermissaoService;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = PachangaApplication.class)
@AutoConfigureMockMvc
class PermissaoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PermissaoService permissaoService;
	
	@Autowired
	private OAuthHelper authHelper;
	
	public Permissao PermissaoTest(int id, String desc, String tipo) {
		Permissao permissao = new Permissao();
		permissao.setCodPermissao(id);
		permissao.setDescPermissao(desc);
		permissao.setTipPermissao(tipo);
		
		return permissao;
	}
	
	public List<Permissao> ColecaoDePermissaoTest() {
		List<Permissao> permissoes = new ArrayList<>();
		
		permissoes.add(PermissaoTest(1, "EDITDFES", "G"));
		permissoes.add(PermissaoTest(2, "CREGRPER", "G"));
		permissoes.add(PermissaoTest(3, "DELGRPER", "G"));
		permissoes.add(PermissaoTest(4, "EDIGRPER", "G"));
		permissoes.add(PermissaoTest(5, "ADDMEMBE", "G"));
		permissoes.add(PermissaoTest(6, "DELMEMBE", "G"));
		permissoes.add(PermissaoTest(7, "DISMEMBE", "G"));
		permissoes.add(PermissaoTest(8, "CADAESTO", "E"));
		permissoes.add(PermissaoTest(9, "DELEESTO", "E"));
		permissoes.add(PermissaoTest(10, "EDITESTO", "E"));
		permissoes.add(PermissaoTest(11, "CADMESTO", "E"));
		permissoes.add(PermissaoTest(12, "DELMESTO", "E"));
		permissoes.add(PermissaoTest(13, "EDIMESTO", "E"));
		permissoes.add(PermissaoTest(14, "ADDMESTO", "E"));
		permissoes.add(PermissaoTest(15, "BAIMESTO", "E"));
		permissoes.add(PermissaoTest(16, "DELEFEST", "G"));
		
		return permissoes;
	}
	
	@Test
	void getAllPermissaoSucessoTest() throws Exception {
		//String festaJson = "{\"codFesta\":\"2\",\"nomeFesta\":\"festao\",\"statusFesta\":\"I\",\"organizador\":\"Joao Neves\",\"horarioInicioFesta\":\"2016-06-22T19:10:00\",\"horarioFimFesta\":\"2016-06-23T19:10:00\",\"descricaoFesta\":\"Bugago\",\"codEnderecoFesta\":\"https//:minhacasa.org\",\"descOrganizador\":\"sou demente\"}";
		String uri = "/permissao/getAllPermissao";
		
		List<Permissao> permissoes = ColecaoDePermissaoTest();		 
		
		Mockito.when(permissaoService.getAllPermissao()).thenReturn(permissoes);
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		String expected = "[{\"codPermissao\":1,\"descPermissao\":\"EDITDFES\",\"tipPermissao\":\"G\"},{\"codPermissao\":2,\"descPermissao\":\"CREGRPER\",\"tipPermissao\":\"G\"},{\"codPermissao\":3,\"descPermissao\":\"DELGRPER\",\"tipPermissao\":\"G\"},{\"codPermissao\":4,\"descPermissao\":\"EDIGRPER\",\"tipPermissao\":\"G\"},{\"codPermissao\":5,\"descPermissao\":\"ADDMEMBE\",\"tipPermissao\":\"G\"},{\"codPermissao\":6,\"descPermissao\":\"DELMEMBE\",\"tipPermissao\":\"G\"},{\"codPermissao\":7,\"descPermissao\":\"DISMEMBE\",\"tipPermissao\":\"G\"},{\"codPermissao\":8,\"descPermissao\":\"CADAESTO\",\"tipPermissao\":\"E\"},{\"codPermissao\":9,\"descPermissao\":\"DELEESTO\",\"tipPermissao\":\"E\"},{\"codPermissao\":10,\"descPermissao\":\"EDITESTO\",\"tipPermissao\":\"E\"},{\"codPermissao\":11,\"descPermissao\":\"CADMESTO\",\"tipPermissao\":\"E\"},{\"codPermissao\":12,\"descPermissao\":\"DELMESTO\",\"tipPermissao\":\"E\"},{\"codPermissao\":13,\"descPermissao\":\"EDIMESTO\",\"tipPermissao\":\"E\"},{\"codPermissao\":14,\"descPermissao\":\"ADDMESTO\",\"tipPermissao\":\"E\"},{\"codPermissao\":15,\"descPermissao\":\"BAIMESTO\",\"tipPermissao\":\"E\"},{\"codPermissao\":16,\"descPermissao\":\"DELEFEST\",\"tipPermissao\":\"G\"}]";
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	void getAllPermissaoExceptionTest() throws Exception {
		String uri = "/permissao/getAllPermissao";
		
		String expected = "errox";	 
		
		Mockito.when(permissaoService.getAllPermissao()).thenThrow(new ValidacaoException(expected));
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
}
