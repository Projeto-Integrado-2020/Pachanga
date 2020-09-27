package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Convidado;
import com.eventmanager.pachanga.dtos.ConvidadoTO;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=ConvidadoFactory.class)
class ConvidadoFactoryTest {

	@Autowired
	ConvidadoFactory convidadoFactory;
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	private Convidado convidadoTest() {
		Convidado convidado = new Convidado();
		convidado.setCodConvidado(1);
		convidado.setEmail("test@email.com");
		return convidado;
	}

	@Test
	void getConvidadosTOSucesso() throws Exception {
		Convidado convidado = convidadoTest();
		List<Convidado> convidados = new ArrayList<>();
		convidados.add(convidado);

		List<ConvidadoTO> convidadosTO = convidadoFactory.getConvidadosTO(convidados);
		ConvidadoTO convidadoTO = convidadosTO.get(0);

		assertEquals(convidadoTO.getCodConvidado(), convidado.getCodConvidado());
		assertEquals(convidadoTO.getEmail(), convidado.getEmail());

	}

}
