package com.eventmanager.pachanga.services;

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

import com.eventmanager.pachanga.domains.Grupo;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.GrupoRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.utils.EmailMensagem;

@RunWith(SpringRunner.class)
@WebMvcTest(value=GrupoService.class)
public class GrupoServiceTest {
	
	@MockBean
	private UsuarioRepository usuarioRepository;

	@MockBean
	private GrupoRepository grupoRepository;

	@MockBean
	private FestaRepository festaRepository;
	
	@Autowired
	private GrupoService grupoService;
	
	@MockBean
	private EmailMensagem emailMensagem;
	
	private Grupo criacaoGrupo() {
		Grupo grupo = new Grupo();
		grupo.setCodGrupo(1);
		grupo.setNomeGrupo("CONVIDADO");
		return grupo;
	}
	
	private Usuario criacaoUsuario() {
		Usuario usuario = new Usuario();
		usuario.setCodUsuario(1);
		usuario.setEmail("guga.72@hotmail.com");
		usuario.setTipConta("F");
		return usuario;
	}
	
	@Test
	public void AddUserFestaTest() {
		Grupo grupo = criacaoGrupo();
		List<String> emails = new ArrayList<String>(); 
		emails.add("guga.72@hotmail.com");
		Mockito.when(grupoRepository.findGrupoConvidadoFesta(14)).thenReturn(grupo);
		Mockito.when(usuarioRepository.findByEmail("guga.72@hotmail.com")).thenReturn(criacaoUsuario());
		
		StringBuilder retorno = grupoService.addUsuariosFesta(emails, 14);
		
		assertEquals(0, retorno.length());
	}
	
	@Test
	public void AddUserFestaEmailTest() {
		Grupo grupo = criacaoGrupo();
		List<String> emails = new ArrayList<String>(); 
		emails.add("guga.72@hotmail.com");
		Mockito.when(grupoRepository.findGrupoConvidadoFesta(14)).thenReturn(grupo);
		Mockito.when(usuarioRepository.findByEmail("guga.72@hotmail.com")).thenReturn(null);
		
		StringBuilder retorno = grupoService.addUsuariosFesta(emails, 14);
		
		assertEquals("guga.72@hotmail.com ", retorno.toString());
	}
	
	@Test
	public void AddUserFestaEmailGrupoNulloTest() {
		List<String> emails = new ArrayList<String>(); 
		emails.add("guga.72@hotmail.com");
		Mockito.when(grupoRepository.findGrupoConvidadoFesta(14)).thenReturn(null);
		Mockito.when(usuarioRepository.findByEmail("guga.72@hotmail.com")).thenReturn(null);
		
		StringBuilder retorno = grupoService.addUsuariosFesta(emails, 14);
		
		assertEquals("guga.72@hotmail.com ", retorno.toString());
	}

}
