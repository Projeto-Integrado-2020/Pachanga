package com.eventmanager.pachanga.services;

import static org.junit.Assert.assertNotNull;
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
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Categoria;
import com.eventmanager.pachanga.domains.CategoriasFesta;
import com.eventmanager.pachanga.domains.Estoque;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.ItemEstoque;
import com.eventmanager.pachanga.domains.Produto;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.ConviteFestaTO;
import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.dtos.ItemEstoqueTO;
import com.eventmanager.pachanga.dtos.NotificacaoMudancaStatusTO;
import com.eventmanager.pachanga.dtos.UsuarioTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.ConviteFestaFactory;
import com.eventmanager.pachanga.factory.FestaFactory;
import com.eventmanager.pachanga.factory.NotificacaoMudancaStatusFactory;
import com.eventmanager.pachanga.repositories.AreaSegurancaProblemaFluxoRepository;
import com.eventmanager.pachanga.repositories.AreaSegurancaProblemaRepository;
import com.eventmanager.pachanga.repositories.AreaSegurancaRepository;
import com.eventmanager.pachanga.repositories.CategoriaRepository;
import com.eventmanager.pachanga.repositories.CategoriasFestaRepository;
import com.eventmanager.pachanga.repositories.ConvidadoRepository;
import com.eventmanager.pachanga.repositories.CupomRepository;
import com.eventmanager.pachanga.repositories.DadoBancarioRepository;
import com.eventmanager.pachanga.repositories.EstoqueRepository;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.InfoIntegracaoRepository;
import com.eventmanager.pachanga.repositories.IngressoRepository;
import com.eventmanager.pachanga.repositories.ItemEstoqueFluxoRepository;
import com.eventmanager.pachanga.repositories.LoteRepository;
import com.eventmanager.pachanga.repositories.ProdutoRepository;
import com.eventmanager.pachanga.repositories.QuestionarioFormsRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoCategoria;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;


@RunWith(SpringRunner.class)
@WebMvcTest(value=FestaService.class)
class FestaServiceTest {

	@MockBean
	private FestaRepository festaRepository;

	@MockBean
	private UsuarioRepository usuarioRepository;

	@MockBean
	private GrupoRepository grupoRepository;

	@MockBean
	private ConvidadoRepository convidadoRepository;

	@MockBean
	private CategoriasFestaRepository categoriasFestaRepository;

	@MockBean
	private CategoriaRepository categoriaRepository;

	@MockBean
	private EstoqueRepository estoqueRepository;

	@MockBean
	private ProdutoRepository produtoRepository;

	@MockBean
	private GrupoService grupoService;
	
	@MockBean
	private ProdutoService produtoService;
	
	@MockBean
	private LoteService loteService;

	@MockBean
	private EstoqueService estoqueService;

	@MockBean
	private FestaFactory festaFactory;

	@MockBean
	private ConviteFestaFactory conviteFestaFactory;

	@MockBean
	private NotificacaoService notificacaoService;
	
	@MockBean
	private AreaSegurancaProblemaFluxoRepository areaProblemaFluxoRepository;
	
	@MockBean
	private AreaSegurancaProblemaRepository areaProblemaRepository;
	
	@MockBean
	private AreaSegurancaRepository areaRepository;
	
	@MockBean
	private CupomRepository cupomRepository;
	
	@MockBean
	private DadoBancarioRepository dadoBancarioRepository;
	
	@MockBean
	private InfoIntegracaoRepository infoIntegracaoRepository;
	
	@MockBean
	private IngressoRepository ingressoRepository;
	
	@MockBean
	private LoteRepository loteRepository;
	
	@MockBean
	private QuestionarioFormsRepository questionarioFormsRepository;
	
	@MockBean
	private ItemEstoqueFluxoRepository itemEstoqueFluxoRepository;
	
	@MockBean
	private NotificacaoMudancaStatusFactory notificacaoMudancaStatusFactory;
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	@Autowired
	private FestaService festaService;
	
	@Autowired
	private Environment env;


	//metodos auxiliares___________________________________________________________________________________________________________________________________	

	public FestaTO festaTOTest() throws Exception{
		FestaTO festaTest = new FestaTO();

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
		festaTest.setCodPrimaria(1);
		festaTest.setCodSecundaria(2);

		UsuarioTO usuario1 = UsuarioServiceTest.usuarioToTest();
		usuario1.setCodUsuario(1);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		UsuarioTO usuario2 = UsuarioServiceTest.usuarioToTest();
		usuario2.setCodUsuario(2);
		usuario2.setNomeUser("Braz_qualquer_e_ficticio");

		List<UsuarioTO> usuarios = new ArrayList<UsuarioTO>();
		usuarios.add(usuario1);
		usuarios.add(usuario2);

		return festaTest;
	}
	
	private NotificacaoMudancaStatusTO notificacaoMudancaoStatusTOTest() {
		NotificacaoMudancaStatusTO notificacaoMudancaStatusTO = new NotificacaoMudancaStatusTO();
		notificacaoMudancaStatusTO.setNomeFesta("teste");
		notificacaoMudancaStatusTO.setTipoAlteracao(TipoStatusFesta.INICIADO.getValor());
		return notificacaoMudancaStatusTO;
	}
	
	private List<Grupo> criacaoGrupos(){
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(criacaoGrupo());
		return grupos;
	}

	@SuppressWarnings("deprecation")
	private Usuario usuarioTest() throws Exception{
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("1234");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setGenero("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");

		return usuarioTest;
	}

	public Grupo criacaoGrupo() {
		Grupo grupo = new Grupo();
		grupo.setCodGrupo(1);
		grupo.setNomeGrupo("CONVIDADO");
		return grupo;
	}
	
