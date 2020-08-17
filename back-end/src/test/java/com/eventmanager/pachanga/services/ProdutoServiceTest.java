package com.eventmanager.pachanga.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.eventmanager.pachanga.dtos.ProdutoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.repositories.EstoqueRepository;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.ProdutoRepository;
import com.eventmanager.pachanga.tipo.TipoPermissao;

@RunWith(SpringRunner.class)
@WebMvcTest(value=ProdutoService.class)
public class ProdutoServiceTest {
	
	@MockBean
	private FestaRepository festaRepository;
	
	@MockBean
	private EstoqueRepository estoqueRepository;
	
	@MockBean
	private GrupoRepository grupoRepository;

	@MockBean
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
//metodos auxiliare________________________________________________________________________________________	
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
		itemEstoque.setCodEstoque(1);
		itemEstoque.setCodFesta(2); //o mesmo do festaTest() 
		itemEstoque.setCodProduto(1);
		itemEstoque.setQuantidadeMax(100);
		itemEstoque.setQuantiadadeAtual(23);
		itemEstoque.setPorcentagemMin(15);
		return itemEstoque;
	}
	
	private ItemEstoqueTO itemEstoqueTOTest() {
		ItemEstoqueTO itemEstoqueTO = new ItemEstoqueTO();
		itemEstoqueTO.setCodEstoque(1);
		itemEstoqueTO.setCodFesta(2); //o mesmo do festaTest() 
		itemEstoqueTO.setCodProduto(1);
		itemEstoqueTO.setQuantidadeMax(100);
		itemEstoqueTO.setQuantiadadeAtual(23);
		itemEstoqueTO.setPorcentagemMin(15);
		return itemEstoqueTO;
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
		festaTest.setStatusFesta("P");
		festaTest.setDescricaoFesta("Bugago");
		festaTest.setHorarioFimFestaReal(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));

		return festaTest;
	}
	
	@SuppressWarnings("deprecation")
	public static Usuario usuarioTest() throws Exception{
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("fc68b677646b5f018d1762e9a19bf65180d9aab2794794340ade50e0d78a239affd43a613e7136a61b5d63b09f072c0c039dea4281873abe826d6e6285d9cefef0a0d868d3b0b0d4582ec787b473b4e0");
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

	
//addProduto_______________________________________________________________________________________	
	@Test
	public void addProdutoSucessoTest() throws Exception {
		Festa festa = festaTest();
		ProdutoTO produtoTO = produtoTOTest();
		int codProduto = 1;
		int codFesta = festa.getCodFesta();
		
		Mockito.when(produtoRepository.getNextValMySequence()).thenReturn(codProduto);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(produtoRepository.save(Mockito.any(Produto.class))).thenReturn(null);
		//doNothing().when(produtoRepository).save(Mockito.any(Produto.class));
		
		Produto produto = produtoService.addProduto(produtoTO, codFesta);

		assertEquals(produto.getCodProduto(), codProduto);
		assertEquals(produto.getCodFesta(), codFesta);
		assertEquals(produto.getMarca(), produtoTO.getMarca());
		assertEquals(produto.getPrecoMedio(), produtoTO.getPrecoMedio());
	}
	
	@Test
	public void addProdutoProdutoJaCadastradoTest() throws Exception {
		Festa festa = festaTest();
		ProdutoTO produtoTO = produtoTOTest();
		int codProduto = 1;
		int codFesta = festa.getCodFesta();
		
		Mockito.when(produtoRepository.getNextValMySequence()).thenReturn(codProduto);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(produtoRepository.save(Mockito.any(Produto.class))).thenReturn(null);
		//doNothing().when(produtoRepository).save(Mockito.any(Produto.class));
		
		Produto produto = produtoService.addProduto(produtoTO, codFesta);

		assertEquals(produto.getCodProduto(), codProduto);
		assertEquals(produto.getCodFesta(), codFesta);
		assertEquals(produto.getMarca(), produtoTO.getMarca());
		assertEquals(produto.getPrecoMedio(), produtoTO.getPrecoMedio());
	}
	
//addProdutoEstoque_________________________________________________________________________________________
	@Test
	public void addProdutoEstoqueSucessoTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = produto.getCodFesta();
		int quantidadeMax = itemEstoqueTO.getQuantidadeMax(); 
		int quantidadeAtual = itemEstoqueTO.getQuantiadadeAtual(); 
		int porcentagemMin = itemEstoqueTO.getPorcentagemMin();
	
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(produtoRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(null);
		doNothing().when(produtoRepository).saveProdutoEstoque(codProduto, codEstoque, codFesta, quantidadeMax, quantidadeAtual, porcentagemMin);
		
		ItemEstoque retorno = produtoService.addProdutoEstoque(itemEstoqueTO, codProduto, codEstoque);

		assertEquals(retorno.getCodEstoque(), codEstoque);
		assertEquals(retorno.getCodProduto(), codProduto);
		assertEquals(retorno.getCodFesta(), codFesta);
		assertEquals(retorno.getQuantidadeMax(), quantidadeMax);
		assertEquals(retorno.getQuantiadadeAtual(), quantidadeAtual);
		assertEquals(retorno.getPorcentagemMin(), porcentagemMin);
	}

	@Test
	public void addProdutoEstoqueFestaInvalidaTest() throws Exception {
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
		int quantidadeMax = itemEstoqueTO.getQuantidadeMax(); 
		int quantidadeAtual = itemEstoqueTO.getQuantiadadeAtual(); 
		int porcentagemMin = itemEstoqueTO.getPorcentagemMin();
	
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(produtoRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		doNothing().when(produtoRepository).saveProdutoEstoque(codProduto, codEstoque, codFesta, quantidadeMax, quantidadeAtual, porcentagemMin);
		
		boolean erroException = false;
		try {
			produtoService.addProdutoEstoque(itemEstoqueTO, codProduto, codEstoque);
		} catch (Exception e) {
			erroException = true;
		}

		assertEquals(erroException, true);
	}
	
	@Test
	public void addProdutoEstoqueProdutoJaNoEstoqueTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = produto.getCodFesta();
		int quantidadeMax = itemEstoqueTO.getQuantidadeMax(); 
		int quantidadeAtual = itemEstoqueTO.getQuantiadadeAtual(); 
		int porcentagemMin = itemEstoqueTO.getPorcentagemMin();
	
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(produtoRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		doNothing().when(produtoRepository).saveProdutoEstoque(codProduto, codEstoque, codFesta, quantidadeMax, quantidadeAtual, porcentagemMin);
		
		boolean erroException = false;
		try {
			produtoService.addProdutoEstoque(itemEstoqueTO, codProduto, codEstoque);
		} catch (Exception e) {
			erroException = true;
		}

		assertEquals(erroException, true);
	}
	
	@Test
	public void addProdutoEstoqueQuantidadeMaxInvalidTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		itemEstoqueTO.setQuantidadeMax(-20);
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = produto.getCodFesta();
		int quantidadeMax = itemEstoqueTO.getQuantidadeMax(); 
		int quantidadeAtual = itemEstoqueTO.getQuantiadadeAtual(); 
		int porcentagemMin = itemEstoqueTO.getPorcentagemMin();
	
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(produtoRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(null);
		doNothing().when(produtoRepository).saveProdutoEstoque(codProduto, codEstoque, codFesta, quantidadeMax, quantidadeAtual, porcentagemMin);
		
		boolean erroException = false;
		try {
			produtoService.addProdutoEstoque(itemEstoqueTO, codProduto, codEstoque);
		} catch (Exception e) {
			erroException = true;
		}

		assertEquals(erroException, true);
	}
	
	@Test
	public void addProdutoEstoqueQuantidadeMaxPequenaInvalidTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		itemEstoqueTO.setQuantidadeMax(100);
		itemEstoqueTO.setQuantiadadeAtual(200);
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = produto.getCodFesta();
		int quantidadeMax = itemEstoqueTO.getQuantidadeMax(); 
		int quantidadeAtual = itemEstoqueTO.getQuantiadadeAtual(); 
		int porcentagemMin = itemEstoqueTO.getPorcentagemMin();
	
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(produtoRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(null);
		doNothing().when(produtoRepository).saveProdutoEstoque(codProduto, codEstoque, codFesta, quantidadeMax, quantidadeAtual, porcentagemMin);
		
		boolean erroException = false;
		try {
			produtoService.addProdutoEstoque(itemEstoqueTO, codProduto, codEstoque);
		} catch (Exception e) {
			erroException = true;
		}

		assertEquals(erroException, true);
	}
	
	@Test
	public void addProdutoEstoquePorcentagemMinTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		itemEstoqueTO.setPorcentagemMin(-10);
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = produto.getCodFesta();
		int quantidadeMax = itemEstoqueTO.getQuantidadeMax(); 
		int quantidadeAtual = itemEstoqueTO.getQuantiadadeAtual(); 
		int porcentagemMin = itemEstoqueTO.getPorcentagemMin();
	
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(produtoRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(null);
		doNothing().when(produtoRepository).saveProdutoEstoque(codProduto, codEstoque, codFesta, quantidadeMax, quantidadeAtual, porcentagemMin);
		
		boolean erroException = false;
		try {
			produtoService.addProdutoEstoque(itemEstoqueTO, codProduto, codEstoque);
		} catch (Exception e) {
			erroException = true;
		}

		assertEquals(erroException, true);
	}
	
	@Test
	public void addProdutoEstoqueQuantidadeAtualTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		itemEstoqueTO.setQuantiadadeAtual(-2);
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = produto.getCodFesta();
		int quantidadeMax = itemEstoqueTO.getQuantidadeMax(); 
		int quantidadeAtual = itemEstoqueTO.getQuantiadadeAtual(); 
		int porcentagemMin = itemEstoqueTO.getPorcentagemMin();
	
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(festa);
		Mockito.when(produtoRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(null);
		doNothing().when(produtoRepository).saveProdutoEstoque(codProduto, codEstoque, codFesta, quantidadeMax, quantidadeAtual, porcentagemMin);
		
		boolean erroException = false;
		try {
			produtoService.addProdutoEstoque(itemEstoqueTO, codProduto, codEstoque);
		} catch (Exception e) {
			erroException = true;
		}

		assertEquals(erroException, true);
	}
	
	
//removerProduto____________________________________________________________________________________________
	@Test
	public void removerProdutoSucessoTest() throws Exception {
		Produto produto = produtoTest();
		int codProduto = produto.getCodProduto();
		
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(produtoRepository.findEstoquesComProduto(codProduto)).thenReturn(null);  
		doNothing().when(produtoRepository).delete(produto);
		//Mockito.when(produtoRepository.delete(produto)).thenReturn(null);
		
		Produto retorno = produtoService.removerProduto(codProduto);

		assertEquals(retorno.getCodProduto(), produto.getCodProduto());
		assertEquals(retorno.getCodFesta(), produto.getCodFesta());
		assertEquals(retorno.getMarca(), produto.getMarca());
		assertEquals(retorno.getPrecoMedio(), produto.getPrecoMedio());
	}
	
	@Test
	public void removerProdutoEmUsoTest() throws Exception {
		Produto produto = produtoTest();
		int codProduto = produto.getCodProduto();
		List<Estoque> estoques = new ArrayList<>();
		estoques.add(estoqueTest());
		
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(produtoRepository.findEstoquesComProduto(codProduto)).thenReturn(estoques);  
		doNothing().when(produtoRepository).delete(produto);
		//Mockito.when(produtoRepository.delete(produto)).thenReturn(null);
		
		
		boolean erro = false;
		try {
			produtoService.removerProduto(codProduto);		
		} catch (Exception e) {
			erro = true;
		
		}

		assertEquals(erro, true);
	}
//removerProdutoEstoque______________________________________________________________________________________
	@Test
	public void removerProdutoEstoqueSucessoTest() throws Exception {
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
	
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(produtoRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		doNothing().when(produtoRepository).deleteProdutoEstoque(codProduto, codEstoque);
		
		ItemEstoque retorno = produtoService.removerProdutoEstoque(codProduto, codEstoque);

		assertEquals(retorno.getCodEstoque(), codEstoque);
		assertEquals(retorno.getCodProduto(), codProduto);
	}
	
//editarProduto_______________________________________________________________________________________________
	@Test
	public void editarProdutoSucessoTest() throws Exception {
		ProdutoTO produtoTO = produtoTOTest();
		produtoTO.setMarca("ItubainexNotPlagio");
		produtoTO.setPrecoMedio(new BigDecimal("73.90"));
		Produto produto = produtoTest();
		int codProduto = produtoTO.getCodProduto();
		
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(produtoRepository.save(Mockito.any(Produto.class))).thenReturn(null);
		
		Produto retorno = produtoService.editarProduto(produtoTO);

		assertEquals(retorno.getCodProduto(), produtoTO.getCodProduto());
		assertEquals(retorno.getCodFesta(), produtoTO.getCodFesta());
		assertEquals(retorno.getMarca(), produtoTO.getMarca());
		assertEquals(retorno.getPrecoMedio(), produtoTO.getPrecoMedio());
	}
//editarProdutoEstoque________________________________________________________________________________________
	@Test
	public void editarProdutoEstoqueSucessoTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		itemEstoqueTO.setQuantidadeMax(200);
		itemEstoqueTO.setQuantiadadeAtual(100);
		itemEstoqueTO.setPorcentagemMin(10);
		
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = festa.getCodFesta();
		int quantidadeMax = itemEstoqueTO.getQuantidadeMax(); 
		int quantidadeAtual = itemEstoqueTO.getQuantiadadeAtual(); 
		int porcentagemMin = itemEstoqueTO.getPorcentagemMin();
	
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(produtoRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		doNothing().when(produtoRepository).updateProdutoEstoque(codProduto, codEstoque, quantidadeMax, quantidadeAtual, porcentagemMin);
		
		ItemEstoque retorno = produtoService.editarProdutoEstoque(itemEstoqueTO);

		assertEquals(retorno.getCodEstoque(), codEstoque);
		assertEquals(retorno.getCodProduto(), codProduto);
		assertEquals(retorno.getCodFesta(), codFesta);
		assertEquals(retorno.getQuantidadeMax(), quantidadeMax);
		assertEquals(retorno.getQuantiadadeAtual(), quantidadeAtual);
		assertEquals(retorno.getPorcentagemMin(), porcentagemMin);
	}
	
	@Test
	public void editarProdutoEstoqueQuantidadeMaxTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		itemEstoqueTO.setQuantidadeMax(-200);
		itemEstoqueTO.setQuantiadadeAtual(100);
		itemEstoqueTO.setPorcentagemMin(10);
		
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int quantidadeMax = itemEstoqueTO.getQuantidadeMax(); 
		int quantidadeAtual = itemEstoqueTO.getQuantiadadeAtual(); 
		int porcentagemMin = itemEstoqueTO.getPorcentagemMin();
	
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(produtoRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		doNothing().when(produtoRepository).updateProdutoEstoque(codProduto, codEstoque, quantidadeMax, quantidadeAtual, porcentagemMin);
		
		boolean erro = false;
		try {
			produtoService.editarProdutoEstoque(itemEstoqueTO);	
		} catch (Exception e) {
			erro = true;
		}

		assertEquals(erro, true);
	}
	
	@Test
	public void editarProdutoEstoqueQuantidadeMaxPequenaTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		itemEstoqueTO.setQuantidadeMax(200);
		itemEstoqueTO.setQuantiadadeAtual(300);
		itemEstoqueTO.setPorcentagemMin(10);
		
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int quantidadeMax = itemEstoqueTO.getQuantidadeMax(); 
		int quantidadeAtual = itemEstoqueTO.getQuantiadadeAtual(); 
		int porcentagemMin = itemEstoqueTO.getPorcentagemMin();
	
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(produtoRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		doNothing().when(produtoRepository).updateProdutoEstoque(codProduto, codEstoque, quantidadeMax, quantidadeAtual, porcentagemMin);
		
		boolean erro = false;
		try {
			produtoService.editarProdutoEstoque(itemEstoqueTO);	
		} catch (Exception e) {
			erro = true;
		}

		assertEquals(erro, true);
	}
	
	@Test
	public void editarProdutoEstoquePorcentagemMinTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		itemEstoqueTO.setQuantidadeMax(200);
		itemEstoqueTO.setQuantiadadeAtual(100);
		itemEstoqueTO.setPorcentagemMin(-10);
		
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int quantidadeMax = itemEstoqueTO.getQuantidadeMax(); 
		int quantidadeAtual = itemEstoqueTO.getQuantiadadeAtual(); 
		int porcentagemMin = itemEstoqueTO.getPorcentagemMin();
	
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(produtoRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		doNothing().when(produtoRepository).updateProdutoEstoque(codProduto, codEstoque, quantidadeMax, quantidadeAtual, porcentagemMin);
		
		boolean erro = false;
		try {
			produtoService.editarProdutoEstoque(itemEstoqueTO);	
		} catch (Exception e) {
			erro = true;
		}

		assertEquals(erro, true);
	}
	
	@Test
	public void editarProdutoEstoqueQuantiadadeAtualTest() throws Exception {
		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		itemEstoqueTO.setQuantidadeMax(200);
		itemEstoqueTO.setQuantiadadeAtual(-100);
		itemEstoqueTO.setPorcentagemMin(10);
		
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int quantidadeMax = itemEstoqueTO.getQuantidadeMax(); 
		int quantidadeAtual = itemEstoqueTO.getQuantiadadeAtual(); 
		int porcentagemMin = itemEstoqueTO.getPorcentagemMin();
	
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(produtoRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		doNothing().when(produtoRepository).updateProdutoEstoque(codProduto, codEstoque, quantidadeMax, quantidadeAtual, porcentagemMin);
		
		boolean erro = false;
		try {
			produtoService.editarProdutoEstoque(itemEstoqueTO);	
		} catch (Exception e) {
			erro = true;
		}

		assertEquals(erro, true);
	}



//baixa_______________________________________________________________________________________________________
	@Test
	public void baixaProdutoSucessoTest() throws Exception {
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = festa.getCodFesta();
		int quantidade = 3;
		int quantidadeAtual = itemEstoque.getQuantiadadeAtual() - quantidade;
	
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(produtoRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		doNothing().when(produtoRepository).updateProdutoEstoqueQuantidade(codProduto, codEstoque, quantidadeAtual);
		
		ItemEstoque retorno = produtoService.baixaProduto(codProduto, codEstoque, quantidade);

		assertEquals(retorno.getCodEstoque(), codEstoque);
		assertEquals(retorno.getCodProduto(), codProduto);
		assertEquals(retorno.getCodFesta(), codFesta);
		assertEquals(retorno.getQuantiadadeAtual(), quantidadeAtual);
	}
	
	@Test
	public void baixaProdutoValorNegativoTest() throws Exception {
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int quantidade = -300;
		int quantidadeAtual = itemEstoque.getQuantiadadeAtual() - quantidade;
	
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(produtoRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		doNothing().when(produtoRepository).updateProdutoEstoqueQuantidade(codProduto, codEstoque, quantidadeAtual);
		
		boolean erro = false;
		try {
			produtoService.baixaProduto(codProduto, codEstoque, quantidade);		
		} catch (Exception e) {
			erro = true;
		}

		assertEquals(erro, true);
	}
	
	@Test
	public void baixaProdutoBaixaExcessivaTest() throws Exception {  //retirar mais do que tem no estoque
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int quantidade = 300;
		int quantidadeAtual = itemEstoque.getQuantiadadeAtual() - quantidade;
	
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(produtoRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		doNothing().when(produtoRepository).updateProdutoEstoqueQuantidade(codProduto, codEstoque, quantidadeAtual);
		
		boolean erro = false;
		try {
			produtoService.baixaProduto(codProduto, codEstoque, quantidade);		
		} catch (Exception e) {
			erro = true;
		}

		assertEquals(erro, true);
	}
	
//recarga_____________________________________________________________________________________________________
	@Test
	public void recargaProdutoSucessoTest() throws Exception {
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		Festa festa = festaTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int codFesta = festa.getCodFesta();
		int quantidade = 3;
		int quantidadeAtual = itemEstoque.getQuantiadadeAtual() + quantidade;
	
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(produtoRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		doNothing().when(produtoRepository).updateProdutoEstoqueQuantidade(codProduto, codEstoque, quantidadeAtual);
		
		ItemEstoque retorno = produtoService.recargaProduto(codProduto, codEstoque, quantidade);

		assertEquals(retorno.getCodEstoque(), codEstoque);
		assertEquals(retorno.getCodProduto(), codProduto);
		assertEquals(retorno.getCodFesta(), codFesta);
		assertEquals(retorno.getQuantiadadeAtual(), quantidadeAtual);
	}
	
	@Test
	public void recargaProdutoValorNegativoTest() throws Exception {
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int quantidade = -3;
		int quantidadeAtual = itemEstoque.getQuantiadadeAtual() + quantidade;
	
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(produtoRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		doNothing().when(produtoRepository).updateProdutoEstoqueQuantidade(codProduto, codEstoque, quantidadeAtual);
		
		
		boolean erro = false;
		try {
			produtoService.recargaProduto(codProduto, codEstoque, quantidade);		
		} catch (Exception e) {
			erro = true;
		}

		assertEquals(erro, true);
	}
	
	@Test
	public void recargaProdutoExcessivaTest() throws Exception {  //adicionar mais do que o estoque comporta
		ItemEstoque itemEstoque = itemEstoqueTest();
		Produto produto = produtoTest();
		Estoque estoque = estoqueTest();
		int codProduto = produto.getCodProduto();
		int codEstoque = estoque.getCodEstoque();
		int quantidade = 300;
		int quantidadeAtual = itemEstoque.getQuantiadadeAtual() + quantidade;
	
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(produtoRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(itemEstoque);
		doNothing().when(produtoRepository).updateProdutoEstoqueQuantidade(codProduto, codEstoque, quantidadeAtual);
		
		
		boolean erro = false;
		try {
			produtoService.recargaProduto(codProduto, codEstoque, quantidade);		
		} catch (Exception e) {
			erro = true;
		}

		assertEquals(erro, true);
	}
	
//validadores__________________________________________________________________________________________________
	@Test
	public void validarFestaExceptionTest() throws Exception {
		Festa festa = festaTest();
		int codFesta = festa.getCodFesta();
		
		Mockito.when(festaRepository.findById(codFesta)).thenReturn(null);
		
		boolean erro = false;
		try {
			produtoService.validarFesta(codFesta);		
		} catch (Exception e) {
			erro = true;
		}
		
		assertEquals(erro, true);
	}
	
	@Test
	public void validarProdutoExceptionTest() throws Exception {
		Produto produto = produtoTest();
		int codProduto = produto.getCodProduto();
		
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(null);
		
		boolean erro = false;
		try {
			produtoService.validarProduto(codProduto);	
		} catch (Exception e) {
			erro = true;
		}
		
		assertEquals(erro, true);
	}
	
	@Test
	public void validarEstoqueExceptionTest() throws Exception {
		Estoque estoque = estoqueTest();
		int codEstoque = estoque.getCodEstoque();
		
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(null);
		
		boolean erro = false;
		try {
			produtoService.validarEstoque(codEstoque);
		} catch (Exception e) {
			erro = true;
		}
		
		assertEquals(erro, true);
	}
	
	@Test
	public void validarProdutoEstoqueExceptionTest() throws Exception {
		//ItemEstoque itemEstoque = itemEstoqueTest();
		Estoque estoque = estoqueTest();
		int codEstoque = estoque.getCodEstoque();
		Produto produto = produtoTest();
		int codProduto = produto.getCodProduto();
		
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		Mockito.when(produtoRepository.findById(codProduto)).thenReturn(produto);
		Mockito.when(produtoRepository.findItemEstoque(codEstoque, codProduto)).thenReturn(null);
		
		boolean erro = false;
		try {
			produtoService.validarProdutoEstoque(codEstoque, codProduto);
		} catch (Exception e) {
			erro = true;
		}
		
		assertEquals(erro, true);
	}
	
	@Test
	public void validarUsuarioPorFestaSucessoTest() throws Exception {
		Grupo grupo = grupoTest();
		Festa festa = festaTest();
		int codFesta = festa.getCodFesta();
		Usuario usuario = usuarioTest();
		int codUsuario = usuario.getCodUsuario();
		
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(codFesta, codUsuario, TipoPermissao.ADDMEMBE.getCodigo())).thenReturn(grupo);
		
		boolean erro = false;
		try {
			produtoService.validarUsuarioPorFesta(codUsuario, codFesta);
		} catch (Exception e) {
			erro = true;
		}
		
		assertEquals(erro, false);
	}
	
	@Test
	public void validarUsuarioPorFestaExceptionTest() throws Exception {
		Festa festa = festaTest();
		int codFesta = festa.getCodFesta();
		Usuario usuario = usuarioTest();
		int codUsuario = usuario.getCodUsuario();
		
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(codFesta, codUsuario, TipoPermissao.ADDMEMBE.getCodigo())).thenReturn(null);
		
		boolean erro = false;
		try {
			produtoService.validarUsuarioPorFesta(codUsuario, codFesta);
		} catch (Exception e) {
			erro = true;
		}
		
		assertEquals(erro, true);
	}
	
	@Test
	public void validarUsuarioPorEstoqueSucessoTest() throws Exception {
		Estoque estoque = estoqueTest();
		int codEstoque = estoque.getCodEstoque();
		Grupo grupo = grupoTest();
		Festa festa = festaTest();
		int codFesta = festa.getCodFesta();
		Usuario usuario = usuarioTest();
		int codUsuario = usuario.getCodUsuario();
		
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(codFesta, codUsuario, TipoPermissao.ADDMEMBE.getCodigo())).thenReturn(grupo);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(estoque);
		
		boolean erro = false;
		try {
			produtoService.validarUsuarioPorEstoque(codUsuario, codEstoque);
		} catch (Exception e) {
			erro = true;
		}
		
		assertEquals(erro, false);
	}
	
	@Test
	public void validarUsuarioPorEstoqueExceptionTest() throws Exception {
		Estoque estoque = estoqueTest();
		int codEstoque = estoque.getCodEstoque();
		Grupo grupo = grupoTest();
		Festa festa = festaTest();
		int codFesta = festa.getCodFesta();
		Usuario usuario = usuarioTest();
		int codUsuario = usuario.getCodUsuario();
		
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(codFesta, codUsuario, TipoPermissao.ADDMEMBE.getCodigo())).thenReturn(grupo);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(codEstoque)).thenReturn(null);
		
		boolean erro = false;
		try {
			produtoService.validarUsuarioPorEstoque(codUsuario, codEstoque);
		} catch (Exception e) {
			erro = true;
		}
		
		assertEquals(erro, true);
	}
	
}	
