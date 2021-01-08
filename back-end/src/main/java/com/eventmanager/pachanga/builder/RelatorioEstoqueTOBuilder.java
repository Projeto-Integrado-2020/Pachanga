package com.eventmanager.pachanga.builder;

import java.util.ArrayList;
import java.util.List;

import com.eventmanager.pachanga.dtos.InformacoesRelatorioEstoqueTO;
import com.eventmanager.pachanga.dtos.RelatorioEstoqueTO;

public class RelatorioEstoqueTOBuilder {

	private String nomeEstoque;

	private List<InformacoesRelatorioEstoqueTO> informacoesEstoque = new ArrayList<>();
	
	public static RelatorioEstoqueTOBuilder getInstance() {
		return new RelatorioEstoqueTOBuilder();
	}

	public RelatorioEstoqueTOBuilder nomeEstoque(String nomeEstoque) {
		this.nomeEstoque = nomeEstoque;
		return this;
	}

	public RelatorioEstoqueTOBuilder informacoesEstoque(List<InformacoesRelatorioEstoqueTO> informacoesEstoque) {
		this.informacoesEstoque.addAll(informacoesEstoque);
		return this;
	}

	public RelatorioEstoqueTO build() {
		RelatorioEstoqueTO relatorioEstoque = new RelatorioEstoqueTO();
		relatorioEstoque.setNomeEstoque(nomeEstoque);
		relatorioEstoque.setInformacoesEstoque(informacoesEstoque);
		return relatorioEstoque;
	}

}
