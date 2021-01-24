package com.eventmanager.pachanga.services;

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

import com.eventmanager.pachanga.domains.DadoBancario;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Ingresso;
import com.eventmanager.pachanga.repositories.DadoBancarioRepository;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.IngressoRepository;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoStatusCompra;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;
import com.eventmanager.pachanga.tipo.TipoStatusIngresso;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ArquivosBancoService.class)
class ArquivosBancoServiceTest {

	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	@MockBean
	private DadoBancarioRepository dadoBancarioRepository;

	@MockBean
	private FestaRepository festaRepository;

	@MockBean
	private IngressoRepository ingressoRepository;

	@Autowired
	private ArquivosBancoService arquivosBancoService;

	private Festa criacaoFesta() throws Exception {
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

	private DadoBancario dadoBancarioTest() {
		DadoBancario dado = new DadoBancario();
		dado.setCodAgencia(1);
		dado.setCodBanco("abc");
		dado.setCodConta(1);
		dado.setCodDadosBancario(1);
		dado.setFesta(new Festa());
		return dado;
	}

	private Ingresso ingressoTest() {
		Ingresso ingresso = new Ingresso();
		ingresso.setCodBoleto("ABC");
		ingresso.setCodIngresso("1");
		ingresso.setDataCheckin(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		ingresso.setDataCompra(LocalDateTime.of(2014, Month.JUNE, 22, 19, 10));
		ingresso.setEmailTitular("teste@email.com.invalid");
		ingresso.setNomeTitular("Fulano");
		ingresso.setPreco((float) 2.30);
		ingresso.setStatusCompra(TipoStatusCompra.PAGO.getDescricao());
		ingresso.setStatusIngresso(TipoStatusIngresso.UNCHECKED.getDescricao());
		ingresso.setUrlBoleto("https://teste.com");
		return ingresso;
	}

	@Test
	void criacaoRemessaSucesso() throws Exception {

		List<Festa> festas = new ArrayList<>();
		festas.add(criacaoFesta());

		Ingresso ingresso = ingressoTest();
		ingresso.setPreco(30f);

		Ingresso ingressoGratis = ingressoTest();
		ingressoGratis.setPreco(0f);

		List<Ingresso> ingressos = new ArrayList<>();
		ingressos.add(ingressoTest());
		ingressos.add(ingresso);
		ingressos.add(ingressoGratis);

		Mockito.when(festaRepository.findFestasAcabadas()).thenReturn(festas);
		Mockito.when(dadoBancarioRepository.findDadosBancariosFesta(Mockito.anyInt())).thenReturn(dadoBancarioTest());
		Mockito.when(ingressoRepository.findIngressosFesta(Mockito.anyInt())).thenReturn(ingressos);

		arquivosBancoService.criacaoRemessa();

	}
}
