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

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Lote;
import com.eventmanager.pachanga.dtos.LoteTO;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;

@RunWith(SpringRunner.class)
@WebMvcTest(value=LoteFactory.class)
class LoteFactoryTest {
	
	@Autowired
	private LoteFactory loteFactory;

	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	private LoteTO loteToTest() {
		LoteTO loteTo = new LoteTO();
		loteTo.setCodLote(1);
		loteTo.setCodFesta(2);
		loteTo.setDescLote("lote vip krl");
		loteTo.setNomeLote("Teste");
		loteTo.setHorarioInicio(LocalDateTime.of(2020, Month.SEPTEMBER, 23, 19, 10));
		loteTo.setHorarioFim(LocalDateTime.of(2020, Month.SEPTEMBER, 24, 19, 10));
		loteTo.setNumeroLote(1);
		loteTo.setPreco(17.2f);
		loteTo.setQuantidade(100);
		return loteTo;
	}

	private Lote loteTest() {
		Lote lote = new Lote();
		lote.setCodLote(1);
		lote.setDescLote("lote vip krl");
		lote.setNomeLote("Teste");
		lote.setHorarioInicio(LocalDateTime.of(2020, Month.SEPTEMBER, 23, 19, 10));
		lote.setHorarioFim(LocalDateTime.of(2020, Month.SEPTEMBER, 24, 19, 10));
		lote.setNumeroLote(1);
		lote.setPreco(17.2f);
		lote.setQuantidade(100);
		lote.setFesta(festaTest());
		return lote;
	}
	
	public Festa festaTest() {
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
	
	@Test
	void getLoteToTest() {
		
		Lote lote= loteTest();
		
		LoteTO loteTo = loteFactory.getLoteTO(lote);
		
		assertEquals( loteTo.getCodLote(), lote.getCodLote());
		assertEquals( loteTo.getDescLote(), lote.getDescLote());
		
	}
	
	@Test
	void getLoteTest() {
		
		LoteTO loteTo= loteToTest();
		
		Lote lote = loteFactory.getLote(loteTo, festaTest());
		
		assertEquals( loteTo.getCodLote(), lote.getCodLote());
		assertEquals( loteTo.getDescLote(), lote.getDescLote());
		
	}

}
