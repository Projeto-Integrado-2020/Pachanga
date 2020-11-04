package com.eventmanager.pachanga.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eventmanager.pachanga.domains.AreaSeguranca;
import com.eventmanager.pachanga.domains.AreaSegurancaProblema;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Problema;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.AreaSegurancaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.AreaSegurancaFactory;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.services.AreaSegurancaService;
import com.eventmanager.pachanga.tipo.TipoAreaSeguranca;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;
import com.eventmanager.pachanga.tipo.TipoStatusProblema;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AreaSegurancaController.class)
class AreaSegurancaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AreaSegurancaService areaSegurancaService;

	@MockBean
	private AreaSegurancaFactory areaSegurancaFactory;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	private AreaSeguranca areaTest() {
		AreaSeguranca area = new AreaSeguranca();
		area.setCodArea(1);
		area.setCodFesta(3);
		area.setNomeArea("teste123");
		area.setStatusSeguranca(TipoAreaSeguranca.SEGURO.getDescricao());
		return area;
	}

	private AreaSegurancaTO areaTOTest() {
		AreaSegurancaTO area = new AreaSegurancaTO();
		area.setCodArea(1);
		area.setCodFesta(3);
		area.setNomeArea("teste123");
		area.setStatusSeguranca(TipoAreaSeguranca.SEGURO.getDescricao());
		return area;
	}
	
	private Festa criacaoFesta() throws Exception{
		Festa festaTest = new Festa();
		festaTest.setCodFesta(1);
		festaTest.setCodEnderecoFesta("https//:minhacasa.org");
		festaTest.setDescOrganizador("sou demente");
		festaTest.setHorarioInicioFesta(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		festaTest.setHorarioFimFesta(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		festaTest.setNomeFesta("festao");
		festaTest.setOrganizador("Joao Neves");
		festaTest.setStatusFesta(TipoStatusFesta.PREPARACAO.getValor());
		festaTest.setDescricaoFesta("Bugago");
		festaTest.setHorarioFimFestaReal(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		return festaTest;
	}
	
	public AreaSegurancaProblema criacaoAreaSegurancaProblema() throws Exception {
		AreaSegurancaProblema areaSegurancaProblema = new AreaSegurancaProblema();
		areaSegurancaProblema.setArea(areaTest());
		areaSegurancaProblema.setProblema(criacaoProblema());
		areaSegurancaProblema.setFesta(criacaoFesta());
		areaSegurancaProblema.setDescProblema("teste2");
		areaSegurancaProblema.setHorarioInicio(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		areaSegurancaProblema.setHorarioFim(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		areaSegurancaProblema.setStatusProblema(TipoStatusProblema.ANDAMENTO.getValor());
		areaSegurancaProblema.setCodUsuarioResolv(usuarioTest());
		areaSegurancaProblema.setCodUsuarioEmissor(usuarioTest());
		
		return areaSegurancaProblema;
	}
	
	public Problema criacaoProblema() {
		Problema problema = new Problema();
		problema.setCodProblema(1);
		problema.setDescProblema("Teste");
		return problema;
	}
	
	@SuppressWarnings("deprecation")
	private Usuario usuarioTest(){
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(2);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("1234");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setGenero("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");

		return usuarioTest;
	}

	@Test
	@WithMockUser
	void listaAreaSegurancaFestaSucesso() throws Exception {
		String uri = "/areaSeguranca/lista";

		HashMap<AreaSeguranca, List<AreaSegurancaProblema>> hash = new HashMap<AreaSeguranca, List<AreaSegurancaProblema>>();
		hash.put(areaTest(), new ArrayList<AreaSegurancaProblema>());
		
		Mockito.when(areaSegurancaService.listaAreaSegurancaFesta(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(hash);
		Mockito.when(areaSegurancaFactory.getAreaTo(Mockito.any())).thenReturn(areaTOTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1").param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	@WithMockUser
	void listaAreaSegurancaFestaErro() throws Exception {
		String uri = "/areaSeguranca/lista";

		String expected = "teste";

		Mockito.when(areaSegurancaService.listaAreaSegurancaFesta(Mockito.anyInt(), Mockito.anyInt()))
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
	void criarAreaSegurancaFestaSucesso() throws Exception {
		String uri = "/areaSeguranca/adicionar";

		String json = "{\"codArea\":1,\"codFesta\":3,\"nomeArea\":\"teste123\",\"statusSeguranca\":\"S\"}";

		Mockito.when(areaSegurancaService.criacaoAreSegurancaFesta(Mockito.anyInt(), Mockito.any()))
				.thenReturn(areaTest());
		Mockito.when(areaSegurancaFactory.getArea(Mockito.any())).thenReturn(areaTest());
		Mockito.when(areaSegurancaFactory.getAreaTo(Mockito.any())).thenReturn(areaTOTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(json).param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	@WithMockUser
	void criarAreaSegurancaFestaErro() throws Exception {
		String uri = "/areaSeguranca/adicionar";

		String json = "{\"codArea\":1,\"codFesta\":3,\"nomeArea\":\"teste123\",\"statusSeguranca\":\"S\"}";

		Mockito.when(areaSegurancaService.criacaoAreSegurancaFesta(Mockito.anyInt(), Mockito.any()))
				.thenThrow(new ValidacaoException("teste"));
		Mockito.when(areaSegurancaFactory.getArea(Mockito.any())).thenReturn(areaTest());
		Mockito.when(areaSegurancaFactory.getAreaTo(Mockito.any())).thenReturn(areaTOTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(json).param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals("teste", result.getResponse().getContentAsString());
	}

	@Test
	@WithMockUser
	void atualizarAreaSegurancaFestaSucesso() throws Exception {
		String uri = "/areaSeguranca/atualizar";

		String json = "{\"codArea\":1,\"codFesta\":3,\"nomeArea\":\"teste123\",\"statusSeguranca\":\"S\"}";

		Mockito.when(areaSegurancaService.atualizarAreSegurancaFesta(Mockito.anyInt(), Mockito.any()))
				.thenReturn(areaTest());
		Mockito.when(areaSegurancaFactory.getArea(Mockito.any())).thenReturn(areaTest());
		Mockito.when(areaSegurancaFactory.getAreaTo(Mockito.any())).thenReturn(areaTOTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON).content(json)
				.param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	@WithMockUser
	void atualizarAreaSegurancaFestaErro() throws Exception {
		String uri = "/areaSeguranca/atualizar";

		String json = "{\"codArea\":1,\"codFesta\":3,\"nomeArea\":\"teste123\",\"statusSeguranca\":\"S\"}";

		Mockito.when(areaSegurancaService.atualizarAreSegurancaFesta(Mockito.anyInt(), Mockito.any()))
				.thenThrow(new ValidacaoException("teste"));
		Mockito.when(areaSegurancaFactory.getArea(Mockito.any())).thenReturn(areaTest());
		Mockito.when(areaSegurancaFactory.getAreaTo(Mockito.any())).thenReturn(areaTOTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(uri).accept(MediaType.APPLICATION_JSON).content(json)
				.param("codUsuario", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals("teste", result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser
	void deletarAreaSegurancaFestaSucesso() throws Exception {
		String uri = "/areaSeguranca/delete";

		Mockito.doNothing().when(areaSegurancaService).deletarAreSegurancaFesta(Mockito.anyInt(), Mockito.anyInt());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(uri).accept(MediaType.APPLICATION_JSON)
				.param("codUsuario", "1").param("codArea", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	
	@Test
	@WithMockUser
	void deletarAreaSegurancaFestaErro() throws Exception {
		String uri = "/areaSeguranca/delete";

		doThrow(new ValidacaoException("teste")).when(areaSegurancaService).deletarAreSegurancaFesta(Mockito.anyInt(), Mockito.anyInt());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(uri).accept(MediaType.APPLICATION_JSON)
				.param("codUsuario", "1").param("codArea", "1").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
		assertEquals("teste", result.getResponse().getContentAsString());

	}

}
