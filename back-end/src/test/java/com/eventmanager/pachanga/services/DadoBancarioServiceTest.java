package com.eventmanager.pachanga.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;

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
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.DadoBancarioTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.DadoBancarioFactory;
import com.eventmanager.pachanga.repositories.DadoBancarioRepository;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;

@RunWith(SpringRunner.class)
@WebMvcTest(value=DadoBancarioService.class)
class DadoBancarioServiceTest {
	
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
	private DadoBancarioFactory dadoBancarioFactory;

	@MockBean
	private FestaService festaService;

	@MockBean
	private GrupoService grupoService;
	
	@Autowired
	private DadoBancarioService dadoBancarioService;
	
	private DadoBancario dadoBancarioTest() {
		DadoBancario dado = new DadoBancario();
		dado.setCodAgencia(1);
		dado.setCodBanco("abc");
		dado.setCodConta(1);
		dado.setCodDadosBancario(1);
		dado.setFesta(new Festa());
		return dado;
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
	
	private DadoBancarioTO dadoBancarioTOTest() {
		DadoBancarioTO dado = new DadoBancarioTO();
		dado.setCodAgencia(1);
		dado.setCodBanco("abc");
		dado.setCodConta(1);
		dado.setCodDadosBancario(1);
		dado.setCodFesta(1);
		return dado;
	}
	
	@Test
	void dadoBancarioUnicoSucess() {
		
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(new Festa());
		Mockito.when(festaService.validarUsuarioFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new Usuario());
		Mockito.when(grupoService.validarPermissaoUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
		Mockito.when(dadoBancarioRepository.findDadosBancariosFesta(Mockito.anyInt())).thenReturn(dadoBancarioTest());
		
		DadoBancario dadoBancario = dadoBancarioService.dadoBancarioUnico(1, 2);
		
		assertEquals(true, dadoBancario != null);
	}
	
	@Test
	void adicionarDadoBancarioSucess() {
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(festaService.validarUsuarioFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new Usuario());
		Mockito.when(grupoService.validarPermissaoUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
		Mockito.when(dadoBancarioRepository.findDadosBancariosFesta(Mockito.anyInt())).thenReturn(null);
		Mockito.when(dadoBancarioFactory.getDadoBancario(Mockito.any(), Mockito.any())).thenReturn(dadoBancarioTest());
		Mockito.when(dadoBancarioRepository.getNextValMySequence()).thenReturn(1);
		
		DadoBancario dadoBancario = dadoBancarioService.adicionarDadoBancario(dadoBancarioTOTest(), 2);
		
		assertEquals(true, dadoBancario != null);
		assertEquals(true, dadoBancario.getCodDadosBancario() == 1);
		
	}
	
	@Test
	void adicionarDadoBancarioErroDadoBancarioExistente() {
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(festaService.validarUsuarioFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new Usuario());
		Mockito.when(grupoService.validarPermissaoUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
		Mockito.when(dadoBancarioRepository.findDadosBancariosFesta(Mockito.anyInt())).thenReturn(dadoBancarioTest());
		Mockito.when(dadoBancarioFactory.getDadoBancario(Mockito.any(), Mockito.any())).thenReturn(dadoBancarioTest());
		Mockito.when(dadoBancarioRepository.getNextValMySequence()).thenReturn(1);
		
		String mensagem = "";
		try {
			dadoBancarioService.adicionarDadoBancario(dadoBancarioTOTest(), 2);			
		} catch (ValidacaoException e) {
			mensagem = e.getMessage();
		}
		
		assertEquals(true, "DADBEXIS".equals(mensagem));
		
	}
	
	@Test
	void deleteDadoBancarioSucesso() {
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(grupoService.validarPermissaoUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
		Mockito.when(dadoBancarioRepository.findDadoByCodBancario(Mockito.anyInt())).thenReturn(dadoBancarioTest());
		Mockito.doNothing().when(dadoBancarioRepository).delete(Mockito.any());
		
		dadoBancarioService.deleteDadoBancario(dadoBancarioTOTest(), 1);
		
	}
	
	@Test
	void deleteDadoBancarioErroDadoBancarioNaoEncontrado() {
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(grupoService.validarPermissaoUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
		Mockito.when(dadoBancarioRepository.findDadoByCodBancario(Mockito.anyInt())).thenReturn(null);
		Mockito.doNothing().when(dadoBancarioRepository).delete(Mockito.any());
		
		String mensagem = "";
		try {
			dadoBancarioService.deleteDadoBancario(dadoBancarioTOTest(), 1);
		} catch (ValidacaoException e) {
			mensagem = e.getMessage();
		}
		
		assertEquals(true, "DADBNFOU".equals(mensagem));
	}
	
	@Test
	void atualizarDadoBancarioSucesso() {
		Mockito.when(festaService.validarFestaExistente(Mockito.anyInt())).thenReturn(festaTest());
		Mockito.when(grupoService.validarPermissaoUsuarioGrupo(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
		Mockito.when(dadoBancarioRepository.findDadoByCodBancario(Mockito.anyInt())).thenReturn(dadoBancarioTest());
		
		
		DadoBancario dadoBancario = dadoBancarioService.atualizarDadoBancario(dadoBancarioTOTest(), 1);
		
		assertEquals(true, dadoBancario != null);
	}

}
