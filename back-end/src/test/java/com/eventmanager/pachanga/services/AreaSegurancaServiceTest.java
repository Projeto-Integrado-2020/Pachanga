package com.eventmanager.pachanga.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.eventmanager.pachanga.dtos.NotificacaoAreaSegurancaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.NotificacaoAreaSegurancaTOFactory;
import com.eventmanager.pachanga.repositories.AreaSegurancaProblemaRepository;
import com.eventmanager.pachanga.repositories.AreaSegurancaRepository;
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
@WebMvcTest(value=AreaSegurancaService.class)
class AreaSegurancaServiceTest {
	
	@Autowired
	private AreaSegurancaService areaService;
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	@MockBean
	private AreaSegurancaRepository areaSegurancaRepository;

	@MockBean
	private AreaSegurancaProblemaRepository areaSegurancaProblemaRepository;

	@MockBean
	private GrupoRepository grupoRepository;

	@MockBean
	private FestaService festaService;

	@MockBean
	private ProblemaRepository problemaRepository;

	@MockBean
	private NotificacaoService notificacaoService;

	@MockBean
	private UsuarioRepository usuarioRepository;

	@MockBean
	private NotificacaoAreaSegurancaTOFactory notificacaoAreaFactory;
	
	@MockBean
	private AreaSegurancaProblemaService areaSegurancaProblemaService;
	
	@MockBean
	private GrupoService grupoService;

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
	
	public AreaSegurancaProblema criacaoAreaSegurancaProblema() {
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
	
	private Festa criacaoFesta() {
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
	
	@Test
	void listaAreaSegurancaFestaSucesso() {
		
		List<AreaSeguranca> areas = new ArrayList<>();
		areas.add(areaTest());
		
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());
		Mockito.when(areaSegurancaRepository.findAllAreasByCodFesta(Mockito.anyInt())).thenReturn(areas);
		
		Map<AreaSeguranca, List<AreaSegurancaProblema>> areasRetorno = areaService.listaAreaSegurancaFesta(1, 1);
		
		assertEquals(true, !areasRetorno.isEmpty());
		
	}
	
	@Test
	void criacaoAreSegurancaFestaSucesso() {
		
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());
		Mockito.when(areaSegurancaRepository.getNextValMySequence()).thenReturn(3);
		Mockito.when(areaSegurancaRepository.findAreaByNomeArea(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		
		AreaSeguranca areaRetorno = areaService.criacaoAreSegurancaFesta(1, this.areaTest());
		
		assertEquals(true, areaRetorno.getCodArea() == 3);
		
	}
	
	@Test
	void criacaoAreSegurancaFestaNomeAreaJaEmUso() {
		
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());
		Mockito.when(areaSegurancaRepository.getNextValMySequence()).thenReturn(3);
		Mockito.when(areaSegurancaRepository.findAreaByNomeArea(Mockito.anyString(), Mockito.anyInt())).thenReturn(areaTest());
		
		String expect = "";
		
		try {
			areaService.criacaoAreSegurancaFesta(1, this.areaTest());			
		} catch (ValidacaoException e) {
			expect = e.getMessage();
		}
		
		assertEquals("NOMEAREA",expect);
		
	}
	
	@Test
	void deletarAreSegurancaFestaAreaNaoEncontrada() {
		
		List<Grupo> grupos = this.criacaoGrupos();
		
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuarioTest());
		
		List<AreaSegurancaProblema> areasSegurancasProblemas = new ArrayList<>();
		
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());
		Mockito.when(areaSegurancaRepository.findAreaCodArea(Mockito.anyInt())).thenReturn(null);
		Mockito.when(grupoRepository.findGruposFesta(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).deleteNotificacao(Mockito.anyInt(), Mockito.anyString());
		Mockito.doNothing().when(notificacaoService).deletarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString());
		Mockito.doNothing().when(areaSegurancaProblemaRepository).deleteAll(Mockito.anyIterable());
		Mockito.doNothing().when(areaSegurancaRepository).delete(Mockito.any());
		Mockito.when(areaSegurancaProblemaRepository.findProblemasArea(Mockito.anyInt())).thenReturn(areasSegurancasProblemas);
		
		String expect = "";
		
		try {			
			areaService.deletarAreSegurancaFesta(1, 1);
		} catch (ValidacaoException e) {
			expect = e.getMessage();
		}
		
		assertEquals("AREANFOU",expect);
		
	}
	
	@Test
	void deletarAreSegurancaFestaSucesso() {
		
		AreaSeguranca area = areaTest();
		
		List<Grupo> grupos = this.criacaoGrupos();
		
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuarioTest());
		
		List<AreaSegurancaProblema> areasSegurancasProblemas = new ArrayList<>();
		areasSegurancasProblemas.add(criacaoAreaSegurancaProblema());
		
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());
		Mockito.when(areaSegurancaRepository.findAreaCodArea(Mockito.anyInt())).thenReturn(area);
		Mockito.when(grupoRepository.findGruposFesta(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.doNothing().when(notificacaoService).deleteNotificacao(Mockito.anyInt(), Mockito.anyString());
		Mockito.doNothing().when(notificacaoService).deletarNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString());
		Mockito.doNothing().when(areaSegurancaProblemaRepository).deleteAll(Mockito.anyIterable());
		Mockito.doNothing().when(areaSegurancaRepository).delete(Mockito.any());
		Mockito.when(areaSegurancaProblemaRepository.findProblemasArea(Mockito.anyInt())).thenReturn(areasSegurancasProblemas);
		
		areaService.deletarAreSegurancaFesta(1, 1);
		
	}
	
	@Test
	void atualizarAreSegurancaFestaSucesso() {
		
		AreaSeguranca area = areaTest();
		
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());
		Mockito.when(areaSegurancaRepository.findAreaByNomeArea(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(areaSegurancaRepository.findAreaCodArea(Mockito.anyInt())).thenReturn(area);
		
		AreaSeguranca areaRetorno = areaService.atualizarAreSegurancaFesta(1, this.areaTest());
		
		assertEquals(true, areaRetorno.getNomeArea().equals(area.getNomeArea()));
		
	}
	
	@Test
	void getNotificacaoProblemaAreaSucesso() {
		
		AreaSeguranca area = areaTest();
		
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());
		Mockito.when(areaSegurancaRepository.findAreaCodArea(Mockito.anyInt())).thenReturn(area);
		Mockito.when(areaSegurancaProblemaService.validarProblemaSeguranca(Mockito.anyInt())).thenReturn(criacaoAreaSegurancaProblema());
		Mockito.when(problemaRepository.findProblemaByCodProblema(Mockito.anyInt())).thenReturn(new Problema());
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(new Festa());
		Mockito.when(notificacaoAreaFactory.getNotificacaoArea(Mockito.any(), Mockito.any())).thenReturn(new NotificacaoAreaSegurancaTO());
		
		NotificacaoAreaSegurancaTO notificacaoArea = areaService.getNotificacaoProblemaArea(1);
		
		assertEquals(true, notificacaoArea != null);
		
	}

}
