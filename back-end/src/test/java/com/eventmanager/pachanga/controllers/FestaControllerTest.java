package com.eventmanager.pachanga.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

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

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.services.FestaService;
import com.eventmanager.pachanga.services.UsuarioService;

@RunWith(SpringRunner.class)
@WebMvcTest(value=FestaController.class)
public class FestaControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FestaService festaService;
	
	@MockBean
	private UsuarioService userService;
	
	
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
		
		return festaTest;
	}
	
	private List<Festa> colecaoDeFestas(int quantidadeFestas) throws Exception {
		List<Festa> festas = null;
		
		if(quantidadeFestas >= 1) {		
			festas = new ArrayList<Festa>();
			
			Festa festaTest1 = festaTest();
			festaTest1.setCodFesta(1);
			festaTest1.setNomeFesta("fest1");
	
			festas.add(festaTest1);
		 
			if(quantidadeFestas >= 2) {	
				Festa festaTest2 = festaTest();
				festaTest2.setCodFesta(3);
				festaTest2.setNomeFesta("fest2");
			
				festas.add(festaTest2);
			} 
			if(quantidadeFestas >= 3) {		
				Festa festaTest3 = festaTest();
				festaTest3.setCodFesta(4);
				festaTest3.setNomeFesta("fest4");
			
				festas.add(festaTest3);
			} 
			if(quantidadeFestas >= 4) {		
				Festa festaTest4 = festaTest();
				festaTest4.setCodFesta(5);
				festaTest4.setNomeFesta("fest5");
			
				festas.add(festaTest4);
			}
		}
		
		return festas;
	}
	
	
