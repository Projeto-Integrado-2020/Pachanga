package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Produto;
import com.eventmanager.pachanga.dtos.ProdutoTO;

@RunWith(SpringRunner.class)
@WebMvcTest(value=ProdutoFactory.class)
class ProdutoFactoryTest {
	
	private ProdutoFactory produtoFactory;
	
	public ProdutoFactoryTest() {
		this.produtoFactory = new ProdutoFactory();
	}

	private ProdutoTO produtoTOTest() {
		ProdutoTO produtoTO = new ProdutoTO();
		produtoTO.setCodProduto(1);
		produtoTO.setCodFesta(2); //o mesmo do festaTest()
		produtoTO.setMarca("Cápsula");
		produtoTO.setPrecoMedio(new BigDecimal("23.90"));
		return produtoTO;

	}
	
	private Produto produtoTest() {
		Produto produto = new Produto();
		produto.setCodProduto(1);
		produto.setCodFesta(2); //o mesmo do festaTest() 
		produto.setMarca("Cápsula");
		produto.setPrecoMedio(new BigDecimal("23.90"));
		return produto;

	}
	
	@Test
	void getProdutoTOSucesso() throws Exception {
		Produto produto = produtoTest();
		
		ProdutoTO produtoTO = produtoFactory.getProdutoTO(produto);
		
		assertEquals( produto.getCodFesta(), produtoTO.getCodFesta());
		assertEquals( produto.getCodProduto(), produtoTO.getCodProduto());
		assertEquals( produto.getMarca(), produtoTO.getMarca());
		assertEquals( produto.getPrecoMedio(), produtoTO.getPrecoMedio());
		//assertEquals( , );
	}
	
	@Test
	void getProdutoSucesso() throws Exception {
		ProdutoTO produtoTO = produtoTOTest();
		
		Produto produto = produtoFactory.getProduto(produtoTO);
		
		assertEquals( produto.getCodFesta(), produtoTO.getCodFesta());
		assertEquals( produto.getCodProduto(), produtoTO.getCodProduto());
		assertEquals( produto.getMarca(), produtoTO.getMarca());
		assertEquals( produto.getPrecoMedio(), produtoTO.getPrecoMedio());
		//assertEquals( , );
	}
	
	@Test
	void getProdutosTOSucesso() throws Exception {
		Produto produto = null;
		List<Produto> produtos = new ArrayList<>();
		produto = produtoTest();
		produtos.add(produto);
		
		List<ProdutoTO> produtosTO = produtoFactory.getProdutosTO(produtos);
			
		ProdutoTO produtoTO = produtosTO.get(0);
		
		assertEquals( produto.getCodFesta(), produtoTO.getCodFesta());
		assertEquals( produto.getCodProduto(), produtoTO.getCodProduto());
		assertEquals( produto.getMarca(), produtoTO.getMarca());
		assertEquals( produto.getPrecoMedio(), produtoTO.getPrecoMedio());
		//assertEquals( , );
	}
}
