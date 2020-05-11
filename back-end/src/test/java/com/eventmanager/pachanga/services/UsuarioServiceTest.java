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
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.services.UsuarioService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=UsuarioService.class)
public class UsuarioServiceTest {

	@MockBean
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService userService;
	
	@SuppressWarnings("deprecation")
	public Usuario usuarioTest() throws Exception{
		Usuario usuarioTest = new Usuario();
		
		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("1234");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setSexo("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");
		usuarioTest.setTipConta("P");
		
		return usuarioTest;
	}

	@Test
	public void loginSucessoTest() throws Exception{

		Usuario usuarioTestBanco = usuarioTest();
		usuarioTestBanco.setSenha("40a5791d6b15b858e233d889dce30778655d9e2b1a9e98db15f3af00d035368882f0aa002da9f2868f62b0d35adebca9d2362d8bbfaef2d2e68f2c51fdee3ea52b44902b824bc7e158a955f0a98f871b");

		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "P")).thenReturn(usuarioTestBanco);
		
		Usuario usuarioTestLogin = usuarioTest();
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
		
		Usuario usuarioTestLogin = usuarioTest();
		usuarioTestLogin.setSenha("12345");
		
		Usuario usuarioRetorno;
		try {
			usuarioRetorno = userService.logar(usuarioTestLogin);
		}catch(ValidacaoException e){
			usuarioRetorno = null;
		};
       
		assertEquals(usuarioRetorno == null, true); 
	}
	
	@Test
	public void loginUserTypeNotPSucessTest() throws Exception{

		Usuario usuarioTestBanco = usuarioTest();
		usuarioTestBanco.setTipConta("G");
		
		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "G")).thenReturn(usuarioTestBanco);
		
		Usuario usuarioTestLogin = usuarioTest();
		usuarioTestLogin.setTipConta("G");
        Usuario usuarioRetorno = userService.logar(usuarioTestLogin);
        
        assertEquals(usuarioTestLogin.getNomeUser(),usuarioRetorno.getNomeUser());
        assertEquals(usuarioTestLogin.getDtNasc(),usuarioRetorno.getDtNasc());
        assertEquals(usuarioTestLogin.getSexo(),usuarioRetorno.getSexo());
        assertEquals(usuarioTestLogin.getTipConta(),usuarioRetorno.getTipConta());
        assertEquals(usuarioTestLogin.getEmail(),usuarioRetorno.getEmail()); 
	}
	
	@Test
	public void loginUserTypeNotPFailTest() throws Exception{

		Usuario usuarioTestBanco = usuarioTest();
		usuarioTestBanco.setTipConta("G");
		
		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "G")).thenReturn(null);
		
		Usuario usuarioTestLogin = usuarioTest();
		usuarioTestLogin.setTipConta("G");
		Usuario usuarioRetorno;
		boolean caiuException = false;
		try {
			usuarioRetorno = userService.logar(usuarioTestLogin);
		}catch(ValidacaoException e){
			usuarioRetorno = null;
			caiuException = true;
		};
       
		assertEquals(usuarioRetorno == null, true); 
		assertEquals(caiuException == true, true); 
	}
	
	@Test
	public void loginEmailNaoCadastradoTest() throws Exception{
		
		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "P")).thenReturn(null);
		
		Usuario usuarioTestLogin = usuarioTest();
		
		Usuario usuarioRetorno;
		boolean caiuException = false;
		try {
			usuarioRetorno = userService.logar(usuarioTestLogin);
		}catch(ValidacaoException e){
			usuarioRetorno = null;
			caiuException = true;
		};
       
		assertEquals(usuarioRetorno == null, true); 
		assertEquals(caiuException == true, true); 
	}
	
	@Test
	public void CadastroSucessoTest() throws Exception{
		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "P")).thenReturn(null);
		
		Usuario usuarioTestCadastro = usuarioTest();
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
		
		Usuario usuarioTestCadastro = usuarioTest();
		
		Usuario usuarioRetorno;
		boolean caiuException = false;
		try {
				usuarioRetorno = userService.cadastrar(usuarioTestCadastro);
				if(usuarioRetorno == null) {
					caiuException = true;
				}
		}catch(ValidacaoException e){
			usuarioRetorno = null;
			caiuException = true;
		};
	
		assertEquals(usuarioRetorno == null, true); 
		assertEquals(caiuException == true, true); 
   
	}
	
	@Test
	public void CadastroUserTypeNotPSucessTest() throws Exception{
		Mockito.when(usuarioRepository.findByEmailAndTipConta("gustavinhoTPD@fodasse.com.br", "G")).thenReturn(null);
		
		Usuario usuarioTestCadastro = usuarioTest();
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
		
		Usuario usuarioTestCadastro = usuarioTest();
		
		Usuario usuarioRetorno;
		boolean caiuException = false;
		try {
				usuarioRetorno = userService.cadastrar(usuarioTestCadastro);
				if(usuarioRetorno == null) {
					caiuException = true;
				}
		}catch(ValidacaoException e){
			usuarioRetorno = null;
			caiuException = true;
		};
	
		assertEquals(usuarioRetorno == null, true); 
		assertEquals(caiuException == true, true); 
   
	}
	

}