package com.eventmanager.pachanga.services;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
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

import com.eventmanager.pachanga.domains.AreaSeguranca;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.RelatorioAreaSegurancaTO;
import com.eventmanager.pachanga.factory.RelatorioAreaSegurancaTOFactory;
import com.eventmanager.pachanga.repositories.AreaSegurancaProblemaRepository;
import com.eventmanager.pachanga.repositories.AreaSegurancaRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoAreaSeguranca;

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
	private AreaSegurancaProblemaRepository areaSegurancaProblemaRepository;

	@MockBean
	private AreaSegurancaRepository areaSegurancaRepository;

	@MockBean
	private RelatorioAreaSegurancaTOFactory relatorioAreaSegurancaTOFactory;

	@MockBean
	private UsuarioRepository usuarioRepository;

	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	private AreaSeguranca areaTest() {
		AreaSeguranca area = new AreaSeguranca();
		area.setCodArea(1);
		area.setCodFesta(3);
		area.setNomeArea("teste123");
		area.setStatusSeguranca(TipoAreaSeguranca.SEGURO.getDescricao());
		return area;
	}

	private RelatorioAreaSegurancaTO relatorioAreaTest() {
		RelatorioAreaSegurancaTO relatorioArea = new RelatorioAreaSegurancaTO();
		relatorioArea.setProblemasArea(new LinkedHashMap<String, Integer>());
		relatorioArea.setChamadasEmitidasFuncionario(new LinkedHashMap<String, Map<Integer, Integer>>());
		relatorioArea.setSolucionadorAlertasSeguranca(new LinkedHashMap<String, Integer>());
		return relatorioArea;
	}

	@SuppressWarnings("deprecation")
	private Usuario usuarioTest() {
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("1234");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setGenero("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");

		return usuarioTest;
	}

	@Test
	void relatorioProblemasAreaSucesso() throws Exception {

		List<AreaSeguranca> areas = new ArrayList<>();
		areas.add(areaTest());

		Mockito.when(areaSegurancaRepository.findAllAreasByCodFesta(Mockito.anyInt())).thenReturn(areas);
		Mockito.when(areaSegurancaProblemaRepository.findQuantidadeProblemasByCodArea(Mockito.anyInt())).thenReturn(1);
		Mockito.when(relatorioAreaSegurancaTOFactory.getProblemasArea(Mockito.anyMap()))
				.thenReturn(relatorioAreaTest());

		RelatorioAreaSegurancaTO relatorioArea = relatorioAreaSegurancaService.relatorioProblemasArea(1, 2);

		assertEquals(true, relatorioArea.getProblemasArea() != null);

	}

	@Test
	void relatorioChamadasUsuarioSucesso() throws Exception {

		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuarioTest());

		Mockito.when(usuarioRepository.findByIdFesta(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.when(areaSegurancaProblemaRepository.findQuantidadeProblemasEmitidosByUsuario(Mockito.anyInt(),
				Mockito.anyString(), Mockito.anyInt())).thenReturn(1, 2);
		Mockito.when(relatorioAreaSegurancaTOFactory.getChamadasProblema(Mockito.anyMap()))
				.thenReturn(relatorioAreaTest());

		RelatorioAreaSegurancaTO relatorioArea = relatorioAreaSegurancaService.relatorioChamadasUsuario(1, 2);

		assertEquals(true, relatorioArea.getChamadasEmitidasFuncionario() != null);

	}

	@Test
	void relatorioUsuarioSolucionadorSucesso() throws Exception {

		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuarioTest());

		Mockito.when(usuarioRepository.findByIdFesta(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.when(areaSegurancaProblemaRepository.countProblemasFesta(Mockito.anyInt())).thenReturn(10);
		Mockito.when(areaSegurancaProblemaRepository.findQuantidadeChamadasResolvidasByUsuario(Mockito.anyInt()))
				.thenReturn(1);
		Mockito.when(relatorioAreaSegurancaTOFactory.getUsuarioSolucionador(Mockito.anyMap()))
		.thenReturn(relatorioAreaTest());

		RelatorioAreaSegurancaTO relatorioArea = relatorioAreaSegurancaService.relatorioUsuarioSolucionador(1, 2);

		assertEquals(true, relatorioArea.getSolucionadorAlertasSeguranca() != null);

	}

}
