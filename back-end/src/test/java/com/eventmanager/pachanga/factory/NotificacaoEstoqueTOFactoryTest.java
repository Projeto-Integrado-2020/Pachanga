package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.PachangaApplication;
import com.eventmanager.pachanga.domains.Estoque;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.ItemEstoque;
import com.eventmanager.pachanga.domains.Produto;
import com.eventmanager.pachanga.dtos.NotificacaoEstoqueTO;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = PachangaApplication.class)
@AutoConfigureMockMvc
class NotificacaoEstoqueTOFactoryTest {

	@Autowired
	private NotificacaoEstoqueTOFactory notificacaoEstoqueTOFactory;

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

	private Produto produtoTest() {
		Produto produto = new Produto();
		produto.setCodProduto(1);
		produto.setCodFesta(2); // o mesmo do festaTest()
		produto.setMarca("Cápsula");
		produto.setPrecoMedio(new BigDecimal("23.90"));
		produto.setDose(Boolean.TRUE);
		produto.setQuantDoses(10);
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

	@Test
	void getNotificacaoEstoqueTODoseTrueSucesso() throws Exception {
		ItemEstoque itemEstoque = itemEstoqueTest();
		Festa festa = festaTest();

		NotificacaoEstoqueTO notificacaoEstoqueTO = notificacaoEstoqueTOFactory.getNotificacaoEstoqueTO(itemEstoque,
				festa);

		assertEquals(notificacaoEstoqueTO.getNomeEstoque(), itemEstoque.getEstoque().getNomeEstoque());
		assertEquals(notificacaoEstoqueTO.getNomeFesta(), festa.getNomeFesta());
		assertEquals(notificacaoEstoqueTO.getNomeProduto(), itemEstoque.getProduto().getMarca());
	}
	
	@Test
	void getNotificacaoEstoqueTODoseFalseSucesso() throws Exception {
		ItemEstoque itemEstoque = itemEstoqueTest();
		Festa festa = festaTest();
		itemEstoque.getProduto().setDose(Boolean.FALSE);

		NotificacaoEstoqueTO notificacaoEstoqueTO = notificacaoEstoqueTOFactory.getNotificacaoEstoqueTO(itemEstoque,
				festa);

		assertEquals(notificacaoEstoqueTO.getNomeEstoque(), itemEstoque.getEstoque().getNomeEstoque());
		assertEquals(notificacaoEstoqueTO.getNomeFesta(), festa.getNomeFesta());
		assertEquals(notificacaoEstoqueTO.getNomeProduto(), itemEstoque.getProduto().getMarca());
	}
}
