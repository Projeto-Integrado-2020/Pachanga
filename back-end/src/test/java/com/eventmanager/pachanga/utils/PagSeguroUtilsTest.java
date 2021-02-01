package com.eventmanager.pachanga.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;

import kong.unirest.GetRequest;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ Unirest.class, PagSeguroUtils.class, JSONObject.class })
public class PagSeguroUtilsTest {

	@Mock
	private GetRequest getRequest;

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

	@Test
	void criacaoBoletoSucesso() throws JSONException {
		PowerMockito.mockStatic(Unirest.class);
		PowerMockito.when(Unirest.get(Mockito.anyString())).thenReturn(getRequest);
		PowerMockito.when(getRequest.asString()).thenReturn(httpResponse);
		PowerMockito.when(httpResponse.getStatus()).thenReturn(201);
		PowerMockito.when(new JSONObject(Mockito.anyString())).thenReturn(obj);
		PowerMockito.when(
				obj.getJSONArray(Mockito.anyString()).getJSONObject(Mockito.anyInt()).getString(Mockito.anyString()))
				.thenReturn("teste");

		String urlBoleto = PagSeguroUtils.criacaoBoleto(null, 20.00f, "abcdefghijklmna", null);

	}

}
