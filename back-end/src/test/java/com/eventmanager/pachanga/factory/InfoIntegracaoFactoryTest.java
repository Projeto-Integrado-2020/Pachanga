package com.eventmanager.pachanga.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.InfoIntegracao;
import com.eventmanager.pachanga.dtos.InfoIntegracaoTO;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoTerceiroIntegracao;

@RunWith(SpringRunner.class)
@WebMvcTest(value=InfoIntegracaoFactory.class)
class InfoIntegracaoFactoryTest {
	
	@Autowired
	private InfoIntegracaoFactory infoIntegracaoFactory;
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	private Festa festaTest(){
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
	void getInfoIntegracaoSucessoTest() {
		InfoIntegracao info = infoIntegracaoFactory.getInfoIntegracao(infoTOTest(), festaTest());
		
		assertEquals(true, info.getCodEvent().equals(infoTest().getCodEvent()));
	}
	
	@Test
	void getInfoIntegracaoTOSucessoTest() {
		InfoIntegracaoTO infoTo = infoIntegracaoFactory.getInfoIntegracaoTO(infoTest());
		
		assertEquals(true, infoTo.getCodEvent().equals(infoTOTest().getCodEvent()));
	}

}
