package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Cupom;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.dtos.CupomTO;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;

@RunWith(SpringRunner.class)
@WebMvcTest(value=CupomFactory.class)
class CupomFactoryTest {
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@Autowired
	private CupomFactory cupomFactory;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	private Cupom gerarCupom() throws Exception {
		Cupom cupom = new Cupom();
		cupom.setCodCupom(1);
		cupom.setNomeCupom("Cupom");
		cupom.setFesta(festaTest());
		cupom.setPrecoDesconto((float) 1.75);
		return cupom;
	}
	
	private List<Cupom> gerarListDeCupons() throws Exception {
		Cupom cupom = new Cupom();
		cupom.setCodCupom(1);
		cupom.setNomeCupom("Cupom");
		cupom.setFesta(festaTest());
		cupom.setPrecoDesconto((float) 1.75);
		List<Cupom> cupons = new ArrayList<>();
		cupons.add(cupom);
		return cupons;
	}
	
	private Festa festaTest() throws Exception{
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
	
	private CupomTO gerarCupomTO() throws Exception {
		CupomTO cupomTO = new CupomTO();
		cupomTO.setCodCupom(1);
		cupomTO.setNomeCupom("Cupom");
		cupomTO.setCodFesta(festaTest().getCodFesta());
		cupomTO.setPrecoDesconto((float) 1.75);
		return cupomTO;
	}
	
	@Test
	void getCupomTOSucesso() throws Exception {
		Cupom cupom = gerarCupom();
		
		CupomTO retorno = cupomFactory.getCupomTO(cupom);
		
		assertEquals( retorno.getCodCupom(), cupom.getCodCupom());
		assertEquals( retorno.getNomeCupom(), cupom.getNomeCupom());
		assertEquals( retorno.getCodFesta(), cupom.getFesta().getCodFesta());
	}
	
	@Test
	void getCupomSucesso() throws Exception {
		CupomTO cupomTO = gerarCupomTO();
		Festa festa = festaTest();
		
		Cupom retorno = cupomFactory.getCupom(cupomTO, festa);
		
		assertEquals( retorno.getCodCupom(), cupomTO.getCodCupom());
		assertEquals( retorno.getNomeCupom(), cupomTO.getNomeCupom());
		assertEquals( retorno.getFesta().getCodFesta(), cupomTO.getCodFesta());
	}
	
	@Test
	void getCuponsTOSucesso() throws Exception {
		List<Cupom> cupons =  gerarListDeCupons();
		
		List<CupomTO> cuponsTO = cupomFactory.getCuponsTO(cupons);
		CupomTO retorno = cuponsTO.get(0);
		Cupom cupom = cupons.get(0);
		
		assertEquals( retorno.getCodCupom(), cupom.getCodCupom());
		assertEquals( retorno.getNomeCupom(), cupom.getNomeCupom());
		assertEquals( retorno.getCodFesta(), cupom.getFesta().getCodFesta());
	}
	
	
}
