package com.eventmanager.pachanga.domains;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_x_festa_x_grupo",
    joinColumns =  @JoinColumn(name = "cod_festa") ,	
    inverseJoinColumns =  @JoinColumn(name = "cod_usuario"))
	private Set<Usuario> usuarios = new HashSet<Usuario>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_x_festa_x_grupo",
    joinColumns =  @JoinColumn(name = "cod_festa") ,	
    inverseJoinColumns =  @JoinColumn(name = "cod_grupo"))
	private Set<Grupo> grupos = new HashSet<Grupo>();
	
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
	public Set<Usuario> getUsuario() {
		return usuarios;
	}
	public void setUsuario(Usuario usuario) {
		this.usuarios.add(usuario);
	}
	
	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	public Set<Grupo> getGrupos() {
		return grupos;
	}
	public void setGrupos(Set<Grupo> grupos) {
		this.grupos = grupos;
	}
	public void setGrupo(Grupo grupo) {
		this.grupos.add(grupo);
	}
	
}
