package com.eventmanager.pachanga.services;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

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

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ ArquivosBancoService.class, FileWriter.class})
class ArquivosBancoServiceTest {

	@Mock
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@Mock
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@Mock
	private JwtTokenUtil defaultJwtTokenUtil;

	@Mock
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	@Mock
	private DadoBancarioRepository dadoBancarioRepository;

	@Mock
	private FestaRepository festaRepository;

	@Mock
	private IngressoRepository ingressoRepository;

	@InjectMocks
	private ArquivosBancoService arquivosBancoService;
	
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

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
		
		FileWriter file = PowerMockito.mock(FileWriter.class);
		
		PowerMockito.whenNew(FileWriter.class).withArguments("remessaItau.RRM").thenReturn(file);
		
		PowerMockito.doNothing().when(file).write(Mockito.anyString());
		
		PowerMockito.when(festaRepository.findFestasAcabadas()).thenReturn(festas);
		PowerMockito.when(dadoBancarioRepository.findDadosBancariosFesta(Mockito.anyInt())).thenReturn(dadoBancarioTest());
		PowerMockito.when(ingressoRepository.findIngressosFesta(Mockito.anyInt())).thenReturn(ingressos);

		arquivosBancoService.criacaoRemessa();
		
		file.close();
		
	}
}
