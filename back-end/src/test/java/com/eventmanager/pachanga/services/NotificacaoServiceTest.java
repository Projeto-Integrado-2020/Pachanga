package com.eventmanager.pachanga.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;

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

import com.eventmanager.pachanga.domains.Convidado;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Notificacao;
import com.eventmanager.pachanga.domains.NotificacaoConvidado;
import com.eventmanager.pachanga.domains.NotificacaoGrupo;
import com.eventmanager.pachanga.domains.NotificacaoUsuario;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.NotificacaoConvidadoTO;
import com.eventmanager.pachanga.dtos.NotificacaoGrupoTO;
import com.eventmanager.pachanga.dtos.NotificacaoTO;
import com.eventmanager.pachanga.dtos.NotificacaoUsuarioTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.NotificacaoFactory;
import com.eventmanager.pachanga.repositories.ConvidadoRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.NotificacaoConvidadoRepository;
import com.eventmanager.pachanga.repositories.NotificacaoGrupoRepository;
import com.eventmanager.pachanga.repositories.NotificacaoRepository;
import com.eventmanager.pachanga.repositories.NotificacaoUsuarioRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoNotificacao;
import com.eventmanager.pachanga.tipo.TipoStatusNotificacao;

@RunWith(SpringRunner.class)
@WebMvcTest(value=NotificacaoService.class)
class NotificacaoServiceTest {

	@Autowired
	private NotificacaoService notificacaoService;

	@MockBean
	private NotificacaoRepository notificacaoRepository;

	@MockBean
	private UsuarioRepository usuarioRepository;

	@MockBean
	private ConvidadoRepository convidadoRepository;

	@MockBean
	private GrupoRepository grupoRepository;

	@MockBean
	private NotificacaoUsuarioRepository notificacaoUsuarioRepository;

	@MockBean
	private NotificacaoGrupoRepository notificacaoGrupoRepository;
	
	@MockBean
	private NotificacaoFactory notificacaoFactory;
	
	@MockBean
	private UsuarioService usuarioService;
	
	@MockBean
	private FestaService festaService;
	
	@MockBean
	private ProdutoService produtoService;

	@MockBean
	private NotificacaoConvidadoRepository notificacaoConvidadoRepository;
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private AreaSegurancaService areaService;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	@SuppressWarnings("deprecation")
	private Usuario usuarioTest(){
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setGenero("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");

		return usuarioTest;
	}

	private Notificacao notificacaoTest() {
		Notificacao notificacao = new Notificacao();
		notificacao.setCodNotificacao(1);
		notificacao.setDescNotificacao("teste");
		return notificacao;
	}

	private NotificacaoUsuario notificacaoUsuarioTest() {
		NotificacaoUsuario notificacaoUsuario = new NotificacaoUsuario();
		notificacaoUsuario.setDestaque(false);
		notificacaoUsuario.setMensagem("teste");
		notificacaoUsuario.setStatus(TipoStatusNotificacao.NAOLIDA.getDescricao());
		return notificacaoUsuario;
	}
	
	private NotificacaoUsuarioTO notificacaoUsuarioTOtest() {
		NotificacaoUsuarioTO notificacaoUsuarioTo = new NotificacaoUsuarioTO();
		notificacaoUsuarioTo.setMensagem(TipoNotificacao.CONVACEI.getValor() + "?12&" + "13");
		return notificacaoUsuarioTo;
	}
	
	private NotificacaoConvidadoTO notificacaoConvidadoTOtest() {
		NotificacaoConvidadoTO notificacaoConvidadoTo = new NotificacaoConvidadoTO();
		notificacaoConvidadoTo.setMensagem(TipoNotificacao.CONVFEST.getValor() + "?12&" + "13");
		return notificacaoConvidadoTo;
	}
	
	private NotificacaoGrupoTO notificacaoGrupoTOtest() {
		NotificacaoGrupoTO notificacaoGrupoTo = new NotificacaoGrupoTO();
		notificacaoGrupoTo.setMensagem(TipoNotificacao.ESTBAIXO.getValor() + "?12&" + "13&" + "13");
		return notificacaoGrupoTo;
	}
	
	private NotificacaoGrupo notificacaoGrupotest() {
		NotificacaoGrupo notificacaoGrupo = new NotificacaoGrupo();
		notificacaoGrupo.setMensagem(TipoNotificacao.ESTBAIXO.getValor() + "?12&" + "13&" + "13");
		return notificacaoGrupo;
	}
	
