package com.eventmanager.pachanga.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.eventmanager.pachanga.idclass.ItemEstoqueId;

@Entity
@Table(name = "produto_x_estoque")
@IdClass(ItemEstoqueId.class)
public class ItemEstoque{
	
	@Id
	@ManyToOne
	@JoinColumn(name = "cod_produto")
	private Produto produto;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "cod_estoque")
	private Estoque estoque;
	
	@Column(name = "cod_festa")
	private int codFesta;
	
	@Column(name = "quantidade_max")
	private int quantidadeMax;
	
	@Column(name = "quantidade_atual")
	private int quantidadeAtual;
	
	@Column(name = "porcentagem_min")
	private int porcentagemMin;
	
	@Column(name = "quantidade_perda")
	private int quantPerda;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public int getQuantidadeMax() {
		return quantidadeMax;
	}

	public void setQuantidadeMax(int quantidadeMax) {
		this.quantidadeMax = quantidadeMax;
	}

	public int getQuantidadeAtual() {
		return quantidadeAtual;
	}

	public void setQuantidadeAtual(int quantiadadeAtual) {
		this.quantidadeAtual = quantiadadeAtual;
	}

	public int getPorcentagemMin() {
		return porcentagemMin;
	}

	public void setPorcentagemMin(int porcentagemMin) {
		this.porcentagemMin = porcentagemMin;
	}

	public int getCodFesta() {
		return codFesta;
	}

	public void setCodFesta(int codFesta) {
		this.codFesta = codFesta;
	}
	
	public int getQuantPerda() {
		return quantPerda;
	}

	public void setQuantPerda(int quantPerda) {
		this.quantPerda = quantPerda;
	}

	public boolean quantidadeAtualAbaixoMin() {
		return Math.round(quantidadeMax * porcentagemMin * 0.01) > quantidadeAtual;
	}

}
