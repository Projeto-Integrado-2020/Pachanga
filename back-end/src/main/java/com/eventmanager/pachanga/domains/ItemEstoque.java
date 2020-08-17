package com.eventmanager.pachanga.domains;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "produto_x_estoque")
public class ItemEstoque implements Serializable{
	@Id
	@Column(name = "cod_produto")
	private int codProduto;
	
	@Id
	@Column(name = "cod_estoque")
	private int codEstoque;
	
	@Column(name = "cod_festa")
	private int codFesta;
	
	@Column(name = "quantidade_max")
	private int quantidadeMax;
	
	@Column(name = "quantiadade_atual")
	private int quantiadadeAtual;
	
	@Column(name = "porcentagem_min")
	private int porcentagemMin;

	public int getCodProduto() {
		return codProduto;
	}

	public void setCodProduto(int codProduto) {
		this.codProduto = codProduto;
	}

	public int getCodEstoque() {
		return codEstoque;
	}

	public void setCodEstoque(int codEstoque) {
		this.codEstoque = codEstoque;
	}

	public int getCodFesta() {
		return codFesta;
	}

	public void setCodFesta(int codFesta) {
		this.codFesta = codFesta;
	}

	public int getQuantidadeMax() {
		return quantidadeMax;
	}

	public void setQuantidadeMax(int quantidadeMax) {
		this.quantidadeMax = quantidadeMax;
	}

	public int getQuantiadadeAtual() {
		return quantiadadeAtual;
	}

	public void setQuantiadadeAtual(int quantiadadeAtual) {
		this.quantiadadeAtual = quantiadadeAtual;
	}

	public int getPorcentagemMin() {
		return porcentagemMin;
	}

	public void setPorcentagemMin(int porcentagemMin) {
		this.porcentagemMin = porcentagemMin;
	}
	
	

}
