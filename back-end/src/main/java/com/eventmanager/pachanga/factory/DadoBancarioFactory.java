package com.eventmanager.pachanga.factory;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.DadoBancarioBuilder;
import com.eventmanager.pachanga.builder.DadoBancarioTOBuilder;
import com.eventmanager.pachanga.domains.DadoBancario;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.dtos.DadoBancarioTO;

@Component(value = "dadoBancarioFactory")
public class DadoBancarioFactory {

	public DadoBancario getDadoBancario(DadoBancarioTO dadoTo, Festa festa) {
		return DadoBancarioBuilder.getInstance().codAgencia(dadoTo.getCodAgencia()).codBanco(dadoTo.getCodBanco())
				.codConta(dadoTo.getCodConta()).codDadosBancario(dadoTo.getCodDadosBancario()).festa(festa).build();
	}

	public DadoBancarioTO getDadoBancarioTO(DadoBancario dadoBancario) {
		return DadoBancarioTOBuilder.getInstance().codAgencia(dadoBancario.getCodAgencia())
				.codBanco(dadoBancario.getCodBanco()).codConta(dadoBancario.getCodConta())
				.codDadosBancario(dadoBancario.getCodDadosBancario()).codFesta(dadoBancario.getFesta().getCodFesta())
				.build();
	}

}
