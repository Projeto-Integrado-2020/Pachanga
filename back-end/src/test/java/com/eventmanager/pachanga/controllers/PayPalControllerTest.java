package com.eventmanager.pachanga.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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

import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.dtos.IngressoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.services.PayPalService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PayPalController.class)
public class PayPalControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PayPalService paypalService;

	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	private List<IngressoTO> ingressosToTeste() {
		IngressoTO ingresso = new IngressoTO();
		FestaTO festa = new FestaTO();
		festa.setCodFesta(1);
		ingresso.setFesta(festa);
		ingresso.setCodUsuario(2);
		ingresso.setCodLote(1);
		ingresso.setStatusIngresso("U");
		ingresso.setStatusCompra("P");
		ingresso.setNomeTitular("Teste");
		ingresso.setEmailTitular("teste@teste.com");
		ingresso.setCodIngresso("codIngresso");
		List<IngressoTO> ingressos = new ArrayList<IngressoTO>();
		ingressos.add(ingresso);
		return ingressos;
	}
	
	@Test
	@WithMockUser
	void authorizeOrderSucesso() throws Exception {

		String uri = "/paypal/authorizeOrder";

		String json = "{\"listaIngresso\":[{\"codLote\":2,\"festa\":{\"codFesta\":1},\"codUsuario\":1,\"statusIngresso\":\"U\",\"preco\":12.0,\"statusCompra\":\"P\",\"nomeTitular\":\"Andrey Camargo Lacerda\",\"emailTitular\":\"guga.72@hotmail.com\",\"boleto\":false}]}";
		
		Mockito.when(paypalService.authorizeOrder(Mockito.anyString(), Mockito.any(), Mockito.anyBoolean())).thenReturn(ingressosToTeste());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri).param("orderId", "TESTEORDERID").content(json)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	
	@Test
	@WithMockUser
	void authorizeOrderErro() throws Exception {

		String uri = "/paypal/authorizeOrder";

		String json = "{\"listaIngresso\":[{\"codLote\":2,\"festa\":{\"codFesta\":1},\"codUsuario\":1,\"statusIngresso\":\"U\",\"preco\":12.0,\"statusCompra\":\"P\",\"nomeTitular\":\"Andrey Camargo Lacerda\",\"emailTitular\":\"guga.72@hotmail.com\",\"boleto\":false}]}";

		Mockito.when(paypalService.authorizeOrder(Mockito.anyString(), Mockito.any(), Mockito.anyBoolean())).thenThrow(new ValidacaoException("teste"));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(uri).param("orderId", "TESTEORDERID").content(json)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

	}

}
