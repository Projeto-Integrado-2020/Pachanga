package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.AreaSeguranca;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Problema;
import com.eventmanager.pachanga.dtos.NotificacaoAreaSegurancaTO;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoAreaSeguranca;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;

@RunWith(SpringRunner.class)
@WebMvcTest(value=NotificacaoAreaSegurancaTOFactory.class)
class NotificacaoAreaSegurancaTOFactoryTest {
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@Autowired
	private NotificacaoAreaSegurancaTOFactory notificacaoAreaSegurancaTOFactory;
	
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
		festaTest.setStatusFesta(TipoStatusFesta.PREPARACAO.getValor());
		festaTest.setDescricaoFesta("Bugago");
		festaTest.setHorarioFimFestaReal(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));

		return festaTest;
	}
	
	private AreaSeguranca areaTest() {
		AreaSeguranca area = new AreaSeguranca();
		area.setCodArea(1);
		area.setCodFesta(3);
		area.setNomeArea("teste123");
		area.setStatusSeguranca(TipoAreaSeguranca.SEGURO.getDescricao());
		return area;
	}
	
	private Problema problemaTest() {
		Problema problema = new Problema();
		problema.setCodProblema(1);
		problema.setDescProblema("teste");
		return problema;
	}
	
	@Test
	void getNotificacaoAreaTest(){
		
		Festa festa = festaTest();
		Problema problema = problemaTest();
		AreaSeguranca area = areaTest();
		
		NotificacaoAreaSegurancaTO notificacaoArea = notificacaoAreaSegurancaTOFactory.getNotificacaoArea(festa, problema, area);
		
		assertEquals( notificacaoArea.getCodFesta(), festa.getCodFesta());
		assertEquals( notificacaoArea.getCodProblema(), problema.getCodProblema());
	}

}
