package com.eventmanager.pachanga.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
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

import com.eventmanager.pachanga.domains.Categoria;
import com.eventmanager.pachanga.domains.Convidado;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.ConviteFestaTO;
import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.ConvidadoFactory;
import com.eventmanager.pachanga.factory.FestaFactory;
import com.eventmanager.pachanga.services.CategoriaService;
import com.eventmanager.pachanga.services.ConvidadoService;
import com.eventmanager.pachanga.services.FestaService;
import com.eventmanager.pachanga.services.UsuarioService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=FestaController.class)
class FestaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FestaService festaService;

	@MockBean
	private UsuarioService userService;

	@MockBean
	private CategoriaService categoriaService;

	@MockBean
	private ConvidadoService convidadoService;

	@MockBean
	private ConvidadoFactory convidadoFactory;
	
	@MockBean
	private FestaFactory festaFactory;
	
	//metodos auxiliares___________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________	
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
		grupos.add(grupoUsuarioTest());
		festaTest.setGrupos(grupos);
		return festaTest;
	}

	private Grupo grupoUsuarioTest() {
		Grupo grupo = new Grupo();
		grupo.setCodGrupo(1);
		grupo.setNomeGrupo("ORGANIZADOR");
		Set<Usuario> usuarios = new HashSet<>();
		usuarios.add(usuarioTest());
		grupo.setUsuarios(usuarios);
		grupo.setOrganizador(true);
		return grupo;
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

	private Categoria categoriaTest() {
		Categoria categoria = new Categoria();
		categoria.setCodCategoria(1);
		categoria.setNomeCategoria("teste");
		return categoria;
	}
	
	private FestaTO festaToTest() {
		FestaTO festaTo = new FestaTO();
		festaTo.setCodEnderecoFesta("https//:minhacasa.org");
		festaTo.setDescOrganizador("sou demente");
		festaTo.setHorarioInicioFesta(LocalDateTime.of(2016, Month.JUNE, 22, 19, 10));
		festaTo.setHorarioFimFesta(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		festaTo.setNomeFesta("festao");
		festaTo.setOrganizador("Joao Neves");
		festaTo.setStatusFesta("I");
		festaTo.setDescricaoFesta("Bugago");
		festaTo.setHorarioFimFestaReal(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		return festaTo;
	}

	private Convidado convidadoTest() {
		Convidado convidado = new Convidado();
		convidado.setCodConvidado(1);
		convidado.setEmail("teste@teste.com");
		return convidado;
	}
	
	public List<Usuario> colecaoDeUsuario(int quantidadeUsuarios) throws Exception {
		List<Usuario> usuarios = null;
		
		if(quantidadeUsuarios >= 1) {		
			usuarios = new ArrayList<Usuario>();
			
			Usuario usuarioTest1 = usuarioTest();
			usuarioTest1.setCodUsuario(1);
			usuarioTest1.setNomeUser("Andrey");
	
			usuarios.add(usuarioTest1);
		 
			if(quantidadeUsuarios >= 2) {	
				Usuario usuarioTest2 = usuarioTest();
				usuarioTest2.setCodUsuario(2);
				usuarioTest2.setNomeUser("Luis");
		
				usuarios.add(usuarioTest2);
			} 
		}
		
		return usuarios;
	}



	//Adicionar_____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________	
	@Test
	void adicionarFestaSucessoTest() throws Exception {
		String festaJson = "{\"codFesta\":\"2\",\"nomeFesta\":\"festao\",\"statusFesta\":\"I\",\"organizador\":\"Joao Neves\",\"horarioInicioFesta\":\"2016-06-22T19:10:00\",\"horarioFimFesta\":\"2016-06-23T19:10:00\",\"descricaoFesta\":\"Bugago\",\"codEnderecoFesta\":\"https//:minhacasa.org\",\"descOrganizador\":\"sou demente\"}";

		Festa festaTest = festaTest();
		
		String uri = "/festa/adicionar";

		Mockito.when(festaService.addFesta(Mockito.any(FestaTO.class), Mockito.any(Integer.class))).thenReturn(festaTest);
		Mockito.when(categoriaService.procurarCategoriaFesta(Mockito.anyInt(), Mockito.anyString())).thenReturn(categoriaTest(), null);
		Mockito.when(festaFactory.getFestaTO(Mockito.any(), Mockito.isNull(), Mockito.anyBoolean(), Mockito.any(), Mockito.any(), Mockito.isNull())).thenReturn(festaToTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(festaJson)
				.param("idUser", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		String expected = "{\"codFesta\":0,\"nomeFesta\":\"festao\",\"statusFesta\":\"I\",\"organizador\":\"Joao Neves\",\"horarioInicioFesta\":\"2016-06-22T19:10:00\",\"horarioFimFesta\":\"2016-06-23T19:10:00\",\"descricaoFesta\":\"Bugago\",\"codEnderecoFesta\":\"https//:minhacasa.org\",\"descOrganizador\":\"sou demente\",\"horarioFimFestaReal\":\"2016-06-23T19:10:00\",\"funcionalidade\":null,\"quantidadeParticipantes\":0,\"usuarios\":null,\"codPrimaria\":0,\"codSecundaria\":0,\"categoriaPrimaria\":null,\"categoriaSecundaria\":null,\"isOrganizador\":null,\"convidados\":null}";

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}

	@Test
	void adicionarFestaExceptionTest() throws Exception {
		String festaJson = "{\"codFesta\":\"2\",\"nomeFesta\":\"festao\",\"statusFesta\":\"I\",\"organizador\":\"Joao Neves\",\"horarioInicioFesta\":\"2016-06-22T19:10:00\",\"horarioFimFesta\":\"2016-06-23T19:10:00\",\"descricaoFesta\":\"Bugago\",\"codEnderecoFesta\":\"https//:minhacasa.org\",\"descOrganizador\":\"sou demente\"}";

		String uri = "/festa/adicionar";

		Mockito.when(festaService.addFesta(Mockito.any(FestaTO.class), Mockito.any(Integer.class))).thenThrow(new ValidacaoException("addFesta"));
		Mockito.when(categoriaService.procurarCategoriaFesta(Mockito.anyInt(), Mockito.anyString())).thenReturn(categoriaTest(), null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(festaJson)
				.param("idUser", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		String expected = "addFesta";

		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}

	//listar_________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________


	@Test
	void listarFestaSucessoTest() throws Exception {
		List<Festa> festas = new ArrayList<>();
		festas.add(festaTest());
		List<Convidado> convidados = new ArrayList<Convidado>();
		convidados.add(convidadoTest());

		Mockito.when(festaService.procurarFestasPorUsuario(Mockito.any(Integer.class))).thenReturn(festas);
		Mockito.when(categoriaService.procurarCategoriaFesta(Mockito.anyInt(), Mockito.anyString())).thenReturn(categoriaTest(), null);
		Mockito.when(convidadoService.pegarConvidadosFesta(Mockito.anyInt())).thenReturn(convidados);
		Mockito.when(festaFactory.getFestaTO(Mockito.any(), Mockito.anyList(), Mockito.anyBoolean(), Mockito.any(), Mockito.any(), Mockito.anyList(), Mockito.anyInt())).thenReturn(festaToTest());

		String uri = "/festa/lista";
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("idUser", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		String expected = "[{\"codFesta\":0,\"nomeFesta\":\"festao\",\"statusFesta\":\"I\",\"organizador\":\"Joao Neves\",\"horarioInicioFesta\":\"2016-06-22T19:10:00\",\"horarioFimFesta\":\"2016-06-23T19:10:00\",\"descricaoFesta\":\"Bugago\",\"codEnderecoFesta\":\"https//:minhacasa.org\",\"descOrganizador\":\"sou demente\",\"horarioFimFestaReal\":\"2016-06-23T19:10:00\",\"funcionalidade\":null,\"quantidadeParticipantes\":0,\"usuarios\":null,\"codPrimaria\":0,\"codSecundaria\":0,\"categoriaPrimaria\":null,\"categoriaSecundaria\":null,\"isOrganizador\":null,\"convidados\":null}]";

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	void listarFestaUsuarioZeroTest() throws Exception {
		List<Festa> festas = new ArrayList<>();
		festas.add(festaTest());
		List<Convidado> convidados = new ArrayList<Convidado>();
		convidados.add(convidadoTest());

		Mockito.when(festaService.procurarFestas()).thenReturn(festas);
		Mockito.when(categoriaService.procurarCategoriaFesta(Mockito.anyInt(), Mockito.anyString())).thenReturn(categoriaTest(), null);
		Mockito.when(convidadoService.pegarConvidadosFesta(Mockito.anyInt())).thenReturn(convidados);
		Mockito.when(userService.getUsuariosFesta(Mockito.any(Integer.class))).thenReturn(colecaoDeUsuario(2));

		String uri = "/festa/lista";
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("idUser", "0")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		String expected = "[null]";

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}

	@Test
	void listarFestaProcurarFestasPorUsuarioExceptionTest() throws Exception {

		Mockito.when(festaService.procurarFestasPorUsuario(Mockito.any(Integer.class))).thenThrow(new ValidacaoException("procurarFestasPorUsuario"));
		Mockito.when(festaService.procurarFestas()).thenThrow(new ValidacaoException("procurarFestas"));
		Mockito.when(categoriaService.procurarCategoriaFesta(Mockito.anyInt(), Mockito.anyString())).thenReturn(categoriaTest(), null);

		String uri = "/festa/lista";

		//problema com o festaService.procurarFestasPorUsuario
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("idUser", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		String expected = "procurarFestasPorUsuario";

		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());

	}

	@Test
	void listarFestaProcurarFestasExceptionTest() throws Exception {

		Mockito.when(festaService.procurarFestasPorUsuario(Mockito.any(Integer.class))).thenThrow(new ValidacaoException("procurarFestasPorUsuario"));
		Mockito.when(festaService.procurarFestas()).thenThrow(new ValidacaoException("procurarFestas"));
		Mockito.when(categoriaService.procurarCategoriaFesta(Mockito.anyInt(), Mockito.anyString())).thenReturn(categoriaTest(), null);

		String uri = "/festa/lista";

		//problema com o festaService.procurarFestasPorUsuario
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(400, response.getStatus());

	}

	//deletar_____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________	


	@Test
	void deletarFestaSucessoTest() throws Exception {
		String uri = "/festa/delete";	

		doNothing().when(festaService).deleteFesta(Mockito.any(Integer.class), Mockito.any(Integer.class));

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("idFesta", "2")
				.param("idUser", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}


	@Test
	void deletarFestaExceptionTest() throws Exception {
		String uri = "/festa/delete";

		String expected = "deleteException";

		doThrow(new ValidacaoException(expected)).when(festaService).deleteFesta(Mockito.any(Integer.class), Mockito.any(Integer.class));


		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("idFesta", "2")
				.param("idUser", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());

	}

	//atualizar________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________	

	@Test
	void atualizarFestaSucessoTest() throws Exception {
		String festaJson = "{\"codFesta\":\"2\",\"nomeFesta\":\"festao\",\"statusFesta\":\"I\",\"organizador\":\"Joao Neves\",\"horarioInicioFesta\":\"2016-06-22T19:10:00\",\"horarioFimFesta\":\"2016-06-23T19:10:00\",\"descricaoFesta\":\"Bugago\",\"codEnderecoFesta\":\"https//:minhacasa.org\",\"descOrganizador\":\"sou demente\"}";

		Festa festaTest = festaTest();

		String uri = "/festa/atualizar";

		Mockito.when(festaService.updateFesta(Mockito.any(FestaTO.class), Mockito.any(Integer.class))).thenReturn(festaTest);
		Mockito.when(categoriaService.procurarCategoriaFesta(Mockito.anyInt(), Mockito.anyString())).thenReturn(categoriaTest(), null);
		Mockito.when(festaFactory.getFestaTO(Mockito.any(), Mockito.isNull(), Mockito.anyBoolean(), Mockito.any(), Mockito.any(), Mockito.isNull())).thenReturn(festaToTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(festaJson)
				.param("idUser", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		String expected = "{\"codFesta\":0,\"nomeFesta\":\"festao\",\"statusFesta\":\"I\",\"organizador\":\"Joao Neves\",\"horarioInicioFesta\":\"2016-06-22T19:10:00\",\"horarioFimFesta\":\"2016-06-23T19:10:00\",\"descricaoFesta\":\"Bugago\",\"codEnderecoFesta\":\"https//:minhacasa.org\",\"descOrganizador\":\"sou demente\",\"horarioFimFestaReal\":\"2016-06-23T19:10:00\",\"funcionalidade\":null,\"quantidadeParticipantes\":0,\"usuarios\":null,\"codPrimaria\":0,\"codSecundaria\":0,\"categoriaPrimaria\":null,\"categoriaSecundaria\":null,\"isOrganizador\":null,\"convidados\":null}";

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);

	}

	@Test
	void atualizarFestaExceptionTest() throws Exception {
		String festaJson = "{\"codFesta\":\"2\",\"nomeFesta\":\"festao\",\"statusFesta\":\"I\",\"organizador\":\"Joao Neves\",\"horarioInicioFesta\":\"2016-06-22T19:10:00\",\"horarioFimFesta\":\"2016-06-23T19:10:00\",\"descricaoFesta\":\"Bugago\",\"codEnderecoFesta\":\"https//:minhacasa.org\",\"descOrganizador\":\"sou demente\"}";

		String expected = "updateFesta";

		String uri = "/festa/atualizar";

		Mockito.when(festaService.updateFesta(Mockito.any(FestaTO.class), Mockito.any(Integer.class))).thenThrow(new ValidacaoException(expected));
		Mockito.when(categoriaService.procurarCategoriaFesta(Mockito.anyInt(), Mockito.anyString())).thenReturn(categoriaTest(), null);


		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(festaJson)
				.param("idUser", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());

	}



	//get festa unica________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________	

	@Test
	void getFestaSucessoTest() throws Exception {

		Festa festaTest = festaTest();

		String uri = "/festa/festaUnica";

		Mockito.when(festaService.procurarFesta(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(festaTest);
		Mockito.when(userService.getUsuariosFesta(Mockito.any(Integer.class))).thenReturn(colecaoDeUsuario(2));
		Mockito.when(categoriaService.procurarCategoriaFesta(Mockito.anyInt(), Mockito.anyString())).thenReturn(categoriaTest(), null);
		Mockito.when(festaFactory.getFestaTO(Mockito.any(), Mockito.anyList(), Mockito.anyBoolean(), Mockito.any(), Mockito.any(), Mockito.anyList(), Mockito.anyInt())).thenReturn(festaToTest());

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("idFesta", "2")
				.param("idUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		String expected = "{\"codFesta\":0,\"nomeFesta\":\"festao\",\"statusFesta\":\"I\",\"organizador\":\"Joao Neves\",\"horarioInicioFesta\":\"2016-06-22T19:10:00\",\"horarioFimFesta\":\"2016-06-23T19:10:00\",\"descricaoFesta\":\"Bugago\",\"codEnderecoFesta\":\"https//:minhacasa.org\",\"descOrganizador\":\"sou demente\",\"horarioFimFestaReal\":\"2016-06-23T19:10:00\",\"funcionalidade\":null,\"quantidadeParticipantes\":0,\"usuarios\":null,\"codPrimaria\":0,\"codSecundaria\":0,\"categoriaPrimaria\":null,\"categoriaSecundaria\":null,\"isOrganizador\":null,\"convidados\":null}";

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}

	@Test
	void getFestaNaoEncontradaTest() throws Exception {

		String uri = "/festa/festaUnica";

		String expected = "";

		Mockito.when(festaService.procurarFesta(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(null);
		Mockito.when(userService.getUsuariosFesta(Mockito.any(Integer.class))).thenReturn(colecaoDeUsuario(2));
		Mockito.when(categoriaService.procurarCategoriaFesta(Mockito.anyInt(), Mockito.anyString())).thenReturn(categoriaTest(), null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.param("idFesta", "2")
				.param("idUsuario", "2")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(200, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}

	@Test
	void getFestaExceptionTest() throws Exception {

		String expected = "";

		String uri = "/festa/festaUnica";

		Mockito.when(festaService.procurarFesta(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenThrow(new ValidacaoException(expected));
		Mockito.when(userService.getUsuariosFesta(Mockito.any(Integer.class))).thenThrow(new ValidacaoException(expected));
		Mockito.when(categoriaService.procurarCategoriaFesta(Mockito.anyInt(), Mockito.anyString())).thenReturn(categoriaTest(), null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("idFesta", "2")
				.param("idUsuario", "2")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();



		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}

	//alterar status festa________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
	@Test
	void alterarStatusFestaSucesso() throws Exception{

		String uri = "/festa/festaMudancaStatus";

		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(usuarioTest());

		Mockito.when(festaService.mudarStatusFesta(Mockito.any(Integer.class), Mockito.any(String.class), Mockito.any(Integer.class))).thenReturn(this.festaTest());

		Mockito.when(userService.getUsuariosFesta(Mockito.any(Integer.class))).thenReturn(usuarios);

		Mockito.when(userService.funcionalidadeUsuarioFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn("ORGANIZADOR");

		Mockito.when(categoriaService.procurarCategoriaFesta(Mockito.anyInt(), Mockito.anyString())).thenReturn(categoriaTest(), null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("idFesta", "2")
				.param("idUsuario", "1")
				.param("statusFesta", "I")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());		
	}

	@Test
	void alterarStatusFestaErro() throws Exception{

		String expected = "teste";

		String uri = "/festa/festaMudancaStatus";

		Mockito.when(festaService.mudarStatusFesta(Mockito.any(Integer.class), Mockito.any(String.class), Mockito.any(Integer.class))).thenThrow(new ValidacaoException("teste"));

		Mockito.when(userService.getUsuariosFesta(Mockito.any(Integer.class))).thenReturn(null);

		Mockito.when(categoriaService.procurarCategoriaFesta(Mockito.anyInt(), Mockito.anyString())).thenReturn(categoriaTest(), null);

		Mockito.when(userService.funcionalidadeUsuarioFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("idFesta", "2")
				.param("idUsuario", "1")
				.param("statusFesta", "I")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());

	}
	
	//festaUnicaConvidado______________________________________________________________________________________________________________________________________________
	@Test
	void festaUnicaConvidadoSucesso() throws Exception{

		String uri = "/festa/festaUnicaConvidado";

		Mockito.when(festaService.procurarFestaConvidado(Mockito.anyInt(), Mockito.anyInt())).thenReturn(new ConviteFestaTO());

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codGrupo", "2")
				.param("codConvidado", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());		
	}
	
	@Test
	void festaUnicaConvidadoErro() throws Exception{

		String uri = "/festa/festaUnicaConvidado";

		Mockito.when(festaService.procurarFestaConvidado(Mockito.anyInt(), Mockito.anyInt())).thenThrow(new ValidacaoException("teste"));

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("codGrupo", "2")
				.param("codConvidado", "1")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(400, response.getStatus());		
	}


}
