package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Estoque;
import com.eventmanager.pachanga.domains.ItemEstoque;
import com.eventmanager.pachanga.domains.Problema;
import com.eventmanager.pachanga.dtos.EstoqueTO;
import com.eventmanager.pachanga.dtos.ItemEstoqueTO;
import com.eventmanager.pachanga.dtos.ProblemaTO;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=ProblemaFactory.class)
class ProblemaFactoryTest {
	
	@Autowired
	private ProblemaFactory problemaFactory;

	@MockBean
	private ItemEstoqueFactory itemEstoqueFactory;
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	
	private Problema problemaTest() {
		 Problema problema = new Problema(); 
		 problema.setCodProblema(1);
		 problema.setDescProblema("Teste");
		 return problema;
	}
	
	private ProblemaTO problemaTOTest() {
		 ProblemaTO problemaTO = new ProblemaTO(); 
		 problemaTO.setCodProblema(1);
		 problemaTO.setDescProblema("Teste");
		 return problemaTO;
	}
	

	@Test
	void getProblemaTOSucesso() throws Exception {
		Problema problema = problemaTest();
		ProblemaTO problemaTO = problemaFactory.getProblemaTO(problema);
		
		assertEquals(problema.getCodProblema(), problemaTO.getCodProblema());
		assertEquals(problema.getDescProblema(), problemaTO.getDescProblema());
	}
	
	@Test
	void getProblemaSucesso() throws Exception {
		ProblemaTO problemaTO = problemaTOTest();
		Problema problema = problemaFactory.getProblema(problemaTO);
		
		assertEquals(problema.getCodProblema(), problemaTO.getCodProblema());
		assertEquals(problema.getDescProblema(), problemaTO.getDescProblema());
	}
	
	@Test
	void getProblemasTOSucesso() throws Exception {
		Problema problema = problemaTest();
		List<Problema> problemas = new ArrayList<>();
		problemas.add(problema);
		
		List<ProblemaTO> problemasTO = problemaFactory.getProblemasTO(problemas);
		ProblemaTO problemaTO = problemasTO.get(0);
		
		assertEquals(problema.getCodProblema(), problemaTO.getCodProblema());
		assertEquals(problema.getDescProblema(), problemaTO.getDescProblema());
	}
}
