package com.eventmanager.pachanga.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
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
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.ItemEstoque;
import com.eventmanager.pachanga.domains.Produto;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.ItemEstoqueTO;
import com.eventmanager.pachanga.dtos.NotificacaoEstoqueTO;
import com.eventmanager.pachanga.dtos.ProdutoTO;
import com.eventmanager.pachanga.factory.ItemEstoqueFactory;
import com.eventmanager.pachanga.factory.NotificacaoEstoqueTOFactory;
import com.eventmanager.pachanga.factory.ProdutoFactory;
import com.eventmanager.pachanga.repositories.EstoqueRepository;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.ItemEstoqueFluxoRepository;
import com.eventmanager.pachanga.repositories.ItemEstoqueRepository;
import com.eventmanager.pachanga.repositories.ProdutoRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.tipo.TipoPermissao;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProdutoService.class)
class ProdutoServiceTest {

	@Autowired
	private ProdutoService produtoService;

	@MockBean
	private ProdutoRepository produtoRepository;

	@MockBean
	private FestaRepository festaRepository;

	@MockBean
	private EstoqueRepository estoqueRepository;

	@MockBean
	private GrupoRepository grupoRepository;

	@MockBean
	private UsuarioRepository usuarioRepository;

	@MockBean
	private ProdutoFactory produtoFactory;

	@MockBean
	private ItemEstoqueFactory itemEstoqueFactory;

	@MockBean
	private ItemEstoqueRepository itemEstoqueRepository;

	@MockBean
	private NotificacaoService notificacaoService;

	@MockBean
	private NotificacaoEstoqueTOFactory notificacaoEstoqueTOFactory;

	@MockBean
	private FestaService festaService;

	@MockBean
	private ItemEstoqueFluxoRepository itemEstoqueFluxoRepository;

	// metodos
	// auxiliare________________________________________________________________________________________
	private ProdutoTO produtoTOTest() {
		ProdutoTO produtoTO = new ProdutoTO();
		produtoTO.setCodProduto(1);
		produtoTO.setCodFesta(2); // o mesmo do festaTest()
		produtoTO.setMarca("Cápsula");
		produtoTO.setPrecoMedio(new BigDecimal("23.90"));
		produtoTO.setDose(true);
		produtoTO.setQuantDoses(14);
		return produtoTO;

	}

	private List<Grupo> criacaoGrupos() {
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupoOrganizadorTest());
		return grupos;
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

	private Estoque estoqueTest() throws Exception {
		Estoque estoque = new Estoque();
		estoque.setCodEstoque(1);
		estoque.setFesta(festaTest());
		estoque.setNomeEstoque("Bar do Zé");
		estoque.setPrincipal(true);

		return estoque;
	}

	private ItemEstoque itemEstoqueTest() {
		ItemEstoque itemEstoque = new ItemEstoque();
		itemEstoque.setCodFesta(2); // o mesmo do festaTest()
		itemEstoque.setQuantidadeMax(100);
		itemEstoque.setPorcentagemMin(15);
		itemEstoque.setQuantidadeAtual(30);
		return itemEstoque;
	}

	private ItemEstoqueTO itemEstoqueTOTest() {
		ItemEstoqueTO itemEstoqueTO = new ItemEstoqueTO();
		itemEstoqueTO.setCodEstoque(1);
		itemEstoqueTO.setCodFesta(2); // o mesmo do festaTest()
		itemEstoqueTO.setCodProduto(1);
		itemEstoqueTO.setQuantidadeMax(100);
		itemEstoqueTO.setPorcentagemMin(15);
		return itemEstoqueTO;
	}

