package com.eventmanager.pachanga.builder;

import java.time.LocalDateTime;
import java.util.List;

import com.eventmanager.pachanga.dtos.CategoriaTO;
import com.eventmanager.pachanga.dtos.ConvidadoTO;
import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.dtos.UsuarioTO;

public class FestaTOBuilder {

	private int codFesta;
	private String nomeFesta;
	private String statusFesta; // Inicializado(I), Finalizado(F), Preparacao(P)
	private String organizador; // responsável da festa
	private LocalDateTime horarioInicioFesta;
	private LocalDateTime horarioFimFesta;
	private String descricaoFesta;
	private String codEnderecoFesta; //Url do local, talvez mude
	private String descOrganizador;
	private LocalDateTime horarioFimFestaReal;
	private int quantidadeParticipantes;
	private List<UsuarioTO> usuarios;
	private CategoriaTO categoriaPrimaria;
	private CategoriaTO categoriaSecundaria;
	private List<ConvidadoTO> convidados;
	private String imagem;

	public static FestaTOBuilder getInstance() {
		return new FestaTOBuilder();
	}

	public FestaTOBuilder codFesta(int codFesta) {
		this.codFesta = codFesta;
		return this;
	}

	public FestaTOBuilder nomeFesta(String nomeFesta) {
		this.nomeFesta = nomeFesta;
		return this;
	}

	public FestaTOBuilder statusFesta(String statusFesta) {
		this.statusFesta = statusFesta;
		return this;
	}

	public FestaTOBuilder organizador(String organizador) {
		this.organizador = organizador;
		return this;
	}

	public FestaTOBuilder horarioInicioFesta(LocalDateTime horarioInicioFesta) {
		this.horarioInicioFesta = horarioInicioFesta;
		return this;
	}

	public FestaTOBuilder horarioFimFesta(LocalDateTime horarioFimFesta) {
		this.horarioFimFesta = horarioFimFesta;
		return this;
	}

	public FestaTOBuilder descricaoFesta(String descricaoFesta) {
		this.descricaoFesta = descricaoFesta;
		return this;
	}

	public FestaTOBuilder codEnderecoFesta(String codEnderecoFesta) {
		this.codEnderecoFesta = codEnderecoFesta;
		return this;
	}

	public FestaTOBuilder descOrganizador(String descOrganizador) {
		this.descOrganizador = descOrganizador;
		return this;
	}

	public FestaTOBuilder horarioFimFestaReal(LocalDateTime horarioFimFestaReal) {
		this.horarioFimFestaReal = horarioFimFestaReal;
		return this;
	}

	public FestaTOBuilder quantidadeParticipantes(int quantidadeParticipantes) {
		this.quantidadeParticipantes = quantidadeParticipantes;
		return this;
	}

	public FestaTOBuilder usuarios(List<UsuarioTO> usuarios) {
		this.usuarios = usuarios;
		return this;
	}

	public FestaTOBuilder categoriaPrimaria(CategoriaTO categoriaPrimaria) {
		this.categoriaPrimaria = categoriaPrimaria;
		return this;
	}
	public FestaTOBuilder categoriaSecundaria(CategoriaTO categoriaSecundaria) {
		this.categoriaSecundaria = categoriaSecundaria;
		return this;
	}

	public FestaTOBuilder convidados(List<ConvidadoTO> convidados) {
		this.convidados = convidados;
		return this;
	}
	
	public FestaTOBuilder imagem(String imagem) {
		this.imagem = imagem;
		return this;
	}

	public FestaTO build() {
		FestaTO festaTo = new FestaTO();
		festaTo.setCodEnderecoFesta(codEnderecoFesta);
		festaTo.setCodFesta(codFesta);
		festaTo.setDescOrganizador(descOrganizador);
		festaTo.setDescricaoFesta(descricaoFesta);
		festaTo.setHorarioInicioFesta(horarioInicioFesta);
		festaTo.setHorarioFimFesta(horarioFimFesta);
		festaTo.setNomeFesta(nomeFesta);
		festaTo.setOrganizador(organizador);
		festaTo.setStatusFesta(statusFesta);
		festaTo.setHorarioFimFestaReal(horarioFimFestaReal);
		festaTo.setQuantidadeParticipantes(quantidadeParticipantes);
		festaTo.setUsuarios(usuarios);
		festaTo.setCategoriaPrimaria(categoriaPrimaria);
		festaTo.setCategoriaSecundaria(categoriaSecundaria);
		festaTo.setConvidados(convidados);
		festaTo.setImagem(imagem);
		return festaTo;
	}

}
