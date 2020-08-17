package com.eventmanager.pachanga.factory;

import com.eventmanager.pachanga.builder.ProdutoBuilder;
import com.eventmanager.pachanga.builder.ProdutoTOBuilder;
import com.eventmanager.pachanga.domains.Produto;
import com.eventmanager.pachanga.dtos.ProdutoTO;

public class ProdutoFactory {
	private ProdutoFactory() {
	}
	
	public static ProdutoTO getProdutoTO(Produto produto) {
		ProdutoTOBuilder produtoTOBuilder = ProdutoTOBuilder.getInstance()
												.codProduto(produto.getCodProduto())
												.codFesta(produto.getCodFesta())
												.precoMedio(produto.getPrecoMedio())
												.marca(produto.getMarca());
		
		return produtoTOBuilder.build();
	}
	
	public static Produto getProduto(ProdutoTO produtoTO) {
		ProdutoBuilder produtoBuilder = ProdutoBuilder.getInstance()
													  .codProduto(produtoTO.getCodProduto())
													  .codFesta(produtoTO.getCodProduto())
													  .precoMedio(produtoTO.getPrecoMedio())
													  .marca(produtoTO.getMarca());
		
		return produtoBuilder.build();
	}	
}
