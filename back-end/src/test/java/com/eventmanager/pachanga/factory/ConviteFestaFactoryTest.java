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
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.dtos.ConviteFestaTO;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;

@RunWith(SpringRunner.class)
@WebMvcTest(value=ConviteFestaFactory.class)
class ConviteFestaFactoryTest {
	
	@Autowired
	ConviteFestaFactory conviteFestaFactory;
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	public Festa festaTest() throws Exception{
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
	
	public Grupo grupoTest() throws Exception {
		Grupo grupo = new Grupo();
		grupo.setCodGrupo(1);
		grupo.setNomeGrupo("CONVIDADO");
		grupo.setFesta(festaTest());
		grupo.setOrganizador(false);
		return grupo;
	}
	

	
	@Test
	void getConviteFestaTOSucesso() throws Exception {
		Festa festa = festaTest();
		Grupo grupo = grupoTest();
		
		ConviteFestaTO conviteFestaTO = conviteFestaFactory.getConviteFestaTO(festa, grupo);
		
		assertEquals( conviteFestaTO.getEnderecoFesta(), festa.getCodEnderecoFesta());
		assertEquals( conviteFestaTO.getHorarioFinal(), festa.getHorarioFimFesta());
		assertEquals( conviteFestaTO.getHorarioInicial(), festa.getHorarioInicioFesta());
		assertEquals( conviteFestaTO.getNomeFesta(), festa.getNomeFesta());
		assertEquals( conviteFestaTO.getNomeGrupo(), grupo.getNomeGrupo());
		//assertEquals( , );
	}
}
