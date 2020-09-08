package com.eventmanager.pachanga.factory;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.NotificacaoEstoqueTOBuilder;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.ItemEstoque;
import com.eventmanager.pachanga.domains.Produto;
import com.eventmanager.pachanga.dtos.NotificacaoEstoqueTO;

@Component(value = "notificacaoEstoqueTOFactory")
public class NotificacaoEstoqueTOFactory {

	public NotificacaoEstoqueTO getNotificacaoEstoqueTO(ItemEstoque itemEstoque, Festa festa) {
		Produto produto = itemEstoque.getProduto();
		int quantidadeAtual = itemEstoque.getQuantidadeAtual();
		if(produto.getDose().booleanValue()) {
			quantidadeAtual = (int)Math.ceil((double)quantidadeAtual / produto.getQuantDoses());
		}
		return NotificacaoEstoqueTOBuilder.getInstance().nomeEstoque(itemEstoque.getEstoque().getNomeEstoque()).nomeFesta(festa.getNomeFesta())
				.nomeProduto(itemEstoque.getProduto().getMarca()).quantAtual(quantidadeAtual).build();
	}

}
