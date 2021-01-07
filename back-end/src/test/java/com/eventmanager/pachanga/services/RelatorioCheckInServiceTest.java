package com.eventmanager.pachanga.services;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.eventmanager.pachanga.dtos.RelatorioCheckInTO;
import com.eventmanager.pachanga.factory.RelatorioCheckInFactory;
import com.eventmanager.pachanga.repositories.IngressoRepository;
import com.eventmanager.pachanga.repositories.LoteRepository;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RelatorioCheckInService.class)
class RelatorioCheckInServiceTest {

	@Autowired
	private RelatorioCheckInService relatorioCheckInService;

	@MockBean
	private IngressoRepository ingressoRepository;

	@MockBean
	private RelatorioCheckInFactory relatorioCheckInFactory;

	@MockBean
	private RelatorioAreaSegurancaService relatorioAreaSegurancaService;
	
	@MockBean
	private LoteRepository loteRepository;

	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	private Ingresso ingressoTest() {
		Ingresso ingresso = new Ingresso();
		ingresso.setUsuario(usuarioTest());
		ingresso.setDataCheckin(LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40));
		return ingresso;
	}
	
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
	
	private Festa festaTest(){
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

	@Test
	void ingressosCompradosEntradasSucesso() throws Exception {
		
		List<Lote> lotes = new ArrayList<>();
		lotes.add(loteTest());

		Mockito.when(ingressoRepository.findIngressosFestaVendido(Mockito.anyInt())).thenReturn(1);

		Mockito.when(ingressoRepository.findIngressosChecked(Mockito.anyInt())).thenReturn(1);
		
		Mockito.when(loteRepository.listaLoteFesta(Mockito.anyInt())).thenReturn(lotes);

		Mockito.when(relatorioCheckInFactory.relatorioIngressosCompradosEntradas(Mockito.anyMap()))
				.thenReturn(new RelatorioCheckInTO());

		RelatorioCheckInTO relatorio = relatorioCheckInService.ingressosCompradosEntradas(1, 2);

		assertEquals(true, relatorio != null);

	}

	@SuppressWarnings("deprecation")
	@Test
	void faixaEtariaFestaSucesso() throws Exception {

		Ingresso ingresso = ingressoTest();
		Ingresso ingressoSemDataNasc = ingressoTest();
		ingressoSemDataNasc.getUsuario().setDtNasc(null);

		Date dataAtual = Calendar.getInstance().getTime();

		ingresso.getUsuario().setDtNasc(new Date(2000, dataAtual.getMonth(), dataAtual.getDay() + 1));

		List<Ingresso> ingressos = new ArrayList<>();
		ingressos.add(ingressoTest());
		ingressos.add(ingressoTest());
		ingressos.add(ingresso);
		ingressos.add(ingressoSemDataNasc);

		Mockito.when(ingressoRepository.findIngressosFesta(Mockito.anyInt())).thenReturn(ingressos);

		Mockito.when(relatorioCheckInFactory.relatorioFaixaEtariaFesta(Mockito.anyMap()))
				.thenReturn(new RelatorioCheckInTO());

		RelatorioCheckInTO relatorio = relatorioCheckInService.faixaEtariaFesta(1, 2);

		assertEquals(true, relatorio != null);

	}

	@Test
	void quantidadeGeneroFestaSucesso() throws Exception {

		Ingresso ingresso = ingressoTest();

		ingresso.getUsuario().setGenero(null);

		List<Ingresso> ingressos = new ArrayList<>();
		ingressos.add(ingressoTest());
		ingressos.add(ingressoTest());
		ingressos.add(ingresso);

		Mockito.when(ingressoRepository.findIngressosFesta(Mockito.anyInt())).thenReturn(ingressos);

		Mockito.when(relatorioCheckInFactory.relatorioGeneroFesta(Mockito.anyMap()))
				.thenReturn(new RelatorioCheckInTO());

		RelatorioCheckInTO relatorio = relatorioCheckInService.quantidadeGeneroFesta(1, 2);

		assertEquals(true, relatorio != null);

	}

	@Test
	void quantidadeEntradaHorasSucesso() throws Exception {

		List<Ingresso> ingressos = new ArrayList<>();
		ingressos.add(ingressoTest());
		ingressos.add(ingressoTest());

		Mockito.when(ingressoRepository.findIngressoCheckedOrdenado(Mockito.anyInt())).thenReturn(ingressos);

		Mockito.when(relatorioCheckInFactory.relatorioEntradaHora(Mockito.anyMap()))
				.thenReturn(new RelatorioCheckInTO());

		RelatorioCheckInTO relatorio = relatorioCheckInService.quantidadeEntradaHoras(1, 2);

		assertEquals(true, relatorio != null);
	}
	
	@Test
	void relatorioDeIngressosCheckInSucesso() throws Exception {

		List<Lote> lotes = new ArrayList<>();
		lotes.add(loteTest());

		Mockito.when(loteRepository.listaLoteFesta(Mockito.anyInt())).thenReturn(lotes);
		
		Mockito.when(ingressoRepository.findQuantidadeIngressosLoteStatusIngresso(Mockito.anyInt(), Mockito.anyString())).thenReturn(3,2);

		Mockito.when(relatorioCheckInFactory.relatorioCheckedUnchecked(Mockito.anyMap()))
				.thenReturn(new RelatorioCheckInTO());

		RelatorioCheckInTO relatorio = relatorioCheckInService.relatorioDeIngressosCheckIn(1, 2);

		assertEquals(true, relatorio != null);
	}

}
