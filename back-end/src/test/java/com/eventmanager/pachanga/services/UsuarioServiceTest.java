package com.eventmanager.pachanga.services;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.PachangaApplication;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.UsuarioFestaTO;
import com.eventmanager.pachanga.dtos.UsuarioTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.UsuarioFestaTOFactory;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.tipo.TipoConta;
import com.eventmanager.pachanga.tipo.TipoGrupo;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = PachangaApplication.class)
@AutoConfigureMockMvc
class UsuarioServiceTest {

	@MockBean
	private UsuarioRepository usuarioRepository;

	@MockBean
	private FestaService festaService;

	@MockBean
	private GrupoRepository grupoRepository;

	@MockBean
	private UsuarioFestaTOFactory usuarioFestaTOFactory;

	@Autowired
	private UsuarioService userService;
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;


	@SuppressWarnings("deprecation")
	public static UsuarioTO usuarioToTest() throws Exception{
		UsuarioTO usuarioTest = new UsuarioTO();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("1234");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setGenero("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");
		usuarioTest.setTipConta("P");

		return usuarioTest;
	}

	public Grupo criacaoGrupo() {
		Grupo grupo = new Grupo();
		grupo.setCodGrupo(1);
		grupo.setNomeGrupo("CONVIDADO");
		return grupo;
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

	//login______________________________________________________________________________________________________________	

	@Test
	void loginSucessoTest() throws Exception{

		Usuario usuarioTestBanco = usuarioTest();
		usuarioTestBanco.setSenha("40a5791d6b15b858e233d889dce30778655d9e2b1a9e98db15f3af00d035368882f0aa002da9f2868f62b0d35adebca9d2362d8bbfaef2d2e68f2c51fdee3ea52b44902b824bc7e158a955f0a98f871b");

		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestLogin = usuarioToTest();
		Usuario usuarioRetorno = userService.logar(usuarioTestLogin);

		assertEquals(usuarioTestLogin.getNomeUser(),usuarioRetorno.getNomeUser());
		assertEquals(usuarioTestLogin.getDtNasc(),usuarioRetorno.getDtNasc());
		assertEquals(usuarioTestLogin.getGenero(),usuarioRetorno.getGenero());
		assertEquals(usuarioTestLogin.getEmail(),usuarioRetorno.getEmail()); 
	}

	@Test
	void loginSemSenhaBancoTest() throws Exception{

		Usuario usuarioTestBanco = usuarioTest();
		usuarioTestBanco.setSenha(null);

		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestLogin = usuarioToTest();
		boolean erro = false;
		String mensagemErro = null;
		try {
			userService.logar(usuarioTestLogin);			
		} catch (ValidacaoException e) {
			mensagemErro = e.getMessage();
			erro = true;
		}

		assertEquals(true, erro);
		assertEquals("PASSINC1", mensagemErro);
	}

	@Test
	void loginErroContaGmailTest() throws Exception{

		Usuario usuarioTestBanco = usuarioTest();
		usuarioTestBanco.setGmail("123");

		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestLogin = usuarioToTest();
		usuarioTestLogin.setTipConta(TipoConta.GMAIL.getDescricao());
		usuarioTestLogin.setConta("1234");

		boolean erro = false;
		try {
			userService.logar(usuarioTestLogin);			
		} catch (ValidacaoException e) {
			erro = true;
		}

		assertEquals(true, erro);
	}

	@Test
	void loginErroContaFacebookTest() throws Exception{

		Usuario usuarioTestBanco = usuarioTest();
		usuarioTestBanco.setFacebook("123");

		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestLogin = usuarioToTest();
		usuarioTestLogin.setTipConta(TipoConta.FACEBOOK.getDescricao());
		usuarioTestLogin.setConta("1234");

		boolean erro = false;
		try {
			userService.logar(usuarioTestLogin);			
		} catch (ValidacaoException e) {
			erro = true;
		}

		assertEquals(true, erro);
	}

	@Test
	void loginSenhaErradaTest() throws Exception{

		Usuario usuarioTestBanco = usuarioTest();
		usuarioTestBanco.setSenha("40a5791d6b15b858e233d889dce30778655d9e2b1a9e98db15f3af00d035368882f0aa002da9f2868f62b0d35adebca9d2362d8bbfaef2d2e68f2c51fdee3ea52b44902b824bc7e158a955f0a98f871b");

		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestLogin = usuarioToTest();
		usuarioTestLogin.setSenha("12345");

		Usuario usuarioRetorno;
		try {
			usuarioRetorno = userService.logar(usuarioTestLogin);
		}catch(ValidacaoException e){
			usuarioRetorno = null;
		};

		assertEquals(true, usuarioRetorno == null); 
	}

	@Test
	void loginUserTypeGSucessTest() throws Exception{

		Usuario usuarioTestBanco = usuarioTest();

		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestLogin = usuarioToTest();
		usuarioTestLogin.setTipConta("G");
		Usuario usuarioRetorno = userService.logar(usuarioTestLogin);

		assertEquals(usuarioTestLogin.getNomeUser(),usuarioRetorno.getNomeUser());
		assertEquals(usuarioTestLogin.getDtNasc(),usuarioRetorno.getDtNasc());
		assertEquals(usuarioTestLogin.getGenero(),usuarioRetorno.getGenero());
		assertEquals(usuarioTestLogin.getEmail(),usuarioRetorno.getEmail()); 
	}

	@Test
	void loginUserTypeFSucessTest() throws Exception{

		Usuario usuarioTestBanco = usuarioTest();

		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestLogin = usuarioToTest();
		usuarioTestLogin.setTipConta("F");
		Usuario usuarioRetorno = userService.logar(usuarioTestLogin);

		assertEquals(usuarioTestLogin.getNomeUser(),usuarioRetorno.getNomeUser());
		assertEquals(usuarioTestLogin.getDtNasc(),usuarioRetorno.getDtNasc());
		assertEquals(usuarioTestLogin.getGenero(),usuarioRetorno.getGenero());
		assertEquals(usuarioTestLogin.getEmail(),usuarioRetorno.getEmail()); 
	}

	@Test
	void loginUserTypeNotPFailTest() throws Exception{

		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(usuarioTest());

		UsuarioTO usuarioTestLogin = usuarioToTest();
		usuarioTestLogin.setTipConta("G");
		Usuario usuarioRetorno;
		usuarioRetorno = userService.logar(usuarioTestLogin);

		assertEquals(true, usuarioRetorno != null); 
	}

	@Test
	void loginEmailNaoCadastradoTest() throws Exception{

		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(null);

		UsuarioTO usuarioTestLogin = usuarioToTest();

		Usuario usuarioRetorno = null;
		boolean caiuException = false;
		try {
			usuarioRetorno = userService.logar(usuarioTestLogin);
		}catch(ValidacaoException e){
			caiuException = true;
		};

		assertEquals(true, usuarioRetorno == null); 
		assertEquals(true, caiuException == true); 
	}

	@Test
	void loginCadastroTest() throws Exception{

		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(null);

		UsuarioTO usuarioTestLogin = usuarioToTest();
		usuarioTestLogin.setTipConta(TipoConta.GMAIL.getDescricao());
		usuarioTestLogin.setConta("1234");

		Usuario usuarioRetorno = null;
		boolean caiuException = false;

		usuarioRetorno = userService.logar(usuarioTestLogin);

		assertEquals(true, usuarioRetorno != null); 
		assertEquals(true, caiuException != true); 

	}


	//cadastro______________________________________________________________________________________________________________	

	@Test
	void cadastroErroTest() throws Exception{
		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(usuarioTest());

		UsuarioTO usuarioTestCadastro = usuarioToTest();

		Usuario usuarioRetorno = null;
		boolean caiuException = false;

		try {
			userService.cadastrar(usuarioTestCadastro);
		} catch (Exception e) {
			caiuException = true;
		}

		assertEquals(true, usuarioRetorno == null); 
		assertEquals(true, caiuException == true); 
	}

	@Test
	void cadastroSemSenhaTest() throws Exception{
		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuarioTest());

		UsuarioTO usuarioTestCadastro = usuarioToTest();

		usuarioTestCadastro.setSenha(null);

		Usuario usuarioRetorno = null;
		boolean caiuException = false;

		try {
			userService.cadastrar(usuarioTestCadastro);
		} catch (Exception e) {
			caiuException = true;
		}

		assertEquals(true, usuarioRetorno == null); 
		assertEquals(true, caiuException == true); 
	}

