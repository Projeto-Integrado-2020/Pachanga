package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Categoria;
import com.eventmanager.pachanga.dtos.CategoriaTO;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=CategoriaFactory.class)
class CategoriaFactoryTest {
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	private Categoria categoriaTest() {
		Categoria categoria = new Categoria();
		categoria.setCodCategoria(1);
		categoria.setNomeCategoria("Categoria");
		return categoria;
	}
	
	@Test
	void getCategoriaTOSucesso() throws Exception {
		Categoria categoria = categoriaTest();
		
		CategoriaTO categoriaTO  = CategoriaFactory.getCategoriaTO(categoria);
		
		assertEquals( categoriaTO.getCodCategoria().intValue(), categoria.getCodCategoria());
		assertEquals( categoriaTO.getNomeCategoria(), categoria.getNomeCategoria());
	}

}
