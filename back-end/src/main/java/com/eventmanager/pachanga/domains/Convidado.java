package com.eventmanager.pachanga.domains;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "convidado")
public class Convidado {
	
	@Id
	@Column(name = "cod_convidado")
	private int codConvidado;
	
	@Column(name = "email")
	private String email;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "convidado")
	private Set<NotificacaoConvidado> notificacaoConvidado;
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	@JoinTable(name = "convidado_x_grupo",
    joinColumns = @JoinColumn(name ="cod_convidado"),
    inverseJoinColumns = @JoinColumn(name = "cod_grupo"))
    private Set<Grupo> grupos;
	
	public Convidado(int codConvidado, String email) {
		this.email = email;
		this.codConvidado = codConvidado;
	}
	
	public Convidado() {
	}
    
	public int getCodConvidado() {
		return codConvidado;
	}

	public void setCodConvidado(int codConvidado) {
		this.codConvidado = codConvidado;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(Set<Grupo> grupos) {
		this.grupos = grupos;
	}
	
}
