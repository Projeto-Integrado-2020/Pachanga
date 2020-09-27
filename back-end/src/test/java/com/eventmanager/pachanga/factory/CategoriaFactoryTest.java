package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.PachangaApplication;
import com.eventmanager.pachanga.domains.Categoria;
import com.eventmanager.pachanga.dtos.CategoriaTO;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = PachangaApplication.class)
@AutoConfigureMockMvc
class CategoriaFactoryTest {
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

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
		
		assertEquals( categoriaTO.getCodCategoria(), categoria.getCodCategoria());
		assertEquals( categoriaTO.getNomeCategoria(), categoria.getNomeCategoria());
	}

}
