package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eventmanager.pachanga.domains.Cupom;

@Repository
public interface CupomRepository extends JpaRepository<Cupom, Integer>{
	
	@Query(value = "SELECT c FROM Cupom c WHERE c.codCupom = :codCupom")
	public Cupom findCupomByCod(int codCupom);
	
	@Query(value = "SELECT c FROM Cupom c JOIN c.festa f WHERE c.nomeCupom = :nomeCupom AND f.codFesta = :codFesta")
	public List<Cupom> findCuponsByNomeAndFesta(String nomeCupom, int codFesta);
	
	@Query(value = "SELECT c FROM Cupom c JOIN c.festa f WHERE f.codFesta = :codFesta")
	public List<Cupom> findCuponsFesta(int codFesta);
	
	@Query(value = "SELECT NEXTVAL('seq_cupom');", nativeQuery = true)
	public int getNextValMySequence();

}
