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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.CategoriaTO;
import com.eventmanager.pachanga.dtos.ConvidadoTO;
import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.dtos.UsuarioTO;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.eventmanager.pachanga.tipo.TipoStatusFesta;

@RunWith(SpringRunner.class)
@WebMvcTest(value=FestaFactory.class)
class FestaFactoryTest {
	@Autowired
	private FestaFactory festaFactory;
	
	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;
	
	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;
	
	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;
	
	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;

	public Festa festaTest() throws Exception {
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

	public FestaTO festaTOTest() throws Exception {
		FestaTO festaTOTest = new FestaTO();
		festaTOTest.setCodFesta(2);
		festaTOTest.setCodEnderecoFesta("https//:minhacasa.org");
		festaTOTest.setDescOrganizador("sou demente");
		festaTOTest.setHorarioInicioFesta(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		festaTOTest.setHorarioFimFesta(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		festaTOTest.setNomeFesta("festao");
		festaTOTest.setOrganizador("Joao Neves");
		festaTOTest.setStatusFesta(TipoStatusFesta.PREPARACAO.getValor());
		festaTOTest.setDescricaoFesta("Bugago");
		festaTOTest.setHorarioFimFestaReal(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));

		return festaTOTest;
	}

	private CategoriaTO categoriaTOTest() {
		CategoriaTO categoriaTO = new CategoriaTO();
		categoriaTO.setCodCategoria(1);
		categoriaTO.setNomeCategoria("Categoria");
		return categoriaTO;
	}

	@SuppressWarnings("deprecation")
	public static UsuarioTO usuarioTOTest() throws Exception {
		UsuarioTO usuarioTOTest = new UsuarioTO();

		usuarioTOTest.setCodUsuario(100);
		usuarioTOTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTOTest.setSenha(
				"fc68b677646b5f018d1762e9a19bf65180d9aab2794794340ade50e0d78a239affd43a613e7136a61b5d63b09f072c0c039dea4281873abe826d6e6285d9cefef0a0d868d3b0b0d4582ec787b473b4e0");
		usuarioTOTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTOTest.setGenero("M");
		usuarioTOTest.setNomeUser("Gustavo Barbosa");

		return usuarioTOTest;
	}

	@SuppressWarnings("deprecation")
	public static Usuario usuarioTest() throws Exception {
		Usuario usuarioTest = new Usuario();

		usuarioTest.setCodUsuario(100);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha(
				"fc68b677646b5f018d1762e9a19bf65180d9aab2794794340ade50e0d78a239affd43a613e7136a61b5d63b09f072c0c039dea4281873abe826d6e6285d9cefef0a0d868d3b0b0d4582ec787b473b4e0");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setGenero("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");

		return usuarioTest;
	}

	private ConvidadoTO convidadoTOTest() {
		ConvidadoTO convidadoTO = new ConvidadoTO();
		convidadoTO.setCodConvidado(1);
		convidadoTO.setEmail("test@email.com");
		return convidadoTO;
	}

	public Grupo grupoTest() throws Exception {
		Grupo grupo = new Grupo();
		grupo.setCodGrupo(1);
		grupo.setNomeGrupo("CONVIDADO");
		grupo.setFesta(festaTest());
		grupo.setOrganizador(false);
		Set<Usuario> usuarios = new HashSet<>();
		usuarios.add(usuarioTest());
		grupo.setUsuarios(usuarios);
		return grupo;
	}

	@Test
	void getFestaSucessoTest() throws Exception {
		FestaTO festaTO = festaTOTest();

		Festa festa = festaFactory.getFesta(festaTO, null);

		assertEquals(festa.getCodEnderecoFesta(), festaTO.getCodEnderecoFesta());
		assertEquals(festa.getCodFesta(), festaTO.getCodFesta());
		assertEquals(festa.getDescOrganizador(), festaTO.getDescOrganizador());
		assertEquals(festa.getDescricaoFesta(), festaTO.getDescricaoFesta());
		assertEquals(festa.getHorarioFimFesta(), festaTO.getHorarioFimFesta());
		assertEquals(festa.getHorarioInicioFesta(), festaTO.getHorarioInicioFesta());
		assertEquals(festa.getNomeFesta(), festaTO.getNomeFesta());
		assertEquals(festa.getOrganizador(), festaTO.getOrganizador());
		assertEquals(festa.getStatusFesta(), festaTO.getStatusFesta());
	}

	@Test
	void getFestaTOSucessoTest() throws Exception {
		Festa festa = festaTest();
		List<UsuarioTO> usuariosTO = new ArrayList<>();
		usuariosTO.add(usuarioTOTest());
		CategoriaTO categoriaTO1 = categoriaTOTest();
		CategoriaTO categoriaTO2 = categoriaTOTest();
		categoriaTO2.setCodCategoria(2);
		ConvidadoTO convidadoTO = convidadoTOTest();
		List<ConvidadoTO> convidadosTO = new ArrayList<>();
		convidadosTO.add(convidadoTO);

		FestaTO festaTO = festaFactory.getFestaTO(festa, usuariosTO, false, categoriaTO1, categoriaTO2, convidadosTO);

		assertEquals(festa.getCodEnderecoFesta(), festaTO.getCodEnderecoFesta());
		assertEquals(festa.getCodFesta(), festaTO.getCodFesta());
		assertEquals(festa.getDescOrganizador(), festaTO.getDescOrganizador());
		assertEquals(festa.getDescricaoFesta(), festaTO.getDescricaoFesta());
		assertEquals(festa.getHorarioFimFesta(), festaTO.getHorarioFimFesta());
		assertEquals(festa.getHorarioInicioFesta(), festaTO.getHorarioInicioFesta());
		assertEquals(festa.getNomeFesta(), festaTO.getNomeFesta());
		assertEquals(festa.getOrganizador(), festaTO.getOrganizador());
		assertEquals(festa.getStatusFesta(), festaTO.getStatusFesta());
	}

	@Test
	void getFestaTOSucesso2Test() throws Exception {
		Festa festa = festaTest();
		List<UsuarioTO> usuariosTO = new ArrayList<>();
		usuariosTO.add(usuarioTOTest());
		CategoriaTO categoriaTO1 = categoriaTOTest();
		CategoriaTO categoriaTO2 = categoriaTOTest();
		categoriaTO2.setCodCategoria(2);
		ConvidadoTO convidadoTO = convidadoTOTest();
		List<ConvidadoTO> convidadosTO = new ArrayList<>();
		convidadosTO.add(convidadoTO);

		FestaTO festaTO = festaFactory.getFestaTO(festa, usuariosTO, true, categoriaTO1, categoriaTO2, convidadosTO);

		assertEquals(festa.getCodEnderecoFesta(), festaTO.getCodEnderecoFesta());
		assertEquals(festa.getCodFesta(), festaTO.getCodFesta());
		assertEquals(festa.getDescOrganizador(), festaTO.getDescOrganizador());
		assertEquals(festa.getDescricaoFesta(), festaTO.getDescricaoFesta());
		assertEquals(festa.getHorarioFimFesta(), festaTO.getHorarioFimFesta());
		assertEquals(festa.getHorarioInicioFesta(), festaTO.getHorarioInicioFesta());
		assertEquals(festa.getNomeFesta(), festaTO.getNomeFesta());
		assertEquals(festa.getOrganizador(), festaTO.getOrganizador());
		assertEquals(festa.getStatusFesta(), festaTO.getStatusFesta());
	}

	@Test
	void getFestaTOSucessoUsuarioNullTest() throws Exception {
		Festa festa = festaTest();
		List<UsuarioTO> usuariosTO = new ArrayList<>();
		usuariosTO.add(usuarioTOTest());
		CategoriaTO categoriaTO1 = categoriaTOTest();
		CategoriaTO categoriaTO2 = categoriaTOTest();
		categoriaTO2.setCodCategoria(2);
		ConvidadoTO convidadoTO = convidadoTOTest();
		List<ConvidadoTO> convidadosTO = new ArrayList<>();
		convidadosTO.add(convidadoTO);

		usuariosTO = null;

		FestaTO festaTO = festaFactory.getFestaTO(festa, usuariosTO, true, categoriaTO1, categoriaTO2, convidadosTO);

		assertEquals(festa.getCodEnderecoFesta(), festaTO.getCodEnderecoFesta());
		assertEquals(festa.getCodFesta(), festaTO.getCodFesta());
		assertEquals(festa.getDescOrganizador(), festaTO.getDescOrganizador());
		assertEquals(festa.getDescricaoFesta(), festaTO.getDescricaoFesta());
		assertEquals(festa.getHorarioFimFesta(), festaTO.getHorarioFimFesta());
		assertEquals(festa.getHorarioInicioFesta(), festaTO.getHorarioInicioFesta());
		assertEquals(festa.getNomeFesta(), festaTO.getNomeFesta());
		assertEquals(festa.getOrganizador(), festaTO.getOrganizador());
		assertEquals(festa.getStatusFesta(), festaTO.getStatusFesta());
	}

	@Test
	void getFestaTOSucessoAlterTest() throws Exception {
		Set<Grupo> grupos = new HashSet<>();
		grupos.add(grupoTest());
		Festa festa = festaTest();
		festa.setGrupos(grupos);
		List<UsuarioTO> usuariosTO = new ArrayList<>();
		usuariosTO.add(usuarioTOTest());
		CategoriaTO categoriaTO1 = categoriaTOTest();
		CategoriaTO categoriaTO2 = categoriaTOTest();
		categoriaTO2.setCodCategoria(2);
		ConvidadoTO convidadoTO = convidadoTOTest();
		List<ConvidadoTO> convidadosTO = new ArrayList<>();
		convidadosTO.add(convidadoTO);

		FestaTO festaTO = festaFactory.getFestaTO(festa, usuariosTO, false, categoriaTO1, categoriaTO2, convidadosTO,
				2);

		assertEquals(festa.getCodEnderecoFesta(), festaTO.getCodEnderecoFesta());
		assertEquals(festa.getCodFesta(), festaTO.getCodFesta());
		assertEquals(festa.getDescOrganizador(), festaTO.getDescOrganizador());
		assertEquals(festa.getDescricaoFesta(), festaTO.getDescricaoFesta());
		assertEquals(festa.getHorarioFimFesta(), festaTO.getHorarioFimFesta());
		assertEquals(festa.getHorarioInicioFesta(), festaTO.getHorarioInicioFesta());
		assertEquals(festa.getNomeFesta(), festaTO.getNomeFesta());
		assertEquals(festa.getOrganizador(), festaTO.getOrganizador());
		assertEquals(festa.getStatusFesta(), festaTO.getStatusFesta());
	}

}
