package com.eventmanager.pachanga.domains;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
    
	@ManyToMany(mappedBy = "produtos",
			fetch = FetchType.LAZY)
	private Set<Estoque> estoques;
	
	
	public Set<Estoque> getEstoques() {
		return estoques;
	}

	public void setEstoques(Set<Estoque> estoques) {
		this.estoques = estoques;
	}

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
