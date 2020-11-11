package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Ingresso;
import com.eventmanager.pachanga.domains.Lote;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.IngressoTO;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;

public class IngressoFactoryTest {

	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	@Autowired 
	private IngressoFactory ingressoFactory;
	
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
	
	@SuppressWarnings("deprecation")
	private Usuario usuarioTest() throws Exception{
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("1234");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setGenero("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");

		return usuarioTest;
	}

	
	private Ingresso ingressoTest() throws Exception {
		Ingresso ingresso = new Ingresso();
		ingresso.setCodIngresso(1);
		ingresso.setDataCheckin(LocalDateTime.of(2016, Month.JUNE, 27, 19, 10));
		ingresso.setDataCompra(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		ingresso.setFesta(festaTest());
		ingresso.setLote(loteTest());
		ingresso.setPreco(10);
		ingresso.setStatusCompra("comprado");
		ingresso.setStatusIngresso("fine");
		ingresso.setUsuario(usuarioTest());
		return ingresso;
	}
	
	private IngressoTO ingressoTOTest() throws Exception {
		IngressoTO ingressoTO = new IngressoTO();
		ingressoTO.setCodIngresso(1);
		ingressoTO.setDataCheckin(LocalDateTime.of(2016, Month.JUNE, 27, 19, 10));
		ingressoTO.setDataCompra(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		ingressoTO.setFesta(festaTest());
		ingressoTO.setLote(loteTest());
		ingressoTO.setPreco(10);
		ingressoTO.setStatusCompra("comprado");
		ingressoTO.setStatusIngresso("fine");
		ingressoTO.setUsuario(usuarioTest());
		return ingressoTO;
	}

}
