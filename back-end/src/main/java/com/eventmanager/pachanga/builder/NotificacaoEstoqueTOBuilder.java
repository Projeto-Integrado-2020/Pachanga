package com.eventmanager.pachanga.builder;

import com.eventmanager.pachanga.dtos.NotificacaoEstoqueTO;

public class NotificacaoEstoqueTOBuilder {

	private String nomeEstoque;

	private String nomeProduto;

	private String nomeFesta;

	private Integer quantAtual;
	
	public static NotificacaoEstoqueTOBuilder getInstance() {
		return new NotificacaoEstoqueTOBuilder();
	}

	public NotificacaoEstoqueTOBuilder nomeEstoque(String nomeEstoque) {
		this.nomeEstoque = nomeEstoque;
		return this;
	}

	public NotificacaoEstoqueTOBuilder nomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
		return this;
	}

	public NotificacaoEstoqueTOBuilder nomeFesta(String nomeFesta) {
		this.nomeFesta = nomeFesta;
		return this;
	}

	public NotificacaoEstoqueTOBuilder quantAtual(Integer quantAtual) {
		this.quantAtual = quantAtual;
		return this;
	}
	
	public NotificacaoEstoqueTO build() {
		NotificacaoEstoqueTO notificacaoEstoqueTo = new NotificacaoEstoqueTO();
		notificacaoEstoqueTo.setNomeEstoque(nomeEstoque);
		notificacaoEstoqueTo.setNomeFesta(nomeFesta);
		notificacaoEstoqueTo.setNomeProduto(nomeProduto);
		notificacaoEstoqueTo.setQuantAtual(quantAtual);
		return notificacaoEstoqueTo;
	}

}
