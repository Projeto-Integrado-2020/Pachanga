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

import com.eventmanager.pachanga.domains.Cupom;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.dtos.CupomTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.repositories.CupomRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;

@RunWith(SpringRunner.class)
@WebMvcTest(value=CupomService.class)
public class CupomServiceTest {
	
	@Autowired
	private CupomService cupomService;
	
	@MockBean
	private CupomRepository cupomRepository;
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	@MockBean
	private GrupoRepository grupoRepository;

	@MockBean
	private FestaService festaService;

	@MockBean
	private GrupoService grupoService;

	private Cupom gerarCupom() throws Exception {
		Cupom cupom = new Cupom();
		cupom.setCodCupom(1);
		cupom.setNomeCupom("Cupom");
		cupom.setFesta(festaTest());
		cupom.setPrecoDesconto((float) 1.75);
		return cupom;
	}
	
	private List<Cupom> gerarListDeCupons() throws Exception {
		Cupom cupom = new Cupom();
		cupom.setCodCupom(1);
		cupom.setNomeCupom("Cupom");
		cupom.setFesta(festaTest());
		cupom.setPrecoDesconto((float) 1.75);
		List<Cupom> cupons = new ArrayList<>();
		cupons.add(cupom);
		return cupons;
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
	
	private CupomTO gerarCupomTO() throws Exception {
		CupomTO cupomTO = new CupomTO();
		cupomTO.setCodCupom(1);
		cupomTO.setNomeCupom("Cupom");
		cupomTO.setCodFesta(festaTest().getCodFesta());
		cupomTO.setPrecoDesconto((float) 1.75);
		return cupomTO;
	}
	
	private List<Grupo> criarListaDeGrupo() {
		Grupo grupo = new Grupo();
		grupo.setCodGrupo(1);
		grupo.setNomeGrupo("CONVIDADO");
		List<Grupo> grupos = new ArrayList<>(); 
		grupos.add(grupo);
		return grupos;
		
	}
	
	@Test
	void getCupomSucesso() throws Exception {
		Cupom cupom = gerarCupom();
		
		Mockito.when(cupomRepository.findCupomByCod(Mockito.anyInt())).thenReturn(cupom);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criarListaDeGrupo());

		Cupom retorno = cupomService.getCupom(cupom.getCodCupom(), 1);
		
		assertEquals(retorno.getCodCupom(), cupom.getCodCupom());
		assertEquals(retorno.getNomeCupom(), cupom.getNomeCupom());
		assertEquals(retorno.getFesta().getCodFesta(), cupom.getFesta().getCodFesta());
		assertEquals(retorno.getPrecoDesconto(), cupom.getPrecoDesconto());
	}
	
	@Test
	void getCuponsFestaSucesso() throws Exception {
		Festa festa = festaTest();
		Cupom cupom = gerarCupom();
		
		Mockito.when(cupomRepository.findCuponsFesta(festa.getCodFesta())).thenReturn(gerarListDeCupons());
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criarListaDeGrupo());

		List<Cupom> listaRetorno = cupomService.getCuponsFesta(festa.getCodFesta(), 1);
		Cupom retorno = listaRetorno.get(0);
		
