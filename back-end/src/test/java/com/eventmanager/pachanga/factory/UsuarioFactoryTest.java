package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.UsuarioTO;
import com.eventmanager.pachanga.tipo.TipoConta;


@RunWith(SpringRunner.class)
@WebMvcTest(value=UsuarioFactory.class)
public class UsuarioFactoryTest {
	
	@SuppressWarnings("deprecation")
	public static UsuarioTO usuarioToTest() throws Exception{
		UsuarioTO usuarioTest = new UsuarioTO();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("1234");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setSexo("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");
		usuarioTest.setTipConta("P");
		usuarioTest.setConta("Panamericano");

		return usuarioTest;
	}
	
	@SuppressWarnings("deprecation")
	public static Usuario usuarioTest() throws Exception{
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("fc68b677646b5f018d1762e9a19bf65180d9aab2794794340ade50e0d78a239affd43a613e7136a61b5d63b09f072c0c039dea4281873abe826d6e6285d9cefef0a0d868d3b0b0d4582ec787b473b4e0");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setSexo("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");

		return usuarioTest;
	}

//getUsuario_________________________________________________________________________________________
	@Test
	void getUsuarioGmailSucesso() throws Exception {
		UsuarioTO usuarioTO = usuarioToTest();
		usuarioTO.setTipConta(TipoConta.GMAIL.getDescricao());
		
		Usuario usuario = UsuarioFactory.getUsuario(usuarioTO);
		
		assertEquals(usuarioTO.getNomeUser(), usuario.getNomeUser());
		assertEquals(usuarioTO.getCodUsuario(), usuario.getCodUsuario());
		assertEquals(usuarioTO.getDtNasc(), usuario.getDtNasc()); 
		assertEquals(usuarioTO.getEmail(), usuario.getEmail()); 
		assertEquals(usuarioTO.getSexo(), usuario.getSexo()); 
		assertEquals(usuarioTO.getSenha(), usuario.getSenha()); 
		assertEquals(usuarioTO.getConta(), usuario.getGmail());
	}

	@Test
	void getUsuarioFacebookSucesso() throws Exception {
		UsuarioTO usuarioTO = usuarioToTest();
		usuarioTO.setTipConta(TipoConta.FACEBOOK.getDescricao());
		
		Usuario usuario = UsuarioFactory.getUsuario(usuarioTO);
		
		assertEquals(usuarioTO.getNomeUser(), usuario.getNomeUser());
		assertEquals(usuarioTO.getCodUsuario(), usuario.getCodUsuario());
		assertEquals(usuarioTO.getDtNasc(), usuario.getDtNasc()); 
		assertEquals(usuarioTO.getEmail(), usuario.getEmail()); 
		assertEquals(usuarioTO.getSexo(), usuario.getSexo()); 
		assertEquals(usuarioTO.getSenha(), usuario.getSenha());
		assertEquals(usuarioTO.getConta(), usuario.getFacebook());
	}
	
	@Test
	void getUsuarioIndefinidoSucesso() throws Exception {
		UsuarioTO usuarioTO = usuarioToTest();
		
		Usuario usuario = UsuarioFactory.getUsuario(usuarioTO);
		
		assertEquals(usuarioTO.getNomeUser(), usuario.getNomeUser());
		assertEquals(usuarioTO.getCodUsuario(), usuario.getCodUsuario());
		assertEquals(usuarioTO.getDtNasc(), usuario.getDtNasc()); 
		assertEquals(usuarioTO.getEmail(), usuario.getEmail()); 
		assertEquals(usuarioTO.getSexo(), usuario.getSexo()); 
		assertEquals(usuarioTO.getSenha(), usuario.getSenha());
	}
	
//getUsuarioTO_________________________________________________________________________________________

	@Test
	void getUsuarioTOSenhaNullSucesso() throws Exception {
		Usuario usuario = usuarioTest();
		usuario.setSenha(null);
		
		UsuarioTO usuarioTO = UsuarioFactory.getUsuarioTO(usuario);
		
		assertEquals(usuarioTO.getNomeUser(), usuario.getNomeUser());
		assertEquals(usuarioTO.getCodUsuario(), usuario.getCodUsuario());
		assertEquals(usuarioTO.getDtNasc(), usuario.getDtNasc()); 
		assertEquals(usuarioTO.getEmail(), usuario.getEmail()); 
		assertEquals(usuarioTO.getSexo(), usuario.getSexo()); 
		assertEquals(usuarioTO.getSenha(), usuario.getSenha()); 
		assertEquals(usuarioTO.getTipConta(), TipoConta.GMAIL.getDescricao());
	}
	
