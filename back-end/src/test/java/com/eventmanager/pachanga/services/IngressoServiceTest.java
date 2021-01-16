package com.eventmanager.pachanga.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
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
import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.dtos.InfoPagamentoBoletoTO;
import com.eventmanager.pachanga.dtos.IngressoTO;
import com.eventmanager.pachanga.dtos.InsercaoIngresso;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.IngressoFactory;
import com.eventmanager.pachanga.repositories.IngressoRepository;
import com.eventmanager.pachanga.repositories.LoteRepository;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoPagamentoBoleto;
import com.eventmanager.pachanga.tipo.TipoStatusCompra;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;
import com.eventmanager.pachanga.tipo.TipoStatusIngresso;
import com.eventmanager.pachanga.utils.EmailMensagem;
import com.eventmanager.pachanga.utils.EnvioEmMassaDeConvite;
import com.eventmanager.pachanga.utils.HashBuilder;
import com.eventmanager.pachanga.utils.PagSeguroUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(value = IngressoService.class)
class IngressoServiceTest {

	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	@MockBean
	private IngressoRepository ingressoRepository;

	@MockBean
	private LoteRepository loteRepository;

	@MockBean
	private UsuarioService usuarioService;

	@MockBean
	private FestaService festaService;

	@MockBean
	private IngressoFactory ingressoFactory;

	@MockBean
	private LoteService loteService;

	@MockBean
	private GrupoService grupoService;

	@MockBean
	private NotificacaoService notificacaoService;

	@Autowired
	private IngressoService ingressoService;

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
	
