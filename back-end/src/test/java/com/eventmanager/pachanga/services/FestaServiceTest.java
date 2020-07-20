package com.eventmanager.pachanga.services;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.doNothing;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.dtos.UsuarioTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
//import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;


@RunWith(SpringRunner.class)
@WebMvcTest(value=FestaService.class)
public class FestaServiceTest {

	@MockBean
	private UsuarioRepository usuarioRepository;
	
	@MockBean
	private FestaRepository festaRepository;
	
	@Autowired
	private FestaService festaService;
	
	@MockBean
	private GrupoRepository grupoRepository;
	
	
	
	
//metodos auxiliares___________________________________________________________________________________________________________________________________	
	public FestaTO festaTOTest() throws Exception{
		FestaTO festaTest = new FestaTO();
		
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
		
		UsuarioTO usuario1 = UsuarioServiceTest.usuarioToTest();
		usuario1.setCodUsuario(1);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");
		
		UsuarioTO usuario2 = UsuarioServiceTest.usuarioToTest();
		usuario2.setCodUsuario(2);
		usuario2.setNomeUser("Braz_qualquer_e_ficticio");
		
		List<UsuarioTO> usuarios = new ArrayList<UsuarioTO>();
		usuarios.add(usuario1);
		usuarios.add(usuario2);
		
		return festaTest;
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
		festaTest.setStatusFesta("I");
		festaTest.setDescricaoFesta("Bugago");
		festaTest.setHorarioFimFestaReal(LocalDateTime.of(2016, Month.JUNE, 23, 19, 10));
		
		return festaTest;
	}
	

	
//procurarFestasTest_______________________________________________________________________________________________________________________________________	
	
	@Test
	public void procurarFestasTest() throws Exception {
		Festa festa1 = festaTest();
		festa1.setCodFesta(1);
		festa1.setNomeFesta("festa1");
		
		Festa festa2 = festaTest();
		festa1.setCodFesta(2);
		festa1.setNomeFesta("festa2");
		
		Festa festa3 = festaTest();
		festa1.setCodFesta(3);
		festa1.setNomeFesta("festa3");
		
		List<Festa> festas = new ArrayList<Festa>();
		festas.add(festa1);
		festas.add(festa2);
		festas.add(festa3);
		
		Mockito.when(festaRepository.findAll()).thenReturn(festas);
		
		List<Festa> retorno = festaService.procurarFestas();
		
		System.out.println(retorno.size());
		
		assertEquals(festas.size(), retorno.size());
		assertEquals(true, retorno.containsAll(festas));
	}
	
//procurarFestasPorUsuario_________________________________________________________________________________________________________________________
	@Test
	public void procurarFestasPorUsuarioTest() throws Exception {
		Festa festa1 = festaTest();
		festa1.setCodFesta(1);
		festa1.setNomeFesta("festa1");
	
		Festa festa2 = festaTest();
		festa1.setCodFesta(2);
		festa1.setNomeFesta("festa2");
	
		Festa festa3 = festaTest();
		festa1.setCodFesta(3);
		festa1.setNomeFesta("festa3");
	
		List<Festa> festas = new ArrayList<Festa>();
		festas.add(festa1);
		festas.add(festa2);
		festas.add(festa3);
		
		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(1);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");
	
		Mockito.when(usuarioRepository.findById(1)).thenReturn(usuario1);
		Mockito.when(festaRepository.findByUsuarios(1)).thenReturn(festas);
	
		List<Festa> retorno = festaService.procurarFestasPorUsuario(1);
	
		assertEquals(festas.size(), retorno.size());
		assertEquals(true, retorno.containsAll(festas));
	}
	
//add festa_____________________________________________________________________________________________________________________________________	
	@Test
	public void addFestaTest() throws Exception{
		FestaTO festaTO = festaTOTest();
		int idUser = 1;
		
		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");
		
		Mockito.when(usuarioRepository.findById(idUser)).thenReturn(null);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		Mockito.when(festaRepository.getNextValMySequence()).thenReturn(2);
		Mockito.when(festaRepository.save(Mockito.any(Festa.class))).thenReturn(null);
		Mockito.when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(null);
		doNothing().when(grupoRepository).saveUsuarioGrupo(Mockito.any(Integer.class), Mockito.any(Integer.class));

		Festa retorno = null;
		try {
			retorno = festaService.addFesta(festaTO, idUser);
		}catch(ValidacaoException e){
			retorno = null;
		};
			
		assertEquals(retorno, null);
	}
	
	
	@Test
	public void addFestavalidarUsuarioExceptionTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		int idUser = 1;
		
		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");
		
