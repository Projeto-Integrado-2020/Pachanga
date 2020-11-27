package com.eventmanager.pachanga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eventmanager.pachanga.domains.DadoBancario;

public interface DadoBancarioRepository extends JpaRepository<DadoBancario, Integer>{
	
	@Query(value = "SELECT NEXTVAL('seq_dado_bancario');", nativeQuery = true)
	public int getNextValMySequence();

	@Query(value = "SELECT db FROM DadoBancario db JOIN db.festa f WHERE f.codFesta = :codFesta")
	public DadoBancario findDadosBancariosFesta(int codFesta);

	@Query(value = "SELECT db FROM DadoBancario db WHERE db.codConta = :codConta")
	public DadoBancario findDadoByConta(int codConta);
	
	@Query(value = "SELECT db FROM DadoBancario db WHERE db.codDadosBancario = :codDadoBancario")
	public DadoBancario findDadoByCodBancario(int codDadoBancario);

}
