package com.eventmanager.pachanga.services;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.pachanga.dtos.RelatorioDeVendaTO;
import com.eventmanager.pachanga.factory.RelatorioDeVendaFactory;
import com.eventmanager.pachanga.repositories.IngressoRepository;
import com.eventmanager.pachanga.repositories.LoteRepository;
import com.eventmanager.pachanga.tipo.TipoPermissao;

@Service
@Transactional
public class RelatorioDeVendaService {
	
	@Autowired
	private FestaService festaService;
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private LoteRepository loteRepository;
	
	@Autowired
	private IngressoRepository ingressoRepository;
	
	public Map<String, Integer> relatorioDeIngressos(int codFesta, int codUsuario, String statusCompra) {
		this.validacaoUsuarioFestaRelatorio(codFesta, codUsuario);
		
		Map<String, Integer> ingreessos = new LinkedHashMap<>();
		loteRepository.listaLoteFesta(codFesta).stream()
			.forEach(e -> 
			{
				ingreessos.put(e.getNomeLote(), ingressoRepository.findQuantidadeIngressosLoteStatusCompra(e.getCodLote(), statusCompra));
		    });

		return ingreessos;
	}
	
	public Map<String, Integer> relatorioDeIngressos(int codFesta, int codUsuario) {
		this.validacaoUsuarioFestaRelatorio(codFesta, codUsuario);
		
		Map<String, Integer> ingreessos = new LinkedHashMap<>();
		loteRepository.listaLoteFesta(codFesta).stream()
			.forEach(e -> 
			{
				ingreessos.put(e.getNomeLote(), ingressoRepository.findQuantidadeIngressosLote(e.getCodLote()));
		    });

		return ingreessos;
	}
	
	public Map<String, Integer> relatorioDeIngressosCheckIn(int codFesta, int codUsuario, String statusIngresso) {
		this.validacaoUsuarioFestaRelatorio(codFesta, codUsuario);
		
		Map<String, Integer> ingreessos = new LinkedHashMap<>();
		loteRepository.listaLoteFesta(codFesta).stream()
			.forEach(e -> 
			{
				ingreessos.put(e.getNomeLote(), ingressoRepository.findQuantidadeIngressosLoteStatusIngresso(e.getCodLote(), statusIngresso));
		    });

		return ingreessos;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public void validacaoUsuarioFestaRelatorio(int codFesta, int codUsuario) {
		festaService.validarFestaExistente(codFesta);
		festaService.validarUsuarioFesta(codUsuario, codFesta);
		grupoService.validarPermissaoUsuarioGrupo(codFesta, codUsuario, TipoPermissao.VISULOTE.getCodigo());// trocar a
																											// permissao
	}
}
