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

import com.eventmanager.pachanga.domains.Cupom;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.dtos.CupomTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.CupomFactory;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.services.CupomService;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;

@RunWith(SpringRunner.class)
@WebMvcTest(value=CupomController.class)
class CupomControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CupomService cupomService;
	
	@MockBean
	private CupomFactory cupomFactory;
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	
	private Cupom gerarCupom() throws Exception {
		Cupom cupom = new Cupom();
		cupom.setCodCupom(1);
		cupom.setNomeCupom("Cupom");
		cupom.setFesta(festaTest());
		cupom.setPrecoDesconto((float) 1.75);
		return cupom;
	}
	
	private CupomTO gerarCupomTO() throws Exception {
		CupomTO cupom = new CupomTO();
		cupom.setCodCupom(1);
		cupom.setNomeCupom("Cupom");
		cupom.setCodFesta(2);
		cupom.setPrecoDesconto(1.75f);
		return cupom;
	}
	
	private List<CupomTO> gerarListDeCuponsTO() throws Exception {
		List<CupomTO> cupons = new ArrayList<>();
		cupons.add(gerarCupomTO());
		return cupons;
	}
	
	private List<Cupom> gerarListDeCupons() throws Exception {
		Cupom cupom = new Cupom();
		cupom.setCodCupom(1);
		cupom.setNomeCupom("Cupom");
		cupom.setFesta(festaTest());
		cupom.setPrecoDesconto((float) 1.75);
		List<Cupom> cupons = new ArrayList<>();
		cupons.add(cupom);
		return cupons;
	}
	
	private Festa festaTest() throws Exception{
		Festa festaTest = new Festa();
		festaTest.setCodFesta(2);
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

	@Test
	@WithMockUser
	void getCupomSucesso() throws Exception {
		String uri = "/cupom/cupomUnico";
	
		Mockito.when(cupomService.getCupom("teste", 1)).thenReturn(gerarCupom());
		Mockito.when(cupomFactory.getCupomTO(Mockito.any())).thenReturn(gerarCupomTO());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.param("nomeCupom", "teste")
				.param("codFesta", "1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
	
	@Test
	@WithMockUser
	void getCupomErro() throws Exception {
		String uri = "/cupom/cupomUnico";
	
		Mockito.when(cupomService.getCupom("teste", 1)).thenThrow(new ValidacaoException("teste"));
		Mockito.when(cupomFactory.getCupomTO(Mockito.any())).thenReturn(gerarCupomTO());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.param("codCupom", "1")
				.param("codFesta", "1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

	}
	
	@Test
	@WithMockUser
	void getCuponsFestaSucesso() throws Exception {
		String uri = "/cupom/cuponsFesta";
	
		Mockito.when(cupomService.getCuponsFesta( 2, 1)).thenReturn(gerarListDeCupons());
		Mockito.when(cupomFactory.getCuponsTO(Mockito.anyList())).thenReturn(gerarListDeCuponsTO());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.param("codFesta", "2")
				.param("codUser", "1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
	
	@Test
	@WithMockUser
	void getCuponsFestaErro() throws Exception {
		String uri = "/cupom/cuponsFesta";
	
		String expected = "erro";

		Mockito.when(cupomService.getCuponsFesta(2, 1)).thenThrow(new ValidacaoException(expected));
		Mockito.when(cupomFactory.getCuponsTO(Mockito.anyList())).thenReturn(gerarListDeCuponsTO());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.param("codFesta", "2")
				.param("codUser", "1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser
	void removeCupomSucesso() throws Exception {
		String uri = "/cupom/excluir";
	
		Mockito.doNothing().when(cupomService).removeCupom( 1, 1);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.param("codCupom", "1")
				.param("codUser", "1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
	
	@Test
	@WithMockUser
	void removeCupomErro() throws Exception {
		String uri = "/cupom/excluir";
	
		String expected = "erro";

		Mockito.doThrow(new ValidacaoException(expected)).when(cupomService).removeCupom( 1, 1);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)	
				.param("codCupom", "1")
				.param("codUser", "1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser
	void criarCupomSucesso() throws Exception {
		String uri = "/cupom/gerar";
	
		String json = "{\"codCupom\": 1,\"nomeCupom\": \"Cupom\",\"codFesta\": 2,\"precoDesconto\": 1.75}";

		Mockito.when(cupomService.gerarCupom(Mockito.any(CupomTO.class), Mockito.anyInt())).thenReturn(gerarCupom());
		Mockito.when(cupomFactory.getCupomTO(Mockito.any())).thenReturn(gerarCupomTO());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.param("codUser", "1")
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	@WithMockUser
	void criarCupomErro() throws Exception {
		String uri = "/cupom/gerar";
	
		String json = "{\"codCupom\": 1,\"nomeCupom\": \"Cupom\",\"codFesta\": 2,\"precoDesconto\": 1.75}";
		String expected = "erro";

		Mockito.when(cupomService.gerarCupom(Mockito.any(CupomTO.class), Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		Mockito.when(cupomFactory.getCupomTO(Mockito.any())).thenReturn(gerarCupomTO());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.content(json)
				.param("codUser", "1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser
	void updateCupomSucesso() throws Exception {
		String uri = "/cupom/atualizar";
	
		String json = "{\"codCupom\": 1,\"nomeCupom\": \"Cupom\",\"codFesta\": 2,\"precoDesconto\": 1.75}";

		Mockito.when(cupomService.atualizarCupom(Mockito.any(CupomTO.class), Mockito.anyInt())).thenReturn(gerarCupom());
		Mockito.when(cupomFactory.getCupomTO(Mockito.any())).thenReturn(gerarCupomTO());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.param("codUser", "1")
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	@WithMockUser
	void updateCupomErro() throws Exception {
		String uri = "/cupom/atualizar";
	
		String json = "{\"codCupom\": 1,\"nomeCupom\": \"Cupom\",\"codFesta\": 2,\"precoDesconto\": 1.75}";
		String expected = "erro";

		Mockito.when(cupomService.atualizarCupom(Mockito.any(CupomTO.class), Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		Mockito.when(cupomFactory.getCupomTO(Mockito.any())).thenReturn(gerarCupomTO());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.content(json)
				.param("codUser", "1")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());
	}

}