	private Grupo grupoTest() {	
		Grupo grupo = new Grupo();	
		grupo.setCodGrupo(1);	
		grupo.setNomeGrupo("CONVIDADO");	
		return grupo;	
	}
	
	private NotificacaoTO notificacaoToTest() {
		List<NotificacaoUsuarioTO> notificacoesUsuario = new ArrayList<>();
		List<NotificacaoConvidadoTO> notificacoesConvidado = new ArrayList<>();
		List<NotificacaoGrupoTO> notificacoesGrupo = new ArrayList<>();
		
		NotificacaoUsuarioTO notificacaoUsuarioEstBaixo = notificacaoUsuarioTOtest();
		notificacaoUsuarioEstBaixo.setMensagem(TipoNotificacao.ESTBAIXO.getValor() + "?12&" + "13&" + "13");
		
		NotificacaoUsuarioTO notificacaoUsuarioMudancaStatus = notificacaoUsuarioTOtest();
		notificacaoUsuarioMudancaStatus.setMensagem(TipoNotificacao.STAALTER.getValor() + "?12&" + "I");
		
		NotificacaoUsuarioTO notificacaoUsuario = notificacaoUsuarioTOtest();
		notificacaoUsuario.setMensagem("teste" + "?12&" + "I");
		
		NotificacaoUsuarioTO notificacaoAreaProblema = notificacaoUsuarioTOtest();
		notificacaoUsuario.setMensagem(TipoNotificacao.AREAPROB.getValor() + "?12&" + "1");
		
		notificacoesUsuario.add(notificacaoUsuarioTOtest());
		notificacoesUsuario.add(notificacaoUsuarioMudancaStatus);
		notificacoesUsuario.add(notificacaoUsuarioEstBaixo);
		notificacoesUsuario.add(notificacaoUsuario);
		notificacoesUsuario.add(notificacaoAreaProblema);
		
		NotificacaoConvidadoTO notificacaoConvidado = notificacaoConvidadoTOtest();
		notificacaoConvidado.setMensagem(TipoNotificacao.ESTBAIXO.getValor() + "?12&" + "13&" + "13");
		
		notificacoesConvidado.add(notificacaoConvidadoTOtest());
		notificacoesConvidado.add(notificacaoConvidado);
		
		notificacoesGrupo.add(notificacaoGrupoTOtest());
		
		NotificacaoTO notificacaoTo = new NotificacaoTO();
		notificacaoTo.setNotificacaoConvidado(notificacoesConvidado);
		notificacaoTo.setNotificacoesGrupo(notificacoesGrupo);
		notificacaoTo.setNotificacoesUsuario(notificacoesUsuario);
		return notificacaoTo;
		
	}

	@Test
	void procurarNoticacaoUsuario() {

		Mockito.when(notificacaoUsuarioRepository.getNotificacoesUsuario(Mockito.anyInt())).thenReturn(new ArrayList<NotificacaoUsuario>());
		
		Mockito.when(notificacaoGrupoRepository.getNotificacoesGrupo(Mockito.anyInt())).thenReturn(new ArrayList<NotificacaoGrupo>());
		
		Mockito.when(notificacaoConvidadoRepository.findConvidadoNotificacaoByEmail(Mockito.anyString())).thenReturn(new ArrayList<NotificacaoConvidado>());

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		Mockito.when(notificacaoFactory.getNotificacaoTO(Mockito.anyList(), Mockito.anyList(), Mockito.anyList())).thenReturn(notificacaoToTest());

		NotificacaoTO notificacaoTo = notificacaoService.procurarNotificacaoUsuario(100);

		assertEquals(true, !notificacaoTo.getNotificacaoConvidado().isEmpty());
		assertEquals(true, !notificacaoTo.getNotificacoesGrupo().isEmpty());

	}

	@Test
	void procurarNoticacaoUsuarioUsuarioNaoEncontrado() {

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(null);

		boolean erro = false;
		String mensagemErro = null;
		try {
			notificacaoService.procurarNotificacaoUsuario(100);			
		} catch (ValidacaoException e) {
			erro = true;
			mensagemErro = e.getMessage();
		}

		assertEquals(true, erro);

		assertEquals("USERNFOU", mensagemErro);

	}