	private InsercaoIngresso insercaoIngressoTest() {
		InsercaoIngresso insercaoIngresso = new InsercaoIngresso();
		insercaoIngresso.setListaIngresso(listaIngressoTOTest());
		insercaoIngresso.setInfoPagmaento(new InfoPagamentoBoletoTO());
		return insercaoIngresso;
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

	private FestaTO festaToTest() {
		FestaTO festaTo = new FestaTO();
		festaTo.setCodEnderecoFesta("https//:minhacasa.org");
		festaTo.setDescOrganizador("sou demente");
		festaTo.setHorarioInicioFesta(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		festaTo.setHorarioFimFesta(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		festaTo.setNomeFesta("festao");
		festaTo.setOrganizador("Joao Neves");
		festaTo.setStatusFesta("I");
		festaTo.setDescricaoFesta("Bugago");
		festaTo.setHorarioFimFestaReal(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		return festaTo;
	}

	private Ingresso ingressoTest() {
		Lote lote = loteTest();
		Ingresso ingresso = new Ingresso();
		ingresso.setCodBoleto("ABC");
		ingresso.setCodIngresso("1");
		ingresso.setDataCheckin(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		ingresso.setDataCompra(LocalDateTime.of(2014, Month.JUNE, 22, 19, 10));
		ingresso.setEmailTitular("teste@email.com.invalid");
		ingresso.setFesta(lote.getFesta());
		ingresso.setLote(lote);
		ingresso.setNomeTitular("Fulano");
		ingresso.setPreco((float) 2.30);
		ingresso.setStatusCompra(TipoStatusCompra.PAGO.getDescricao());
		ingresso.setStatusIngresso(TipoStatusIngresso.UNCHECKED.getDescricao());
		ingresso.setUrlBoleto("https://teste.com");
		return ingresso;
	}

	private IngressoTO ingressoTOTest() {
		IngressoTO ingressoTO = new IngressoTO();
		ingressoTO.setCodBoleto("ABC");
		ingressoTO.setCodIngresso("1");
		ingressoTO.setBoleto(true);
		ingressoTO.setDataCheckin(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		ingressoTO.setDataCompra(LocalDateTime.of(2014, Month.JUNE, 22, 19, 10));
		ingressoTO.setEmailTitular("teste@email.com.invalid");
		ingressoTO.setFesta(festaToTest());
		ingressoTO.setCodLote(loteTest().getCodLote());
		ingressoTO.setNomeTitular("Fulano");
		ingressoTO.setPreco((float) 2.30);
		ingressoTO.setStatusCompra(TipoStatusCompra.PAGO.getDescricao());
		ingressoTO.setStatusIngresso(TipoStatusIngresso.CHECKED.getDescricao());
		ingressoTO.setUrlBoleto("https://teste.com");
		return ingressoTO;
	}

	private List<Ingresso> listaIngressoTest() {
		List<Ingresso> ingressos = new ArrayList<>();
		ingressos.add(ingressoTest());
		return ingressos;
	}

	private List<IngressoTO> listaIngressoTOTest() {
		List<IngressoTO> ingressosTO = new ArrayList<>();
		ingressosTO.add(ingressoTOTest());
		return ingressosTO;
	}

	@SuppressWarnings("deprecation")
	public static Usuario usuarioTest() {
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha(
				"fc68b677646b5f018d1762e9a19bf65180d9aab2794794340ade50e0d78a239affd43a613e7136a61b5d63b09f072c0c039dea4281873abe826d6e6285d9cefef0a0d868d3b0b0d4582ec787b473b4e0");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setGenero("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");

		return usuarioTest;
	}

	@Test
	void getIngressosUser() {
		int codUsuario = 1;
		List<Ingresso> expected = listaIngressoTest();

		Mockito.when(usuarioService.validarUsuario(Mockito.anyInt())).thenReturn(usuarioTest());
		Mockito.when(ingressoRepository.findIngressosUser(Mockito.anyInt())).thenReturn(listaIngressoTest());

		List<Ingresso> retorno = ingressoService.getIngressosUser(codUsuario);

		assertEquals(expected.get(0).getCodIngresso(), retorno.get(0).getCodIngresso());
		assertEquals(expected.get(0).getCodBoleto(), retorno.get(0).getCodBoleto());
		assertEquals(expected.get(0).getDataCheckin(), retorno.get(0).getDataCheckin());
		assertEquals(expected.get(0).getDataCompra(), retorno.get(0).getDataCompra());
		assertEquals(expected.get(0).getEmailTitular(), retorno.get(0).getEmailTitular());
		assertEquals(expected.get(0).getFesta().getCodFesta(), retorno.get(0).getFesta().getCodFesta());
		assertEquals(expected.get(0).getLote().getCodLote(), retorno.get(0).getLote().getCodLote());
		assertEquals(expected.get(0).getNomeTitular(), retorno.get(0).getNomeTitular());
		assertEquals(expected.get(0).getPreco(), retorno.get(0).getPreco());
		assertEquals(expected.get(0).getStatusCompra(), retorno.get(0).getStatusCompra());
		assertEquals(expected.get(0).getStatusIngresso(), retorno.get(0).getStatusIngresso());
		assertEquals(expected.get(0).getUrlBoleto(), retorno.get(0).getUrlBoleto());
	}

	@Test
	void getIngressosFesta() {
		int codFesta = 1;
		List<Ingresso> expected = listaIngressoTest();

		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(ingressoRepository.findIngressosFesta(Mockito.anyInt())).thenReturn(listaIngressoTest());

		List<Ingresso> retorno = ingressoService.getIngressosFesta(codFesta);

		assertEquals(expected.get(0).getCodIngresso(), retorno.get(0).getCodIngresso());
		assertEquals(expected.get(0).getCodBoleto(), retorno.get(0).getCodBoleto());
		assertEquals(expected.get(0).getDataCheckin(), retorno.get(0).getDataCheckin());
		assertEquals(expected.get(0).getDataCompra(), retorno.get(0).getDataCompra());
		assertEquals(expected.get(0).getEmailTitular(), retorno.get(0).getEmailTitular());
		assertEquals(expected.get(0).getFesta().getCodFesta(), retorno.get(0).getFesta().getCodFesta());
		assertEquals(expected.get(0).getLote().getCodLote(), retorno.get(0).getLote().getCodLote());
		assertEquals(expected.get(0).getNomeTitular(), retorno.get(0).getNomeTitular());
		assertEquals(expected.get(0).getPreco(), retorno.get(0).getPreco());
		assertEquals(expected.get(0).getStatusCompra(), retorno.get(0).getStatusCompra());
		assertEquals(expected.get(0).getStatusIngresso(), retorno.get(0).getStatusIngresso());
		assertEquals(expected.get(0).getUrlBoleto(), retorno.get(0).getUrlBoleto());
	}

	@Test
	void updateCheckinSucesso() {
		Ingresso ingresso = ingressoTest();
		ingresso.getFesta().setStatusFesta(TipoStatusFesta.INICIADO.getValor());

		Mockito.when(ingressoRepository.findIngressoByCodIngresso(Mockito.anyString())).thenReturn(ingresso);
		Mockito.when(ingressoRepository.save(Mockito.any())).thenReturn(null);

		Ingresso ingressoRetorno = ingressoService.updateCheckin("abc", 1, 2);

		assertEquals(true, ingressoRetorno != null);
	}

	@Test
	void updateCheckinErroIngressoExistente() {

		Mockito.when(ingressoRepository.findIngressoByCodIngresso(Mockito.anyString())).thenReturn(null);
		Mockito.when(ingressoRepository.save(Mockito.any())).thenReturn(null);

		String expected = "";

		try {
			ingressoService.updateCheckin("abc", 1, 2);
		} catch (ValidacaoException e) {
			expected = e.getMessage();
		}

		assertEquals("INGRNFOU", expected);
	}

	@Test
	void updateCheckinErroHorarioIncorreto() {
		Ingresso ingresso = ingressoTest();

		Mockito.when(ingressoRepository.findIngressoByCodIngresso(Mockito.anyString())).thenReturn(ingresso);
		Mockito.when(ingressoRepository.save(Mockito.any())).thenReturn(null);

		String expected = "";

		try {
			ingressoService.updateCheckin("abc", 1, 2);
		} catch (ValidacaoException e) {
			expected = e.getMessage();
		}

		assertEquals("HORAINCC", expected);
	}

	@Test
	void updateCheckinErroIngressoJaVerificado() {
		Ingresso ingresso = ingressoTest();
		ingresso.setStatusIngresso(TipoStatusIngresso.CHECKED.getDescricao());
		ingresso.getFesta().setStatusFesta(TipoStatusFesta.INICIADO.getValor());

		Mockito.when(ingressoRepository.findIngressoByCodIngresso(Mockito.anyString())).thenReturn(ingresso);
		Mockito.when(ingressoRepository.save(Mockito.any())).thenReturn(null);

		String expected = "";

		try {
			ingressoService.updateCheckin("abc", 1, 2);
		} catch (ValidacaoException e) {
			expected = e.getMessage();
		}

		assertEquals("INGRVERI", expected);
	}

	@Test
	void updateCheckinErroIngressoNaoFoiPago() {
		Ingresso ingresso = ingressoTest();
		ingresso.setStatusCompra(TipoStatusCompra.COMPRADO.getDescricao());
		ingresso.getFesta().setStatusFesta(TipoStatusFesta.INICIADO.getValor());

		Mockito.when(ingressoRepository.findIngressoByCodIngresso(Mockito.anyString())).thenReturn(ingresso);
		Mockito.when(ingressoRepository.save(Mockito.any())).thenReturn(null);

		String expected = "";

		try {
			ingressoService.updateCheckin("abc", 1, 2);
		} catch (ValidacaoException e) {
			expected = e.getMessage();
		}

		assertEquals("INGRNOTP", expected);
	}

	@Test
	void updateCheckinErroIngressoNaoPertenceFesta() {
		Ingresso ingresso = ingressoTest();
		ingresso.getFesta().setStatusFesta(TipoStatusFesta.INICIADO.getValor());
		ingresso.getFesta().setCodFesta(3);

		Mockito.when(ingressoRepository.findIngressoByCodIngresso(Mockito.anyString())).thenReturn(ingresso);
		Mockito.when(ingressoRepository.save(Mockito.any())).thenReturn(null);

		String expected = "";

		try {
			ingressoService.updateCheckin("abc", 1, 2);
		} catch (ValidacaoException e) {
			expected = e.getMessage();
		}

		assertEquals("INGRNOTF", expected);
	}

	@Test
	void updateStatusCompraIngressoPagoTest() {

		try (MockedStatic<PagSeguroUtils> mockPagSeguro = Mockito.mockStatic(PagSeguroUtils.class)) {
			try (MockedStatic<HashBuilder> mockHash = Mockito.mockStatic(HashBuilder.class)) {
				try (MockedStatic<EnvioEmMassaDeConvite> mockEnvioConvite = Mockito
						.mockStatic(EnvioEmMassaDeConvite.class)) {
					mockHash.when(() -> HashBuilder.compararCodigos(Mockito.anyString(), Mockito.anyString()))
							.thenReturn(true);
					mockPagSeguro.when(() -> PagSeguroUtils.retornoDadosTransacao(Mockito.anyString()))
							.thenReturn(TipoPagamentoBoleto.PAGO.getStatus());

					Mockito.when(ingressoRepository.findIngressosEmProcessoBoleto()).thenReturn(listaIngressoTest());
					Mockito.doNothing().when(ingressoRepository).delete(Mockito.any());

					ingressoService.updateStatusCompra("teste", "teste");
				}
			}
		}

	}

	@Test
	void updateStatusCompraIngressoDevolvidoTest() {

		try (MockedStatic<PagSeguroUtils> mockPagSeguro = Mockito.mockStatic(PagSeguroUtils.class)) {
			try (MockedStatic<HashBuilder> mockHash = Mockito.mockStatic(HashBuilder.class)) {
				try (MockedStatic<EnvioEmMassaDeConvite> mockEnvioConvite = Mockito
						.mockStatic(EnvioEmMassaDeConvite.class)) {
					mockHash.when(() -> HashBuilder.compararCodigos(Mockito.anyString(), Mockito.anyString()))
							.thenReturn(true);
					mockPagSeguro.when(() -> PagSeguroUtils.retornoDadosTransacao(Mockito.anyString()))
							.thenReturn(TipoPagamentoBoleto.DEVOLVIDO.getStatus());

					Mockito.when(ingressoRepository.findIngressosEmProcessoBoleto()).thenReturn(listaIngressoTest());
					Mockito.doNothing().when(ingressoRepository).delete(Mockito.any());

					ingressoService.updateStatusCompra("teste", "teste");
				}
			}
		}

	}

	@Test
	void updateStatusCompraIngressoCanceladoTest() {

		try (MockedStatic<PagSeguroUtils> mockPagSeguro = Mockito.mockStatic(PagSeguroUtils.class)) {
			try (MockedStatic<HashBuilder> mockHash = Mockito.mockStatic(HashBuilder.class)) {
				try (MockedStatic<EnvioEmMassaDeConvite> mockEnvioConvite = Mockito
						.mockStatic(EnvioEmMassaDeConvite.class)) {
					mockHash.when(() -> HashBuilder.compararCodigos(Mockito.anyString(), Mockito.anyString()))
							.thenReturn(true);
					mockPagSeguro.when(() -> PagSeguroUtils.retornoDadosTransacao(Mockito.anyString()))
							.thenReturn(TipoPagamentoBoleto.CANCELADO.getStatus());

					Mockito.when(ingressoRepository.findIngressosEmProcessoBoleto()).thenReturn(listaIngressoTest());
					Mockito.doNothing().when(ingressoRepository).delete(Mockito.any());

					ingressoService.updateStatusCompra("teste", "teste");
				}
			}
		}

	}

	@Test
	void updateStatusCompraIngressoDebitadoTest() {

		try (MockedStatic<PagSeguroUtils> mockPagSeguro = Mockito.mockStatic(PagSeguroUtils.class)) {
			try (MockedStatic<HashBuilder> mockHash = Mockito.mockStatic(HashBuilder.class)) {
				try (MockedStatic<EnvioEmMassaDeConvite> mockEnvioConvite = Mockito
						.mockStatic(EnvioEmMassaDeConvite.class)) {
					mockHash.when(() -> HashBuilder.compararCodigos(Mockito.anyString(), Mockito.anyString()))
							.thenReturn(true);
					mockPagSeguro.when(() -> PagSeguroUtils.retornoDadosTransacao(Mockito.anyString()))
							.thenReturn(TipoPagamentoBoleto.DEBITADO.getStatus());

					Mockito.when(ingressoRepository.findIngressosEmProcessoBoleto()).thenReturn(listaIngressoTest());
					Mockito.doNothing().when(ingressoRepository).delete(Mockito.any());

					ingressoService.updateStatusCompra("teste", "teste");
				}
			}
		}

	}

	@Test
	void addListaIngressoTestSucessoBoleto() {
		try (MockedStatic<PagSeguroUtils> mockPagSeguro = Mockito.mockStatic(PagSeguroUtils.class)) {
			try (MockedStatic<EmailMensagem> emailMensagem = Mockito.mockStatic(EmailMensagem.class)) {
				try (MockedStatic<HashBuilder> mockHash = Mockito.mockStatic(HashBuilder.class)) {
					Mockito.when(loteRepository.findLoteByFestaAndLote(Mockito.anyInt(), Mockito.anyInt()))
					.thenReturn(loteTest());
					Mockito.when(ingressoRepository.findIngressosLoteVendido(Mockito.anyInt(), Mockito.anyInt())).thenReturn(2);
					Mockito.when(ingressoRepository.findIngressoByCodIngresso(Mockito.anyString())).thenReturn(ingressoTest(), ingressoTest(), null);
					Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
					mockPagSeguro.when(() -> PagSeguroUtils.criacaoBoleto(Mockito.any(), Mockito.anyFloat(),
							Mockito.anyString(), Mockito.any())).thenReturn("teste");
					Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
					Mockito.when(usuarioService.validarUsuario(Mockito.anyInt())).thenReturn(usuarioTest());
					Mockito.when(loteService.validarLoteExistente(Mockito.anyInt())).thenReturn(loteTest());
					Mockito.when(ingressoFactory.getIngresso(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
					.thenReturn(ingressoTest());
					Mockito.when(notificacaoService.getDataAtual()).thenReturn(LocalDateTime.of(2014, Month.JUNE, 22, 19, 10));
					Mockito.when(ingressoFactory.getIngressoTO(Mockito.any(), Mockito.anyString())).thenReturn(ingressoTOTest());
					mockHash.when(()-> HashBuilder.gerarCodigoHasheado(Mockito.anyString())).thenReturn("teste");
					mockHash.when(() -> HashBuilder.compararCodigos(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
					Mockito.when(ingressoRepository.findIngressosEmProcessoBoletoFesta(Mockito.anyInt())).thenReturn(listaIngressoTest());
					
					
					
					List<IngressoTO> ingressosTo = ingressoService.addListaIngresso(insercaoIngressoTest());
					
					assertEquals(true, !ingressosTo.isEmpty());
					
				}
			}

		}

	}
	
	@Test
	void addListaIngressoTestSucesso() {
		try (MockedStatic<PagSeguroUtils> mockPagSeguro = Mockito.mockStatic(PagSeguroUtils.class)) {
			try (MockedStatic<EmailMensagem> emailMensagem = Mockito.mockStatic(EmailMensagem.class)) {
				try (MockedStatic<HashBuilder> mockHash = Mockito.mockStatic(HashBuilder.class)) {
					Mockito.when(loteRepository.findLoteByFestaAndLote(Mockito.anyInt(), Mockito.anyInt()))
					.thenReturn(loteTest());
					Mockito.when(ingressoRepository.findIngressosLoteVendido(Mockito.anyInt(), Mockito.anyInt())).thenReturn(2);
					Mockito.when(ingressoRepository.findIngressoByCodIngresso(Mockito.anyString())).thenReturn(ingressoTest(), ingressoTest(), null);
					Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
					mockPagSeguro.when(() -> PagSeguroUtils.criacaoBoleto(Mockito.any(), Mockito.anyFloat(),
							Mockito.anyString(), Mockito.any())).thenReturn("teste");
					Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
					Mockito.when(usuarioService.validarUsuario(Mockito.anyInt())).thenReturn(usuarioTest());
					Mockito.when(loteService.validarLoteExistente(Mockito.anyInt())).thenReturn(loteTest());
					Mockito.when(ingressoFactory.getIngresso(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
					.thenReturn(ingressoTest());
					Mockito.when(notificacaoService.getDataAtual()).thenReturn(LocalDateTime.of(2014, Month.JUNE, 22, 19, 10));
					Mockito.when(ingressoFactory.getIngressoTO(Mockito.any(), Mockito.any())).thenReturn(ingressoTOTest());
					mockHash.when(()-> HashBuilder.gerarCodigoHasheado(Mockito.anyString())).thenReturn("teste");
					mockHash.when(() -> HashBuilder.compararCodigos(Mockito.anyString(), Mockito.anyString())).thenReturn(true, false, false);
					Mockito.when(ingressoRepository.findIngressosEmProcessoBoletoFesta(Mockito.anyInt())).thenReturn(listaIngressoTest());
					
					InsercaoIngresso insercaoIngresso = insercaoIngressoTest();
					insercaoIngresso.getListaIngresso().get(0).setBoleto(false);
					insercaoIngresso.getListaIngresso().get(0).setCodBoleto("teste123");
					
					List<IngressoTO> ingressosTo = ingressoService.addListaIngresso(insercaoIngresso);
					
					assertEquals(true, !ingressosTo.isEmpty());
					
				}
			}

		}

	}
	
	@Test
	void addListaIngressoTestErroQuantidadeInvalido() {
		try (MockedStatic<PagSeguroUtils> mockPagSeguro = Mockito.mockStatic(PagSeguroUtils.class)) {
			try (MockedStatic<EmailMensagem> emailMensagem = Mockito.mockStatic(EmailMensagem.class)) {
				try (MockedStatic<HashBuilder> mockHash = Mockito.mockStatic(HashBuilder.class)) {
					Mockito.when(loteRepository.findLoteByFestaAndLote(Mockito.anyInt(), Mockito.anyInt()))
					.thenReturn(loteTest());
					Mockito.when(ingressoRepository.findIngressosLoteVendido(Mockito.anyInt(), Mockito.anyInt())).thenReturn(100);
					Mockito.when(ingressoRepository.findIngressoByCodIngresso(Mockito.anyString())).thenReturn(ingressoTest());
					Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
					mockPagSeguro.when(() -> PagSeguroUtils.criacaoBoleto(Mockito.any(), Mockito.anyFloat(),
							Mockito.anyString(), Mockito.any())).thenReturn("teste");
					Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
					Mockito.when(usuarioService.validarUsuario(Mockito.anyInt())).thenReturn(usuarioTest());
					Mockito.when(loteService.validarLoteExistente(Mockito.anyInt())).thenReturn(loteTest());
					Mockito.when(ingressoFactory.getIngresso(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
					.thenReturn(ingressoTest());
					Mockito.when(notificacaoService.getDataAtual()).thenReturn(LocalDateTime.of(2014, Month.JUNE, 22, 19, 10));
					Mockito.when(ingressoFactory.getIngressoTO(Mockito.any(), Mockito.anyString())).thenReturn(ingressoTOTest());
					mockHash.when(()-> HashBuilder.gerarCodigoHasheado(Mockito.anyString())).thenReturn("teste");
					
					String erro = "";
					
					try {
						ingressoService.addListaIngresso(insercaoIngressoTest());
						
					} catch (ValidacaoException e) {
						erro = e.getMessage();
					}
					
					assertEquals("QINGRINV", erro);
					
				}
			}

		}

	}
	
	@Test
	void addIngressoTestSucesso() {
		try (MockedStatic<PagSeguroUtils> mockPagSeguro = Mockito.mockStatic(PagSeguroUtils.class)) {
			try (MockedStatic<EmailMensagem> emailMensagem = Mockito.mockStatic(EmailMensagem.class)) {
				try (MockedStatic<HashBuilder> mockHash = Mockito.mockStatic(HashBuilder.class)) {
					Mockito.when(loteRepository.findLoteByFestaAndLote(Mockito.anyInt(), Mockito.anyInt()))
					.thenReturn(loteTest());
					Mockito.when(ingressoRepository.findIngressosLoteVendido(Mockito.anyInt(), Mockito.anyInt())).thenReturn(2);
					Mockito.when(ingressoRepository.findIngressoByCodIngresso(Mockito.anyString())).thenReturn(ingressoTest(), ingressoTest(), null);
					Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
					mockPagSeguro.when(() -> PagSeguroUtils.criacaoBoleto(Mockito.any(), Mockito.anyFloat(),
							Mockito.anyString(), Mockito.any())).thenReturn("teste");
					Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
					Mockito.when(usuarioService.validarUsuario(Mockito.anyInt())).thenReturn(usuarioTest());
					Mockito.when(loteService.validarLoteExistente(Mockito.anyInt())).thenReturn(loteTest());
					Mockito.when(ingressoFactory.getIngresso(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
					.thenReturn(ingressoTest());
					Mockito.when(notificacaoService.getDataAtual()).thenReturn(LocalDateTime.of(2014, Month.JUNE, 22, 19, 10));
					Mockito.when(ingressoFactory.getIngressoTO(Mockito.any(), Mockito.anyString())).thenReturn(ingressoTOTest());
					mockHash.when(()-> HashBuilder.gerarCodigoHasheado(Mockito.anyString())).thenReturn("teste");
					mockHash.when(() -> HashBuilder.compararCodigos(Mockito.anyString(), Mockito.anyString())).thenReturn(true, false);
					Mockito.when(ingressoRepository.findIngressosEmProcessoBoletoFesta(Mockito.anyInt())).thenReturn(listaIngressoTest());
					
					
					
					IngressoTO ingressosTo = ingressoService.addIngresso(ingressoTOTest(), null);
					
					assertEquals(true, ingressosTo != null);
					
				}
			}

		}
	}

}
