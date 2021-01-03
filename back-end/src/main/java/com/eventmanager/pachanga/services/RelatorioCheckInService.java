package com.eventmanager.pachanga.services;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.pachanga.dtos.RelatorioCheckInTO;
import com.eventmanager.pachanga.factory.RelatorioCheckInFactory;
import com.eventmanager.pachanga.repositories.IngressoRepository;

@Service
@Transactional
public class RelatorioCheckInService {

	@Autowired
	private IngressoRepository ingressoRepository;

	@Autowired
	private RelatorioCheckInFactory relatorioCheckInFactory;

	@Autowired
	private RelatorioAreaSegurancaService relatorioAreaSegurancaService;

	public RelatorioCheckInTO ingressosCompradosEntradas(int codFesta, int codUsuario) {
		relatorioAreaSegurancaService.validacaoUsuarioFestaRelatorio(codFesta, codUsuario);
		int quantidadeComprados = ingressoRepository.findIngressosFestaVendido(codFesta);
		int quantidadeEntradas = ingressoRepository.findIngressosChecked(codFesta);
		return relatorioCheckInFactory.relatorioIngressosCompradosEntradas(quantidadeComprados, quantidadeEntradas);
	}

	public RelatorioCheckInTO faixaEtariaFesta(int codFesta, int codUsuario) {
		relatorioAreaSegurancaService.validacaoUsuarioFestaRelatorio(codFesta, codUsuario);
		Map<Integer, Integer> quantidadeFaixaEtaria = new LinkedHashMap<>();
		ingressoRepository.findIngressosFesta(codFesta).stream().forEach(i -> {
			Date dataNasc = i.getUsuario().getDtNasc();
			int idade = dataNasc == null ? 0 : this.calculaIdade(dataNasc);

			if (quantidadeFaixaEtaria.containsKey(idade)) {
				Integer quantidade = quantidadeFaixaEtaria.get(idade);
				quantidadeFaixaEtaria.put(idade, quantidade + 1);
			} else {
				quantidadeFaixaEtaria.put(idade, 1);
			}
		});
		return relatorioCheckInFactory.relatorioFaixaEtariaFesta(quantidadeFaixaEtaria);
	}

	private int calculaIdade(Date dataNasc) {
		Calendar dataNascimento = Calendar.getInstance();
		dataNascimento.setTime(dataNasc);
		Calendar hoje = Calendar.getInstance();

		int idade = hoje.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR);

		if (hoje.get(Calendar.MONTH) < dataNascimento.get(Calendar.MONTH)) {
			idade--;
		} else {
			if (hoje.get(Calendar.MONTH) == dataNascimento.get(Calendar.MONTH)
					&& hoje.get(Calendar.DAY_OF_MONTH) < dataNascimento.get(Calendar.DAY_OF_MONTH)) {
				idade--;
			}
		}
		return idade;
	}

	public RelatorioCheckInTO quantidadeGeneroFesta(int codFesta, int codUsuario) {
		relatorioAreaSegurancaService.validacaoUsuarioFestaRelatorio(codFesta, codUsuario);
		Map<String, Integer> quantidadeGenero = new LinkedHashMap<>();
		ingressoRepository.findIngressosFesta(codFesta).stream().forEach(i -> {
			String generoBanco = i.getUsuario().getGenero();
			String genero = generoBanco == null ? "NF" : generoBanco;
			if (quantidadeGenero.containsKey(genero)) {
				Integer quantidade = quantidadeGenero.get(genero);
				quantidadeGenero.put(genero, quantidade + 1);
			} else {
				quantidadeGenero.put(genero, 1);
			}
		});

		return relatorioCheckInFactory.relatorioGeneroFesta(quantidadeGenero);
	}

	public RelatorioCheckInTO quantidadeEntradaHoras(int codFesta, int codUsuario) {
		relatorioAreaSegurancaService.validacaoUsuarioFestaRelatorio(codFesta, codUsuario);
		Map<String, Integer> quantidadeEntradaHora = new LinkedHashMap<>();

		ingressoRepository.findIngressoCheckedOrdenado(codFesta).stream().forEach(i -> {
			String diaHora = i.getDataCheckin().getDayOfMonth() + "d" + i.getDataCheckin().getHour();
			if (quantidadeEntradaHora.containsKey(diaHora)) {
				Integer quantidade = quantidadeEntradaHora.get(diaHora);
				quantidadeEntradaHora.put(diaHora, quantidade + 1);
			} else {
				quantidadeEntradaHora.put(diaHora, 1);
			}
		});
		return relatorioCheckInFactory.relatorioEntradaHora(quantidadeEntradaHora);
	}

}
