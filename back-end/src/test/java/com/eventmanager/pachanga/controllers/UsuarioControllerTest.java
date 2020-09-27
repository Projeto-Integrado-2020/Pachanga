package com.eventmanager.pachanga.controllers;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.UsuarioTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.services.UsuarioService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=UsuarioController.class)
class UsuarioControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UsuarioService userService;
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	@SuppressWarnings("deprecation")
	public Usuario usuarioTest() throws Exception{
		Usuario usuarioTest = new Usuario();
		
		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("1234");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setGenero("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");
		
		return usuarioTest;
	}	

//login__________________________________________________________________________________________________________	
	
	@Test
	@WithMockUser
	void loginSucessoTest() throws Exception{

		String usuarioLoginJson = "{\"email\":\"gustavinhoTPD@fodasse.com.br\",\"senha\":\"1234\",\"tipConta\": \"P\"}";

		Usuario usuarioTest = usuarioTest();
		
		String uri = "/usuario/login";

		Mockito.when(userService.logar(Mockito.any(UsuarioTO.class))).thenReturn(usuarioTest);
		

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(usuarioLoginJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		String expected = "{\"dtNasc\":\"3900-09-27T00:00:00.000+0000\",\"codUsuario\":100,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\": null,\"sexo\":\"M\",\"funcionalidade\": null}";

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}

	@Test
	@WithMockUser
	void loginSenhaIncorretaTest() throws Exception{

		String usuarioLoginJson = "{\"email\":\"gustavinhoTPD@fodasse.com.br\",\"senha\":\"123456\",\"tipConta\": \"P\"}";

		Usuario usuarioTest = usuarioTest();
		usuarioTest.setSenha("123456");
		
		String uri = "/usuario/login";

		Mockito.when(userService.logar(Mockito.any(UsuarioTO.class))).thenThrow(new ValidacaoException("2"));
		

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(usuarioLoginJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		String expected = "2";

		assertEquals(400, response.getStatus());
		
		assertEquals(expected, result.getResponse().getContentAsString());
		
	}
	
	@Test
	@WithMockUser
	void loginEmailInesistenteTest() throws Exception{

		String usuarioLoginJson = "{\"email\":\"gustavinhoTPDnaoexiste@fodasse.com.br\",\"senha\":\"1234\",\"tipConta\": \"P\"}";
		
		String uri = "/usuario/login";
		
		Mockito.when(userService.logar(Mockito.any(UsuarioTO.class))).thenThrow(new ValidacaoException("3"));
		

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(usuarioLoginJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		String expected = "3";

		assertEquals(true, 400 == response.getStatus());
		
		assertEquals(expected, result.getResponse().getContentAsString());
		
	}
	
//cadastro__________________________________________________________________________________________________________	
	
	@Test
	@WithMockUser
	void CadastroSucessoTest() throws Exception{
		String usuarioCadastroJson = "{\"dtNasc\":\"3900-09-27\",\"codUsuario\":100,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"email\":\"gustavinhoTPD@fodasse.com.br\",\"senha\":\"1234\",\"sexo\":\"M\"}";

		Usuario usuarioTest = usuarioTest();
		
		String uri = "/usuario/cadastro";
		
		Mockito.when(userService.cadastrar(Mockito.any(UsuarioTO.class))).thenReturn(usuarioTest);
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(usuarioCadastroJson )
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();

		String expected = "{\"dtNasc\":\"3900-09-27T00:00:00.000+0000\",\"codUsuario\":100,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\": null,\"sexo\":\"M\", \"funcionalidade\": null}";

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
		
	}

	@Test
	@WithMockUser
	void CadastroValidacaoExceptionTest() throws Exception{
		String usuarioCadastroJson = "{\"dtNasc\":\"3900-09-27\",\"codUsuario\":100,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"email\":\"gustavinhoTPD@fodasse.com.br\",\"senha\":\"1234\",\"sexo\":\"M\"}";
		
		String uri = "/usuario/cadastro";
		
		Mockito.when(userService.cadastrar(Mockito.any(UsuarioTO.class))).thenThrow(new ValidacaoException("1"));
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(usuarioCadastroJson )
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();

		String expected = "1";
	
		assertEquals(true, 400 == response.getStatus());
		
		assertEquals(expected, result.getResponse().getContentAsString());
		
	}
	
//atualizacao__________________________________________________________________________________________________________	
	
	@SuppressWarnings("deprecation")
	@Test
	@WithMockUser
	void AtualizacaoSucessoTest() throws Exception{
		String usuarioAtualizacaoJson = "{\"dtNasc\":\"3900-09-26\",\"codUsuario\":100,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"email\":\"gustavinhoTPD@fodasse.com.br\",\"senha\":\"1234567\",\"sexo\":\"F\"}";

		Usuario usuarioTest = usuarioTest();
		usuarioTest.setGenero("F");
		usuarioTest.setDtNasc(new Date(2000, 8, 26));
		
		String uri = "/usuario/atualizacao";
		
		Mockito.when(userService.atualizar(Mockito.any(UsuarioTO.class))).thenReturn(usuarioTest);
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(usuarioAtualizacaoJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();

		String expected = "{\"dtNasc\":\"3900-09-26T00:00:00.000+0000\",\"codUsuario\":100,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\": null,\"sexo\":\"F\",\"funcionalidade\": null}";

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
		
	}

	@Test
	@WithMockUser
	void AtualizacaoValidacaoExceptionTest() throws Exception{
		String usuarioAtualizacaoJson = "{\"dtNasc\":\"3900-09-26\",\"codUsuario\":100,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"email\":\"luisinhofoda1234@fodasse.com.br\",\"senha\":\"1234567\",\"sexo\":\"F\"}";
		
		String uri = "/usuario/atualizacao";
		
		Mockito.when(userService.atualizar(Mockito.any(UsuarioTO.class))).thenThrow(new ValidacaoException("3"));
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(usuarioAtualizacaoJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();

		String expected = "3";
		
		assertEquals(true, 400 == response.getStatus());
		
		
		assertEquals(expected, result.getResponse().getContentAsString());
		
	}
}
