package com.eventmanager.pachanga.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

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
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Ingresso;
import com.eventmanager.pachanga.domains.Lote;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.LoteTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.LoteFactory;
import com.eventmanager.pachanga.repositories.IngressoRepository;
import com.eventmanager.pachanga.repositories.LoteRepository;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;

@RunWith(SpringRunner.class)
@WebMvcTest(value = LoteService.class)
class LoteServiceTest {

	@Autowired
	private LoteService loteService;

	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	@MockBean
	private LoteRepository loteRepository;

	@MockBean
	private FestaService festaService;

	@MockBean
	private GrupoService grupoService;

	@MockBean
	private LoteFactory loteFactory;

	@MockBean
	private IngressoRepository ingressoRepository;

	private Lote loteTest() {
		Lote lote = new Lote();
		lote.setFesta(festaTest());
		lote.setCodLote(1);
		lote.setDescLote("lote vip krl");
		lote.setNomeLote("Teste");
		lote.setHorarioInicio(LocalDateTime.of(2020, Month.SEPTEMBER, 23, 19, 10));
		lote.setHorarioFim(LocalDateTime.of(2020, Month.SEPTEMBER, 24, 19, 10));
		lote.setNumeroLote(1);
		lote.setPreco(17.2f);
		lote.setQuantidade(100);
		return lote;
	}

