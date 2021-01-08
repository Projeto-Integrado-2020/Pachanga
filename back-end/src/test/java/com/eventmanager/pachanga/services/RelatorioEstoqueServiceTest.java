package com.eventmanager.pachanga.services;

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
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Estoque;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.ItemEstoque;
import com.eventmanager.pachanga.domains.ItemEstoqueFluxo;
import com.eventmanager.pachanga.domains.Produto;
import com.eventmanager.pachanga.dtos.RelatorioEstoqueTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.RelatorioEstoqueTOFactory;
import com.eventmanager.pachanga.repositories.ItemEstoqueFluxoRepository;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RelatorioEstoqueService.class)
class RelatorioEstoqueServiceTest {

	@Autowired
	private RelatorioEstoqueService relatorioEstoqueService;

	@MockBean
	private ItemEstoqueFluxoRepository itemEstoqueFluxoRepository;

	@MockBean
	private RelatorioEstoqueTOFactory relatorioEstoqueTOFactory;

	@MockBean
	private RelatorioAreaSegurancaService relatorioAreaSegurancaService;

	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	public Estoque estoqueTest() {
		Set<ItemEstoque> itens = new HashSet<>();
		itens.add(itemEstoqueTest());
		Estoque estoque = new Estoque();
		estoque.setCodEstoque(1);
		estoque.setNomeEstoque("Estoque");
		estoque.setFesta(criacaoFesta());
		estoque.setPrincipal(false);
		estoque.setItemEstoque(itens);
		return estoque;
	}

	private ItemEstoque itemEstoqueTest() {
		ItemEstoque itemEstoque = new ItemEstoque();
		itemEstoque.setCodFesta(2); // o mesmo do festaTest()
		itemEstoque.setQuantidadeMax(100);
		itemEstoque.setPorcentagemMin(15);
		itemEstoque.setQuantidadeAtual(30);
		itemEstoque.setQuantPerda(0);
		itemEstoque.setProduto(produtoTest());
		return itemEstoque;
	}

	private Produto produtoTest() {
		Produto produto = new Produto();
		produto.setCodProduto(1);
		produto.setCodFesta(2); // o mesmo do festaTest()
		produto.setMarca("Cápsula");
		produto.setPrecoMedio(new BigDecimal("23.90"));
		produto.setDose(true);
		produto.setQuantDoses(15);
		return produto;
	}