	private Festa festaTest() throws Exception {
		Festa festaTest = new Festa();

		festaTest.setCodFesta(2);
		festaTest.setCodEnderecoFesta("https//:minhacasa.org");
		festaTest.setDescOrganizador("sou demente");
		festaTest.setHorarioInicioFesta(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		festaTest.setHorarioFimFesta(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		festaTest.setNomeFesta("festao");
		festaTest.setOrganizador("Joao Neves");
		festaTest.setStatusFesta("P");
		festaTest.setDescricaoFesta("Bugago");
		festaTest.setHorarioFimFestaReal(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));

		return festaTest;
	}

	private Grupo grupoOrganizadorTest() {
		Grupo grupo = new Grupo();
		grupo.setCodGrupo(1);
		grupo.setNomeGrupo("ORGANIZADOR");
		Set<Usuario> usuarios = new HashSet<>();
		grupo.setUsuarios(usuarios);
		grupo.setOrganizador(true);
		return grupo;
	}

	@SuppressWarnings("deprecation")
	public static Usuario usuarioTest() throws Exception {
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha(
				"fc68b677646b5f018d1762e9a19bf65180d9aab2794794340ade50e0d78a239affd43a613e7136a61b5d63b09f072c0c039dea4281873abe826d6e6285d9cefef0a0d868d3b0b0d4582ec787b473b4e0");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setSexo("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");

		return usuarioTest;
	}

	public Grupo grupoTest() {
		Grupo grupo = new Grupo();
		grupo.setCodGrupo(1);
		grupo.setNomeGrupo("CONVIDADO");
		return grupo;
	}

	public NotificacaoEstoqueTO notificacaoEstoqueToTest() {
		NotificacaoEstoqueTO notificacaoEstoqueTo = new NotificacaoEstoqueTO();
		notificacaoEstoqueTo.setNomeEstoque("teste");
		return notificacaoEstoqueTo;
	}

	// addProduto_______________________________________________________________________________________

	@Test
	void addProdutoSucessoTest() throws Exception {
		Festa festa = festaTest();
		ProdutoTO produtoTO = produtoTOTest();
		int codProduto = 1;
		int codFesta = festa.getCodFesta();

		Mockito.when(produtoRepository.getNextValMySequence()).thenReturn(codProduto);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(produtoRepository.save(Mockito.any(Produto.class))).thenReturn(null);
		Mockito.when(produtoRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());
		Mockito.when(produtoFactory.getProduto(Mockito.any())).thenReturn(produtoTest());

		Produto produto = produtoService.addProduto(produtoTO, codFesta, 1);

		assertEquals(produto.getCodProduto(), codProduto);
		assertEquals(produto.getCodFesta(), codFesta);
		assertEquals(produto.getMarca(), produtoTO.getMarca());
		assertEquals(produto.getPrecoMedio(), produtoTO.getPrecoMedio());
	}

	@Test
	void addProdutoErroQuantidadeDosesErradaTest() throws Exception {
		Festa festa = festaTest();
		ProdutoTO produtoTO = produtoTOTest();
		int codProduto = 1;
		int codFesta = festa.getCodFesta();
		produtoTO.setQuantDoses(0);

		Mockito.when(produtoRepository.getNextValMySequence()).thenReturn(codProduto);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(produtoRepository.save(Mockito.any(Produto.class))).thenReturn(null);
		Mockito.when(produtoRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());
		Mockito.when(produtoFactory.getProduto(Mockito.any())).thenReturn(produtoTest());

		boolean erroException = false;
		try {
			produtoService.addProduto(produtoTO, codFesta, 1);
		} catch (Exception e) {
			erroException = true;
		}

		assertEquals(true, erroException);
	}

	@Test
	void addProdutoMesmoNomeTest() throws Exception {
		Festa festa = festaTest();
		ProdutoTO produtoTO = produtoTOTest();
		int codProduto = 1;
		int codFesta = festa.getCodFesta();

		Mockito.when(produtoRepository.getNextValMySequence()).thenReturn(codProduto);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(produtoRepository.save(Mockito.any(Produto.class))).thenReturn(null);
		Mockito.when(produtoRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());
		Mockito.when(produtoFactory.getProduto(Mockito.any())).thenReturn(produtoTest());
		Mockito.when(produtoRepository.findByMarca(Mockito.anyString(), Mockito.anyInt())).thenReturn(produtoTest());

		boolean erroException = false;
		try {
			produtoService.addProduto(produtoTO, codFesta, 1);
		} catch (Exception e) {
			erroException = true;
		}

		assertEquals(true, erroException);
	}

	@Test
	void addProdutoProdutoJaCadastradoTest() throws Exception {
		Festa festa = festaTest();
		ProdutoTO produtoTO = produtoTOTest();
		int codProduto = 1;
		int codFesta = festa.getCodFesta();

		Mockito.when(produtoRepository.getNextValMySequence()).thenReturn(codProduto);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(produtoRepository.save(Mockito.any(Produto.class))).thenReturn(null);
		Mockito.when(produtoRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(produtoFactory.getProduto(Mockito.any())).thenReturn(produtoTest());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());

		Produto produto = produtoService.addProduto(produtoTO, codFesta, 1);

		assertEquals(produto.getCodProduto(), codProduto);
		assertEquals(produto.getCodFesta(), codFesta);
		assertEquals(produto.getMarca(), produtoTO.getMarca());
		assertEquals(produto.getPrecoMedio(), produtoTO.getPrecoMedio());
	}

	// addProdutoEstoque_________________________________________________________________________________________
	@Test
	void addProdutoEstoqueSucessoTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = produto.getCodFesta();
		int quantidadeMax = itemEstoqueTO.getQuantidadeMax();
		int porcentagemMin = itemEstoqueTO.getPorcentagemMin();

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(null);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(itemEstoqueFactory.getItemEstoque(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(itemEstoqueTest());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());

		ItemEstoque retorno = produtoService.addProdutoEstoque(itemEstoqueTO, codProduto, codEstoque);

		assertEquals(retorno.getCodFesta(), codFesta);
		assertEquals(retorno.getQuantidadeMax(), quantidadeMax);
		assertEquals(retorno.getPorcentagemMin(), porcentagemMin);
	}

	@Test
	void addProdutoEstoquePercentagemInvalidaTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = produto.getCodFesta();
		itemEstoqueTO.setPorcentagemMin(-1);

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(null);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(itemEstoqueFactory.getItemEstoque(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(itemEstoqueTest());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());

		boolean erroException = false;
		try {
			produtoService.addProdutoEstoque(itemEstoqueTO, codProduto, codEstoque);
		} catch (Exception e) {
			erroException = true;
		}

		assertEquals(true, erroException);
	}

	@Test
	void addProdutoEstoqueQuantidadeMaxZero() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = produto.getCodFesta();
		itemEstoqueTO.setQuantidadeMax(0);

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(null);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(itemEstoqueFactory.getItemEstoque(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(itemEstoqueTest());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());

		boolean erroException = false;
		try {
			produtoService.addProdutoEstoque(itemEstoqueTO, codProduto, codEstoque);
		} catch (Exception e) {
			erroException = true;
		}

		assertEquals(true, erroException);
	}

	@Test
	void addProdutoEstoqueQuantidadeAtualMenorZero() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = produto.getCodFesta();
		itemEstoqueTO.setQuantidadeAtual(-1);

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(null);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(itemEstoqueFactory.getItemEstoque(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(itemEstoqueTest());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());

		boolean erroException = false;
		try {
			produtoService.addProdutoEstoque(itemEstoqueTO, codProduto, codEstoque);
		} catch (Exception e) {
			erroException = true;
		}

		assertEquals(true, erroException);
	}

	@Test
	void addProdutoEstoqueJaCadastradoTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = produto.getCodFesta();

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoqueTest());
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(itemEstoqueFactory.getItemEstoque(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(itemEstoqueTest());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());

		boolean erroException = false;
		try {
			produtoService.addProdutoEstoque(itemEstoqueTO, codProduto, codEstoque);
		} catch (Exception e) {
			erroException = true;
		}

		assertEquals(true, erroException);
	}

