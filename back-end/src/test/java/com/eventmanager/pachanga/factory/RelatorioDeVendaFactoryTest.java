package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.dtos.InfoLucroFesta;
import com.eventmanager.pachanga.dtos.RelatorioDeVendaTO;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RelatorioDeVendaFactory.class)
class RelatorioDeVendaFactoryTest {

	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@Autowired
	private RelatorioDeVendaFactory relatorioDeVendaFactory;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	@MockBean
	private AreaSegurancaProblemaFactory areaSegurancaProblemaFactory;

	private InfoLucroFesta infoLucroFestaTest() {
		InfoLucroFesta infoLucro = new InfoLucroFesta();
		infoLucro.setLucroTotal(120f);
		Map<String, Float> lucroLote = new HashMap<>();
		lucroLote.put("teste", 2f);
		infoLucro.setLucroLote(lucroLote);
		return infoLucro;
	}

	@Test
	void getRelatorioDeVendaTest() {
		Map<String, Integer> ingressos = new HashMap<>();
		ingressos.put("teste", 1);

		RelatorioDeVendaTO relatorio = relatorioDeVendaFactory.getRelatorioDeVenda(ingressos);

		assertEquals(true, relatorio != null);
	}

	@Test
	void getIngressosPagosCompradosTest() {
		Map<Integer, Integer> pagosComprados = new HashMap<>();
		pagosComprados.put(1, 2);

		Map<String, Map<Integer, Integer>> ingressos = new HashMap<>();
		ingressos.put("teste", pagosComprados);

		RelatorioDeVendaTO relatorio = relatorioDeVendaFactory.getIngressosPagosComprados(ingressos);

		assertEquals(true, relatorio != null);
	}

	@Test
	void getRelatorioLucroFestaTest() {
		Map<String, Float> lucroLote = new HashMap<>();
		lucroLote.put("teste", 2f);

		InfoLucroFesta infoRel = relatorioDeVendaFactory.getRelatorioLucroFesta(1f, lucroLote);

		assertEquals(true, infoRel != null);

	}

	@Test
	void getRelatorioLucroTotalFestaTest() {

		RelatorioDeVendaTO relatorio = relatorioDeVendaFactory.getRelatorioLucroTotalFesta(infoLucroFestaTest(),
				infoLucroFestaTest(), "teste");

		assertEquals(true, relatorio != null);
	}

}
