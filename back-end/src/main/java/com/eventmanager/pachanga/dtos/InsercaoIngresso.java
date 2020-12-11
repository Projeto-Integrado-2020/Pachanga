package com.eventmanager.pachanga.dtos;

import java.util.List;

public class InsercaoIngresso {

	private List<IngressoTO> listaIngresso;
	
	private InfoPagamentoBoletoTO infoPagamento;

	public List<IngressoTO> getListaIngresso() {
		return listaIngresso;
	}

	public void setListaIngresso(List<IngressoTO> listaIngresso) {
		this.listaIngresso = listaIngresso;
	}

	public InfoPagamentoBoletoTO getInfoPagamento() {
		return infoPagamento;
	}

	public void setInfoPagmaento(InfoPagamentoBoletoTO infoPagamento) {
		this.infoPagamento = infoPagamento;
	}
}