	@Test
	void addProdutoEstoqueCodFestaDiferenteTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = produto.getCodFesta();
		estoque.setFesta(festa);
		produto.setCodFesta(2001);

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(null);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(itemEstoqueFactory.getItemEstoque(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(itemEstoqueTest());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());

		boolean erroException = false;
		try {
			produtoService.addProdutoEstoque(itemEstoqueTO, codProduto, codEstoque);

		} catch (Exception e) {
			erroException = true;
		}

		assertEquals(true, erroException);
	}

	@Test
	void addProdutoEstoqueNaoEncontradoTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = produto.getCodFesta();
		estoque.setFesta(festa);
		produto.setCodFesta(2001);

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(null);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(null);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(itemEstoqueFactory.getItemEstoque(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(itemEstoqueTest());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());

		boolean erroException = false;
		try {
			produtoService.addProdutoEstoque(itemEstoqueTO, codProduto, codEstoque);

		} catch (Exception e) {
			erroException = true;
		}

		assertEquals(true, erroException);
	}

	@Test
	void addProdutoEstoqueFestaNaoExistenteTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = produto.getCodFesta();

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(null);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(null);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(itemEstoqueFactory.getItemEstoque(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(itemEstoqueTest());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());

		boolean erroException = false;
		try {
			produtoService.addProdutoEstoque(itemEstoqueTO, codProduto, codEstoque);
		} catch (Exception e) {
			erroException = true;
		}

		assertEquals(true, erroException);
	}

	@Test
	void addProdutoEstoqueFestaInvalidaTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		produto.setCodFesta(22);

		Estoque estoque = estoqueTest();
		estoque.getFesta().setCodFesta(333);

