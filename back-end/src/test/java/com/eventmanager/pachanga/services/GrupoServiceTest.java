package com.eventmanager.pachanga.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

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

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Permissao;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.GrupoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.repositories.ConvidadoRepository;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.PermissaoRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=GrupoService.class)
 class GrupoServiceTest {
	@MockBean
	private GrupoRepository grupoRepository;

	@MockBean
	private FestaRepository festaRepository;

	@MockBean
	private UsuarioRepository usuarioRepository;
	
	@MockBean
	private NotificacaoService notificacaoService;

	@MockBean
	private PermissaoRepository permissaoRepository;

	@MockBean
	private ConvidadoRepository convidadoRepository;
	
	@Autowired
	private GrupoService grupoService;
	
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
	
	private Usuario usuarioTest() throws Exception{
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("fc68b677646b5f018d1762e9a19bf65180d9aab2794794340ade50e0d78a239affd43a613e7136a61b5d63b09f072c0c039dea4281873abe826d6e6285d9cefef0a0d868d3b0b0d4582ec787b473b4e0");
		usuarioTest.setGenero("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");

		return usuarioTest;
	}
	
	private GrupoTO criacaoGrupoTO() {
		GrupoTO grupoTO = new GrupoTO();
		grupoTO.setCodGrupo(1);
		grupoTO.setNomeGrupo("CONVIDADO");
		grupoTO.setIsOrganizador(false);
		grupoTO.setCodFesta(criacaoFesta().getCodFesta());
		grupoTO.setQuantMaxPessoas(15);
		return grupoTO;
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

	private Permissao PermissaoTest(int id, String desc, String tipo) {
		Permissao permissao = new Permissao();
		permissao.setCodPermissao(id);
		permissao.setDescPermissao(desc);
		permissao.setTipPermissao(tipo);
		
		return permissao;
	}
	
	private List<Permissao> ColecaoDePermissaoTest() {
		List<Permissao> permissoes = new ArrayList<>();
		
		permissoes.add(PermissaoTest(1, "EDITDFES", "G"));
		permissoes.add(PermissaoTest(2, "CREGRPER", "G"));
		permissoes.add(PermissaoTest(3, "DELGRPER", "G"));
		permissoes.add(PermissaoTest(4, "EDIGRPER", "G"));
		permissoes.add(PermissaoTest(5, "ADDMEMBE", "G"));
		permissoes.add(PermissaoTest(6, "DELMEMBE", "G"));
		permissoes.add(PermissaoTest(7, "DISMEMBE", "G"));
		permissoes.add(PermissaoTest(8, "CADAESTO", "E"));
		permissoes.add(PermissaoTest(9, "DELEESTO", "E"));
		permissoes.add(PermissaoTest(10, "EDITESTO", "E"));
		permissoes.add(PermissaoTest(11, "CADMESTO", "E"));
		permissoes.add(PermissaoTest(12, "DELMESTO", "E"));
		permissoes.add(PermissaoTest(13, "EDIMESTO", "E"));
		permissoes.add(PermissaoTest(14, "ADDMESTO", "E"));
		permissoes.add(PermissaoTest(15, "BAIMESTO", "E"));
		permissoes.add(PermissaoTest(16, "DELEFEST", "G"));
		
		return permissoes;
	}
	
	
//addGrupo_________________________________________________________________________________________________
	@Test
	 void addGrupoSucessTest() {
		int codGrupo = 4;
		String nomeGrupo = "Otavio";
		Festa festa = criacaoFesta();
		List<Integer> permissoes = new ArrayList<>();
		permissoes.add(1);
		permissoes.add(2);
		permissoes.add(3);
		permissoes.add(4);
		permissoes.add(5);
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
		Mockito.when(grupoRepository.getNextValMySequence()).thenReturn(codGrupo);
		Mockito.when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(null);	
		Mockito.doNothing().when(grupoRepository).saveGrupoPermissao(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(permissaoRepository.findAll()).thenReturn(ColecaoDePermissaoTest());
		Mockito.when(permissaoRepository.findPermissoes(Mockito.anyList())).thenReturn(ColecaoDePermissaoTest());
	
		Grupo grupo = grupoService.addGrupo(festa.getCodFesta(), nomeGrupo, true, permissoes);
		
		assertEquals(grupo.getCodGrupo(), codGrupo);
		assertEquals(grupo.getFesta().getCodFesta(), festa.getCodFesta());
		assertEquals(grupo.getNomeGrupo(), nomeGrupo);
		assertEquals(true, grupo.getOrganizador());
		
	}
	
	@Test
	 void addGrupoFESTNFOUExceptionTest() {
		int codGrupo = 4;
		String nomeGrupo = "Otavio";
		Festa festa = criacaoFesta();
		List<Integer> permissoes = new ArrayList<>();
		permissoes.add(1);
		permissoes.add(2);
		permissoes.add(3);
		permissoes.add(4);
		permissoes.add(5);
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(null);
		Mockito.when(grupoRepository.getNextValMySequence()).thenReturn(codGrupo);
		Mockito.when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(null);	
		Mockito.doNothing().when(grupoRepository).saveGrupoPermissao(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(permissaoRepository.findAll()).thenReturn(ColecaoDePermissaoTest());
		Mockito.when(permissaoRepository.findPermissoes(Mockito.anyList())).thenReturn(ColecaoDePermissaoTest());
		
		boolean erro = false;
		try {		
			grupoService.addGrupo(festa.getCodFesta(), nomeGrupo, true, permissoes);
		} catch (ValidacaoException e) {
			erro = true;

		}

		assertEquals(true, erro);
		
	}
	
	@Test
	 void addGrupoNotOrganizadorSucessTest() {
		int codGrupo = 4;
		String nomeGrupo = "Otavio";
		Festa festa = criacaoFesta();
		List<Integer> permissoes = new ArrayList<>();
		permissoes.add(1);
		permissoes.add(2);
		permissoes.add(3);
		permissoes.add(4);
		permissoes.add(5);
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
		Mockito.when(grupoRepository.getNextValMySequence()).thenReturn(codGrupo);
		Mockito.when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(null);	
		Mockito.doNothing().when(grupoRepository).saveGrupoPermissao(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(permissaoRepository.findAll()).thenReturn(ColecaoDePermissaoTest());
		Mockito.when(permissaoRepository.findPermissoes(Mockito.anyList())).thenReturn(ColecaoDePermissaoTest());
	
		Grupo grupo = grupoService.addGrupo(festa.getCodFesta(), nomeGrupo, false, permissoes);
		
		assertEquals(grupo.getCodGrupo(), codGrupo);
		assertEquals(grupo.getFesta().getCodFesta(), festa.getCodFesta());
		assertEquals(grupo.getNomeGrupo(), nomeGrupo);
		assertEquals(false, grupo.getOrganizador());
	}
	
	
	
//addGrupoFesta_________________________________________________________________________________________________
	
	@Test
	 void addGrupoFestaSucessTest() {
		GrupoTO grupoTO = criacaoGrupoTO();
		int codGrupo = 4;
		Festa festa = criacaoFesta();
		List<Integer> permissoes = new ArrayList<>();
		permissoes.add(1);
		permissoes.add(2);
		permissoes.add(3);
		permissoes.add(4);
		permissoes.add(5);
		
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(criacaoGrupo());
		List<Grupo> gruposDuplicados = new ArrayList<>();
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
		Mockito.when(grupoRepository.getNextValMySequence()).thenReturn(codGrupo);
		Mockito.when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(null);	
		Mockito.doNothing().when(grupoRepository).saveGrupoPermissao(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(permissaoRepository.findAll()).thenReturn(ColecaoDePermissaoTest());
		Mockito.when(permissaoRepository.findPermissoes(Mockito.anyList())).thenReturn(ColecaoDePermissaoTest());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(grupoRepository.findGruposDuplicados(Mockito.anyInt(), Mockito.anyString())).thenReturn(gruposDuplicados);
		
		Grupo grupo = grupoService.addGrupoFesta(grupoTO, 2);
		
		assertEquals(grupo.getCodGrupo(), codGrupo);
		assertEquals(grupo.getFesta().getCodFesta(), festa.getCodFesta());
		assertEquals(grupo.getNomeGrupo(), grupoTO.getNomeGrupo());
		assertEquals(grupo.getOrganizador(), grupoTO.getIsOrganizador());
		//assertEquals(grupo.getQuantMaxPessoas(), grupoTO.getQuantMaxPessoas());
		assertEquals(grupo.getFesta().getCodFesta(), festa.getCodFesta());
	}
	
	@Test
	 void addGrupoFestaUSESPERMExceptionTest() {
		GrupoTO grupoTO = criacaoGrupoTO();
		int codGrupo = 4;
		//Festa festa = criacaoFesta();
		List<Integer> permissoes = new ArrayList<>();
		permissoes.add(1);
		permissoes.add(2);
		permissoes.add(3);
		permissoes.add(4);
		permissoes.add(5);
		
		List<Grupo> grupos = new ArrayList<>();
		List<Grupo> gruposDuplicados = new ArrayList<>();
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
		Mockito.when(grupoRepository.getNextValMySequence()).thenReturn(codGrupo);
		Mockito.when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(null);	
		Mockito.doNothing().when(grupoRepository).saveGrupoPermissao(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(permissaoRepository.findAll()).thenReturn(ColecaoDePermissaoTest());
		Mockito.when(permissaoRepository.findPermissoes(Mockito.anyList())).thenReturn(ColecaoDePermissaoTest());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(grupoRepository.findGruposDuplicados(Mockito.anyInt(), Mockito.anyString())).thenReturn(gruposDuplicados);
		
		boolean erro = false;
		try {		
			grupoService.addGrupoFesta(grupoTO, 2);
		} catch (ValidacaoException e) {
			erro = true;

		}

		assertEquals(true, erro);
	}
	
	@Test
	 void addGrupoFestaExceptionTest() {
		GrupoTO grupoTO = criacaoGrupoTO();
		int codGrupo = 4;
		//Festa festa = criacaoFesta();
		List<Integer> permissoes = new ArrayList<>();
		permissoes.add(1);
		permissoes.add(2);
		permissoes.add(3);
		permissoes.add(4);
		permissoes.add(5);
		
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(criacaoGrupo());
		List<Grupo> gruposDuplicados = new ArrayList<>();
		gruposDuplicados.add(criacaoGrupo());
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
		Mockito.when(grupoRepository.getNextValMySequence()).thenReturn(codGrupo);
		Mockito.when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(null);	
		Mockito.doNothing().when(grupoRepository).saveGrupoPermissao(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(permissaoRepository.findAll()).thenReturn(ColecaoDePermissaoTest());
		Mockito.when(permissaoRepository.findPermissoes(Mockito.anyList())).thenReturn(ColecaoDePermissaoTest());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(grupoRepository.findGruposDuplicados(Mockito.anyInt(), Mockito.anyString())).thenReturn(gruposDuplicados);
		
		boolean erro = false;
		try {		
			grupoService.addGrupoFesta(grupoTO, 2);
		} catch (ValidacaoException e) {
			erro = true;

		}

		assertEquals(true, erro);
	}
	
//getByIdGrupo_________________________________________________________________________________________________
	
	@Test
	 void getByIdGrupoSucessTest() {
		Grupo grupo = criacaoGrupo();
		
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(grupo);
		
		Grupo retorno = grupoService.getByIdGrupo(grupo.getCodGrupo());
		
		assertEquals(retorno.getCodGrupo(), grupo.getCodGrupo());
		assertEquals(retorno.getNomeGrupo(), grupo.getNomeGrupo());
		assertEquals(retorno.getOrganizador(), grupo.getOrganizador());
	}
	
	@Test
	 void getByIdGrupoGRUPNFOUExceptionTest() {
		Grupo grupo = criacaoGrupo();
		
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(null);
		
		boolean erro = false;
		try {		
			grupoService.getByIdGrupo(grupo.getCodGrupo());
		} catch (ValidacaoException e) {
			erro = true;

		}

		assertEquals(true, erro);
	}

//deleteGrupo___________________________________________________________________________________________________
	@Test
	 void deleteGrupoSucessTest() {
		Grupo grupo = criacaoGrupo();
		Set<Usuario> usuarios = new HashSet<>();  //vazio
		grupo.setUsuarios(usuarios);
				
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
		Mockito.doNothing().when(grupoRepository).deleteGrupo(Mockito.anyInt());
		Mockito.doNothing().when(permissaoRepository).deletePermissoesGrupo(Mockito.anyInt());
		
		Grupo retorno = grupoService.deleteGrupo(grupo.getCodGrupo(), 2);
		
		assertEquals(retorno.getCodGrupo(), grupo.getCodGrupo());
		assertEquals(retorno.getNomeGrupo(), grupo.getNomeGrupo());
		assertEquals(retorno.getOrganizador(), grupo.getOrganizador());
		
	}
	
	@Test
	void deleteCascadeTeste() {
		List<Integer> codGrupos = new ArrayList<Integer>();
		codGrupos.add(1);
		Mockito.when(grupoRepository.findCodGruposFesta(Mockito.anyInt())).thenReturn(codGrupos);
		List<Integer> codConvidados = new ArrayList<Integer>();
		codConvidados.add(1);
		Mockito.when(convidadoRepository.findCodConvidadosNoGrupo(Mockito.anyInt())).thenReturn(codConvidados);

		doNothing().when(convidadoRepository).deleteAllConvidadosNotificacao(Mockito.any());
		doNothing().when(convidadoRepository).deleteAllConvidadosGrupo(Mockito.anyInt());
		doNothing().when(convidadoRepository).deleteConvidados(Mockito.any());

		List<Usuario> usuarios = new ArrayList<Usuario>();
		Usuario usuario = new Usuario();
		usuario.setCodUsuario(1);
		usuarios.add(usuario);
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);

		doNothing().when(notificacaoService).deleteNotificacao(Mockito.anyInt(),  Mockito.anyString());
		Mockito.when(notificacaoService.criacaoMensagemNotificacaoUsuarioConvidado(Mockito.anyInt(), Mockito.anyInt(),  Mockito.anyString())).thenReturn(null);
		doNothing().when(notificacaoService).deleteNotificacao(Mockito.anyInt(), Mockito.anyString());
		doNothing().when(notificacaoService).deleteNotificacoesGrupos(Mockito.any());
		doNothing().when(grupoRepository).deleteUsuariosGrupo(Mockito.anyInt());
		doNothing().when(grupoRepository).deletePermissoesGrupos(Mockito.any());

		grupoService.deleteCascade(1, 1);
	}
	
	@Test
	 void deleteGrupoGRPOORGNExceptionTest() {
		Grupo grupo = criacaoGrupo();
		grupo.setOrganizador(true);
		Set<Usuario> usuarios = new HashSet<>();  //vazio
		grupo.setUsuarios(usuarios);
				
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
		Mockito.doNothing().when(grupoRepository).deleteGrupo(Mockito.anyInt());
		Mockito.doNothing().when(permissaoRepository).deletePermissoesGrupo(Mockito.anyInt());
		
		boolean erro = false;
		try {		
			grupoService.deleteGrupo(grupo.getCodGrupo(), 2);
		} catch (ValidacaoException e) {
			erro = true;

		}
		assertEquals(true, erro);
	}
	
	@Test
	 void deleteGrupoGRPONVAZExceptionTest() throws Exception {
		Grupo grupo = criacaoGrupo();
		Set<Usuario> usuarios = new HashSet<>();  //vazio
		usuarios.add(usuarioTest());
		grupo.setUsuarios(usuarios);
				
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
		Mockito.doNothing().when(grupoRepository).deleteGrupo(Mockito.anyInt());
		Mockito.doNothing().when(permissaoRepository).deletePermissoesGrupo(Mockito.anyInt());
		
		boolean erro = false;
		try {		
			grupoService.deleteGrupo(grupo.getCodGrupo(), 2);
		} catch (ValidacaoException e) {
			erro = true;

		}
		assertEquals(true, erro);
		
	}

	
//atualizar___________________________________________________________________________________________________
	@Test
	 void atualizarSucessTest() {
		Grupo grupo = criacaoGrupo();
		List<Integer> permissoes = new ArrayList<>();
		permissoes.add(1);
		permissoes.add(2);
		permissoes.add(3);
		permissoes.add(4);
		permissoes.add(5);
		GrupoTO grupoTO = criacaoGrupoTO();
		grupoTO.setPermissoes(permissoes);
		
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.doNothing().when(permissaoRepository).deletePermissoesGrupo(Mockito.anyInt());
		Mockito.doNothing().when(grupoRepository).saveGrupoPermissao(Mockito.anyInt(), Mockito.anyInt());
		//Mockito.doNothing().when(grupoRepository).save(Mockito.any(Grupo.class));
		Mockito.when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(null);	
		
		Grupo retorno = grupoService.atualizar(grupoTO, 2);
		
		assertEquals(retorno.getCodGrupo(), grupo.getCodGrupo());
		assertEquals(retorno.getNomeGrupo(), grupo.getNomeGrupo());
		assertEquals(retorno.getOrganizador(), grupo.getOrganizador());
		
	}
	
	@Test
	 void atualizarOrganizadorExceptionTest() {
		Grupo grupo = criacaoGrupo();
		grupo.setOrganizador(true);
		List<Integer> permissoes = new ArrayList<>();
		permissoes.add(1);
		permissoes.add(2);
		permissoes.add(3);
		permissoes.add(4);
		permissoes.add(5);
		GrupoTO grupoTO = criacaoGrupoTO();
		grupoTO.setPermissoes(permissoes);
		
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.doNothing().when(permissaoRepository).deletePermissoesGrupo(Mockito.anyInt());
		Mockito.doNothing().when(grupoRepository).saveGrupoPermissao(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(null);	
		
		boolean erro = false;
		try {		
			grupoService.atualizar(grupoTO, 2);
		} catch (ValidacaoException e) {
			erro = true;

		}
		
		assertEquals(true, erro);
		
	}
	
	@Test
	 void atualizarNomeGrupoNullSucessTest() {
		Grupo grupo = criacaoGrupo();
		List<Integer> permissoes = new ArrayList<>();
		permissoes.add(1);
		permissoes.add(2);
		permissoes.add(3);
		permissoes.add(4);
		permissoes.add(5);
		GrupoTO grupoTO = criacaoGrupoTO();
		grupoTO.setNomeGrupo(null);
		grupoTO.setPermissoes(permissoes);
		
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.doNothing().when(permissaoRepository).deletePermissoesGrupo(Mockito.anyInt());
		Mockito.doNothing().when(grupoRepository).saveGrupoPermissao(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(null);	
		
		Grupo retorno = grupoService.atualizar(grupoTO, 2);
		
		assertEquals(retorno.getCodGrupo(), grupo.getCodGrupo());
		assertEquals(retorno.getNomeGrupo(), grupo.getNomeGrupo());
		assertEquals(retorno.getOrganizador(), grupo.getOrganizador());
		
	}
	
	@Test
	 void atualizarNomeGrupoVazioSucessTest() {
		Grupo grupo = criacaoGrupo();
		List<Integer> permissoes = new ArrayList<>();
		permissoes.add(1);
		permissoes.add(2);
		permissoes.add(3);
		permissoes.add(4);
		permissoes.add(5);
		GrupoTO grupoTO = criacaoGrupoTO();
		grupoTO.setNomeGrupo("");
		grupoTO.setPermissoes(permissoes);
		
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.doNothing().when(permissaoRepository).deletePermissoesGrupo(Mockito.anyInt());
		Mockito.doNothing().when(grupoRepository).saveGrupoPermissao(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(null);	
		
		Grupo retorno = grupoService.atualizar(grupoTO, 2);
		
		assertEquals(retorno.getCodGrupo(), grupo.getCodGrupo());
		assertEquals(retorno.getNomeGrupo(), grupo.getNomeGrupo());
		assertEquals(retorno.getOrganizador(), grupo.getOrganizador());
		
	}
	
	@Test
	 void atualizarQuantMaxZeroSucessTest() {
		Grupo grupo = criacaoGrupo();
		List<Integer> permissoes = new ArrayList<>();
		permissoes.add(1);
		permissoes.add(2);
		permissoes.add(3);
		permissoes.add(4);
		permissoes.add(5);
		GrupoTO grupoTO = criacaoGrupoTO();
		grupoTO.setPermissoes(permissoes);
		grupoTO.setQuantMaxPessoas(0);
		
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.doNothing().when(permissaoRepository).deletePermissoesGrupo(Mockito.anyInt());
		Mockito.doNothing().when(grupoRepository).saveGrupoPermissao(Mockito.anyInt(), Mockito.anyInt());
		//Mockito.doNothing().when(grupoRepository).save(Mockito.any(Grupo.class));
		Mockito.when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(null);	
		
		Grupo retorno = grupoService.atualizar(grupoTO, 2);
		
		assertEquals(retorno.getCodGrupo(), grupo.getCodGrupo());
		assertEquals(retorno.getNomeGrupo(), grupo.getNomeGrupo());
		assertEquals(retorno.getOrganizador(), grupo.getOrganizador());
		
	}
	
	@Test
	 void atualizarQuantMaxInvalidSucessTest() throws Exception {
		Grupo grupo = criacaoGrupo();
		List<Integer> permissoes = new ArrayList<>();
		permissoes.add(1);
		permissoes.add(2);
		permissoes.add(3);
		permissoes.add(4);
		permissoes.add(5);
		GrupoTO grupoTO = criacaoGrupoTO();
		grupoTO.setPermissoes(permissoes);
		grupoTO.setQuantMaxPessoas(1);
		
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuarioTest());
		usuarios.add(usuarioTest());
		usuarios.add(usuarioTest());
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupos);
		Mockito.doNothing().when(permissaoRepository).deletePermissoesGrupo(Mockito.anyInt());
		Mockito.doNothing().when(grupoRepository).saveGrupoPermissao(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(null);	
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		
		Grupo retorno = grupoService.atualizar(grupoTO, 2);
		
		assertEquals(retorno.getCodGrupo(), grupo.getCodGrupo());
		assertEquals(retorno.getNomeGrupo(), grupo.getNomeGrupo());
		assertEquals(retorno.getOrganizador(), grupo.getOrganizador());
		
	}
	
//atualizar___________________________________________________________________________________________________
	
	@Test
	 void editUsuarioSucessTest() throws Exception {
		Grupo grupo = criacaoGrupo();
		
		List<Integer> gruposId = new ArrayList<>();
		List<Grupo> gruposValidacao = new ArrayList<>();
		gruposValidacao.add(grupo);
		gruposId.add(grupo.getCodGrupo());
		
		Mockito.when(grupoRepository.findByCod(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(gruposValidacao);
		Mockito.doNothing().when(grupoRepository).saveUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(grupoRepository.findGruposFestaIn(Mockito.anyList(), Mockito.anyInt())).thenReturn(gruposId);
		Mockito.doNothing().when(grupoRepository).deleteMembroGrupo(Mockito.anyInt(), Mockito.anyInt());;
		
		grupoService.editUsuario(gruposId, grupo.getCodGrupo(), 2, 2);		
	}

//editUsuarios___________________________________________________________________________________

	@Test
	 void editUsuariosSucessTest() throws Exception {
		Grupo grupo = criacaoGrupo();
		
		List<Integer> gruposId = new ArrayList<>();
		List<Grupo> gruposValidacao = new ArrayList<>();
		gruposValidacao.add(grupo);
		gruposId.add(grupo.getCodGrupo());
		
		Mockito.when(grupoRepository.findByCod(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(gruposValidacao);
		Mockito.doNothing().when(grupoRepository).saveUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(grupoRepository.findGruposFestaIn(Mockito.anyList(), Mockito.anyInt())).thenReturn(gruposId);
		Mockito.doNothing().when(grupoRepository).deleteAllMembrosGrupo(Mockito.anyInt());
		
		grupoService.editUsuarios(gruposId, grupo.getCodGrupo(), 2);	
	}
	
	@Test
	 void editUsuariosUSESPERMExceptionTest() throws Exception {
		Grupo grupo = criacaoGrupo();
		
		List<Integer> gruposId = new ArrayList<>();
		List<Grupo> gruposValidacao = new ArrayList<>();
		//gruposValidacao.add(grupo);
		gruposId.add(grupo.getCodGrupo());
		
		Mockito.when(grupoRepository.findByCod(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(gruposValidacao);
		Mockito.doNothing().when(grupoRepository).saveUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(grupoRepository.findGruposFestaIn(Mockito.anyList(), Mockito.anyInt())).thenReturn(gruposId);
		Mockito.doNothing().when(grupoRepository).deleteAllMembrosGrupo(Mockito.anyInt());
		
		boolean erro = false;
		try {		
			grupoService.editUsuarios(gruposId, grupo.getCodGrupo(), 2);	
		} catch (ValidacaoException e) {
			erro = true;

		}
		
		assertEquals(true, erro);
		
		
	}
	
//deleteMembro___________________________________________________________________________________
	@Test
	 void deleteMembroSucessTest() throws Exception {
		Grupo grupo = criacaoGrupo();
		
		List<Integer> gruposId = new ArrayList<>();
		List<Grupo> gruposValidacao = new ArrayList<>();
		gruposValidacao.add(grupo);
		gruposId.add(grupo.getCodGrupo());
		
		Mockito.when(grupoRepository.findByCod(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(gruposValidacao);
		Mockito.doNothing().when(grupoRepository).deleteUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt());
		
		grupoService.deleteMembro(2, grupo.getCodGrupo(), 2);	
		
	}
	
//deleteConvidado________________________________________________________________________________
	@Test
	 void deleteConvidadoSucessTest() throws Exception {
		Grupo grupo = criacaoGrupo();
		
		List<Integer> gruposId = new ArrayList<>();
		List<Grupo> gruposValidacao = new ArrayList<>();
		gruposValidacao.add(grupo);
		gruposId.add(grupo.getCodGrupo());
		
		Mockito.when(grupoRepository.findByCod(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(gruposValidacao);
		Mockito.doNothing().when(grupoRepository).deleteUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt());
		
		Mockito.doNothing().when(notificacaoService).deletarNotificacoesConvidado(Mockito.anyInt());
		Mockito.doNothing().when(grupoRepository).deleteConvidadoGrupo(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(grupoRepository.existsConvidadoGrupo(Mockito.anyInt())).thenReturn(2);
		
		Mockito.doNothing().when(convidadoRepository).deleteConvidado(Mockito.anyInt());
		
		Mockito.when(notificacaoService.criacaoMensagemNotificacaoUsuarioConvidado(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString())).thenReturn("Batata");;
		Mockito.doNothing().when(notificacaoService).deletarNotificacaoConvidado(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		
		grupoService.deleteConvidado(2, grupo.getCodGrupo(), 2);
		
	}
	
	@Test
	 void deleteConvidadoConvxgrupNullSucessTest() throws Exception {
		Grupo grupo = criacaoGrupo();
		
		List<Integer> gruposId = new ArrayList<>();
		List<Grupo> gruposValidacao = new ArrayList<>();
		gruposValidacao.add(grupo);
		gruposId.add(grupo.getCodGrupo());
		
		Mockito.when(grupoRepository.findByCod(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(gruposValidacao);
		Mockito.doNothing().when(grupoRepository).deleteUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt());
		
		Mockito.doNothing().when(notificacaoService).deletarNotificacoesConvidado(Mockito.anyInt());
		Mockito.doNothing().when(grupoRepository).deleteConvidadoGrupo(Mockito.anyInt(), Mockito.anyInt());
		Mockito.when(grupoRepository.existsConvidadoGrupo(Mockito.anyInt())).thenReturn(null);
		
		Mockito.doNothing().when(convidadoRepository).deleteConvidado(Mockito.anyInt());
		
		Mockito.when(notificacaoService.criacaoMensagemNotificacaoUsuarioConvidado(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString())).thenReturn("Batata");;
		Mockito.doNothing().when(notificacaoService).deletarNotificacaoConvidado(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString());
		
		grupoService.deleteConvidado(2, grupo.getCodGrupo(), 2);
		
	}
	
//addPermissaoGrupo_____________________________________________________________________________
	@Test
	 void addPermissaoGrupoSucessTest() throws Exception {
		Grupo grupo = criacaoGrupo();
		Permissao permissao = PermissaoTest(21, "EDITDFE2", "G");
		
		Mockito.when(permissaoRepository.findById(Mockito.anyInt())).thenReturn(permissao);
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoRepository.findPermissoesPorGrupo(grupo.getCodGrupo())).thenReturn(ColecaoDePermissaoTest());
		Mockito.doNothing().when(grupoRepository).saveGrupoPermissao(Mockito.anyInt(), Mockito.anyInt());
	
		grupoService.addPermissaoGrupo(permissao.getCodPermissao(), grupo.getCodGrupo());
	}
	
	@Test
	 void addPermissaoGrupoPERMNFOUExceptionTest() throws Exception {
		Grupo grupo = criacaoGrupo();
		Permissao permissao = PermissaoTest(21, "EDITDFE2", "G");
		
		Mockito.when(permissaoRepository.findById(Mockito.anyInt())).thenReturn(null);
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoRepository.findPermissoesPorGrupo(grupo.getCodGrupo())).thenReturn(ColecaoDePermissaoTest());
		Mockito.doNothing().when(grupoRepository).saveGrupoPermissao(Mockito.anyInt(), Mockito.anyInt());

		boolean erro = false;
		try {		
			grupoService.addPermissaoGrupo(permissao.getCodPermissao(), grupo.getCodGrupo());
		} catch (ValidacaoException e) {
			erro = true;

		}
		assertEquals(true, erro);
	}
	
	@Test
	 void addPermissaoGrupExceptionTest() throws Exception {
		Grupo grupo = criacaoGrupo();
		Permissao permissao = PermissaoTest(1, "EDITDFES", "G");
		
		Mockito.when(permissaoRepository.findById(Mockito.anyInt())).thenReturn(permissao);
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoRepository.findPermissoesPorGrupo(grupo.getCodGrupo())).thenReturn(ColecaoDePermissaoTest());
		Mockito.doNothing().when(grupoRepository).saveGrupoPermissao(Mockito.anyInt(), Mockito.anyInt());
		
		boolean erro = false;
		try {		
			grupoService.addPermissaoGrupo(permissao.getCodPermissao(), grupo.getCodGrupo());
		} catch (ValidacaoException e) {
			erro = true;

		}
		
		assertEquals(true, erro);
	}
	
//deletePermissaoGrupo___________________________________________________________________________
	@Test
	 void deletePermissaoGrupoSucessTest() throws Exception {
		Grupo grupo = criacaoGrupo();
		Permissao permissao = PermissaoTest(1, "EDITDFES", "G");
		
		Mockito.when(permissaoRepository.findById(Mockito.anyInt())).thenReturn(permissao);
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoRepository.findPermissoesPorGrupo(grupo.getCodGrupo())).thenReturn(ColecaoDePermissaoTest());
		Mockito.doNothing().when(grupoRepository).deleteGrupoPermissao(Mockito.anyInt(), Mockito.anyInt());
		
		grupoService.deletePermissaoGrupo(permissao.getCodPermissao(), grupo.getCodGrupo());
	}
	
	@Test
	 void deletePermissaoGrupoExceptionTest() throws Exception {
		Grupo grupo = criacaoGrupo();
		Permissao permissao = PermissaoTest(1, "EDITDFES", "G");
		List<Permissao> listaVazia = new ArrayList<>();
		
		Mockito.when(permissaoRepository.findById(Mockito.anyInt())).thenReturn(permissao);
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoRepository.findPermissoesPorGrupo(grupo.getCodGrupo())).thenReturn(listaVazia);
		Mockito.doNothing().when(grupoRepository).deleteGrupoPermissao(Mockito.anyInt(), Mockito.anyInt());
		
		boolean erro = false;
		try {		
			grupoService.deletePermissaoGrupo(permissao.getCodPermissao(), grupo.getCodGrupo());
		} catch (ValidacaoException e) {
			erro = true;

		}
		
		assertEquals(true, erro);
	}
	
//procurarGruposPorUsuario_________________________________________________________________________
	@Test
	 void procurarGruposPorUsuarioSucessoTest() throws Exception {
		Usuario usuario = usuarioTest();
		Grupo grupo = criacaoGrupo();
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(usuario);
		Mockito.when(grupoRepository.findGruposUsuario(Mockito.anyInt())).thenReturn(grupos);
		
		List<Grupo> gruposRetorno = grupoService.procurarGruposPorUsuario(usuario.getCodUsuario());
		
		Grupo retorno = gruposRetorno.get(0);
		
		assertEquals(retorno.getCodGrupo(), grupo.getCodGrupo());
		assertEquals(retorno.getFesta().getCodFesta(), grupo.getFesta().getCodFesta());
		assertEquals(retorno.getNomeGrupo(), grupo.getNomeGrupo());
		assertEquals(retorno.getOrganizador(), grupo.getOrganizador());
	}
	
	@Test
	 void procurarGruposPorUsuarioUSERNFOUExceptionTest() throws Exception {
		Usuario usuario = usuarioTest();
		Grupo grupo = criacaoGrupo();
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		
		Mockito.when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(null);
		Mockito.when(grupoRepository.findGruposUsuario(Mockito.anyInt())).thenReturn(grupos);
		
		boolean erro = false;
		try {		
			grupoService.procurarGruposPorUsuario(usuario.getCodUsuario());
		} catch (ValidacaoException e) {
			erro = true;

		}
		assertEquals(true, erro);
	}
	
//procurarGruposPorFesta_____________________________________________________________________________
	@Test
	 void procurarGruposPorFestaSucessoTest() throws Exception {
		Grupo grupo = criacaoGrupo();
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		
		Mockito.when(festaRepository.findById(Mockito.anyInt())).thenReturn(criacaoFesta());
		Mockito.when(grupoRepository.findGruposFesta(Mockito.anyInt())).thenReturn(grupos);
		
		List<Grupo> gruposRetorno = grupoService.procurarGruposPorFesta(criacaoFesta().getCodFesta());
		
		Grupo retorno = gruposRetorno.get(0);
		
		assertEquals(retorno.getCodGrupo(), grupo.getCodGrupo());
		assertEquals(retorno.getFesta().getCodFesta(), grupo.getFesta().getCodFesta());
		assertEquals(retorno.getNomeGrupo(), grupo.getNomeGrupo());
		assertEquals(retorno.getOrganizador(), grupo.getOrganizador());
	}
	
//procurarPermissoesPorGrupo__________________________________________________________________________
	@Test
	 void procurarPermissoesPorGrupoSucessoTest() throws Exception {
		Mockito.when(grupoRepository.findPermissoesPorGrupo(Mockito.anyInt())).thenReturn(ColecaoDePermissaoTest());
		
		List<Permissao> permissoes = grupoService.procurarPermissoesPorGrupo(2);
		
		assertEquals(permissoes.size(), ColecaoDePermissaoTest().size());
	}
	
//procurarGrupoPorId__________________________________________________________________________________
	@Test
	 void procurarGrupoPorIdSucessoTest() throws Exception {
		Grupo grupo = criacaoGrupo();
		
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(grupo);
		
		Grupo retorno = grupoService.procurarGrupoPorId(grupo.getCodGrupo());
		
		assertEquals(retorno.getCodGrupo(), grupo.getCodGrupo());
		assertEquals(retorno.getFesta().getCodFesta(), grupo.getFesta().getCodFesta());
		assertEquals(retorno.getNomeGrupo(), grupo.getNomeGrupo());
		assertEquals(retorno.getOrganizador(), grupo.getOrganizador());
	}
	
	@Test
	 void pprocurarUsuariosPorGrupoSucessoTest() throws Exception {
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuarioTest());
		
		Mockito.when(usuarioRepository.findUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.when(grupoRepository.findById(Mockito.anyInt())).thenReturn(criacaoGrupo());
		
		List<Usuario> retorno = grupoService.procurarUsuariosPorGrupo(1);
		
		assertEquals(true, !retorno.isEmpty());
	}

}