	private Festa criacaoFesta() {
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

	private ItemEstoqueFluxo itemEstoqueFluxoTest() {
		ItemEstoqueFluxo itemFluxo = new ItemEstoqueFluxo();
		itemFluxo.setCodFesta(2); // o mesmo do festaTest()
		itemFluxo.setQuantidadeEstoque(30);
		itemFluxo.setQuantPerda(10);
		itemFluxo.setDataHorario(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		return itemFluxo;
	}

	@Test
	void relatorioConsumoItemEstoqueSucesso() throws Exception {

		List<Estoque> estoques = new ArrayList<>();
		estoques.add(estoqueTest());

		ItemEstoqueFluxo fluxo = itemEstoqueFluxoTest();
		fluxo.setDataHorario(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		fluxo.setQuantidadeEstoque(10);

		List<ItemEstoqueFluxo> itemFluxo = new ArrayList<>();
		itemFluxo.add(itemEstoqueFluxoTest());
		itemFluxo.add(fluxo);

		List<Integer[]> dadosItensFluxo = new ArrayList<>();
		dadosItensFluxo.add(new Integer[] { 1, 2 });

		Mockito.when(itemEstoqueFluxoRepository.getEstoqueProdutoFluxoFesta(Mockito.anyInt()))
				.thenReturn(dadosItensFluxo);
		Mockito.when(itemEstoqueFluxoRepository.getFluxoEstoqueProduto(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(itemFluxo);
		Mockito.when(relatorioEstoqueTOFactory.getRelatorioEstoque(Mockito.anyString(), Mockito.anyList()))
				.thenReturn(new RelatorioEstoqueTO());
		List<RelatorioEstoqueTO> relatorios = relatorioEstoqueService.relatoriosEstoque(1, 2, 1);

		assertEquals(true, !relatorios.isEmpty());
	}

	@Test
	void relatorioPerdaItemEstoqueSucesso() throws Exception {

		List<Estoque> estoques = new ArrayList<>();
		estoques.add(estoqueTest());

		ItemEstoqueFluxo fluxo = itemEstoqueFluxoTest();
		fluxo.setDataHorario(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		fluxo.setQuantidadeEstoque(10);

		List<ItemEstoqueFluxo> itemFluxo = new ArrayList<>();
		itemFluxo.add(itemEstoqueFluxoTest());
		itemFluxo.add(fluxo);

		List<Integer[]> dadosItensFluxo = new ArrayList<>();
		dadosItensFluxo.add(new Integer[] { 1, 2 });

		Mockito.when(itemEstoqueFluxoRepository.getEstoqueProdutoFluxoFesta(Mockito.anyInt()))
				.thenReturn(dadosItensFluxo);
		Mockito.when(itemEstoqueFluxoRepository.getFluxoEstoqueProduto(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(itemFluxo);
		Mockito.when(relatorioEstoqueTOFactory.getRelatorioEstoque(Mockito.anyString(), Mockito.anyList()))
				.thenReturn(new RelatorioEstoqueTO());
		List<RelatorioEstoqueTO> relatorios = relatorioEstoqueService.relatoriosEstoque(1, 2, 2);

		assertEquals(true, !relatorios.isEmpty());
	}

	@Test
	void relatorioQuantidadeItemEstoqueSucesso() throws Exception {

		List<Estoque> estoques = new ArrayList<>();
		estoques.add(estoqueTest());

		ItemEstoqueFluxo fluxo = itemEstoqueFluxoTest();
		fluxo.setDataHorario(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		fluxo.setQuantidadeEstoque(10);

		List<ItemEstoqueFluxo> itemFluxo = new ArrayList<>();
		itemFluxo.add(itemEstoqueFluxoTest());
		itemFluxo.add(fluxo);

		List<Integer[]> dadosItensFluxo = new ArrayList<>();
		dadosItensFluxo.add(new Integer[] { 1, 2 });

		Mockito.when(itemEstoqueFluxoRepository.getEstoqueProdutoFluxoFesta(Mockito.anyInt()))
				.thenReturn(dadosItensFluxo);
		Mockito.when(itemEstoqueFluxoRepository.getFluxoEstoqueProduto(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(itemFluxo);
		Mockito.when(relatorioEstoqueTOFactory.getRelatorioEstoque(Mockito.anyString(), Mockito.anyList()))
				.thenReturn(new RelatorioEstoqueTO());
		List<RelatorioEstoqueTO> relatorios = relatorioEstoqueService.relatoriosEstoque(1, 2, 3);

		assertEquals(true, !relatorios.isEmpty());
	}

	@Test
	void relatoriosEstoqueErro() throws Exception {

		List<Estoque> estoques = new ArrayList<>();
		estoques.add(estoqueTest());

		ItemEstoqueFluxo fluxo = itemEstoqueFluxoTest();
		fluxo.setDataHorario(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		fluxo.setQuantidadeEstoque(10);

		List<ItemEstoqueFluxo> itemFluxo = new ArrayList<>();
		itemFluxo.add(itemEstoqueFluxoTest());
		itemFluxo.add(fluxo);

		List<Integer[]> dadosItensFluxo = new ArrayList<>();
		dadosItensFluxo.add(new Integer[] { 1, 2 });

		Mockito.when(itemEstoqueFluxoRepository.getEstoqueProdutoFluxoFesta(Mockito.anyInt()))
				.thenReturn(dadosItensFluxo);
		Mockito.when(itemEstoqueFluxoRepository.getFluxoEstoqueProduto(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(itemFluxo);
		Mockito.when(relatorioEstoqueTOFactory.getRelatorioEstoque(Mockito.anyString(), Mockito.anyList()))
				.thenReturn(new RelatorioEstoqueTO());

		String erro = "";

		try {
			relatorioEstoqueService.relatoriosEstoque(1, 2, 4);
		} catch (ValidacaoException e) {
			erro = e.getMessage();
		}

		assertEquals(true, "CODRELIN".equals(erro));
	}

}
