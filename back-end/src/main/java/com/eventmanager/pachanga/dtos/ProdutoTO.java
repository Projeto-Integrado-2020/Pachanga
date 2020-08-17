package com.eventmanager.pachanga.dtos;

import java.math.BigDecimal;

public class ProdutoTO {
	
	private int codProduto;
    private int codFesta;
	private BigDecimal precoMedio;
    private String marca;
	
    
    public int getCodProduto() {
		return codProduto;
	}
	public void setCodProduto(int codProduto) {
		this.codProduto = codProduto;
	}
	public int getCodFesta() {
		return codFesta;
	}
	public void setCodFesta(int codFesta) {
		this.codFesta = codFesta;
	}
	public BigDecimal getPrecoMedio() {
		return precoMedio;
	}
	public void setPrecoMedio(BigDecimal precoMedio) {
		this.precoMedio = precoMedio;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
    
    

}
