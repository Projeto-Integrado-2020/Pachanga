package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Estoque;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.ItemEstoque;
import com.eventmanager.pachanga.domains.Produto;
import com.eventmanager.pachanga.dtos.EstoqueTO;
import com.eventmanager.pachanga.dtos.ItemEstoqueTO;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;

@RunWith(SpringRunner.class)
@WebMvcTest(value=EstoqueFactory.class)
public class EstoqueFactoryTest {
	
	@Autowired
	private EstoqueFactory estoqueFactory;
	
	@MockBean
	private ItemEstoqueFactory itemEstoqueFactory;
	
	private Estoque estoqueTest() throws Exception {
		Estoque estoque = new Estoque();
		estoque.setCodEstoque(1);
		estoque.setFesta(festaTest());
		estoque.setNomeEstoque("estoque");
		estoque.setPrincipal(false);
		return estoque;
	}
	
	public Festa festaTest() throws Exception{
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
	
	private ItemEstoque itemEstoqueTest() throws Exception {
		ItemEstoque itemEstoque = new ItemEstoque();
		itemEstoque.setCodFesta(2); //o mesmo do festaTest() 
		itemEstoque.setQuantidadeMax(100);
		itemEstoque.setPorcentagemMin(15);
		itemEstoque.setQuantidadeAtual(20);
		itemEstoque.setEstoque(estoqueTest());
		itemEstoque.setProduto(produtoTest());
		return itemEstoque;
	}

	private ItemEstoqueTO itemEstoqueTOTest() {
		ItemEstoqueTO itemEstoqueTO = new ItemEstoqueTO();
		itemEstoqueTO.setCodFesta(2); //o mesmo do festaTest() 
		itemEstoqueTO.setQuantidadeMax(100);
		itemEstoqueTO.setPorcentagemMin(15);
		itemEstoqueTO.setQuantidadeAtual(20);
		itemEstoqueTO.setCodEstoque(1);
		itemEstoqueTO.setCodProduto(1);
		return itemEstoqueTO;
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
	void getEstoqueTOSucesso() throws Exception {
		Estoque estoque = estoqueTest();
		ItemEstoque itemEstoque = itemEstoqueTest();
		Set<ItemEstoque> itensEstoque = new HashSet<>();
		itensEstoque.add(itemEstoque);
		estoque.setItemEstoque(itensEstoque);
		
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		List<ItemEstoqueTO> itensEstoqueTO = new ArrayList<>();
		itensEstoqueTO.add(itemEstoqueTO);
		
		Mockito.when(itemEstoqueFactory.getListItemEstoqueTO(itensEstoque)).thenReturn(itensEstoqueTO);
		EstoqueTO estoqueTO = estoqueFactory.getEstoqueTO(estoque);
		
		assertEquals( estoqueTO.getCodEstoque(), estoque.getCodEstoque());
		assertEquals( estoqueTO.getNomeEstoque(), estoque.getNomeEstoque());
		assertEquals( estoqueTO.isPrincipal(), estoque.isPrincipal());
		//assertEquals( , );
	}
	
	@Test
	void getEstoqueTOEstoqueNullSucesso() throws Exception {
		Estoque estoque = estoqueTest();
		ItemEstoque itemEstoque = itemEstoqueTest();
		Set<ItemEstoque> itensEstoque = new HashSet<>();
		itensEstoque.add(itemEstoque);
		estoque.setItemEstoque(null);
		
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		List<ItemEstoqueTO> itensEstoqueTO = new ArrayList<>();
		itensEstoqueTO.add(itemEstoqueTO);
		
		Mockito.when(itemEstoqueFactory.getListItemEstoqueTO(itensEstoque)).thenReturn(itensEstoqueTO);
		EstoqueTO estoqueTO = estoqueFactory.getEstoqueTO(estoque);
		
		assertEquals( estoqueTO.getCodEstoque(), estoque.getCodEstoque());
		assertEquals( estoqueTO.getNomeEstoque(), estoque.getNomeEstoque());
		assertEquals( estoqueTO.isPrincipal(), estoque.isPrincipal());
		//assertEquals( , );
	}
	
	@Test
	void getEstoqueTOEstoqueVazioSucesso() throws Exception {
		Estoque estoque = estoqueTest();
		Set<ItemEstoque> itensEstoque = new HashSet<>();
		estoque.setItemEstoque(itensEstoque );
		
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		List<ItemEstoqueTO> itensEstoqueTO = new ArrayList<>();
		itensEstoqueTO.add(itemEstoqueTO);
		
		Mockito.when(itemEstoqueFactory.getListItemEstoqueTO(itensEstoque)).thenReturn(itensEstoqueTO);
		EstoqueTO estoqueTO = estoqueFactory.getEstoqueTO(estoque);
		
		assertEquals( estoqueTO.getCodEstoque(), estoque.getCodEstoque());
		assertEquals( estoqueTO.getNomeEstoque(), estoque.getNomeEstoque());
		assertEquals( estoqueTO.isPrincipal(), estoque.isPrincipal());
	}
	
	@Test
	void getEstoqueSucesso() throws Exception {
		Estoque estoque = estoqueTest();
		
		String nomeEstoque = estoque.getNomeEstoque();
		boolean principal = estoque.isPrincipal();
		
		Estoque retorno = estoqueFactory.getEstoque(nomeEstoque, principal);
		
		assertEquals( retorno.getNomeEstoque(), estoque.getNomeEstoque());
		assertEquals( retorno.isPrincipal(), estoque.isPrincipal());
		
	}
	
	@Test
	void  getListEstoqueTOSucesso() throws Exception {
		List<Estoque> estoques = new ArrayList<>();
		Estoque estoque = estoqueTest();
		estoques.add(estoque);
		ItemEstoque itemEstoque = itemEstoqueTest();
		Set<ItemEstoque> itensEstoque = new HashSet<>();
		itensEstoque.add(itemEstoque);
		estoque.setItemEstoque(itensEstoque);
		
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		List<ItemEstoqueTO> itensEstoqueTO = new ArrayList<>();
		itensEstoqueTO.add(itemEstoqueTO);
		
		Mockito.when(itemEstoqueFactory.getListItemEstoqueTO(itensEstoque)).thenReturn(itensEstoqueTO);
		
		List<EstoqueTO> estoquesTO = estoqueFactory.getListEstoqueTO(estoques);
		
		EstoqueTO estoqueTO = estoquesTO.get(0);
		
		assertEquals( estoqueTO.getCodEstoque(), estoque.getCodEstoque());
		assertEquals( estoqueTO.getNomeEstoque(), estoque.getNomeEstoque());
		assertEquals( estoqueTO.isPrincipal(), estoque.isPrincipal());
	}

}