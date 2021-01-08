package com.eventmanager.pachanga.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.pachanga.domains.ItemEstoqueFluxo;
import com.eventmanager.pachanga.dtos.InformacoesRelatorioEstoqueTO;
import com.eventmanager.pachanga.dtos.RelatorioEstoqueTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.RelatorioEstoqueTOFactory;
import com.eventmanager.pachanga.repositories.ItemEstoqueFluxoRepository;

@Service
@Transactional
public class RelatorioEstoqueService {

	@Autowired
	private ItemEstoqueFluxoRepository itemEstoqueFluxoRepository;

	@Autowired
	private RelatorioAreaSegurancaService relatorioAreaSegurancaService;

	@Autowired
	private RelatorioEstoqueTOFactory relatorioEstoqueTOFactory;

	public List<RelatorioEstoqueTO> relatoriosEstoque(int codFesta, int codUsuario, int codRelatorio) {
		relatorioAreaSegurancaService.validacaoUsuarioFestaRelatorio(codFesta, codUsuario);

		List<RelatorioEstoqueTO> relatoriosEstoques = new ArrayList<>();

		List<InformacoesRelatorioEstoqueTO> informacoesEstoque = new ArrayList<>();

		List<Integer[]> estoqueProdutoFesta = itemEstoqueFluxoRepository.getEstoqueProdutoFluxoFesta(codFesta);

		for (int i = 0; i < estoqueProdutoFesta.size(); i++) {
			List<ItemEstoqueFluxo> itensEstoque = itemEstoqueFluxoRepository
					.getFluxoEstoqueProduto(estoqueProdutoFesta.get(i)[0], estoqueProdutoFesta.get(i)[1]);

			Map<LocalDateTime, Integer> quantidadeHora = new LinkedHashMap<>();

			if (codRelatorio == 1) {
				this.relatorioConsumoItemEstoque(itensEstoque, quantidadeHora);
			} else if (codRelatorio == 2) {
				this.relatorioPerdaItemEstoque(itensEstoque, quantidadeHora);
			} else if (codRelatorio == 3) {
				this.relatorioQuantidadeItemEstoque(itensEstoque, quantidadeHora);
			} else {
				throw new ValidacaoException("CODRELIN");
			}

			informacoesEstoque.add(relatorioEstoqueTOFactory
					.getInformacoesRelatorioEstoque(itensEstoque.get(0).getNomeProduto(), quantidadeHora));

			if (i == estoqueProdutoFesta.size() - 1
					|| estoqueProdutoFesta.get(i + 1)[0].intValue() != estoqueProdutoFesta.get(i)[0].intValue()) {
				relatoriosEstoques.add(relatorioEstoqueTOFactory
						.getRelatorioEstoque(itensEstoque.get(0).getNomeEstoque(), informacoesEstoque));
				informacoesEstoque.clear();
			}

		}

		return relatoriosEstoques;
	}

	public List<InformacoesRelatorioEstoqueTO> relatorioQuantidadeConsumoProduto(int codFesta, int codUsuario) {
		relatorioAreaSegurancaService.validacaoUsuarioFestaRelatorio(codFesta, codUsuario);
		List<InformacoesRelatorioEstoqueTO> informacoesProduto = new ArrayList<>();
		itemEstoqueFluxoRepository.getProdutoEstoqueFesta(codFesta).stream().forEach(p -> {
			List<Integer[]> quantidadeProdutoConsumido = itemEstoqueFluxoRepository
					.getQuantidadeProdutoConsumidoFesta((int) p[0], codFesta);
			int quantidadeConsumida = 0;
			int quantidadeDescontoAnterior = 0;
			int codEstoque = 0;
			int quantidadeQuebraAnterior = 0;
			int quantidadePerdaTotal = 0;
			for (int i = 0; i < quantidadeProdutoConsumido.size(); i++) {
				if (i == 0 || codEstoque != quantidadeProdutoConsumido.get(i)[1]) {
					if (codEstoque != quantidadeProdutoConsumido.get(i)[1]) {
						quantidadePerdaTotal += quantidadeProdutoConsumido.get(i)[2];
					}
					quantidadeQuebraAnterior = quantidadeProdutoConsumido.get(i)[2];
					quantidadeDescontoAnterior = quantidadeProdutoConsumido.get(i)[0];
					codEstoque = quantidadeProdutoConsumido.get(i)[1];
				}
				if (i != 0 && quantidadeDescontoAnterior > quantidadeProdutoConsumido.get(i)[0]) {
					quantidadeConsumida += quantidadeDescontoAnterior - quantidadeProdutoConsumido.get(i)[0];
					quantidadeDescontoAnterior = quantidadeProdutoConsumido.get(i)[0];
					if (quantidadeQuebraAnterior != quantidadeProdutoConsumido.get(i)[2]) {
						quantidadePerdaTotal += quantidadeProdutoConsumido.get(i)[2];
						quantidadeQuebraAnterior = quantidadeProdutoConsumido.get(i)[2];
					}
				}
			}

			quantidadeConsumida -= quantidadePerdaTotal;

			informacoesProduto
					.add(relatorioEstoqueTOFactory.getInformacaoRelatorioConsumo((String) p[1], quantidadeConsumida));
		});
		return informacoesProduto;

	}

