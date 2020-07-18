package com.eventmanager.pachanga.services;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.UsuarioTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.tipo.TipoGrupo;

@RunWith(SpringRunner.class)
@WebMvcTest(value=UsuarioService.class)
public class UsuarioServiceTest {

	@MockBean
	private UsuarioRepository usuarioRepository;
	
	@MockBean
	private GrupoRepository grupoRepository;

	@Autowired
	private UsuarioService userService;

	@SuppressWarnings("deprecation")
	public UsuarioTO usuarioToTest() throws Exception{
		UsuarioTO usuarioTest = new UsuarioTO();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("1234");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setSexo("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");
		usuarioTest.setTipConta("P");

		return usuarioTest;
	}

	@SuppressWarnings("deprecation")
	public Usuario usuarioTest() throws Exception{
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("fc68b677646b5f018d1762e9a19bf65180d9aab2794794340ade50e0d78a239affd43a613e7136a61b5d63b09f072c0c039dea4281873abe826d6e6285d9cefef0a0d868d3b0b0d4582ec787b473b4e0");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setSexo("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");
		usuarioTest.setTipConta("P");

		return usuarioTest;
	}

	//login______________________________________________________________________________________________________________	

	@Test
	public void loginSucessoTest() throws Exception{

		Usuario usuarioTestBanco = usuarioTest();
		usuarioTestBanco.setSenha("40a5791d6b15b858e233d889dce30778655d9e2b1a9e98db15f3af00d035368882f0aa002da9f2868f62b0d35adebca9d2362d8bbfaef2d2e68f2c51fdee3ea52b44902b824bc7e158a955f0a98f871b");

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "P")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestLogin = usuarioToTest();
		Usuario usuarioRetorno = userService.logar(usuarioTestLogin);

