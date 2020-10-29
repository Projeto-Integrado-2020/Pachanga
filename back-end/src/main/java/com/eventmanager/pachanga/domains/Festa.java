package com.eventmanager.pachanga.domains;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "festa")
public class Festa {
	
	@Id
	@Column(name = "cod_festa")
	private int codFesta;
	@Column(name = "nome")
	private String nomeFesta;
	@Column(name = "status_festa")
	private String statusFesta; // Inicializado(I), Finalizado(F)
	@Column(name = "organizador")
	private String organizador; // responsável da festa
	@Column(name = "horario_inicio")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime horarioInicioFesta;
	@Column(name = "horario_fim")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime horarioFimFesta;
	@Column(name = "descricao_festa")
	private String descricaoFesta;
	@Column(name = "cod_endereco")
	private String codEnderecoFesta; //Url do local, talvez mude
	@Column(name = "desc_organizador")
	private String descOrganizador;
	@Column(name = "horario_fim_real")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime horarioFimFestaReal;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "festa")
	private Set<Grupo> grupos;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "festa")
	private Set<CategoriasFesta> categoriaFesta;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "festa")
	private Set<Estoque> estoques;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "festa")
	private Set<AreaSegurancaProblema> areaSegurancaProblema;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "festa")
	private Set<Lote> lotes;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "festa")
	private Set<Ingresso> ingressos;
	
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
	public LocalDateTime getHorarioInicioFesta() {
		return horarioInicioFesta;
	}
	public void setHorarioInicioFesta(LocalDateTime horaioInicioFesta) {
		this.horarioInicioFesta = horaioInicioFesta;
	}
	public Set<Grupo> getGrupos() {
		return grupos;
	}
	public void setGrupos(Set<Grupo> grupos) {
		this.grupos = grupos;
	}
	public Set<CategoriasFesta> getCategoriaFesta() {
		return categoriaFesta;
	}
	public void setCategoriaFesta(Set<CategoriasFesta> categoriaFesta) {
		this.categoriaFesta = categoriaFesta;
	}
	
	public Set<Estoque> getEstoques() {
		return estoques;
	}
	
	public void setEstoques(Set<Estoque> estoques) {
		this.estoques = estoques;
	}
	
	public Set<AreaSegurancaProblema> getAreaSegurancaProblema() {
		return areaSegurancaProblema;
	}
	public void setAreaSegurancaProblema(Set<AreaSegurancaProblema> areaSegurancaProblema) {
		this.areaSegurancaProblema = areaSegurancaProblema;
	}
	
	public Set<Lote> getLotes() {
		return lotes;
	}
	
	public void setLotes(Set<Lote> lotes) {
		this.lotes = lotes;
	}
	
	public Set<Ingresso> getIngressos() {
		return ingressos;
	}
	public void setIngressos(Set<Ingresso> ingressos) {
		this.ingressos = ingressos;
	}
	public Boolean isOrganizador(int codUsuario) {
		for(Grupo grupo: this.grupos) {
			if(grupo.getOrganizador()) {
				long usuario = grupo.getUsuarios().stream().filter(u -> u.getCodUsuario() == codUsuario).count();
				if(usuario != 0l) {
					return true;
				}
			}
		}
		return false;
	}
	
}