//Adicionar_____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________	
	@Test
	public void adicionarFestaSucessoTest() throws Exception {
		String festaJson = "{\"codFesta\":\"2\",\"nomeFesta\":\"festao\",\"statusFesta\":\"I\",\"organizador\":\"Joao Neves\",\"horarioInicioFesta\":\"2016-06-22T19:10:00\",\"horarioFimFesta\":\"2016-06-23T19:10:00\",\"descricaoFesta\":\"Bugago\",\"codEnderecoFesta\":\"https//:minhacasa.org\",\"descOrganizador\":\"sou demente\"}";

		Festa festaTest = festaTest();
		 
		String uri = "/festa/adicionar";

		Mockito.when(festaService.addFesta(Mockito.any(FestaTO.class), Mockito.any(Integer.class))).thenReturn(festaTest);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(festaJson)
				.param("idUser", "1")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		String expected = "{\"codFesta\":2,\"nomeFesta\":\"festao\",\"statusFesta\":\"I\",\"organizador\":\"Joao Neves\",\"horarioInicioFesta\":\"2016-06-22T19:10:00\",\"horarioFimFesta\":\"2016-06-23T19:10:00\",\"descricaoFesta\":\"Bugago\",\"codEnderecoFesta\":\"https//:minhacasa.org\",\"descOrganizador\":\"sou demente\",\"horarioFimFestaReal\":\"2016-06-23T19:10:00\",\"funcionalidade\":null,\"quantidadeParticipantes\":0,\"usuarios\":null}";
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	public void adicionarFestaExceptionTest() throws Exception {
		String festaJson = "{\"codFesta\":\"2\",\"nomeFesta\":\"festao\",\"statusFesta\":\"I\",\"organizador\":\"Joao Neves\",\"horarioInicioFesta\":\"2016-06-22T19:10:00\",\"horarioFimFesta\":\"2016-06-23T19:10:00\",\"descricaoFesta\":\"Bugago\",\"codEnderecoFesta\":\"https//:minhacasa.org\",\"descOrganizador\":\"sou demente\"}";
		 
		String uri = "/festa/adicionar";

		Mockito.when(festaService.addFesta(Mockito.any(FestaTO.class), Mockito.any(Integer.class))).thenThrow(new ValidacaoException("addFesta"));

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
	public void listarFestaSucessoTest() throws Exception {
		List<Festa> festas = colecaoDeFestas(2);
		List<Festa> festasAll = colecaoDeFestas(4);

		Mockito.when(festaService.procurarFestasPorUsuario(Mockito.any(Integer.class))).thenReturn(festas);
		Mockito.when(festaService.procurarFestas()).thenReturn(festasAll);
		
		String uri = "/festa/lista";
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("idUser", "1")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		String expected = "[{\"codFesta\":1,\"nomeFesta\":\"fest1\",\"statusFesta\":\"I\",\"organizador\":\"Joao Neves\",\"horarioInicioFesta\":\"2016-06-22T19:10:00\",\"horarioFimFesta\":\"2016-06-23T19:10:00\",\"descricaoFesta\":\"Bugago\",\"codEnderecoFesta\":\"https//:minhacasa.org\",\"descOrganizador\":\"sou demente\",\"horarioFimFestaReal\":\"2016-06-23T19:10:00\",\"funcionalidade\":null,\"quantidadeParticipantes\":0,\"usuarios\":null},{\"codFesta\":3,\"nomeFesta\":\"fest2\",\"statusFesta\":\"I\",\"organizador\":\"Joao Neves\",\"horarioInicioFesta\":\"2016-06-22T19:10:00\",\"horarioFimFesta\":\"2016-06-23T19:10:00\",\"descricaoFesta\":\"Bugago\",\"codEnderecoFesta\":\"https//:minhacasa.org\",\"descOrganizador\":\"sou demente\",\"horarioFimFestaReal\":\"2016-06-23T19:10:00\",\"funcionalidade\":null,\"quantidadeParticipantes\":0,\"usuarios\":null}]";
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	public void listarFestaParametroNullTest() throws Exception {
		List<Festa> festas = colecaoDeFestas(2);
		List<Festa> festasAll = colecaoDeFestas(4);
		
		Mockito.when(festaService.procurarFestasPorUsuario(Mockito.any(Integer.class))).thenReturn(festas);
		Mockito.when(festaService.procurarFestas()).thenReturn(festasAll);

		String uri = "/festa/lista";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		String expected = "[{\"codFesta\":1,\"nomeFesta\":\"fest1\",\"statusFesta\":\"I\",\"organizador\":\"Joao Neves\",\"horarioInicioFesta\":\"2016-06-22T19:10:00\",\"horarioFimFesta\":\"2016-06-23T19:10:00\",\"descricaoFesta\":\"Bugago\",\"codEnderecoFesta\":\"https//:minhacasa.org\",\"descOrganizador\":\"sou demente\",\"horarioFimFestaReal\":\"2016-06-23T19:10:00\",\"funcionalidade\":null,\"quantidadeParticipantes\":0,\"usuarios\":null},"
				          +"{\"codFesta\":3,\"nomeFesta\":\"fest2\",\"statusFesta\":\"I\",\"organizador\":\"Joao Neves\",\"horarioInicioFesta\":\"2016-06-22T19:10:00\",\"horarioFimFesta\":\"2016-06-23T19:10:00\",\"descricaoFesta\":\"Bugago\",\"codEnderecoFesta\":\"https//:minhacasa.org\",\"descOrganizador\":\"sou demente\",\"horarioFimFestaReal\":\"2016-06-23T19:10:00\",\"funcionalidade\":null,\"quantidadeParticipantes\":0,\"usuarios\":null},"
		                  +"{\"codFesta\":4,\"nomeFesta\":\"fest4\",\"statusFesta\":\"I\",\"organizador\":\"Joao Neves\",\"horarioInicioFesta\":\"2016-06-22T19:10:00\",\"horarioFimFesta\":\"2016-06-23T19:10:00\",\"descricaoFesta\":\"Bugago\",\"codEnderecoFesta\":\"https//:minhacasa.org\",\"descOrganizador\":\"sou demente\",\"horarioFimFestaReal\":\"2016-06-23T19:10:00\",\"funcionalidade\":null,\"quantidadeParticipantes\":0,\"usuarios\":null},"
                          +"{\"codFesta\":5,\"nomeFesta\":\"fest5\",\"statusFesta\":\"I\",\"organizador\":\"Joao Neves\",\"horarioInicioFesta\":\"2016-06-22T19:10:00\",\"horarioFimFesta\":\"2016-06-23T19:10:00\",\"descricaoFesta\":\"Bugago\",\"codEnderecoFesta\":\"https//:minhacasa.org\",\"descOrganizador\":\"sou demente\",\"horarioFimFestaReal\":\"2016-06-23T19:10:00\",\"funcionalidade\":null,\"quantidadeParticipantes\":0,\"usuarios\":null}]";

		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	public void listarFestaProcurarFestasPorUsuarioExceptionTest() throws Exception {

		Mockito.when(festaService.procurarFestasPorUsuario(Mockito.any(Integer.class))).thenThrow(new ValidacaoException("procurarFestasPorUsuario"));
		Mockito.when(festaService.procurarFestas()).thenThrow(new ValidacaoException("procurarFestas"));;
		
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
	public void listarFestaProcurarFestasExceptionTest() throws Exception {
		
		Mockito.when(festaService.procurarFestasPorUsuario(Mockito.any(Integer.class))).thenThrow(new ValidacaoException("procurarFestasPorUsuario"));
		Mockito.when(festaService.procurarFestas()).thenThrow(new ValidacaoException("procurarFestas"));
		
		String uri = "/festa/lista";
		
		//problema com o festaService.procurarFestasPorUsuario
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		String expected = "procurarFestas";

		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
		
	}
		
//deletar_____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________	
	
	 
	@Test
	public void deletarFestaSucessoTest() throws Exception {
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
	public void deletarFestaExceptionTest() throws Exception {
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
	public void atualizarFestaSucessoTest() throws Exception {
		String festaJson = "{\"codFesta\":\"2\",\"nomeFesta\":\"festao\",\"statusFesta\":\"I\",\"organizador\":\"Joao Neves\",\"horarioInicioFesta\":\"2016-06-22T19:10:00\",\"horarioFimFesta\":\"2016-06-23T19:10:00\",\"descricaoFesta\":\"Bugago\",\"codEnderecoFesta\":\"https//:minhacasa.org\",\"descOrganizador\":\"sou demente\"}";

		Festa festaTest = festaTest();
		 
		String uri = "/festa/atualizar";

		Mockito.when(festaService.updateFesta(Mockito.any(FestaTO.class), Mockito.any(Integer.class))).thenReturn(festaTest);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(uri)
				.accept(MediaType.APPLICATION_JSON)
				.content(festaJson)
				.param("idUser", "1")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		String expected = "{\"codFesta\":2,\"nomeFesta\":\"festao\",\"statusFesta\":\"I\",\"organizador\":\"Joao Neves\",\"horarioInicioFesta\":\"2016-06-22T19:10:00\",\"horarioFimFesta\":\"2016-06-23T19:10:00\",\"descricaoFesta\":\"Bugago\",\"codEnderecoFesta\":\"https//:minhacasa.org\",\"descOrganizador\":\"sou demente\",\"horarioFimFestaReal\":\"2016-06-23T19:10:00\",\"funcionalidade\":null,\"quantidadeParticipantes\":0,\"usuarios\":null}";
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
		
	}
	
	@Test
	public void atualizarFestaExceptionTest() throws Exception {
		String festaJson = "{\"codFesta\":\"2\",\"nomeFesta\":\"festao\",\"statusFesta\":\"I\",\"organizador\":\"Joao Neves\",\"horarioInicioFesta\":\"2016-06-22T19:10:00\",\"horarioFimFesta\":\"2016-06-23T19:10:00\",\"descricaoFesta\":\"Bugago\",\"codEnderecoFesta\":\"https//:minhacasa.org\",\"descOrganizador\":\"sou demente\"}";

		String expected = "updateFesta";
				 
		String uri = "/festa/atualizar";

		Mockito.when(festaService.updateFesta(Mockito.any(FestaTO.class), Mockito.any(Integer.class))).thenThrow(new ValidacaoException(expected));


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
	public void getFestaSucessoTest() throws Exception {
		
		Festa festaTest = festaTest();
		
		String uri = "/festa/festaUnica";

		Mockito.when(festaService.procurarFesta(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(festaTest);
		Mockito.when(userService.getUsuariosFesta(Mockito.any(Integer.class))).thenReturn(UsuarioControllerTest.colecaoDeUsuario(2));

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("idFesta", "2")
				.param("idUsuario", "1")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		String expected = "{\"codFesta\":2,\"nomeFesta\":\"festao\",\"statusFesta\":\"I\",\"organizador\":\"Joao Neves\",\"horarioInicioFesta\":\"2016-06-22T19:10:00\",\"horarioFimFesta\":\"2016-06-23T19:10:00\",\"descricaoFesta\":\"Bugago\",\"codEnderecoFesta\":\"https//:minhacasa.org\",\"descOrganizador\":\"sou demente\",\"horarioFimFestaReal\":\"2016-06-23T19:10:00\",\"funcionalidade\":null,\"quantidadeParticipantes\":0,\"usuarios\":["
				         +"{\"dtNasc\":\"3900-09-27T00:00:00.000+0000\",\"codUsuario\":1,\"nomeUser\":\"Andrey\",\"tipConta\":\"P\",\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\": null,\"sexo\":\"M\",\"funcionalidade\": null},"
				         +"{\"dtNasc\":\"3900-09-27T00:00:00.000+0000\",\"codUsuario\":2,\"nomeUser\":\"Luis\",\"tipConta\":\"P\",\"email\":\"gustavinhoTPD@fodasse.com.br\",\"emailNovo\":null,\"senha\":null,\"senhaNova\": null,\"sexo\":\"M\",\"funcionalidade\": null}]}";
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}
	
	@Test
	public void getFestaNaoEncontradaTest() throws Exception {
		
		String uri = "/festa/festaUnica";
		
		String expected = "";
		
		Mockito.when(festaService.procurarFesta(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(null);
		Mockito.when(userService.getUsuariosFesta(Mockito.any(Integer.class))).thenReturn(UsuarioControllerTest.colecaoDeUsuario(2));

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	public void getFestaExceptionTest() throws Exception {
		
		String expected = "";
		
		String uri = "/festa/festaUnica";

		Mockito.when(festaService.procurarFesta(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenThrow(new ValidacaoException(expected));
		Mockito.when(userService.getUsuariosFesta(Mockito.any(Integer.class))).thenThrow(new ValidacaoException(expected));

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.param("idFesta", "2")
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		
		
		assertEquals(400, response.getStatus());
		assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	//alterar status festa________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________
	@Test
	public void alterarStatusFestaSucesso() throws Exception{
				
		String uri = "/festa/festaMudancaStatus";
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(UsuarioControllerTest.usuarioTest());
		
		Mockito.when(festaService.mudarStatusFesta(Mockito.any(Integer.class), Mockito.any(String.class), Mockito.any(Integer.class))).thenReturn(this.festaTest());
		
		Mockito.when(userService.getUsuariosFesta(Mockito.any(Integer.class))).thenReturn(usuarios);
		
		Mockito.when(userService.funcionalidadeUsuarioFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn("ORGANIZADOR");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
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
	public void alterarStatusFestaErro() throws Exception{
		
		String expected = "teste";
		
		String uri = "/festa/festaMudancaStatus";
		
		Mockito.when(festaService.mudarStatusFesta(Mockito.any(Integer.class), Mockito.any(String.class), Mockito.any(Integer.class))).thenThrow(new ValidacaoException("teste"));
		
		Mockito.when(userService.getUsuariosFesta(Mockito.any(Integer.class))).thenReturn(null);
		
		Mockito.when(userService.funcionalidadeUsuarioFesta(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(uri)
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
	
	
}