	public void relatorioConsumoItemEstoque(List<ItemEstoqueFluxo> itensEstoque,
			Map<LocalDateTime, Integer> quantidadeHora) {
		LocalDateTime hora = LocalDateTime.of(1, 1, 1, 1, 1);
		for (int i = 0; i < itensEstoque.size(); i++) {

			if (i == 0) {
				hora = itensEstoque.get(i).getDataHorario();
				quantidadeHora.put(hora, 0);
			}

			if (i > 0 && hora != itensEstoque.get(i).getDataHorario()
					&& itensEstoque.get(i - 1).getQuantidadeEstoque() > itensEstoque.get(i).getQuantidadeEstoque()) {
				hora = itensEstoque.get(i).getDataHorario();
				quantidadeHora.put(hora,
						itensEstoque.get(i - 1).getQuantidadeEstoque() - itensEstoque.get(i).getQuantidadeEstoque());
			}

			if (i == itensEstoque.size() - 1 && i != 0) {
				quantidadeHora.put(itensEstoque.get(i).getDataHorario(),
						itensEstoque.get(i - 1).getQuantidadeEstoque() - itensEstoque.get(i).getQuantidadeEstoque());
			}
		}
	}

	public void relatorioPerdaItemEstoque(List<ItemEstoqueFluxo> itensEstoque,
			Map<LocalDateTime, Integer> quantidadeHora) {
		LocalDateTime hora = LocalDateTime.of(1, 1, 1, 1, 1);
		for (int i = 0; i < itensEstoque.size(); i++) {

			if (i == 0) {
				hora = itensEstoque.get(i).getDataHorario();
			}

			if (hora != itensEstoque.get(i).getDataHorario()) {
				quantidadeHora.put(hora, itensEstoque.get(i - 1).getQuantPerda());
				hora = itensEstoque.get(i).getDataHorario();
			}

			if (i == itensEstoque.size() - 1) {
				quantidadeHora.put(itensEstoque.get(i).getDataHorario(), itensEstoque.get(i).getQuantPerda());
			}
		}
	}

	public void relatorioQuantidadeItemEstoque(List<ItemEstoqueFluxo> itensEstoque,
			Map<LocalDateTime, Integer> quantidadeHora) {
		LocalDateTime hora = LocalDateTime.of(1, 1, 1, 1, 1);
		for (int i = 0; i < itensEstoque.size(); i++) {

			if (i == 0) {
				hora = itensEstoque.get(i).getDataHorario();
			}

			if (hora != itensEstoque.get(i).getDataHorario()) {
				quantidadeHora.put(hora, itensEstoque.get(i - 1).getQuantidadeEstoque());
				hora = itensEstoque.get(i).getDataHorario();
			}

			if (i == itensEstoque.size() - 1) {
				quantidadeHora.put(itensEstoque.get(i).getDataHorario(), itensEstoque.get(i).getQuantidadeEstoque());
			}

		}
	}

}
