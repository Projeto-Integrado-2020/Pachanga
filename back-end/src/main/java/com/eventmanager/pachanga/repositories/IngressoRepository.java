package com.eventmanager.pachanga.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.eventmanager.pachanga.domains.Ingresso;

public interface IngressoRepository extends JpaRepository<Ingresso, Integer>{

	@Query(value = "SELECT i FROM Ingresso i JOIN i.lote l WHERE l.codLote = :codLote")
	List<Ingresso> findIngressosLote(int codLote);
	
	@Query(value = "SELECT i FROM Ingresso i JOIN i.usuario u WHERE u.codUsuario = :codUsuario")
	List<Ingresso> findIngressosUser(int codUsuario);
	
	@Query(value = "SELECT i FROM Ingresso i JOIN i.festa f WHERE f.codFesta = :codFesta")
	List<Ingresso> findIngressosFesta(int codFesta);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE Ingresso i SET i.statusIngresso = :statusIngresso, i.dataCheckin = :dataCheckin WHERE i.codIngresso = :codIngresso")
	Ingresso updateCheckin(int codIngresso, String statusIngresso, LocalDateTime dataCheckin);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE Ingresso i SET i.statusCompra = :statusCompra WHERE i.codIngresso = :codIngresso")
	Ingresso updateStatusCompra(int codIngresso, String statusCompra);
	

}
