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
import com.eventmanager.pachanga.services.GrupoService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=GrupoController.class)
public class GrupoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private GrupoService grupoService;
	
	private StringBuilder criacaoStringEmails() {
		StringBuilder emails = new StringBuilder();
		emails.append("{guga.72@hotmail.com}");
		return emails;
	}
	
	
	@Test
	public void addUserFestaTest() throws Exception{
		String uri = "/grupo/addUserFesta";
		
		String emailsEnviados = "[\"luis_iruca@hotmail.com\",\"guga.72@hotmail.com\"]";
		
		StringBuilder emailsRetorno = criacaoStringEmails(); 
		
		Mockito.when(grupoService.addUsuariosFesta(Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(emailsRetorno);
		
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
		
		Mockito.when(grupoService.addUsuariosFesta(Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		
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

}
