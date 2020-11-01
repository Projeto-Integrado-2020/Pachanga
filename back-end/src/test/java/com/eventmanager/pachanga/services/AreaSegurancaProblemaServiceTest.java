package com.eventmanager.pachanga.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.AreaSeguranca;
import com.eventmanager.pachanga.domains.AreaSegurancaProblema;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Problema;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.AreaSegurancaProblemaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.NotificacaoAreaSegurancaTOFactory;
import com.eventmanager.pachanga.repositories.AreaSegurancaProblemaRepository;
import com.eventmanager.pachanga.repositories.AreaSegurancaRepository;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.ProblemaRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoAreaSeguranca;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;
import com.eventmanager.pachanga.tipo.TipoStatusProblema;

@RunWith(SpringRunner.class)
@WebMvcTest(value=AreaSegurancaProblemaService.class)
public class AreaSegurancaProblemaServiceTest {
	
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
	private FestaRepository festaRepository;
	
	@MockBean
	private NotificacaoService notificacaoService;
	
	

	@MockBean
	private NotificacaoAreaSegurancaTOFactory notificacaoAreaFactory;
	
	public AreaSegurancaProblema criacaoAreaSegurancaProblema() throws Exception {
		AreaSegurancaProblema areaSegurancaProblema = new AreaSegurancaProblema();
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
	
	private List<Grupo> criacaoGrupos(){
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
	private Usuario usuarioTest(){
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("1234");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setGenero("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");

		return usuarioTest;
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
		
		Mockito.when(areaSegurancaProblemaRepository.findAreaSegurancaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(areaSegurancaRepository.findAreaByCodFestaAndCodArea(Mockito.anyInt(), Mockito.anyInt())).thenReturn(areaSeguranca);
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(problemaRepository.findProblemaByCodProblema(Mockito.anyInt())).thenReturn(problema);
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa);
		Mockito.when(grupoRepository.findGruposPermissaoAreaSegurancaProblema(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(notificacaoService.criarMensagemAreaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn("ABCD");
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString())).thenReturn(true);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).inserirNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		
		AreaSegurancaProblema retorno = areaSegurancaProblemaService.addProblemaSeguranca(areaSegurancaProblemaTO, usuarioTest().getCodUsuario());
		
		assertEquals(retorno.getArea(), areaSeguranca);
		assertEquals(retorno.getProblema(), problema);
		assertEquals(retorno.getFesta(), festa);
		assertEquals(retorno.getDescProblema(), areaSegurancaProblema.getDescProblema());
		assertEquals(retorno.getCodUsuarioEmissor(), usuario);
		assertEquals(retorno.getCodUsuarioResolv(), null);
		assertEquals(retorno.getHorarioInicio(), areaSegurancaProblema.getHorarioInicio());
		assertEquals(retorno.getHorarioFim(), areaSegurancaProblema.getHorarioFim());
		assertEquals(retorno.getStatusProblema(), TipoStatusProblema.ANDAMENTO.getValor());
		
	}
	
	@Test
	void addProblemaSegurancaFestaInvalida() throws Exception {
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = criacaoAreaSegurancaProblemaTO();
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		AreaSeguranca areaSeguranca = areaSegurancaProblema.getArea(); 
		Usuario usuario = usuarioTest();
		Problema problema = areaSegurancaProblema.getProblema();
		List<Grupo> grupos = criacaoGrupos();
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);
		
		Mockito.when(areaSegurancaProblemaRepository.findAreaSegurancaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(areaSegurancaRepository.findAreaByCodFestaAndCodArea(Mockito.anyInt(), Mockito.anyInt())).thenReturn(areaSeguranca);
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(problemaRepository.findProblemaByCodProblema(Mockito.anyInt())).thenReturn(problema);
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(null);
		Mockito.when(grupoRepository.findGruposPermissaoAreaSegurancaProblema(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(notificacaoService.criarMensagemAreaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn("ABCD");
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString())).thenReturn(true);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).inserirNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		
		boolean sucesso;
		try {
			areaSegurancaProblemaService.addProblemaSeguranca(areaSegurancaProblemaTO, usuarioTest().getCodUsuario());
			sucesso = true;
		}catch(ValidacaoException e){
			sucesso = false;
		};
		
		assertEquals(sucesso, false);
		
	}
	
	@Test
	void addProblemaSegurancaProblemaInvalida() throws Exception {
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = criacaoAreaSegurancaProblemaTO();
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		AreaSeguranca areaSeguranca = areaSegurancaProblema.getArea(); 
		Usuario usuario = usuarioTest();
		Festa festa = areaSegurancaProblema.getFesta();
		List<Grupo> grupos = criacaoGrupos();
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);
		
		Mockito.when(areaSegurancaProblemaRepository.findAreaSegurancaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(areaSegurancaRepository.findAreaByCodFestaAndCodArea(Mockito.anyInt(), Mockito.anyInt())).thenReturn(areaSeguranca);
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(problemaRepository.findProblemaByCodProblema(Mockito.anyInt())).thenReturn(null);
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa);
		Mockito.when(grupoRepository.findGruposPermissaoAreaSegurancaProblema(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(notificacaoService.criarMensagemAreaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn("ABCD");
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString())).thenReturn(true);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).inserirNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		
		boolean sucesso;
		try {
			areaSegurancaProblemaService.addProblemaSeguranca(areaSegurancaProblemaTO, usuarioTest().getCodUsuario());
			sucesso = true;
		}catch(ValidacaoException e){
			sucesso = false;
		};
		
		assertEquals(sucesso, false);
		
	}
	
	@Test
	void addProblemaSegurancaUsuarioNaoEncontrado() throws Exception {
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = criacaoAreaSegurancaProblemaTO();
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		AreaSeguranca areaSeguranca = areaSegurancaProblema.getArea(); 
		Usuario usuario = usuarioTest();
		Problema problema = areaSegurancaProblema.getProblema();
		Festa festa = areaSegurancaProblema.getFesta();
		List<Grupo> grupos = criacaoGrupos();
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);
		
		Mockito.when(areaSegurancaProblemaRepository.findAreaSegurancaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(areaSegurancaRepository.findAreaByCodFestaAndCodArea(Mockito.anyInt(), Mockito.anyInt())).thenReturn(areaSeguranca);
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(null);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(problemaRepository.findProblemaByCodProblema(Mockito.anyInt())).thenReturn(problema);
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa);
		Mockito.when(grupoRepository.findGruposPermissaoAreaSegurancaProblema(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(notificacaoService.criarMensagemAreaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn("ABCD");
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString())).thenReturn(true);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).inserirNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		
		boolean sucesso;
		try {
			areaSegurancaProblemaService.addProblemaSeguranca(areaSegurancaProblemaTO, usuarioTest().getCodUsuario());
			sucesso = true;
		}catch(ValidacaoException e){
			sucesso = false;
		};
		
		assertEquals(sucesso, false);
		
	}
	
