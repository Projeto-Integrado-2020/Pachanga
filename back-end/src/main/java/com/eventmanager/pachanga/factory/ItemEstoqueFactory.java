package com.eventmanager.pachanga.factory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.ItemEstoqueBuilder;
import com.eventmanager.pachanga.builder.ItemEstoqueTOBuilder;
import com.eventmanager.pachanga.domains.Estoque;
import com.eventmanager.pachanga.domains.ItemEstoque;
import com.eventmanager.pachanga.domains.Produto;
import com.eventmanager.pachanga.dtos.ItemEstoqueTO;

@Component(value = "itemEstoqueFactory")
public class ItemEstoqueFactory {
	
	@Autowired
	private ProdutoFactory produtoFactory;
	
	ItemEstoqueFactory() {
	}

	public ItemEstoque getItemEstoque(ItemEstoqueTO itemEstoqueTO, Produto produto, Estoque estoque) {
		return ItemEstoqueBuilder.getInstance()
				  .produto(produto)
				  .estoque(estoque)
				  .codFesta(itemEstoqueTO.getCodFesta())
				  .quantidadeMax(itemEstoqueTO.getQuantidadeMax())
				  .quantidadeAtual(itemEstoqueTO.getQuantidadeAtual())
				  .porcentagemMin(itemEstoqueTO.getPorcentagemMin()).build();
	}
	
	public ItemEstoqueTO getItemEstoqueTO(ItemEstoque itemEstoque) {
		return ItemEstoqueTOBuilder.getInstance()
				  .codProduto(itemEstoque.getProduto().getCodProduto())
				  .codEstoque(itemEstoque.getEstoque().getCodEstoque())
				  .codFesta(itemEstoque.getCodFesta())
				  .quantidadeMax(itemEstoque.getQuantidadeMax())
				  .quantidadeAtual(itemEstoque.getQuantidadeAtual())
				  .porcentagemMin(itemEstoque.getPorcentagemMin())
				  .produto(produtoFactory.getProdutoTO(itemEstoque.getProduto())).build();
	}
	
	public List<ItemEstoqueTO> getListItemEstoqueTO(Set<ItemEstoque> itensEstoque){
		return itensEstoque.stream().map(ie -> this.getItemEstoqueTO(ie)).collect(Collectors.toList());
	}
}
