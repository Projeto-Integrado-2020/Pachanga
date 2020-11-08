package com.eventmanager.pachanga.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.InfoIntegracao;
import com.eventmanager.pachanga.dtos.InfoIntegracaoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.InfoIntegracaoFactory;
import com.eventmanager.pachanga.repositories.InfoIntegracaoRepository;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoTerceiroIntegracao;

@RunWith(SpringRunner.class)
@WebMvcTest(value = InfoIntegracaoService.class)
class InfoIntegracaoServiceTest {

	@Autowired
	private InfoIntegracaoService infoIntegracaoService;

	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	@MockBean
	private InfoIntegracaoRepository infoIntegracaoRepository;

	@MockBean
	private InfoIntegracaoFactory infoIntegracaoFactory;

	@MockBean
	private FestaService festaService;

	@MockBean
	private GrupoService grupoService;

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

	private InfoIntegracao infoTest() {
		InfoIntegracao info = new InfoIntegracao();
		info.setCodEvent("teste");
		info.setCodInfo(1);
		info.setFesta(festaTest());
		info.setTerceiroInt(TipoTerceiroIntegracao.SYMPLA.getValor());
		info.setToken("teste");
		return info;
	}

	private InfoIntegracaoTO infoTOTest() {
		InfoIntegracaoTO infoTo = new InfoIntegracaoTO();
		infoTo.setCodEvent("teste");
		infoTo.setCodInfo(1);
		infoTo.setCodFesta(2);
		infoTo.setTerceiroInt(TipoTerceiroIntegracao.SYMPLA.getValor());
		infoTo.setToken("teste");
		return infoTo;
	}

	@Test
	void listaInfoIntegracaoFestaSucesso() {
		Mockito.when(infoIntegracaoRepository.findAllInfosFesta(Mockito.anyInt()))
				.thenReturn(new ArrayList<InfoIntegracao>());

		List<InfoIntegracao> infos = infoIntegracaoService.listaInfoIntegracaoFesta(1, 2);

		assertEquals(true, infos.isEmpty());
	}

	@Test
	void infoIntegracaoFestaSucesso() {
		Mockito.when(infoIntegracaoRepository.findInfoFesta(Mockito.anyInt(), Mockito.anyString()))
				.thenReturn(infoTest());

		InfoIntegracao info = infoIntegracaoService.infoIntegracaoFesta(infoTOTest(), 2);

		assertEquals(true, infoTest().getTerceiroInt().equals(info.getTerceiroInt()));
	}

	@Test
	void infoIntegracaoFestaErroInfoNaoEncontrada() {
		Mockito.when(infoIntegracaoRepository.findInfoFesta(Mockito.anyInt(), Mockito.anyString())).thenReturn(null);

		String mensagem = "";

		try {
			infoIntegracaoService.infoIntegracaoFesta(infoTOTest(), 2);
		} catch (ValidacaoException e) {
			mensagem = e.getMessage();
		}

		assertEquals(true, "INFONFOU".equals(mensagem));
	}

	@Test
	void infoIntegracaoFestaErroInfoTerceiroErrado() {
		Mockito.when(infoIntegracaoRepository.findInfoFesta(Mockito.anyInt(), Mockito.anyString()))
				.thenReturn(infoTest());

		InfoIntegracaoTO infoTo = infoTOTest();
		infoTo.setTerceiroInt("A");

		String mensagem = "";

		try {
			infoIntegracaoService.infoIntegracaoFesta(infoTo, 2);
		} catch (ValidacaoException e) {
			mensagem = e.getMessage();
		}

		assertEquals(true, "TERCINCO".equals(mensagem));
	}

	@Test
	void adicionarinfoIntegracaoFestaSucesso() {
		Mockito.when(infoIntegracaoRepository.findInfoFesta(Mockito.anyInt(), Mockito.anyString())).thenReturn(null);
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(infoIntegracaoFactory.getInfoIntegracao(Mockito.any(), Mockito.any())).thenReturn(infoTest());
		Mockito.when(infoIntegracaoRepository.getNextValMySequence()).thenReturn(1);

		InfoIntegracao info = infoIntegracaoService.adicionarinfoIntegracaoFesta(infoTOTest(), 2);

		assertEquals(true, infoTest().getTerceiroInt().equals(info.getTerceiroInt()));
	}

	@Test
	void adicionarinfoIntegracaoFestaErroInfoTerceiroCadastrada() {
		Mockito.when(infoIntegracaoRepository.findInfoFesta(Mockito.anyInt(), Mockito.anyString())).thenReturn(infoTest());
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(infoIntegracaoFactory.getInfoIntegracao(Mockito.any(), Mockito.any())).thenReturn(infoTest());
		Mockito.when(infoIntegracaoRepository.getNextValMySequence()).thenReturn(1);

		String mensagem = "";

		try {
			infoIntegracaoService.adicionarinfoIntegracaoFesta(infoTOTest(), 2);
		} catch (ValidacaoException e) {
			mensagem = e.getMessage();
		}

		assertEquals(true, "INFOTCAD".equals(mensagem));

	}
	
	@Test
	void atualizarinfoIntegracaoFestaSucesso() {
		Mockito.when(infoIntegracaoRepository.findInfoFesta(Mockito.anyInt(), Mockito.anyString())).thenReturn(infoTest());
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(infoIntegracaoFactory.getInfoIntegracao(Mockito.any(), Mockito.any())).thenReturn(infoTest());

		InfoIntegracao info = infoIntegracaoService.atualizarinfoIntegracaoFesta(infoTOTest(), 2);

		assertEquals(true, infoTest().getTerceiroInt().equals(info.getTerceiroInt()));
	}
	
	@Test
	void deleteinfoIntegracaoFestaSucesso() {
		Mockito.when(infoIntegracaoRepository.findByCodInfo(Mockito.anyInt())).thenReturn(infoTest());

		infoIntegracaoService.deleteinfoIntegracaoFesta(1, 2);

	}

}
