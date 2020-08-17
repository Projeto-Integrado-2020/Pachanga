package com.eventmanager.pachanga.controllers;

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
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.eventmanager.pachanga.domains.Convidado;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.ConvidadoFactory;
import com.eventmanager.pachanga.services.ConvidadoService;
import com.eventmanager.pachanga.services.GrupoService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=GrupoController.class)
public class GrupoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private GrupoService grupoService;
	
	@MockBean
	private ConvidadoService convidadoService;
	
	@MockBean
	private ConvidadoFactory convidadoFactory;
	
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
		grupo.setQuantMaxPessoas(quantMaxPessoas);
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
		usuarioTest.setSexo("M");
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
	public void getAllGruposFestaSucessoTest() throws Exception {
		String uri = "/grupo/getAllGruposFesta";
		
		List<Convidado> convidados = colecaoConvidadosTest();
		List<Grupo> grupos = colecaoGruposTest(4);
		List<Usuario> usuarios = colecaoUsuariosTest();
		
		Mockito.when(grupoService.procurarGruposPorFesta(Mockito.anyInt())).thenReturn(grupos);
		Mockito.when(grupoService.procurarUsuariosPorGrupo(Mockito.anyInt())).thenReturn(usuarios);
		Mockito.when(convidadoService.pegarConvidadosGrupo(Mockito.anyInt())).thenReturn(convidados);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1")
				.param("idUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		String expected = "[{\"codGrupo\":1,\"codFesta\":2,\"nomeGrupo\":\"GOGO-BOYS\",\"quantMaxPessoas\":0,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null},{\"codGrupo\":2,\"codFesta\":2,\"nomeGrupo\":\"Banda\",\"quantMaxPessoas\":0,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null},{\"codGrupo\":3,\"codFesta\":2,\"nomeGrupo\":\"SeuranÃ§a\",\"quantMaxPessoas\":0,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null},{\"codGrupo\":4,\"codFesta\":2,\"nomeGrupo\":\"STAF\",\"quantMaxPessoas\":0,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null}]";

	    //System.out.print("\033[H\033[2J");    --código de des cas o console não retorne o body
	    //System.out.flush();   
		//System.out.println("result-> " + result.getResponse().getContentAsString());
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	public void getAllGruposFestaExceptionTest() throws Exception {
		String uri = "/grupo/getAllGruposFesta";
		String expected = "erro";
		
		Mockito.when(grupoService.procurarGruposPorFesta(Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		Mockito.when(grupoService.procurarUsuariosPorGrupo(Mockito.anyInt())).thenThrow(new ValidacaoException(expected));
		Mockito.when(convidadoService.pegarConvidadosGrupo(Mockito.anyInt())).thenThrow(new ValidacaoException(expected));

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codFesta", "1")
				.param("idUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
//updateUser___________________________________________________________________________________________________________________________________________________________________________________
	/*
	@Test
	public void updateUserSucessoTest() throws Exception {
		String uri = "/grupo/updateUser";  
		String json = "{\"gruposId\": [1, 2, 3]}";
		
		Usuario usuario = usuarioTest();
		
		Mockito.when(grupoService.editUsuarioFesta(Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(usuario);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				//.param("gruposId", "1", "2", "3")
				.content(json)
				.param("grupoIdAtual", "1")
				.param("idUsuario", "1")
				.param("idUsuarioPermissao", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		String expected = "[{\"codGrupo\":1,\"codFesta\":2,\"nomeGrupo\":\"GOGO-BOYS\",\"quantMaxPessoas\":0,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null},{\"codGrupo\":2,\"codFesta\":2,\"nomeGrupo\":\"Banda\",\"quantMaxPessoas\":0,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null},{\"codGrupo\":3,\"codFesta\":2,\"nomeGrupo\":\"SeuranÃ§a\",\"quantMaxPessoas\":0,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null},{\"codGrupo\":4,\"codFesta\":2,\"nomeGrupo\":\"STAF\",\"quantMaxPessoas\":0,\"usuariosTO\":[{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":4,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD4@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null},{\"dtNasc\":\"3900-09-27T03:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Gustavo Barbosa\",\"tipConta\":\"P\",\"conta\":null,\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\":null,\"sexo\":\"M\",\"funcionalidade\":null}],\"convidadosTO\":[],\"permissoesTO\":null,\"permissoes\":null}]";
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}	
	*/
	
//addPermissaoGrupo_________________________________________________________________________________________________________________________________________________________________________________
	
	
}
