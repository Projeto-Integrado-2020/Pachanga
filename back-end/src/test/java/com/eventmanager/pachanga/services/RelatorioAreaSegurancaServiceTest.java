package com.eventmanager.pachanga.services;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.dtos.RelatorioAreaSegurancaTO;
import com.eventmanager.pachanga.factory.RelatorioAreaSegurancaTOFactory;
import com.eventmanager.pachanga.repositories.AreaSegurancaProblemaFluxoRepository;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RelatorioAreaSegurancaService.class)
class RelatorioAreaSegurancaServiceTest {

	@Autowired
	private RelatorioAreaSegurancaService relatorioAreaSegurancaService;

	@MockBean
	private FestaService festaService;

	@MockBean
	private GrupoService grupoService;

	@MockBean
	private RelatorioAreaSegurancaTOFactory relatorioAreaSegurancaTOFactory;

	@MockBean
	private AreaSegurancaProblemaFluxoRepository areaSegurancaProblemaFluxoRepository;

	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	private RelatorioAreaSegurancaTO relatorioAreaTest() {
		RelatorioAreaSegurancaTO relatorioArea = new RelatorioAreaSegurancaTO();
		relatorioArea.setProblemasArea(new LinkedHashMap<String, Integer>());
		relatorioArea.setChamadasEmitidasFuncionario(new LinkedHashMap<String, Map<Integer, Integer>>());
		relatorioArea.setSolucionadorAlertasSeguranca(new LinkedHashMap<String, Integer>());
		return relatorioArea;
	}

	@Test
	void relatorioProblemasAreaSucesso() throws Exception {

		List<Object[]> informacoesAreaProblema = new ArrayList<>();
		informacoesAreaProblema.add(new Object[] { 1, "teste" });

		Mockito.when(areaSegurancaProblemaFluxoRepository.findAreasByIdFesta(Mockito.anyInt()))
				.thenReturn(informacoesAreaProblema);
		Mockito.when(areaSegurancaProblemaFluxoRepository.findQuantidadeProblemasByCodArea(Mockito.anyInt()))
				.thenReturn(1);
		Mockito.when(relatorioAreaSegurancaTOFactory.getProblemasArea(Mockito.anyMap()))
				.thenReturn(relatorioAreaTest());

		RelatorioAreaSegurancaTO relatorioArea = relatorioAreaSegurancaService.relatorioProblemasArea(1, 2);

		assertEquals(true, relatorioArea.getProblemasArea() != null);

	}

	@Test
	void relatorioChamadasUsuarioSucesso() throws Exception {

		List<Object[]> informacoesUsuario = new ArrayList<>();
		informacoesUsuario.add(new Object[] { 1, "teste" });

		Mockito.when(areaSegurancaProblemaFluxoRepository.findUsuariosByIdFesta(Mockito.anyInt()))
				.thenReturn(informacoesUsuario);
		Mockito.when(areaSegurancaProblemaFluxoRepository.findQuantidadeProblemasEmitidosByUsuario(Mockito.anyInt(),
				Mockito.anyString(), Mockito.anyInt())).thenReturn(1, 2);
		Mockito.when(relatorioAreaSegurancaTOFactory.getChamadasProblema(Mockito.anyMap()))
				.thenReturn(relatorioAreaTest());

		RelatorioAreaSegurancaTO relatorioArea = relatorioAreaSegurancaService.relatorioChamadasUsuario(1, 2);

		assertEquals(true, relatorioArea.getChamadasEmitidasFuncionario() != null);

	}

	@Test
	void relatorioUsuarioSolucionadorSucesso() throws Exception {

		List<Object[]> informacoesUsuario = new ArrayList<>();
		informacoesUsuario.add(new Object[] { 1, "teste" });

		Mockito.when(areaSegurancaProblemaFluxoRepository.findUsuariosByIdFesta(Mockito.anyInt()))
				.thenReturn(informacoesUsuario);
		Mockito.when(areaSegurancaProblemaFluxoRepository.countProblemasFesta(Mockito.anyInt())).thenReturn(10f);
		Mockito.when(areaSegurancaProblemaFluxoRepository.findQuantidadeChamadasResolvidasByUsuario(Mockito.anyInt(),
				Mockito.anyInt())).thenReturn(1);
		Mockito.when(relatorioAreaSegurancaTOFactory.getUsuarioSolucionador(Mockito.anyMap()))
				.thenReturn(relatorioAreaTest());

		RelatorioAreaSegurancaTO relatorioArea = relatorioAreaSegurancaService.relatorioUsuarioSolucionador(1, 2);

		assertEquals(true, relatorioArea.getSolucionadorAlertasSeguranca() != null);

	}

}
