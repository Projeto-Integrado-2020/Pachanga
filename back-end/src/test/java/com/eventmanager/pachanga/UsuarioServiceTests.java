package com.eventmanager.pachanga;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eventmanager.pachanga.controllers.UsuarioController;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.services.UsuarioService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=UsuarioController.class)
public class UsuarioServiceTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UsuarioService userService;

	@SuppressWarnings("deprecation")
	@Test
	public void loginTest() throws Exception{

		String usuarioLoginJson = "{\"email\":\"gustavinhoTPD@fodasse.com.br\",\"senha\":\"1234\",\"tipConta\": \"P\"}";

		Usuario usuarioTest = new Usuario();
		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("1234");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setSexo("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");
		usuarioTest.setTipConta("P");
		
		String uri = "/usuario/login";

		Mockito.when(userService.login(Mockito.any(Usuario.class))).thenReturn(usuarioTest);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(usuarioLoginJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		String expected = "{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":100,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"email\":\"gustavinhoTPD@fodasse.com.br\",\"senha\":\"1234\",\"sexo\":\"M\"}";

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
}
