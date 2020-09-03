package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Categoria;
import com.eventmanager.pachanga.dtos.CategoriaTO;

@RunWith(SpringRunner.class)
@WebMvcTest(value=CategoriaFactory.class)
class CategoriaFactoryTest {

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