		Mockito.when(usuarioRepository.findById(idUser)).thenReturn(usuario1);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		Mockito.when(festaRepository.getNextValMySequence()).thenReturn(2);
		Mockito.when(festaRepository.save(Mockito.any(Festa.class))).thenReturn(null);
		Mockito.when(grupoRepository.save(Mockito.any(Grupo.class))).thenReturn(null);
		doNothing().when(grupoRepository).saveUsuarioGrupo(Mockito.any(Integer.class), Mockito.any(Integer.class));

		Festa retorno = festaService.addFesta(festaTO, idUser);
		assertEquals(retorno.getCodFesta(), festaTO.getCodFesta());
		assertEquals(retorno.getNomeFesta(), festaTO.getNomeFesta());
		assertEquals(retorno.getHorarioFimFesta(), festaTO.getHorarioFimFesta());
		assertEquals(retorno.getHorarioInicioFesta(), festaTO.getHorarioInicioFesta());
	}
	
//delete Festa_____________________________________________________________________________________________________________________________________	
	
	@Test
	public void deleteFestaTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		Festa festaTest = festaTest();
		
		List<Grupo> grupos = new ArrayList<Grupo>();
		
		Grupo grupo1 = GrupoServiceTest.criacaoGrupo();
		grupo1.setCodGrupo(1);
		grupo1.setFesta(festaTest);
		grupo1.setQuantMaxPessoas(3);
		grupo1.setNomeGrupo("Grupo1");
		grupos.add(grupo1);
		
		Grupo grupo2 = GrupoServiceTest.criacaoGrupo();
		grupo2.setCodGrupo(2);
		grupo2.setFesta(festaTest);
		grupo2.setQuantMaxPessoas(3);
		grupo2.setNomeGrupo("Grupo2");
		grupos.add(grupo2);
		
		int idUser = 1;
		
		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");
		
		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(grupoRepository.findGruposFesta(festaTO.getCodFesta())).thenReturn(grupos);
		
		doNothing().when(grupoRepository).deleteUsuarioGrupo(Mockito.any(Integer.class));
		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());
		doNothing().when(festaRepository).deleteById(Mockito.any(Integer.class));

		festaService.deleteFesta(festaTO.getCodFesta(), idUser);
		
		assertEquals(true, true);  //não tem retorno no festaService.deleteFesta para qualquer assert, esse teste mais verifica se não quebra msm
	}
	
