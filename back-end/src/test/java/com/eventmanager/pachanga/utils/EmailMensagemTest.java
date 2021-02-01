package com.eventmanager.pachanga.utils;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.legacy.PowerMockRunner;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Ingresso;
import com.eventmanager.pachanga.domains.Lote;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoStatusCompra;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;
import com.eventmanager.pachanga.tipo.TipoStatusIngresso;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ MimeMessage.class, Session.class, EmailMensagem.class, PasswordAuthentication.class})
class EmailMensagemTest {

	@Mock
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@Mock
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@Mock
	private JwtTokenUtil defaultJwtTokenUtil;

	@Mock
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	private Festa festaTest() throws Exception {
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
	
	@SuppressWarnings("deprecation")
	public static Usuario usuarioTest() throws Exception{
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("fc68b677646b5f018d1762e9a19bf65180d9aab2794794340ade50e0d78a239affd43a613e7136a61b5d63b09f072c0c039dea4281873abe826d6e6285d9cefef0a0d868d3b0b0d4582ec787b473b4e0");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setGenero("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");

		return usuarioTest;
	}
	
	@Test
	void enviarEmailQRCodeTest() throws Exception {

//		try (MockedStatic<Session> mockSession = Mockito.mockStatic(Session.class)) {
//			try (MockedStatic<Transport> mockTransport = Mockito.mockStatic(Transport.class)) {
//				Properties props = PowerMockito.mock(Properties.class);
//				
//				Authenticator authenticator = PowerMockito.mock(Authenticator.class);
//				
//				Session session =  PowerMockito.mock(Session.class);
//				
//				mockSession.when(
//						() -> Session.getInstance(Mockito.any(Properties.class), Mockito.any(Authenticator.class)))
//						.thenReturn(session);
//
//				MimeMessage message = PowerMockito.mock(MimeMessage.class);
//
//
//				PasswordAuthentication pass = PowerMockito.mock(PasswordAuthentication.class);
//
//				PowerMockito.whenNew(PasswordAuthentication.class)
//						.withArguments(Mockito.anyString(), Mockito.anyString()).thenReturn(pass);
//
//				PowerMockito.whenNew(MimeMessage.class).withArguments(session)
//						.thenReturn(message);
//
//				Transport transport = PowerMockito.mock(Transport.class);
//				
//				PowerMockito.when(session.getTransport(Mockito.anyString())).thenReturn(transport);
//
//				EmailMensagem.enviarEmailQRCode("teste@email.invalid", festaTest(), listaIngressoTest());
//
//			}
//		}

	}

	@Test
	void enviarEmailTest() throws Exception {
		// EmailMensagem.enviarEmail("teste@email.invalid", "haha", festaTest());

	}

	@Test
	void enviarPDFRelatorioTest() throws Exception {
		/*
		 * EmailMensagem emailMensagem = new EmailMensagem(); List<String> listaDeEmails
		 * = new ArrayList<>(); //listaDeEmails.add("fernando@email.invalid");
		 * //listaDeEmails.add("eduardo@email.invalid"); File file = new
		 * File("target/Lorem_ipsum.pdf");
		 * emailMensagem.enviarPDFRelatorio(listaDeEmails, file);
		 */
	}
}
