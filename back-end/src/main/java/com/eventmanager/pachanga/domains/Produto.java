package com.eventmanager.pachanga.domains;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto {
	@Id
	@Column(name = "cod_produto")
	private int codProduto;
	
	@Column(name = "cod_festa")
    private int codFesta;
    
	@Column(name = "preco_medio")
	private BigDecimal precoMedio;
    
    @Column(name = "marca")
    private String marca;
    
    @Column(name = "dose")
    private Boolean dose;
    
    @Column(name = "quant_doses")
    private Integer quantDoses;
    
    @OneToMany(fetch = FetchType.LAZY,
			mappedBy = "produto")
	private Set<ItemEstoque> itemEstoque;

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

	public Set<ItemEstoque> getItemEstoque() {
		return itemEstoque;
	}

	public void setItemEstoque(Set<ItemEstoque> itemEstoque) {
		this.itemEstoque = itemEstoque;
	}

	public Boolean getDose() {
		return dose;
	}

	public void setDose(Boolean dose) {
		this.dose = dose;
	}

	public Integer getQuantDoses() {
		return quantDoses;
	}

	public void setQuantDoses(Integer quantDoses) {
		this.quantDoses = quantDoses;
	}
    
}
