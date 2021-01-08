package com.eventmanager.pachanga.factory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.InformacoesRelatorioEstoqueTOBuilder;
import com.eventmanager.pachanga.builder.RelatorioEstoqueTOBuilder;
import com.eventmanager.pachanga.dtos.InformacoesRelatorioEstoqueTO;
import com.eventmanager.pachanga.dtos.RelatorioEstoqueTO;

@Component(value = "relatorioEstoqueTOFactory")
public class RelatorioEstoqueTOFactory {

	public RelatorioEstoqueTO getRelatorioEstoque(String nomeEstoque,
			List<InformacoesRelatorioEstoqueTO> informacoesEstoque) {
		return RelatorioEstoqueTOBuilder.getInstance().nomeEstoque(nomeEstoque).informacoesEstoque(informacoesEstoque)
				.build();

	}

	public InformacoesRelatorioEstoqueTO getInformacoesRelatorioEstoque(String nomeProduto,
			Map<LocalDateTime, Integer> quantidadeHora) {
		return InformacoesRelatorioEstoqueTOBuilder.getInstance().nomeProduto(nomeProduto)
				.quantidadeHora(quantidadeHora).build();
	}

	public InformacoesRelatorioEstoqueTO getInformacaoRelatorioConsumo(String nomeProduto, Integer quantidadeConsumida) {
		return InformacoesRelatorioEstoqueTOBuilder.getInstance().nomeProduto(nomeProduto).quantidadeConsumo(quantidadeConsumida).build();
	}

}
