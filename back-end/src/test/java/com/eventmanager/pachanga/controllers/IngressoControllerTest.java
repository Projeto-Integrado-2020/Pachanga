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
import com.eventmanager.pachanga.domains.Ingresso;
import com.eventmanager.pachanga.dtos.CategoriaTO;
import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.dtos.IngressoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.FestaFactory;
import com.eventmanager.pachanga.factory.IngressoFactory;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.services.IngressoService;
import com.eventmanager.pachanga.tipo.TipoStatusCompra;
import com.eventmanager.pachanga.tipo.TipoStatusIngresso;

@RunWith(SpringRunner.class)
@WebMvcTest(value = IngressoController.class)
class IngressoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IngressoService ingressoService;

	@MockBean
	private IngressoFactory ingressoFactory;

	@MockBean
	private FestaController festaController;

	@MockBean
	private FestaFactory festaFactory;

	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	private Ingresso ingressoTest() {
		Ingresso ingresso = new Ingresso();
		ingresso.setCodBoleto("abc");
		ingresso.setCodIngresso("aesdad");
		ingresso.setDataCheckin(LocalDateTime.of(2020, Month.SEPTEMBER, 23, 19, 10));
		ingresso.setDataCompra(LocalDateTime.of(2020, Month.SEPTEMBER, 24, 19, 10));
		ingresso.setEmailTitular("a@a.com");
		ingresso.setNomeTitular("teste");
		ingresso.setPreco(12f);
		ingresso.setFesta(festaTest());
		ingresso.setStatusCompra(TipoStatusCompra.COMPRADO.getDescricao());
		ingresso.setStatusIngresso(TipoStatusIngresso.CHECKED.getDescricao());
		return ingresso;
	}
	
	private Festa festaTest() {
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

	private FestaTO festaToTest() {
		FestaTO festaTo = new FestaTO();
		festaTo.setCodEnderecoFesta("https//:minhacasa.org");
		festaTo.setDescOrganizador("sou demente");
		festaTo.setHorarioInicioFesta(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		festaTo.setHorarioFimFesta(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		festaTo.setNomeFesta("festao");
		festaTo.setOrganizador("Joao Neves");
		festaTo.setStatusFesta("I");
		festaTo.setDescricaoFesta("Bugago");
		festaTo.setHorarioFimFestaReal(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		return festaTo;
	}

	@Test
	@WithMockUser
	void getIngressosUserSucesso() throws Exception {

		String uri = "/ingresso/listaUser";

		List<Ingresso> ingressos = new ArrayList<>();
		ingressos.add(ingressoTest());
		ingressos.add(ingressoTest());

		Mockito.when(ingressoService.getIngressosUser(Mockito.anyInt())).thenReturn(ingressos);
		
		Mockito.when(ingressoFactory.getIngressoTO(Mockito.any(), Mockito.any())).thenReturn(new IngressoTO());

		Mockito.when(festaController.categoriaFesta(Mockito.anyInt(), Mockito.anyString()))
				.thenReturn(new CategoriaTO());

		Mockito.when(festaFactory.getFestaTO(Mockito.any(), Mockito.anyList(), Mockito.anyBoolean(), Mockito.any(),
				Mockito.any(), Mockito.anyList())).thenReturn(festaToTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).param("idUser", "2")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	@WithMockUser
	void getIngressosUserErro() throws Exception {

		String uri = "/ingresso/listaUser";

		String expected = "teste";

		List<Ingresso> ingressos = new ArrayList<>();
		ingressos.add(ingressoTest());
		ingressos.add(ingressoTest());

		Mockito.when(ingressoService.getIngressosUser(Mockito.anyInt())).thenThrow(new ValidacaoException(expected));

		Mockito.when(festaController.categoriaFesta(Mockito.anyInt(), Mockito.anyString()))
				.thenReturn(new CategoriaTO());

		Mockito.when(festaFactory.getFestaTO(Mockito.any(), Mockito.anyList(), Mockito.anyBoolean(), Mockito.any(),
				Mockito.any(), Mockito.anyList())).thenReturn(festaToTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).param("idUser", "2")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());

	}
	
	
	@Test
	@WithMockUser
	void getIngressosFestaSucesso() throws Exception {

		String uri = "/ingresso/listaFesta";

		List<Ingresso> ingressos = new ArrayList<>();
		ingressos.add(ingressoTest());
		ingressos.add(ingressoTest());

		Mockito.when(ingressoService.getIngressosFesta(Mockito.anyInt())).thenReturn(ingressos);
		
		Mockito.when(festaFactory.getFestaTO(Mockito.any(), Mockito.anyList(), Mockito.anyBoolean(), Mockito.any(),
				Mockito.any(), Mockito.anyList())).thenReturn(festaToTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).param("idFesta", "2")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	@WithMockUser
	void getIngressosFestarErro() throws Exception {

		String uri = "/ingresso/listaFesta";

		String expected = "teste";

		List<Ingresso> ingressos = new ArrayList<>();
		ingressos.add(ingressoTest());
		ingressos.add(ingressoTest());

		Mockito.when(ingressoService.getIngressosFesta(Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		
		Mockito.when(ingressoFactory.getIngressoTO(Mockito.any(), Mockito.any())).thenReturn(new IngressoTO());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).param("idFesta", "2")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());

	}
	
	@Test
	@WithMockUser
	void addIngressoSucesso() throws Exception {

		String uri = "/ingresso/addIngresso";
		
		String ingressoJson = "{\r\n"
				+ "        \"codIngresso\": \"asdnasdnas231\",\r\n"
				+ "        \"codLote\": 1,\r\n"
				+ "        \"festa\": {\r\n"
				+ "        \"codFesta\": 1,\r\n"
				+ "        \"nomeFesta\": \"teste\",\r\n"
				+ "        \"statusFesta\": \"P\",\r\n"
				+ "        \"organizador\": \"Gustavo Pereira\",\r\n"
				+ "        \"horarioInicioFesta\": \"2020-12-03 20:00:00\",\r\n"
				+ "        \"horarioFimFesta\": \"2020-12-04 12:00:00\",\r\n"
				+ "        \"descricaoFesta\": \"teste\",\r\n"
				+ "        \"codEnderecoFesta\": \"teste\",\r\n"
				+ "        \"descOrganizador\": \"teste\",\r\n"
				+ "        \"horarioFimFestaReal\": null,\r\n"
				+ "        \"funcionalidade\": \"Organizador\",\r\n"
				+ "        \"quantidadeParticipantes\": 1,\r\n"
				+ "        \"usuarios\": null,\r\n"
				+ "        \"codPrimaria\": 0,\r\n"
				+ "        \"codSecundaria\": 0,\r\n"
				+ "        \"categoriaPrimaria\": {\r\n"
				+ "            \"codCategoria\": 2,\r\n"
				+ "            \"nomeCategoria\": \"RAVEAFIM\"\r\n"
				+ "        },\r\n"
				+ "        \"categoriaSecundaria\": {\r\n"
				+ "            \"codCategoria\": 4,\r\n"
				+ "            \"nomeCategoria\": \"ELETRONC\"\r\n"
				+ "        },\r\n"
				+ "        \"isOrganizador\": true,\r\n"
				+ "        \"convidados\": null,\r\n"
				+ "        \"imagem\": null\r\n"
				+ "    },\r\n"
				+ "        \"codUsuario\": 1,\r\n"
				+ "        \"statusIngresso\": \"U\",\r\n"
				+ "        \"preco\": 12.0,\r\n"
				+ "        \"statusCompra\": \"C\",\r\n"
				+ "        \"dataCompra\": \"2020-10-01T14:10:00\",\r\n"
				+ "        \"dataCheckin\": null,\r\n"
				+ "        \"nomeTitular\": \"Andrey Camargo Lacerda\",\r\n"
				+ "        \"emailTitular\": \"guga.72@hotmail.com\",\r\n"
				+ "        \"boleto\": true\r\n"
				+ "    }";


		Mockito.when(ingressoService.addIngresso(Mockito.any(), Mockito.any())).thenReturn(new IngressoTO());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri).content(ingressoJson)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	@WithMockUser
	void addIngressoErro() throws Exception {

		String uri = "/ingresso/addIngresso";
		
		String ingressoJson = "{\r\n"
				+ "        \"codIngresso\": \"asdnasdnas231\",\r\n"
				+ "        \"codLote\": 1,\r\n"
				+ "        \"festa\": {\r\n"
				+ "        \"codFesta\": 1,\r\n"
				+ "        \"nomeFesta\": \"teste\",\r\n"
				+ "        \"statusFesta\": \"P\",\r\n"
				+ "        \"organizador\": \"Gustavo Pereira\",\r\n"
				+ "        \"horarioInicioFesta\": \"2020-12-03 20:00:00\",\r\n"
				+ "        \"horarioFimFesta\": \"2020-12-04 12:00:00\",\r\n"
				+ "        \"descricaoFesta\": \"teste\",\r\n"
				+ "        \"codEnderecoFesta\": \"teste\",\r\n"
				+ "        \"descOrganizador\": \"teste\",\r\n"
				+ "        \"horarioFimFestaReal\": null,\r\n"
				+ "        \"funcionalidade\": \"Organizador\",\r\n"
				+ "        \"quantidadeParticipantes\": 1,\r\n"
				+ "        \"usuarios\": null,\r\n"
				+ "        \"codPrimaria\": 0,\r\n"
				+ "        \"codSecundaria\": 0,\r\n"
				+ "        \"categoriaPrimaria\": {\r\n"
				+ "            \"codCategoria\": 2,\r\n"
				+ "            \"nomeCategoria\": \"RAVEAFIM\"\r\n"
				+ "        },\r\n"
				+ "        \"categoriaSecundaria\": {\r\n"
				+ "            \"codCategoria\": 4,\r\n"
				+ "            \"nomeCategoria\": \"ELETRONC\"\r\n"
				+ "        },\r\n"
				+ "        \"isOrganizador\": true,\r\n"
				+ "        \"convidados\": null,\r\n"
				+ "        \"imagem\": null\r\n"
				+ "    },\r\n"
				+ "        \"codUsuario\": 1,\r\n"
				+ "        \"statusIngresso\": \"U\",\r\n"
				+ "        \"preco\": 12.0,\r\n"
				+ "        \"statusCompra\": \"C\",\r\n"
				+ "        \"dataCompra\": \"2020-10-01T14:10:00\",\r\n"
				+ "        \"dataCheckin\": null,\r\n"
				+ "        \"nomeTitular\": \"Andrey Camargo Lacerda\",\r\n"
				+ "        \"emailTitular\": \"guga.72@hotmail.com\",\r\n"
				+ "        \"boleto\": true\r\n"
				+ "    }";

		String expected = "teste";

		Mockito.when(ingressoService.addIngresso(Mockito.any(), Mockito.any())).thenThrow(new ValidacaoException(expected));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri)
				.content(ingressoJson)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());

	}
	
	
	@Test
	@WithMockUser
	void addIngressoListaSucesso() throws Exception {

		String uri = "/ingresso/addIngressoLista";
		
		String ingressoJson = "{\r\n"
				+ "    \"listaIngresso\": [\r\n"
				+ "        {\r\n"
				+ "        \"codIngresso\": \"asdnasdnas231\",\r\n"
				+ "        \"codLote\": 2,\r\n"
				+ "        \"festa\": {\r\n"
				+ "        \"codFesta\": 1,\r\n"
				+ "        \"nomeFesta\": \"teste\",\r\n"
				+ "        \"statusFesta\": \"P\",\r\n"
				+ "        \"organizador\": \"Gustavo Pereira\",\r\n"
				+ "        \"horarioInicioFesta\": \"2020-12-03 20:00:00\",\r\n"
				+ "        \"horarioFimFesta\": \"2020-12-04 12:00:00\",\r\n"
				+ "        \"descricaoFesta\": \"teste\",\r\n"
				+ "        \"codEnderecoFesta\": \"teste\",\r\n"
				+ "        \"descOrganizador\": \"teste\",\r\n"
				+ "        \"horarioFimFestaReal\": null,\r\n"
				+ "        \"funcionalidade\": \"Organizador\",\r\n"
				+ "        \"quantidadeParticipantes\": 1,\r\n"
				+ "        \"usuarios\": null,\r\n"
				+ "        \"codPrimaria\": 0,\r\n"
				+ "        \"codSecundaria\": 0,\r\n"
				+ "        \"categoriaPrimaria\": {\r\n"
				+ "            \"codCategoria\": 2,\r\n"
				+ "            \"nomeCategoria\": \"RAVEAFIM\"\r\n"
				+ "        },\r\n"
				+ "        \"categoriaSecundaria\": {\r\n"
				+ "            \"codCategoria\": 4,\r\n"
				+ "            \"nomeCategoria\": \"ELETRONC\"\r\n"
				+ "        },\r\n"
				+ "        \"isOrganizador\": true,\r\n"
				+ "        \"convidados\": null,\r\n"
				+ "        \"imagem\": null\r\n"
				+ "    },\r\n"
				+ "        \"codUsuario\": 1,\r\n"
				+ "        \"statusIngresso\": \"U\",\r\n"
				+ "        \"preco\": 12.0,\r\n"
				+ "        \"statusCompra\": \"C\",\r\n"
				+ "        \"dataCompra\": \"2020-10-01T14:10:00\",\r\n"
				+ "        \"dataCheckin\": null,\r\n"
				+ "        \"nomeTitular\": \"Andrey Camargo Lacerda\",\r\n"
				+ "        \"emailTitular\": \"guga.72@hotmail.com\",\r\n"
				+ "        \"boleto\": true\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"codIngresso\": \"asdnasdnas231\",\r\n"
				+ "        \"codLote\": 2,\r\n"
				+ "        \"festa\": {\r\n"
				+ "        \"codFesta\": 1,\r\n"
				+ "        \"nomeFesta\": \"teste\",\r\n"
				+ "        \"statusFesta\": \"P\",\r\n"
				+ "        \"organizador\": \"Gustavo Pereira\",\r\n"
				+ "        \"horarioInicioFesta\": \"2020-12-03 20:00:00\",\r\n"
				+ "        \"horarioFimFesta\": \"2020-12-04 12:00:00\",\r\n"
				+ "        \"descricaoFesta\": \"teste\",\r\n"
				+ "        \"codEnderecoFesta\": \"teste\",\r\n"
				+ "        \"descOrganizador\": \"teste\",\r\n"
				+ "        \"horarioFimFestaReal\": null,\r\n"
				+ "        \"funcionalidade\": \"Organizador\",\r\n"
				+ "        \"quantidadeParticipantes\": 1,\r\n"
				+ "        \"usuarios\": null,\r\n"
				+ "        \"codPrimaria\": 0,\r\n"
				+ "        \"codSecundaria\": 0,\r\n"
				+ "        \"categoriaPrimaria\": {\r\n"
				+ "            \"codCategoria\": 2,\r\n"
				+ "            \"nomeCategoria\": \"RAVEAFIM\"\r\n"
				+ "        },\r\n"
				+ "        \"categoriaSecundaria\": {\r\n"
				+ "            \"codCategoria\": 4,\r\n"
				+ "            \"nomeCategoria\": \"ELETRONC\"\r\n"
				+ "        },\r\n"
				+ "        \"isOrganizador\": true,\r\n"
				+ "        \"convidados\": null,\r\n"
				+ "        \"imagem\": null\r\n"
				+ "    },\r\n"
				+ "        \"codUsuario\": 1,\r\n"
				+ "        \"statusIngresso\": \"U\",\r\n"
				+ "        \"preco\": 12.0,\r\n"
				+ "        \"statusCompra\": \"C\",\r\n"
				+ "        \"dataCompra\": \"2020-10-01T14:10:00\",\r\n"
				+ "        \"dataCheckin\": null,\r\n"
				+ "        \"nomeTitular\": \"Andrey Camargo Lacerda\",\r\n"
				+ "        \"emailTitular\": \"guga.72@hotmail.com\",\r\n"
				+ "        \"boleto\": true\r\n"
				+ "    }\r\n"
				+ "    ],\r\n"
				+ "     \"infoPagamento\":{\r\n"
				+ "         \"pais\": \"Brasil\",\r\n"
				+ "         \"estado\": \"SP\",\r\n"
				+ "         \"cidade\": \"Sao Paulo\",\r\n"
				+ "         \"cep\":\"05171010\",\r\n"
				+ "         \"rua\":\"Rua José Carlos de Resende\",\r\n"
				+ "         \"numero\": \"533\",\r\n"
				+ "         \"bairro\":\"Pirituba\",\r\n"
				+ "         \"cpf\":\"48949888874\",\r\n"
				+ "         \"email\":\"guga.721@hotmail.com\",\r\n"
				+ "         \"nome\":\"Silvana da Costa\"\r\n"
				+ "    }\r\n"
				+ "}";


		Mockito.when(ingressoService.addListaIngresso(Mockito.any())).thenReturn(new ArrayList<IngressoTO>());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri).content(ingressoJson)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	@WithMockUser
	void addIngressoListaErro() throws Exception {

		String uri = "/ingresso/addIngressoLista";
		
		String ingressoJson = "{\r\n"
				+ "    \"listaIngresso\": [\r\n"
				+ "        {\r\n"
				+ "        \"codIngresso\": \"asdnasdnas231\",\r\n"
				+ "        \"codLote\": 2,\r\n"
				+ "        \"festa\": {\r\n"
				+ "        \"codFesta\": 1,\r\n"
				+ "        \"nomeFesta\": \"teste\",\r\n"
				+ "        \"statusFesta\": \"P\",\r\n"
				+ "        \"organizador\": \"Gustavo Pereira\",\r\n"
				+ "        \"horarioInicioFesta\": \"2020-12-03 20:00:00\",\r\n"
				+ "        \"horarioFimFesta\": \"2020-12-04 12:00:00\",\r\n"
				+ "        \"descricaoFesta\": \"teste\",\r\n"
				+ "        \"codEnderecoFesta\": \"teste\",\r\n"
				+ "        \"descOrganizador\": \"teste\",\r\n"
				+ "        \"horarioFimFestaReal\": null,\r\n"
				+ "        \"funcionalidade\": \"Organizador\",\r\n"
				+ "        \"quantidadeParticipantes\": 1,\r\n"
				+ "        \"usuarios\": null,\r\n"
				+ "        \"codPrimaria\": 0,\r\n"
				+ "        \"codSecundaria\": 0,\r\n"
				+ "        \"categoriaPrimaria\": {\r\n"
				+ "            \"codCategoria\": 2,\r\n"
				+ "            \"nomeCategoria\": \"RAVEAFIM\"\r\n"
				+ "        },\r\n"
				+ "        \"categoriaSecundaria\": {\r\n"
				+ "            \"codCategoria\": 4,\r\n"
				+ "            \"nomeCategoria\": \"ELETRONC\"\r\n"
				+ "        },\r\n"
				+ "        \"isOrganizador\": true,\r\n"
				+ "        \"convidados\": null,\r\n"
				+ "        \"imagem\": null\r\n"
				+ "    },\r\n"
				+ "        \"codUsuario\": 1,\r\n"
				+ "        \"statusIngresso\": \"U\",\r\n"
				+ "        \"preco\": 12.0,\r\n"
				+ "        \"statusCompra\": \"C\",\r\n"
				+ "        \"dataCompra\": \"2020-10-01T14:10:00\",\r\n"
				+ "        \"dataCheckin\": null,\r\n"
				+ "        \"nomeTitular\": \"Andrey Camargo Lacerda\",\r\n"
				+ "        \"emailTitular\": \"guga.72@hotmail.com\",\r\n"
				+ "        \"boleto\": true\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"codIngresso\": \"asdnasdnas231\",\r\n"
				+ "        \"codLote\": 2,\r\n"
				+ "        \"festa\": {\r\n"
				+ "        \"codFesta\": 1,\r\n"
				+ "        \"nomeFesta\": \"teste\",\r\n"
				+ "        \"statusFesta\": \"P\",\r\n"
				+ "        \"organizador\": \"Gustavo Pereira\",\r\n"
				+ "        \"horarioInicioFesta\": \"2020-12-03 20:00:00\",\r\n"
				+ "        \"horarioFimFesta\": \"2020-12-04 12:00:00\",\r\n"
				+ "        \"descricaoFesta\": \"teste\",\r\n"
				+ "        \"codEnderecoFesta\": \"teste\",\r\n"
				+ "        \"descOrganizador\": \"teste\",\r\n"
				+ "        \"horarioFimFestaReal\": null,\r\n"
				+ "        \"funcionalidade\": \"Organizador\",\r\n"
				+ "        \"quantidadeParticipantes\": 1,\r\n"
				+ "        \"usuarios\": null,\r\n"
				+ "        \"codPrimaria\": 0,\r\n"
				+ "        \"codSecundaria\": 0,\r\n"
				+ "        \"categoriaPrimaria\": {\r\n"
				+ "            \"codCategoria\": 2,\r\n"
				+ "            \"nomeCategoria\": \"RAVEAFIM\"\r\n"
				+ "        },\r\n"
				+ "        \"categoriaSecundaria\": {\r\n"
				+ "            \"codCategoria\": 4,\r\n"
				+ "            \"nomeCategoria\": \"ELETRONC\"\r\n"
				+ "        },\r\n"
				+ "        \"isOrganizador\": true,\r\n"
				+ "        \"convidados\": null,\r\n"
				+ "        \"imagem\": null\r\n"
				+ "    },\r\n"
				+ "        \"codUsuario\": 1,\r\n"
				+ "        \"statusIngresso\": \"U\",\r\n"
				+ "        \"preco\": 12.0,\r\n"
				+ "        \"statusCompra\": \"C\",\r\n"
				+ "        \"dataCompra\": \"2020-10-01T14:10:00\",\r\n"
				+ "        \"dataCheckin\": null,\r\n"
				+ "        \"nomeTitular\": \"Andrey Camargo Lacerda\",\r\n"
				+ "        \"emailTitular\": \"guga.72@hotmail.com\",\r\n"
				+ "        \"boleto\": true\r\n"
				+ "    }\r\n"
				+ "    ],\r\n"
				+ "     \"infoPagamento\":{\r\n"
				+ "         \"pais\": \"Brasil\",\r\n"
				+ "         \"estado\": \"SP\",\r\n"
				+ "         \"cidade\": \"Sao Paulo\",\r\n"
				+ "         \"cep\":\"05171010\",\r\n"
				+ "         \"rua\":\"Rua José Carlos de Resende\",\r\n"
				+ "         \"numero\": \"533\",\r\n"
				+ "         \"bairro\":\"Pirituba\",\r\n"
				+ "         \"cpf\":\"48949888874\",\r\n"
				+ "         \"email\":\"guga.721@hotmail.com\",\r\n"
				+ "         \"nome\":\"Silvana da Costa\"\r\n"
				+ "    }\r\n"
				+ "}";

		String expected = "teste";

		Mockito.when(ingressoService.addListaIngresso(Mockito.any())).thenThrow(new ValidacaoException(expected));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri)
				.content(ingressoJson)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());

	}
	
	@Test
	@WithMockUser
	void updateChekinSucesso() throws Exception {

		String uri = "/ingresso/updateCheckin";
		
		Mockito.when(ingressoService.updateCheckin(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(new Ingresso());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(uri).param("codIngresso", "2")
				.param("codFesta", "2").param("codUsuario", "2")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		
		Mockito.when(ingressoFactory.getIngressoTO(Mockito.any(), Mockito.any())).thenReturn(new IngressoTO());

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	@WithMockUser
	void updateChekinoErro() throws Exception {

		String uri = "/ingresso/updateCheckin";
		
		String expected = "teste";
		
		Mockito.when(ingressoFactory.getIngressoTO(Mockito.any(), Mockito.any())).thenReturn(new IngressoTO());

		Mockito.when(ingressoService.updateCheckin(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenThrow(new ValidacaoException(expected));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(uri).param("codIngresso", "2")
				.param("codFesta", "2").param("codUsuario", "2")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());

	}
	
	@Test
	@WithMockUser
	void updateStatusCompraSucesso() throws Exception {

		String uri = "/ingresso/updateStatusCompra";
		
		Mockito.doNothing().when(ingressoService).updateStatusCompra(Mockito.anyString(), Mockito.anyString());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri).param("codBoleto", "123")
				.param("notificationType", "asdc").param("notificationCode", "asdcx")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	@WithMockUser
	void updateStatusCompraErro() throws Exception {

		String uri = "/ingresso/updateStatusCompra";
		
		String expected = "teste";
		
		Mockito.when(ingressoFactory.getIngressoTO(Mockito.any(), Mockito.any())).thenReturn(new IngressoTO());

		Mockito.doThrow(new ValidacaoException(expected)).when(ingressoService).updateStatusCompra(Mockito.anyString(), Mockito.anyString());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri).param("codBoleto", "123")
				.param("notificationType", "asdc").param("notificationCode", "asdcx")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());

	}

}
