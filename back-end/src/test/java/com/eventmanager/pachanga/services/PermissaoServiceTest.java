package com.eventmanager.pachanga.services;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Permissao;
import com.eventmanager.pachanga.repositories.PermissaoRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value=PermissaoService.class)
public class PermissaoServiceTest {

	@MockBean
	private PermissaoRepository permissaoRepository;	
	
	@Autowired
	private PermissaoService permissaoService;
	
	public Permissao PermissaoTest(int id, String desc, String tipo) {
		Permissao permissao = new Permissao();
		permissao.setCodPermissao(id);
		permissao.setDescPermissao(desc);
		permissao.setTipPermissao(tipo);
		
		return permissao;
	}
	
	public List<Permissao> ColecaoDePermissaoTest() {
		List<Permissao> permissoes = new ArrayList<>();
		
		permissoes.add(PermissaoTest(1, "EDITDFES", "G"));
		permissoes.add(PermissaoTest(2, "CREGRPER", "G"));
		permissoes.add(PermissaoTest(3, "DELGRPER", "G"));
		permissoes.add(PermissaoTest(4, "EDIGRPER", "G"));
		permissoes.add(PermissaoTest(5, "ADDMEMBE", "G"));
		permissoes.add(PermissaoTest(6, "DELMEMBE", "G"));
		permissoes.add(PermissaoTest(7, "DISMEMBE", "G"));
		permissoes.add(PermissaoTest(8, "CADAESTO", "E"));
		permissoes.add(PermissaoTest(9, "DELEESTO", "E"));
		permissoes.add(PermissaoTest(10, "EDITESTO", "E"));
		permissoes.add(PermissaoTest(11, "CADMESTO", "E"));
		permissoes.add(PermissaoTest(12, "DELMESTO", "E"));
		permissoes.add(PermissaoTest(13, "EDIMESTO", "E"));
		permissoes.add(PermissaoTest(14, "ADDMESTO", "E"));
		permissoes.add(PermissaoTest(15, "BAIMESTO", "E"));
		permissoes.add(PermissaoTest(16, "DELEFEST", "G"));
		
		return permissoes;
	}
	
	@Test
	public void  getAllPermissaoSucessoTest() throws Exception {
		Mockito.when(permissaoRepository.findAllPermissao()).thenReturn(ColecaoDePermissaoTest());
		List<Permissao> permissoes = permissaoService.getAllPermissao();
	
		assertFalse(permissoes == null);
		assertEquals(permissoes.size(), 16);
	}
	
}
