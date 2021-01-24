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
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eventmanager.pachanga.dtos.PDFPorEmailTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.services.EnvioDePDFPorEmailService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=EnvioDePDFPorEmailController.class)
public class EnvioDePDFPorEmailControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	@MockBean
	private EnvioDePDFPorEmailService envioDePDFPorEmailService;
	
	@Autowired
	EnvioDePDFPorEmailController envioDePDFPorEmailController;
	
	@Test
	@WithMockUser
	public void enviarRelatorio() throws Exception {
		String json = "{\r\n" + 
				"\"base64Pdf\": \"JVBERi0xLjQKJeLjz9MKMSAwIG9iago8PC9MZW5ndGggNTQvRmlsdGVyL0ZsYXRlRGVjb2RlPj5zdHJlYW0KeJwr5HIK4TI2U7AwMFMISeFyDeEK5DJQMFQwAEJvkAQQ5cIU5HCZmlqCRCAMiFAxFwDI1g01CmVuZHN0cmVhbQplbmRvYmoKMyAwIG9iago8PC9QYXJlbnQgMiAwIFIvQ29udGVudHMgMSAwIFIvVHlwZS9QYWdlL1Jlc291cmNlczw8Pj4vTWVkaWFCb3hbMCAwIDU5NSA4NDJdPj4KZW5kb2JqCjIgMCBvYmoKPDwvVHlwZS9QYWdlcy9Db3VudCAxL0tpZHNbMyAwIFJdPj4KZW5kb2JqCjQgMCBvYmoKPDwvVHlwZS9DYXRhbG9nL1BhZ2VzIDIgMCBSPj4KZW5kb2JqCjUgMCBvYmoKPDwvUHJvZHVjZXIoaVRleHSuIDUuNS45LVNOQVBTSE9UIKkyMDAwLTIwMTUgaVRleHQgR3JvdXAgTlYgXChBR1BMLXZlcnNpb25cKSkvTW9kRGF0ZShEOjIwMTUxMjE0MTQyNzEwKzAxJzAwJykvQ3JlYXRpb25EYXRlKEQ6MjAxNTEyMTQxNDI3MTArMDEnMDAnKT4+CmVuZG9iagp4cmVmCjAgNgowMDAwMDAwMDAwIDY1NTM1IGYgCjAwMDAwMDAwMTUgMDAwMDAgbiAKMDAwMDAwMDIyOSAwMDAwMCBuIAowMDAwMDAwMTM1IDAwMDAwIG4gCjAwMDAwMDAyODAgMDAwMDAgbiAKMDAwMDAwMDMyNSAwMDAwMCBuIAp0cmFpbGVyCjw8L1Jvb3QgNCAwIFIvSUQgWzxkNjgyNzc1MmE1N2U1YjdhNDU1ZWQ5NGI0ODBhMjA3Yz48ZDY4Mjc3NTJhNTdlNWI3YTQ1NWVkOTRiNDgwYTIwN2M+XS9JbmZvIDUgMCBSL1NpemUgNj4+CiVpVGV4dC01LjUuOS1TTkFQU0hPVApzdGFydHhyZWYKNDkxCiUlRU9GCg==\",\r\n" + 
				"\"listaDeEmails\": [\r\n" + 
				"\"fernando@email.invalid\"\r\n" + 
				"]\r\n" + 
	            "}";
		
		String uri = "/envioDePDFPorEmail/enviarRelatorio";

		//Mockito.when(festaService.addFesta(Mockito.any(FestaTO.class), Mockito.any(Integer.class), Mockito.any())).thenReturn(festaTest);
		Mockito.when(envioDePDFPorEmailService.enviarRelatorio(Mockito.any(PDFPorEmailTO.class), Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.param("codUsuario", "1")
				.param("codFesta", "1")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		String expected = "true";
	
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	@WithMockUser
	public void enviarRelatorioException() throws Exception {
		String json = "{\r\n" + 
				"\"base64Pdf\": \"JVBERi0xLjQKJeLjz9MKMSAwIG9iago8PC9MZW5ndGggNTQvRmlsdGVyL0ZsYXRlRGVjb2RlPj5zdHJlYW0KeJwr5HIK4TI2U7AwMFMISeFyDeEK5DJQMFQwAEJvkAQQ5cIU5HCZmlqCRCAMiFAxFwDI1g01CmVuZHN0cmVhbQplbmRvYmoKMyAwIG9iago8PC9QYXJlbnQgMiAwIFIvQ29udGVudHMgMSAwIFIvVHlwZS9QYWdlL1Jlc291cmNlczw8Pj4vTWVkaWFCb3hbMCAwIDU5NSA4NDJdPj4KZW5kb2JqCjIgMCBvYmoKPDwvVHlwZS9QYWdlcy9Db3VudCAxL0tpZHNbMyAwIFJdPj4KZW5kb2JqCjQgMCBvYmoKPDwvVHlwZS9DYXRhbG9nL1BhZ2VzIDIgMCBSPj4KZW5kb2JqCjUgMCBvYmoKPDwvUHJvZHVjZXIoaVRleHSuIDUuNS45LVNOQVBTSE9UIKkyMDAwLTIwMTUgaVRleHQgR3JvdXAgTlYgXChBR1BMLXZlcnNpb25cKSkvTW9kRGF0ZShEOjIwMTUxMjE0MTQyNzEwKzAxJzAwJykvQ3JlYXRpb25EYXRlKEQ6MjAxNTEyMTQxNDI3MTArMDEnMDAnKT4+CmVuZG9iagp4cmVmCjAgNgowMDAwMDAwMDAwIDY1NTM1IGYgCjAwMDAwMDAwMTUgMDAwMDAgbiAKMDAwMDAwMDIyOSAwMDAwMCBuIAowMDAwMDAwMTM1IDAwMDAwIG4gCjAwMDAwMDAyODAgMDAwMDAgbiAKMDAwMDAwMDMyNSAwMDAwMCBuIAp0cmFpbGVyCjw8L1Jvb3QgNCAwIFIvSUQgWzxkNjgyNzc1MmE1N2U1YjdhNDU1ZWQ5NGI0ODBhMjA3Yz48ZDY4Mjc3NTJhNTdlNWI3YTQ1NWVkOTRiNDgwYTIwN2M+XS9JbmZvIDUgMCBSL1NpemUgNj4+CiVpVGV4dC01LjUuOS1TTkFQU0hPVApzdGFydHhyZWYKNDkxCiUlRU9GCg==\",\r\n" + 
				"\"listaDeEmails\": [\r\n" + 
				"\"fernando@email.invalid\"\r\n" + 
				"]\r\n" + 
	            "}";
		
		String uri = "/envioDePDFPorEmail/enviarRelatorio";
		String expected = "erro";

		//Mockito.when(festaService.addFesta(Mockito.any(FestaTO.class), Mockito.any(Integer.class), Mockito.any())).thenReturn(festaTest);
		Mockito.when(envioDePDFPorEmailService.enviarRelatorio(Mockito.any(PDFPorEmailTO.class), Mockito.anyInt(), Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.param("codUsuario", "1")
				.param("codFesta", "1")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		//String expected = "true";
	
		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
}