	@Test
	void getUsuarioTOSenhaNotNullSucesso() throws Exception {
		Usuario usuario = usuarioTest();
		
		UsuarioTO usuarioTO = UsuarioFactory.getUsuarioTO(usuario);
		
		assertEquals(usuarioTO.getNomeUser(), usuario.getNomeUser());
		assertEquals(usuarioTO.getCodUsuario(), usuario.getCodUsuario());
		assertEquals(usuarioTO.getDtNasc(), usuario.getDtNasc()); 
		assertEquals(usuarioTO.getEmail(), usuario.getEmail()); 
		assertEquals(usuarioTO.getSexo(), usuario.getSexo()); 
		assertEquals(usuarioTO.getTipConta(), TipoConta.PACHANGA.getDescricao());
	}
	
//getUsuarioTO(COM FUNCIONALIDADE)___________________________________________________________________
	@Test
	void getUsuarioTOComFuncSenhaNullSucesso() throws Exception {
		Usuario usuario = usuarioTest();
		usuario.setSenha(null);
		String funcionalidade = "funcionalidade";
		
		UsuarioTO usuarioTO = UsuarioFactory.getUsuarioTO(usuario, funcionalidade);
		
		assertEquals(usuarioTO.getNomeUser(), usuario.getNomeUser());
		assertEquals(usuarioTO.getCodUsuario(), usuario.getCodUsuario());
		assertEquals(usuarioTO.getDtNasc(), usuario.getDtNasc()); 
		assertEquals(usuarioTO.getEmail(), usuario.getEmail()); 
		assertEquals(usuarioTO.getSexo(), usuario.getSexo()); 
		assertEquals(usuarioTO.getSenha(), usuario.getSenha()); 
		assertEquals(usuarioTO.getTipConta(), TipoConta.GMAIL.getDescricao());
		assertEquals(usuarioTO.getFuncionalidade(), funcionalidade);
	}
	
	@Test
	void getUsuarioTOComFuncSenhaNotNullSucesso() throws Exception {
		Usuario usuario = usuarioTest();
		String funcionalidade = "funcionalidade";
		
		UsuarioTO usuarioTO = UsuarioFactory.getUsuarioTO(usuario, funcionalidade);
		
		assertEquals(usuarioTO.getNomeUser(), usuario.getNomeUser());
		assertEquals(usuarioTO.getCodUsuario(), usuario.getCodUsuario());
		assertEquals(usuarioTO.getDtNasc(), usuario.getDtNasc()); 
		assertEquals(usuarioTO.getEmail(), usuario.getEmail()); 
		assertEquals(usuarioTO.getSexo(), usuario.getSexo()); 
		assertEquals(usuarioTO.getTipConta(), TipoConta.PACHANGA.getDescricao());
		assertEquals(usuarioTO.getFuncionalidade(), funcionalidade);
	}
	
//getUsuariosTO______________________________________________________________________________________
	@Test
	void getUsuariosTOSucesso() throws Exception {
		List<Usuario> usuarios = new ArrayList<>();
		Usuario usuario = null;
		usuario = usuarioTest();
		usuario.setSenha(null);  
		usuarios.add(usuario);
		
		List<UsuarioTO> usuariosTO = UsuarioFactory.getUsuariosTO(usuarios);
		
		UsuarioTO usuarioTO = null;
		
		usuarioTO = usuariosTO.get(0);
		usuario = usuarios.get(0);
			
		assertEquals(usuarioTO.getNomeUser(), usuario.getNomeUser());
		assertEquals(usuarioTO.getCodUsuario(), usuario.getCodUsuario());
		assertEquals(usuarioTO.getDtNasc(), usuario.getDtNasc()); 
		assertEquals(usuarioTO.getEmail(), usuario.getEmail()); 
		assertEquals(usuarioTO.getSexo(), usuario.getSexo()); 
		assertEquals(usuarioTO.getSenha(), usuario.getSenha());
		assertEquals(usuarioTO.getTipConta(), TipoConta.GMAIL.getDescricao());
	}
	

	
}