	@Test
	void addProblemaSegurancaUsuarioNestaNaFesta() throws Exception {
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = criacaoAreaSegurancaProblemaTO();
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		AreaSeguranca areaSeguranca = areaSegurancaProblema.getArea(); 
		Usuario usuario = usuarioTest();
		Problema problema = areaSegurancaProblema.getProblema();
		Festa festa = areaSegurancaProblema.getFesta();
		List<Grupo> grupos = criacaoGrupos();
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);
		
		Mockito.when(areaSegurancaProblemaRepository.findAreaSegurancaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(areaSegurancaRepository.findAreaByCodFestaAndCodArea(Mockito.anyInt(), Mockito.anyInt())).thenReturn(areaSeguranca);
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(problemaRepository.findProblemaByCodProblema(Mockito.anyInt())).thenReturn(problema);
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa);
		Mockito.when(grupoRepository.findGruposPermissaoAreaSegurancaProblema(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(notificacaoService.criarMensagemAreaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn("ABCD");
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString())).thenReturn(true);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).inserirNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		
		boolean sucesso;
		try {
			areaSegurancaProblemaService.addProblemaSeguranca(areaSegurancaProblemaTO, usuarioTest().getCodUsuario());
			sucesso = true;
		}catch(ValidacaoException e){
			sucesso = false;
		};
		
