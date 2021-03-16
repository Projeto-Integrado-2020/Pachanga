package com.eventmanager.pachanga.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.ConvidadoTOBuilder;
import com.eventmanager.pachanga.domains.Convidado;
import com.eventmanager.pachanga.dtos.ConvidadoTO;

@Component(value = "convidadoFactory")
public class ConvidadoFactory {

	public List<ConvidadoTO> getConvidadosTO(List<Convidado> convidados) {
		List<ConvidadoTO> convidadosTo = new ArrayList<>();
		convidadosTo.addAll(convidados
				.stream().map(c -> ConvidadoTOBuilder.getInstance().email(c.getEmail())
						.codConvidado(c.getCodConvidado()).statusConvite(c.getStatusConvite()).build())
				.collect(Collectors.toList()));
		return convidadosTo;
	}

}
