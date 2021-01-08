package com.eventmanager.pachanga.dtos;

import java.util.ArrayList;
import java.util.List;

public class RelatorioEstoqueTO {

	private String nomeEstoque;

	private List<InformacoesRelatorioEstoqueTO> informacoesEstoque = new ArrayList<>();

	public String getNomeEstoque() {
		return nomeEstoque;
	}

	public void setNomeEstoque(String nomeEstoque) {
		this.nomeEstoque = nomeEstoque;
	}

	public List<InformacoesRelatorioEstoqueTO> getInformacoesEstoque() {
		return informacoesEstoque;
	}
	
	public void setInformacoesEstoque(List<InformacoesRelatorioEstoqueTO> informacoesEstoque) {
		this.informacoesEstoque.addAll(informacoesEstoque);
	}
}
