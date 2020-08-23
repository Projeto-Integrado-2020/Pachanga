package com.eventmanager.pachanga.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Categoria;
import com.eventmanager.pachanga.repositories.CategoriaRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value=CategoriaService.class)
class CategoriaServiceTest {
	
	@MockBean
	private CategoriaRepository categoriaRepository;

	@Autowired
	private CategoriaService categoriaService;
	
	private Categoria categoriaTest() {
		Categoria categoria = new Categoria();
		categoria.setCodCategoria(1);
		categoria.setNomeCategoria("teste");
		return categoria;
	}
	
	@Test
	void procurarCategoriasTest() {
		List<Categoria> categorias = new ArrayList<Categoria>();
		categorias.add(categoriaTest());
		
		Mockito.when(categoriaRepository.findAll()).thenReturn(categorias);
		
		List<Categoria> categoriasRetorno = categoriaService.procurarCategorias();
		
		assertEquals(categorias.size(), categoriasRetorno.size());
		assertEquals(true, categoriasRetorno.containsAll(categorias));		
		
	}
	
	@Test
	void procurarCategoriaFestaTest() {
		
		Mockito.when(categoriaRepository.findCategoriaFesta(Mockito.anyInt(), Mockito.anyString())).thenReturn(categoriaTest());
		
		Categoria categoriaRetorno = categoriaService.procurarCategoriaFesta(1, "teste");
		
		assertEquals(categoriaRetorno.getCodCategoria(), categoriaTest().getCodCategoria());
		assertEquals(categoriaRetorno.getNomeCategoria(), categoriaTest().getNomeCategoria());		
		
	}

}