	public Festa festaTest() {
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

	@SuppressWarnings("deprecation")
	private Usuario usuarioTest() {
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("1234");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setGenero("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");

		return usuarioTest;
	}

	private LoteTO loteToTest() {
		LoteTO loteTo = new LoteTO();
		loteTo.setCodLote(1);
		loteTo.setCodFesta(2);
		loteTo.setDescLote("lote vip krl");
		loteTo.setNomeLote("Teste");
		loteTo.setHorarioInicio(LocalDateTime.of(2020, Month.SEPTEMBER, 23, 19, 10));
		loteTo.setHorarioFim(LocalDateTime.of(2020, Month.SEPTEMBER, 24, 19, 10));
		loteTo.setNumeroLote(1);
		loteTo.setPreco(17.2f);
		loteTo.setQuantidade(100);
		return loteTo;
	}

	@Test
	void listaLoteFestaTest() {
		List<Lote> lotes = new ArrayList<>();
		lotes.add(loteTest());

		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(new Festa());
		Mockito.when(festaService.validarUsuarioFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuarioTest());
		Mockito.when(grupoService.validarPermissaoUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(true);
		Mockito.when(loteRepository.listaLoteFesta(Mockito.anyInt())).thenReturn(lotes);

		List<Lote> lotesReturn = loteService.listaLoteFesta(1, 2);

		assertEquals(1, lotesReturn.size());

	}

	@Test
	void listarLotesFestaDadosPublicosTest() {
		Lote lote = loteTest();
		List<Lote> lotes = new ArrayList<>();
		lotes.add(lote);

		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(new Festa());
		Mockito.when(loteRepository.findAllCompraveisFesta(Mockito.anyInt())).thenReturn(lotes);

		List<Lote> lotesReturn = loteService.listarLotesFestaDadosPublicos(lote.getFesta().getCodFesta());

		assertEquals(lotesReturn.get(0), lote);

	}

	@Test
	void adicionarLoteTest() {
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(festaService.validarUsuarioFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuarioTest());
		Mockito.when(grupoService.validarPermissaoUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(true);
		Mockito.when(loteRepository.findByNomeLote(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(new ArrayList<Lote>());
		Mockito.when(loteFactory.getLote(Mockito.any(), Mockito.any())).thenReturn(loteTest());
		Mockito.when(loteRepository.getNextValMySequence()).thenReturn(1);

		Lote lote = loteService.adicionarLote(loteToTest(), 1);

		assertEquals(true, lote != null);

	}

	@Test
	void adicionarLoteErroLoteMesmoNome() {
		LoteTO lote = loteToTest();
		lote.setNumeroLote(2);
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(festaService.validarUsuarioFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuarioTest());
		Mockito.when(grupoService.validarPermissaoUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(true);
		Mockito.when(loteRepository.findByNomeLote(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(new ArrayList<Lote>());
		Mockito.when(loteFactory.getLote(Mockito.any(), Mockito.any())).thenReturn(loteTest());
		Mockito.when(loteRepository.getNextValMySequence()).thenReturn(1);

		String erro = "";
		try {
			loteService.adicionarLote(lote, 1);
		} catch (ValidacaoException e) {
			erro = e.getMessage();
		}

		assertEquals("NINVLOTE", erro);

	}

	@Test
	void adicionarLoteErroPrecoIncorreto() {

		LoteTO loteTo = loteToTest();
		loteTo.setPreco(-0.72f);

		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(festaService.validarUsuarioFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuarioTest());
		Mockito.when(grupoService.validarPermissaoUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(true);
		Mockito.when(loteRepository.findByNomeLote(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(loteRepository.findByNomeLote(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(new ArrayList<Lote>());
		Mockito.when(loteFactory.getLote(Mockito.any(), Mockito.any())).thenReturn(loteTest());
		Mockito.when(loteRepository.getNextValMySequence()).thenReturn(1);

		String erro = "";
		try {
			loteService.adicionarLote(loteTo, 1);
		} catch (ValidacaoException e) {
			erro = e.getMessage();
		}

		assertEquals("PREILOTE", erro);

	}

	@Test
	void adicionarLoteErroQuantidadeIncorreta() {

		LoteTO loteTo = loteToTest();
		loteTo.setQuantidade(0);

		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(festaService.validarUsuarioFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuarioTest());
		Mockito.when(grupoService.validarPermissaoUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(true);
		Mockito.when(loteRepository.findByNomeLote(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(loteFactory.getLote(Mockito.any(), Mockito.any())).thenReturn(loteTest());
		Mockito.when(loteRepository.findByNomeLote(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(new ArrayList<Lote>());
		Mockito.when(loteRepository.getNextValMySequence()).thenReturn(1);

		String erro = "";
		try {
			loteService.adicionarLote(loteTo, 1);
		} catch (ValidacaoException e) {
			erro = e.getMessage();
		}

		assertEquals("QUAILOTE", erro);

	}

	@Test
	void adicionarLoteErroDataIncorreta() {

		LoteTO loteTo = loteToTest();
		loteTo.setHorarioFim(LocalDateTime.of(2020, Month.SEPTEMBER, 23, 19, 10));

		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(festaService.validarUsuarioFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuarioTest());
		Mockito.when(grupoService.validarPermissaoUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(true);
		Mockito.when(loteRepository.findByNomeLote(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(loteFactory.getLote(Mockito.any(), Mockito.any())).thenReturn(loteTest());
		Mockito.when(loteRepository.findByNomeLote(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(new ArrayList<Lote>());
		Mockito.when(loteRepository.getNextValMySequence()).thenReturn(1);

		String erro = "";
		try {
			loteService.adicionarLote(loteTo, 1);
		} catch (ValidacaoException e) {
			erro = e.getMessage();
		}

		assertEquals("DATEINFE", erro);

	}

	@Test
	void atualizarLoteTest() {
		Lote loteRetorno = loteTest();
		loteRetorno.setNumeroLote(2);
		loteRetorno.setNomeLote("SAFE");
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(festaService.validarUsuarioFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuarioTest());
		Mockito.when(loteRepository.findByCodLote(Mockito.anyInt())).thenReturn(loteRetorno);
		Mockito.when(grupoService.validarPermissaoUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(true);
		Mockito.when(loteRepository.findByNomeLote(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(new ArrayList<Lote>());
		Mockito.when(loteFactory.getLote(Mockito.any(), Mockito.any())).thenReturn(loteTest());

		Lote lote = loteService.atualizarLote(loteToTest(), 1);

		assertEquals(true, lote != null);

	}

	@Test
	void atualizarLoteTestErroNumeroLoteInvalido() {
		Lote loteRetorno = loteTest();
		loteRetorno.setNumeroLote(2);
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(festaService.validarUsuarioFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuarioTest());
		Mockito.when(loteRepository.findByCodLote(Mockito.anyInt())).thenReturn(loteRetorno);
		Mockito.when(grupoService.validarPermissaoUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(true);
		Mockito.when(loteRepository.findByNomeLote(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(new ArrayList<Lote>());
		Mockito.when(loteFactory.getLote(Mockito.any(), Mockito.any())).thenReturn(loteTest());

		String erro = "";
		try {
			loteService.atualizarLote(loteToTest(), 1);
		} catch (ValidacaoException e) {
			erro = e.getMessage();
		}

		assertEquals("NINVLOTE", erro);

	}

	@Test
	void atualizarLoteErroLoteNaoEncontrado() {
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(festaService.validarUsuarioFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuarioTest());
		Mockito.when(loteRepository.findByCodLote(Mockito.anyInt())).thenReturn(null);
		Mockito.when(grupoService.validarPermissaoUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(true);
		Mockito.when(loteRepository.findByNomeLote(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(loteFactory.getLote(Mockito.any(), Mockito.any())).thenReturn(loteTest());

		String erro = "";
		try {
			loteService.atualizarLote(loteToTest(), 1);
		} catch (ValidacaoException e) {
			erro = e.getMessage();
		}

		assertEquals("LOTENFOU", erro);

	}

	@Test
	void removerLoteTest() {
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(festaService.validarUsuarioFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuarioTest());
		Mockito.when(loteRepository.findByCodLote(Mockito.anyInt())).thenReturn(loteTest());
		Mockito.when(grupoService.validarPermissaoUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(true);
		Mockito.when(ingressoRepository.findIngressosLote(Mockito.anyInt())).thenReturn(new ArrayList<Ingresso>());

		loteService.removerLote(2, 1);

	}

	@Test
	void removerLoteErroIngressosVendidos() {

		List<Ingresso> ingressos = new ArrayList<>();
		ingressos.add(new Ingresso());

		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(festaService.validarUsuarioFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuarioTest());
		Mockito.when(loteRepository.findByCodLote(Mockito.anyInt())).thenReturn(loteTest());
		Mockito.when(grupoService.validarPermissaoUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(true);
		Mockito.when(ingressoRepository.findIngressosLote(Mockito.anyInt())).thenReturn(ingressos);

		String erro = "";
		try {
			loteService.removerLote(2, 1);
		} catch (ValidacaoException e) {
			erro = e.getMessage();
		}

		assertEquals("LOTEVING", erro);
	}
	
	@Test
	void deleteCascadeTeste() {
		Festa festa = new Festa();
		festa.setCodFesta(1);
		festa.setStatusFesta("F");
		List<Ingresso> ingressos = new ArrayList<Ingresso>();
		ingressos.add(new Ingresso());
		Mockito.when(ingressoRepository.findIngressosFesta(Mockito.anyInt())).thenReturn(ingressos);
		doNothing().when(ingressoRepository).deleteByCodFesta(Mockito.anyInt());
		doNothing().when(loteRepository).deleteByCodFesta(Mockito.anyInt());

		loteService.deleteCascade(festa);
	}
	
	@Test
	void deleteCascadeTesteError() {
		Festa festa = new Festa();
		festa.setCodFesta(1);
		festa.setStatusFesta("I");
		List<Ingresso> ingressos = new ArrayList<Ingresso>();
		ingressos.add(new Ingresso());
		Mockito.when(ingressoRepository.findIngressosFesta(Mockito.anyInt())).thenReturn(ingressos);
		doNothing().when(ingressoRepository).deleteByCodFesta(Mockito.anyInt());
		doNothing().when(loteRepository).deleteByCodFesta(Mockito.anyInt());

		String mensagemError = "";
		try {
			loteService.deleteCascade(festa);
		} catch (Exception error) {
			mensagemError = error.getMessage();
		}
		
		assertEquals(true, mensagemError.equals("FESDEING"));
		
	}

	@Test
	void encontrarLoteTest() {
		Lote lote = loteTest();

		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(festaService.validarUsuarioFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuarioTest());
		Mockito.when(grupoService.validarPermissaoUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt()))
				.thenReturn(true);
		Mockito.when(loteRepository.findByCodLote(Mockito.anyInt())).thenReturn(loteTest());

		Lote loteReturn = loteService.encontrarLote(1, 2);

		assertEquals(true, lote.getCodLote() == loteReturn.getCodLote());

	}

	@Test
	void encontrarLotesCompraveisFestaTest() {
		List<Lote> lotes = new ArrayList<>();
		lotes.add(loteTest());

		Mockito.when(loteRepository.findAllCompraveisFesta(Mockito.anyInt())).thenReturn(lotes);

		List<Lote> lotesReturn = loteService.encontrarLotesCompraveisFesta(festaTest().getCodFesta());

		assertEquals(lotesReturn.get(0), lotes.get(0));

	}

	@Test
	void encontrarLoteDadosPublicosTest() {
		Lote lote = loteTest();

		Mockito.when(loteRepository.findByCodLote(Mockito.anyInt())).thenReturn(lote);

		Lote loteReturn = loteService.encontrarLoteDadosPublicos(lote.getCodLote());

		assertEquals(lote, loteReturn);

	}

}