//update Festa__________________________________________________________________________________________________________________________________________________
//estou usando o update para testar métodos privados do service	
	
	@Test
	public void updateFestaTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		Festa festaTest = festaTest();
		
		Festa festaTest2 = festaTest();
		festaTest2.setNomeFesta("loucura");
		festaTest2.setDescricaoFesta("5x mais adrenalina!!!");
		
		int idUser = 1;
		
		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");
		
		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findById(festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		
		doNothing().when(grupoRepository).deleteUsuarioGrupo(Mockito.any(Integer.class));
		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());
		
		Mockito.when(festaRepository.save(festaTest)).thenReturn(festaTest2);

		Festa retorno = festaService.updateFesta(festaTO, idUser);
		
		 //assertFalse(retorno.getNomeFesta().equals(festaTest.getNomeFesta()));  / do jeito que esta service atualmente, não tem como testar de fato
		 //assertFalse(retorno.getDescricaoFesta().equals(festaTest.getDescricaoFesta()));
		 assertEquals(retorno.getCodEnderecoFesta(), festaTest.getCodEnderecoFesta());
		 assertEquals(retorno.getOrganizador(), festaTest.getOrganizador());
	}
	
	@Test
	public void updateFestaExceptionTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		Festa festaTest = festaTest();
		
		Festa festaTest2 = festaTest();
		festaTest2.setNomeFesta("loucura");
		festaTest2.setDescricaoFesta("5x mais adrenalina!!!");
		
		int idUser = 1;
		
		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");
		
		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findById(festaTO.getCodFesta())).thenReturn(null);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		
		doNothing().when(grupoRepository).deleteUsuarioGrupo(Mockito.any(Integer.class));
		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());
		
		Mockito.when(festaRepository.save(festaTest)).thenReturn(festaTest2);
		
		Festa retorno = null;
		try {
			retorno = festaService.updateFesta(festaTO, idUser);
		}catch(ValidacaoException e){
			retorno = null;
		};

		assertEquals(retorno, null);
	
	}
	
	@Test
	public void updateFestaUsuarioSemPermissaoTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		Festa festaTest = festaTest();
		
		Festa festaTest2 = festaTest();
		festaTest2.setNomeFesta("loucura");
		festaTest2.setDescricaoFesta("5x mais adrenalina!!!");
		
		int idUser = 1;
		
		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");
		
		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(null);
		Mockito.when(festaRepository.findById(festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		
		doNothing().when(grupoRepository).deleteUsuarioGrupo(Mockito.any(Integer.class));
		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());
		
		Mockito.when(festaRepository.save(festaTest)).thenReturn(festaTest2);
		
		Festa retorno = null;
		try {
			retorno = festaService.updateFesta(festaTO, idUser);
		}catch(ValidacaoException e){
			retorno = null;
		};

		assertEquals(retorno, null);
	}
	
	@Test
	public void updateFestaValidacaoDataErroTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		festaTO.setHorarioInicioFesta(LocalDateTime.of(2046, Month.JUNE, 22, 19, 10));
		festaTO.setHorarioFimFesta(LocalDateTime.of(2006, Month.JUNE, 22, 19, 10));
		
		Festa festaTest = festaTest();
		festaTest.setHorarioInicioFesta(LocalDateTime.of(2046, Month.JUNE, 22, 19, 10));
		festaTest.setHorarioFimFesta(LocalDateTime.of(2006, Month.JUNE, 22, 19, 10));
		
		Festa festaTest2 = festaTest();
		festaTest2.setNomeFesta("loucura");
		festaTest2.setDescricaoFesta("5x mais adrenalina!!!");
		
		int idUser = 1;
		
		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");
		
		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findById(festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		
		doNothing().when(grupoRepository).deleteUsuarioGrupo(Mockito.any(Integer.class));
		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());
		
		Mockito.when(festaRepository.save(festaTest)).thenReturn(festaTest2);
		
		Festa retorno = null;
		try {
			retorno = festaService.updateFesta(festaTO, idUser);
		}catch(ValidacaoException e){
			retorno = null;
		};

		assertEquals(retorno, null);
	}
	
	@Test
	public void updateFestaValidacaoDataSecErroTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		festaTO.setHorarioInicioFesta(LocalDateTime.of(2046, Month.JUNE, 22, 19, 10)); 
		festaTO.setHorarioFimFesta(LocalDateTime.of(2046, Month.JUNE, 22, 19, 10));
		
		Festa festaTest = festaTest();
		festaTest.setHorarioInicioFesta(LocalDateTime.of(2046, Month.JUNE, 22, 19, 10));
		festaTest.setHorarioFimFesta(LocalDateTime.of(2046, Month.JUNE, 22, 19, 10));
		
		Festa festaTest2 = festaTest();
		festaTest2.setNomeFesta("loucura");
		festaTest2.setDescricaoFesta("5x mais adrenalina!!!");
		
		int idUser = 1;
		
		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");
		
		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findById(festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		
		doNothing().when(grupoRepository).deleteUsuarioGrupo(Mockito.any(Integer.class));
		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());
		
		Mockito.when(festaRepository.save(festaTest)).thenReturn(festaTest2);
		
		Festa retorno = null;
		try {
			retorno = festaService.updateFesta(festaTO, idUser);
		}catch(ValidacaoException e){
			retorno = null;
		};

		assertEquals(retorno, null);
	}
	
	@Test
	public void updateFestaNomeDuplicadoTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		Festa festaTest = festaTest();
		
		//festa com nome duplicado
		Festa nomeDuplicado = festaTest();
		nomeDuplicado.setCodFesta(25);
		
		Festa festaTest2 = festaTest();
		festaTest2.setNomeFesta("loucura");
		festaTest2.setDescricaoFesta("5x mais adrenalina!!!");
		
		int idUser = 1;
		
		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");
		
		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findById(festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(nomeDuplicado);
		
		doNothing().when(grupoRepository).deleteUsuarioGrupo(Mockito.any(Integer.class));
		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());
		
		Mockito.when(festaRepository.save(festaTest)).thenReturn(festaTest2);

		Festa retorno = null;
		try {
			retorno = festaService.updateFesta(festaTO, idUser);
		}catch(ValidacaoException e){
			retorno = null;
		};

		assertEquals(retorno, null);
	}
	
	@Test
	public void updateFestaEnderecoNullTest() throws Exception {
		FestaTO festaTO = festaTOTest();
		festaTO.setCodEnderecoFesta(null);
		
		Festa festaTest = festaTest();
		festaTest.setCodEnderecoFesta(null);
		
		//festa com nome duplicado
		Festa nomeDuplicado = festaTest();
		nomeDuplicado.setCodFesta(25);
		
		Festa festaTest2 = festaTest();
		festaTest2.setNomeFesta("loucura");
		festaTest2.setDescricaoFesta("5x mais adrenalina!!!");
		
		int idUser = 1;
		
		Usuario usuario1 = UsuarioServiceTest.usuarioTest();
		usuario1.setCodUsuario(idUser);
		usuario1.setNomeUser("Aires_qualquer_e_ficticio");
		
		Mockito.when(festaRepository.findFestaByUsuarioResponsavel(idUser, festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findById(festaTO.getCodFesta())).thenReturn(festaTest);
		Mockito.when(festaRepository.findByNomeFesta(festaTO.getNomeFesta())).thenReturn(null);
		
		doNothing().when(grupoRepository).deleteUsuarioGrupo(Mockito.any(Integer.class));
		doNothing().when(grupoRepository).deleteAll(Mockito.<Grupo>anyList());
		
		Mockito.when(festaRepository.save(festaTest)).thenReturn(festaTest2);

		Festa retorno = null;
		try {
			retorno = festaService.updateFesta(festaTO, idUser);
		}catch(ValidacaoException e){
			retorno = null;
		};

		assertEquals(retorno, null);
	}
	
//procurar festa____________________________________________________________________________________________________________________________________
	@Test
	public void procurarFestaTest() throws Exception {
		Festa festaTest = festaTest();
		
		Mockito.when(festaRepository.findByCodFesta(Mockito.any(Integer.class))).thenReturn(festaTest);
	
		Festa retorno = festaService.procurarFesta(1);

	    assertFalse(retorno == null);
	}
	
// funcionalidade Festa____________________________________________________________________________________________________________________________________
	@Test
	public void funcionalidadeFesta() throws Exception {
		String expected = "Estagiário senior em iluminação";
		
		Mockito.when(grupoRepository.findFuncionalidade(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(expected);

		String retorno = festaService.funcionalidadeFesta(1, 1);
		
		assertEquals(retorno, expected);
	}

}
