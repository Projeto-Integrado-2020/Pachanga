package com.eventmanager.pachanga.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.legacy.PowerMockRunner;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.PDFPorEmailTO;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;
import com.eventmanager.pachanga.utils.EmailMensagem;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ EnvioDePDFPorEmailService.class, Session.class, EmailMensagem.class, PasswordAuthentication.class })
class EnvioDePDFPorEmailServiceTest {

	@Mock
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@Mock
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@Mock
	private JwtTokenUtil defaultJwtTokenUtil;

	@Mock
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	@InjectMocks
	private EnvioDePDFPorEmailService envioDePDFPorEmailService;

	@Mock
	private EmailMensagem emailMensagem;

	@Mock
	private GrupoService grupoService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Mock
	private FestaRepository festaRepository;

	@Mock
	private UsuarioRepository usuarioRepository;

	private PDFPorEmailTO gerarPDFPorEmailTO() {
		List<String> listaDeEmails = new ArrayList<>();
		listaDeEmails.add("fernando@email.invalid");
		listaDeEmails.add("eduardo@email.invalid");

		PDFPorEmailTO pdfPorEmailTO = new PDFPorEmailTO();
		pdfPorEmailTO.setBase64Pdf(
				"JVBERi0xLjQKJeLjz9MKMSAwIG9iago8PC9MZW5ndGggNTQvRmlsdGVyL0ZsYXRlRGVjb2RlPj5zdHJlYW0KeJwr5HIK4TI2U7AwMFMISeFyDeEK5DJQMFQwAEJvkAQQ5cIU5HCZmlqCRCAMiFAxFwDI1g01CmVuZHN0cmVhbQplbmRvYmoKMyAwIG9iago8PC9QYXJlbnQgMiAwIFIvQ29udGVudHMgMSAwIFIvVHlwZS9QYWdlL1Jlc291cmNlczw8Pj4vTWVkaWFCb3hbMCAwIDU5NSA4NDJdPj4KZW5kb2JqCjIgMCBvYmoKPDwvVHlwZS9QYWdlcy9Db3VudCAxL0tpZHNbMyAwIFJdPj4KZW5kb2JqCjQgMCBvYmoKPDwvVHlwZS9DYXRhbG9nL1BhZ2VzIDIgMCBSPj4KZW5kb2JqCjUgMCBvYmoKPDwvUHJvZHVjZXIoaVRleHSuIDUuNS45LVNOQVBTSE9UIKkyMDAwLTIwMTUgaVRleHQgR3JvdXAgTlYgXChBR1BMLXZlcnNpb25cKSkvTW9kRGF0ZShEOjIwMTUxMjE0MTQyNzEwKzAxJzAwJykvQ3JlYXRpb25EYXRlKEQ6MjAxNTEyMTQxNDI3MTArMDEnMDAnKT4+CmVuZG9iagp4cmVmCjAgNgowMDAwMDAwMDAwIDY1NTM1IGYgCjAwMDAwMDAwMTUgMDAwMDAgbiAKMDAwMDAwMDIyOSAwMDAwMCBuIAowMDAwMDAwMTM1IDAwMDAwIG4gCjAwMDAwMDAyODAgMDAwMDAgbiAKMDAwMDAwMDMyNSAwMDAwMCBuIAp0cmFpbGVyCjw8L1Jvb3QgNCAwIFIvSUQgWzxkNjgyNzc1MmE1N2U1YjdhNDU1ZWQ5NGI0ODBhMjA3Yz48ZDY4Mjc3NTJhNTdlNWI3YTQ1NWVkOTRiNDgwYTIwN2M+XS9JbmZvIDUgMCBSL1NpemUgNj4+CiVpVGV4dC01LjUuOS1TTkFQU0hPVApzdGFydHhyZWYKNDkxCiUlRU9GCg==");
		pdfPorEmailTO.setListaDeEmails(listaDeEmails);
		return pdfPorEmailTO;
	}

	@SuppressWarnings("deprecation")
	public static Usuario usuarioTest(){
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha(
				"fc68b677646b5f018d1762e9a19bf65180d9aab2794794340ade50e0d78a239affd43a613e7136a61b5d63b09f072c0c039dea4281873abe826d6e6285d9cefef0a0d868d3b0b0d4582ec787b473b4e0");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setGenero("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");

		return usuarioTest;
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
	void enviarRelatorio() {
		PowerMockito
				.when(grupoService.validarPermissaoUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(true);
		doNothing().when(emailMensagem).enviarPDFRelatorio(Mockito.anyList(), Mockito.any(), Mockito.any(), Mockito.any());
		Mockito.when(grupoService.validarPermissaoUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(true);
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());
		boolean retorno = envioDePDFPorEmailService.enviarRelatorio(gerarPDFPorEmailTO(), 1, 1);
		assertEquals(true, retorno);
	}
}
