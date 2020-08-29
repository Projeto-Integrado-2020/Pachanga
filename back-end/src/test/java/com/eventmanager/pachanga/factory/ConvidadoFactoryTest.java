package com.eventmanager.pachanga.factory;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventmanager.pachanga.domains.Convidado;
import com.eventmanager.pachanga.dtos.ConvidadoTO;

@RunWith(SpringRunner.class)
@WebMvcTest(value=ConvidadoFactory.class)
public class ConvidadoFactoryTest {
	
	@Autowired
	ConvidadoFactory convidadoFactory;
	
	private Convidado convidadoTest() {
		Convidado convidado = new Convidado();
		convidado.setCodConvidado(1);
		convidado.setEmail("test@email.com");
		return convidado;
	}
	
	@Test
	void getConvidadosTOSucesso() throws Exception {
		Convidado convidado = convidadoTest();
		List<Convidado> convidados = new ArrayList<>();
		convidados.add(convidado);
		
		List<ConvidadoTO> convidadosTO = convidadoFactory.getConvidadosTO(convidados);
		ConvidadoTO convidadoTO = convidadosTO.get(0);
		
		assertEquals( convidadoTO.getCodConvidado(), convidado .getCodConvidado());
		assertEquals( convidadoTO.getEmail(), convidado .getEmail());
		
	}

}
