package com.eventmanager.pachanga.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.services.ConvidadoService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=ConvidadoController.class)
public class ConvidadoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ConvidadoService convidadoService;
	
	private StringBuilder criacaoStringEmails() {
		StringBuilder emails = new StringBuilder();
		emails.append("{guga.72@hotmail.com}");
		return emails;
	}
	
	
///addUserFesta_______________________________________________________________________________________________
	@Test
	public void addUserFestaTest() throws Exception{
		String uri = "/grupo/addUserFesta";
		
		String emailsEnviados = "[\"luis_iruca@hotmail.com\",\"guga.72@hotmail.com\"]";
		
		StringBuilder emailsRetorno = criacaoStringEmails(); 
		
		Mockito.when(convidadoService.addUsuariosFesta(Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(emailsRetorno);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "14")
				.param("idUsuario", "14")
				.param("idGrupo", "14")
				.content(emailsEnviados)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String expected = "{guga.72@hotmail.com}";
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		assertEquals(expected, result.getResponse().getContentAsString());
		
	}
	
	@Test
	public void addUserFestaExceptionTest() throws Exception{
		String uri = "/grupo/addUserFesta";
		
		String expected = "addUserFesta";
		
		String emailsEnviados = "[\"luis_iruca@hotmail.com\",\"guga.72@hotmail.com\"]";
		
		Mockito.when(convidadoService.addUsuariosFesta(Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "14")
				.param("idUsuario", "14")
				.param("idGrupo", "14")
				.content(emailsEnviados)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
		
	}
	
///removeUserFesta___________________________________________________________________________________________________________________________________________________________
	/*
	@Test
	public void removeUserFestaTest() throws Exception{
		String uri = "/grupo/removeUserFesta";
		
		List<Usuario> retorno = new ArrayList<>();
		
		Usuario luis = UsuarioControllerTest.usuarioTest();
		luis.setCodUsuario(101);
		luis.setEmail("luis_iruca@hotmail.com");
		retorno.add(luis);
		
		Usuario gustavo = UsuarioControllerTest.usuarioTest();
		gustavo.setCodUsuario(102);
		gustavo.setEmail("guga.72@hotmail.com");
		retorno.add(gustavo);
		
		String emailsEnviados = "[\"luis_iruca@hotmail.com\",\"guga.72@hotmail.com\"]";
		
		Mockito.when(grupoService.deleteUsuariosFesta(Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(retorno);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "14")
				.param("idUsuario", "14")
				.param("idGrupo", "14")
				.content(emailsEnviados)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String expected = "[{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":101,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"luis_iruca@hotmail.com\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":102,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"guga.72@hotmail.com\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}]";
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		assertEquals(expected, result.getResponse().getContentAsString());
		
	}
	
	///addUserFestaById_______________________________________________________________________________________________
	/* Ainda estou fazendo
	@Test
	public void addUserFestaByIdTest() throws Exception{
		String uri = "/grupo/addUserFestaById";
		
		Usuario retorno = UsuarioControllerTest.usuarioTest();
		
		Mockito.when(grupoService.deleteUsuariosFesta(Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(retorno);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codGrupo", "14")
				.param("idUsuario", "14")
				.param("idUsuarioPermissao", "14")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String expected = "[{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":101,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"luis_iruca@hotmail.com\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":102,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"guga.72@hotmail.com\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}]";
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		assertEquals(expected, result.getResponse().getContentAsString());
	
	}
	*/
}
