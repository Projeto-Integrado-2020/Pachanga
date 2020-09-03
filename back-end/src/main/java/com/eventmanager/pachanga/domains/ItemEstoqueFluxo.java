package com.eventmanager.pachanga.domains;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "produto_X_estoque_fluxo")
public class ItemEstoqueFluxo {
	
	@Id
	@Column(name="cod_historico")
	private Integer codHistorico;
	
	@Column(name = "cod_produto")
	private Integer codProduto;
	
	@Column(name = "cod_estoque")
	private Integer codEstoque;
	
	@Column(name = "cod_festa")
	private Integer codFesta;
	
	@Column(name = "quantidade_estoque")
	private Integer quantidadeEstoque;
	
	@Column(name = "quantidade_perda")
	private int quantPerda;
	
	@Column(name = "dt_horario")
	private LocalDateTime dataHorario;
	
	public ItemEstoqueFluxo() {
	}
	
	public ItemEstoqueFluxo(ItemEstoque itemEstoque,LocalDateTime dataHorario, Integer codHistorico) {
		this.codHistorico = codHistorico;
		this.codProduto = itemEstoque.getProduto().getCodProduto();
		this.codEstoque = itemEstoque.getEstoque().getCodEstoque();
		this.codFesta = itemEstoque.getCodFesta();
		this.quantidadeEstoque = itemEstoque.getQuantidadeAtual();
		this.dataHorario = dataHorario;
		this.quantPerda = itemEstoque.getQuantPerda();
	}

	public Integer getCodHistorico() {
		return codHistorico;
	}

	public void setCodHistorico(Integer codHistorico) {
		this.codHistorico = codHistorico;
	}

	public Integer getCodProduto() {
		return codProduto;
	}

	public void setCodProduto(Integer codProduto) {
		this.codProduto = codProduto;
	}

	public Integer getCodEstoque() {
		return codEstoque;
	}

	public void setCodEstoque(Integer codEstoque) {
		this.codEstoque = codEstoque;
	}

	public Integer getCodFesta() {
		return codFesta;
	}

	public void setCodFesta(Integer codFesta) {
		this.codFesta = codFesta;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public LocalDateTime getDataHorario() {
		return dataHorario;
	}

	public void setDataHorario(LocalDateTime dataHorario) {
		this.dataHorario = dataHorario;
	}

	public int getQuantPerda() {
		return quantPerda;
	}

	public void setQuantPerda(int quantPerda) {
		this.quantPerda = quantPerda;
	}

}
