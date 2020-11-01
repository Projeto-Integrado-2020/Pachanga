package com.eventmanager.pachanga.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Problema;
import com.eventmanager.pachanga.repositories.ProblemaRepository;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;


@RunWith(SpringRunner.class)
@WebMvcTest(value=ProblemaSevice.class)
public class ProblemaServiceTest {
	
	@MockBean
	private ProblemaRepository problemaRepository;
	
	@Autowired
	private ProblemaSevice problemaSevice;
	
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

	@Test
	void findProblemaSegurancaSucesso() throws Exception {
		List<Problema> problemas = new ArrayList<>();
		problemas.add(criacaoProblema());
		
		Mockito.when(problemaRepository.findAllProblemas()).thenReturn(problemas);

		List<Problema> retorno = problemaSevice.listarProblemas();
		
		assertEquals(retorno.get(0), problemas.get(0));
		
	}
}
