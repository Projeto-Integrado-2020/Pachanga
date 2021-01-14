package com.eventmanager.pachanga.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.dtos.IngressoTO;
import com.eventmanager.pachanga.dtos.InsercaoIngresso;
import com.eventmanager.pachanga.factory.IngressoFactory;
import com.eventmanager.pachanga.repositories.IngressoRepository;
import com.eventmanager.pachanga.repositories.LoteRepository;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoStatusCompra;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;
import com.eventmanager.pachanga.tipo.TipoStatusIngresso;

@RunWith(SpringRunner.class)
@WebMvcTest(value=IngressoService.class)
public class IngressoServiceTest {
	
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
		ingresso.setStatusIngresso(TipoStatusIngresso.CHECKED.getDescricao());
		ingresso.setUrlBoleto("https://teste.com");
		return ingresso;
	}
	
	private IngressoTO ingressoTOTest() {
		IngressoTO ingressoTO = new IngressoTO();
		ingressoTO.setCodBoleto("ABC");
		ingressoTO.setCodIngresso("1");
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
	public static Usuario usuarioTest() throws Exception{
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("fc68b677646b5f018d1762e9a19bf65180d9aab2794794340ade50e0d78a239affd43a613e7136a61b5d63b09f072c0c039dea4281873abe826d6e6285d9cefef0a0d868d3b0b0d4582ec787b473b4e0");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setGenero("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");

		return usuarioTest;
	}
	
	@Test
	public void getIngressosUser() throws Exception {
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
	public void getIngressosFesta() {
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
	/*
	@Test
	public void addListaIngresso() {
		
		InsercaoIngresso ingressosInsercao = new InsercaoIngresso();
		ingressosInsercao.setListaIngresso(listaIngressoTOTest());
		List<Ingresso> expected = listaIngressoTest();
		
		Mockito.when(loteRepository.findLoteByFestaAndLote(Mockito.anyInt(), Mockito.anyInt())).thenReturn(loteTest());
		Mockito.when(ingressoRepository.findIngressosLoteVendido(Mockito.anyInt(), Mockito.anyInt())).thenReturn(3);
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(ingressoFactory.getIngresso(Mockito.any(IngressoTO.class), Mockito.any(Usuario.class),  Mockito.any(Festa.class),  Mockito.any(Lote.class))).thenReturn(ingressoTest());
		Mockito.when(notificacaoService.getDataAtual()).thenReturn(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		
		List<IngressoTO> retorno = ingressoService.addListaIngresso(ingressosInsercao);
		
		assertEquals(expected.get(0).getCodIngresso(), retorno.get(0).getCodIngresso());
		assertEquals(expected.get(0).getCodBoleto(), retorno.get(0).getCodBoleto());
		assertEquals(expected.get(0).getDataCheckin(), retorno.get(0).getDataCheckin());
		assertEquals(expected.get(0).getDataCompra(), retorno.get(0).getDataCompra());
		assertEquals(expected.get(0).getEmailTitular(), retorno.get(0).getEmailTitular());
		assertEquals(expected.get(0).getFesta().getCodFesta(), retorno.get(0).getFesta().getCodFesta());
		assertEquals(expected.get(0).getNomeTitular(), retorno.get(0).getNomeTitular());
		assertEquals(expected.get(0).getPreco(), retorno.get(0).getPreco());
		assertEquals(expected.get(0).getStatusCompra(), retorno.get(0).getStatusCompra());
		assertEquals(expected.get(0).getStatusIngresso(), retorno.get(0).getStatusIngresso());
		assertEquals(expected.get(0).getUrlBoleto(), retorno.get(0).getUrlBoleto());
	}
*/
}
