package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Convidado;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Permissao;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.CategoriaTO;
import com.eventmanager.pachanga.dtos.ConvidadoTO;
import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.dtos.GrupoTO;
import com.eventmanager.pachanga.dtos.PermissaoTO;
import com.eventmanager.pachanga.dtos.UsuarioTO;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;

@RunWith(SpringRunner.class)
@WebMvcTest(value=GrupoFactory.class)
public class GrupoFactoryTest {
	
	//@Autowired
	//private GrupoFactory grupoFactory;

	public GrupoTO grupoTOTest() throws Exception {
		GrupoTO grupoTO = new GrupoTO();
		grupoTO.setCodGrupo(1);
		grupoTO.setNomeGrupo("CONVIDADO");
		grupoTO.setCodFesta(1);
		grupoTO.setIsOrganizador(false);
		grupoTO.setQuantMaxPessoas(15);
		return grupoTO;
	}
	
	public Grupo grupoTest() throws Exception {
		Grupo grupo = new Grupo();
		grupo.setCodGrupo(1);
		grupo.setNomeGrupo("CONVIDADO");
		grupo.setFesta(festaTest());
		grupo.setOrganizador(false);
		grupo.setQuantMaxPessoas(15);
		return grupo;
	}
	
	public Festa festaTest() throws Exception{
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
	public static UsuarioTO usuarioTOTest() throws Exception{
		UsuarioTO usuarioTOTest = new UsuarioTO();

		usuarioTOTest.setCodUsuario(100);
		usuarioTOTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTOTest.setSenha("fc68b677646b5f018d1762e9a19bf65180d9aab2794794340ade50e0d78a239affd43a613e7136a61b5d63b09f072c0c039dea4281873abe826d6e6285d9cefef0a0d868d3b0b0d4582ec787b473b4e0");
		usuarioTOTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTOTest.setSexo("M");
		usuarioTOTest.setNomeUser("Gustavo Barbosa");

		return usuarioTOTest;
	}
	
	private ConvidadoTO convidadoTOTest() {
		ConvidadoTO convidadoTO = new ConvidadoTO();
		convidadoTO.setCodConvidado(1);
		convidadoTO.setEmail("test@email.com");
		return convidadoTO;
	}
	
	private PermissaoTO permissaoTOTest() {
		PermissaoTO permissaoTO = new PermissaoTO();
		permissaoTO.setCodPermissao(1);
		permissaoTO.setDescPermissao("perm");
		permissaoTO.setTipPermissao("tanto faz");
		return permissaoTO;
	}
	@Test
	public void getGrupoTOSucessoTest() throws Exception {
		Grupo grupo = grupoTest();
		GrupoTO grupoTO = GrupoFactory.getGrupoTO(grupo);
		
		assertEquals(grupo.getCodGrupo(),grupoTO.getCodGrupo());
		assertEquals(grupo.getFesta().getCodFesta(),grupoTO.getCodFesta());
		assertEquals(grupo.getNomeGrupo(),grupoTO.getNomeGrupo());
		assertEquals(grupo.getOrganizador(),grupoTO.getIsOrganizador());
		//assertEquals(grupo.getQuantMaxPessoas(),grupoTO.getQuantMaxPessoas());
		//assertEquals(,);
	}
	
	@Test
	public void getGrupoSucessoTest() throws Exception {
		Grupo grupo = grupoTest();
		String nomeGrupo = grupo.getNomeGrupo();
		int codGrupo = grupo.getCodGrupo();
		Festa festa = grupo.getFesta();
		boolean organizador = grupo.getOrganizador();
		
		Grupo retorno = GrupoFactory.getGrupo(nomeGrupo, codGrupo, festa, organizador);
		
		assertEquals(grupo.getCodGrupo(),retorno.getCodGrupo());
		assertEquals(grupo.getFesta().getCodFesta(),retorno.getFesta().getCodFesta());
		assertEquals(grupo.getNomeGrupo(),retorno.getNomeGrupo());
		assertEquals(grupo.getOrganizador(),retorno.getOrganizador());
		//assertEquals(grupo.getQuantMaxPessoas(),grupoTO.getQuantMaxPessoas());
		//assertEquals(,);
	}
	
	@Test
	public void getGrupoTOAlterSucessoTest() throws Exception {
		Grupo grupo = grupoTest();
		
		List<UsuarioTO> usuariosTO = new ArrayList<>();
		usuariosTO.add(usuarioTOTest());
	
		List<ConvidadoTO> convidadosTO = new ArrayList<>();
		convidadosTO.add(convidadoTOTest());
		
		List<PermissaoTO> permissoesTO = new ArrayList<>();
		permissoesTO.add(permissaoTOTest());
		
		GrupoTO grupoTO = GrupoFactory.getGrupoTO(grupo, usuariosTO, convidadosTO, permissoesTO);
		
		assertEquals(grupo.getCodGrupo(),grupoTO.getCodGrupo());
		assertEquals(grupo.getFesta().getCodFesta(),grupoTO.getCodFesta());
		assertEquals(grupo.getNomeGrupo(),grupoTO.getNomeGrupo());
		assertEquals(grupo.getOrganizador(),grupoTO.getIsOrganizador());
		//assertEquals(grupo.getQuantMaxPessoas(),grupoTO.getQuantMaxPessoas());
		//assertEquals(,);
	}
	
}