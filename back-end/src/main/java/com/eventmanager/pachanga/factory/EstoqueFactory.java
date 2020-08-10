package com.eventmanager.pachanga.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.EstoqueBuilder;
import com.eventmanager.pachanga.builder.EstoqueTOBuilder;
import com.eventmanager.pachanga.domains.Estoque;
import com.eventmanager.pachanga.dtos.EstoqueTO;

@Component(value = "estoqueFactory")
public class EstoqueFactory {
	
	private EstoqueFactory() {
	}
	
	public Estoque getEstoque(EstoqueTO estoqueTo, boolean principal) {
		return EstoqueBuilder.getInstance().codEstoque(estoqueTo.getCodEstoque()).nomeEstoque(estoqueTo.getNomeEstoque())
				.principal(principal).build();
	}
	
	public EstoqueTO getEstoqueTO(Estoque estoque) {
		return EstoqueTOBuilder.getInstance().codEstoque(estoque.getCodEstoque()).nomeEstoque(estoque.getNomeEstoque())
				.principal(estoque.isPrincipal()).build();
	}
	
	public List<EstoqueTO> getListEstoqueTO(List<Estoque> estoques){
		return estoques.stream().map(e -> this.getEstoqueTO(e)).collect(Collectors.toList());
	}

}
