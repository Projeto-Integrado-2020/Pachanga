package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.PachangaApplication;
import com.eventmanager.pachanga.domains.Permissao;
import com.eventmanager.pachanga.dtos.PermissaoTO;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = PachangaApplication.class)
@AutoConfigureMockMvc
class PermissaoFactoryTest {
	
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
