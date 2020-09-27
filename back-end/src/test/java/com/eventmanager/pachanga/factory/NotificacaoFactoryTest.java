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

import com.eventmanager.pachanga.domains.Convidado;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Notificacao;
import com.eventmanager.pachanga.domains.NotificacaoConvidado;
import com.eventmanager.pachanga.domains.NotificacaoGrupo;
import com.eventmanager.pachanga.domains.NotificacaoUsuario;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.NotificacaoConvidadoTO;
import com.eventmanager.pachanga.dtos.NotificacaoTO;
import com.eventmanager.pachanga.dtos.NotificacaoUsuarioTO;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoStatusNotificacao;

@RunWith(SpringRunner.class)
@WebMvcTest(value=NotificacaoFactory.class)
class NotificacaoFactoryTest {
	
	@Autowired
	NotificacaoFactory notificacaoFactory;
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	private Notificacao notificacaoTest() {
		Notificacao notificacao = new Notificacao();
		notificacao.setCodNotificacao(1);
		notificacao.setDescNotificacao("teste");
		return notificacao;
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

	private NotificacaoUsuario notificacaoUsuarioTest() throws Exception {
		NotificacaoUsuario notificacaoUsuario = new NotificacaoUsuario();
		notificacaoUsuario.setDestaque(false);
		notificacaoUsuario.setMensagem("teste");
		notificacaoUsuario.setStatus(TipoStatusNotificacao.NAOLIDA.getDescricao());
		notificacaoUsuario.setDataEmissao(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		notificacaoUsuario.setNotificacao(notificacaoTest() );
		notificacaoUsuario.setUsuario(usuarioTest());
		return notificacaoUsuario;
	}
	
	private NotificacaoConvidado notificacaoConvidadoTest() {
		NotificacaoConvidado notificacaoConvidado = new NotificacaoConvidado();
		notificacaoConvidado.setConvidado(convidadoTest());
		notificacaoConvidado.setDataEmissao(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		notificacaoConvidado.setMensagem("mensagem");
		notificacaoConvidado.setNotificacao(notificacaoTest());
		return notificacaoConvidado;
	}
	
	private Convidado convidadoTest() {
		Convidado convidado = new Convidado();
		convidado.setCodConvidado(1);
		convidado.setEmail("teste@email.com");
		return convidado;
	}
	
	private NotificacaoGrupo notificacaoGrupoTest() {
		NotificacaoGrupo notificacaoGrupo = new NotificacaoGrupo();
		notificacaoGrupo.setNotificacao(notificacaoTest());
		notificacaoGrupo.setMensagem("mensagem");
		notificacaoGrupo.setGrupo(grupoTest());
		notificacaoGrupo.setDataEmissao(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		return notificacaoGrupo;
	}
	
	public Grupo grupoTest() {
		Grupo grupo = new Grupo();
		grupo.setCodGrupo(1);
		grupo.setNomeGrupo("CONVIDADO");
		grupo.setOrganizador(false);
		grupo.setFesta(festaTest());
		return grupo;
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
		festaTest.setStatusFesta("P");
		festaTest.setDescricaoFesta("Bugago");
		festaTest.setHorarioFimFestaReal(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));

		return festaTest;
	}
	
	@Test
	void getNotificacaoUsuarioTOSucesso() throws Exception {
		NotificacaoUsuario notificacaoUsuario = notificacaoUsuarioTest();
		
		NotificacaoUsuarioTO notificacaoUsuarioTO = notificacaoFactory.getNotificacaoUsuarioTO(notificacaoUsuario);
		
		assertEquals( notificacaoUsuarioTO.getDataEmissao(), notificacaoUsuario.getDataEmissao());
		assertEquals( notificacaoUsuarioTO.getMensagem(), notificacaoUsuario .getMensagem());
	}
	
	@Test
	void getNotificacaoTOSucesso() throws Exception {
		NotificacaoConvidado notificacaoConvidado = notificacaoConvidadoTest();
		
		NotificacaoConvidadoTO notificacaoConvidadoTO = notificacaoFactory.getNotificacaoTO(notificacaoConvidado);
		
		assertEquals( notificacaoConvidadoTO.getCodConvidado(), notificacaoConvidado.getConvidado().getCodConvidado());
	}
	@Test
	
	void getNotificacaoTOAlterSucesso() throws Exception {
		NotificacaoUsuario notificacaoUsuario = notificacaoUsuarioTest();
		List<NotificacaoUsuario> notificacoesUsuario = new ArrayList<>();
		notificacoesUsuario.add(notificacaoUsuario);
		
		NotificacaoGrupo notificacaoGrupo = notificacaoGrupoTest();
		List<NotificacaoGrupo> notificacoesGrupo = new ArrayList<>();
		notificacoesGrupo.add(notificacaoGrupo);
		
		NotificacaoConvidado notificacaoConvidado = notificacaoConvidadoTest();
		List<NotificacaoConvidado> notificacoesConvidado = new ArrayList<>();
		notificacoesConvidado.add(notificacaoConvidado);
		
		NotificacaoTO notificacaoTO = notificacaoFactory.getNotificacaoTO(notificacoesUsuario, notificacoesGrupo, notificacoesConvidado);
		
		NotificacaoUsuarioTO notificacaoUsuarioTO = notificacaoTO.getNotificacoesUsuario().get(0);
		
		assertEquals( notificacaoUsuarioTO.getCodUsuario(), notificacaoUsuario.getUsuario().getCodUsuario());
		assertEquals( notificacaoUsuarioTO.getDataEmissao(), notificacaoUsuario.getDataEmissao());
		assertEquals( notificacaoUsuarioTO.getMensagem(), notificacaoUsuario.getMensagem());
		
	}
	
	
	
}
