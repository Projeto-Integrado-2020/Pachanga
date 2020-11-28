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

import com.eventmanager.pachanga.domains.DadoBancario;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.dtos.DadoBancarioTO;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;

@RunWith(SpringRunner.class)
@WebMvcTest(value = DadoBancarioFactory.class)
class DadoBancarioFactoryTest {

	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@Autowired
	private DadoBancarioFactory dadoBancarioFactory;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	private DadoBancario dadoBancarioTest() {
		DadoBancario dado = new DadoBancario();
		dado.setCodAgencia(1);
		dado.setCodBanco("abc");
		dado.setCodConta(1);
		dado.setCodDadosBancario(1);
		dado.setFesta(festaTest());
		return dado;
	}

	private Festa festaTest() {
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

	private DadoBancarioTO dadoBancarioTOTest() {
		DadoBancarioTO dado = new DadoBancarioTO();
		dado.setCodAgencia(1);
		dado.setCodBanco("abc");
		dado.setCodConta(1);
		dado.setCodDadosBancario(1);
		dado.setCodFesta(1);
		return dado;
	}

	@Test
	void getDadoBancarioSucess() {

		DadoBancario dadoBancario = dadoBancarioFactory.getDadoBancario(dadoBancarioTOTest(), festaTest());

		assertEquals(true, dadoBancario.getFesta() != null);

	}

	@Test
	void getDadoBancarioTOSucess() {

		DadoBancarioTO dadoBancario = dadoBancarioFactory.getDadoBancarioTO(dadoBancarioTest());

		assertEquals(true, dadoBancario.getCodFesta() == 2);

	}

}
