package com.eventmanager.pachanga.services;

import static org.junit.Assert.assertEquals;

import java.util.Date;

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
import com.eventmanager.pachanga.repositories.UsuarioRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value=UsuarioService.class)
public class UsuarioServiceTest {

	@MockBean
	private UsuarioRepository usuarioRepository;

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

		Usuario usuarioRetorno;
		boolean caiuException = false;
		try {
			usuarioRetorno = userService.logar(usuarioTestLogin);
		}catch(ValidacaoException e){
			usuarioRetorno = null;
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
	public void CadastroSucessoTest() throws Exception{
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
	public void CadastroEmailDuplicadoTest() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "P")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestCadastro = usuarioToTest();

		Usuario usuarioRetorno;
		boolean caiuException = false;
		try {
			usuarioRetorno = userService.cadastrar(usuarioTestCadastro);
		}catch(ValidacaoException e){
			usuarioRetorno = null;
			caiuException = true;
		};

		assertEquals(true, usuarioRetorno == null); 
		assertEquals(true, caiuException == true); 

	}

	@Test
	public void CadastroUserTypeNotPSucessTest() throws Exception{
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
	public void CadastroTest() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "P")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestCadastro = usuarioToTest();

		Usuario usuarioRetorno;
		boolean caiuException = false;
		try {
			usuarioRetorno = userService.cadastrar(usuarioTestCadastro);
		}catch(ValidacaoException e){
			usuarioRetorno = null;
			caiuException = true;
		};

		assertEquals(true, usuarioRetorno == null); 
		assertEquals(true, caiuException == true); 

	}

	//atualizar______________________________________________________________________________________________________________	

	@SuppressWarnings("deprecation")
	@Test
	public void AtualizaTestException() throws Exception{ //Atualiza as senhas, mas as senhas são identicas então da erro
		Usuario usuarioTestBanco = usuarioTest();

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "P")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestAtualizacao= usuarioToTest();
		usuarioTestAtualizacao.setDtNasc(new Date(2002, 6, 27));
		usuarioTestAtualizacao.setSexo("F");

		Usuario usuarioRetorno;
		boolean caiuException = false;
		try {
			usuarioRetorno = userService.atualizar(usuarioTestAtualizacao);
		}catch(ValidacaoException e){
			usuarioRetorno = null;
			caiuException = true;
		};

		assertEquals(true, usuarioRetorno == null); 
		assertEquals(true, caiuException == true); 

	}

	@SuppressWarnings("deprecation")
	@Test
	public void AtualizaTest() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "P")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestAtualizacao= usuarioToTest();
		usuarioTestAtualizacao.setDtNasc(new Date(2002, 6, 27));
		usuarioTestAtualizacao.setSexo("F");
		usuarioTestAtualizacao.setSenha("12345");

		Usuario usuarioRetorno;
		boolean caiuException = false;
		usuarioRetorno = userService.atualizar(usuarioTestAtualizacao);

		assertEquals(false, usuarioRetorno == null); 
		assertEquals(false, caiuException == true); 

	}

	@SuppressWarnings("deprecation")
	@Test
	public void AtualizaUserTypeNotPTest() throws Exception{
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
	public void AtualizaUserNotExistTest() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();
		usuarioTestBanco.setTipConta("G");

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "G")).thenReturn(null);

		UsuarioTO usuarioTestAtualizacao= usuarioToTest();
		usuarioTestAtualizacao.setTipConta("G");
		usuarioTestAtualizacao.setDtNasc(new Date(2002, 6, 27));
		usuarioTestAtualizacao.setSexo("F");

		Usuario usuarioRetorno;
		boolean caiuException = false;
		try {
			usuarioRetorno = userService.atualizar(usuarioTestAtualizacao);

		}catch(ValidacaoException e){
			usuarioRetorno = null;
			caiuException = true;
		};

		assertEquals(true, usuarioRetorno == null); 
		assertEquals(true, caiuException == true); 

	}

	@Test
	public void AtualizaEmailTest() throws Exception{
		Usuario usuarioTestBanco = usuarioTest();
		usuarioTestBanco.setTipConta("P");

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "P")).thenReturn(usuarioTestBanco);

		UsuarioTO usuarioTestAtualizacao= usuarioToTest();
		usuarioTestAtualizacao.setSenha(null);
		usuarioTestAtualizacao.setTipConta("P");
		usuarioTestAtualizacao.setEmailNovo("gustavinhoTPD1@fodasse.com.br");

		Usuario usuarioRetorno;
		usuarioRetorno = userService.atualizar(usuarioTestAtualizacao);

		assertEquals(true, usuarioRetorno != null); 

	}

}
