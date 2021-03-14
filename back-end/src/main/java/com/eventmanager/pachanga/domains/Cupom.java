package com.eventmanager.pachanga.domains;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cupom")
public class Cupom {
	
	@Id
	@Column(name = "cod_cupom")
    private int codCupom;
	
	@Column(name = "nome_cupom")
	private String nomeCupom;
	
	@ManyToOne
	@JoinColumn(name = "cod_festa")
	private Festa festa;
	
	@Column(name = "preco_desconto")
	private float precoDesconto;
	
	@Column(name = "porcentagem_desconto")
	private int porcentagemDesc;
	
	@Column(name = "tip_desconto")
	private String tipoDesconto;
	
	@Column(name = "data_ini_desconto")
	private LocalDateTime dataIniDesconto;
	
	@Column(name = "data_fim_desconto")
	private LocalDateTime dataFimDesconto;

	public int getCodCupom() {
		return codCupom;
	}

	public void setCodCupom(int codCupom) {
		this.codCupom = codCupom;
	}

	public Festa getFesta() {
		return festa;
	}

	public void setFesta(Festa festa) {
		this.festa = festa;
	}

	public float getPrecoDesconto() {
		return precoDesconto;
	}

	public void setPrecoDesconto(float precoDesconto) {
		this.precoDesconto = precoDesconto;
	}

	public String getNomeCupom() {
		return nomeCupom;
	}

	public void setNomeCupom(String nomeCupom) {
		this.nomeCupom = nomeCupom;
	}

	public int getPorcentagemDesc() {
		return porcentagemDesc;
	}

	public void setPorcentagemDesc(int porcentagemDesc) {
		this.porcentagemDesc = porcentagemDesc;
	}

	public String getTipoDesconto() {
		return tipoDesconto;
	}

	public void setTipoDesconto(String tipoDesconto) {
		this.tipoDesconto = tipoDesconto;
	}

	public LocalDateTime getDataIniDesconto() {
		return dataIniDesconto;
	}

	public void setDataIniDesconto(LocalDateTime dataIniDesconto) {
		this.dataIniDesconto = dataIniDesconto;
	}

	public LocalDateTime getDataFimDesconto() {
		return dataFimDesconto;
	}

	public void setDataFimDesconto(LocalDateTime dataFimDesconto) {
		this.dataFimDesconto = dataFimDesconto;
	}
}
