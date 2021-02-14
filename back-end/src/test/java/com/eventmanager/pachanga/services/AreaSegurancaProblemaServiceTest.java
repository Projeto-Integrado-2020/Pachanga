package com.eventmanager.pachanga.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import com.eventmanager.pachanga.domains.AreaSeguranca;
import com.eventmanager.pachanga.domains.AreaSegurancaProblema;
import com.eventmanager.pachanga.domains.AreaSegurancaProblemaFluxo;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Problema;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.AreaSegurancaProblemaHistorico;
import com.eventmanager.pachanga.dtos.AreaSegurancaProblemaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.AreaSegurancaProblemaFactory;
import com.eventmanager.pachanga.factory.NotificacaoAreaSegurancaTOFactory;
import com.eventmanager.pachanga.repositories.AreaSegurancaProblemaFluxoRepository;
import com.eventmanager.pachanga.repositories.AreaSegurancaProblemaRepository;
import com.eventmanager.pachanga.repositories.AreaSegurancaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.ProblemaRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoAreaSeguranca;
import com.eventmanager.pachanga.tipo.TipoProblema;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;
import com.eventmanager.pachanga.tipo.TipoStatusProblema;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AreaSegurancaProblemaService.class)
class AreaSegurancaProblemaServiceTest {

	@Autowired
	private AreaSegurancaProblemaService areaSegurancaProblemaService;

	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	@MockBean
	private AreaSegurancaProblemaRepository areaSegurancaProblemaRepository;

	@MockBean
	private GrupoRepository grupoRepository;

	@MockBean
	private FestaService festaService;

	@MockBean
	private ProblemaRepository problemaRepository;

	@MockBean
	private UsuarioRepository usuarioRepository;

	@MockBean
	private AreaSegurancaRepository areaSegurancaRepository;

	@MockBean
	private NotificacaoService notificacaoService;

	@MockBean
	private AreaSegurancaProblemaFactory areaSegurancaProblemaFactory;

	@MockBean
	private GrupoService grupoService;

	@MockBean
	private UsuarioService usuarioService;

	@MockBean
	private NotificacaoAreaSegurancaTOFactory notificacaoAreaFactory;

	@MockBean
	private AreaSegurancaProblemaFluxoRepository areaSegurancaProblemaFluxoRepository;