		assertEquals(retorno.getCodCupom(), cupom.getCodCupom());
		assertEquals(retorno.getNomeCupom(), cupom.getNomeCupom());
		assertEquals(retorno.getFesta().getCodFesta(), cupom.getFesta().getCodFesta());
		assertEquals(retorno.getPrecoDesconto(), cupom.getPrecoDesconto());
	}
	
	@Test
	void gerarCupomSucesso() throws Exception {
		Festa festa = festaTest();
		CupomTO cupomTO = gerarCupomTO();
		Cupom cupom = gerarCupom();
		
		Mockito.when(festaService.procurarFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(festa);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criarListaDeGrupo());
		Mockito.when(cupomRepository.findCuponsByNomeAndFesta(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(cupomRepository.getNextValMySequence()).thenReturn(cupom.getCodCupom());
		
		Cupom retorno = cupomService.gerarCupom(cupomTO, 1);
		
		assertEquals(retorno.getCodCupom(), cupom.getCodCupom());
		assertEquals(retorno.getNomeCupom(), cupom.getNomeCupom());
		assertEquals(retorno.getFesta().getCodFesta(), cupom.getFesta().getCodFesta());
		assertEquals(retorno.getPrecoDesconto(), cupom.getPrecoDesconto());
	}
	
	@Test
	void gerarCupomDuplicadoException() throws Exception {
		Festa festa = festaTest();
		CupomTO cupomTO = gerarCupomTO();
		Cupom cupom = gerarCupom();
		
		Mockito.when(festaService.procurarFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(festa);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criarListaDeGrupo());
		Mockito.when(cupomRepository.findCuponsByNomeAndFesta(Mockito.anyString(), Mockito.anyInt())).thenReturn(gerarListDeCupons());
		Mockito.when(cupomRepository.getNextValMySequence()).thenReturn(cupom.getCodCupom());
		
		boolean erro = false;
		Cupom retorno;
		try {
			retorno = cupomService.gerarCupom(cupomTO, 1);
		}catch(ValidacaoException e){
			erro = true;
			retorno = null;
		};

		assertEquals(null, retorno);
		assertEquals(true, erro);
	}
	
	@Test
	void gerarCupomSucessoSize() throws Exception {
		Festa festa = festaTest();
		CupomTO cupomTO = gerarCupomTO();
		Cupom cupom = gerarCupom();
		List<Cupom> vazio = new ArrayList<>();
		
		Mockito.when(festaService.procurarFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(festa);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criarListaDeGrupo());
		Mockito.when(cupomRepository.findCuponsByNomeAndFesta(Mockito.anyString(), Mockito.anyInt())).thenReturn(vazio);
		Mockito.when(cupomRepository.getNextValMySequence()).thenReturn(cupom.getCodCupom());
		
		Cupom retorno = cupomService.gerarCupom(cupomTO, 1);

		assertEquals(retorno.getCodCupom(), cupom.getCodCupom());
		assertEquals(retorno.getNomeCupom(), cupom.getNomeCupom());
		assertEquals(retorno.getFesta().getCodFesta(), cupom.getFesta().getCodFesta());
		assertEquals(retorno.getPrecoDesconto(), cupom.getPrecoDesconto());
	}
	
	@Test
	void gerarCupomPermissaoUsuarioException() throws Exception {
		Festa festa = festaTest();
		CupomTO cupomTO = gerarCupomTO();
		Cupom cupom = gerarCupom();
		List<Grupo> vazio = new ArrayList<>();
		
		Mockito.when(festaService.procurarFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(festa);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(vazio);
		Mockito.when(cupomRepository.findCuponsByNomeAndFesta(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(cupomRepository.getNextValMySequence()).thenReturn(cupom.getCodCupom());
		
		boolean erro = false;
		Cupom retorno;
		try {
			retorno = cupomService.gerarCupom(cupomTO, 1);
		}catch(ValidacaoException e){
			erro = true;
			retorno = null;
		};

		assertEquals(null, retorno);
		assertEquals(true, erro);
	}
	
	
	@Test
	void atualizarCupomSucesso() throws Exception {
		CupomTO cupomTO = gerarCupomTO();
		Cupom cupom = gerarCupom();
		
		
		Mockito.when(cupomRepository.findCupomByCod(cupomTO.getCodCupom())).thenReturn(cupom);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criarListaDeGrupo());
		Mockito.when(cupomRepository.findCuponsByNomeAndFesta(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(cupomRepository.getNextValMySequence()).thenReturn(cupom.getCodCupom());
		
		Cupom retorno = cupomService.atualizarCupom(cupomTO, 1);
		
		assertEquals(retorno.getCodCupom(), cupom.getCodCupom());
		assertEquals(retorno.getNomeCupom(), cupom.getNomeCupom());
		assertEquals(retorno.getFesta().getCodFesta(), cupom.getFesta().getCodFesta());
		assertEquals(retorno.getPrecoDesconto(), cupom.getPrecoDesconto());
	}
	
	@Test
	void removeCupomSucesso() throws Exception {
		Cupom cupom = gerarCupom();
		
		Mockito.when(cupomRepository.findCupomByCod(Mockito.anyInt())).thenReturn(cupom);
		Mockito.when(grupoRepository.findGrupoPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(criarListaDeGrupo());

		Cupom retorno = cupomService.removeCupom(cupom.getCodCupom(), 1);
		
		assertEquals(retorno.getCodCupom(), cupom.getCodCupom());
		assertEquals(retorno.getNomeCupom(), cupom.getNomeCupom());
		assertEquals(retorno.getFesta().getCodFesta(), cupom.getFesta().getCodFesta());
		assertEquals(retorno.getPrecoDesconto(), cupom.getPrecoDesconto());
	}

}
