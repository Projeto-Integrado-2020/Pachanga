package com.eventmanager.pachanga.controllers;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
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

import com.eventmanager.pachanga.domains.Lote;
import com.eventmanager.pachanga.dtos.LoteTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.LoteFactory;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.services.LoteService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = LoteController.class)
class LoteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LoteService loteService;
	
	@MockBean
	private LoteFactory loteFactory;

	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	private LoteTO loteToTest() {
		LoteTO loteTo = new LoteTO();
		loteTo.setCodLote(1);
		;
		loteTo.setCodFesta(2);
		loteTo.setDescLote("lote vip krl");
		loteTo.setNomeLote("Teste");
		loteTo.setHorarioInicio(LocalDateTime.of(2020, Month.SEPTEMBER, 23, 19, 10));
		loteTo.setHorarioFim(LocalDateTime.of(2020, Month.SEPTEMBER, 24, 19, 10));
		loteTo.setNumeroLote(1);
		loteTo.setPreco(17.2f);
		loteTo.setQuantidade(100);
		return loteTo;
	}

	private Lote loteTest() {
		Lote lote = new Lote();
		lote.setCodLote(1);
		lote.setDescLote("lote vip krl");
		lote.setNomeLote("Teste");
		lote.setHorarioInicio(LocalDateTime.of(2020, Month.SEPTEMBER, 23, 19, 10));
		lote.setHorarioFim(LocalDateTime.of(2020, Month.SEPTEMBER, 24, 19, 10));
		lote.setNumeroLote(1);
		lote.setPreco(17.2f);
		lote.setQuantidade(100);
		return lote;
	}

	@Test
	@WithMockUser
	void listaLoteSucesso() throws Exception {

		String uri = "/lote/lista";

		String expected = "[{\"codLote\":1,\"codFesta\":2,\"numeroLote\":1,\"quantidade\":100,\"preco\":17.2,\"nomeLote\":\"Teste\",\"descLote\":\"lote vip krl\",\"horarioInicio\":\"2020-09-23T19:10:00\",\"horarioFim\":\"2020-09-24T19:10:00\"}]";

		List<Lote> lotes = new ArrayList<>();
		lotes.add(loteTest());

		Mockito.when(loteService.listaLoteFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(lotes);
		
		Mockito.when(loteFactory.getLoteTO(Mockito.any())).thenReturn(loteToTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).param("codFesta", "2").param("codUsuario", "1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);

	}
	
	@Test
	@WithMockUser
	void listaLoteErro() throws Exception {

		String uri = "/lote/lista";

		String expected = "teste";

		List<Lote> lotes = new ArrayList<>();
		lotes.add(loteTest());

		Mockito.when(loteService.listaLoteFesta(Mockito.anyInt(), Mockito.anyInt())).thenThrow(new ValidacaoException("teste"));
		
		Mockito.when(loteFactory.getLoteTO(Mockito.any())).thenReturn(loteToTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).param("codFesta", "2").param("codUsuario", "1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());

	}
	
	@Test
	@WithMockUser
	void adicionaraLoteSucesso() throws Exception {

		String uri = "/lote/adicionar";

		String expected = "{\"codLote\":1,\"codFesta\":2,\"numeroLote\":1,\"quantidade\":100,\"preco\":17.2,\"nomeLote\":\"Teste\",\"descLote\":\"lote vip krl\",\"horarioInicio\":\"2020-09-23T19:10:00\",\"horarioFim\":\"2020-09-24T19:10:00\"}";
		String json = "{\"codLote\":1,\"codFesta\":2,\"numeroLote\":1,\"quantidade\":100,\"preco\":17.2,\"nomeLote\":\"Teste\",\"descLote\":\"lote vip krl\",\"horarioInicio\":\"2020-09-23T19:10:00\",\"horarioFim\":\"2020-09-24T19:10:00\"}";

		Mockito.when(loteService.adicionarLote(Mockito.any(), Mockito.anyInt())).thenReturn(loteTest());
		
		Mockito.when(loteFactory.getLoteTO(Mockito.any())).thenReturn(loteToTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri).param("codUsuario", "1").content(json)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);

	}
	
	@Test
	@WithMockUser
	void adicionaraLoteErro() throws Exception {

		String uri = "/lote/adicionar";

		String expected = "teste";
		String json = "{\"codLote\":1,\"codFesta\":2,\"numeroLote\":1,\"quantidade\":100,\"preco\":17.2,\"nomeLote\":\"Teste\",\"descLote\":\"lote vip krl\",\"horarioInicio\":\"2020-09-23T19:10:00\",\"horarioFim\":\"2020-09-24T19:10:00\"}";

		Mockito.when(loteService.adicionarLote(Mockito.any(), Mockito.anyInt())).thenThrow(new ValidacaoException("teste"));
		
		Mockito.when(loteFactory.getLoteTO(Mockito.any())).thenReturn(loteToTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri).param("codUsuario", "1").content(json)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());

	}
	
	@Test
	@WithMockUser
	void atualizarLoteSucesso() throws Exception {

		String uri = "/lote/atualizar";

		String expected = "{\"codLote\":1,\"codFesta\":2,\"numeroLote\":1,\"quantidade\":100,\"preco\":17.2,\"nomeLote\":\"Teste\",\"descLote\":\"lote vip krl\",\"horarioInicio\":\"2020-09-23T19:10:00\",\"horarioFim\":\"2020-09-24T19:10:00\"}";
		String json = "{\"codLote\":1,\"codFesta\":2,\"numeroLote\":1,\"quantidade\":100,\"preco\":17.2,\"nomeLote\":\"Teste\",\"descLote\":\"lote vip krl\",\"horarioInicio\":\"2020-09-23T19:10:00\",\"horarioFim\":\"2020-09-24T19:10:00\"}";

		Mockito.when(loteService.atualizarLote(Mockito.any(), Mockito.anyInt())).thenReturn(loteTest());
		
		Mockito.when(loteFactory.getLoteTO(Mockito.any())).thenReturn(loteToTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(uri).param("codUsuario", "1").content(json)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);

	}
	
	@Test
	@WithMockUser
	void atualizarLoteErro() throws Exception {

		String uri = "/lote/atualizar";

		String expected = "teste";
		String json = "{\"codLote\":1,\"codFesta\":2,\"numeroLote\":1,\"quantidade\":100,\"preco\":17.2,\"nomeLote\":\"Teste\",\"descLote\":\"lote vip krl\",\"horarioInicio\":\"2020-09-23T19:10:00\",\"horarioFim\":\"2020-09-24T19:10:00\"}";

		Mockito.when(loteService.atualizarLote(Mockito.any(), Mockito.anyInt())).thenThrow(new ValidacaoException("teste"));
		
		Mockito.when(loteFactory.getLoteTO(Mockito.any())).thenReturn(loteToTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(uri).param("codUsuario", "1").content(json)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());

	}
	
	@Test
	@WithMockUser
	void removerLoteSucesso() throws Exception {

		String uri = "/lote/remover";

		Mockito.doNothing().when(loteService).removerLote(Mockito.anyInt(), Mockito.anyInt());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(uri).param("codUsuario", "1").param("codLote", "1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	
	@Test
	@WithMockUser
	void removerLoteErro() throws Exception {

		String uri = "/lote/remover";

		Mockito.doThrow(new ValidacaoException("teste")).when(loteService).removerLote(Mockito.anyInt(), Mockito.anyInt());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(uri).param("codUsuario", "1").param("codLote", "1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
		assertEquals("teste", result.getResponse().getContentAsString());

	}
	
	@Test
	@WithMockUser
	void encontrarLoteSucesso() throws Exception {

		String uri = "/lote/loteUnico";

		String expected = "{\"codLote\":1,\"codFesta\":2,\"numeroLote\":1,\"quantidade\":100,\"preco\":17.2,\"nomeLote\":\"Teste\",\"descLote\":\"lote vip krl\",\"horarioInicio\":\"2020-09-23T19:10:00\",\"horarioFim\":\"2020-09-24T19:10:00\"}";

		Mockito.when(loteService.encontrarLote(Mockito.anyInt(), Mockito.anyInt())).thenReturn(loteTest());
		
		Mockito.when(loteFactory.getLoteTO(Mockito.any())).thenReturn(loteToTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).param("codUsuario", "1").param("codLote","1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);

	}
	
	@Test
	@WithMockUser
	void encontrarLoteErro() throws Exception {

		String uri = "/lote/loteUnico";

		String expected = "teste";

		Mockito.when(loteService.encontrarLote(Mockito.anyInt(), Mockito.anyInt())).thenThrow(new ValidacaoException("teste"));
		
		Mockito.when(loteFactory.getLoteTO(Mockito.any())).thenReturn(loteToTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).param("codUsuario", "1").param("codLote","1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());

	}
	
	@Test
	@WithMockUser
	void encontrarLoteUnicoDadosPublicosSucesso() throws Exception {

		String uri = "/lote/loteUnicoDadosPublicos";

		String expected = "{\"codLote\":1,\"codFesta\":2,\"numeroLote\":1,\"quantidade\":100,\"preco\":17.2,\"nomeLote\":\"Teste\",\"descLote\":\"lote vip krl\",\"horarioInicio\":\"2020-09-23T19:10:00\",\"horarioFim\":\"2020-09-24T19:10:00\"}";

		Mockito.when(loteService.encontrarLoteDadosPublicos(Mockito.anyInt())).thenReturn(loteTest());
		
		Mockito.when(loteFactory.getLoteTO(Mockito.any())).thenReturn(loteToTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).param("codLote","1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);

	}
	
	@Test
	@WithMockUser
	void encontrarLoteUnicoDadosPublicosErro() throws Exception {

		String uri = "/lote/loteUnicoDadosPublicos";

		String expected = "teste";

		Mockito.when(loteService.encontrarLoteDadosPublicos(Mockito.anyInt())).thenThrow(new ValidacaoException("teste"));
		
		Mockito.when(loteFactory.getLoteTO(Mockito.any())).thenReturn(loteToTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).param("codLote","1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());

	}
	
	@Test
	@WithMockUser
	void listaLotedisponiveisSucesso() throws Exception {

		String uri = "/lote/disponiveis";

		String expected = "[{\"codLote\":1,\"codFesta\":2,\"numeroLote\":1,\"quantidade\":100,\"preco\":17.2,\"nomeLote\":\"Teste\",\"descLote\":\"lote vip krl\",\"horarioInicio\":\"2020-09-23T19:10:00\",\"horarioFim\":\"2020-09-24T19:10:00\"}]";

		List<Lote> lotes = new ArrayList<>();
		lotes.add(loteTest());

		Mockito.when(loteService.encontrarLotesCompraveisFesta(Mockito.anyInt())).thenReturn(lotes);
		
		Mockito.when(loteFactory.getLoteTO(Mockito.any())).thenReturn(loteToTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).param("codFesta", "2").param("codUsuario", "1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);

	}
	
	@Test
	@WithMockUser
	void listaLotedisponiveisErro() throws Exception {

		String uri = "/lote/disponiveis";

		String expected = "teste";

		List<Lote> lotes = new ArrayList<>();
		lotes.add(loteTest());

		Mockito.when(loteService.encontrarLotesCompraveisFesta(Mockito.anyInt())).thenThrow(new ValidacaoException("teste"));
		
		Mockito.when(loteFactory.getLoteTO(Mockito.any())).thenReturn(loteToTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).param("codFesta", "2").param("codUsuario", "1")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());

	}

	
	
}