	public AreaSegurancaProblema criacaoAreaSegurancaProblema() throws Exception {
		AreaSegurancaProblema areaSegurancaProblema = new AreaSegurancaProblema();
		areaSegurancaProblema.setCodAreaProblema(1);
		areaSegurancaProblema.setArea(areaTest());
		areaSegurancaProblema.setProblema(criacaoProblema());
		areaSegurancaProblema.setFesta(criacaoFesta());
		areaSegurancaProblema.setDescProblema("Situação está séria");
		areaSegurancaProblema.setHorarioInicio(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		areaSegurancaProblema.setHorarioFim(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		areaSegurancaProblema.setStatusProblema(TipoStatusProblema.ANDAMENTO.getValor());
		areaSegurancaProblema.setCodUsuarioResolv(usuarioTest());
		areaSegurancaProblema.setCodUsuarioEmissor(usuarioTest());

		return areaSegurancaProblema;
	}

	public AreaSegurancaProblemaTO criacaoAreaSegurancaProblemaTO() throws Exception {
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = new AreaSegurancaProblemaTO();
		areaSegurancaProblemaTO.setCodAreaProblema(1);
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

	private Festa criacaoFesta() throws Exception {
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

	public Problema criacaoProblema() {
		Problema problema = new Problema();
		problema.setCodProblema(1);
		problema.setDescProblema("Teste");
		return problema;
	}

	public Grupo criacaoGrupo() {
		Grupo grupo = new Grupo();
		grupo.setCodGrupo(1);
		grupo.setNomeGrupo("CONVIDADO");
		return grupo;
	}

	private List<Grupo> criacaoGrupos() {
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(criacaoGrupo());
		return grupos;
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
	private Usuario usuarioTest() {
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("1234");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setGenero("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");

		return usuarioTest;
	}
	
	private AreaSegurancaProblemaFluxo areaHistoricoTest() {
		AreaSegurancaProblemaFluxo areaHistorico = new AreaSegurancaProblemaFluxo();
		areaHistorico.setCodArea(1);
		areaHistorico.setCodUsuarioResolv(2);
		areaHistorico.setDescProblema("teste");
		areaHistorico.setNomeArea("teste123");
		areaHistorico.setStatusProblema(TipoAreaSeguranca.SEGURO.getDescricao());
		return areaHistorico;
	}

	@Test
	void addProblemaSegurancaSucesso() throws Exception {
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = criacaoAreaSegurancaProblemaTO();
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		AreaSeguranca areaSeguranca = areaSegurancaProblema.getArea();
		Usuario usuario = usuarioTest();
		Problema problema = areaSegurancaProblema.getProblema();
		Festa festa = areaSegurancaProblema.getFesta();
		List<Grupo> grupos = criacaoGrupos();
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);

		MultipartFile imagem = new MockMultipartFile("teste", "Hello World".getBytes());

		Mockito.when(areaSegurancaProblemaFactory.getProblemaSeguranca(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(areaSegurancaProblema);
		Mockito.when(areaSegurancaProblemaRepository.findByCodProblema(Mockito.anyInt())).thenReturn(null);
		Mockito.when(areaSegurancaRepository.findAreaByCodFestaAndCodArea(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(areaSeguranca);
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(usuario);
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(grupos);
		Mockito.when(problemaRepository.findProblemaByCodProblema(Mockito.anyInt())).thenReturn(problema);
		Mockito.when(grupoRepository.findGruposPermissaoAreaSegurancaProblema(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(notificacaoService.criarMensagemAreaProblema(Mockito.anyInt())).thenReturn("ABCD");
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString()))
				.thenReturn(true);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).inserirNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt(),
				Mockito.anyString());

		AreaSegurancaProblema retorno = areaSegurancaProblemaService.addProblemaSeguranca(areaSegurancaProblemaTO,
				usuarioTest().getCodUsuario(), imagem);

		assertEquals(retorno.getArea(), areaSeguranca);
		assertEquals(retorno.getProblema(), problema);
		assertEquals(retorno.getFesta(), festa);
		assertEquals(retorno.getDescProblema(), areaSegurancaProblema.getDescProblema());

	}

	@Test
	void addProblemaSegurancaErroProblemaSemDescricao() throws Exception {
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = criacaoAreaSegurancaProblemaTO();
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		AreaSeguranca areaSeguranca = areaSegurancaProblema.getArea();
		Usuario usuario = usuarioTest();
		Problema problema = areaSegurancaProblema.getProblema();
		List<Grupo> grupos = criacaoGrupos();
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);
		areaSegurancaProblema.getProblema().setCodProblema(TipoProblema.OUTRPROB.getCodigo());
		areaSegurancaProblema.setDescProblema(null);

		MultipartFile imagem = new MockMultipartFile("teste", "Hello World".getBytes());

		Mockito.when(areaSegurancaProblemaFactory.getProblemaSeguranca(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(areaSegurancaProblema);
		Mockito.when(areaSegurancaProblemaRepository.findByCodProblema(Mockito.anyInt())).thenReturn(null);
		Mockito.when(areaSegurancaRepository.findAreaByCodFestaAndCodArea(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(areaSeguranca);
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(usuario);
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(grupos);
		Mockito.when(problemaRepository.findProblemaByCodProblema(Mockito.anyInt())).thenReturn(problema);
		Mockito.when(grupoRepository.findGruposPermissaoAreaSegurancaProblema(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(notificacaoService.criarMensagemAreaProblema(Mockito.anyInt())).thenReturn("ABCD");
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString()))
				.thenReturn(true);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).inserirNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt(),
				Mockito.anyString());

		boolean sucesso;
		try {
			areaSegurancaProblemaService.addProblemaSeguranca(areaSegurancaProblemaTO, usuarioTest().getCodUsuario(),
					imagem);
			sucesso = true;
		} catch (ValidacaoException e) {
			sucesso = false;
		}
		;

		assertEquals(false, sucesso);

	}

	@Test
	void addProblemaSegurancaProblemaInvalida() throws Exception {
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = criacaoAreaSegurancaProblemaTO();
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		AreaSeguranca areaSeguranca = areaSegurancaProblema.getArea();
		Usuario usuario = usuarioTest();
		List<Grupo> grupos = criacaoGrupos();
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);

		MultipartFile imagem = new MockMultipartFile("teste", "Hello World".getBytes());

		Mockito.when(areaSegurancaProblemaRepository.findByCodProblema(Mockito.anyInt())).thenReturn(null);
		Mockito.when(areaSegurancaRepository.findAreaByCodFestaAndCodArea(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(areaSeguranca);
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(usuario);
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(grupos);
		Mockito.when(problemaRepository.findProblemaByCodProblema(Mockito.anyInt())).thenReturn(null);
		Mockito.when(grupoRepository.findGruposPermissaoAreaSegurancaProblema(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(notificacaoService.criarMensagemAreaProblema(Mockito.anyInt())).thenReturn("ABCD");
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString()))
				.thenReturn(true);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).inserirNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt(),
				Mockito.anyString());

		boolean sucesso;
		try {
			areaSegurancaProblemaService.addProblemaSeguranca(areaSegurancaProblemaTO, usuarioTest().getCodUsuario(),
					imagem);
			sucesso = true;
		} catch (ValidacaoException e) {
			sucesso = false;
		}
		;

		assertEquals(false, sucesso);

	}

	@Test
	void addProblemaSegurancaDataInvalida() throws Exception {
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = criacaoAreaSegurancaProblemaTO();
		areaSegurancaProblemaTO.setHorarioFim(LocalDateTime.of(1888, Month.JUNE, 22, 19, 10));
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		AreaSeguranca areaSeguranca = areaSegurancaProblema.getArea();
		Usuario usuario = usuarioTest();
		Problema problema = areaSegurancaProblema.getProblema();
		List<Grupo> grupos = criacaoGrupos();
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);

		AreaSegurancaProblema areaSegurancaProblemaRetorno = criacaoAreaSegurancaProblema();
		areaSegurancaProblemaRetorno.setHorarioInicio(LocalDateTime.of(1888, Month.JUNE, 22, 19, 10));
		areaSegurancaProblemaRetorno.setHorarioFim(LocalDateTime.of(1888, Month.JUNE, 21, 19, 10));

		MultipartFile imagem = new MockMultipartFile("teste", "Hello World".getBytes());

		Mockito.when(areaSegurancaProblemaFactory.getProblemaSeguranca(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(areaSegurancaProblemaRetorno);
		Mockito.when(areaSegurancaProblemaRepository.findByCodProblema(Mockito.anyInt())).thenReturn(null);
		Mockito.when(areaSegurancaRepository.findAreaByCodFestaAndCodArea(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(areaSeguranca);
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(usuario);
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(grupos);
		Mockito.when(problemaRepository.findProblemaByCodProblema(Mockito.anyInt())).thenReturn(problema);
		Mockito.when(grupoRepository.findGruposPermissaoAreaSegurancaProblema(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(notificacaoService.criarMensagemAreaProblema(Mockito.anyInt())).thenReturn("ABCD");
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString()))
				.thenReturn(true);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).inserirNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt(),
				Mockito.anyString());

		boolean sucesso;
		try {
			areaSegurancaProblemaService.addProblemaSeguranca(areaSegurancaProblemaTO, usuarioTest().getCodUsuario(),
					imagem);
			sucesso = true;
		} catch (ValidacaoException e) {
			sucesso = false;
		}
		;

		assertEquals(false, sucesso);

	}

	@Test
	void addProblemaSegurancaNotificacaoValidFail() throws Exception {
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = criacaoAreaSegurancaProblemaTO();
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		AreaSeguranca areaSeguranca = areaSegurancaProblema.getArea();
		Usuario usuario = usuarioTest();
		Problema problema = areaSegurancaProblema.getProblema();
		Festa festa = areaSegurancaProblema.getFesta();
		List<Grupo> grupos = criacaoGrupos();
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);

		Mockito.when(areaSegurancaProblemaFactory.getProblemaSeguranca(Mockito.any(), Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(areaSegurancaProblema);
		Mockito.when(areaSegurancaProblemaRepository.findByCodProblema(Mockito.anyInt())).thenReturn(null);
		Mockito.when(areaSegurancaRepository.findAreaByCodFestaAndCodArea(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(areaSeguranca);
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(usuario);
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(grupos);
		Mockito.when(problemaRepository.findProblemaByCodProblema(Mockito.anyInt())).thenReturn(problema);
		Mockito.when(grupoRepository.findGruposPermissaoAreaSegurancaProblema(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(notificacaoService.criarMensagemAreaProblema(Mockito.anyInt())).thenReturn("ABCD");
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString()))
				.thenReturn(false);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).inserirNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt(),
				Mockito.anyString());

		MultipartFile imagem = new MockMultipartFile("teste", "Hello World".getBytes());

		AreaSegurancaProblema retorno = areaSegurancaProblemaService.addProblemaSeguranca(areaSegurancaProblemaTO,
				usuarioTest().getCodUsuario(), imagem);

		assertEquals(retorno.getArea(), areaSeguranca);
		assertEquals(retorno.getProblema(), problema);
		assertEquals(retorno.getFesta(), festa);
		assertEquals(retorno.getDescProblema(), areaSegurancaProblema.getDescProblema());
	}

	@Test
	void updateProblemaSegurancaSucesso() throws Exception {
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = criacaoAreaSegurancaProblemaTO();
		areaSegurancaProblemaTO.setStatusProblema(TipoStatusProblema.FINALIZADO.getValor());
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		AreaSeguranca areaSeguranca = areaSegurancaProblema.getArea();
		Usuario usuario = usuarioTest();
		Problema problema = areaSegurancaProblema.getProblema();
		Festa festa = areaSegurancaProblema.getFesta();
		List<Grupo> grupos = criacaoGrupos();
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);

		Mockito.when(areaSegurancaProblemaRepository.findByCodProblema(Mockito.anyInt()))
				.thenReturn(areaSegurancaProblema);
		Mockito.when(areaSegurancaRepository.findAreaByCodFestaAndCodArea(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(areaSeguranca);
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(usuario);
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(grupos);
		Mockito.when(problemaRepository.findProblemaByCodProblema(Mockito.anyInt())).thenReturn(problema);
		Mockito.when(grupoRepository.findGruposPermissaoAreaSegurancaProblema(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(notificacaoService.criarMensagemAreaProblema(Mockito.anyInt())).thenReturn("ABCD");
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString()))
				.thenReturn(true);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).inserirNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt(),
				Mockito.anyString());

		AreaSegurancaProblema retorno = areaSegurancaProblemaService.updateProblemaSeguranca(areaSegurancaProblemaTO,
				usuarioTest().getCodUsuario());

		assertEquals(retorno.getArea(), areaSeguranca);
		assertEquals(retorno.getProblema(), problema);
		assertEquals(retorno.getFesta(), festa);
		assertEquals(retorno.getDescProblema(), areaSegurancaProblema.getDescProblema());
	}

	@Test
	void updateProblemaSegurancaNExist() throws Exception {
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = criacaoAreaSegurancaProblemaTO();
		areaSegurancaProblemaTO.setStatusProblema(TipoStatusProblema.FINALIZADO.getValor());
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		AreaSeguranca areaSeguranca = areaSegurancaProblema.getArea();
		Usuario usuario = usuarioTest();
		Problema problema = areaSegurancaProblema.getProblema();
		List<Grupo> grupos = criacaoGrupos();
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);

		Mockito.when(areaSegurancaProblemaRepository.findByCodProblema(Mockito.anyInt())).thenReturn(null);
		Mockito.when(areaSegurancaRepository.findAreaByCodFestaAndCodArea(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(areaSeguranca);
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(usuario);
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(grupos);
		Mockito.when(problemaRepository.findProblemaByCodProblema(Mockito.anyInt())).thenReturn(problema);
		Mockito.when(grupoRepository.findGruposPermissaoAreaSegurancaProblema(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(notificacaoService.criarMensagemAreaProblema(Mockito.anyInt())).thenReturn("ABCD");
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString()))
				.thenReturn(true);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).inserirNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt(),
				Mockito.anyString());

		boolean sucesso;
		try {
			areaSegurancaProblemaService.updateProblemaSeguranca(areaSegurancaProblemaTO,
					usuarioTest().getCodUsuario());
			sucesso = true;
		} catch (ValidacaoException e) {
			sucesso = false;
		}
		;

		assertEquals(false, sucesso);

	}

	@Test
	void findProblemaSegurancaSucesso() throws Exception {
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		int codFesta = areaSegurancaProblema.getFesta().getCodFesta();

		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());
		Mockito.when(areaSegurancaProblemaRepository.findAreaSegurancaProblema(Mockito.anyInt()))
				.thenReturn(areaSegurancaProblema);

		AreaSegurancaProblema retorno = areaSegurancaProblemaService.findProblemaSeguranca(1, codFesta,
				usuarioTest().getCodUsuario());

		assertEquals(retorno, areaSegurancaProblema);

	}

	@Test
	void findAllProblemasSegurancaAreaSucesso() throws Exception {
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		List<AreaSegurancaProblema> areasSegurancaProblema = new ArrayList<>();
		areasSegurancaProblema.add(areaSegurancaProblema);
		int codAreaSeguranca = areaSegurancaProblema.getArea().getCodArea();
		int codFesta = areaSegurancaProblema.getFesta().getCodFesta();

		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());
		Mockito.when(
				areaSegurancaProblemaRepository.findAllAreaSegurancaProblemaArea(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(areasSegurancaProblema);

		List<AreaSegurancaProblema> retorno = areaSegurancaProblemaService
				.findAllProblemasSegurancaArea(codAreaSeguranca, codFesta, usuarioTest().getCodUsuario());

		assertEquals(retorno.get(0), areaSegurancaProblema);

	}

	@Test
	void findAllProblemasSegurancaFestaSucesso() throws Exception {
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		List<AreaSegurancaProblema> areasSegurancaProblema = new ArrayList<>();
		areasSegurancaProblema.add(areaSegurancaProblema);
		int codFesta = areaSegurancaProblema.getFesta().getCodFesta();

		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());
		Mockito.when(areaSegurancaProblemaRepository.findAllAreaSegurancaProblemaFesta(Mockito.anyInt()))
				.thenReturn(areasSegurancaProblema);

		List<AreaSegurancaProblema> retorno = areaSegurancaProblemaService.findAllProblemasSegurancaFesta(codFesta,
				usuarioTest().getCodUsuario());

		assertEquals(retorno.get(0), areaSegurancaProblema);

	}

	@Test
	void alterarStatusProblemaFinalizada() throws Exception {
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = criacaoAreaSegurancaProblemaTO();
		areaSegurancaProblemaTO.setStatusProblema(TipoStatusProblema.FINALIZADO.getValor());
		areaSegurancaProblemaTO.setObservacaoSolucao("teste");
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		Usuario usuario = usuarioTest();
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(this.criacaoGrupo());
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);

		Mockito.when(areaSegurancaRepository.findAreaByCodFestaAndCodArea(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(areaTest());
		Mockito.when(areaSegurancaProblemaRepository.findByCodProblema(Mockito.anyInt()))
				.thenReturn(areaSegurancaProblema);
		Mockito.when(usuarioService.validarUsuario(Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(grupoRepository.findGruposFesta(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).deleteNotificacao(Mockito.anyInt(), Mockito.anyString());
		Mockito.doNothing().when(notificacaoService).deletarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString());
		Mockito.when(areaSegurancaProblemaRepository.quantidadeProblemasAreaFesta(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(1);

		areaSegurancaProblemaService.alterarStatusProblema(areaSegurancaProblemaTO, 1, true);

	}

	@Test
	void alterarStatusProblemaColocarEmAndamento() throws Exception {
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = criacaoAreaSegurancaProblemaTO();
		areaSegurancaProblemaTO.setStatusProblema(TipoStatusProblema.FINALIZADO.getValor());
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		Usuario usuario = usuarioTest();
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(this.criacaoGrupo());
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);

		Mockito.when(areaSegurancaRepository.findAreaByCodFestaAndCodArea(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(areaTest());
		Mockito.when(grupoRepository.findGruposPermissaoAreaSegurancaProblema(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(notificacaoService.criarMensagemAreaProblema(Mockito.anyInt())).thenReturn("ABCD");
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString()))
				.thenReturn(true);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).inserirNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt(),
				Mockito.anyString());
		Mockito.when(areaSegurancaProblemaRepository.quantidadeProblemasAreaFesta(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(2);

		Mockito.when(areaSegurancaProblemaRepository.findByCodProblema(Mockito.anyInt()))
				.thenReturn(areaSegurancaProblema);

		areaSegurancaProblemaService.alterarStatusProblema(areaSegurancaProblemaTO, 1, false);

	}

	@Test
	void alterarStatusProblemaErro() throws Exception {
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = criacaoAreaSegurancaProblemaTO();
		areaSegurancaProblemaTO.setStatusProblema(TipoStatusProblema.FINALIZADO.getValor());
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();

		Mockito.when(areaSegurancaProblemaRepository.findByCodProblema(Mockito.anyInt()))
				.thenReturn(areaSegurancaProblema);
		Mockito.when(usuarioService.validarUsuario(Mockito.anyInt())).thenReturn(null);
		Mockito.when(areaSegurancaProblemaRepository.quantidadeProblemasAreaFesta(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(2);
		Mockito.when(areaSegurancaRepository.findAreaByCodFestaAndCodArea(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(areaTest());

		boolean sucesso = true;
		try {
			areaSegurancaProblemaService.alterarStatusProblema(areaSegurancaProblemaTO, 1, true);
		} catch (ValidacaoException e) {
			sucesso = false;
		}

		assertEquals(false, sucesso);

	}

	@Test
	void deleteByFestaTest() {
		Usuario usuario = usuarioTest();
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(this.criacaoGrupo());
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);
		List<AreaSegurancaProblema> areasSegurancaProblema = new ArrayList<>();
		AreaSegurancaProblema area = new AreaSegurancaProblema();
		area.setCodAreaProblema(1);
		area.setArea(new AreaSeguranca());
		areasSegurancaProblema.add(area);
		Mockito.when(areaSegurancaProblemaRepository.findAllAreaSegurancaProblemaFesta(Mockito.anyInt()))
				.thenReturn(areasSegurancaProblema);
		Mockito.doNothing().when(areaSegurancaProblemaRepository).deleteByCodFesta(Mockito.anyInt());
		Mockito.when(usuarioService.validarUsuario(Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(grupoRepository.findGruposFesta(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).deleteNotificacao(Mockito.anyInt(), Mockito.anyString());
		Mockito.doNothing().when(notificacaoService).deletarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString());

		areaSegurancaProblemaService.deleteByFesta(1);

	}

	@Test
	void getHistoricosAreaFestaTest() {
		List<Integer> codigos = Arrays.asList(0, 1, 2, 3);
		List<AreaSegurancaProblemaFluxo> areaHistorico = new ArrayList<>();
		areaHistorico.add(areaHistoricoTest());

		Mockito.when(areaSegurancaProblemaFluxoRepository.findAreaProblemaFesta(Mockito.anyInt())).thenReturn(codigos);
		Mockito.when(areaSegurancaProblemaFluxoRepository.findProblemaAreaHistorico(Mockito.anyInt())).thenReturn(areaHistorico);
		
		Mockito.when(areaSegurancaProblemaFactory.getProblemaHistorico(Mockito.any()))
				.thenReturn(new AreaSegurancaProblemaHistorico());

		List<AreaSegurancaProblemaHistorico> areasProblemas = areaSegurancaProblemaService.getHistoricosAreaFesta(1);

		assertEquals(true, !areasProblemas.isEmpty());
	}

}
