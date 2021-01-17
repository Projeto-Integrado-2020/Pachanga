package com.eventmanager.pachanga.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.eventmanager.pachanga.domains.Ingresso;

public interface IngressoRepository extends JpaRepository<Ingresso, Integer>{

	@Query(value = "SELECT i FROM Ingresso i JOIN i.lote l WHERE l.codLote = :codLote")
	public List<Ingresso> findIngressosLote(int codLote);
	
	@Query(value = "SELECT count(i) FROM Ingresso i JOIN i.lote l WHERE l.codLote = :codLote")
	public int findQuantidadeIngressosLote(int codLote);
	
	@Query(value = "SELECT count(i) FROM Ingresso i JOIN i.lote l WHERE l.codLote = :codLote AND i.statusCompra = 'C'")
	public int findQuantidadeIngressosLoteComprado(int codLote);
	
	@Query(value = "SELECT count(i) FROM Ingresso i JOIN i.lote l WHERE l.codLote = :codLote AND i.statusCompra = 'P'")
	public int findQuantidadeIngressosLotePago(int codLote);
	
	@Query(value = "SELECT count(i) FROM Ingresso i JOIN i.lote l WHERE l.codLote = :codLote AND i.statusIngresso = :statusIngresso")
	public int findQuantidadeIngressosLoteStatusIngresso(int codLote, String statusIngresso);
	
	@Query(value = "SELECT i FROM Ingresso i JOIN i.usuario u JOIN i.festa f JOIN i.lote l WHERE u.codUsuario = :codUsuario")
	public List<Ingresso> findIngressosUser(int codUsuario);
	
	@Query(value = "SELECT i FROM Ingresso i JOIN i.festa f JOIN i.usuario u WHERE f.codFesta = :codFesta")
	public List<Ingresso> findIngressosFesta(int codFesta);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE Ingresso i SET i.statusIngresso = :statusIngresso, i.dataCheckin = :dataCheckin WHERE i.codIngresso = :codIngresso")
	public Ingresso updateCheckin(int codIngresso, String statusIngresso, LocalDateTime dataCheckin);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE Ingresso i SET i.statusCompra = :statusCompra WHERE i.codIngresso = :codIngresso")
	public Ingresso updateStatusCompra(int codIngresso, String statusCompra);
	
	@Query(value = "SELECT i FROM Ingresso i JOIN i.festa f WHERE i.codIngresso = :codIngresso")
	public Ingresso findIngressoByCodIngresso(String codIngresso);

	@Query(value = "SELECT i FROM Ingresso i WHERE i.codBoleto = :codBoleto")
	public List<Ingresso> findIngressoByCodBoleto(String codBoleto);

	@Query(value = "SELECT i FROM Ingresso i JOIN i.festa f WHERE i.statusCompra = 'C' AND i.codBoleto != null")
	public List<Ingresso> findIngressosEmProcessoBoleto();

	@Query(value = "SELECT i FROM Ingresso i JOIN i.festa f WHERE i.statusCompra = 'C' AND f.codFesta = :codFesta AND i.codBoleto != null")
	public List<Ingresso> findIngressosEmProcessoBoletoFesta(int codFesta);
	
	@Query(value = "SELECT count(i) FROM Ingresso i JOIN i.lote lt JOIN lt.festa fe WHERE fe.codFesta = :codFesta AND lt.codLote = :codLote")
	public int findIngressosLoteVendido(int codFesta, int codLote);
	
	@Query(value = "SELECT count(i) FROM Ingresso i JOIN i.lote lt JOIN lt.festa fe WHERE fe.codFesta = :codFesta AND i.statusIngresso = 'C' AND lt.codLote = :codLote")
	public int findIngressosLoteChecked(int codFesta, int codLote);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM ingresso WHERE cod_Festa = :idFesta", nativeQuery = true)
	public void deleteByCodFesta(int idFesta);

	@Query(value = "SELECT count(i) FROM Ingresso i JOIN i.lote lt JOIN lt.festa fe WHERE fe.codFesta = :codFesta")
	public int findIngressosFestaVendido(int codFesta);

	@Query(value = "SELECT count(i) FROM Ingresso i JOIN i.lote lt JOIN lt.festa fe WHERE fe.codFesta = :codFesta AND i.statusIngresso = 'C'")
	public int findIngressosChecked(int codFesta);
	
	@Query(value = "SELECT i FROM Ingresso i JOIN i.festa f JOIN i.usuario u WHERE f.codFesta = :codFesta AND i.statusIngresso = 'C' ORDER BY i.dataCheckin")
	public List<Ingresso> findIngressoCheckedOrdenado(int codFesta);

	@Query(value = "SELECT SUM(i.preco) FROM Ingresso i JOIN i.festa f JOIN i.lote l WHERE f.codFesta = :codFesta AND l.codLote = :codLote")
	public Float findLucroEsperadoLote(int codLote, int codFesta);
	
	@Query(value = "SELECT SUM(i.preco) FROM Ingresso i JOIN i.festa f JOIN i.lote l WHERE f.codFesta = :codFesta AND l.codLote = :codLote AND i.statusCompra = 'P'")
	public Float findLucroRealizadoLote(int codLote, int codFesta);

}
