package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.AreaSeguranca;
import com.eventmanager.pachanga.domains.AreaSegurancaProblema;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Problema;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.AreaSegurancaProblemaTO;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoAreaSeguranca;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;
import com.eventmanager.pachanga.tipo.TipoStatusProblema;

@RunWith(SpringRunner.class)
@WebMvcTest(value=AreaSegurancaProblemaFactory.class)
class AreaSegurancaProblemaFactoryTest {
	
	@Autowired
	private AreaSegurancaProblemaFactory areaSegurancaProblemaFactory;
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	
	public AreaSegurancaProblema criacaoAreaSegurancaProblema() throws Exception {
		AreaSegurancaProblema areaSegurancaProblema = new AreaSegurancaProblema();
		areaSegurancaProblema.setArea(areaTest());
		areaSegurancaProblema.setProblema(criacaoProblema());
		areaSegurancaProblema.setFesta(criacaoFesta());
		areaSegurancaProblema.setDescProblema("teste2");
		areaSegurancaProblema.setHorarioInicio(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		areaSegurancaProblema.setHorarioFim(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		areaSegurancaProblema.setStatusProblema(TipoStatusProblema.ANDAMENTO.getValor());
		areaSegurancaProblema.setCodUsuarioResolv(usuarioTest());
		areaSegurancaProblema.setCodUsuarioEmissor(usuarioTest());
		
		return areaSegurancaProblema;
	}
	
	public AreaSegurancaProblemaTO criacaoAreaSegurancaProblemaTO() throws Exception {
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = new AreaSegurancaProblemaTO();
		areaSegurancaProblemaTO.setCodAreaSeguranca(areaTest().getCodArea());
		areaSegurancaProblemaTO.setCodProblema(criacaoProblema().getCodProblema());	
		areaSegurancaProblemaTO.setCodFesta(criacaoFesta().getCodFesta());
		areaSegurancaProblemaTO.setDescProblema("Situação está séria");
		areaSegurancaProblemaTO.setHorarioInicio(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		areaSegurancaProblemaTO.setHorarioFim(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		areaSegurancaProblemaTO.setStatusProblema("A");
		areaSegurancaProblemaTO.setCodUsuarioEmissor(usuarioTest().getCodUsuario());
		areaSegurancaProblemaTO.setCodUsuarioResolv(usuarioTest().getCodUsuario());
		
		return areaSegurancaProblemaTO;
	}
	
	private Festa criacaoFesta() throws Exception{
		Festa festaTest = new Festa();
		festaTest.setCodFesta(1);
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
	
	public Problema criacaoProblema() {
		Problema problema = new Problema();
		problema.setCodProblema(1);
		problema.setDescProblema("Teste");
		return problema;
	}
	
	private AreaSeguranca areaTest() {
		AreaSeguranca area = new AreaSeguranca();
		area.setCodArea(1);
		area.setCodFesta(3);
		area.setNomeArea("teste123");
		area.setStatusSeguranca(TipoAreaSeguranca.SEGURO.getDescricao());
		return area;
	}
	
	@SuppressWarnings("deprecation")
	private Usuario usuarioTest(){
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(2);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("1234");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setGenero("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");

		return usuarioTest;
	}
	@Test
	void getAreaSegurancaProblemaTOSucesso() throws Exception {
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = areaSegurancaProblemaFactory.getAreaSegurancaProblemaTO(areaSegurancaProblema);
		
		assertEquals(areaSegurancaProblemaTO.getCodAreaSeguranca(), areaSegurancaProblema.getArea().getCodArea());
		assertEquals(areaSegurancaProblemaTO.getCodFesta(), areaSegurancaProblema.getFesta().getCodFesta());
		assertEquals(areaSegurancaProblemaTO.getCodProblema(), areaSegurancaProblema.getProblema().getCodProblema());
		assertEquals(areaSegurancaProblemaTO.getCodUsuarioEmissor(), areaSegurancaProblema.getCodUsuarioEmissor().getCodUsuario());
		assertEquals(areaSegurancaProblemaTO.getCodUsuarioResolv(), areaSegurancaProblema.getCodUsuarioResolv().getCodUsuario());
		assertEquals(areaSegurancaProblemaTO.getDescProblemaEmissor(), areaSegurancaProblema.getDescProblema());
		assertEquals(areaSegurancaProblemaTO.getHorarioFim(), areaSegurancaProblema.getHorarioFim());
		assertEquals(areaSegurancaProblemaTO.getHorarioInicio(), areaSegurancaProblema.getHorarioInicio());
		assertEquals(areaSegurancaProblemaTO.getStatusProblema(), areaSegurancaProblema.getStatusProblema());

	}
	@Test
	void getAreaSegurancaProblemaTOSucessoUsuarioResolvNull() throws Exception {
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		areaSegurancaProblema.setCodUsuarioResolv(null);
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = areaSegurancaProblemaFactory.getAreaSegurancaProblemaTO(areaSegurancaProblema);
		
		assertEquals(areaSegurancaProblemaTO.getCodAreaSeguranca(), areaSegurancaProblema.getArea().getCodArea());
		assertEquals(areaSegurancaProblemaTO.getCodFesta(), areaSegurancaProblema.getFesta().getCodFesta());
		assertEquals(areaSegurancaProblemaTO.getCodProblema(), areaSegurancaProblema.getProblema().getCodProblema());
		assertEquals(areaSegurancaProblemaTO.getCodUsuarioEmissor(), areaSegurancaProblema.getCodUsuarioEmissor().getCodUsuario());
		assertEquals(areaSegurancaProblemaTO.getCodUsuarioResolv(), 0);
		assertEquals(areaSegurancaProblemaTO.getDescProblemaEmissor(), areaSegurancaProblema.getDescProblema());
		assertEquals(areaSegurancaProblemaTO.getHorarioFim(), areaSegurancaProblema.getHorarioFim());
		assertEquals(areaSegurancaProblemaTO.getHorarioInicio(), areaSegurancaProblema.getHorarioInicio());
		assertEquals(areaSegurancaProblemaTO.getStatusProblema(), areaSegurancaProblema.getStatusProblema());

	}
	
	@Test
	void getAreaSegurancaProblemaSucesso() throws Exception {
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = criacaoAreaSegurancaProblemaTO();
		AreaSegurancaProblema areaSegurancaProblema = areaSegurancaProblemaFactory.getProblemaSeguranca(areaSegurancaProblemaTO, criacaoFesta(), areaTest(), criacaoProblema(), usuarioTest(), usuarioTest());
		
		assertEquals(areaSegurancaProblemaTO.getCodAreaSeguranca(), areaSegurancaProblema.getArea().getCodArea());
		assertEquals(areaSegurancaProblemaTO.getCodFesta(), areaSegurancaProblema.getFesta().getCodFesta());
		assertEquals(areaSegurancaProblemaTO.getCodProblema(), areaSegurancaProblema.getProblema().getCodProblema());
		assertEquals(areaSegurancaProblemaTO.getCodUsuarioEmissor(), areaSegurancaProblema.getCodUsuarioEmissor().getCodUsuario());
		assertEquals(areaSegurancaProblemaTO.getCodUsuarioResolv(), areaSegurancaProblema.getCodUsuarioResolv().getCodUsuario());
		assertEquals(areaSegurancaProblemaTO.getDescProblema(), areaSegurancaProblema.getDescProblema());
		assertEquals(areaSegurancaProblemaTO.getHorarioFim(), areaSegurancaProblema.getHorarioFim());
		assertEquals(areaSegurancaProblemaTO.getHorarioInicio(), areaSegurancaProblema.getHorarioInicio());
		assertEquals(areaSegurancaProblemaTO.getStatusProblema(), areaSegurancaProblema.getStatusProblema());

	}
	
	@Test
	void getAreasSegurancaProblemaTOSucesso() throws Exception {
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		List<AreaSegurancaProblema> areasSegurancaProblema = new ArrayList<>();
		areasSegurancaProblema.add(areaSegurancaProblema);
		
		List<AreaSegurancaProblemaTO> areasSegurancaProblemaTO = areaSegurancaProblemaFactory.getProblemasSegurancaTO(areasSegurancaProblema);
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = areasSegurancaProblemaTO.get(0);
		
		
		assertEquals(areaSegurancaProblemaTO.getCodAreaSeguranca(), areaSegurancaProblema.getArea().getCodArea());
		assertEquals(areaSegurancaProblemaTO.getCodFesta(), areaSegurancaProblema.getFesta().getCodFesta());
		assertEquals(areaSegurancaProblemaTO.getCodProblema(), areaSegurancaProblema.getProblema().getCodProblema());
		assertEquals(areaSegurancaProblemaTO.getCodUsuarioEmissor(), areaSegurancaProblema.getCodUsuarioEmissor().getCodUsuario());
		assertEquals(areaSegurancaProblemaTO.getCodUsuarioResolv(), areaSegurancaProblema.getCodUsuarioResolv().getCodUsuario());
		assertEquals(areaSegurancaProblemaTO.getDescProblemaEmissor(), areaSegurancaProblema.getDescProblema());
		assertEquals(areaSegurancaProblemaTO.getHorarioFim(), areaSegurancaProblema.getHorarioFim());
		assertEquals(areaSegurancaProblemaTO.getHorarioInicio(), areaSegurancaProblema.getHorarioInicio());
		assertEquals(areaSegurancaProblemaTO.getStatusProblema(), areaSegurancaProblema.getStatusProblema());

	}
}
