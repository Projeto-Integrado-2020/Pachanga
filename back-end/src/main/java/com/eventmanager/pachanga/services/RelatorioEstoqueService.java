package com.eventmanager.pachanga.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.pachanga.domains.Estoque;
import com.eventmanager.pachanga.domains.ItemEstoqueFluxo;
import com.eventmanager.pachanga.dtos.RelatorioEstoqueTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.RelatorioEstoqueTOFactory;
import com.eventmanager.pachanga.repositories.EstoqueRepository;
import com.eventmanager.pachanga.repositories.ItemEstoqueFluxoRepository;
import com.eventmanager.pachanga.tipo.TipoPermissao;

@Service
@Transactional
public class RelatorioEstoqueService {

	@Autowired
	private ItemEstoqueFluxoRepository itemEstoqueFluxoRepository;

	@Autowired
	private FestaService festaService;

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private EstoqueRepository estoqueRepository;

	@Autowired
	private RelatorioEstoqueTOFactory relatorioEstoqueTOFactory;

	public List<RelatorioEstoqueTO> relatoriosEstoque(int codFesta, int codUsuario, int codRelatorio) {
		festaService.validarFestaExistente(codFesta);
		festaService.validarUsuarioFesta(codUsuario, codFesta);
		grupoService.validarPermissaoUsuarioGrupo(codFesta, codUsuario, TipoPermissao.VISURELA.getCodigo());// trocar a
																											// permissao

		List<RelatorioEstoqueTO> relatoriosEstoques = new ArrayList<>();

		List<Estoque> estoques = estoqueRepository.findEstoqueByCodFestaJoinProduto(codFesta);

		estoques.stream().forEach(e -> {

			e.getItemEstoque().stream().forEach(ie -> {

				Map<LocalDateTime, Integer> quantidadeHora = new LinkedHashMap<>();

				List<ItemEstoqueFluxo> itensEstoque = itemEstoqueFluxoRepository
						.getFluxoEstoqueProduto(e.getCodEstoque(), ie.getProduto().getCodProduto());
				if (codRelatorio == 1) {
					this.relatorioConsumoItemEstoque(itensEstoque, quantidadeHora);
				} else if (codRelatorio == 2) {
					this.relatorioPerdaItemEstoque(itensEstoque, quantidadeHora);
				} else if (codRelatorio == 3) {
					this.relatorioQuantidadeItemEstoque(itensEstoque, quantidadeHora);
				} else {
					throw new ValidacaoException("CODRELIN");
				}

				relatoriosEstoques.add(relatorioEstoqueTOFactory.getRelatorioEstoque(e.getNomeEstoque(),
						ie.getProduto().getMarca(), quantidadeHora));

			});

		});

		return relatoriosEstoques;
	}

	public void relatorioConsumoItemEstoque(List<ItemEstoqueFluxo> itensEstoque,
			Map<LocalDateTime, Integer> quantidadeHora) {
		LocalDateTime hora = LocalDateTime.of(1, 1, 1, 1, 1);
		for (int i = 0; i < itensEstoque.size(); i++) {

			if (i > 0 && hora != itensEstoque.get(i).getDataHorario()
					&& itensEstoque.get(i - 1).getQuantidadeEstoque() > itensEstoque.get(i).getQuantidadeEstoque()) {
				hora = itensEstoque.get(i).getDataHorario();
				quantidadeHora.put(hora,
						itensEstoque.get(i - 1).getQuantidadeEstoque() - itensEstoque.get(i).getQuantidadeEstoque());
			}

			if (i == itensEstoque.size() - 1) {
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