	public Estoque estoqueTest() {
		Estoque estoque = new Estoque();
		estoque.setCodEstoque(1);
		return estoque;
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
	
	private ItemEstoque itemEstoqueTest() {
		ItemEstoque itemEstoque = new ItemEstoque();
		itemEstoque.setEstoque(estoqueTest());
		itemEstoque.setCodFesta(2); // o mesmo do festaTest()
		itemEstoque.setProduto(produtoTest());
		itemEstoque.setQuantidadeMax(100);
		itemEstoque.setPorcentagemMin(15);
		return itemEstoque;
	}

	private Festa festaTest() throws Exception{
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
	
	public Festa festaIniTest() throws Exception{
		Festa festaTest = new Festa();
		festaTest.setCodFesta(2);
		festaTest.setCodEnderecoFesta("https//:minhacasa.org");
		festaTest.setDescOrganizador("sou demente");
		festaTest.setHorarioInicioFesta(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		festaTest.setHorarioFimFesta(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		festaTest.setNomeFesta("festao");
		festaTest.setOrganizador("Joao Neves");
		festaTest.setStatusFesta(TipoStatusFesta.INICIADO.getValor());
		festaTest.setDescricaoFesta("Bugago");
		festaTest.setHorarioFimFestaReal(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));

		return festaTest;
	}

	private Categoria categoriaTest() {
		Categoria categoria = new Categoria();
		categoria.setCodCategoria(1);
		categoria.setNomeCategoria("teste");
		return categoria;
	}

	private CategoriasFesta categoriaFestaTest() throws Exception {
		CategoriasFesta categoriasFesta = new CategoriasFesta();
		categoriasFesta.setCategoria(categoriaTest());
		categoriasFesta.setFesta(festaTest());
		categoriasFesta.setTipCategoria(TipoCategoria.PRIMARIO.getDescricao());
		return categoriasFesta;
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


	//procurarFestasTest_______________________________________________________________________________________________________________________________________	

	@Test
	void procurarFestasTest() throws Exception {
		Festa festa1 = festaTest();
		festa1.setCodFesta(1);
		festa1.setNomeFesta("festa1");

		Festa festa2 = festaTest();
		festa1.setCodFesta(2);
		festa1.setNomeFesta("festa2");

		Festa festa3 = festaTest();
		festa1.setCodFesta(3);
		festa1.setNomeFesta("festa3");

		List<Festa> festas = new ArrayList<Festa>();
		festas.add(festa1);
		festas.add(festa2);
		festas.add(festa3);

		Mockito.when(festaRepository.findAll()).thenReturn(festas);

		List<Festa> retorno = festaService.procurarFestas();

		assertEquals(festas.size(), retorno.size());
		assertEquals(true, retorno.containsAll(festas));
	}

	//procurarFestasPorUsuario_________________________________________________________________________________________________________________________
	@Test
	void procurarFestasPorUsuarioTest() throws Exception {
		Festa festa1 = festaTest();
		festa1.setCodFesta(1);
		festa1.setNomeFesta("festa1");

		Festa festa2 = festaTest();
		festa1.setCodFesta(2);
		festa1.setNomeFesta("festa2");

		Festa festa3 = festaTest();
		festa1.setCodFesta(3);
		festa1.setNomeFesta("festa3");

		List<Festa> festas = new ArrayList<Festa>();
		festas.add(festa1);
		festas.add(festa2);
		festas.add(festa3);

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(1);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Mockito.when(usuarioRepository.findById(1)).thenReturn(usuario1);
		Mockito.when(festaRepository.findByUsuarios(1)).thenReturn(festas);

		List<Festa> retorno = festaService.procurarFestasPorUsuario(1);

		assertEquals(festas.size(), retorno.size());
		assertEquals(true, retorno.containsAll(festas));
	}

	//add festa_____________________________________________________________________________________________________________________________________	
	@Test
	void addFestaUsuarioInexistenteTest() throws Exception{
		FestaTO festaTO = festaTOTest();
		int idUser = 1;

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Mockito.when(usuarioRepository.findById(idUser)).thenReturn(null);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		Mockito.when(festaRepository.getNextValMySequence()).thenReturn(2);
		Mockito.when(festaRepository.save(Mockito.any(Festa.class))).thenReturn(null);
		Mockito.when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(null);
		doNothing().when(grupoRepository).saveUsuarioGrupo(Mockito.any(Integer.class), Mockito.any(Integer.class));
		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.when(grupoService.addGrupo(Mockito.anyInt(), Mockito.anyString(), Mockito.anyBoolean(), Mockito.isNull())).thenReturn(criacaoGrupo());
		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(categoriaTest(),categoriaTest());

		Festa retorno = null;
		try {
			retorno = festaService.addFesta(festaTO, idUser, null);
		}catch(ValidacaoException e){
			retorno = null;
		};

		assertEquals(null, retorno);
	}

	@Test
	void addFestaErroTest() throws Exception{
		FestaTO festaTO = festaTOTest();
		festaTO.setCodSecundaria(1);
		festaTO.setCodPrimaria(1);
		int idUser = 1;

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Mockito.when(usuarioRepository.findById(idUser)).thenReturn(usuario1);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		Mockito.when(festaRepository.getNextValMySequence()).thenReturn(2);
		Mockito.when(festaRepository.save(Mockito.any(Festa.class))).thenReturn(null);
		Mockito.when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(null);
		doNothing().when(grupoRepository).saveUsuarioGrupo(Mockito.any(Integer.class), Mockito.any(Integer.class));
		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(categoriaTest(),categoriaTest());
		Mockito.when(grupoService.addGrupo(Mockito.anyInt(), Mockito.anyString(), Mockito.anyBoolean(), Mockito.isNull())).thenReturn(criacaoGrupo());
		Mockito.when(notificacaoService.getDataAtual()).thenReturn(LocalDateTime.of(2016, Month.JUNE, 22, 19, 9));

		Festa retorno = null;
		boolean erro = false;
		try {
			retorno = festaService.addFesta(festaTO, idUser, null);
		}catch(ValidacaoException e){
			erro = true;
		};

		assertEquals(null, retorno);
		assertEquals(true, erro);
	}

	@Test
	void addFestaErroCodPrimarioTest() throws Exception{
		FestaTO festaTO = festaTOTest();
		festaTO.setCodPrimaria(0);
		int idUser = 1;

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Mockito.when(usuarioRepository.findById(idUser)).thenReturn(usuario1);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		Mockito.when(festaRepository.getNextValMySequence()).thenReturn(2);
		Mockito.when(festaRepository.save(Mockito.any(Festa.class))).thenReturn(null);
		Mockito.when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(null);
		doNothing().when(grupoRepository).saveUsuarioGrupo(Mockito.any(Integer.class), Mockito.any(Integer.class));
		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(categoriaTest(),categoriaTest());
		Mockito.when(grupoService.addGrupo(Mockito.anyInt(), Mockito.anyString(), Mockito.anyBoolean(), Mockito.isNull())).thenReturn(criacaoGrupo());
		Mockito.when(notificacaoService.getDataAtual()).thenReturn(LocalDateTime.of(2016, Month.JUNE, 22, 19, 9));

		Festa retorno = null;
		boolean erro = false;
		try {
			retorno = festaService.addFesta(festaTO, idUser, null);
		}catch(ValidacaoException e){
			erro = true;
		};

		assertEquals(null, retorno);
		assertEquals(true, erro);
	}

	@Test
	void addFestaErroCategoriaNaoEncontradoTest() throws Exception{
		FestaTO festaTO = festaTOTest();
		int idUser = 1;

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Mockito.when(usuarioRepository.findById(idUser)).thenReturn(usuario1);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		Mockito.when(festaRepository.getNextValMySequence()).thenReturn(2);
		Mockito.when(festaRepository.save(Mockito.any(Festa.class))).thenReturn(null);
		Mockito.when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(null);
		doNothing().when(grupoRepository).saveUsuarioGrupo(Mockito.any(Integer.class), Mockito.any(Integer.class));
		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(null,categoriaTest());
		Mockito.when(grupoService.addGrupo(Mockito.anyInt(), Mockito.anyString(), Mockito.anyBoolean(), Mockito.isNull())).thenReturn(criacaoGrupo());
		Mockito.when(festaFactory.getFesta(Mockito.any(), Mockito.any())).thenReturn(festaTest());
		Mockito.when(notificacaoService.getDataAtual()).thenReturn(LocalDateTime.of(2016, Month.JUNE, 22, 19, 9));

		Festa retorno = null;
		boolean erro = false;
		try {
			retorno = festaService.addFesta(festaTO, idUser, null);
		}catch(ValidacaoException e){
			erro = true;
		};

		assertEquals(null, retorno);
		assertEquals(true, erro);
	}


	@Test
	void addFestavalidarUsuarioExceptionTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		int idUser = 1;

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario1);
		Mockito.when(festaRepository.findByNomeFesta(Mockito.anyString())).thenReturn(null);
		Mockito.when(festaRepository.getNextValMySequence()).thenReturn(2);
		Mockito.when(festaRepository.save(Mockito.any(Festa.class))).thenReturn(null);
		Mockito.when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(null);
		doNothing().when(grupoRepository).saveUsuarioGrupo(Mockito.any(Integer.class), Mockito.any(Integer.class));
		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(categoriaTest(),categoriaTest());
		Mockito.when(grupoService.addGrupo(Mockito.anyInt(), Mockito.anyString(), Mockito.anyBoolean(), Mockito.isNull())).thenReturn(criacaoGrupo());
		Mockito.when(festaFactory.getFesta(Mockito.any(), Mockito.any())).thenReturn(festaTest());
		Mockito.when(notificacaoService.getDataAtual()).thenReturn(LocalDateTime.of(2016, Month.JUNE, 22, 19, 9));

		Festa retorno = festaService.addFesta(festaTO, idUser,null);
		assertEquals(retorno.getCodFesta(), festaTO.getCodFesta());
		assertEquals(retorno.getNomeFesta(), festaTO.getNomeFesta());
		assertEquals(retorno.getHorarioFimFesta(), festaTO.getHorarioFimFesta());
		assertEquals(retorno.getHorarioInicioFesta(), festaTO.getHorarioInicioFesta());
	}
	
	@Test
	void addFestaValidarFesta1Test() throws Exception {
		FestaTO festaTO = festaTOTest();
		festaTO.setCodSecundaria(0);
		Festa festaTest = festaTest();
		int idUser = 1;

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario1);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.getNextValMySequence()).thenReturn(2);
		Mockito.when(festaRepository.save(Mockito.any(Festa.class))).thenReturn(null);
		Mockito.when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(null);
		doNothing().when(grupoRepository).saveUsuarioGrupo(Mockito.any(Integer.class), Mockito.any(Integer.class));
		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(categoriaTest(),categoriaTest());
		Mockito.when(grupoService.addGrupo(Mockito.anyInt(), Mockito.anyString(), Mockito.anyBoolean(), Mockito.isNull())).thenReturn(criacaoGrupo());
		Mockito.when(festaFactory.getFesta(Mockito.any(), Mockito.any())).thenReturn(festaTest());
		Mockito.when(notificacaoService.getDataAtual()).thenReturn(LocalDateTime.of(2016, Month.JUNE, 22, 19, 9));

		Festa retorno = festaService.addFesta(festaTO, idUser,null);
		assertEquals(retorno.getCodFesta(), festaTO.getCodFesta());
		assertEquals(retorno.getNomeFesta(), festaTO.getNomeFesta());
		assertEquals(retorno.getHorarioFimFesta(), festaTO.getHorarioFimFesta());
		assertEquals(retorno.getHorarioInicioFesta(), festaTO.getHorarioInicioFesta());
	}

