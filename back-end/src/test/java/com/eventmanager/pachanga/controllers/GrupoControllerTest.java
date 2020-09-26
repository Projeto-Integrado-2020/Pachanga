package com.eventmanager.pachanga.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.eventmanager.pachanga.PachangaApplication;
import com.eventmanager.pachanga.domains.Convidado;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Permissao;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.GrupoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.ConvidadoFactory;
import com.eventmanager.pachanga.securingweb.OAuthHelper;
import com.eventmanager.pachanga.services.ConvidadoService;
import com.eventmanager.pachanga.services.GrupoService;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = PachangaApplication.class)
@AutoConfigureMockMvc
class GrupoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private GrupoService grupoService;
	
	@MockBean
	private ConvidadoService convidadoService;
	
	@MockBean
	private ConvidadoFactory convidadoFactory;
	
	@Autowired
	private OAuthHelper authHelper;
	
	public Convidado convidadoTest(int cod, String email) {
		Convidado convidado = new Convidado();
		convidado.setCodConvidado(cod);
		convidado.setEmail(email);
		
		return convidado;
	}
	
	public List<Convidado> colecaoConvidadosTest() {
		List<Convidado> convidados = new ArrayList<>();
		convidados.add(convidadoTest( 1, "teste1@teste.com"));
		convidados.add(convidadoTest( 2, "teste2@teste.com"));
		convidados.add(convidadoTest( 3, "teste3@teste.com"));
		convidados.add(convidadoTest( 4, "teste4@teste.com"));
		
		return convidados;
	}
	
	public Grupo grupoTest(int codGrupo, String nome, int quantMaxPessoas) throws Exception {
		Grupo grupo = new Grupo();
		grupo.setCodGrupo(codGrupo);
		grupo.setFesta(festaTest());
		grupo.setNomeGrupo(nome);
		grupo.setOrganizador(false);
		return grupo;
	}
	
	private Grupo grupoOrganizadorTest() {
		Grupo grupo = new Grupo();
		grupo.setCodGrupo(1);
		grupo.setNomeGrupo("ORGANIZADOR");
		Set<Usuario> usuarios = new HashSet<>();
		usuarios.add(usuarioTest());
		grupo.setUsuarios(usuarios);
		grupo.setOrganizador(true);
		return grupo;
	}
	
	public List<Grupo> colecaoGruposTest(int quantidade) throws Exception {
		List<Grupo> grupos = null;
		if(quantidade >= 1) {
			grupos = new ArrayList<>();  //preguiça de fazer algo otimizado em performance	
			grupos.add(grupoTest(1, "GOGO-BOYS", 3));
			if(quantidade >= 2) grupos.add(grupoTest(2, "Banda", 4));
			if(quantidade >= 3) grupos.add(grupoTest(3, "Seurança", 7));
			if(quantidade >= 4) grupos.add(grupoTest(4, "STAF", 12));
		}
		return grupos;

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
		festaTest.setStatusFesta("I");
		festaTest.setDescricaoFesta("Bugago");
		festaTest.setHorarioFimFestaReal(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		Set<Grupo> grupos = new HashSet<>();
		grupos.add(grupoOrganizadorTest());
		festaTest.setGrupos(grupos);
		return festaTest;
	}
	
	@SuppressWarnings("deprecation")
	private Usuario usuarioTest() {
		Usuario usuarioTest = new Usuario();
		usuarioTest.setCodUsuario(1);
		usuarioTest.setEmail("gustavinhoTPD@fodasse.com.br");
		usuarioTest.setSenha("1234");
		usuarioTest.setDtNasc(new Date(2000, 8, 27));
		usuarioTest.setGenero("M");
		usuarioTest.setNomeUser("Gustavo Barbosa");
		return usuarioTest;
	}
	

	private List<Usuario> colecaoUsuariosTest() {
		List<Usuario> usuarios = new ArrayList<>();
		
		Usuario usuario1 = usuarioTest();
		usuarios.add(usuario1);

		Usuario usuario2 = usuarioTest();
		usuario1.setCodUsuario(2);
		usuario1.setEmail("gustavinhoTPD2@fodasse.com.br");
		usuarios.add(usuario2);
		
		Usuario usuario3 = usuarioTest();
		usuario1.setCodUsuario(3);
		usuario1.setEmail("gustavinhoTPD3@fodasse.com.br");
		usuarios.add(usuario3);
		
		Usuario usuario4 = usuarioTest();
		usuario1.setCodUsuario(4);
		usuario1.setEmail("gustavinhoTPD4@fodasse.com.br");
		usuarios.add(usuario4);
		
		return usuarios;
		
	}
	
//getAllGruposFesta______________________________________________________________________________________________	

	@Test
	void getAllGruposFestaSucessoTest() throws Exception {
		String uri = "/grupo/getAllGruposFesta";
		
		List<Convidado> convidados = colecaoConvidadosTest();
		List<Grupo> grupos = colecaoGruposTest(4);
		List<Usuario> usuarios = colecaoUsuariosTest();
		
		Mockito.when(grupoService.procurarGruposPorFesta(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(grupoService.procurarUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.when(convidadoService.pegarConvidadosGrupo(Mockito.anyInt())).thenReturn(convidados);
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1")
				.param("idUsuario", "1")
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		String expected = "[{\"codGrupo\":1,\"codFesta\":2,\"nomeGrupo\":\"GOGO-BOYS\",\"quantMaxPessoas\":0,\"isOrganizador\":false,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T00:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T00:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T00:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T00:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null},{\"codGrupo\":2,\"codFesta\":2,\"nomeGrupo\":\"Banda\",\"quantMaxPessoas\":0,\"isOrganizador\":false,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T00:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T00:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T00:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T00:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null},{\"codGrupo\":3,\"codFesta\":2,\"nomeGrupo\":\"SeuranÃ§a\",\"quantMaxPessoas\":0,\"isOrganizador\":false,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T00:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T00:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T00:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T00:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null},{\"codGrupo\":4,\"codFesta\":2,\"nomeGrupo\":\"STAF\",\"quantMaxPessoas\":0,\"isOrganizador\":false,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T00:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T00:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T00:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T00:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null}]";
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	void getAllGruposFestaExceptionTest() throws Exception {
		String uri = "/grupo/getAllGruposFesta";
		String expected = "erro";
		
		Mockito.when(grupoService.procurarGruposPorFesta(Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		Mockito.when(grupoService.procurarUsuariosPorGrupo(Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		Mockito.when(convidadoService.pegarConvidadosGrupo(Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1")
				.param("idUsuario", "1")
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
//updateUser___________________________________________________________________________________________________________________________________________________________________________________
	
	
//addPermissaoGrupo_________________________________________________________________________________________________________________________________________________________________________________
	@Test
	void addPermissaoGrupoSucessoTest() throws Exception {
		String uri = "/grupo/addPermissaoGrupo";  
		Grupo grupo = grupoTest(2, "Banda", 4);
		
		Mockito.when(grupoService.validarGrupo(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoService.validarPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
		Mockito.doNothing().when(grupoService).addPermissaoGrupo(Mockito.anyInt(), Mockito.anyInt());
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("idGrupo", "1")
				.param("idPermissao", "1")
				.param("idUsuario", "1")
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		//String expected = "[{\"codGrupo\":1,\"codFesta\":2,\"nomeGrupo\":\"GOGO-BOYS\",\"quantMaxPessoas\":0,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null},{\"codGrupo\":2,\"codFesta\":2,\"nomeGrupo\":\"Banda\",\"quantMaxPessoas\":0,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null},{\"codGrupo\":3,\"codFesta\":2,\"nomeGrupo\":\"SeuranÃ§a\",\"quantMaxPessoas\":0,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null},{\"codGrupo\":4,\"codFesta\":2,\"nomeGrupo\":\"STAF\",\"quantMaxPessoas\":0,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null}]";
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}	
	
	@Test
	void addPermissaoGrupoExceptionTest() throws Exception {
		String uri = "/grupo/addPermissaoGrupo";  
		//Grupo grupo = grupoTest(2, "Banda", 4);
		String expected = "erro";
		
		Mockito.when(grupoService.validarGrupo(Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		Mockito.when(grupoService.validarPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
		Mockito.doNothing().when(grupoService).addPermissaoGrupo(Mockito.anyInt(), Mockito.anyInt());
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("idGrupo", "1")
				.param("idPermissao", "1")
				.param("idUsuario", "1")
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		//String expected = "[{\"codGrupo\":1,\"codFesta\":2,\"nomeGrupo\":\"GOGO-BOYS\",\"quantMaxPessoas\":0,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null},{\"codGrupo\":2,\"codFesta\":2,\"nomeGrupo\":\"Banda\",\"quantMaxPessoas\":0,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null},{\"codGrupo\":3,\"codFesta\":2,\"nomeGrupo\":\"SeuranÃ§a\",\"quantMaxPessoas\":0,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null},{\"codGrupo\":4,\"codFesta\":2,\"nomeGrupo\":\"STAF\",\"quantMaxPessoas\":0,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null}]";
		
		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
//removePermissaoGrupo_____________________________________________________________________________________
	@Test
	void removePermissaoGrupoSucessoTest() throws Exception {
		String uri = "/grupo/removePermissaoGrupo";  
		Grupo grupo = grupoTest(2, "Banda", 4);
		
		Mockito.when(grupoService.validarGrupo(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoService.validarPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
		Mockito.doNothing().when(grupoService).deletePermissaoGrupo(Mockito.anyInt(), Mockito.anyInt());
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("idGrupo", "1")
				.param("idPermissao", "1")
				.param("idUsuario", "1")
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		//String expected = "[{\"codGrupo\":1,\"codFesta\":2,\"nomeGrupo\":\"GOGO-BOYS\",\"quantMaxPessoas\":0,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null},{\"codGrupo\":2,\"codFesta\":2,\"nomeGrupo\":\"Banda\",\"quantMaxPessoas\":0,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null},{\"codGrupo\":3,\"codFesta\":2,\"nomeGrupo\":\"SeuranÃ§a\",\"quantMaxPessoas\":0,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null},{\"codGrupo\":4,\"codFesta\":2,\"nomeGrupo\":\"STAF\",\"quantMaxPessoas\":0,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null}]";
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	void removePermissaoGrupoExceptionTest() throws Exception {
		String uri = "/grupo/removePermissaoGrupo";  
		String expected = "erro";
		
		Mockito.when(grupoService.validarGrupo(Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		Mockito.when(grupoService.validarPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
		Mockito.doNothing().when(grupoService).deletePermissaoGrupo(Mockito.anyInt(), Mockito.anyInt());
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("idGrupo", "1")
				.param("idPermissao", "1")
				.param("idUsuario", "1")
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
///addGrupo_________________________________________________________________________________________________
	@Test
	void addGrupoSucessoTest() throws Exception {
		String uri = "/grupo/addGrupo";  
		Grupo grupo = grupoTest(2, "Banda", 4);
		
		String json = "{\"codGrupo\": 1,\"codFesta\": 2,\"nomeGrupo\": \"Batata\",\"quantMaxPessoas\":3,\"isOrganizador\": \"false\"}";
		
		Mockito.when(grupoService.addGrupoFesta(Mockito.any(GrupoTO.class), Mockito.anyInt())).thenReturn(grupo);
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("idUsuario", "1")
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		String expected = "{\"codGrupo\":2,\"codFesta\":2,\"nomeGrupo\":\"Banda\",\"quantMaxPessoas\":0,\"isOrganizador\":false,\"usuariosTO\":null,\"convidadosTO\":null,\"permissoesTO\":null,\"permissoes\":null}";
				
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	void addGrupoExceptionTest() throws Exception {
		String uri = "/grupo/addGrupo";  
		//Grupo grupo = grupoTest(2, "Banda", 4);
		String expected = "erro";
		String json = "{\"codGrupo\": 1,\"codFesta\": 2,\"nomeGrupo\": \"Batata\",\"quantMaxPessoas\":3,\"isOrganizador\": \"false\"}";
		
		Mockito.when(grupoService.addGrupoFesta(Mockito.any(GrupoTO.class), Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("idUsuario", "1")
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
				
		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
//deleteGrupo______________________________________________________________________________________________
	@Test
	void deleteGrupoSucessoTest() throws Exception {
		String uri = "/grupo/deleteGrupo";  
		Grupo grupo = grupoTest(2, "Banda", 4);
	
		Mockito.when(grupoService.deleteGrupo(Mockito.anyInt(), Mockito.anyInt())).thenReturn(grupo);
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codGrupo", "1")
				.param("idUsuario", "1")
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		String expected = "{\"codGrupo\":2,\"codFesta\":2,\"nomeGrupo\":\"Banda\",\"quantMaxPessoas\":0,\"isOrganizador\":false,\"usuariosTO\":null,\"convidadosTO\":null,\"permissoesTO\":null,\"permissoes\":null}";
				
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	void deleteGrupoExceptionTest() throws Exception {
		String uri = "/grupo/deleteGrupo";  
		//Grupo grupo = grupoTest(2, "Banda", 4);
		String expected = "erro";
		Mockito.when(grupoService.deleteGrupo(Mockito.anyInt(), Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codGrupo", "1")
				.param("idUsuario", "1")
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
				
		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
//updateGrupo______________________________________________________________________________________________
	@Test
	void updateGrupoSucessoTest() throws Exception {
		String uri = "/grupo/updateGrupo";  
		Grupo grupo = grupoTest(2, "Banda", 4);
		
		String json = "{\"codGrupo\": 1,\"codFesta\": 2,\"nomeGrupo\": \"Batata\",\"quantMaxPessoas\":3,\"isOrganizador\": \"false\"}";
		
		Mockito.when(grupoService.atualizar(Mockito.any(GrupoTO.class), Mockito.anyInt())).thenReturn(grupo);
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("idUsuario", "1")
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		String expected = "{\"codGrupo\":2,\"codFesta\":2,\"nomeGrupo\":\"Banda\",\"quantMaxPessoas\":0,\"isOrganizador\":false,\"usuariosTO\":null,\"convidadosTO\":null,\"permissoesTO\":null,\"permissoes\":null}";
				
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	void updateGrupoExceptionTest() throws Exception {
		String uri = "/grupo/updateGrupo";  
		//Grupo grupo = grupoTest(2, "Banda", 4);
		String expected = "erro";
		String json = "{\"codGrupo\": 1,\"codFesta\": 2,\"nomeGrupo\": \"Batata\",\"quantMaxPessoas\":3,\"isOrganizador\": \"false\"}";
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
		
		Mockito.when(grupoService.atualizar(Mockito.any(GrupoTO.class), Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("idUsuario", "1")
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
				
		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
//updateUser_______________________________________________________________________________________________
	@Test
	void updateUserSucessoTest() throws Exception {
		String uri = "/grupo/updateUser";  
		//String json = "{\"gruposId\": [1, 2, 3]}";
		String json = "[1, 2, 3]";
		Mockito.doNothing().when(grupoService).editUsuario(Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
	
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("grupoIdAtual", "1")
				.param("idUsuario", "1")
				.param("idUsuarioPermissao", "1")
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		//String expected = "{\"codGrupo\":2,\"codFesta\":2,\"nomeGrupo\":\"Banda\",\"quantMaxPessoas\":0,\"isOrganizador\":false,\"usuariosTO\":null,\"convidadosTO\":null,\"permissoesTO\":null,\"permissoes\":null}";
				
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		//assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	void updateUserExceptionTest() throws Exception {
		String uri = "/grupo/updateUser";  
		//Grupo grupo = grupoTest(2, "Banda", 4);
		String expected = "erro";
		String json = "[1, 2, 3]";
		
		doThrow(new ValidacaoException(expected)).when(grupoService).editUsuario(Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());                             
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("grupoIdAtual", "1")
				.param("idUsuario", "1")
				.param("idUsuarioPermissao", "1")
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
				
		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
//updateUsers______________________________________________________________________________________________
	@Test
	void updateUsersSucessoTest() throws Exception {
		String uri = "/grupo/updateUsers";  
		//String json = "{\"gruposId\": [1, 2, 3]}";
		String json = "[1, 2, 3]";
		Mockito.doNothing().when(grupoService).editUsuarios(Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt());
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
	
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("idGrupo", "1")
				.param("idUsuarioPermissao", "1")
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		//String expected = "{\"codGrupo\":2,\"codFesta\":2,\"nomeGrupo\":\"Banda\",\"quantMaxPessoas\":0,\"isOrganizador\":false,\"usuariosTO\":null,\"convidadosTO\":null,\"permissoesTO\":null,\"permissoes\":null}";
				
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		//assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	void updateUsersExceptionTest() throws Exception {
		String uri = "/grupo/updateUsers";  
		//Grupo grupo = grupoTest(2, "Banda", 4);
		String expected = "erro";
		String json = "[1, 2, 3]";
		
		doThrow(new ValidacaoException(expected)).when(grupoService).editUsuarios(Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt());           
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(json)
				.param("idGrupo", "1")
				.param("idUsuarioPermissao", "1")
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
				
		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
//deleteConvidado__________________________________________________________________________________________
	@Test
	void deleteConvidadoSucessoTest() throws Exception {
		String uri = "/grupo/deleteConvidado";  
		
		Mockito.doNothing().when(grupoService).deleteConvidado(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("idConvidado", "1")
				.param("idGrupo", "1")
				.param("idUsuarioPermissao", "1")
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
	
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		//assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	void deleteConvidadoExceptionTest() throws Exception {
		String uri = "/grupo/deleteConvidado";  
		String expected = "erro";
		
		doThrow(new ValidacaoException(expected)).when(grupoService).deleteConvidado(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());  
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("idConvidado", "1")
				.param("idGrupo", "1")
				.param("idUsuarioPermissao", "1")
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
				
		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
//deleteMembro____________________________________________________________________________________________
	@Test
	void deleteMembroSucessoTest() throws Exception {
		String uri = "/grupo/deleteMembro";  
		
		Mockito.doNothing().when(grupoService).deleteMembro(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("idMembro", "1")
				.param("idGrupo", "1")
				.param("idUsuarioPermissao", "1")
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
	
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		//assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	void deleteMembroExceptionTest() throws Exception {
		String uri = "/grupo/deleteMembro";  
		String expected = "erro";
		
		doThrow(new ValidacaoException(expected)).when(grupoService).deleteMembro(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt());            
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("idMembro", "1")
				.param("idGrupo", "1")
				.param("idUsuarioPermissao", "1")
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
				
		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}

//getGrupoFesta___________________________________________________________________________________________________________________________
	@Test
	void getGrupoFestaSucessTest() throws Exception {
		String uri = "/grupo/getGrupoFesta";  
		Grupo grupo = grupoTest(2, "Banda", 4);
		String expected = "{\"codGrupo\":2,\"codFesta\":2,\"nomeGrupo\":\"Banda\",\"quantMaxPessoas\":0,\"isOrganizador\":false,\"usuariosTO\":[],\"convidadosTO\":[],\"permissoesTO\":[],\"permissoes\":null}";
		List<Permissao> permissoes = new ArrayList<>();
		List<Usuario> usuarios = new ArrayList<>();
		
		Mockito.when(grupoService.validarGrupo(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoService.validarPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
		//grupoService.validarGrupo(Mockito.anyInt());
		Mockito.when(grupoService.procurarPermissoesPorGrupo(Mockito.anyInt())).thenReturn(permissoes);
		Mockito.when(grupoService.procurarUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.when(convidadoService.pegarConvidadosGrupo(Mockito.anyInt())).thenReturn(null);
		Mockito.when(convidadoFactory.getConvidadosTO(Mockito.anyList())).thenReturn(null);
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codGrupo", "1")
				.param("idUsuario", "1")
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
				
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	@Test
	void getGrupoFestaExceptionTest() throws Exception {
		String uri = "/grupo/getGrupoFesta";  
		Grupo grupo = grupoTest(2, "Banda", 4);
		String expected = "batata";
		List<Permissao> permissoes = new ArrayList<>();
		List<Usuario> usuarios = new ArrayList<>();
		
		Mockito.when(grupoService.validarGrupo(Mockito.anyInt())).thenReturn(grupo);
		Mockito.when(grupoService.validarPermissaoUsuario(Mockito.anyInt(), Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		//grupoService.validarGrupo(Mockito.anyInt());
		Mockito.when(grupoService.procurarPermissoesPorGrupo(Mockito.anyInt())).thenReturn(permissoes);
		Mockito.when(grupoService.procurarUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.when(convidadoService.pegarConvidadosGrupo(Mockito.anyInt())).thenReturn(null);
		Mockito.when(convidadoFactory.getConvidadosTO(Mockito.anyList())).thenReturn(null);
		
		RequestPostProcessor bearerToken = authHelper.addBearerToken("pachanga", "ROLE_USER");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codGrupo", "1")
				.param("idUsuario", "1")
				.with(bearerToken)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
				
		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
}
