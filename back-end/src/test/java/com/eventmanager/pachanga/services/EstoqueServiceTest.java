package com.eventmanager.pachanga.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

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
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.ItemEstoque;
import com.eventmanager.pachanga.domains.Produto;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.EstoqueTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.EstoqueFactory;
import com.eventmanager.pachanga.repositories.EstoqueRepository;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.ItemEstoqueFluxoRepository;
import com.eventmanager.pachanga.repositories.ItemEstoqueRepository;
import com.eventmanager.pachanga.repositories.ProdutoRepository;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=EstoqueService.class)
class EstoqueServiceTest {
	
	@MockBean
	private FestaRepository festaRepository;

	@MockBean
	private GrupoRepository grupoRepository;

	@MockBean
	private EstoqueRepository estoqueRepository;
	
	@MockBean
	private ProdutoRepository produtoRepository;
	
	@MockBean
	private ItemEstoqueRepository itemEstoqueRepository;
	
	@MockBean
	private EstoqueFactory estoqueFactory;
	
	@MockBean
	private FestaService festaService;
	
	@MockBean
	private ItemEstoqueFluxoRepository itemEstoqueFluxoRepository;
	
	@Autowired
	private EstoqueService estoqueService;
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

//metodos auxiliares____________________________________________________________________________________________
	private Grupo criacaoGrupo() {
		Grupo grupo = new Grupo();
		grupo.setCodGrupo(1);
		grupo.setNomeGrupo("CONVIDADO");
		grupo.setOrganizador(false);
		grupo.setFesta(criacaoFesta());
		
		return grupo;
	}
	
	public static Usuario usuarioTest() throws Exception{
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("fc68b677646b5f018d1762e9a19bf65180d9aab2794794340ade50e0d78a239affd43a613e7136a61b5d63b09f072c0c039dea4281873abe826d6e6285d9cefef0a0d868d3b0b0d4582ec787b473b4e0");
		usuarioTest.setGenero("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");

		return usuarioTest;
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
		festaTest.setStatusFesta("P");
		festaTest.setDescricaoFesta("Bugago");
		festaTest.setHorarioFimFestaReal(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));

