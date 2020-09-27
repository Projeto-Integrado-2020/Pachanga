package com.eventmanager.pachanga.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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

import com.eventmanager.pachanga.domains.Estoque;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.EstoqueTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.EstoqueFactory;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.services.EstoqueService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=EstoqueController.class)
class EstoqueControllerTest {
	
	@MockBean
	private EstoqueService estoqueService;
	
	@MockBean
	private EstoqueFactory estoqueFactory;
	
	@Autowired
	EstoqueController estoqueController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	public static Usuario usuarioTest() throws Exception{
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("fc68b677646b5f018d1762e9a19bf65180d9aab2794794340ade50e0d78a239affd43a613e7136a61b5d63b09f072c0c039dea4281873abe826d6e6285d9cefef0a0d868d3b0b0d4582ec787b473b4e0");
		usuarioTest.setGenero("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");

		return usuarioTest;
	}
	
	private Festa criacaoFesta() {
		Festa festaTest = new Festa();

		festaTest.setCodFesta(2);
		festaTest.setCodEnderecoFesta("https//:minhacasa.org");
		festaTest.setDescOrganizador("sou demente");
		festaTest.setHorarioInicioFesta(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		festaTest.setHorarioFimFesta(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		festaTest.setNomeFesta("festao");
		festaTest.setOrganizador("Joao Neves");
		festaTest.setStatusFesta("P");
		festaTest.setDescricaoFesta("Bugago");
		festaTest.setHorarioFimFestaReal(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));

		return festaTest;
	}

	public Estoque estoqueTest() {
		Estoque estoque = new Estoque();
		estoque.setCodEstoque(1);
		estoque.setNomeEstoque("Estoque");
		estoque.setFesta(criacaoFesta());
		estoque.setPrincipal(false);
		return estoque;
	}
	
	public EstoqueTO estoqueTOTest() {
		EstoqueTO estoqueTO = new EstoqueTO();
		estoqueTO.setCodEstoque(1);
		estoqueTO.setNomeEstoque("Estoque");
		estoqueTO.setPrincipal(false);
		return estoqueTO;
	}
	
//lista____________________________________________________________________________________________________
	@Test
	@WithMockUser
	void listaSucessoTest() throws Exception{
		String uri = "/estoque/lista";
		String expected = "[{\"codEstoque\":1,\"principal\":false,\"nomeEstoque\":\"Estoque\",\"itemEstoque\":null}]";
		
		List<Estoque> estoques = new ArrayList<>();
		estoques.add(estoqueTest());
		
		List<EstoqueTO> estoquesTO = new ArrayList<>();
		estoquesTO.add(estoqueTOTest());
		
		Mockito.when(estoqueService.estoquesFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(estoques);
		Mockito.when(estoqueFactory.getListEstoqueTO(estoques)).thenReturn(estoquesTO);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1")
				.param("codUsuario", "14")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
		
	}
	
	@Test
	@WithMockUser
	void listaExceptionTest() throws Exception{
		String uri = "/estoque/lista";
		
		String expected = "batata";
		List<Estoque> estoques = new ArrayList<>();
		estoques.add(estoqueTest());
		
		Mockito.when(estoqueService.estoquesFesta(Mockito.anyInt(), Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1")
				.param("codUsuario", "14")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
		
	}

//adicionar________________________________________________________________________________________________
	@Test
	@WithMockUser
	void adicionarSucessoTest() throws Exception{
		String uri = "/estoque/adicionar";
		String expected = "{\"codEstoque\":1,\"principal\":false,\"nomeEstoque\":\"Estoque\",\"itemEstoque\":null}";
		String json = "{\"codEstoque\":1,\"principal\":false,\"nomeEstoque\":\"Estoque\",\"itemEstoque\":null}";

		Estoque estoque = estoqueTest();
		EstoqueTO estoqueTO = estoqueTOTest();
		
		Mockito.when(estoqueService.addEstoque(Mockito.anyString(), Mockito.anyInt(), Mockito.anyBoolean(), Mockito.anyInt())).thenReturn(estoque);
		Mockito.when(estoqueFactory.getEstoqueTO(estoque)).thenReturn(estoqueTO);
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("codFesta", "1")
				.param("codUsuario", "14")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
		
	}
	
	@Test
	@WithMockUser
	void adicionarExceptionTest() throws Exception{
		String uri = "/estoque/adicionar";
		String expected = "Batata";
		String json = "{\"codEstoque\":1,\"principal\":false,\"nomeEstoque\":\"Estoque\",\"itemEstoque\":null}";

		Estoque estoque = estoqueTest();
		EstoqueTO estoqueTO = estoqueTOTest();
		
		Mockito.when(estoqueService.addEstoque(Mockito.anyString(), Mockito.anyInt(), Mockito.anyBoolean(), Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		Mockito.when(estoqueFactory.getEstoqueTO(estoque)).thenReturn(estoqueTO);
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("codFesta", "1")
				.param("codUsuario", "14")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
		
	}
	
//delete___________________________________________________________________________________________________
	@Test
	@WithMockUser
	void deleteSucessoTest() throws Exception{
		String uri = "/estoque/delete";
		String expected = "DELEESTO";
		
		List<Estoque> estoques = new ArrayList<>();
		estoques.add(estoqueTest());
		
		List<EstoqueTO> estoquesTO = new ArrayList<>();
		estoquesTO.add(estoqueTOTest());
		
		Mockito.doNothing().when(estoqueService).deleteEstoque(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codEstoque", "1")
				.param("codFesta", "1")
				.param("codUsuario", "14")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
		
	}
	
	@Test
	@WithMockUser
	void deleteExceptionTest() throws Exception{
		String uri = "/estoque/delete";
		
		String expected = "batata";
		List<Estoque> estoques = new ArrayList<>();
		estoques.add(estoqueTest());
		
		doThrow(new ValidacaoException(expected)).when(estoqueService).deleteEstoque(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codEstoque", "1")
				.param("codFesta", "1")
				.param("codUsuario", "14")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
		
	}
	
//atualizar________________________________________________________________________________________________
	@Test
	@WithMockUser
	void atualizarSucessoTest() throws Exception{
		String uri = "/estoque/atualizar";
		String expected = "{\"codEstoque\":1,\"principal\":false,\"nomeEstoque\":\"Estoque\",\"itemEstoque\":null}";
		String json = "{\"codEstoque\":1,\"principal\":false,\"nomeEstoque\":\"Estoque\",\"itemEstoque\":null}";

		Estoque estoque = estoqueTest();
		EstoqueTO estoqueTO = estoqueTOTest();
		
		Mockito.when(estoqueService.updateEstoque(Mockito.any(EstoqueTO.class), Mockito.anyInt(), Mockito.anyInt())).thenReturn(estoque);
		
		
		Mockito.when(estoqueFactory.getEstoqueTO(estoque)).thenReturn(estoqueTO);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("codFesta", "1")
				.param("codUsuario", "14")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
		
	}
	
	@Test
	@WithMockUser
	void atualizarExceptionTest() throws Exception{
		String uri = "/estoque/atualizar";
		String expected = "Batata";
		String json = "{\"codEstoque\":1,\"principal\":false,\"nomeEstoque\":\"Estoque\",\"itemEstoque\":null}";

		Estoque estoque = estoqueTest();
		EstoqueTO estoqueTO = estoqueTOTest();
		
		Mockito.when(estoqueService.updateEstoque(Mockito.any(EstoqueTO.class), Mockito.anyInt(), Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		Mockito.when(estoqueFactory.getEstoqueTO(estoque)).thenReturn(estoqueTO);
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("codFesta", "1")
				.param("codUsuario", "14")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
		
	}
}
