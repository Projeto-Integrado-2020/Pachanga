package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.AreaSeguranca;
import com.eventmanager.pachanga.dtos.AreaSegurancaTO;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoAreaSeguranca;

@RunWith(SpringRunner.class)
@WebMvcTest(value=AreaSegurancaFactory.class)
class AreaSegurancaFactoryTest {
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@Autowired
	private AreaSegurancaFactory areaSegurancaFactory;
	
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

	private AreaSegurancaTO areaTOTest() {
		AreaSegurancaTO area = new AreaSegurancaTO();
		area.setCodArea(1);
		area.setCodFesta(3);
		area.setNomeArea("teste123");
		area.setStatusSeguranca(TipoAreaSeguranca.SEGURO.getDescricao());
		return area;
	}
	
	@Test
	void getAreaToTest() {
		
		AreaSeguranca area = areaTest();
		
		AreaSegurancaTO areaTo = areaSegurancaFactory.getAreaTo(area);
		
		assertEquals( areaTo.getCodArea(), area.getCodArea());
		assertEquals( areaTo.getCodArea(), area.getCodArea());
		
	}
	
	@Test
	void getAreaTest() {
		
		AreaSegurancaTO areaTo = areaTOTest();
		
		AreaSeguranca area = areaSegurancaFactory.getArea(areaTo);
		
		assertEquals( areaTo.getCodArea(), area.getCodArea());
		assertEquals( areaTo.getCodArea(), area.getCodArea());
		
	}

}
