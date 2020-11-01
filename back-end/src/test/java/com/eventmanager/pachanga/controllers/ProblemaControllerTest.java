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
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eventmanager.pachanga.domains.Problema;
import com.eventmanager.pachanga.dtos.ProblemaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.ProblemaFactory;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.services.ProblemaSevice;

@RunWith(SpringRunner.class)
@WebMvcTest(value=ProblemaController.class)
public class ProblemaControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProblemaFactory problemaFactory;
	
	@MockBean
	private ProblemaSevice problemaService;  
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	public Problema criacaoProblema() {
		Problema problema = new Problema();
		problema.setCodProblema(1);
		problema.setDescProblema("Teste");
		return problema;
	} 
	
	public ProblemaTO criacaoProblemaTO() {
		ProblemaTO problemaTO = new ProblemaTO();
		problemaTO.setCodProblema(1);
		problemaTO.setDescProblema("Teste");
		return problemaTO;
	} 
	
	@Test
	@WithMockUser
	void findAllProblemasSegurancaAreaSucesso() throws Exception {
		List<Problema> problemas = new ArrayList<>();
		problemas.add(criacaoProblema());
		List<ProblemaTO> problemasTO = new ArrayList<>();
		problemasTO.add(criacaoProblemaTO());
		
		String uri = "/problema/lista";

		String expected = "[{\"codProblema\": 1,\"descProblema\": \"Teste\"}]";

		Mockito.when(problemaService.listarProblemas()).thenReturn(problemas);
		Mockito.when(problemaFactory.getProblemasTO(problemas)).thenReturn(problemasTO);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	@WithMockUser
	void findAllProblemasSegurancaAreaErro() throws Exception {
		String uri = "/problema/lista";

		String expected = "erro";
		
		Mockito.when(problemaService.listarProblemas()).thenThrow(new ValidacaoException(expected));

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

		assertEquals(expected, result.getResponse().getContentAsString());
	}
}
