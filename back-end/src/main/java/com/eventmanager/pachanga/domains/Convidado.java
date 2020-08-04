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
import javax.persistence.Table;

@Entity
@Table(name = "convidado")
public class Convidado {
	
	@Id
	@Column(name = "cod_convidado")
	private int codConvidado;
	
	@Column(name = "email")
	private int email;
	
	@ManyToMany(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	@JoinTable(name = "convidado_x_grupo",
    joinColumns = @JoinColumn(name ="cod_convidado"),
    inverseJoinColumns = @JoinColumn(name = "cod_grupo"))
    private Set<Grupo> grupos;
	
	@ManyToMany(mappedBy = "convidados",
			fetch = FetchType.LAZY)
	private Set<Notificacao> notificacoes;
    
	public int getCodConvidado() {
		return codConvidado;
	}

	public void setCodConvidado(int codConvidado) {
		this.codConvidado = codConvidado;
	}

	public int getEmail() {
		return email;
	}

	public void setEmail(int email) {
		this.email = email;
	}

	public Set<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(Set<Grupo> grupos) {
		this.grupos = grupos;
	}
	
}
