package com.eventmanager.pachanga.services;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.pachanga.domains.Lote;
import com.eventmanager.pachanga.dtos.InfoLucroFesta;
import com.eventmanager.pachanga.dtos.RelatorioDeVendaTO;
import com.eventmanager.pachanga.factory.RelatorioDeVendaFactory;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.IngressoRepository;
import com.eventmanager.pachanga.repositories.LoteRepository;

@Service
@Transactional
public class RelatorioDeVendaService {

	@Autowired
	private LoteRepository loteRepository;

	@Autowired
	private FestaRepository festaRepository;

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
			Integer quantidadeIngressoPago = ingressoRepository.findQuantidadeIngressosLotePago(e.getCodLote());
			Integer quantidadeIngressoComprado = ingressoRepository.findQuantidadeIngressosLoteComprado(e.getCodLote());
			quantidadeIngressosPagoComprado.put(quantidadeIngressoPago,
					quantidadeIngressoComprado + quantidadeIngressoPago);
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

	public RelatorioDeVendaTO relatorioLucroFesta(int codFesta, int codUsuario) {
		relatorioAreaSegurancaService.validacaoUsuarioFestaRelatorio(codFesta, codUsuario);

		InfoLucroFesta infoLucroEsperado = this.relatorioLucroFesta(codFesta, true);
		InfoLucroFesta infoLucroReal = this.relatorioLucroFesta(codFesta, false);

		return relatorioDeVendaFactory.getRelatorioLucroTotalFesta(infoLucroEsperado, infoLucroReal,
				festaRepository.findByCodFesta(codFesta).getNomeFesta());
	}

	private InfoLucroFesta relatorioLucroFesta(int codFesta, boolean esperado) {
		Map<String, Float> lucroLote = new LinkedHashMap<>();
		Float lucroTotal = 0f;
		for (Lote lote : loteRepository.listaLoteFesta(codFesta)) {
			Float lucro = (esperado ? ingressoRepository.findLucroEsperadoLote(lote.getCodLote(), codFesta)
					: ingressoRepository.findLucroRealizadoLote(lote.getCodLote(), codFesta));
			lucro = lucro == null ? 0 : lucro;
			String nomeLote = lote.getNomeLote();
			lucroTotal += lucro;
			if (lucroLote.containsKey(nomeLote)) {
				lucro += lucroLote.get(nomeLote);
			}
			lucroLote.put(nomeLote, lucro);
		}
		return relatorioDeVendaFactory.getRelatorioLucroFesta(lucroTotal, lucroLote);
	}

}
