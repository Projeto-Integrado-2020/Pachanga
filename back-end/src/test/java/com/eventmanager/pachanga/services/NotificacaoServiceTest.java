package com.eventmanager.pachanga.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Convidado;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Notificacao;
import com.eventmanager.pachanga.domains.NotificacaoGrupo;
import com.eventmanager.pachanga.domains.NotificacaoUsuario;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.NotificacaoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.NotificacaoFactory;
import com.eventmanager.pachanga.repositories.ConvidadoRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.NotificacaoConvidadoRepository;
import com.eventmanager.pachanga.repositories.NotificacaoGrupoRepository;
import com.eventmanager.pachanga.repositories.NotificacaoRepository;
import com.eventmanager.pachanga.repositories.NotificacaoUsuarioRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
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
	private NotificacaoConvidadoRepository notificacaoConvidadoRepository;

	@SuppressWarnings("deprecation")
	private Usuario usuarioTest(){
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setSexo("M");
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
	
	public Grupo grupoTest() {
		Grupo grupo = new Grupo();
		grupo.setCodGrupo(1);
		grupo.setNomeGrupo("CONVIDADO");
		return grupo;
	}

	@Test
	void procurarNoticacaoUsuario() {

		List<Notificacao> notificacoesRetorno = new ArrayList<Notificacao>(); 

		Mockito.when(notificacaoRepository.findNotificacaoGrupoByUserId(Mockito.anyInt())).thenReturn(notificacoesRetorno);

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		Mockito.when(notificacaoFactory.getNotificacaoTO(Mockito.anyList(), Mockito.anyList(), Mockito.anyList())).thenReturn(new NotificacaoTO());

		NotificacaoTO notificacaoTo = notificacaoService.procurarNotificacaoUsuario(100);

		assertEquals(true, notificacaoTo.getNotificacaoConvidado() == null);
		assertEquals(true, notificacaoTo.getNotificacoesGrupo() == null);

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

		doNothing().when(notificacaoRepository).deleteNotificacaoGrupo(Mockito.anyInt(), Mockito.anyInt());

		Mockito.when(grupoRepository.findByCod(Mockito.anyInt())).thenReturn(new Grupo());

		notificacaoService.deletarNotificacaoGrupo(1,100);

	}

	@Test
	void deletarNotificacaoGrupoNotFound() {

		Mockito.when(notificacaoRepository.findByCodNotificacao(Mockito.anyInt())).thenReturn(notificacaoTest());

		doNothing().when(notificacaoRepository).deleteNotificacaoGrupo(Mockito.anyInt(), Mockito.anyInt());

		Mockito.when(grupoRepository.findByCod(Mockito.anyInt())).thenReturn(null);

		boolean erro = false;
		String mensagemErro = null;
		try {
			notificacaoService.deletarNotificacaoGrupo(1,100);
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

		doNothing().when(notificacaoRepository).insertNotificacaoGrupo(Mockito.anyInt(), Mockito.anyInt());

		Mockito.when(grupoRepository.findByCod(Mockito.anyInt())).thenReturn(new Grupo());

		notificacaoService.inserirNotificacaoGrupo(1,100);

	}

	@Test
	void inserirNotificacaoConvidado() {
		
		Convidado convidado = new Convidado();
		convidado.setEmail("teste@teste.com");

		Mockito.when(notificacaoRepository.findByCodNotificacao(Mockito.anyInt())).thenReturn(new Notificacao());

		Mockito.when(convidadoRepository.findByIdConvidado(Mockito.anyInt())).thenReturn(convidado);

		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuarioTest());

		doNothing().when(notificacaoConvidadoRepository).insertConvidadoNotificacao(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());

		notificacaoService.inserirNotificacaoConvidado(1,100, "teste");

	}

	@Test
	void alterarStatusNotificacaoUsuarioTest() {

		Mockito.when(notificacaoUsuarioRepository.getNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(notificacaoUsuarioTest());

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		Mockito.when(notificacaoUsuarioRepository.save(Mockito.any())).thenReturn(null);

		notificacaoService.alterarStatus(1,1);

	}

	@Test
	void deleteNotificacaoUsuarioTest() {

		Mockito.when(notificacaoUsuarioRepository.getNotificacaoUsuarioByMensagem(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString())).thenReturn(notificacaoUsuarioTest());

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		doNothing().when(notificacaoUsuarioRepository).delete(Mockito.any());

		notificacaoService.deleteNotificacao(1,1,"teste");

	}

	@Test
	void destaqueNotificacaoUsuarioTest() {

		Mockito.when(notificacaoUsuarioRepository.getNotificacaoUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(notificacaoUsuarioTest());

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		Mockito.when(notificacaoUsuarioRepository.save(Mockito.any())).thenReturn(null);

		notificacaoService.destaqueNotificacao(1,1);

	}
	
	@Test
	public void verificarNotificacaoGrupoSucessoTest() {
		Grupo grupo = grupoTest();
		Notificacao notificacao = notificacaoTest();
		NotificacaoGrupo notificacaoGrupo = new NotificacaoGrupo();
		notificacaoGrupo.setGrupo(grupo);
		notificacaoGrupo.setNotificacao(notificacao);
		int codGrupo = grupo.getCodGrupo();
		int codNotificacao = notificacao.getCodNotificacao();
		
		Mockito.when(grupoRepository.findByCod(codGrupo)).thenReturn(grupo);
		Mockito.when(notificacaoRepository.findByCodNotificacao(codNotificacao)).thenReturn(notificacao);
		Mockito.when(notificacaoGrupoRepository.findNotificacaoGrupo(codGrupo, codNotificacao)).thenReturn(notificacaoGrupo);
	
		boolean retorno = notificacaoService.verificarNotificacaoGrupo(codGrupo, codNotificacao);

		assertEquals(retorno, true);
	}

	@Test
	public void verificarNotificacaoGrupoFalhaTest() {
		Grupo grupo = grupoTest();
		Notificacao notificacao = notificacaoTest();
		int codGrupo = grupo.getCodGrupo();
		int codNotificacao = notificacao.getCodNotificacao();
		
		Mockito.when(grupoRepository.findByCod(codGrupo)).thenReturn(grupo);
		Mockito.when(notificacaoRepository.findByCodNotificacao(codNotificacao)).thenReturn(notificacao);
		Mockito.when(notificacaoGrupoRepository.findNotificacaoGrupo(codGrupo, codNotificacao)).thenReturn(null);
	
		boolean retorno = notificacaoService.verificarNotificacaoGrupo(codGrupo, codNotificacao);

		assertEquals(retorno, false);
	}

}