		assertEquals(usuarioTestLogin.getNomeUser(),usuarioRetorno.getNomeUser());
		assertEquals(usuarioTestLogin.getDtNasc(),usuarioRetorno.getDtNasc());
		assertEquals(usuarioTestLogin.getSexo(),usuarioRetorno.getSexo());
		assertEquals(usuarioTestLogin.getTipConta(),usuarioRetorno.getTipConta());
		assertEquals(usuarioTestLogin.getEmail(),usuarioRetorno.getEmail()); 
	}

	@Test
	public void loginSenhaErradaTest() throws Exception{

		Usuario usuarioTestBanco = usuarioTest();
		usuarioTestBanco.setSenha("40a5791d6b15b858e233d889dce30778655d9e2b1a9e98db15f3af00d035368882f0aa002da9f2868f62b0d35adebca9d2362d8bbfaef2d2e68f2c51fdee3ea52b44902b824bc7e158a955f0a98f871b");

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "P")).thenReturn(usuarioTestBanco);

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
	public void loginUserTypeGSucessTest() throws Exception{

		Usuario usuarioTestBanco = usuarioTest();
		usuarioTestBanco.setTipConta("G");

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "G")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestLogin = usuarioToTest();
		usuarioTestLogin.setTipConta("G");
		Usuario usuarioRetorno = userService.logar(usuarioTestLogin);

		assertEquals(usuarioTestLogin.getNomeUser(),usuarioRetorno.getNomeUser());
		assertEquals(usuarioTestLogin.getDtNasc(),usuarioRetorno.getDtNasc());
		assertEquals(usuarioTestLogin.getSexo(),usuarioRetorno.getSexo());
		assertEquals(usuarioTestLogin.getTipConta(),usuarioRetorno.getTipConta());
		assertEquals(usuarioTestLogin.getEmail(),usuarioRetorno.getEmail()); 
	}

	@Test
	public void loginUserTypeFSucessTest() throws Exception{

		Usuario usuarioTestBanco = usuarioTest();
		usuarioTestBanco.setTipConta("F");

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "F")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestLogin = usuarioToTest();
		usuarioTestLogin.setTipConta("F");
		Usuario usuarioRetorno = userService.logar(usuarioTestLogin);

		assertEquals(usuarioTestLogin.getNomeUser(),usuarioRetorno.getNomeUser());
		assertEquals(usuarioTestLogin.getDtNasc(),usuarioRetorno.getDtNasc());
		assertEquals(usuarioTestLogin.getSexo(),usuarioRetorno.getSexo());
		assertEquals(usuarioTestLogin.getTipConta(),usuarioRetorno.getTipConta());
		assertEquals(usuarioTestLogin.getEmail(),usuarioRetorno.getEmail()); 
	}

	@Test
	public void loginUserTypeNotPFailTest() throws Exception{

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "G")).thenReturn(null);

		UsuarioTO usuarioTestLogin = usuarioToTest();
		usuarioTestLogin.setTipConta("G");
		Usuario usuarioRetorno;
		usuarioRetorno = userService.logar(usuarioTestLogin);

		assertEquals(true, usuarioRetorno != null); 
	}

	@Test
	public void loginEmailNaoCadastradoTest() throws Exception{

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "P")).thenReturn(null);

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
	public void loginPeloCadastro() throws Exception{

		Usuario usuarioTestBanco = usuarioTest();
		usuarioTestBanco.setTipConta("F");

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "F")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestLogin = usuarioToTest();
		usuarioTestLogin.setTipConta("F");

		Usuario usuarioRetorno;

		usuarioRetorno = userService.cadastrar(usuarioTestLogin);

		assertEquals(false, usuarioRetorno == null); 
	}


	//cadastro______________________________________________________________________________________________________________	

	@Test
	public void cadastroSucessoTest() throws Exception{
		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "P")).thenReturn(null);

		UsuarioTO usuarioTestCadastro = usuarioToTest();
		Usuario usuarioRetorno = userService.cadastrar(usuarioTestCadastro);

		assertEquals(usuarioTestCadastro.getNomeUser(),usuarioRetorno.getNomeUser());
		assertEquals(usuarioTestCadastro.getDtNasc(),usuarioRetorno.getDtNasc());
		assertEquals(usuarioTestCadastro.getSexo(),usuarioRetorno.getSexo());
		assertEquals(usuarioTestCadastro.getTipConta(),usuarioRetorno.getTipConta());
		assertEquals(usuarioTestCadastro.getEmail(),usuarioRetorno.getEmail()); 
	}

	@Test
	public void cadastroEmailDuplicadoTest() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "P")).thenReturn(usuarioTestBanco);

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
	public void cadastroUserTypeNotPSucessTest() throws Exception{
		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "G")).thenReturn(null);

		UsuarioTO usuarioTestCadastro = usuarioToTest();
		usuarioTestCadastro.setTipConta("G");
		Usuario usuarioRetorno = userService.cadastrar(usuarioTestCadastro);

		assertEquals(usuarioTestCadastro.getNomeUser(),usuarioRetorno.getNomeUser());
		assertEquals(usuarioTestCadastro.getDtNasc(),usuarioRetorno.getDtNasc());
		assertEquals(usuarioTestCadastro.getSexo(),usuarioRetorno.getSexo());
		assertEquals(usuarioTestCadastro.getTipConta(),usuarioRetorno.getTipConta());
		assertEquals(usuarioTestCadastro.getEmail(),usuarioRetorno.getEmail()); 
		assertEquals(usuarioTestCadastro.getEmail(),usuarioRetorno.getEmail()); 
	}

	@Test
	public void cadastroTest() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "P")).thenReturn(usuarioTestBanco);

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
	public void atualizaTestException() throws Exception{ //Atualiza as senhas, mas as senhas são identicas então da erro
		Usuario usuarioTestBanco = usuarioTest();

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "P")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestAtualizacao= usuarioToTest();
		usuarioTestAtualizacao.setDtNasc(new Date(2002, 6, 27));
		usuarioTestAtualizacao.setSexo("F");
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
	public void atualizaTest() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "P")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestAtualizacao= usuarioToTest();
		usuarioTestAtualizacao.setDtNasc(new Date(2002, 6, 27));
		usuarioTestAtualizacao.setSexo("F");
		usuarioTestAtualizacao.setSenha("1234");

		Usuario usuarioRetorno;
		boolean caiuException = false;
		usuarioRetorno = userService.atualizar(usuarioTestAtualizacao);

		assertEquals(false, usuarioRetorno == null); 
		assertEquals(false, caiuException == true); 

	}

	@SuppressWarnings("deprecation")
	@Test
	public void atualizaUserTypeNotPTest() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();
		usuarioTestBanco.setTipConta("G");

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "G")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestAtualizacao= usuarioToTest();
		usuarioTestAtualizacao.setTipConta("G");
		usuarioTestAtualizacao.setDtNasc(new Date(2002, 6, 27));
		usuarioTestAtualizacao.setSexo("F");

		Usuario usuarioRetorno;
		boolean caiuException = false;
		usuarioRetorno = userService.atualizar(usuarioTestAtualizacao);

		assertEquals(false, usuarioRetorno == null); 
		assertEquals(false, caiuException == true); 

	}

	@SuppressWarnings("deprecation")
	@Test
	public void atualizaUserNotExistTest() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();
		usuarioTestBanco.setTipConta("G");

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "G")).thenReturn(null);

		UsuarioTO usuarioTestAtualizacao= usuarioToTest();
		usuarioTestAtualizacao.setTipConta("G");
		usuarioTestAtualizacao.setDtNasc(new Date(2002, 6, 27));
		usuarioTestAtualizacao.setSexo("F");

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
	public void atualizaEmailTest() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();
		usuarioTestBanco.setTipConta("P");

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "P")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestAtualizacao= usuarioToTest();
		usuarioTestAtualizacao.setSenha("1234");
		usuarioTestAtualizacao.setTipConta("P");
		usuarioTestAtualizacao.setEmailNovo("gustavinhoTPD1@fodasse.com.br");

		 Usuario usuarioRetorno = userService.atualizar(usuarioTestAtualizacao);

		assertEquals(true, usuarioRetorno != null); 

	}
	
	@Test
	public void atualizaSenhaTest() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();
		usuarioTestBanco.setTipConta("P");

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "P")).thenReturn(usuarioTestBanco);

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
	public void atualizaSenhaTestErro() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();
		usuarioTestBanco.setTipConta("P");

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "P")).thenReturn(usuarioTestBanco);

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
	public void atualizaEmailTestErro() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();
		usuarioTestBanco.setTipConta("P");

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "P")).thenReturn(usuarioTestBanco);
		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD1@fodasse.com.br", "P")).thenReturn(usuarioTestBanco);

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
	public void getUsuarioFestaTest() throws Exception {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(usuarioTest());
		Mockito.when(usuarioRepository.findByIdFesta(14)).thenReturn(usuarios);
		
		List<Usuario> usuariosResposta = userService.getUsuariosFesta(14);
		
		assertEquals(1, usuariosResposta.size());
	}
	
	@Test
	public void funcionalidadeUsuarioFestaSucessTest() throws Exception{
		
		Mockito.when(usuarioRepository.findById(1)).thenReturn(usuarioTest());
		
		Mockito.when(grupoRepository.findFuncionalidade(14, 1)).thenReturn(TipoGrupo.ORGANIZADOR.getValor());
		
		String funcionalidade = userService.funcionalidadeUsuarioFesta(14, 1);
		
		assertEquals(funcionalidade, TipoGrupo.ORGANIZADOR.getValor());
		
	}
	
	@Test
	public void funcionalidadeUsuarioFestaFailedTest() throws Exception{
		
		Mockito.when(usuarioRepository.findById(1)).thenReturn(null);
		
		Mockito.when(grupoRepository.findFuncionalidade(14, 1)).thenReturn(TipoGrupo.ORGANIZADOR.getValor());
		
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
	public void getUsuarioResponsavelFestaTest() throws Exception{
		Mockito.when(usuarioRepository.findByFestaGrupo(14, TipoGrupo.ORGANIZADOR.getValor())).thenReturn(usuarioTest());
		
		Usuario usuarioResposta = userService.getUsuarioResponsavelFesta(14);
		
		assertEquals(true, usuarioResposta != null);
	}

}