	@Test
	void addFestaValidarFesta2Test() throws Exception {
		FestaTO festaTO = festaTOTest();
		Festa festaTest = festaTest();
		int idUser = 1;
		festaTO.setCodFesta(33);
		festaTO.setNomeFesta("Batata2");

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");
		
		Festa festaCreateByFactory = festaTest();
		festaCreateByFactory.setCodFesta(33);
		festaCreateByFactory.setNomeFesta("Batata2");

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario1);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.getNextValMySequence()).thenReturn(33);
		Mockito.when(festaRepository.save(Mockito.any(Festa.class))).thenReturn(null);
		Mockito.when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(null);
		doNothing().when(grupoRepository).saveUsuarioGrupo(Mockito.any(Integer.class), Mockito.any(Integer.class));
		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(categoriaTest(),categoriaTest());
		Mockito.when(grupoService.addGrupo(Mockito.anyInt(), Mockito.anyString(), Mockito.anyBoolean(), Mockito.isNull())).thenReturn(criacaoGrupo());
		Mockito.when(festaFactory.getFesta(Mockito.any(), Mockito.any())).thenReturn(festaCreateByFactory);
		Mockito.when(notificacaoService.getDataAtual()).thenReturn(LocalDateTime.of(2016, Month.JUNE, 22, 19, 9));

		Festa retorno = festaService.addFesta(festaTO, idUser, null);
		assertEquals(retorno.getCodFesta(), festaTO.getCodFesta());
		assertEquals(retorno.getNomeFesta(), festaTO.getNomeFesta());
		assertEquals(retorno.getHorarioFimFesta(), festaTO.getHorarioFimFesta());
		assertEquals(retorno.getHorarioInicioFesta(), festaTO.getHorarioInicioFesta());
	}
	
	@Test
	void addFestaSecundariaSucessTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		festaTO.setCodSecundaria(0);
		int idUser = 1;

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario1);
		Mockito.when(festaRepository.findByNomeFesta(Mockito.anyString())).thenReturn(null);
		Mockito.when(festaRepository.getNextValMySequence()).thenReturn(2);
		Mockito.when(festaRepository.save(Mockito.any(Festa.class))).thenReturn(null);
		Mockito.when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(null);
		doNothing().when(grupoRepository).saveUsuarioGrupo(Mockito.any(Integer.class), Mockito.any(Integer.class));
		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(categoriaTest(),categoriaTest());
		Mockito.when(grupoService.addGrupo(Mockito.anyInt(), Mockito.anyString(), Mockito.anyBoolean(), Mockito.isNull())).thenReturn(criacaoGrupo());
		Mockito.when(festaFactory.getFesta(Mockito.any(), Mockito.any())).thenReturn(festaTest());
		Mockito.when(notificacaoService.getDataAtual()).thenReturn(LocalDateTime.of(2016, Month.JUNE, 22, 19, 9));

		Festa retorno = festaService.addFesta(festaTO, idUser, null);
		assertEquals(retorno.getCodFesta(), festaTO.getCodFesta());
		assertEquals(retorno.getNomeFesta(), festaTO.getNomeFesta());
		assertEquals(retorno.getHorarioFimFesta(), festaTO.getHorarioFimFesta());
		assertEquals(retorno.getHorarioInicioFesta(), festaTO.getHorarioInicioFesta());
	}

	//delete Festa_____________________________________________________________________________________________________________________________________	

	@Test
	void deleteFestaTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		Festa festaTest = festaTest();

		List<Grupo> grupos = new ArrayList<Grupo>();

		Grupo grupo1 = this.criacaoGrupo();
		grupo1.setCodGrupo(1);
		grupo1.setFesta(festaTest);
		grupo1.setNomeGrupo("Grupo1");
		grupos.add(grupo1);

		Grupo grupo2 = this.criacaoGrupo();
		grupo2.setCodGrupo(2);
		grupo2.setFesta(festaTest);
		grupo2.setNomeGrupo("Grupo2");
		grupos.add(grupo2);

		int idUser = 1;

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Set<CategoriasFesta> categoriasFesta = new HashSet<CategoriasFesta>();
		categoriasFesta.add(categoriaFestaTest());
		
		List<Estoque> estoques = new ArrayList<>();
		estoques.add(estoqueTest());
		
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuarioTest());
		
		List<Integer> cods = new ArrayList<>();
		cods.add(1);

		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festaTest);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());
		Mockito.when(grupoRepository.findGruposFesta(festaTO.getCodFesta())).thenReturn(grupos);
		Mockito.when(categoriasFestaRepository.findCategoriasFesta(Mockito.anyInt())).thenReturn(categoriasFesta);
		
		doNothing().when(grupoService).deleteCascade(Mockito.anyInt(), Mockito.anyInt());
		doNothing().when(estoqueService).deleteCascade(Mockito.anyInt());
		doNothing().when(loteService).deleteCascade(Mockito.any());

		doNothing().when(festaRepository).deleteById(Mockito.any(Integer.class));
		doNothing().when(categoriasFestaRepository).deleteAll(Mockito.anySet());
		doNothing().when(areaProblemaRepository).deleteByCodFesta(Mockito.anyInt());
		doNothing().when(areaRepository).deleteByCodFesta(Mockito.anyInt());
		doNothing().when(areaProblemaFluxoRepository).deleteByCodFesta(Mockito.anyInt());
		doNothing().when(questionarioFormsRepository).deleteByCodFesta(Mockito.anyInt());
		doNothing().when(infoIntegracaoRepository).deleteByCodFesta(Mockito.anyInt());
		doNothing().when(cupomRepository).deleteByCodFesta(Mockito.anyInt());
		doNothing().when(dadoBancarioRepository).deleteByCodFesta(Mockito.anyInt());


		festaService.deleteFesta(festaTO.getCodFesta(), idUser);

	}

	@Test
	void deleteFestavalidarPermissaoUsuario2Test() throws Exception {
		FestaTO festaTO = festaTOTest();
		Festa festaTest = festaTest();
		festaTest.setStatusFesta(TipoStatusFesta.FINALIZADO.getValor());
		
		List<Grupo> grupos = new ArrayList<Grupo>();

		Grupo grupo1 = this.criacaoGrupo();
		grupo1.setCodGrupo(1);
		grupo1.setFesta(festaTest);
		grupo1.setNomeGrupo("Grupo1");
		grupos.add(grupo1);

		Grupo grupo2 = this.criacaoGrupo();
		grupo2.setCodGrupo(2);
		grupo2.setFesta(festaTest);
		grupo2.setNomeGrupo("Grupo2");
		grupos.add(grupo2);

		int idUser = 1;

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Set<CategoriasFesta> categoriasFesta = new HashSet<CategoriasFesta>();
		categoriasFesta.add(categoriaFestaTest());
		
		List<Estoque> estoques = new ArrayList<>();
		estoques.add(estoqueTest());
		
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuarioTest());
		
		List<Integer> cods = new ArrayList<>();
		cods.add(1);

		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festaTest);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());
		Mockito.when(grupoRepository.findGruposFesta(festaTO.getCodFesta())).thenReturn(grupos);
		Mockito.when(categoriasFestaRepository.findCategoriasFesta(Mockito.anyInt())).thenReturn(categoriasFesta);
		
		doNothing().when(grupoService).deleteCascade(Mockito.anyInt(), Mockito.anyInt());
		doNothing().when(estoqueService).deleteCascade(Mockito.anyInt());
		doNothing().when(loteService).deleteCascade(Mockito.any());

		doNothing().when(festaRepository).deleteById(Mockito.any(Integer.class));
		doNothing().when(categoriasFestaRepository).deleteAll(Mockito.anySet());
		doNothing().when(areaProblemaRepository).deleteByCodFesta(Mockito.anyInt());
		doNothing().when(areaRepository).deleteByCodFesta(Mockito.anyInt());
		doNothing().when(areaProblemaFluxoRepository).deleteByCodFesta(Mockito.anyInt());
		doNothing().when(questionarioFormsRepository).deleteByCodFesta(Mockito.anyInt());
		doNothing().when(infoIntegracaoRepository).deleteByCodFesta(Mockito.anyInt());
		doNothing().when(cupomRepository).deleteByCodFesta(Mockito.anyInt());
		doNothing().when(dadoBancarioRepository).deleteByCodFesta(Mockito.anyInt());


		festaService.deleteFesta(festaTO.getCodFesta(), idUser);

	}

	//update Festa__________________________________________________________________________________________________________________________________________________
	//estou usando o update para testar métodos privados do service	

