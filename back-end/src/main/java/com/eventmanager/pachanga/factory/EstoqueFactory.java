package com.eventmanager.pachanga.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.EstoqueBuilder;
import com.eventmanager.pachanga.builder.EstoqueTOBuilder;
import com.eventmanager.pachanga.domains.Estoque;
import com.eventmanager.pachanga.dtos.EstoqueTO;

@Component(value = "estoqueFactory")
public class EstoqueFactory {
	
	@Autowired
	private ItemEstoqueFactory itemEstoqueFactory;

	private EstoqueFactory() {
	}

	public EstoqueTO getEstoqueTO(Estoque estoque) {
		EstoqueTOBuilder estoqueTO = EstoqueTOBuilder.getInstance().codEstoque(estoque.getCodEstoque()).nomeEstoque(estoque.getNomeEstoque())
				.principal(estoque.isPrincipal());
		if(!estoque.getItemEstoque().isEmpty()) {
			estoqueTO.itensEstoque(itemEstoqueFactory.getListItemEstoqueTO(estoque.getItemEstoque()));
		}
		return estoqueTO.build();
	}

	public List<EstoqueTO> getListEstoqueTO(List<Estoque> estoques){
		return estoques.stream().map(e -> this.getEstoqueTO(e)).collect(Collectors.toList());
	}

	public Estoque getEstoque(String nomeEstoque, boolean principal) {
		return EstoqueBuilder.getInstance().nomeEstoque(nomeEstoque).principal(principal).build();
	}

}
