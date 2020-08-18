package com.eventmanager.pachanga.factory;

import com.eventmanager.pachanga.builder.ItemEstoqueBuilder;
import com.eventmanager.pachanga.builder.ItemEstoqueTOBuilder;
import com.eventmanager.pachanga.domains.ItemEstoque;
import com.eventmanager.pachanga.dtos.ItemEstoqueTO;

public class ItemEstoqueFactory {
	
	private ItemEstoqueFactory() {
	}

	public static ItemEstoque getItemEstoque(ItemEstoqueTO ItemEstoqueTO) {
		ItemEstoqueBuilder itemEstoqueBuilder = ItemEstoqueBuilder.getInstance()
																	  .codProduto(ItemEstoqueTO.getCodProduto())
																	  .codEstoque(ItemEstoqueTO.getCodEstoque())
																	  .codFesta(ItemEstoqueTO.getCodFesta())
																	  .quantidadeMax(ItemEstoqueTO.getQuantidadeMax())
																	  .quantidadeAtual(ItemEstoqueTO.getQuantidadeAtual())
																	  .porcentagemMin(ItemEstoqueTO.getPorcentagemMin());
		
		
		return itemEstoqueBuilder.build();
	}
	
	public static ItemEstoqueTO getItemEstoqueTO(ItemEstoque ItemEstoque) {
		ItemEstoqueTOBuilder itemEstoqueTOBuilder = ItemEstoqueTOBuilder.getInstance()
																	  .codProduto(ItemEstoque.getCodProduto())
																	  .codEstoque(ItemEstoque.getCodEstoque())
																	  .codFesta(ItemEstoque.getCodFesta())
																	  .quantidadeMax(ItemEstoque.getQuantidadeMax())
																	  .quantidadeAtual(ItemEstoque.getQuantidadeAtual())
																	  .porcentagemMin(ItemEstoque.getPorcentagemMin());
		
		
		return itemEstoqueTOBuilder.build();
	}
}
