package com.eventmanager.pachanga.controllers;

import static org.junit.Assert.assertEquals;

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

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.InfoIntegracao;
import com.eventmanager.pachanga.dtos.InfoIntegracaoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.InfoIntegracaoFactory;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.services.InfoIntegracaoService;
import com.eventmanager.pachanga.tipo.TipoTerceiroIntegracao;

@RunWith(SpringRunner.class)
@WebMvcTest(value=InfoIntegracaoController.class)
class InfoIntegracaoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private InfoIntegracaoFactory infoIntegracaoFactory;

	@MockBean
	private InfoIntegracaoService infoIntegracaoService;
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	private Festa festaTest(){
		Festa festaTest = new Festa();

		festaTest.setCodFesta(2);
		festaTest.setCodEnderecoFesta("https//:minhacasa.org");
		festaTest.setDescOrganizador("sou demente");
		festaTest.setHorarioInicioFesta(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		festaTest.setHorarioFimFesta(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		festaTest.setNomeFesta("festao");
		festaTest.setOrganizador("Joao Neves");
		festaTest.setStatusFesta("I");
		festaTest.setDescricaoFesta("Bugago");
		festaTest.setHorarioFimFestaReal(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		return festaTest;
	}
	
	private InfoIntegracao infoTest() {
		InfoIntegracao info = new InfoIntegracao();
		info.setCodEvent("teste");
		info.setCodInfo(1);
		info.setFesta(festaTest());
		info.setTerceiroInt(TipoTerceiroIntegracao.SYMPLA.getValor());
		info.setToken("teste");
		return info;
	}
	
	private InfoIntegracaoTO infoTOTest() {
		InfoIntegracaoTO infoTo = new InfoIntegracaoTO();
		infoTo.setCodEvent("teste");
		infoTo.setCodInfo(1);
		infoTo.setCodFesta(2);
		infoTo.setTerceiroInt(TipoTerceiroIntegracao.SYMPLA.getValor());
		infoTo.setToken("teste");
		return infoTo;
	}
	
	@Test
	@WithMockUser
	void listaInfoIntegracaoFestaSucessoTest() throws Exception {
		String uri = "/integracao/lista";
		
		List<InfoIntegracao> infos = new ArrayList<>();
		infos.add(infoTest());
		
		Mockito.when(infoIntegracaoService.listaInfoIntegracaoFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(infos);
		Mockito.when(infoIntegracaoFactory.getInfoIntegracaoTO(Mockito.any())).thenReturn(infoTOTest());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1")
				.param("codUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
	
	@Test
	@WithMockUser
	void listaInfoIntegracaoFestaErroTest() throws Exception {
		String uri = "/integracao/lista";
		
		Mockito.when(infoIntegracaoService.listaInfoIntegracaoFesta(Mockito.anyInt(), Mockito.anyInt())).thenThrow(new ValidacaoException("teste"));
		Mockito.when(infoIntegracaoFactory.getInfoIntegracaoTO(Mockito.any())).thenReturn(infoTOTest());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1")
				.param("codUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}
	
	@Test
	@WithMockUser
	void infoIntegracaoFestaSucessoTest() throws Exception {
		String uri = "/integracao/infoUnico";
		
		String json = "{\"codInfo\":2,\"terceiroInt\":\"S\",\"token\":\"01d104a3df9006a2dbcfad2074e2f274a43c121cef9f427a7226a7cfd04d8a54\",\"codFesta\":4,\"codEvent\":\"1042568\"}";
		
		Mockito.when(infoIntegracaoService.infoIntegracaoFesta(Mockito.any(), Mockito.anyInt())).thenReturn(infoTest());
		Mockito.when(infoIntegracaoFactory.getInfoIntegracaoTO(Mockito.any())).thenReturn(infoTOTest());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("codUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
	
	@Test
	@WithMockUser
	void infoIntegracaoFestaErroTest() throws Exception {
		String uri = "/integracao/infoUnico";
		
		String json = "{\"codInfo\":2,\"terceiroInt\":\"S\",\"token\":\"01d104a3df9006a2dbcfad2074e2f274a43c121cef9f427a7226a7cfd04d8a54\",\"codFesta\":4,\"codEvent\":\"1042568\"}";
		
		Mockito.when(infoIntegracaoService.infoIntegracaoFesta(Mockito.any(), Mockito.anyInt())).thenThrow(new ValidacaoException("teste"));
		Mockito.when(infoIntegracaoFactory.getInfoIntegracaoTO(Mockito.any())).thenReturn(infoTOTest());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("codUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}
	
	@Test
	@WithMockUser
	void adicionarinfoIntegracaoFestaSucessoTest() throws Exception {
		String uri = "/integracao/adicionar";
		
		String json = "{\"codInfo\":2,\"terceiroInt\":\"S\",\"token\":\"01d104a3df9006a2dbcfad2074e2f274a43c121cef9f427a7226a7cfd04d8a54\",\"codFesta\":4,\"codEvent\":\"1042568\"}";
		
		Mockito.when(infoIntegracaoService.adicionarinfoIntegracaoFesta(Mockito.any(), Mockito.anyInt())).thenReturn(infoTest());
		Mockito.when(infoIntegracaoFactory.getInfoIntegracaoTO(Mockito.any())).thenReturn(infoTOTest());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("codUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
	
	@Test
	@WithMockUser
	void adicionarinfoIntegracaoFestaErroTest() throws Exception {
		String uri = "/integracao/adicionar";
		
		String json = "{\"codInfo\":2,\"terceiroInt\":\"S\",\"token\":\"01d104a3df9006a2dbcfad2074e2f274a43c121cef9f427a7226a7cfd04d8a54\",\"codFesta\":4,\"codEvent\":\"1042568\"}";
		
		Mockito.when(infoIntegracaoService.adicionarinfoIntegracaoFesta(Mockito.any(), Mockito.anyInt())).thenThrow(new ValidacaoException("teste"));
		Mockito.when(infoIntegracaoFactory.getInfoIntegracaoTO(Mockito.any())).thenReturn(infoTOTest());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("codUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}
	
	@Test
	@WithMockUser
	void atualizarinfoIntegracaoFestaSucessoTest() throws Exception {
		String uri = "/integracao/atualizar";
		
		String json = "{\"codInfo\":2,\"terceiroInt\":\"S\",\"token\":\"01d104a3df9006a2dbcfad2074e2f274a43c121cef9f427a7226a7cfd04d8a54\",\"codFesta\":4,\"codEvent\":\"1042568\"}";
		
		Mockito.when(infoIntegracaoService.atualizarinfoIntegracaoFesta(Mockito.any(), Mockito.anyInt())).thenReturn(infoTest());
		Mockito.when(infoIntegracaoFactory.getInfoIntegracaoTO(Mockito.any())).thenReturn(infoTOTest());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("codUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
	
	@Test
	@WithMockUser
	void atualizarinfoIntegracaoFestaErroTest() throws Exception {
		String uri = "/integracao/atualizar";
		
		String json = "{\"codInfo\":2,\"terceiroInt\":\"S\",\"token\":\"01d104a3df9006a2dbcfad2074e2f274a43c121cef9f427a7226a7cfd04d8a54\",\"codFesta\":4,\"codEvent\":\"1042568\"}";
		
		Mockito.when(infoIntegracaoService.atualizarinfoIntegracaoFesta(Mockito.any(), Mockito.anyInt())).thenThrow(new ValidacaoException("teste"));
		Mockito.when(infoIntegracaoFactory.getInfoIntegracaoTO(Mockito.any())).thenReturn(infoTOTest());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("codUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}
	
	@Test
	@WithMockUser
	void deleteinfoIntegracaoFestaSucessoTest() throws Exception {
		String uri = "/integracao/delete";
		
		
		Mockito.doNothing().when(infoIntegracaoService).deleteinfoIntegracaoFesta(Mockito.anyInt(), Mockito.anyInt());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codInfo", "1")
				.param("codUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
	
	@Test
	@WithMockUser
	void deleteinfoIntegracaoFestaErroTest() throws Exception {
		String uri = "/integracao/delete";
		
		Mockito.doThrow(new ValidacaoException("teste")).when(infoIntegracaoService).deleteinfoIntegracaoFesta(Mockito.anyInt(), Mockito.anyInt());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codUsuario", "1")
				.param("codInfo", "1")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}

}