		Festa festa = festaTest();
		festa.setCodFesta(333);

		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = produto.getCodFesta();

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);

		boolean erroException = false;
		try {
			produtoService.addProdutoEstoque(itemEstoqueTO, codProduto, codEstoque);
		} catch (Exception e) {
			erroException = true;
		}

		assertEquals(true, erroException);
	}

	@Test
	void addProdutoEstoqueProdutoJaNoEstoqueTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = produto.getCodFesta();

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);

		boolean erroException = false;
		try {
			produtoService.addProdutoEstoque(itemEstoqueTO, codProduto, codEstoque);
		} catch (Exception e) {
			erroException = true;
		}

		assertEquals(true, erroException);
	}

	@Test
	void addProdutoEstoqueQuantidadeMaxInvalidTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		itemEstoqueTO.setQuantidadeMax(-20);
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = produto.getCodFesta();

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(null);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);

		boolean erroException = false;
		try {
			produtoService.addProdutoEstoque(itemEstoqueTO, codProduto, codEstoque);
		} catch (Exception e) {
			erroException = true;
		}

		assertEquals(true, erroException);
	}

	@Test
	void addProdutoEstoqueQuantidadeMaxPequenaInvalidTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		itemEstoqueTO.setQuantidadeMax(100);
		itemEstoqueTO.setQuantidadeAtual(200);
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = produto.getCodFesta();

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(itemEstoqueRepository.findItemEstoque(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);

		boolean erroException = false;
		try {
			produtoService.addProdutoEstoque(itemEstoqueTO, codProduto, codEstoque);
		} catch (Exception e) {
			erroException = true;
		}

		assertEquals(true, erroException);
	}

	@Test
	void addProdutoEstoquePorcentagemMinTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		itemEstoqueTO.setPorcentagemMin(-10);
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = produto.getCodFesta();

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(itemEstoqueRepository.findItemEstoque(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);

		boolean erroException = false;
		try {
			produtoService.addProdutoEstoque(itemEstoqueTO, codProduto, codEstoque);
		} catch (Exception e) {
			erroException = true;
		}

		assertEquals(true, erroException);
	}

	@Test
	void addProdutoEstoqueQuantidadeAtualTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		itemEstoqueTO.setQuantidadeAtual(-2);
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = produto.getCodFesta();

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(itemEstoqueRepository.findItemEstoque(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);

		boolean erroException = false;
		try {
			produtoService.addProdutoEstoque(itemEstoqueTO, codProduto, codEstoque);
		} catch (Exception e) {
			erroException = true;
		}

		assertEquals(true, erroException);
	}

	// removerProduto____________________________________________________________________________________________
	@Test
	void removerProdutoSucessoTest() throws Exception {
		Produto produto = produtoTest();
		int codProduto = produto.getCodProduto();

		List<Produto> produtos = new ArrayList<>();

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(produtoRepository.findEstoquesComProduto(Mockito.anyInt())).thenReturn(produtos);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());
		doNothing().when(produtoRepository).delete(produto);

		produtoService.removerProduto(codProduto, 2, 1);
	}

	@Test
	void removerProdutoNaoEncontradoTest() throws Exception {
		Produto produto = produtoTest();
		int codProduto = produto.getCodProduto();

		List<Produto> produtos = new ArrayList<>();

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(null);
		Mockito.when(produtoRepository.findEstoquesComProduto(Mockito.anyInt())).thenReturn(produtos);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());
		doNothing().when(produtoRepository).delete(produto);

		boolean erro = false;
		try {
			produtoService.removerProduto(codProduto, 2, 1);
		} catch (Exception e) {
			erro = true;

		}

		assertEquals(true, erro);
	}

	@Test
	void removerProdutoEmUsoExceptionTest() throws Exception {
		Produto produto = produtoTest();
		int codProduto = produto.getCodProduto();

		List<Produto> produtos = new ArrayList<>();
		produtos.add(produto);

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(produtoRepository.findEstoquesComProduto(Mockito.anyInt())).thenReturn(produtos);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());
		doNothing().when(produtoRepository).delete(produto);

		boolean erro = false;
		try {
			produtoService.removerProduto(codProduto, 2, 1);
		} catch (Exception e) {
			erro = true;

		}

		assertEquals(true, erro);
	}

	@Test
	void removerProdutoErroUsuarioSemPermissaoTest() throws Exception {
		Produto produto = produtoTest();
		int codProduto = produto.getCodProduto();

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(produtoRepository.findEstoquesComProduto(codProduto)).thenReturn(null);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(null);
		doNothing().when(produtoRepository).delete(produto);

		boolean erro = false;
		try {
			produtoService.removerProduto(codProduto, 2, 1);
		} catch (Exception e) {
			erro = true;

		}

		assertEquals(true, erro);
	}

	@Test
	void removerProdutoEmUsoTest() throws Exception {
		Produto produto = produtoTest();
		int codProduto = produto.getCodProduto();
		List<Estoque> estoques = new ArrayList<>();
		estoques.add(estoqueTest());
		List<Produto> produtos = new ArrayList<>();

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(produtoRepository.findEstoquesComProduto(codProduto)).thenReturn(produtos);
		doNothing().when(produtoRepository).delete(produto);

		boolean erro = false;
		try {
			produtoService.removerProduto(codProduto, 2, 1);
		} catch (Exception e) {
			erro = true;

		}

		assertEquals(true, erro);
	}

	// removerProdutoEstoque______________________________________________________________________________________
	@Test
	void removerProdutoEstoqueSucessoTest() throws Exception {
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());
		doNothing().when(produtoRepository).deleteProdutoEstoque(codProduto, codEstoque);

		produtoService.removerProdutoEstoque(codProduto, codEstoque, 2);

	}

	@Test
	void removerProdutoEstoqueNaoPossuiProdutoTest() throws Exception {
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(null);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());
		doNothing().when(produtoRepository).deleteProdutoEstoque(codProduto, codEstoque);

		boolean erro = false;
		try {
			produtoService.removerProdutoEstoque(codProduto, codEstoque, 2);
		} catch (Exception e) {
			erro = true;
		}

		assertEquals(true, erro);
	}

	// editarProduto_______________________________________________________________________________________________
	@Test
	void editarProdutoSucessoTest() throws Exception {
		ProdutoTO produtoTO = produtoTOTest();
		produtoTO.setMarca("ItubainexNotPlagio");
		produtoTO.setPrecoMedio(new BigDecimal("73.90"));
		Produto produto = produtoTest();
		int codProduto = produtoTO.getCodProduto();

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(produtoRepository.save(Mockito.any(Produto.class))).thenReturn(null);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());

		Produto retorno = produtoService.editarProduto(produtoTO, 1);

		assertEquals(retorno.getCodProduto(), produtoTO.getCodProduto());
		assertEquals(retorno.getCodFesta(), produtoTO.getCodFesta());
		assertEquals(retorno.getMarca(), produtoTO.getMarca());
		assertEquals(retorno.getPrecoMedio(), produtoTO.getPrecoMedio());
	}

	@Test
	void editarProdutoSucessoMudancaSemDoseTest() throws Exception {
		ProdutoTO produtoTO = produtoTOTest();
		produtoTO.setMarca("ItubainexNotPlagio");
		produtoTO.setPrecoMedio(new BigDecimal("73.90"));
		Produto produto = produtoTest();
		int codProduto = produtoTO.getCodProduto();
		produtoTO.setDose(false);
		List<ItemEstoque> itensEstoque = new ArrayList<>();
		itensEstoque.add(itemEstoqueTest());

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(produtoRepository.save(Mockito.any(Produto.class))).thenReturn(null);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());
		Mockito.when(itemEstoqueRepository.findItensEstoqueByCodProduto(Mockito.anyInt())).thenReturn(itensEstoque);

		Produto retorno = produtoService.editarProduto(produtoTO, 1);

		assertEquals(retorno.getCodProduto(), produtoTO.getCodProduto());
		assertEquals(retorno.getCodFesta(), produtoTO.getCodFesta());
		assertEquals(retorno.getMarca(), produtoTO.getMarca());
		assertEquals(retorno.getPrecoMedio(), produtoTO.getPrecoMedio());
	}

	@Test
	void editarProdutoSucessoMudancaParaDoseTest() throws Exception {
		ProdutoTO produtoTO = produtoTOTest();
		produtoTO.setMarca("ItubainexNotPlagio");
		produtoTO.setPrecoMedio(new BigDecimal("73.90"));
		Produto produto = produtoTest();
		int codProduto = produtoTO.getCodProduto();
		produto.setDose(false);
		produtoTO.setDose(true);
		List<ItemEstoque> itensEstoque = new ArrayList<>();
		itensEstoque.add(itemEstoqueTest());

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(produtoRepository.save(Mockito.any(Produto.class))).thenReturn(null);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());
		Mockito.when(itemEstoqueRepository.findItensEstoqueByCodProduto(Mockito.anyInt())).thenReturn(itensEstoque);

		Produto retorno = produtoService.editarProduto(produtoTO, 1);

		assertEquals(retorno.getCodProduto(), produtoTO.getCodProduto());
		assertEquals(retorno.getCodFesta(), produtoTO.getCodFesta());
		assertEquals(retorno.getMarca(), produtoTO.getMarca());
		assertEquals(retorno.getPrecoMedio(), produtoTO.getPrecoMedio());
	}

	// editarProdutoEstoque________________________________________________________________________________________
	@Test
	void editarProdutoEstoqueSucessoTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		itemEstoqueTO.setQuantidadeMax(200);
		itemEstoqueTO.setQuantidadeAtual(10);
		itemEstoqueTO.setPorcentagemMin(20);

		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = festa.getCodFesta();
		int quantidadeMax = itemEstoqueTO.getQuantidadeMax();
		int porcentagemMin = itemEstoqueTO.getPorcentagemMin();

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());

		ItemEstoque retorno = produtoService.editarProdutoEstoque(itemEstoqueTO, 1);

		assertEquals(retorno.getCodFesta(), codFesta);
		assertEquals(retorno.getQuantidadeMax(), quantidadeMax);
		assertEquals(retorno.getPorcentagemMin(), porcentagemMin);
	}

	@Test
	void editarProdutoEstoqueQuantidadeMaxTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		itemEstoqueTO.setQuantidadeMax(-200);
		itemEstoqueTO.setQuantidadeAtual(100);
		itemEstoqueTO.setPorcentagemMin(10);

		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		Mockito.when(produtoRepository.save(Mockito.any())).thenReturn(null);

		boolean erro = false;
		try {
			produtoService.editarProdutoEstoque(itemEstoqueTO, 1);
		} catch (Exception e) {
			erro = true;
		}

		assertEquals(true, erro);
	}

	@Test
	void editarProdutoEstoqueQuantidadeMaxPequenaTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		itemEstoqueTO.setQuantidadeMax(200);
		itemEstoqueTO.setQuantidadeAtual(300);
		itemEstoqueTO.setPorcentagemMin(10);

		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);

		boolean erro = false;
		try {
			produtoService.editarProdutoEstoque(itemEstoqueTO, 1);
		} catch (Exception e) {
			erro = true;
		}

		assertEquals(true, erro);
	}

	@Test
	void editarProdutoEstoquePorcentagemMinTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		itemEstoqueTO.setQuantidadeMax(200);
		itemEstoqueTO.setQuantidadeAtual(100);
		itemEstoqueTO.setPorcentagemMin(-10);

		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);

		boolean erro = false;
		try {
			produtoService.editarProdutoEstoque(itemEstoqueTO, 1);
		} catch (Exception e) {
			erro = true;
		}

		assertEquals(true, erro);
	}

	@Test
	void editarProdutoEstoqueQuantidadeAtualTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		itemEstoqueTO.setQuantidadeMax(200);
		itemEstoqueTO.setQuantidadeAtual(-100);
		itemEstoqueTO.setPorcentagemMin(10);

		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);

		boolean erro = false;
		try {
			produtoService.editarProdutoEstoque(itemEstoqueTO, 1);
		} catch (Exception e) {
			erro = true;
		}

		assertEquals(true, erro);
	}

	// baixa_______________________________________________________________________________________________________

	@Test

	void baixaProdutoSucessoTest() throws Exception {
		Usuario usuario = usuarioTest();
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codUsuario = usuario.getCodUsuario();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = festa.getCodFesta();
		int quantidade = 3;
		itemEstoque.setQuantidadeAtual(10);
		itemEstoque.setProduto(produto);
		itemEstoque.setEstoque(estoque);

		// notificacaoGrupoRepository.findNotificacaoGrupo(codGrupo, codNotificacao)

		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);

		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.any(Integer.class), Mockito.anyString()))
				.thenReturn(true);
		Mockito.when(
				grupoRepository.findGrupoPermissaoUsuario(codFesta, codUsuario, TipoPermissao.EDIMESTO.getCodigo()))
				.thenReturn(criacaoGrupos());
		Mockito.when(grupoRepository.findGruposPermissaoEstoque(Mockito.anyInt())).thenReturn(criacaoGrupos());

		ItemEstoque retorno = produtoService.baixaProduto(codProduto, codEstoque, quantidade, codUsuario, true);

		assertEquals(retorno.getCodFesta(), codFesta);
	}

	@Test
	void baixaProdutoQuantidadeMaximaTest() throws Exception {
		Usuario usuario = usuarioTest();
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codUsuario = usuario.getCodUsuario();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = festa.getCodFesta();
		int quantidade = 3;
		itemEstoque.setQuantidadeAtual(200);

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.any(Integer.class), Mockito.anyString()))
				.thenReturn(false);
		Mockito.when(
				grupoRepository.findGrupoPermissaoUsuario(codFesta, codUsuario, TipoPermissao.EDIMESTO.getCodigo()));

		boolean erro = false;
		try {
			produtoService.baixaProduto(codProduto, codEstoque, quantidade, codUsuario, false);
		} catch (Exception e) {
			erro = true;
		}
		assertEquals(true, erro);
	}

	@Test
	void baixaProdutoQuantidadeInvalidaTest() throws Exception {
		Usuario usuario = usuarioTest();
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codFesta = festa.getCodFesta();
		int codUsuario = usuario.getCodUsuario();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int quantidade = 3;

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.any(Integer.class), Mockito.anyString()))
				.thenReturn(false);
		Mockito.when(
				grupoRepository.findGrupoPermissaoUsuario(codFesta, codUsuario, TipoPermissao.EDIMESTO.getCodigo()));

		boolean erro = false;
		try {
			produtoService.baixaProduto(codProduto, codEstoque, quantidade, codUsuario, false);
		} catch (Exception e) {
			erro = true;
		}
		assertEquals(true, erro);
	}

	@Test
	void baixaProdutoValorNegativoTest() throws Exception {
		Usuario usuario = usuarioTest();
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codFesta = festa.getCodFesta();
		int codUsuario = usuario.getCodUsuario();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int quantidade = -300;

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.any(Integer.class), Mockito.anyString()))
				.thenReturn(false);
		Mockito.when(
				grupoRepository.findGrupoPermissaoUsuario(codFesta, codUsuario, TipoPermissao.EDIMESTO.getCodigo()));

		boolean erro = false;
		try {
			produtoService.baixaProduto(codProduto, codEstoque, quantidade, codUsuario, false);
		} catch (Exception e) {
			erro = true;
		}

		assertEquals(true, erro);
	}

	@Test
	void baixaProdutoBaixaExcessivaTest() throws Exception { // retirar mais do que tem no estoque
		ItemEstoque itemEstoque = itemEstoqueTest();
		Usuario usuario = usuarioTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codFesta = festa.getCodFesta();
		int codUsuario = usuario.getCodUsuario();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int quantidade = 300;

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.any(Integer.class), Mockito.anyString()))
				.thenReturn(false);
		Mockito.when(
				grupoRepository.findGrupoPermissaoUsuario(codFesta, codUsuario, TipoPermissao.EDIMESTO.getCodigo()));

		boolean erro = false;
		try {
			produtoService.baixaProduto(codProduto, codEstoque, quantidade, codUsuario, false);
		} catch (Exception e) {
			erro = true;
		}

		assertEquals(true, erro);
	}

	@Test
	void baixaProdutoEstoqueBaixoTest() throws Exception {
		Grupo grupo = grupoTest();
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		Usuario usuario = usuarioTest();
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		estoque.setFesta(festa);
		int codUsuario = usuario.getCodUsuario();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = festa.getCodFesta();
		int quantidade = 3;
		itemEstoque.setQuantidadeAtual(10);
		itemEstoque.setEstoque(estoque);
		itemEstoque.setProduto(produto);

		// notificacaoGrupoRepository.findNotificacaoGrupo(codGrupo, codNotificacao)

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(
				grupoRepository.findGrupoPermissaoUsuario(codFesta, codUsuario, TipoPermissao.EDIMESTO.getCodigo()))
				.thenReturn(criacaoGrupos());
		Mockito.when(grupoRepository.findGruposPermissaoEstoque(codFesta)).thenReturn(grupos);
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.any(Integer.class), Mockito.anyString()))
				.thenReturn(false);

		ItemEstoque retorno = produtoService.baixaProduto(codProduto, codEstoque, quantidade, codUsuario, false);

		assertEquals(retorno.getCodFesta(), codFesta);
	}

	@Test
	void baixaProdutoEstoqueBaixoDNVTest() throws Exception {
		Grupo grupo = grupoTest();
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		Usuario usuario = usuarioTest();
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		estoque.setFesta(festa);
		int codUsuario = usuario.getCodUsuario();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = festa.getCodFesta();
		int quantidade = 3;
		itemEstoque.setQuantidadeAtual(10);
		itemEstoque.setEstoque(estoque);
		itemEstoque.setProduto(produto);

		// notificacaoGrupoRepository.findNotificacaoGrupo(codGrupo, codNotificacao)

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(
				grupoRepository.findGrupoPermissaoUsuario(codFesta, codUsuario, TipoPermissao.EDIMESTO.getCodigo()))
				.thenReturn(criacaoGrupos());
		Mockito.when(grupoRepository.findGruposPermissaoEstoque(codFesta)).thenReturn(grupos);
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.any(Integer.class), Mockito.anyString()))
				.thenReturn(false);

		ItemEstoque retorno = produtoService.baixaProduto(codProduto, codEstoque, quantidade, codUsuario, false);

		assertEquals(retorno.getCodFesta(), codFesta);
	}

	// recarga_____________________________________________________________________________________________________
	@Test
	void recargaProdutoSucessoTest() throws Exception {
		Usuario usuario = usuarioTest();
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codUsuario = usuario.getCodUsuario();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = festa.getCodFesta();
		int quantidade = 3;
		itemEstoque.setProduto(produto);
		itemEstoque.setEstoque(estoque);
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupoTest());
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);

		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		Mockito.when(produtoRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(grupoRepository.findGruposPermissaoEstoque(codFesta)).thenReturn(grupos);
		Mockito.when(
				grupoRepository.findGrupoPermissaoUsuario(codFesta, codUsuario, TipoPermissao.EDIMESTO.getCodigo()))
				.thenReturn(criacaoGrupos());
		Mockito.when(notificacaoService.verificarNotificacaoGrupo(Mockito.any(Integer.class), Mockito.anyString()))
				.thenReturn(false);

		ItemEstoque retorno = produtoService.recargaProduto(codProduto, codEstoque, quantidade, codUsuario);

		assertEquals(retorno.getCodFesta(), codFesta);
	}

	@Test
	void recargaProdutoValorNegativoTest() throws Exception {
		Usuario usuario = usuarioTest();
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codUsuario = usuario.getCodUsuario();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = festa.getCodFesta();
		int quantidade = -3;

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		Mockito.when(produtoRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(
				grupoRepository.findGrupoPermissaoUsuario(codFesta, codUsuario, TipoPermissao.EDIMESTO.getCodigo()))
				.thenReturn(criacaoGrupos());

		boolean erro = false;
		try {
			produtoService.recargaProduto(codProduto, codEstoque, quantidade, codUsuario);
		} catch (Exception e) {
			erro = true;
		}

		assertEquals(true, erro);
	}

	@Test
	void recargaProdutoExcessivaTest() throws Exception { // adicionar mais do que o estoque comporta
		ItemEstoque itemEstoque = itemEstoqueTest();
		Usuario usuario = usuarioTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codUsuario = usuario.getCodUsuario();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int quantidade = 300;
		int codFesta = festa.getCodFesta();

		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(itemEstoqueRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		Mockito.when(itemEstoqueRepository.save(Mockito.any())).thenReturn(null);
		Mockito.when(
				grupoRepository.findGrupoPermissaoUsuario(codFesta, codUsuario, TipoPermissao.EDIMESTO.getCodigo()))
				.thenReturn(criacaoGrupos());

		boolean erro = false;
		try {
			produtoService.recargaProduto(codProduto, codEstoque, quantidade, codUsuario);
		} catch (Exception e) {
			erro = true;
		}

		assertEquals(true, erro);
	}

	@Test
	void getListaProdutos() throws Exception {
		List<Produto> produtos = new ArrayList<>();
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());
		Mockito.when(produtoRepository.findAll()).thenReturn(produtos);

		produtoService.listaProduto(1, 1);
	}

	@Test
	void getProduto() throws Exception {
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(criacaoGrupos());
		Mockito.when(produtoRepository.findById(Mockito.anyInt())).thenReturn(produtoTest());

		produtoService.getProduto(1, 1, 2);
	}

	@Test
	void getInfoEstoqueProdutoTest() throws Exception {

		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(produtoRepository.findById(Mockito.anyInt())).thenReturn(produtoTest());
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(Mockito.anyInt())).thenReturn(estoqueTest());
		Mockito.when(itemEstoqueRepository.findItemEstoque(Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(itemEstoqueTest());
		Mockito.when(notificacaoEstoqueTOFactory.getNotificacaoEstoqueTO(Mockito.any(), Mockito.any()))
				.thenReturn(notificacaoEstoqueToTest());

		NotificacaoEstoqueTO notificacaoEstoque = produtoService.getInfoEstoqueProduto(1, 2, 3);
		assertEquals(true, "teste".equals(notificacaoEstoque.getNomeEstoque()));
	}

}
