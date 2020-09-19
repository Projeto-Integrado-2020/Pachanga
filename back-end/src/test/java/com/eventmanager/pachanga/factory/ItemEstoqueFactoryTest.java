package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.PachangaApplication;
import com.eventmanager.pachanga.domains.Estoque;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.ItemEstoque;
import com.eventmanager.pachanga.domains.Produto;
import com.eventmanager.pachanga.dtos.ItemEstoqueTO;
import com.eventmanager.pachanga.dtos.ProdutoTO;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = PachangaApplication.class)
@AutoConfigureMockMvc
class ItemEstoqueFactoryTest {

	@Autowired
	private ItemEstoqueFactory itemEstoqueFactory;

	@MockBean
	private ProdutoFactory produtoFactory;

	private ItemEstoqueFactoryTest() {
		this.itemEstoqueFactory = new ItemEstoqueFactory();
		// this.produtoFactory = new ProdutoFactory();
	}
//metodos auxiliares__________________________________________________________________________________

	private ItemEstoque itemEstoqueTest() throws Exception {
		ItemEstoque itemEstoque = new ItemEstoque();
		itemEstoque.setCodFesta(2); // o mesmo do festaTest()
		itemEstoque.setQuantidadeMax(100);
		itemEstoque.setPorcentagemMin(15);
		itemEstoque.setQuantidadeAtual(20);
		itemEstoque.setEstoque(estoqueTest());
		itemEstoque.setProduto(produtoTest());
		return itemEstoque;
	}

	private ItemEstoqueTO itemEstoqueTOTest() {
		ItemEstoqueTO itemEstoqueTO = new ItemEstoqueTO();
		itemEstoqueTO.setCodFesta(2); // o mesmo do festaTest()
		itemEstoqueTO.setQuantidadeMax(100);
		itemEstoqueTO.setPorcentagemMin(15);
		itemEstoqueTO.setQuantidadeAtual(20);
		itemEstoqueTO.setCodEstoque(1);
		itemEstoqueTO.setCodProduto(1);
		return itemEstoqueTO;
	}

	private ProdutoTO produtoTOTest() {
		ProdutoTO produtoTO = new ProdutoTO();
		produtoTO.setCodProduto(1);
		produtoTO.setCodFesta(2); // o mesmo do festaTest()
		produtoTO.setMarca("Cápsula");
		produtoTO.setPrecoMedio(new BigDecimal("23.90"));
		return produtoTO;

	}

	private Produto produtoTest() {
		Produto produto = new Produto();
		produto.setCodProduto(1);
		produto.setCodFesta(2); // o mesmo do festaTest()
		produto.setMarca("Cápsula");
		produto.setPrecoMedio(new BigDecimal("23.90"));
		return produto;

	}

	private Estoque estoqueTest() throws Exception {
		Estoque estoque = new Estoque();
		estoque.setCodEstoque(1);
		estoque.setFesta(festaTest());
		estoque.setNomeEstoque("estoque");
		estoque.setPrincipal(false);
		return estoque;
	}

	public Festa festaTest() throws Exception {
		Festa festaTest = new Festa();
		festaTest.setCodFesta(2);
		festaTest.setCodEnderecoFesta("https//:minhacasa.org");
		festaTest.setDescOrganizador("sou demente");
		festaTest.setHorarioInicioFesta(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		festaTest.setHorarioFimFesta(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		festaTest.setNomeFesta("festao");
		festaTest.setOrganizador("Joao Neves");
		festaTest.setStatusFesta(TipoStatusFesta.PREPARACAO.getValor());
		festaTest.setDescricaoFesta("Bugago");
		festaTest.setHorarioFimFestaReal(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));

		return festaTest;
	}

//testes_________________________________________________________________________________________________
	@Test
	void getItemEstoqueSucesso() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();

		ItemEstoque itemEstoque = itemEstoqueFactory.getItemEstoque(itemEstoqueTO, produto, estoque);

		assertEquals(itemEstoque.getCodFesta(), itemEstoqueTO.getCodFesta());
		assertEquals(itemEstoque.getQuantidadeMax(), itemEstoqueTO.getQuantidadeMax());
		assertEquals(itemEstoque.getQuantidadeAtual(), itemEstoqueTO.getQuantidadeAtual());
		assertEquals(itemEstoque.getPorcentagemMin(), itemEstoqueTO.getPorcentagemMin());
		assertEquals(itemEstoque.getEstoque(), estoque);
		assertEquals(itemEstoque.getProduto(), produto);
	}

	@Test
	void getItemEstoqueTOSucesso() throws Exception {
		ItemEstoque itemEstoque = itemEstoqueTest();
		ProdutoTO produtoTO = produtoTOTest();

		Mockito.when(produtoFactory.getProdutoTO(Mockito.any(Produto.class))).thenReturn(produtoTO);
		ItemEstoqueTO itemEstoqueTO = itemEstoqueFactory.getItemEstoqueTO(itemEstoque);

		assertEquals(itemEstoqueTO.getCodFesta(), itemEstoque.getCodFesta());
		assertEquals(itemEstoqueTO.getQuantidadeMax(), itemEstoque.getQuantidadeMax());
		assertEquals(itemEstoqueTO.getQuantidadeAtual(), itemEstoque.getQuantidadeAtual());
		assertEquals(itemEstoqueTO.getPorcentagemMin(), itemEstoque.getPorcentagemMin());
		assertEquals(itemEstoqueTO.getCodEstoque(), itemEstoque.getEstoque().getCodEstoque());
		assertEquals(itemEstoqueTO.getProduto().getCodProduto(), itemEstoque.getProduto().getCodProduto());
	}

	@Test
	void getListItemEstoqueTOSucesso() throws Exception {
		Produto produto = produtoTest();
		ItemEstoque itemEstoque = itemEstoqueTest();
		itemEstoque.setProduto(produto);

		Set<ItemEstoque> itensEstoque = new HashSet<>();
		itensEstoque.add(itemEstoque);

		List<ItemEstoqueTO> itensEstoqueTO = itemEstoqueFactory.getListItemEstoqueTO(itensEstoque);

		ItemEstoqueTO itemEstoqueTO = itensEstoqueTO.get(0);

		assertEquals(itemEstoqueTO.getCodFesta(), itemEstoque.getCodFesta());
		assertEquals(itemEstoqueTO.getQuantidadeMax(), itemEstoque.getQuantidadeMax());
		assertEquals(itemEstoqueTO.getQuantidadeAtual(), itemEstoque.getQuantidadeAtual());
		assertEquals(itemEstoqueTO.getPorcentagemMin(), itemEstoque.getPorcentagemMin());
		assertEquals(itemEstoqueTO.getCodEstoque(), itemEstoque.getEstoque().getCodEstoque());
	}

}
