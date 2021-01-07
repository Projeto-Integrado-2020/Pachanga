package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.dtos.RelatorioCheckInTO;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=RelatorioCheckInFactory.class)
class RelatorioCheckInFactoryTest {
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@Autowired
	private RelatorioCheckInFactory relatorioCheckInFactory;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	@Test
	void relatorioIngressosCompradosEntradasTest() {
		
		RelatorioCheckInTO relatorio = relatorioCheckInFactory.relatorioIngressosCompradosEntradas(new LinkedHashMap<String, Map<Integer, Integer>>());
		
		assertEquals(true, relatorio.getIngressosLoteFesta() != null);
		
	}
	
	@Test
	void relatorioFaixaEtariaFestaTest() {
		
		RelatorioCheckInTO relatorio = relatorioCheckInFactory.relatorioFaixaEtariaFesta(new LinkedHashMap<Integer, Integer>());
		
		assertEquals(true, relatorio.getQuantitadeFaixaEtaria() != null);
		
	}
	
	@Test
	void relatorioGeneroFestaTest() {
		
		RelatorioCheckInTO relatorio = relatorioCheckInFactory.relatorioGeneroFesta(new LinkedHashMap<String, Integer>());
		
		assertEquals(true, relatorio.getQuantidadeGenero() != null);
		
	}
	
	@Test
	void relatorioEntradaHoraTest() {
		
		RelatorioCheckInTO relatorio = relatorioCheckInFactory.relatorioEntradaHora(new LinkedHashMap<String, Integer>());
		
		assertEquals(true, relatorio.getQuantidadePessoasHora() != null);
		
	}
	
	@Test
	void relatorioCheckedUncheckedTest() {
		
		RelatorioCheckInTO relatorio = relatorioCheckInFactory.relatorioCheckedUnchecked(new LinkedHashMap<String, Map<Integer, Integer>>());
		
		assertEquals(true, relatorio.getIngressoFestaCheckedUnchecked() != null);
		
	}

}