		assertEquals(sucesso, false);
		
	}
	
	@Test
	void addProblemaSegurancaDataInvalida() throws Exception {
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = criacaoAreaSegurancaProblemaTO();
		areaSegurancaProblemaTO.setHorarioFim(LocalDateTime.of(1888, Month.JUNE, 22, 19, 10));
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		AreaSeguranca areaSeguranca = areaSegurancaProblema.getArea(); 
		Usuario usuario = usuarioTest();
		Problema problema = areaSegurancaProblema.getProblema();
		Festa festa = areaSegurancaProblema.getFesta();
		List<Grupo> grupos = criacaoGrupos();
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);
		
		Mockito.when(areaSegurancaProblemaRepository.findAreaSegurancaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(areaSegurancaRepository.findAreaByCodFestaAndCodArea(Mockito.anyInt(), Mockito.anyInt())).thenReturn(areaSeguranca);
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(problemaRepository.findProblemaByCodProblema(Mockito.anyInt())).thenReturn(problema);
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa);
		Mockito.when(grupoRepository.findGruposPermissaoAreaSegurancaProblema(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(notificacaoService.criarMensagemAreaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn("ABCD");
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString())).thenReturn(true);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).inserirNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		
		boolean sucesso;
		try {
			areaSegurancaProblemaService.addProblemaSeguranca(areaSegurancaProblemaTO, usuarioTest().getCodUsuario());
			sucesso = true;
		}catch(ValidacaoException e){
			sucesso = false;
		};
		
		assertEquals(sucesso, false);
		
	}
	
	@Test
	void addProblemaSegurancaDuplicado() throws Exception {
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = criacaoAreaSegurancaProblemaTO();
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		AreaSeguranca areaSeguranca = areaSegurancaProblema.getArea(); 
		Usuario usuario = usuarioTest();
		Problema problema = areaSegurancaProblema.getProblema();
		Festa festa = areaSegurancaProblema.getFesta();
		List<Grupo> grupos = criacaoGrupos();
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);
		
		Mockito.when(areaSegurancaProblemaRepository.findAreaSegurancaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn(areaSegurancaProblema);
		Mockito.when(areaSegurancaRepository.findAreaByCodFestaAndCodArea(Mockito.anyInt(), Mockito.anyInt())).thenReturn(areaSeguranca);
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(problemaRepository.findProblemaByCodProblema(Mockito.anyInt())).thenReturn(problema);
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa);
		Mockito.when(grupoRepository.findGruposPermissaoAreaSegurancaProblema(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(notificacaoService.criarMensagemAreaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn("ABCD");
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString())).thenReturn(true);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).inserirNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		
		boolean sucesso;
		try {
			areaSegurancaProblemaService.addProblemaSeguranca(areaSegurancaProblemaTO, usuarioTest().getCodUsuario());
			sucesso = true;
		}catch(ValidacaoException e){
			sucesso = false;
		};
		
		assertEquals(sucesso, false);
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
		
		Mockito.when(areaSegurancaProblemaRepository.findAreaSegurancaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(areaSegurancaRepository.findAreaByCodFestaAndCodArea(Mockito.anyInt(), Mockito.anyInt())).thenReturn(areaSeguranca);
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(problemaRepository.findProblemaByCodProblema(Mockito.anyInt())).thenReturn(problema);
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa);
		Mockito.when(grupoRepository.findGruposPermissaoAreaSegurancaProblema(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(notificacaoService.criarMensagemAreaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn("ABCD");
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString())).thenReturn(false);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).inserirNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		
		AreaSegurancaProblema retorno = areaSegurancaProblemaService.addProblemaSeguranca(areaSegurancaProblemaTO, usuarioTest().getCodUsuario());
			
		assertEquals(retorno.getArea(), areaSeguranca);
		assertEquals(retorno.getProblema(), problema);
		assertEquals(retorno.getFesta(), festa);
		assertEquals(retorno.getDescProblema(), areaSegurancaProblema.getDescProblema());
		assertEquals(retorno.getCodUsuarioEmissor(), usuario);
		assertEquals(retorno.getCodUsuarioResolv(), null);
		assertEquals(retorno.getHorarioInicio(), areaSegurancaProblema.getHorarioInicio());
		assertEquals(retorno.getHorarioFim(), areaSegurancaProblema.getHorarioFim());
		assertEquals(retorno.getStatusProblema(), TipoStatusProblema.ANDAMENTO.getValor());
		
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
		
		Mockito.when(areaSegurancaProblemaRepository.findAreaSegurancaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn(areaSegurancaProblema);
		Mockito.when(areaSegurancaRepository.findAreaByCodFestaAndCodArea(Mockito.anyInt(), Mockito.anyInt())).thenReturn(areaSeguranca);
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(problemaRepository.findProblemaByCodProblema(Mockito.anyInt())).thenReturn(problema);
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa);
		Mockito.when(grupoRepository.findGruposPermissaoAreaSegurancaProblema(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(notificacaoService.criarMensagemAreaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn("ABCD");
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString())).thenReturn(true);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).inserirNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		
		AreaSegurancaProblema retorno = areaSegurancaProblemaService.updateProblemaSeguranca(areaSegurancaProblemaTO, usuarioTest().getCodUsuario());
		
		assertEquals(retorno.getArea(), areaSeguranca);
		assertEquals(retorno.getProblema(), problema);
		assertEquals(retorno.getFesta(), festa);
		assertEquals(retorno.getDescProblema(), areaSegurancaProblema.getDescProblema());
		assertEquals(retorno.getCodUsuarioEmissor(), usuario);
		assertEquals(retorno.getCodUsuarioResolv(), usuario);
		assertEquals(retorno.getHorarioInicio(), areaSegurancaProblema.getHorarioInicio());
		assertEquals(retorno.getHorarioFim(), areaSegurancaProblema.getHorarioFim());
		assertEquals(retorno.getStatusProblema(), TipoStatusProblema.FINALIZADO.getValor());
		
	}
	
	@Test
	void updateProblemaSegurancaEngano() throws Exception {
		AreaSegurancaProblemaTO areaSegurancaProblemaTO = criacaoAreaSegurancaProblemaTO();
		areaSegurancaProblemaTO.setStatusProblema(TipoStatusProblema.ENGANO.getValor());
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		AreaSeguranca areaSeguranca = areaSegurancaProblema.getArea(); 
		Usuario usuario = usuarioTest();
		Problema problema = areaSegurancaProblema.getProblema();
		Festa festa = areaSegurancaProblema.getFesta();
		List<Grupo> grupos = criacaoGrupos();
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);
		
		Mockito.when(areaSegurancaProblemaRepository.findAreaSegurancaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn(areaSegurancaProblema);
		Mockito.when(areaSegurancaRepository.findAreaByCodFestaAndCodArea(Mockito.anyInt(), Mockito.anyInt())).thenReturn(areaSeguranca);
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(problemaRepository.findProblemaByCodProblema(Mockito.anyInt())).thenReturn(problema);
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa);
		Mockito.when(grupoRepository.findGruposPermissaoAreaSegurancaProblema(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(notificacaoService.criarMensagemAreaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn("ABCD");
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString())).thenReturn(true);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).inserirNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		
		boolean sucesso;
		try {
			areaSegurancaProblemaService.updateProblemaSeguranca(areaSegurancaProblemaTO, usuarioTest().getCodUsuario());
			sucesso = true;
		}catch(ValidacaoException e){
			sucesso = false;
		};
		
		assertEquals(sucesso, false);
		
	}
	
	@Test
	void updateProblemaSegurancaNExist() throws Exception {
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
		
		Mockito.when(areaSegurancaProblemaRepository.findAreaSegurancaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(areaSegurancaRepository.findAreaByCodFestaAndCodArea(Mockito.anyInt(), Mockito.anyInt())).thenReturn(areaSeguranca);
		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(problemaRepository.findProblemaByCodProblema(Mockito.anyInt())).thenReturn(problema);
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa);
		Mockito.when(grupoRepository.findGruposPermissaoAreaSegurancaProblema(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(notificacaoService.criarMensagemAreaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn("ABCD");
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString())).thenReturn(true);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).inserirNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		
		boolean sucesso;
		try {
			areaSegurancaProblemaService.updateProblemaSeguranca(areaSegurancaProblemaTO, usuarioTest().getCodUsuario());
			sucesso = true;
		}catch(ValidacaoException e){
			sucesso = false;
		};
		
		assertEquals(sucesso, false);
		
	}
	
	@Test
	void deleteProblemaSegurancaSucesso() throws Exception {
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		areaSegurancaProblema .setStatusProblema(TipoStatusProblema.FINALIZADO.getValor());
		int codAreaSeguranca = areaSegurancaProblema.getArea().getCodArea();
		int codProblema = areaSegurancaProblema.getProblema().getCodProblema();
		int codFesta = areaSegurancaProblema.getFesta().getCodFesta();
		Usuario usuario = usuarioTest();

		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());
		Mockito.when(areaSegurancaProblemaRepository.findAreaSegurancaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn(areaSegurancaProblema);
		
		AreaSegurancaProblema retorno = areaSegurancaProblemaService.deleteProblemaSeguranca(codAreaSeguranca, codProblema, codFesta, usuario.getCodUsuario());
		
		assertEquals(retorno.getArea(), areaSegurancaProblema.getArea());
		assertEquals(retorno.getProblema(), areaSegurancaProblema.getProblema());
		assertEquals(retorno.getFesta(), areaSegurancaProblema.getFesta());
		assertEquals(retorno.getDescProblema(), areaSegurancaProblema.getDescProblema());
		assertEquals(retorno.getCodUsuarioEmissor(), areaSegurancaProblema.getCodUsuarioEmissor());
		assertEquals(retorno.getCodUsuarioResolv(), areaSegurancaProblema.getCodUsuarioResolv());
		assertEquals(retorno.getHorarioInicio(), areaSegurancaProblema.getHorarioInicio());
		assertEquals(retorno.getHorarioFim(), areaSegurancaProblema.getHorarioFim());
		assertEquals(retorno.getStatusProblema(), TipoStatusProblema.FINALIZADO.getValor());
		
	}
	
	@Test
	void deleteProblemaSegurancaNaoExiste() throws Exception {
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		areaSegurancaProblema .setStatusProblema(TipoStatusProblema.FINALIZADO.getValor());
		int codAreaSeguranca = areaSegurancaProblema.getArea().getCodArea();
		int codProblema = areaSegurancaProblema.getProblema().getCodProblema();
		int codFesta = areaSegurancaProblema.getFesta().getCodFesta();
		Usuario usuario = usuarioTest();

		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());
		Mockito.when(areaSegurancaProblemaRepository.findAreaSegurancaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);
		
		boolean sucesso;
		try {
			areaSegurancaProblemaService.deleteProblemaSeguranca(codAreaSeguranca, codProblema, codFesta, usuario.getCodUsuario());
			sucesso = true;
		}catch(ValidacaoException e){
			sucesso = false;
		};
		
		assertEquals(sucesso, false);
	}
	
	@Test
	void findProblemaSegurancaSucesso() throws Exception {
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		int codAreaSeguranca = areaSegurancaProblema.getArea().getCodArea();
		int codProblema = areaSegurancaProblema.getProblema().getCodProblema();
		int codFesta = areaSegurancaProblema.getFesta().getCodFesta();

		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());
		Mockito.when(areaSegurancaProblemaRepository.findAreaSegurancaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn(areaSegurancaProblema);
		
		
		AreaSegurancaProblema retorno = areaSegurancaProblemaService.findProblemaSeguranca(codAreaSeguranca, codProblema, codFesta, usuarioTest().getCodUsuario());
		
		assertEquals(retorno, areaSegurancaProblema);
		
	}
	
	@Test
	void findProblemaSegurancaUsuarioSemPermissao() throws Exception {
		List<Grupo> grupos = new ArrayList<>();
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		int codAreaSeguranca = areaSegurancaProblema.getArea().getCodArea();
		int codProblema = areaSegurancaProblema.getProblema().getCodProblema();
		int codFesta = areaSegurancaProblema.getFesta().getCodFesta();

		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(areaSegurancaProblemaRepository.findAreaSegurancaProblema(Mockito.anyInt(), Mockito.anyInt())).thenReturn(areaSegurancaProblema);
		
		boolean sucesso;
		try {
			areaSegurancaProblemaService.findProblemaSeguranca(codAreaSeguranca, codProblema, codFesta, usuarioTest().getCodUsuario());
			sucesso = true;
		}catch(ValidacaoException e){
			sucesso = false;
		};
		
		assertEquals(sucesso, false);
		
	}
	
	@Test
	void findAllProblemasSegurancaAreaSucesso() throws Exception {
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		List<AreaSegurancaProblema> areasSegurancaProblema = new ArrayList<>();
		areasSegurancaProblema.add(areaSegurancaProblema);
		int codAreaSeguranca = areaSegurancaProblema.getArea().getCodArea();
		int codFesta = areaSegurancaProblema.getFesta().getCodFesta();

		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());
		Mockito.when(areaSegurancaProblemaRepository.findAllAreaSegurancaProblemaArea(Mockito.anyInt(), Mockito.anyInt())).thenReturn(areasSegurancaProblema);
		
		List<AreaSegurancaProblema> retorno = areaSegurancaProblemaService.findAllProblemasSegurancaArea(codAreaSeguranca, codFesta, usuarioTest().getCodUsuario());
		
		assertEquals(retorno.get(0), areaSegurancaProblema);
		
	}
	
	@Test
	void findAllProblemasSegurancaFestaSucesso() throws Exception {
		AreaSegurancaProblema areaSegurancaProblema = criacaoAreaSegurancaProblema();
		List<AreaSegurancaProblema> areasSegurancaProblema = new ArrayList<>();
		areasSegurancaProblema.add(areaSegurancaProblema);
		int codFesta = areaSegurancaProblema.getFesta().getCodFesta();

		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());
		Mockito.when(areaSegurancaProblemaRepository.findAllAreaSegurancaProblemaFesta(Mockito.anyInt())).thenReturn(areasSegurancaProblema);
		
		List<AreaSegurancaProblema> retorno = areaSegurancaProblemaService.findAllProblemasSegurancaFesta(codFesta, usuarioTest().getCodUsuario());
		
		assertEquals(retorno.get(0), areaSegurancaProblema);
		
	}
	
}