	@Test
	void deletarNotificacaoConvidado() {
		
		Convidado convidado = new Convidado();
		convidado.setEmail("teste@teste.com");

		Mockito.when(notificacaoRepository.findByCodNotificacao(Mockito.anyInt())).thenReturn(new Notificacao());

		doNothing().when(notificacaoRepository).deleteConvidadoNotificacao(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());

		Mockito.when(convidadoRepository.findByIdConvidado(Mockito.anyInt())).thenReturn(convidado);

		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuarioTest());

		notificacaoService.deletarNotificacaoConvidado(1,100, "TESTE");

	}

	@Test
	void deletarNotificacaoConvidadoNotificacaoNaoEncontrado() {

		Mockito.when(notificacaoRepository.findByCodNotificacao(Mockito.anyInt())).thenReturn(null);

		doNothing().when(notificacaoRepository).deleteConvidadoNotificacao(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());

		Mockito.when(convidadoRepository.findByEmail(Mockito.anyString())).thenReturn(new Convidado());

		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuarioTest());

		boolean erro = false;
		String mensagemErro = null;
		try {
			notificacaoService.deletarNotificacaoConvidado(1,100,"TESTE");
		} catch (ValidacaoException e) {
			erro = true;
			mensagemErro = e.getMessage();
		}

		assertEquals(true, erro);

		assertEquals("NOTINFOU", mensagemErro);
	}

	@Test
	void deletarNotificacaoConvidadoConvidadoNaoEncontrado() {

		Mockito.when(notificacaoRepository.findByCodNotificacao(Mockito.anyInt())).thenReturn(new Notificacao());

		doNothing().when(notificacaoRepository).deleteConvidadoNotificacao(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());

		Mockito.when(convidadoRepository.findByEmail(Mockito.anyString())).thenReturn(null);

		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuarioTest());

		boolean erro = false;
		String mensagemErro = null;
		try {
			notificacaoService.deletarNotificacaoConvidado(1,100,"TESTE");
		} catch (ValidacaoException e) {
			erro = true;
			mensagemErro = e.getMessage();
		}

		assertEquals(true, erro);

		assertEquals("CONVNFOU", mensagemErro);
	}

	@Test
	void deletarNotificacaoGrupo() {

		Mockito.when(notificacaoRepository.findByCodNotificacao(Mockito.anyInt())).thenReturn(notificacaoTest());

		doNothing().when(notificacaoRepository).deleteNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString());

		Mockito.when(grupoRepository.findByCod(Mockito.anyInt())).thenReturn(new Grupo());

		notificacaoService.deletarNotificacaoGrupo(1,"teste");

	}
	
	@Test
	void  deleteNotificacoesGruposSucessoTest() {
		NotificacaoGrupo notificacaoGrupo =  notificacaoGrupotest();
		List<NotificacaoGrupo> notificacoesGrupo = new ArrayList<>();
		notificacoesGrupo.add(notificacaoGrupo);
		
		List<Integer> codGrupo = new ArrayList<>();
		codGrupo.add(1);

		Mockito.when(notificacaoGrupoRepository.findNotificacoesGrupos(Mockito.anyList())).thenReturn(notificacoesGrupo);
		doNothing().when(notificacaoGrupoRepository).deleteAll(Mockito.any());
		
		notificacaoService.deleteNotificacoesGrupos(codGrupo);

	}
	
	@Test
	void deletarNotificacaoConvidadoSucesso() {

		doNothing().when(notificacaoConvidadoRepository).deleteNotificacoesConvidado(Mockito.anyInt());

		Mockito.when(convidadoRepository.findByIdConvidado(Mockito.anyInt())).thenReturn(new Convidado());

		notificacaoService.deletarNotificacoesConvidado(1);

	}

	@Test
	void deletarNotificacaoGrupoNotFound() {

		Mockito.when(notificacaoRepository.findByCodNotificacao(Mockito.anyInt())).thenReturn(notificacaoTest());

		doNothing().when(notificacaoRepository).deleteNotificacaoGrupo(Mockito.anyInt(), Mockito.anyString());

		Mockito.when(grupoRepository.findByCod(Mockito.anyInt())).thenReturn(null);

		boolean erro = false;
		String mensagemErro = null;
		try {
			notificacaoService.deletarNotificacaoGrupo(1,"teste");
		} catch (ValidacaoException e) {
			erro = true;
			mensagemErro = e.getMessage();
		}
		assertEquals(true, erro);

		assertEquals("GRUPNFOU", mensagemErro);

	}

	@Test
	void inserirNotificacaoGrupo() {

		Mockito.when(notificacaoRepository.findByCodNotificacao(Mockito.anyInt())).thenReturn(notificacaoTest());

		doNothing().when(notificacaoGrupoRepository).insertNotificacaoGrupo(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.any());

		Mockito.when(grupoRepository.findByCod(Mockito.anyInt())).thenReturn(new Grupo());

		notificacaoService.inserirNotificacaoGrupo(1, 100, Mockito.anyString());

	}

	@Test
	void inserirNotificacaoConvidado() {
		
		Convidado convidado = new Convidado();
		convidado.setEmail("teste@teste.com");

		Mockito.when(notificacaoRepository.findByCodNotificacao(Mockito.anyInt())).thenReturn(new Notificacao());

		Mockito.when(convidadoRepository.findByIdConvidado(Mockito.anyInt())).thenReturn(convidado);

		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuarioTest());

		doNothing().when(notificacaoConvidadoRepository).insertConvidadoNotificacao(Mockito.anyInt(), Mockito.anyInt(),  Mockito.anyInt(), Mockito.anyString(), Mockito.any());

		notificacaoService.inserirNotificacaoConvidado(1,100, "teste");

	}
	
	@Test
	void inserirNotificacaoUsuario() {
		
		Convidado convidado = new Convidado();
		convidado.setEmail("teste@teste.com");

		Mockito.when(notificacaoRepository.findByCodNotificacao(Mockito.anyInt())).thenReturn(new Notificacao());

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		doNothing().when(notificacaoUsuarioRepository).insertNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt(),  Mockito.anyInt(), Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString(), Mockito.any());

		notificacaoService.inserirNotificacaoUsuario(1,100, "teste");

	}

	@Test
	void alterarStatusNotificacaoUsuarioTest() {

		Mockito.when(notificacaoUsuarioRepository.getNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(notificacaoUsuarioTest());

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		Mockito.when(notificacaoUsuarioRepository.save(Mockito.any())).thenReturn(null);

		notificacaoService.alterarStatus(1,1);

	}
	
	@Test
	void alterarStatusNotificacaoUsuarioNullTest() {
		Mockito.when(notificacaoUsuarioRepository.getNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		Mockito.when(notificacaoUsuarioRepository.save(Mockito.any())).thenReturn(null);

		notificacaoService.alterarStatus(1,1);

	}
	
	@Test
	void alterarStatusNotificacaoUsuarioDestaqueTrueTest() {
		NotificacaoUsuario notificacaoUsuario = notificacaoUsuarioTest();
		notificacaoUsuario.setDestaque(true);
		
		Mockito.when(notificacaoUsuarioRepository.getNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(notificacaoUsuario);

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		Mockito.when(notificacaoUsuarioRepository.save(Mockito.any())).thenReturn(null);

		notificacaoService.alterarStatus(1,1);

	}

	@Test
	void deleteNotificacaoUsuarioTest() {
		List<NotificacaoUsuario> notificacoesUsuario = new ArrayList<>();
		notificacoesUsuario.add(notificacaoUsuarioTest());

		Mockito.when(notificacaoUsuarioRepository.getNotificacaoUsuarioByMensagem(Mockito.anyInt(), Mockito.anyString())).thenReturn(notificacoesUsuario);

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		doNothing().when(notificacaoUsuarioRepository).delete(Mockito.any());

		notificacaoService.deleteNotificacao(1,"teste");

	}
	
	@Test
	void deleteNotificacaoUsuarioIsEmptyTest() {
		List<NotificacaoUsuario> notificacoesUsuario = new ArrayList<>();
		//notificacoesUsuario.add(notificacaoUsuarioTest());

		Mockito.when(notificacaoUsuarioRepository.getNotificacaoUsuarioByMensagem(Mockito.anyInt(), Mockito.anyString())).thenReturn(notificacoesUsuario);

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		doNothing().when(notificacaoUsuarioRepository).delete(Mockito.any());

		notificacaoService.deleteNotificacao(1,"teste");

	}
	
	@Test
	void criacaoMensagemNotificacaoUsuarioConvidadoTest() {
		
		String mensagem = notificacaoService.criacaoMensagemNotificacaoUsuarioConvidado(1, 2, TipoNotificacao.CONVACEI.getValor());
		
		assertEquals(TipoNotificacao.CONVACEI.getValor() + "?1&2", mensagem);

	}
	
	@Test
	void criarMensagemEstoqueBaixoTest() {
		
		String mensagem = notificacaoService.criarMensagemEstoqueBaixo(1, 2, 3);
		
		assertEquals(TipoNotificacao.ESTBAIXO.getValor() + "?1&2&3", mensagem);

	}
	
	@Test
	void criarMensagemAlteracaoStatusFestaTest() {
		
		String mensagem = notificacaoService.criarMensagemAlteracaoStatusFesta(1, "I");
		
		assertEquals(TipoNotificacao.STAALTER.getValor() + "?1&I", mensagem);

	}
	
	@Test
	void deletarNotificacaoGrupoTest() {
		
		Mockito.doNothing().when(notificacaoRepository).deleteNotificacaoGrupo(Mockito.anyString());
		
		notificacaoService.deletarNotificacaoGrupo("teste");
		
	}
	
	@Test
	void criarMensagemAreaProblemaTest() {
		
		String mensagem = notificacaoService.criarMensagemAreaProblema(1, 1);
		
		assertEquals(TipoNotificacao.AREAPROB.getValor() + "?1&1", mensagem);

	}

	@Test
	void destaqueNotificacaoUsuarioTest() {

		Mockito.when(notificacaoUsuarioRepository.getNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(notificacaoUsuarioTest());

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		Mockito.when(notificacaoUsuarioRepository.save(Mockito.any())).thenReturn(null);

		notificacaoService.destaqueNotificacao(1,1);

	}
	
	@Test
	void destaqueNotificacaoUsuarioNotificacaoUsuarioNullTest() {
		Mockito.when(notificacaoUsuarioRepository.getNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		Mockito.when(notificacaoUsuarioRepository.save(Mockito.any())).thenReturn(null);

		notificacaoService.destaqueNotificacao(1,1);

	}
	
	@Test
	void destaqueNotificacaoUsuarioNotificacaoUsuarioNullDestaqueTrueTest() {
		NotificacaoUsuario notificacaoUsuario = notificacaoUsuarioTest();
		notificacaoUsuario .setDestaque(true);

		Mockito.when(notificacaoUsuarioRepository.getNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(notificacaoUsuario);

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		Mockito.when(notificacaoUsuarioRepository.save(Mockito.any())).thenReturn(null);

		notificacaoService.destaqueNotificacao(1,1);

	}
	
	@Test	
	void verificarNotificacaoGrupoSucessoTest() {	
		Grupo grupo = grupoTest();	
		Notificacao notificacao = notificacaoTest();	
		NotificacaoGrupo notificacaoGrupo = new NotificacaoGrupo();	
		notificacaoGrupo.setGrupo(grupo);	
		notificacaoGrupo.setNotificacao(notificacao);	
		int codGrupo = grupo.getCodGrupo();	
		
		List<NotificacaoGrupo> notificacoesGrupo = new ArrayList<>();
		notificacoesGrupo.add(notificacaoGrupo);

		Mockito.when(grupoRepository.findByCod(codGrupo)).thenReturn(grupo);	
		Mockito.when(notificacaoGrupoRepository.getNotificacoesGrupoByMensagem(Mockito.anyInt(), Mockito.anyString())).thenReturn(notificacoesGrupo);	

		boolean retorno = notificacaoService.verificarNotificacaoGrupo(codGrupo, "teste");	

		assertEquals(false, retorno);	
	}	

	@Test	
	void verificarNotificacaoGrupoFalhaTest() {	
		Grupo grupo = grupoTest();	
		int codGrupo = grupo.getCodGrupo();	
		
		List<NotificacaoGrupo> notificacoesGrupo = new ArrayList<>();

		Mockito.when(grupoRepository.findByCod(codGrupo)).thenReturn(grupo);	
		Mockito.when(notificacaoGrupoRepository.getNotificacoesGrupoByMensagem(Mockito.anyInt(), Mockito.anyString())).thenReturn(notificacoesGrupo);

		boolean retorno = notificacaoService.verificarNotificacaoGrupo(codGrupo, "teste");	

		assertEquals(true, retorno);	
	}

}
