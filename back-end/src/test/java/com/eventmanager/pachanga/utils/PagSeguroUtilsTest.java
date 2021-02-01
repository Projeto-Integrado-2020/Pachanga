package com.eventmanager.pachanga.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.legacy.PowerMockRunner;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Unirest.class, PagSeguroUtils.class, JSONObject.class })
class PagSeguroUtilsTest {

	@Mock
	private HttpResponse<String> httpResponse;

	@Mock
	private JSONObject obj;

	@Mock
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@Mock
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@Mock
	private JwtTokenUtil defaultJwtTokenUtil;

	@Mock
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

//	@Test
//	void criacaoBoletoSucesso() throws Exception {
//		PowerMockito.mockStatic(Unirest.class);
//		PowerMockito.when(Unirest.get(Mockito.anyString()).asString()).thenReturn(httpResponse);
////		PowerMockito.when(getRequest.asString()).thenReturn(httpResponse);
//		PowerMockito.when(httpResponse.getStatus()).thenReturn(201);
//		PowerMockito.when(new JSONObject(Mockito.anyString())).thenReturn(obj);
//		PowerMockito.when(
//				obj.getJSONArray(Mockito.anyString()).getJSONObject(Mockito.anyInt()).getString(Mockito.anyString()))
//				.thenReturn("teste");
//
//		String urlBoleto = PagSeguroUtils.criacaoBoleto(null, 20.00f, "abcdefghijklmna", null);
//		
//		assertEquals(true, "teste".equals(urlBoleto));
//	}

}
