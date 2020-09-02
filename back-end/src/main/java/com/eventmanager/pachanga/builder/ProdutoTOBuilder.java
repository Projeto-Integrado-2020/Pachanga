package com.eventmanager.pachanga.builder;

import java.math.BigDecimal;

import com.eventmanager.pachanga.dtos.ProdutoTO;

public class ProdutoTOBuilder {
	
	private int codProduto;
    private int codFesta;
	private BigDecimal precoMedio;
    private String marca;
    private boolean dose;
    private int quantDoses;
    
	public static ProdutoTOBuilder getInstance() {
		return new ProdutoTOBuilder();
	}

	
	public ProdutoTOBuilder codProduto(int codProduto) {
		this.codProduto = codProduto;
		return this;
	}
	
	public ProdutoTOBuilder dose(boolean dose) {
		this.dose = dose;
		return this;
	}
	
	public ProdutoTOBuilder quantDoses(int quantDoses) {
		this.quantDoses = quantDoses;
		return this;
	}

	public ProdutoTOBuilder codFesta(int codFesta) {
		this.codFesta = codFesta;
		return this;
	}

	public ProdutoTOBuilder precoMedio(BigDecimal precoMedio) {
		this.precoMedio = precoMedio;
		return this;
	}

	public ProdutoTOBuilder marca(String marca) {
		this.marca = marca;
		return this;
	}
	
	public ProdutoTO build() {
		ProdutoTO produtoTO = new ProdutoTO();
		produtoTO.setCodFesta(codFesta);
		produtoTO.setCodProduto(codProduto);
		produtoTO.setMarca(marca);
		produtoTO.setPrecoMedio(precoMedio);
		produtoTO.setDose(dose);
		produtoTO.setQuantDoses(quantDoses);
		return produtoTO;
	}
	
}
