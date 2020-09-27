package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Permissao;
import com.eventmanager.pachanga.dtos.PermissaoTO;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=PermissaoFactory.class)
class PermissaoFactoryTest {
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	private PermissaoTO permissaoTOTest() {
		PermissaoTO permissaoTO = new PermissaoTO();
		permissaoTO.setCodPermissao(1);
		permissaoTO.setDescPermissao("perm");
		permissaoTO.setTipPermissao("tanto faz");
		return permissaoTO;
	}
	
	private Permissao permissaoTest() {
		Permissao permissao = new Permissao();
		permissao.setCodPermissao(1);
		permissao.setDescPermissao("perm");
		permissao.setTipPermissao("tanto faz");
		return permissao;
	}
	
	@Test
	void getPermissaoSucessoTest() throws Exception {
		PermissaoTO permissaoTO = permissaoTOTest();
		
		Permissao permissao = PermissaoFactory.getPermissao(permissaoTO);
		
		assertEquals(permissao.getCodPermissao(), permissaoTO.getCodPermissao());
		assertEquals(permissao.getDescPermissao(), permissaoTO.getDescPermissao());
		assertEquals(permissao.getTipPermissao(), permissaoTO.getTipPermissao());
		//assertEquals(,);
	}
	
	@Test
	void getPermissaoTOSucessoTest() throws Exception {
		Permissao permissao = permissaoTest();
		
		PermissaoTO permissaoTO = PermissaoFactory.getPermissaoTO(permissao);
		
		assertEquals(permissao.getCodPermissao(), permissaoTO.getCodPermissao());
		assertEquals(permissao.getDescPermissao(), permissaoTO.getDescPermissao());
		assertEquals(permissao.getTipPermissao(), permissaoTO.getTipPermissao());
		//assertEquals(,);
	}
	
	@Test
	void getPermissoesTOSucessoTest() throws Exception {
		Permissao permissao = permissaoTest();
		List<Permissao> permissoes = new ArrayList<>();
		permissoes.add(permissao);
		
		List<PermissaoTO> permissoesTO = PermissaoFactory.getPermissoesTO(permissoes);
		PermissaoTO permissaoTO = permissoesTO.get(0);
		
		assertEquals(permissao.getCodPermissao(), permissaoTO.getCodPermissao());
		assertEquals(permissao.getDescPermissao(), permissaoTO.getDescPermissao());
		assertEquals(permissao.getTipPermissao(), permissaoTO.getTipPermissao());
		//assertEquals(,);
	}
	
	
}
