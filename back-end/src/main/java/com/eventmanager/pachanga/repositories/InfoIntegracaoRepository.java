package com.eventmanager.pachanga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eventmanager.pachanga.domains.InfoIntegracao;

public interface InfoIntegracaoRepository extends JpaRepository<InfoIntegracao, Integer> {
	
	@Query(value = "SELECT NEXTVAL('seq_info_integracao');", nativeQuery = true)
	public int getNextValMySequence();

	@Query(value = "SELECT ini FROM InfoIntegracao ini JOIN ini.festa f WHERE f.codFesta = :codFesta")
	public List<InfoIntegracao> findAllInfosFesta(int codFesta);

	@Query(value="SELECT ini FROM InfoIntegracao ini JOIN ini.festa f WHERE f.codFesta = :codFesta AND ini.terceiroInt = :terceiroInt AND ini.codEvent = :codEvent AND ini.token =:token")
	public InfoIntegracao findInfo(int codFesta, String terceiroInt, String codEvent, String token);

	@Query(value="SELECT ini FROM InfoIntegracao ini JOIN ini.festa f WHERE f.codFesta = :codFesta AND ini.token = :token AND ini.terceiroInt = :terceiroInt")
	public InfoIntegracao findInfoByToken(int codFesta, String token, String terceiroInt);

	@Query(value="SELECT ini FROM InfoIntegracao ini JOIN ini.festa f WHERE ini.codInfo = :codInfo")
	public InfoIntegracao findByCodInfo(int codInfo);

	@Query(value="SELECT ini FROM InfoIntegracao ini JOIN ini.festa f WHERE f.codFesta = :codFesta AND ini.terceiroInt = :terceiroInt")
	public InfoIntegracao findInfoFesta(int codFesta, String terceiroInt);

}
