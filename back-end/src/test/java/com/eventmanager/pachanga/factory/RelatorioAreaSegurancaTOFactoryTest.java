package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.dtos.ChamadasEmitidasFuncionarioTO;
import com.eventmanager.pachanga.dtos.RelatorioAreaSegurancaTO;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=RelatorioAreaSegurancaTOFactory.class)
class RelatorioAreaSegurancaTOFactoryTest {
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@Autowired
	private RelatorioAreaSegurancaTOFactory relatorioAreaSegurancaTOFactory;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	@Test
	void getProblemasAreaTest() {
		
		RelatorioAreaSegurancaTO relatorio = relatorioAreaSegurancaTOFactory.getProblemasArea(new LinkedHashMap<String, Integer>());
		
		assertEquals(true, relatorio.getProblemasArea() != null);
	}
	
	@Test
	void getChamadasProblemaTest() {
		
		RelatorioAreaSegurancaTO relatorio = relatorioAreaSegurancaTOFactory.getChamadasProblema(new ArrayList<ChamadasEmitidasFuncionarioTO>());
		
		assertEquals(true, relatorio.getChamadasEmitidasFuncionario() != null);
	}
	
	@Test
	void getChamadasEmitidasTest() {
		
		ChamadasEmitidasFuncionarioTO chamadas = relatorioAreaSegurancaTOFactory.getChamadasEmitidas("teste", new LinkedHashMap<Integer, Integer>(), 1);
		
		assertEquals(true, chamadas != null);
	}
	
	@Test
	void getUsuarioSolucionadorTest() {
		
		RelatorioAreaSegurancaTO relatorio = relatorioAreaSegurancaTOFactory.getUsuarioSolucionador(new LinkedHashMap<String, Integer>());
		
		assertEquals(true, relatorio.getSolucionadorAlertasSeguranca() != null);
	}

}