//	@Test
//	void updateFestaTest() throws Exception {
//		FestaTO festaTO = festaTOTest();
//		Festa festaTest = festaTest();
//
//		Festa festaTest2 = festaTest();
//		festaTest2.setNomeFesta("loucura");
//		festaTest2.setDescricaoFesta("5x mais adrenalina!!!");
//
//		int idUser = 1;
//
//		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
//		usuario1.setCodUsuario(idUser);
//		usuario1.setNomeUser("Aires_qualquer_e_ficticio");
//
//		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(festaTest);
//		Mockito.when(festaRepository.findById(festaTO.getCodFesta())).thenReturn(festaTest);
//		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
//		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festaTest);
//		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festaTest);
//		
//		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());
//
//		doNothing().when(categoriasFestaRepository).delete(Mockito.any(CategoriasFesta.class));
//		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
//		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(categoriaTest(), categoriaTest());
//
//		Mockito.when(festaRepository.save(festaTest)).thenReturn(festaTest2);
//
//		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());
//
//		Mockito.when(categoriasFestaRepository.findCategoriasFestaTipoCategoria(Mockito.anyInt(), Mockito.anyString())).thenReturn(categoriaFestaTest());
//
//		Mockito.when(notificacaoService.getDataAtual()).thenReturn(LocalDateTime.of(2016, Month.JUNE, 22, 19, 9));
//		
//		byte[] bytesTeste = "Any String you want".getBytes();
//		
//		MultipartFile file = new MockMultipartFile("file",
//	            "teste", "text/plain", bytesTeste);
//		
//		Festa retorno = festaService.updateFesta(festaTO, idUser, file);
//
//		assertEquals(retorno.getCodEnderecoFesta(), festaTest.getCodEnderecoFesta());
//		assertEquals(retorno.getOrganizador(), festaTest.getOrganizador());
//	}
	
	@Test
	void updateFestafestaFinalizadaDelete2Test() throws Exception {
		FestaTO festaTO = festaTOTest();
		Festa festaTest = festaTest();
		festaTest.setStatusFesta(TipoStatusFesta.FINALIZADO.getValor());

		Festa festaTest2 = festaTest();
		festaTest2.setNomeFesta("loucura");
		festaTest2.setDescricaoFesta("5x mais adrenalina!!!");

		int idUser = 1;

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findById(festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festaTest);

		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());

		doNothing().when(categoriasFestaRepository).delete(Mockito.any(CategoriasFesta.class));
		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(categoriaTest(), categoriaTest());

		Mockito.when(festaRepository.save(festaTest)).thenReturn(festaTest2);

		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());

		Mockito.when(categoriasFestaRepository.findCategoriasFestaTipoCategoria(Mockito.anyInt(), Mockito.anyString())).thenReturn(categoriaFestaTest());
		
		boolean retornoB = true;
		try {
			festaService.updateFesta(festaTO, idUser, null);
		}catch(ValidacaoException e){
			retornoB = false;
		};

		assertEquals(false, retornoB);
	}
	
	@Test
	void updateFestavalidarPermissaoUsuario1Test() throws Exception {
		FestaTO festaTO = festaTOTest();
		Festa festaTest = festaTest();
		festaTest.setStatusFesta(TipoStatusFesta.INICIADO.getValor());

		Festa festaTest2 = festaTest();
		festaTest2.setNomeFesta("loucura");
		festaTest2.setDescricaoFesta("5x mais adrenalina!!!");

		int idUser = 1;

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findById(festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festaTest);

		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());

		doNothing().when(categoriasFestaRepository).delete(Mockito.any(CategoriasFesta.class));
		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(categoriaTest(), categoriaTest());

		Mockito.when(festaRepository.save(festaTest)).thenReturn(festaTest2);

		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());

		Mockito.when(categoriasFestaRepository.findCategoriasFestaTipoCategoria(Mockito.anyInt(), Mockito.anyString())).thenReturn(categoriaFestaTest());

		boolean retornoB = true;
		try {
			festaService.updateFesta(festaTO, idUser, null);
		}catch(ValidacaoException e){
			retornoB = false;
		};

		assertEquals(false, retornoB);
	}
	
	@Test
	void updateFestaCategoriaNPrimarioTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		Festa festaTest = festaTest();
		CategoriasFesta categoriaFesta = categoriaFestaTest();
		categoriaFesta.setTipCategoria(TipoCategoria.SECUNDARIO.getDescricao());
		

		Festa festaTest2 = festaTest();
		festaTest2.setNomeFesta("loucura");
		festaTest2.setDescricaoFesta("5x mais adrenalina!!!");

		int idUser = 1;

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findById(festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festaTest);
		
		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());

		doNothing().when(categoriasFestaRepository).delete(Mockito.any(CategoriasFesta.class));
		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(categoriaTest(), categoriaTest());

		Mockito.when(festaRepository.save(festaTest)).thenReturn(festaTest2);

		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());

		Mockito.when(categoriasFestaRepository.findCategoriasFestaTipoCategoria(Mockito.anyInt(), Mockito.anyString())).thenReturn(categoriaFesta);
		
		Mockito.when(notificacaoService.getDataAtual()).thenReturn(LocalDateTime.of(2016, Month.JUNE, 22, 19, 9));

		Festa retorno = festaService.updateFesta(festaTO, idUser, null);

		assertEquals(retorno.getCodEnderecoFesta(), festaTest.getCodEnderecoFesta());
		assertEquals(retorno.getOrganizador(), festaTest.getOrganizador());
	}
	
	@Test
	void updateFestaCategoriaNullTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		Festa festaTest = festaTest();

		Festa festaTest2 = festaTest();
		festaTest2.setNomeFesta("loucura");
		festaTest2.setDescricaoFesta("5x mais adrenalina!!!");

		int idUser = 1;

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findById(festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festaTest);
		
		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());

		doNothing().when(categoriasFestaRepository).delete(Mockito.any(CategoriasFesta.class));
		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(null);

		Mockito.when(festaRepository.save(festaTest)).thenReturn(festaTest2);

		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());

		Mockito.when(categoriasFestaRepository.findCategoriasFestaTipoCategoria(Mockito.anyInt(), Mockito.anyString())).thenReturn(categoriaFestaTest());
		
		Mockito.when(notificacaoService.getDataAtual()).thenReturn(LocalDateTime.of(2016, Month.JUNE, 22, 19, 9));

		Festa retorno = festaService.updateFesta(festaTO, idUser, null);

		assertEquals(retorno.getCodEnderecoFesta(), festaTest.getCodEnderecoFesta());
		assertEquals(retorno.getOrganizador(), festaTest.getOrganizador());
	}
	
	@Test
	void updateFestaCategoriaNullECatFestaNullTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		Festa festaTest = festaTest();

		Festa festaTest2 = festaTest();
		festaTest2.setNomeFesta("loucura");
		festaTest2.setDescricaoFesta("5x mais adrenalina!!!");

		int idUser = 1;

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findById(festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festaTest);
		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());

		doNothing().when(categoriasFestaRepository).delete(Mockito.any(CategoriasFesta.class));
		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(null);
		Mockito.when(notificacaoService.getDataAtual()).thenReturn(LocalDateTime.of(2016, Month.JUNE, 22, 19, 9));

		Mockito.when(festaRepository.save(festaTest)).thenReturn(festaTest2);

		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());

		Mockito.when(categoriasFestaRepository.findCategoriasFestaTipoCategoria(Mockito.anyInt(), Mockito.anyString())).thenReturn(categoriaFestaTest(), null);

		Festa retorno = festaService.updateFesta(festaTO, idUser, null);

		assertEquals(retorno.getCodEnderecoFesta(), festaTest.getCodEnderecoFesta());
		assertEquals(retorno.getOrganizador(), festaTest.getOrganizador());
	}

	@Test
	void updateFestaAlterandoInfoTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		festaTO.setNomeFesta("teste124215");
		festaTO.setCodEnderecoFesta("jasdgudhashdasoidjhas8udhasi");
		festaTO.setDescOrganizador("123214214214214214214");
		festaTO.setOrganizador("teste1254152");
		festaTO.setDescricaoFesta("askjdhasodhjasudhiasdauisdhaisduash");
		festaTO.setHorarioInicioFesta(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		festaTO.setHorarioFimFesta(LocalDateTime.of(2016, Month.JUNE, 24, 22, 11));

		Festa festaTest = festaTest();

		Festa festaTest2 = festaTest();
		festaTest2.setNomeFesta("loucura");
		festaTest2.setDescricaoFesta("5x mais adrenalina!!!");

		int idUser = 1;

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festaTest);
		
		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());

		doNothing().when(categoriasFestaRepository).delete(Mockito.any(CategoriasFesta.class));
		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(categoriaTest(), categoriaTest());

		Mockito.when(festaRepository.save(festaTest)).thenReturn(festaTest2);
		Mockito.when(notificacaoService.getDataAtual()).thenReturn(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));

		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());

		Mockito.when(categoriasFestaRepository.findCategoriasFestaTipoCategoria(Mockito.anyInt(), Mockito.anyString())).thenReturn(categoriaFestaTest(), null);

		Festa retorno = festaService.updateFesta(festaTO, idUser, null);

		assertEquals(retorno.getCodEnderecoFesta(), festaTest.getCodEnderecoFesta());
		assertEquals(retorno.getOrganizador(), festaTest.getOrganizador());
	}

	@Test
	void updateFestaCategoriaTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		festaTO.setCodPrimaria(3);
		Festa festaTest = festaTest();

		Festa festaTest2 = festaTest();
		festaTest2.setNomeFesta("loucura");
		festaTest2.setDescricaoFesta("5x mais adrenalina!!!");

		int idUser = 1;

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findById(festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festaTest);
		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());

		doNothing().when(categoriasFestaRepository).delete(Mockito.any(CategoriasFesta.class));
		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(categoriaTest(), categoriaTest());

		Mockito.when(festaRepository.save(festaTest)).thenReturn(festaTest2);

		Mockito.when(notificacaoService.getDataAtual()).thenReturn(LocalDateTime.of(2016, Month.JUNE, 22, 19, 9));
		
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());

		Mockito.when(categoriasFestaRepository.findCategoriasFestaTipoCategoria(Mockito.anyInt(), Mockito.anyString())).thenReturn(categoriaFestaTest(), categoriaFestaTest());

		Festa retorno = festaService.updateFesta(festaTO, idUser, null);

		assertEquals(retorno.getCodEnderecoFesta(), festaTest.getCodEnderecoFesta());
		assertEquals(retorno.getOrganizador(), festaTest.getOrganizador());
	}

	@Test
	void updateFestaSemPermissaoTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		Festa festaTest = festaTest();

		Festa festaTest2 = festaTest();
		festaTest2.setNomeFesta("loucura");
		festaTest2.setDescricaoFesta("5x mais adrenalina!!!");

		int idUser = 1;
		
		List<Grupo> grupos = new ArrayList<>();

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findById(festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festaTest);

		doNothing().when(categoriasFestaRepository).delete(Mockito.any(CategoriasFesta.class));
		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(categoriaTest(), categoriaTest());

		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());

		Mockito.when(festaRepository.save(festaTest)).thenReturn(festaTest2);

		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);

		Festa retorno = null;
		try {
			retorno = festaService.updateFesta(festaTO, idUser, null);
		}catch(ValidacaoException e){
			retorno = null;
		};

		assertEquals(null, retorno);

	}

	@Test
	void updateFestaExceptionTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		Festa festaTest = festaTest();

		Festa festaTest2 = festaTest();
		festaTest2.setNomeFesta("loucura");
		festaTest2.setDescricaoFesta("5x mais adrenalina!!!");

		int idUser = 1;

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findById(festaTO.getCodFesta())).thenReturn(null, festaTest);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);

		doNothing().when(categoriasFestaRepository).delete(Mockito.any(CategoriasFesta.class));
		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(categoriaTest(), categoriaTest());

		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());

		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());

		Mockito.when(festaRepository.save(festaTest)).thenReturn(festaTest2);

		Festa retorno = null;
		try {
			retorno = festaService.updateFesta(festaTO, idUser, null);
		}catch(ValidacaoException e){
			retorno = null;
		};

		assertEquals(null, retorno);

	}

	@Test
	void updateFestaUsuarioSemPermissaoTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		Festa festaTest = festaTest();

		Festa festaTest2 = festaTest();
		festaTest2.setNomeFesta("loucura");
		festaTest2.setDescricaoFesta("5x mais adrenalina!!!");

		int idUser = 1;

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");
		
		List<Grupo> grupos = new ArrayList<>();

		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(null);
		Mockito.when(festaRepository.findById(festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festaTest);

		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());

		doNothing().when(categoriasFestaRepository).delete(Mockito.any(CategoriasFesta.class));
		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(categoriaTest(), categoriaTest());

		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);

		Mockito.when(festaRepository.save(festaTest)).thenReturn(festaTest2);

		Festa retorno = null;
		try {
			retorno = festaService.updateFesta(festaTO, idUser, null);
		}catch(ValidacaoException e){
			retorno = null;
		};

		assertEquals(null, retorno);
	}

	@Test
	void updateFestaValidacaoDataErroTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		festaTO.setHorarioInicioFesta(LocalDateTime.of(2046, Month.JUNE, 22, 19, 10));
		festaTO.setHorarioFimFesta(LocalDateTime.of(2006, Month.JUNE, 22, 19, 10));

		Festa festaTest = festaTest();
		festaTest.setHorarioInicioFesta(LocalDateTime.of(2046, Month.JUNE, 22, 19, 10));
		festaTest.setHorarioFimFesta(LocalDateTime.of(2006, Month.JUNE, 22, 19, 10));

		Festa festaTest2 = festaTest();
		festaTest2.setNomeFesta("loucura");
		festaTest2.setDescricaoFesta("5x mais adrenalina!!!");

		int idUser = 1;

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findById(festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festaTest);

		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());

		doNothing().when(categoriasFestaRepository).delete(Mockito.any(CategoriasFesta.class));
		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(categoriaTest(), categoriaTest());

		Mockito.when(festaRepository.save(festaTest)).thenReturn(festaTest2);

		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());

		Festa retorno = null;
		try {
			retorno = festaService.updateFesta(festaTO, idUser, null);
		}catch(ValidacaoException e){
			retorno = null;
		};

		assertEquals(null, retorno);
	}

	@Test
	void updateFestaValidacaoDataSecErroTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		festaTO.setHorarioInicioFesta(LocalDateTime.of(2046, Month.JUNE, 22, 19, 10)); 
		festaTO.setHorarioFimFesta(LocalDateTime.of(2046, Month.JUNE, 22, 19, 10));

		Festa festaTest = festaTest();
		festaTest.setHorarioInicioFesta(LocalDateTime.of(2046, Month.JUNE, 22, 19, 10));
		festaTest.setHorarioFimFesta(LocalDateTime.of(2046, Month.JUNE, 22, 19, 10));

		Festa festaTest2 = festaTest();
		festaTest2.setNomeFesta("loucura");
		festaTest2.setDescricaoFesta("5x mais adrenalina!!!");

		int idUser = 1;

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());

		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findById(festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festaTest);

		doNothing().when(categoriasFestaRepository).delete(Mockito.any(CategoriasFesta.class));
		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(categoriaTest(), categoriaTest());

		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());

		Mockito.when(festaRepository.save(festaTest)).thenReturn(festaTest2);

		Festa retorno = null;
		try {
			retorno = festaService.updateFesta(festaTO, idUser, null);
		}catch(ValidacaoException e){
			retorno = null;
		};

		assertEquals(null, retorno);
	}

	@Test
	void updateFestaNomeDuplicadoTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		Festa festaTest = festaTest();

		//festa com nome duplicado
		Festa nomeDuplicado = festaTest();
		nomeDuplicado.setCodFesta(25);

		Festa festaTest2 = festaTest();
		festaTest2.setNomeFesta("loucura");
		festaTest2.setDescricaoFesta("5x mais adrenalina!!!");

		int idUser = 1;

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findById(festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(nomeDuplicado);
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festaTest);

		doNothing().when(categoriasFestaRepository).delete(Mockito.any(CategoriasFesta.class));
		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(categoriaTest(), categoriaTest());

		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());

		Mockito.when(festaRepository.save(festaTest)).thenReturn(festaTest2);
		Mockito.when(notificacaoService.getDataAtual()).thenReturn(LocalDateTime.of(2016, Month.JUNE, 22, 19, 9));

		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());

		Festa retorno = null;
		try {
			retorno = festaService.updateFesta(festaTO, idUser, null);
		}catch(ValidacaoException e){
			retorno = null;
		};

		assertEquals(null, retorno);
	}

	@Test
	void updateFestaEnderecoNullTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		festaTO.setCodEnderecoFesta(null);

		Festa festaTest = festaTest();
		festaTest.setCodEnderecoFesta(null);

		//festa com nome duplicado
		Festa nomeDuplicado = festaTest();
		nomeDuplicado.setCodFesta(25);

		Festa festaTest2 = festaTest();
		festaTest2.setNomeFesta("loucura");
		festaTest2.setDescricaoFesta("5x mais adrenalina!!!");

		int idUser = 1;

		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");

		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findById(festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festaTest);

		doNothing().when(categoriasFestaRepository).delete(Mockito.any(CategoriasFesta.class));
		doNothing().when(categoriasFestaRepository).addCategoriasFesta(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		Mockito.when(categoriaRepository.findByCodCategoria(Mockito.anyInt())).thenReturn(categoriaTest(), categoriaTest());

		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());
		
		Mockito.when(notificacaoService.getDataAtual()).thenReturn(LocalDateTime.of(2016, Month.JUNE, 22, 19, 9));

		Mockito.when(festaRepository.save(festaTest)).thenReturn(festaTest2);

		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criacaoGrupos());

		Festa retorno = null;
		try {
			retorno = festaService.updateFesta(festaTO, idUser, null);
		}catch(ValidacaoException e){
			retorno = null;
		};

		assertEquals(null, retorno);
	}

	//procurar festa____________________________________________________________________________________________________________________________________
	@Test
	void procurarFestaTest() throws Exception {
		Festa festaTest = festaTest();

		Mockito.when(festaRepository.findByCodFesta(Mockito.any(Integer.class))).thenReturn(festaTest);

		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(usuarioTest());

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		Festa retorno = festaService.procurarFesta(1, 2);

		assertNotNull(retorno);
	}

	@Test
	void procurarFestaErroTest() throws Exception{
		Festa festaTest = festaTest();

		Usuario usuario = usuarioTest();

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario);

		Mockito.when(festaRepository.findByCodFesta(Mockito.any(Integer.class))).thenReturn(festaTest);

		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(null);

		boolean erro = false;

		try {
			festaService.procurarFesta(1, 2);
		} catch (ValidacaoException e) {
			erro = true;
		}

		assertEquals(true, erro);
	}

	@Test
	void procurarFestaUsuarioNaoRelacionadoFestaErroTest() throws Exception{
		Festa festaTest = festaTest();

		Mockito.when(usuarioRepository.findById(Mockito.any(Integer.class))).thenReturn(null);

		Mockito.when(festaRepository.findByCodFesta(Mockito.any(Integer.class))).thenReturn(festaTest);

		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(null);

		boolean erro = false;

		try {
			festaService.procurarFesta(1, 2);
		} catch (ValidacaoException e) {
			erro = true;
		}

		assertEquals(true, erro);
	}

	// funcionalidade Festa____________________________________________________________________________________________________________________________________
	@Test
	void funcionalidadeFesta() throws Exception {
		List<String> expected = new ArrayList<>();
		expected.add("Estagiário senior em iluminação");

		Mockito.when(grupoRepository.findFuncionalidade(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(expected);

		String retorno = festaService.funcionalidadeFesta(1, 1);

		assertEquals(retorno, expected.get(0));
	}

	// mudar Status festa_______________________________________________________________________________________________________________________________________

	@Test
	void mudarStatusFestaErroTipoStatus() throws Exception {

		Festa festaTest = festaTest();

		Mockito.when(festaRepository.findByCodFesta(Mockito.any(Integer.class))).thenReturn(festaTest);

		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(usuarioTest());

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		boolean erro = false;

		String mensagemErro = null;

		try {
			festaService.mudarStatusFesta(1,"A",2);
		} catch (ValidacaoException e) {
			erro = true;
			mensagemErro = e.getMessage();
		}

		assertEquals(true, erro);

		assertEquals("STATERRA", mensagemErro);


	}

	@Test
	void mudarStatusFestaErroStatusNaoAlterado() throws Exception {

		Festa festaTest = festaTest();

		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(usuarioTest());

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festaTest);

		boolean erro = false;

		String mensagemErro = null;

		try {
			festaService.mudarStatusFesta(1,"p",2);
		} catch (ValidacaoException e) {
			erro = true;
			mensagemErro = e.getMessage();
		}

		assertEquals(true, erro);

		assertEquals("STANMUDA", mensagemErro);


	}

	@Test
	void mudarStatusFestaErroStatusColocadoFinalizarSucesso() throws Exception {// erro para quando o usuário tenta colocar de preparação para finalizado o status da festa

		Festa festaTest = festaTest();

		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(usuarioTest());

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festaTest);

		boolean erro = false;

		String mensagemErro = null;

		try {
			festaService.mudarStatusFesta(1,"F",2);
		} catch (ValidacaoException e) {
			erro = true;
			mensagemErro = e.getMessage();
		}

		assertEquals(true, erro);	

		assertEquals("FSTANINI", mensagemErro);

	}

	@Test
	void mudarStatusFestaSucesso() throws Exception {
		
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuarioTest());

		Festa festaTest = festaTest();

		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(usuarioTest());

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festaTest);
		
		Mockito.when(usuarioRepository.findByIdFesta(Mockito.anyInt())).thenReturn(usuarios);

		boolean erro = false;

			festaService.mudarStatusFesta(1,"I",2);

		assertEquals(false, erro);		

	}
	
	@Test
	void mudarStatusFestaFestaNPreparacaoSucesso() throws Exception {

		Festa festaTest = festaTest();
		festaTest.setStatusFesta(TipoStatusFesta.FINALIZADO.getValor());

		Mockito.when(usuarioRepository.findBycodFestaAndUsuario(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(usuarioTest());

		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuarioTest());

		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festaTest);

		boolean erro = false;

		try {
			festaService.mudarStatusFesta(1,"I",2);
		} catch (ValidacaoException e) {
			erro = true;
		}

		assertEquals(false, erro);		

	}

	@Test
	void procurarFestaConvidadoSucesso() throws Exception {

		Festa festaTest = festaTest();

		Mockito.when(festaRepository.findFestaByCodConvidadoAndCodGrupo(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(festaTest);
		
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(criacaoGrupo());

		Mockito.when(conviteFestaFactory.getConviteFestaTO(Mockito.any(), Mockito.any())).thenReturn(new ConviteFestaTO());
		
		ConviteFestaTO conviteFestaTO = festaService.procurarFestaConvidado(1,2);

		assertEquals(true, conviteFestaTO != null);

	}
	
	@Test
	void procurarFestaConvidadoErroGrupoNull() throws Exception {

		Festa festaTest = festaTest();

		Mockito.when(festaRepository.findFestaByCodConvidadoAndCodGrupo(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(festaTest);
		
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(null);

		Mockito.when(conviteFestaFactory.getConviteFestaTO(Mockito.any(), Mockito.any())).thenReturn(new ConviteFestaTO());
		
		boolean erro = false;
		
		try {
			festaService.procurarFestaConvidado(1,2);
		} catch (ValidacaoException e) {
			erro = true;
		}
		
		assertEquals(true, erro);

	}
	
	@Test
	void procurarFestaConvidadoErroFestaNull() throws Exception {

		Mockito.when(festaRepository.findFestaByCodConvidadoAndCodGrupo(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(null);
		
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(criacaoGrupo());

		Mockito.when(conviteFestaFactory.getConviteFestaTO(Mockito.any(), Mockito.any())).thenReturn(new ConviteFestaTO());
		
		boolean erro = false;
		
		try {
			festaService.procurarFestaConvidado(1,2);
		} catch (ValidacaoException e) {
			erro = true;
		}
		
		assertEquals(true, erro);

	}
	
	@Test
	void validarFestaFinalizadaSucessTest() throws Exception {

		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festaTest());
		
		festaService.validarFestaFinalizada(2);

	}
	
	@Test
	void validarFestaFinalizadaErroTest() throws Exception {
		Festa festa = festaTest();
		festa.setStatusFesta(TipoStatusFesta.FINALIZADO.getValor());

		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa);
		
		boolean erro = false;
		
		try {
			festaService.validarFestaFinalizada(2);
		} catch (ValidacaoException e) {
			erro = true;
		}
		
		assertEquals(true, erro);

	}
	
	@Test
	void testValidarFestaInicializadaPrepErroTest() throws Exception{
		Festa festa = festaTest();
		
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa);
		
		boolean erro = false;
		
		try {
			festaService.validarFestaInicializadaPrep(2);
		} catch (ValidacaoException e) {
			erro = true;
		}
		
		assertEquals(true, erro);
	}
	
	@Test
	void testValidarFestaInicializadaFinalErroTest() throws Exception{
		Festa festa = festaTest();
		festa.setStatusFesta(TipoStatusFesta.FINALIZADO.getValor());
		
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa);
		
		boolean erro = false;
		
		try {
			festaService.validarFestaInicializadaFinal(2);
		} catch (ValidacaoException e) {
			erro = true;
		}
		
		assertEquals(true, erro);
	}
	
	@Test
	void validarFestaInicinalizadaPrepSucessTest() throws Exception {
	
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festaIniTest());
		
		festaService.validarFestaInicializadaPrep(2);

	}
	
	@Test
	void validarFestaInicinalizadaFinalSucessTest() throws Exception {

		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festaIniTest());
		
		festaService.validarFestaInicializadaFinal(2);

	}
	
	@Test
	void validarProdEstoqueIniciadaSucesso() throws Exception{

		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		itemEstoqueTO.setQuantidadeMax(200);
		itemEstoqueTO.setQuantidadeAtual(10);
		itemEstoqueTO.setPorcentagemMin(20);

		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festaTest());	

		festaService.validarProdEstoqueIniciada(itemEstoqueTO, 2);
		
	}
	
	@Test
	void validarProdEstoqueIniciadaElseSucesso() throws Exception{

		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		itemEstoqueTO.setQuantidadeMax(200);
		itemEstoqueTO.setQuantidadeAtual(10);
		itemEstoqueTO.setPorcentagemMin(20);
		
		Festa festa = festaTest();
		festa.setStatusFesta(TipoStatusFesta.INICIADO.getValor());
		
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa);	
		Mockito.when(produtoService.validarProdutoEstoque(Mockito.anyInt(), Mockito.anyInt())).thenReturn(itemEstoqueTest());	
		
		boolean erro = false;
		
		try {
			festaService.validarProdEstoqueIniciada(itemEstoqueTO, 2);
		} catch (ValidacaoException e) {
			erro = true;
		}
		
		assertEquals(true, erro);
		
	}
	
	@Test
	void validarProdEstoqueIniciadaElse1Sucesso() throws Exception{

		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		itemEstoqueTO.setQuantidadeMax(200);
		itemEstoqueTO.setQuantidadeAtual(10);
		itemEstoqueTO.setPorcentagemMin(20);
		
		Festa festa = festaTest();
		festa.setStatusFesta(TipoStatusFesta.FINALIZADO.getValor());
		
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa);	
		Mockito.when(produtoService.validarProdutoEstoque(Mockito.anyInt(), Mockito.anyInt())).thenReturn(itemEstoqueTest());	
		
		festaService.validarProdEstoqueIniciada(itemEstoqueTO, 2);
	}
	
	@Test
	void validarProdEstoqueIniciadaElse3Sucesso() throws Exception{

		ItemEstoqueTO itemEstoqueTO = itemEstoqueTOTest();
		itemEstoqueTO.setQuantidadeMax(200);
		itemEstoqueTO.setQuantidadeAtual(itemEstoqueTest().getQuantidadeAtual());
		itemEstoqueTO.setPorcentagemMin(20);
		
		Festa festa = festaTest();
		festa.setStatusFesta(TipoStatusFesta.INICIADO.getValor());
		
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa);	
		Mockito.when(produtoService.validarProdutoEstoque(Mockito.anyInt(), Mockito.anyInt())).thenReturn(itemEstoqueTest());	
		
		festaService.validarProdEstoqueIniciada(itemEstoqueTO, 2);
	}
	
	@Test
	void getNotificacaoMudancaStatusSucesso() throws Exception{
		
		Festa festa = festaTest();
		festa.setStatusFesta(TipoStatusFesta.INICIADO.getValor());
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(festa);	
		Mockito.when(notificacaoMudancaStatusFactory.getNotificacaoMudancaStatus(Mockito.any())).thenReturn(notificacaoMudancaoStatusTOTest());
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festa);
		
		NotificacaoMudancaStatusTO notificacaoMudancaoStatusTO = festaService.getNotificacaoMudancaStatus(2);
		assertEquals(true, notificacaoMudancaoStatusTO.getNomeFesta().equals("teste"));
	}
	
	@Test
	void procurarDadosPublicosFestaTest() throws Exception {
		Festa festaTest = festaTest();

		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festaTest);

		Festa retorno = festaService.procurarDadosPublicosFesta(festaTest.getCodFesta());

		assertEquals(festaTest , retorno);
	}
	
	@Test
	void procurarFestasComLotesCompraveisTest() throws Exception {
		Festa festaTest = festaTest();
		List<Festa> festas = new ArrayList<>();
		festas.add(festaTest);
		
		Mockito.when(festaRepository.findAllComLotesCompraveis()).thenReturn(festas);

		List<Festa> retorno = festaService.procurarFestasComLotesCompraveis();

		assertEquals(festaTest , retorno.get(0));
	}
}