	@Test
	void cadastroValorGmailDiferenteTest() throws Exception{
		Usuario usuario = usuarioTest();
		usuario.setGmail("1234");

		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuario);

		UsuarioTO usuarioTestCadastro = usuarioToTest();
		usuarioTestCadastro.setTipConta(TipoConta.GMAIL.getDescricao());
		usuarioTestCadastro.setConta("123");

		Usuario usuarioRetorno = null;
		boolean caiuException = false;

		try {
			userService.cadastrar(usuarioTestCadastro);
		} catch (Exception e) {
			caiuException = true;
		}

		assertEquals(true, usuarioRetorno == null); 
		assertEquals(true, caiuException == true); 
	}

	@Test
	void cadastroValorFacebookDiferenteTest() throws Exception{
		Usuario usuario = usuarioTest();
		usuario.setFacebook("1234");

		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuario);

		UsuarioTO usuarioTestCadastro = usuarioToTest();
		usuarioTestCadastro.setTipConta(TipoConta.FACEBOOK.getDescricao());
		usuarioTestCadastro.setConta("123");

		Usuario usuarioRetorno = null;
		boolean caiuException = false;

		try {
			userService.cadastrar(usuarioTestCadastro);
		} catch (Exception e) {
			caiuException = true;
		}

		assertEquals(true, usuarioRetorno == null); 
		assertEquals(true, caiuException == true); 
	}

	@Test
	void cadastroFacebookTest() throws Exception{
		Usuario usuario = usuarioTest();
		usuario.setFacebook("1234");

		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuario, usuario);

		UsuarioTO usuarioTestCadastro = usuarioToTest();
		usuarioTestCadastro.setTipConta(TipoConta.FACEBOOK.getDescricao());
		usuarioTestCadastro.setConta("1234");

		Usuario usuarioRetorno = null;
		boolean caiuException = false;

		usuarioRetorno = userService.cadastrar(usuarioTestCadastro);

		assertEquals(true, usuarioRetorno != null); 
		assertEquals(true, caiuException != true); 
	}

	@Test
	void cadastroGmailTest() throws Exception{
		Usuario usuario = usuarioTest();
		usuario.setGmail("1234");

		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuario, usuario);

		UsuarioTO usuarioTestCadastro = usuarioToTest();
		usuarioTestCadastro.setTipConta(TipoConta.GMAIL.getDescricao());
		usuarioTestCadastro.setConta("1234");

		Usuario usuarioRetorno = null;
		boolean caiuException = false;

		usuarioRetorno = userService.cadastrar(usuarioTestCadastro);

		assertEquals(true, usuarioRetorno != null); 
		assertEquals(true, caiuException != true); 
	}

	@Test
	void cadastroCorretoPachangaTest() throws Exception{
		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(null);

		UsuarioTO usuarioTestCadastro = usuarioToTest();

		Usuario usuarioRetorno = null;
		boolean caiuException = false;

		usuarioRetorno = userService.cadastrar(usuarioTestCadastro);

		assertEquals(true, usuarioRetorno != null); 
		assertEquals(true, caiuException != true); 
	}

	@Test
	void cadastroCorretoGmailTest() throws Exception{
		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(null);

		UsuarioTO usuarioTestCadastro = usuarioToTest();
		usuarioTestCadastro.setTipConta(TipoConta.GMAIL.getDescricao());
		usuarioTestCadastro.setConta("1234");

		Usuario usuarioRetorno = null;
		boolean caiuException = false;

		usuarioRetorno = userService.cadastrar(usuarioTestCadastro);

		assertEquals(true, usuarioRetorno != null); 
		assertEquals(true, caiuException != true); 
	}

	@Test
	void cadastroCorretoFacebookTest() throws Exception{
		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(null);

		UsuarioTO usuarioTestCadastro = usuarioToTest();
		usuarioTestCadastro.setTipConta(TipoConta.FACEBOOK.getDescricao());
		usuarioTestCadastro.setConta("1234");

		Usuario usuarioRetorno = null;
		boolean caiuException = false;

		usuarioRetorno = userService.cadastrar(usuarioTestCadastro);

		assertEquals(true, usuarioRetorno != null); 
		assertEquals(true, caiuException != true); 
	}


	@Test
	void cadastroCorretoPachangaUsuarioExistenteTest() throws Exception{
		Usuario usuario = usuarioTest();
		usuario.setFacebook("1234");
		usuario.setSenha(null);
		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(usuario);

		UsuarioTO usuarioTestCadastro = usuarioToTest();
		usuarioTestCadastro.setSenha("1234");

		Usuario usuarioRetorno = null;
		boolean caiuException = false;

		usuarioRetorno = userService.cadastrar(usuarioTestCadastro);

		assertEquals(true, usuarioRetorno != null); 
		assertEquals(true, caiuException != true); 
	}

	@Test
	void cadastroEmailDuplicadoTest() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();

		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestCadastro = usuarioToTest();

		Usuario usuarioRetorno = null;
		boolean caiuException = false;
		try {
			usuarioRetorno = userService.cadastrar(usuarioTestCadastro);
		}catch(ValidacaoException e){
			caiuException = true;
		};

		assertEquals(true, usuarioRetorno == null); 
		assertEquals(true, caiuException == true); 

	}

	@Test
	void cadastroUserGmail() throws Exception{
		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(usuarioTest());

		UsuarioTO usuarioTestCadastro = usuarioToTest();
		usuarioTestCadastro.setTipConta("G");

		boolean erro = false;
		String mensagemErro = null;

		try {
			userService.cadastrar(usuarioTestCadastro);			
		} catch (Exception e) {
			erro = true;
			mensagemErro = e.getMessage();
		}

		assertEquals(true, erro);
		assertEquals("USERNCON", mensagemErro);
	}

	@Test
	void cadastroTest() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();

		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestCadastro = usuarioToTest();

		Usuario usuarioRetorno = null;
		boolean caiuException = false;
		try {
			usuarioRetorno = userService.cadastrar(usuarioTestCadastro);
		}catch(ValidacaoException e){
			caiuException = true;
		};

		assertEquals(true, usuarioRetorno == null); 
		assertEquals(true, caiuException == true); 

	}

	//atualizar______________________________________________________________________________________________________________	

	@SuppressWarnings("deprecation")
	@Test
	void atualizaTestException() throws Exception{ //Atualiza as senhas, mas as senhas são identicas então da erro
		Usuario usuarioTestBanco = usuarioTest();

		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestAtualizacao= usuarioToTest();
		usuarioTestAtualizacao.setDtNasc(new Date(2002, 6, 27));
		usuarioTestAtualizacao.setGenero("F");
		usuarioTestAtualizacao.setSenhaNova("1234");

		Usuario usuarioRetorno = null;
		boolean caiuException = false;
		try {
			usuarioRetorno = userService.atualizar(usuarioTestAtualizacao);
		}catch(ValidacaoException e){
			caiuException = true;
		};

		assertEquals(true, usuarioRetorno == null); 
		assertEquals(true, caiuException == true); 

	}

	@SuppressWarnings("deprecation")
	@Test
	void atualizaTest() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();

		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestAtualizacao= usuarioToTest();
		usuarioTestAtualizacao.setDtNasc(new Date(2002, 6, 27));
		usuarioTestAtualizacao.setGenero("F");
		usuarioTestAtualizacao.setSenha("1234");

		Usuario usuarioRetorno;
		boolean caiuException = false;
		usuarioRetorno = userService.atualizar(usuarioTestAtualizacao);

		assertEquals(false, usuarioRetorno == null); 
		assertEquals(false, caiuException == true); 

	}

	@SuppressWarnings("deprecation")
	@Test
	void atualizaUserTypeNotPTest() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();

		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestAtualizacao= usuarioToTest();
		usuarioTestAtualizacao.setTipConta("G");
		usuarioTestAtualizacao.setDtNasc(new Date(2002, 6, 27));
		usuarioTestAtualizacao.setGenero("F");

		Usuario usuarioRetorno;
		boolean caiuException = false;
		usuarioRetorno = userService.atualizar(usuarioTestAtualizacao);

		assertEquals(false, usuarioRetorno == null); 
		assertEquals(false, caiuException == true); 

	}

	@SuppressWarnings("deprecation")
	@Test
	void atualizaUserNotExistTest() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();

		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(null,usuarioTestBanco);

		UsuarioTO usuarioTestAtualizacao= usuarioToTest();
		usuarioTestAtualizacao.setTipConta("G");
		usuarioTestAtualizacao.setDtNasc(new Date(2002, 6, 27));
		usuarioTestAtualizacao.setGenero("F");

		Usuario usuarioRetorno = null;
		boolean caiuException = false;
		try {
			userService.atualizar(usuarioTestAtualizacao);

		}catch(ValidacaoException e){
			caiuException = true;
		};

		assertEquals(true, usuarioRetorno == null); 
		assertEquals(true, caiuException == true); 

	}

	@Test
	void atualizaEmailTest() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();

		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestAtualizacao= usuarioToTest();
		usuarioTestAtualizacao.setSenha("1234");
		usuarioTestAtualizacao.setTipConta("P");
		usuarioTestAtualizacao.setEmailNovo("gustavinhoTPD1@fodasse.com.br");

		Usuario usuarioRetorno = userService.atualizar(usuarioTestAtualizacao);

		assertEquals(true, usuarioRetorno != null); 

	}

	@Test
	void atualizaSenhaTest() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();

		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestAtualizacao= usuarioToTest();
		usuarioTestAtualizacao.setSenha("1234");
		usuarioTestAtualizacao.setTipConta("P");
		usuarioTestAtualizacao.setEmailNovo("gustavinhoTPD1@fodasse.com.br");
		usuarioTestAtualizacao.setSenhaNova("12345");

		Usuario usuarioRetorno;
		usuarioRetorno = userService.atualizar(usuarioTestAtualizacao);

		assertEquals(true, usuarioRetorno != null); 

	}

	@Test
	void atualizaSenhaTestErro() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();

		Mockito.when(usuarioRepository.findByEmail("gustavinhoTPD@fodasse.com.br")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestAtualizacao= usuarioToTest();
		usuarioTestAtualizacao.setSenha("12345");
		usuarioTestAtualizacao.setTipConta("P");
		usuarioTestAtualizacao.setEmailNovo("gustavinhoTPD1@fodasse.com.br");

		Usuario usuarioRetorno = null;
		boolean caiuException = false;
		try {
			userService.atualizar(usuarioTestAtualizacao);

		}catch(ValidacaoException e){
			caiuException = true;
		};

		assertEquals(true, usuarioRetorno == null); 
		assertEquals(true, caiuException == true); 

	}

	@Test
	void atualizaEmailTestErro() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();
		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(usuarioTestBanco, usuarioTestBanco);

		UsuarioTO usuarioTestAtualizacao= usuarioToTest();
		usuarioTestAtualizacao.setSenha("1234");
		usuarioTestAtualizacao.setTipConta("P");
		usuarioTestAtualizacao.setEmailNovo("gustavinhoTPD1@fodasse.com.br");

		Usuario usuarioRetorno = null;
		boolean caiuException = false;
		try {
			userService.atualizar(usuarioTestAtualizacao);

		}catch(ValidacaoException e){
			caiuException = true;
		};

		assertEquals(true, usuarioRetorno == null); 
		assertEquals(true, caiuException == true); 

	}

	@Test
	void getUsuarioFestaTest() throws Exception {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(usuarioTest());
		Mockito.when(usuarioRepository.findByIdFesta(14)).thenReturn(usuarios);

		List<Usuario> usuariosResposta = userService.getUsuariosFesta(14);

		assertEquals(1, usuariosResposta.size());
	}

	@Test
	void funcionalidadeUsuarioFestaSucessTest() throws Exception{

		Mockito.when(usuarioRepository.findById(1)).thenReturn(usuarioTest());

		Mockito.when(festaService.funcionalidadeFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(TipoGrupo.ORGANIZADOR.getValor());

		String funcionalidade = userService.funcionalidadeUsuarioFesta(14, 1);

		assertEquals(funcionalidade, TipoGrupo.ORGANIZADOR.getValor());

	}

	@Test
	void funcionalidadeUsuarioFestaFailedTest() throws Exception{

		List<String> expected = new ArrayList<>();
		expected.add("Estagiário senior em iluminação");

		Mockito.when(usuarioRepository.findById(1)).thenReturn(null);

		Mockito.when(festaService.funcionalidadeFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(TipoGrupo.ORGANIZADOR.getValor());

		boolean caiuException = false;
		String funcionalidade = null;
		try {
			funcionalidade = userService.funcionalidadeUsuarioFesta(14, 1);
		}catch(ValidacaoException e) {
			caiuException = true;
		}
		assertEquals(true, caiuException == true); 

		assertEquals(true, funcionalidade == null);

	}

	@Test
	void getUsuarioResponsavelFestaTest() throws Exception{
		Mockito.when(usuarioRepository.findByFestaGrupo(Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(usuarioTest());

		Usuario usuarioResposta = userService.getUsuarioResponsavelFesta(14);

		assertEquals(true, usuarioResposta != null);
	}

	@Test
	void getInfoUserFestaSucessTest() throws Exception{
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(criacaoGrupo());

		Mockito.when(usuarioFestaTOFactory.getUsuarioFestaTO(Mockito.any(), Mockito.any())).thenReturn(new UsuarioFestaTO());

		userService.getInfoUserFesta(14, 1);

	}

	@Test
	void getInfoUserFestaErroGrupoNaoEncontradoTest() throws Exception{
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(null);

		Mockito.when(usuarioFestaTOFactory.getUsuarioFestaTO(Mockito.any(), Mockito.any())).thenReturn(new UsuarioFestaTO());

		boolean caiuException = false;
		try {
			userService.getInfoUserFesta(14, 1);
		}catch(ValidacaoException e) {
			caiuException = true;
		}
		assertEquals(true, caiuException == true); 
	}

}
