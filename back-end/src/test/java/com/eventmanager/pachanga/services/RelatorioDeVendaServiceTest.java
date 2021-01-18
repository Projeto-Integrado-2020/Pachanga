package com.eventmanager.pachanga.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
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
import com.eventmanager.pachanga.domains.Lote;
import com.eventmanager.pachanga.dtos.RelatorioDeVendaTO;
import com.eventmanager.pachanga.factory.RelatorioDeVendaFactory;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.IngressoRepository;
import com.eventmanager.pachanga.repositories.LoteRepository;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RelatorioDeVendaService.class)
class RelatorioDeVendaServiceTest {

	@Autowired
	private RelatorioDeVendaService relatorioDeVendaService;

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
	private FestaRepository festaRepository;

	@MockBean
	private IngressoRepository ingressoRepository;

	@MockBean
	private RelatorioAreaSegurancaService relatorioAreaSegurancaService;

	@MockBean
	private RelatorioDeVendaFactory relatorioDeVendaFactory;

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

	public List<Lote> listaLoteTest() {
		List<Lote> lotes = new ArrayList<>();
		lotes.add(loteTest());
		lotes.add(loteTest());
		return lotes;
	}

	@Test
	void relatorioDeIngressosPagosCompradosTest() throws Exception {

		List<Lote> lotes = new ArrayList<>();
		lotes.addAll(listaLoteTest());

		Mockito.when(loteRepository.listaLoteFesta(Mockito.anyInt())).thenReturn(lotes);
		Mockito.when(ingressoRepository.findQuantidadeIngressosLotePago(Mockito.anyInt())).thenReturn(1);
		Mockito.when(ingressoRepository.findQuantidadeIngressosLoteComprado(Mockito.anyInt())).thenReturn(1);
		Mockito.when(relatorioDeVendaFactory.getIngressosPagosComprados(Mockito.anyMap()))
				.thenReturn(new RelatorioDeVendaTO());

		RelatorioDeVendaTO relatorio = relatorioDeVendaService.relatorioDeIngressosPagosComprados(1, 2);

		assertEquals(true, relatorio != null);

	}

	@Test
	void relatorioDeIngressosTest() throws Exception {
		List<Lote> lotes = new ArrayList<>();
		lotes.addAll(listaLoteTest());

		Mockito.when(loteRepository.listaLoteFesta(Mockito.anyInt())).thenReturn(lotes);
		Mockito.when(ingressoRepository.findQuantidadeIngressosLote(Mockito.anyInt())).thenReturn(1);
		Mockito.when(relatorioDeVendaFactory.getRelatorioDeVenda(Mockito.anyMap()))
				.thenReturn(new RelatorioDeVendaTO());

		RelatorioDeVendaTO relatorio = relatorioDeVendaService.relatorioDeIngressos(1, 2);

		assertEquals(true, relatorio != null);

	}
	
	@Test
	void relatorioLucroFestaTest() throws Exception {
		List<Lote> lotes = new ArrayList<>();
		lotes.addAll(listaLoteTest());

		Mockito.when(loteRepository.listaLoteFesta(Mockito.anyInt())).thenReturn(lotes);
		Mockito.when(ingressoRepository.findLucroEsperadoLote(Mockito.anyInt(), Mockito.anyInt())).thenReturn(1f);
		Mockito.when(ingressoRepository.findLucroRealizadoLote(Mockito.anyInt(), Mockito.anyInt())).thenReturn(1f, null);
		Mockito.when(festaRepository.findByCodFesta(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(relatorioDeVendaFactory.getRelatorioLucroTotalFesta(Mockito.any(), Mockito.any(), Mockito.anyString())).thenReturn(new RelatorioDeVendaTO());
		
		RelatorioDeVendaTO relatorio = relatorioDeVendaService.relatorioLucroFesta(1, 2);
		
		assertEquals(true, relatorio != null);
	}

}
