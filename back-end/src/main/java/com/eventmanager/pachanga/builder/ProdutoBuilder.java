package com.eventmanager.pachanga.builder;

import java.math.BigDecimal;

import com.eventmanager.pachanga.domains.Produto;

public class ProdutoBuilder {
	
	private int codProduto;
    private int codFesta;
	private BigDecimal precoMedio;
    private String marca;
    private boolean dose;
    private int quantDoses;
    
	public static ProdutoBuilder getInstance() {
		return new ProdutoBuilder();
	}

	public ProdutoBuilder codProduto(int codProduto) {
		this.codProduto = codProduto;
		return this;
	}
	
	public ProdutoBuilder dose(boolean dose) {
		this.dose = dose;
		return this;
	}
	
	public ProdutoBuilder quantDoses(int quantDoses) {
		this.quantDoses = quantDoses;
		return this;
	}

	public ProdutoBuilder codFesta(int codFesta) {
		this.codFesta = codFesta;
		return this;
	}

	public ProdutoBuilder precoMedio(BigDecimal precoMedio) {
		this.precoMedio = precoMedio;
		return this;
	}

	public ProdutoBuilder marca(String marca) {
		this.marca = marca;
		return this;
	}
	
	public Produto build() {
		Produto produto = new Produto();
		produto.setCodFesta(codFesta);
		produto.setCodProduto(codProduto);
		produto.setMarca(marca);
		produto.setPrecoMedio(precoMedio);
		produto.setDose(dose);
		produto.setQuantDoses(quantDoses);
		return produto;
	}
	
}
