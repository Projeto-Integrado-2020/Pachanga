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

@Service
@Transactional
public class RelatorioDeVendaService {

	@Autowired
	private LoteRepository loteRepository;

	@Autowired
	private IngressoRepository ingressoRepository;

	@Autowired
	private RelatorioAreaSegurancaService relatorioAreaSegurancaService;
	
	@Autowired
	private RelatorioDeVendaFactory relatorioDeVendaFactory;

	public RelatorioDeVendaTO relatorioDeIngressosPagosComprados(int codFesta, int codUsuario) {
		relatorioAreaSegurancaService.validacaoUsuarioFestaRelatorio(codFesta, codUsuario);

		Map<String, Map<Integer, Integer>> ingreessos = new LinkedHashMap<>();
		loteRepository.listaLoteFesta(codFesta).stream().forEach(e -> {
			Map<Integer, Integer> quantidadeIngressosPagoComprado = new LinkedHashMap<>();
			quantidadeIngressosPagoComprado.put(ingressoRepository.findQuantidadeIngressosLotePago(e.getCodLote()),
					ingressoRepository.findQuantidadeIngressosLoteComprado(e.getCodLote()));
			ingreessos.put(e.getNomeLote(), quantidadeIngressosPagoComprado);
		});

		return relatorioDeVendaFactory.getIngressosPagosComprados(ingreessos);
	}

	public RelatorioDeVendaTO relatorioDeIngressos(int codFesta, int codUsuario) {
		relatorioAreaSegurancaService.validacaoUsuarioFestaRelatorio(codFesta, codUsuario);

		Map<String, Integer> ingreessos = new LinkedHashMap<>();
		loteRepository.listaLoteFesta(codFesta).stream().forEach(
				e -> ingreessos.put(e.getNomeLote(), ingressoRepository.findQuantidadeIngressosLote(e.getCodLote())));

		return relatorioDeVendaFactory.getRelatorioDeVenda(ingreessos);
	}

}
