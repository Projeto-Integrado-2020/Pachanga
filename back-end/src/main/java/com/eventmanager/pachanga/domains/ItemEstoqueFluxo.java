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
	
	@Column(name = "nome_produto")
	private String nomeProduto;
	
	@Column(name = "cod_estoque")
	private Integer codEstoque;
	
	@Column(name = "nome_estoque")
	private String nomeEstoque;
	
	@Column(name = "cod_festa")
	private Integer codFesta;
	
	@Column(name = "quantidade_estoque")
	private Integer quantidadeEstoque;
	
	@Column(name = "quantidade_perda")
	private int quantPerda;

	@Column(name = "dose")
	private Boolean dose;
	
	@Column(name = "dt_horario")
	private LocalDateTime dataHorario;
	
	@Column(name= "cod_usuario_resp_perda")
	private Integer codUsuarioRespPerda;
	
	public ItemEstoqueFluxo() {
	}
	
	public ItemEstoqueFluxo(ItemEstoque itemEstoque,LocalDateTime dataHorario, Integer codHistorico, boolean dose, Integer codUsuarioRespPerda) {
		this.codHistorico = codHistorico;
		this.codProduto = itemEstoque.getProduto().getCodProduto();
		this.nomeProduto = itemEstoque.getProduto().getMarca();
		this.codEstoque = itemEstoque.getEstoque().getCodEstoque();
		this.nomeEstoque = itemEstoque.getEstoque().getNomeEstoque();
		this.codFesta = itemEstoque.getCodFesta();
		this.quantidadeEstoque = itemEstoque.getQuantidadeAtual();
		this.dataHorario = dataHorario;
		this.quantPerda = itemEstoque.getQuantPerda();
		this.dose = dose;
		this.codUsuarioRespPerda = codUsuarioRespPerda;
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

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getNomeEstoque() {
		return nomeEstoque;
	}

	public void setNomeEstoque(String nomeEstoque) {
		this.nomeEstoque = nomeEstoque;
	}

	public Boolean getDose() {
		return dose;
	}

	public void setDose(Boolean dose) {
		this.dose = dose;
	}

	public int getCodUsuarioRespPerda() {
		return codUsuarioRespPerda;
	}

	public void setCodUsuarioRespPerda(int codUsuarioRespPerda) {
		this.codUsuarioRespPerda = codUsuarioRespPerda;
	}
	
}
