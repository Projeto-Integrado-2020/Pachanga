package com.eventmanager.pachanga.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

import com.eventmanager.pachanga.PachangaApplication;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.services.ConvidadoService;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = PachangaApplication.class)
@AutoConfigureMockMvc
class ConvidadoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ConvidadoService convidadoService;
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	private StringBuilder criacaoStringEmails() {
		StringBuilder emails = new StringBuilder();
		emails.append("{guga.72@hotmail.com}");
		return emails;
	}
	
	
///addUserFesta_______________________________________________________________________________________________
	@Test
	@WithMockUser
	void addUserFestaTest() throws Exception{
		String uri = "/convidado/addUserFesta";
		
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
	@WithMockUser
	void addUserFestaExceptionTest() throws Exception{
		String uri = "/convidado/addUserFesta";
		
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
	
//accConvite____________________________________________________________________________________________________________________________________________
	@Test
	@WithMockUser
	void accConviteTest() throws Exception{
		String uri = "/convidado/accConvite";
		
		Mockito.doNothing().when(convidadoService).aceitarConvite(Mockito.anyInt(), Mockito.anyInt());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codConvidado", "1")
				.param("idGrupo", "14")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
	
	@Test
	@WithMockUser
	void accConviteErroTest() throws Exception{
		String uri = "/convidado/accConvite";
		
		Mockito.doThrow(new ValidacaoException("teste")).when(convidadoService).aceitarConvite(Mockito.anyInt(), Mockito.anyInt());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codConvidado", "1")
				.param("idGrupo", "14")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(400, response.getStatus());
		
	}
	
//recuConvite___________________________________________________________________________________________________________________________________
	@Test
	@WithMockUser
	void recuConviteTest() throws Exception{
		String uri = "/convidado/recuConvite";
		
		Mockito.doNothing().when(convidadoService).recusarConvite(Mockito.anyInt(), Mockito.anyInt());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codConvidado", "1")
				.param("idGrupo", "14")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
	
	@Test
	@WithMockUser
	void recuConviteErroTest() throws Exception{
		String uri = "/convidado/recuConvite";
		
		Mockito.doThrow(new ValidacaoException("teste")).when(convidadoService).recusarConvite(Mockito.anyInt(), Mockito.anyInt());
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codConvidado", "1")
				.param("idGrupo", "14")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(400, response.getStatus());
		
	}
	
}
