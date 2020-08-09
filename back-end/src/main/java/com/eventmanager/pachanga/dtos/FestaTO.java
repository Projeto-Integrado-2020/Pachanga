package com.eventmanager.pachanga.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class FestaTO {
	
	private int codFesta;
	private String nomeFesta;
	private String statusFesta; // Inicializado(I), Finalizado(F)
	private String organizador; // responsável da festa
	private LocalDateTime horarioInicioFesta;
	private LocalDateTime horarioFimFesta;
	private String descricaoFesta;
	private String codEnderecoFesta; //Url do local, talvez mude
	private String descOrganizador;
	private LocalDateTime horarioFimFestaReal;
	private String funcionalidade;
	private int quantidadeParticipantes;
	private List<UsuarioTO> usuarios;
	private int codPrimaria;//codigo da categoria enviada pelo front
	private int codSecundaria;//codigo da categoria enviada pelo front
	private CategoriaTO categoriaPrimaria;
	private CategoriaTO categoriaSecundaria;
	private Boolean isOrganizador;
	private List<ConvidadoTO> convidados;
	
	public int getCodFesta() {
		return codFesta;
	}
	public void setCodFesta(int codFesta) {
		this.codFesta = codFesta;
	}
	public String getNomeFesta() {
		return nomeFesta;
	}
	public void setNomeFesta(String nomeFesta) {
		this.nomeFesta = nomeFesta;
	}
	public String getStatusFesta() {
		return statusFesta;
	}
	public void setStatusFesta(String statusFesta) {
		this.statusFesta = statusFesta;
	}
	public String getOrganizador() {
		return organizador;
	}
	public void setOrganizador(String organizador) {
		this.organizador = organizador;
	}
	public LocalDateTime getHorarioInicioFesta() {
		return horarioInicioFesta;
	}
	public void setHorarioInicioFesta(LocalDateTime horarioInicioFesta) {
		this.horarioInicioFesta = horarioInicioFesta;
	}
	public LocalDateTime getHorarioFimFesta() {
		return horarioFimFesta;
	}
	public void setHorarioFimFesta(LocalDateTime horarioFimFesta) {
		this.horarioFimFesta = horarioFimFesta;
	}
	public String getDescricaoFesta() {
		return descricaoFesta;
	}
	public void setDescricaoFesta(String descricaoFesta) {
		this.descricaoFesta = descricaoFesta;
	}
	public String getCodEnderecoFesta() {
		return codEnderecoFesta;
	}
	public void setCodEnderecoFesta(String codEnderecoFesta) {
		this.codEnderecoFesta = codEnderecoFesta;
	}
	public String getDescOrganizador() {
		return descOrganizador;
	}
	public void setDescOrganizador(String descOrganizador) {
		this.descOrganizador = descOrganizador;
	}
	public LocalDateTime getHorarioFimFestaReal() {
		return horarioFimFestaReal;
	}
	public void setHorarioFimFestaReal(LocalDateTime horarioFimFestaReal) {
		this.horarioFimFestaReal = horarioFimFestaReal;
	}
	public String getFuncionalidade() {
		return funcionalidade;
	}
	public void setFuncionalidade(String funcionalidade) {
		this.funcionalidade = funcionalidade;
	}
	public int getQuantidadeParticipantes() {
		return quantidadeParticipantes;
	}
	public void setQuantidadeParticipantes(int quantidadeParticipantes) {
		this.quantidadeParticipantes = quantidadeParticipantes;
	}
	public List<UsuarioTO> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<UsuarioTO> usuarios) {
		this.usuarios = usuarios;
	}
	public int getCodPrimaria() {
		return codPrimaria;
	}
	public void setCodPrimaria(int codPrimaria) {
		this.codPrimaria = codPrimaria;
	}
	public int getCodSecundaria() {
		return codSecundaria;
	}
	public void setCodSecundaria(int codSecundaria) {
		this.codSecundaria = codSecundaria;
	}
	public CategoriaTO getCategoriaPrimaria() {
		return categoriaPrimaria;
	}
	public void setCategoriaPrimaria(CategoriaTO categoriaPrimaria) {
		this.categoriaPrimaria = categoriaPrimaria;
	}
	public CategoriaTO getCategoriaSecundaria() {
		return categoriaSecundaria;
	}
	public void setCategoriaSecundaria(CategoriaTO categoriaSecundaria) {
		this.categoriaSecundaria = categoriaSecundaria;
	}
	public Boolean getIsOrganizador() {
		return isOrganizador;
	}
	public void setIsOrganizador(Boolean isOrganizador) {
		this.isOrganizador = isOrganizador;
	}
	public List<ConvidadoTO> getConvidados() {
		return convidados;
	}
	public void setConvidados(List<ConvidadoTO> convidados) {
		this.convidados = convidados;
	}

}
