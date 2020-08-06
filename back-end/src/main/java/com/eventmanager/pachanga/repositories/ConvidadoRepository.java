package com.eventmanager.pachanga.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.Convidado;
import com.eventmanager.pachanga.domains.Usuario;

@Repository
public interface ConvidadoRepository extends CrudRepository<Convidado, Integer>{

	@Query(value = "SELECT u FROM Grupo g JOIN g.usuarios u WHERE g.codGrupo = :codGrupo AND u.codUsuario = :codUsuario ")
	public Usuario findUsuarioNoGrupo(@Param("codGrupo") int codGrupo, @Param("codUsuario") int codUsuario);
	
	@Modifying
	@Query(value = "INSERT INTO usuario_x_grupo(cod_usuario, cod_grupo) VALUES(:codUsuario, :codGrupo)", nativeQuery = true)
	public void saveUsuarioGrupo(@Param("codUsuario") int codUsuario, @Param("codGrupo") int codGrupo);
}
