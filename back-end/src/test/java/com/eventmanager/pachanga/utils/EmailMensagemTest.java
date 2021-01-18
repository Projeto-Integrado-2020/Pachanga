package com.eventmanager.pachanga.utils;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Ingresso;
import com.eventmanager.pachanga.domains.Lote;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoStatusCompra;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;
import com.eventmanager.pachanga.tipo.TipoStatusIngresso;
import com.sun.mail.util.PropUtil;


@RunWith(SpringRunner.class)
@WebMvcTest(value=EmailMensagem.class)
//@PrepareForTest(javax.mail.Transport.class)
//@PrepareForTest({Session.class, PropUtil.class})
//@RunWith(PowerMockRunner.class)
//@RunWith(MockitoJUnitRunner.class)
//@PrepareForTest(Transport.class)
public class EmailMensagemTest {
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	//private Transport mockTransport;
	//private Session mockSession;
	
	//private Transport transport = Mockito.mock(Transport.class);
	
	//@Mock
	//private Transport transport;
	//@Mock
	//private Transport transport;
	
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
	
	private Ingresso ingressoTest() throws Exception {
		Lote lote = loteTest();
		Ingresso ingresso = new Ingresso();
		ingresso.setCodBoleto("ABC");
		ingresso.setCodIngresso("1");
		ingresso.setDataCheckin(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		ingresso.setDataCompra(LocalDateTime.of(2014, Month.JUNE, 22, 19, 10));
		ingresso.setEmailTitular("teste@email.com.invalid");
		ingresso.setFesta(lote.getFesta());
		ingresso.setLote(lote);
		ingresso.setNomeTitular("Fulano");
		ingresso.setPreco((float) 2.30);
		ingresso.setStatusCompra(TipoStatusCompra.PAGO.getDescricao());
		ingresso.setStatusIngresso(TipoStatusIngresso.CHECKED.getDescricao());
		ingresso.setUrlBoleto("https://teste.com");
		return ingresso;
	}
	
	private Lote loteTest() throws Exception {
		Lote lote = new Lote();
		lote.setFesta(festaTest());
		lote.setCodLote(1);
		lote.setDescLote("lote vip krl");
		lote.setNomeLote("Teste");
		lote.setHorarioInicio(LocalDateTime.of(2020, Month.SEPTEMBER, 23, 19, 10));
		lote.setHorarioFim(LocalDateTime.of(2020, Month.SEPTEMBER, 24, 19, 10));
		lote.setNumeroLote(1);
		lote.setPreco(17.2f);
		lote.setQuantidade(100);
		return lote;
	}
	
	private List<Ingresso> listaIngressoTest() throws Exception {
		List<Ingresso> ingressos = new ArrayList<>();
		ingressos.add(ingressoTest());
		return ingressos;
	}
	/*
    @Before
    public void setupMockSessionAndTransport() throws NoSuchProviderException {
    	mockSession = PowerMockito.mock(Session.class);
        PowerMockito.mockStatic(PropUtil.class);
        Mockito.when(PropUtil.getBooleanSessionProperty(mockSession, "mail.mime.address.strict", true)).thenReturn(false);
        mockTransport = Mockito.mock(Transport.class);
        Mockito.when(mockSession.getTransport("smtp")).thenReturn(mockTransport);
    }
	*/
	/*
	@Test
	public void enviarEmailQRCodeTest() throws Exception {
		//suppress(method(Transport.class, "send", Message.class));
		//method(Transport.class, "send", Message.class);
		//mockStatic(Transport.class);
	    //PowerMockito.doNothing().when(Transport.class, "send", any(Message.class));
		//PowerMockito.mockStatic(Transport.class);
		//transport.sendMessage(null, null);
  	    //suppress(method(Transport.class, "send", Message.class));
		//Mockito.doNothing().when(transport).send(Mockito.any(Message.class));
		//PowerMock.mockStatic(Transport.class);
		//suppress(method(Transport.class, "send", Message.class));
		//mockStatic(Transport.class);
        //replay(Transport.class);
		//suppress(method(Transport.class, "send", Message.class));
		
		PowerMockito.mockStatic(javax.mail.Transport.class);
	    PowerMockito.doNothing().when(javax.mail.Transport.class, "send", Mockito.any(Message.class));
		
		EmailMensagem.enviarEmailQRCode("opedrofreitas@gmail.com", festaTest(), listaIngressoTest());
	}
	*/
	/*
	@Test
	public void enviarEmailTest() throws Exception {
		//suppress(method(Transport.class, "send", Message.class));
		//method(Transport.class, "send", Message.class);
		//mockStatic(Transport.class);
	    //PowerMockito.doNothing().when(Transport.class, "send", any(Message.class));
		//PowerMockito.mockStatic(Transport.class);
		//transport.sendMessage(null, null);
  	    //suppress(method(Transport.class, "send", Message.class));
		//Mockito.doNothing().when(transport).send(Mockito.any(Message.class));
		//PowerMock.mockStatic(Transport.class);
		//suppress(method(Transport.class, "send", Message.class));
		//mockStatic(Transport.class);
        //replay(Transport.class);
		//suppress(method(Transport.class, "send", Message.class));
		
		PowerMockito.mockStatic(javax.mail.Transport.class);
	    PowerMockito.doNothing().when(javax.mail.Transport.class, "send", Mockito.any(Message.class));
		EmailMensagem.enviarEmail("opedrofreitas@gmail.com", "haha", festaTest());
	}
	*/
}
