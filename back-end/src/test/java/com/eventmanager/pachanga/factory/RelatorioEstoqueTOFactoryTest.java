package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.dtos.InformacoesRelatorioEstoqueTO;
import com.eventmanager.pachanga.dtos.RelatorioEstoqueTO;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=RelatorioEstoqueTOFactory.class)
class RelatorioEstoqueTOFactoryTest {
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@Autowired
	private RelatorioEstoqueTOFactory relatorioEstoqueTOFactory;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	@Test
	void getRelatorioEstoqueTest() {
		
		RelatorioEstoqueTO relatorio = relatorioEstoqueTOFactory.getRelatorioEstoque("teste123", new ArrayList<InformacoesRelatorioEstoqueTO>());
		
		assertEquals(true, relatorio.getInformacoesEstoque() != null);
		assertEquals(true, "teste123".equals(relatorio.getNomeEstoque()));
		
	}
	
	@Test
	void getInformacoesRelatorioEstoqueTest() {
		
		InformacoesRelatorioEstoqueTO infoRelatorio = relatorioEstoqueTOFactory.getInformacoesRelatorioEstoque("teste123", new LinkedHashMap<LocalDateTime, Integer>());
		
		assertEquals(true, infoRelatorio.getQuantidadeHora()!= null);
		assertEquals(true, "teste123".equals(infoRelatorio.getNomeProduto() ));
		
	}
	
	@Test
	void getInformacaoRelatorioConsumoTest() {
		
		InformacoesRelatorioEstoqueTO infoRelatorio = relatorioEstoqueTOFactory.getInformacaoRelatorioConsumo("teste123", 1);
		
		assertEquals(true, infoRelatorio.getQuantidadeConsumo() == 1);
		assertEquals(true, "teste123".equals(infoRelatorio.getNomeProduto() ));
		
	}

}