		return festaTest;
	}

	public Estoque estoqueTest() {
		Estoque estoque = new Estoque();
		estoque.setCodEstoque(1);
		estoque.setNomeEstoque("Estoque");
		estoque.setFesta(criacaoFesta());
		estoque.setPrincipal(false);
		return estoque;
	}
	
	public EstoqueTO estoqueTOTest() {
		EstoqueTO estoqueTO = new EstoqueTO();
		estoqueTO.setCodEstoque(1);
		estoqueTO.setNomeEstoque("Estoque");
		estoqueTO.setPrincipal(false);
		return estoqueTO;
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
	
	private ItemEstoque itemEstoqueTest() {
		ItemEstoque itemEstoque = new ItemEstoque();
		itemEstoque.setCodFesta(2); // o mesmo do festaTest()
		itemEstoque.setQuantidadeMax(100);
		itemEstoque.setPorcentagemMin(15);
		itemEstoque.setQuantidadeAtual(30);
		itemEstoque.setQuantPerda(0);
		return itemEstoque;
	}

//estoquesFesta_____________________________________________________________________________________________
	
	@Test
	void estoquesFestaSucessTest() {
		Festa festa = criacaoFesta();
		Grupo grupo = criacaoGrupo();
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		List<Estoque> estoques = new ArrayList<>();
		Estoque estoque = estoqueTest();
		estoques.add(estoque);
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festa);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(estoqueRepository.findEstoqueByCodFesta(Mockito.anyInt())).thenReturn(estoques);
		Mockito.when(itemEstoqueRepository.findItensEstoqueByFestaAndEstoque(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);
		
		List<Estoque> retornos = estoqueService.estoquesFesta(festa.getCodFesta(), 2);
		Estoque retorno = retornos.get(0);
		
		assertEquals(retorno.getCodEstoque(), estoque.getCodEstoque());
		assertEquals(retorno.getFesta().getCodFesta(), estoque.getFesta().getCodFesta());
		assertEquals(retorno.getNomeEstoque(), estoque.getNomeEstoque());
	}
	
	@Test
	void estoquesFestaUSESPERMExceptionTest() {
		Festa festa = criacaoFesta();
		List<Grupo> grupos = new ArrayList<>();
		//grupos.add(grupo);
		List<Estoque> estoques = new ArrayList<>();
		Estoque estoque = estoqueTest();
		estoques.add(estoque);
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festa);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(estoqueRepository.findEstoqueByCodFesta(Mockito.anyInt())).thenReturn(estoques);
		Mockito.when(itemEstoqueRepository.findItensEstoqueByFestaAndEstoque(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);
		
		boolean erro = false;
		try {		
			estoqueService.estoquesFesta(festa.getCodFesta(), 2);
		} catch (ValidacaoException e) {
			erro = true;

		}
		
		assertEquals(true, erro);
	}
	
	@Test
	void estoquesFestaFESTNFOUExceptionTest() {
		Festa festa = criacaoFesta();
		Grupo grupo = criacaoGrupo();
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		List<Estoque> estoques = new ArrayList<>();
		Estoque estoque = estoqueTest();
		estoques.add(estoque);
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(null);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(estoqueRepository.findEstoqueByCodFesta(Mockito.anyInt())).thenReturn(estoques);
		Mockito.when(itemEstoqueRepository.findItensEstoqueByFestaAndEstoque(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);
		
		boolean erro = false;
		try {		
			estoqueService.estoquesFesta(festa.getCodFesta(), 2);
		} catch (ValidacaoException e) {
			erro = true;

		}
		
		assertEquals(true, erro);
	}
	
//estoquesFestaComProdutos__________________________________________________________________________________
	@Test
	void estoquesFestaComProdutosSucessTest() {
		Festa festa = criacaoFesta();
		Grupo grupo = criacaoGrupo();
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		List<Estoque> estoques = new ArrayList<>();
		Estoque estoque = estoqueTest();
		estoques.add(estoque);
		Produto produto = produtoTest();
		List<Produto> produtos = new ArrayList<>();
		produtos.add(produto);
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festa);
		Mockito.doNothing().when(festaService).validarFestaFinalizada(Mockito.anyInt());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(estoqueRepository.findEstoqueByCodFesta(Mockito.anyInt())).thenReturn(estoques);
		Mockito.when(produtoRepository.findProdutosPorEstoque(Mockito.anyInt())).thenReturn(produtos);
		
		List<Estoque> retornos = estoqueService.estoquesFestaComProdutos(estoque.getFesta().getCodFesta(), 2);
		Estoque retorno = retornos.get(0);
		
		assertEquals(retorno.getCodEstoque(), estoque.getCodEstoque());
		assertEquals(retorno.getFesta().getCodFesta(), estoque.getFesta().getCodFesta());
		assertEquals(retorno.getNomeEstoque(), estoque.getNomeEstoque());
	}

//addEstoque_______________________________________________________________________________________________
	
	@Test
	void addEstoqueSucessTest() {
		Festa festa = criacaoFesta();
		Grupo grupo = criacaoGrupo();
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		Estoque estoque = estoqueTest();
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festa);
		Mockito.doNothing().when(festaService).validarFestaFinalizada(Mockito.anyInt());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(estoqueRepository.getNextValMySequence()).thenReturn(estoque.getCodEstoque());
		Mockito.when(estoqueRepository.findByNomeEstoque(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(estoqueRepository.save(Mockito.any(Estoque.class))).thenReturn(null);
		Mockito.when(estoqueFactory.getEstoque(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(estoque);
		
		Estoque retorno = estoqueService.addEstoque(estoque.getNomeEstoque(), festa.getCodFesta(), estoque.isPrincipal(), 2);
	
		assertEquals(retorno.getCodEstoque(), estoque.getCodEstoque());
		assertEquals(retorno.getFesta().getCodFesta(), estoque.getFesta().getCodFesta());
		assertEquals(retorno.getNomeEstoque(), estoque.getNomeEstoque());
		assertEquals(retorno.isPrincipal(), estoque.isPrincipal());
	}
	
	@Test
	void addEstoqueESTOMNOMExceptionTest() {
		Festa festa = criacaoFesta();
		Grupo grupo = criacaoGrupo();
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		Estoque estoque = estoqueTest();
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festa);
		Mockito.doNothing().when(festaService).validarFestaFinalizada(Mockito.anyInt());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(estoqueRepository.getNextValMySequence()).thenReturn(estoque.getCodEstoque());
		Mockito.when(estoqueRepository.findByNomeEstoque(Mockito.anyString(), Mockito.anyInt())).thenReturn(estoque);
		Mockito.when(estoqueRepository.save(Mockito.any(Estoque.class))).thenReturn(null);
		Mockito.when(estoqueFactory.getEstoque(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(estoque);
		
		boolean erro = false;
		try {		
			estoqueService.addEstoque(estoque.getNomeEstoque(), festa.getCodFesta(), estoque.isPrincipal(), 2);
		} catch (ValidacaoException e) {
			erro = true;

		}
		
		assertEquals(true, erro);
	}

//updateEstoque____________________________________________________________________________________________
	@Test
	void updateEstoqueSucessTest() {
		EstoqueTO estoqueTO = estoqueTOTest();
		Festa festa = criacaoFesta();
		Grupo grupo = criacaoGrupo();
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		Estoque estoque = estoqueTest();
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festa);
		Mockito.doNothing().when(festaService).validarFestaFinalizada(Mockito.anyInt());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(Mockito.anyInt())).thenReturn(estoque);
		Mockito.when(estoqueRepository.findByNomeEstoque(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(estoqueRepository.save(Mockito.any(Estoque.class))).thenReturn(null);
		Mockito.when(estoqueFactory.getEstoque(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(estoque);
		
		Estoque retorno = estoqueService.updateEstoque(estoqueTO, festa.getCodFesta(), 2);
		
		assertEquals(retorno.getCodEstoque(), estoque.getCodEstoque());
		assertEquals(retorno.getFesta().getCodFesta(), estoque.getFesta().getCodFesta());
		assertEquals(retorno.getNomeEstoque(), estoque.getNomeEstoque());
		assertEquals(retorno.isPrincipal(), estoque.isPrincipal());
	}
	
	@Test
	void updateEstoqueESTONOMEExceptionTest() {
		EstoqueTO estoqueTO = estoqueTOTest();
		Festa festa = criacaoFesta();
		Grupo grupo = criacaoGrupo();
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		Estoque estoque = estoqueTest();
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festa);
		Mockito.doNothing().when(festaService).validarFestaFinalizada(Mockito.anyInt());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(Mockito.anyInt())).thenReturn(estoque);
		Mockito.when(estoqueRepository.findByNomeEstoque(Mockito.anyString(), Mockito.anyInt())).thenReturn(estoque);
		Mockito.when(estoqueRepository.save(Mockito.any(Estoque.class))).thenReturn(null);
		Mockito.when(estoqueFactory.getEstoque(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(estoque);
		
		boolean erro = false;
		try {		
			estoqueService.updateEstoque(estoqueTO, festa.getCodFesta(), 2);
		} catch (ValidacaoException e) {
			erro = true;

		}
		
		assertEquals(true, erro);
	}
	
	@Test
	void updateEstoqueESTONFOUExceptionTest() {
		EstoqueTO estoqueTO = estoqueTOTest();
		Festa festa = criacaoFesta();
		Grupo grupo = criacaoGrupo();
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		Estoque estoque = estoqueTest();
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festa);
		Mockito.doNothing().when(festaService).validarFestaFinalizada(Mockito.anyInt());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(Mockito.anyInt())).thenReturn(null);
		Mockito.when(estoqueRepository.findByNomeEstoque(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(estoqueRepository.save(Mockito.any(Estoque.class))).thenReturn(null);
		Mockito.when(estoqueFactory.getEstoque(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(estoque);
		
		boolean erro = false;
		try {		
			estoqueService.updateEstoque(estoqueTO, festa.getCodFesta(), 2);
		} catch (ValidacaoException e) {
			erro = true;

		}
		
		assertEquals(true, erro);
	}

//deleteEstoque____________________________________________________________________________________________
	@Test
	void deleteEstoqueSucessTest() {
		Festa festa = criacaoFesta();
		Grupo grupo = criacaoGrupo();
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		Estoque estoque = estoqueTest();
		Set<ItemEstoque> itensEstoque = new HashSet<>();
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festa);
		Mockito.doNothing().when(festaService).validarFestaFinalizada(Mockito.anyInt());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(itemEstoqueRepository.findItensEstoqueByFestaAndEstoque(Mockito.anyInt(), Mockito.anyInt())).thenReturn(itensEstoque);
		Mockito.doNothing().when(estoqueRepository).delete(Mockito.any(Estoque.class));
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(Mockito.anyInt())).thenReturn(estoque);
		
		estoqueService.deleteEstoque(estoque.getCodEstoque(), festa.getCodFesta(), 2);
	}
	
	@Test
	void deleteCascadeTeste() {
		List<Estoque> estoques = new ArrayList<Estoque>();
		estoques.add(new Estoque());
		Mockito.when(estoqueRepository.findEstoqueByCodFesta(Mockito.anyInt())).thenReturn(estoques);
		doNothing().when(estoqueRepository).deleteProdEstoque(Mockito.anyInt(), Mockito.anyInt());
		doNothing().when(produtoRepository).deleteProdFesta(Mockito.anyInt());
		doNothing().when(estoqueRepository).deleteEstoque(Mockito.anyInt());
		doNothing().when(itemEstoqueFluxoRepository).deleteByCodFesta(Mockito.anyInt());

		estoqueService.deleteCascade(Mockito.anyInt());
	}
	
	@Test
	void deleteEstoqueESTOCITEExceptionTest() {
		Festa festa = criacaoFesta();
		Grupo grupo = criacaoGrupo();
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		Estoque estoque = estoqueTest();
		Set<ItemEstoque> itensEstoque = new HashSet<>();
		itensEstoque.add(itemEstoqueTest());
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festa);
		Mockito.doNothing().when(festaService).validarFestaFinalizada(Mockito.anyInt());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(itemEstoqueRepository.findItensEstoqueByFestaAndEstoque(Mockito.anyInt(), Mockito.anyInt())).thenReturn(itensEstoque);
		Mockito.doNothing().when(estoqueRepository).delete(Mockito.any(Estoque.class));
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(Mockito.anyInt())).thenReturn(estoque);
		
		boolean erro = false;
		try {		
			estoqueService.deleteEstoque(estoque.getCodEstoque(), festa.getCodFesta(), 2);
		} catch (ValidacaoException e) {
			erro = true;

		}
		
		assertEquals(true, erro);
	}
	
	@Test
	void deleteEstoqueESTOPRINExceptionTest() {
		Festa festa = criacaoFesta();
		Grupo grupo = criacaoGrupo();
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		Estoque estoque = estoqueTest();
		estoque.setPrincipal(true);
		Set<ItemEstoque> itensEstoque = new HashSet<>();
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festa);
		Mockito.doNothing().when(festaService).validarFestaFinalizada(Mockito.anyInt());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(itemEstoqueRepository.findItensEstoqueByFestaAndEstoque(Mockito.anyInt(), Mockito.anyInt())).thenReturn(itensEstoque);
		Mockito.doNothing().when(estoqueRepository).delete(Mockito.any(Estoque.class));
		Mockito.when(estoqueRepository.findByEstoqueCodEstoque(Mockito.anyInt())).thenReturn(estoque);
		
		boolean erro = false;
		try {		
			estoqueService.deleteEstoque(estoque.getCodEstoque(), festa.getCodFesta(), 2);
		} catch (ValidacaoException e) {
			erro = true;

		}
		
		assertEquals(true, erro);
	}
}
