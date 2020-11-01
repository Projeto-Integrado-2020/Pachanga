package com.eventmanager.pachanga.controllers;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
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
import com.eventmanager.pachanga.dtos.AreaSegurancaProblemaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.AreaSegurancaFactory;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.services.AreaSegurancaProblemaService;
import com.eventmanager.pachanga.tipo.TipoAreaSeguranca;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;
import com.eventmanager.pachanga.tipo.TipoStatusProblema;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AreaSegurancaProblemaController.class)
public class AreaSegurancaProblemaControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AreaSegurancaProblemaService areaSegurancaProblemaService;

	@MockBean
	private AreaSegurancaFactory areaSegurancaFactory;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	
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
	
	public List<AreaSegurancaProblema> criacaoAreasSegurancaProblema() throws Exception {
		List<AreaSegurancaProblema> lista = new ArrayList<>();
		lista.add(criacaoAreaSegurancaProblema());
		return lista;
	}
	
	
	public AreaSegurancaProblemaTO criacaoAreaSegurancaProblemaTO() throws Exception {
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = new AreaSegurancaProblemaTO();
		areaSegurancaProblemaTO.setCodAreaSeguranca(areaTest().getCodArea());
		areaSegurancaProblemaTO.setCodProblema(criacaoProblema().getCodProblema());	
		areaSegurancaProblemaTO.setCodFesta(criacaoFesta().getCodFesta());
		areaSegurancaProblemaTO.setDescProblema("Situação está séria");
		areaSegurancaProblemaTO.setHorarioInicio(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		areaSegurancaProblemaTO.setHorarioFim(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		areaSegurancaProblemaTO.setStatusProblema("A");
		areaSegurancaProblemaTO.setCodUsuarioEmissor(usuarioTest().getCodUsuario());
		areaSegurancaProblemaTO.setCodUsuarioResolv(usuarioTest().getCodUsuario());
		
		return areaSegurancaProblemaTO;
	}
	
	public List<AreaSegurancaProblemaTO> criacaoAreasSegurancaProblemaTO() throws Exception {
		List<AreaSegurancaProblemaTO> listaTO = new ArrayList<>();
		listaTO.add(criacaoAreaSegurancaProblemaTO());
		return listaTO;
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
	
	public Problema criacaoProblema() {
		Problema problema = new Problema();
		problema.setCodProblema(1);
		problema.setDescProblema("Teste");
		return problema;
	}
	
	private AreaSeguranca areaTest() {
		AreaSeguranca area = new AreaSeguranca();
		area.setCodArea(1);
		area.setCodFesta(3);
		area.setNomeArea("teste123");
		area.setStatusSeguranca(TipoAreaSeguranca.SEGURO.getDescricao());
		return area;
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
	void adicionarSucesso() throws Exception {
		String uri = "/areaSegurancaProblema/adicionar";

		String expected = "{\"codAreaSeguranca\": 1,\"codFesta\": 1,\"codProblema\": 1,\"codUsuarioResolv\": 2,\"statusProblema\": \"A\",\"horarioInicio\": \"2016-06-22T19:10:00\",\"horarioFim\":  \"2016-06-23T19:10:00\",\"codUsuarioEmissor\": 2,\"descProblema\": \"teste2\"}";
		String json = "{\"codAreaSeguranca\": 1,\"codFesta\": 1,\"codProblema\": 1,\"codUsuarioResolv\": 2,\"statusProblema\": \"A\",\"horarioInicio\": \"2016-06-22T19:10:00\",\"horarioFim\":  \"2018-06-22T19:10:00\",\"codUsuarioEmissor\": 2,\"descProblema\": \"teste2\"}";
		
		Mockito.when(areaSegurancaProblemaService.addProblemaSeguranca(Mockito.any(AreaSegurancaProblemaTO.class), Mockito.anyInt())).thenReturn(criacaoAreaSegurancaProblema());

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("idUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	@WithMockUser
	void adicionarErro() throws Exception {
		String uri = "/areaSegurancaProblema/adicionar";

		String expected = "erro";
		String json = "{\"codAreaSeguranca\": 1,\"codFesta\": 1,\"codProblema\": 1,\"codUsuarioResolv\": 2,\"statusProblema\": \"A\",\"horarioInicio\": \"2016-06-22T19:10:00\",\"horarioFim\":  \"2018-06-22T19:10:00\",\"codUsuarioEmissor\": 2,\"descProblema\": \"teste2\"}";

		Mockito.when(areaSegurancaProblemaService.addProblemaSeguranca(Mockito.any(AreaSegurancaProblemaTO.class), Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri).accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("idUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());
	}
	

	@Test
	@WithMockUser
	void atualizarSucesso() throws Exception {
		String uri = "/areaSegurancaProblema/atualizar";

		String expected = "{\"codAreaSeguranca\": 1,\"codFesta\": 1,\"codProblema\": 1,\"codUsuarioResolv\": 2,\"statusProblema\": \"A\",\"horarioInicio\": \"2016-06-22T19:10:00\",\"horarioFim\":  \"2016-06-23T19:10:00\",\"codUsuarioEmissor\": 2,\"descProblema\": \"teste2\"}";
		String json = "{\"codAreaSeguranca\": 1,\"codFesta\": 1,\"codProblema\": 1,\"codUsuarioResolv\": 2,\"statusProblema\": \"A\",\"horarioInicio\": \"2016-06-22T19:10:00\",\"horarioFim\":  \"2018-06-22T19:10:00\",\"codUsuarioEmissor\": 2,\"descProblema\": \"teste2\"}";
		
		Mockito.when(areaSegurancaProblemaService.updateProblemaSeguranca(Mockito.any(AreaSegurancaProblemaTO.class), Mockito.anyInt())).thenReturn(criacaoAreaSegurancaProblema());

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri).accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("idUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	@WithMockUser
	void atualizarErro() throws Exception {
		String uri = "/areaSegurancaProblema/atualizar";

		String expected = "erro";
		String json = "{\"codAreaSeguranca\": 1,\"codFesta\": 1,\"codProblema\": 1,\"codUsuarioResolv\": 2,\"statusProblema\": \"A\",\"horarioInicio\": \"2016-06-22T19:10:00\",\"horarioFim\":  \"2018-06-22T19:10:00\",\"codUsuarioEmissor\": 2,\"descProblema\": \"teste2\"}";

		Mockito.when(areaSegurancaProblemaService.updateProblemaSeguranca(Mockito.any(AreaSegurancaProblemaTO.class), Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri).accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("idUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser
	void removerSucesso() throws Exception {
		String uri = "/areaSegurancaProblema/remover";

		String expected = "{\"codAreaSeguranca\": 1,\"codFesta\": 1,\"codProblema\": 1,\"codUsuarioResolv\": 2,\"statusProblema\": \"A\",\"horarioInicio\": \"2016-06-22T19:10:00\",\"horarioFim\":  \"2016-06-23T19:10:00\",\"codUsuarioEmissor\": 2,\"descProblema\": \"teste2\"}";
		Mockito.when(areaSegurancaProblemaService.deleteProblemaSeguranca(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoAreaSegurancaProblema());

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri).accept(MediaType.APPLICATION_JSON)
				.param("codAreaSeguranca", "1")
				.param("codProblema", "1")
				.param("codFesta", "1")
				.param("idUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	@WithMockUser
	void removerErro() throws Exception {
		String uri = "/areaSegurancaProblema/remover";

		String expected = "erro";

		Mockito.when(areaSegurancaProblemaService.deleteProblemaSeguranca(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri).accept(MediaType.APPLICATION_JSON)
				.param("codAreaSeguranca", "1")
				.param("codProblema", "1")
				.param("codFesta", "1")
				.param("idUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser
	void findProblemaSegurancaSucesso() throws Exception {
		String uri = "/areaSegurancaProblema/findProblemaSeguranca";

		String expected = "{\"codAreaSeguranca\": 1,\"codFesta\": 1,\"codProblema\": 1,\"codUsuarioResolv\": 2,\"statusProblema\": \"A\",\"horarioInicio\": \"2016-06-22T19:10:00\",\"horarioFim\":  \"2016-06-23T19:10:00\",\"codUsuarioEmissor\": 2,\"descProblema\": \"teste2\"}";

		Mockito.when(areaSegurancaProblemaService.findProblemaSeguranca(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoAreaSegurancaProblema());

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codAreaSeguranca", "1")
				.param("codProblema", "1")
				.param("codFesta", "1")
				.param("idUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	@WithMockUser
	void findProblemaSegurancaErro() throws Exception {
		String uri = "/areaSegurancaProblema/findProblemaSeguranca";

		String expected = "erro";

		Mockito.when(areaSegurancaProblemaService.findProblemaSeguranca(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codAreaSeguranca", "1")
				.param("codProblema", "1")
				.param("codFesta", "1")
				.param("idUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser
	void findAllProblemasSegurancaAreaSucesso() throws Exception {
		String uri = "/areaSegurancaProblema/findAllProblemasSegurancaArea";

		String expected = "[{\"codAreaSeguranca\": 1,\"codFesta\": 1,\"codProblema\": 1,\"codUsuarioResolv\": 2,\"statusProblema\": \"A\",\"horarioInicio\": \"2016-06-22T19:10:00\",\"horarioFim\":  \"2016-06-23T19:10:00\",\"codUsuarioEmissor\": 2,\"descProblema\": \"teste2\"}]";

		Mockito.when(areaSegurancaProblemaService.findAllProblemasSegurancaArea(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoAreasSegurancaProblema());

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codAreaSeguranca", "1")
				.param("codFesta", "1")
				.param("idUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	@WithMockUser
	void findAllProblemasSegurancaAreaErro() throws Exception {
		String uri = "/areaSegurancaProblema/findAllProblemasSegurancaArea";

		String expected = "erro";
		
		Mockito.when(areaSegurancaProblemaService.findAllProblemasSegurancaArea(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenThrow(new ValidacaoException(expected));

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codAreaSeguranca", "1")
				.param("codFesta", "1")
				.param("idUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser
	void findAllProblemasSegurancaFestaSucesso() throws Exception {
		String uri = "/areaSegurancaProblema/findAllProblemasSegurancaFesta";

		String expected = "[{\"codAreaSeguranca\": 1,\"codFesta\": 1,\"codProblema\": 1,\"codUsuarioResolv\": 2,\"statusProblema\": \"A\",\"horarioInicio\": \"2016-06-22T19:10:00\",\"horarioFim\":  \"2016-06-23T19:10:00\",\"codUsuarioEmissor\": 2,\"descProblema\": \"teste2\"}]";

		Mockito.when(areaSegurancaProblemaService.findAllProblemasSegurancaFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoAreasSegurancaProblema());

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1")
				.param("idUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	@WithMockUser
	void findAllProblemasSegurancaFestaErro() throws Exception {
		String uri = "/areaSegurancaProblema/findAllProblemasSegurancaFesta";

		String expected = "erro";
		
		Mockito.when(areaSegurancaProblemaService.findAllProblemasSegurancaFesta(Mockito.anyInt(), Mockito.anyInt())).thenThrow(new ValidacaoException(expected));

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri).accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1")
				.param("idUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());
	}

}
