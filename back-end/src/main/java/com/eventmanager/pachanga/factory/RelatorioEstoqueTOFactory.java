package com.eventmanager.pachanga.factory;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.RelatorioEstoqueTOBuilder;
import com.eventmanager.pachanga.dtos.RelatorioEstoqueTO;

@Component(value = "relatorioEstoqueTOFactory")
public class RelatorioEstoqueTOFactory {

	public RelatorioEstoqueTO getRelatorioEstoque(String nomeEstoque, String nomeProduto,
			Map<LocalDateTime, Integer> quantidadeHora) {
		return RelatorioEstoqueTOBuilder.getInstance().nomeEstoque(nomeEstoque).nomeProduto(nomeProduto)
				.quantidadeHora(quantidadeHora).build();

	}

}
