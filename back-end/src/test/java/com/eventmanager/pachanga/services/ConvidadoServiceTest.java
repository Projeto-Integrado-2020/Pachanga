package com.eventmanager.pachanga.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.repositories.ConvidadoRepository;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;
import com.eventmanager.pachanga.utils.EmailMensagem;

@RunWith(SpringRunner.class)
@WebMvcTest(value=ConvidadoService.class)
class ConvidadoServiceTest {

	@MockBean
	private UsuarioRepository usuarioRepository;

	@MockBean
	private GrupoRepository grupoRepository;

	@MockBean
	private ConvidadoRepository convidadoRepository;

	@MockBean
	private FestaRepository festaRepository;

	@Autowired
	private ConvidadoService convidadoService;

	@MockBean
	private EmailMensagem emailMensagem;

	@MockBean
	private NotificacaoService notificacaoService;

	public Grupo criacaoGrupo() {
		Grupo grupo = new Grupo();
		grupo.setCodGrupo(1);
		grupo.setNomeGrupo("CONVIDADO");
		grupo.setOrganizador(false);
		return grupo;
	}

	private Usuario criacaoUsuario() {
		Usuario usuario = new Usuario();
		usuario.setCodUsuario(1);
		usuario.setEmail("guga.72@hotmail.com");
		return usuario;
	}

	private Festa criacaoFesta() {
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
	void AddUserFestaTest() {
		List<String> emails = new ArrayList<String>(); 
		emails.add("guga.72@hotmail.com");

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(criacaoUsuario());
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(criacaoGrupo());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupo());
		Mockito.when(usuarioRepository.findByEmail("guga.72@hotmail.com")).thenReturn(criacaoUsuario());

		StringBuilder retorno = convidadoService.addUsuariosFesta(emails, 14, 1, 13);

		assertEquals(0, retorno.length());
	}

	@Test
	void AddUserFestaEmailTest() {
		List<String> emails = new ArrayList<String>(); 
		emails.add("guga.72@hotmail.com");

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(criacaoUsuario());
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(criacaoGrupo());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupo());
		Mockito.when(usuarioRepository.findByEmail("guga.72@hotmail.com")).thenReturn(null);

		StringBuilder retorno = convidadoService.addUsuariosFesta(emails, 14, 1, 13);

		assertEquals("guga.72@hotmail.com ", retorno.toString());
	}

	@Test
	void AddUserFestaEmailGrupoOrganizadorTest() {
		List<String> emails = new ArrayList<String>(); 
		emails.add("guga.72@hotmail.com");

		Grupo grupo = criacaoGrupo();
		grupo.setOrganizador(true);

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(criacaoUsuario());
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupo());
		Mockito.when(usuarioRepository.findByEmail("guga.72@hotmail.com")).thenReturn(null);

		boolean erro = false;
		String msgErro = "";
		try {	
			convidadoService.addUsuariosFesta(emails, 14, 1, 13);
		} catch (ValidacaoException e) {
			erro = true;
			msgErro = e.getMessage();

		}

		assertEquals(true, erro);
		assertEquals("GRUPORGN", msgErro);
	}
	
	@Test
	void AddUserFestaEmailFestaNaoEmPreparacaoTest() {
		List<String> emails = new ArrayList<String>(); 
		emails.add("guga.72@hotmail.com");

		Festa festa = criacaoFesta();
		festa.setStatusFesta(TipoStatusFesta.INICIADO.getValor());

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(criacaoUsuario());
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festa);
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(criacaoGrupo());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupo());
		Mockito.when(usuarioRepository.findByEmail("guga.72@hotmail.com")).thenReturn(null);

		boolean erro = false;
		String msgErro = "";
		try {	
			convidadoService.addUsuariosFesta(emails, 14, 1, 13);
		} catch (ValidacaoException e) {
			erro = true;
			msgErro = e.getMessage();

		}

		assertEquals(true, erro);
		assertEquals("FESTNPRE", msgErro);
	}

	@Test
	void AddUserFestaEmailGrupoNulloTest() { // para quando o id do grupo passado retorna null
		List<String> emails = new ArrayList<String>(); 
		emails.add("guga.72@hotmail.com");

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(criacaoUsuario());
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(null);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupo());
		Mockito.when(usuarioRepository.findByEmail("guga.72@hotmail.com")).thenReturn(null);

		boolean erro = false;
		String msgErro = "";
		try {			
			convidadoService.addUsuariosFesta(emails, 14, 1, 13);
		} catch (ValidacaoException e) {
			erro = true;
			msgErro = e.getMessage();

		}

		assertEquals(true, erro);
		assertEquals("GRUPNFOU", msgErro);
	}

	@Test
	void AddUserFestaEmailGrupoPermissaoNulloTest() { // para quando o cara nn tiver permissão para fazer isso
		List<String> emails = new ArrayList<String>(); 
		emails.add("guga.72@hotmail.com");

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(criacaoUsuario());
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(criacaoGrupo());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(usuarioRepository.findByEmail("guga.72@hotmail.com")).thenReturn(null);

		boolean erro = false;
		String msgErro = "";
		try {			
			convidadoService.addUsuariosFesta(emails, 14, 1, 13);
		} catch (ValidacaoException e) {
			erro = true;
			msgErro = e.getMessage();
		}

		assertEquals(true, erro);
		assertEquals("USESPERM", msgErro);
	}

	@Test
	void AddUserFestaEmailFestaNulloTest() { // para quando a festa não existir
		List<String> emails = new ArrayList<String>(); 
		emails.add("guga.72@hotmail.com");

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(criacaoUsuario());
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(null);
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(criacaoGrupo());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupo());
		Mockito.when(usuarioRepository.findByEmail("guga.72@hotmail.com")).thenReturn(null);

		boolean erro = false;
		String msgErro = "";
		try {			
			convidadoService.addUsuariosFesta(emails, 14, 1, 13);
		} catch (ValidacaoException e) {
			erro = true;
			msgErro = e.getMessage();
		}

		assertEquals(true, erro);
		assertEquals("FESTNFOU", msgErro);
	}

	@Test
	void AddUserFestaEmailUsuarioNulloTest() { // para quando o usuário não existir no banco de dados
		List<String> emails = new ArrayList<String>(); 
		emails.add("guga.72@hotmail.com");

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(null);
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(criacaoGrupo());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupo());
		Mockito.when(usuarioRepository.findByEmail("guga.72@hotmail.com")).thenReturn(null);

		boolean erro = false;
		String msgErro = "";
		try {			
			convidadoService.addUsuariosFesta(emails, 14, 1, 13);
		} catch (ValidacaoException e) {
			erro = true;
			msgErro = e.getMessage();
		}

		assertEquals(true, erro);
		assertEquals("USERNFOU", msgErro);
	}

}