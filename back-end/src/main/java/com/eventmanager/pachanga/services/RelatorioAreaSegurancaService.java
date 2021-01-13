package com.eventmanager.pachanga.services;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.pachanga.dtos.RelatorioAreaSegurancaTO;
import com.eventmanager.pachanga.factory.RelatorioAreaSegurancaTOFactory;
import com.eventmanager.pachanga.repositories.AreaSegurancaProblemaFluxoRepository;
import com.eventmanager.pachanga.tipo.TipoPermissao;
import com.eventmanager.pachanga.tipo.TipoStatusProblema;

@Service
@Transactional
public class RelatorioAreaSegurancaService {

	@Autowired
	private FestaService festaService;

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private RelatorioAreaSegurancaTOFactory relatorioAreaSegurancaTOFactory;

	@Autowired
	private AreaSegurancaProblemaFluxoRepository areaSegurancaProblemaFluxoRepository;

	public RelatorioAreaSegurancaTO relatorioProblemasArea(int codFesta, int codUsuario) {
		this.validacaoUsuarioFestaRelatorio(codFesta, codUsuario);

		Map<String, Integer> problemasArea = new LinkedHashMap<>();
		areaSegurancaProblemaFluxoRepository.findAreasByIdFesta(codFesta).stream().forEach(a -> {
			int quantidadeProblemas = areaSegurancaProblemaFluxoRepository
					.findQuantidadeProblemasByCodArea((Integer) a[0]);
			problemasArea.put((String) a[1], quantidadeProblemas);
		});

		return relatorioAreaSegurancaTOFactory.getProblemasArea(problemasArea);
	}

	public RelatorioAreaSegurancaTO relatorioChamadasUsuario(int codFesta, int codUsuario) {
		this.validacaoUsuarioFestaRelatorio(codFesta, codUsuario);

		Map<String, Map<Integer, Integer>> chamadasEmitidasFuncionario = new LinkedHashMap<>();
		Map<Integer, Integer> chamadasEnganoFinalizadas = new LinkedHashMap<>();

		areaSegurancaProblemaFluxoRepository.findUsuariosByIdFesta(codFesta).stream().forEach(u -> {

			int chamadasEmitidasFinalizadas = areaSegurancaProblemaFluxoRepository
					.findQuantidadeProblemasEmitidosByUsuario((Integer) u[0], TipoStatusProblema.FINALIZADO.getValor(),
							codFesta);

			int chamadasEmitidasEngano = areaSegurancaProblemaFluxoRepository.findQuantidadeProblemasEmitidosByUsuario(
					(Integer) u[0], TipoStatusProblema.ENGANO.getValor(), codFesta);

			chamadasEnganoFinalizadas.put(chamadasEmitidasFinalizadas, chamadasEmitidasEngano);
			chamadasEmitidasFuncionario.put((String) u[1], chamadasEnganoFinalizadas);
		});
		return relatorioAreaSegurancaTOFactory.getChamadasProblema(chamadasEmitidasFuncionario);
	}

	public RelatorioAreaSegurancaTO relatorioUsuarioSolucionador(int codFesta, int codUsuario) {
		this.validacaoUsuarioFestaRelatorio(codFesta, codUsuario);

		Map<String, Integer> solucionadorAlertasSeguranca = new LinkedHashMap<>();
		float quantidadeProblemasTotal = areaSegurancaProblemaFluxoRepository.countProblemasFesta(codFesta);
		areaSegurancaProblemaFluxoRepository.findUsuariosByIdFesta(codFesta).stream().forEach(u -> {
			int chamadasResolvidas = areaSegurancaProblemaFluxoRepository
					.findQuantidadeChamadasResolvidasByUsuario((Integer) u[0], codFesta);
			solucionadorAlertasSeguranca.put((String) u[1],
					(int) (chamadasResolvidas == 0 ? 0 : (chamadasResolvidas / quantidadeProblemasTotal) * 100));
		});
		return relatorioAreaSegurancaTOFactory.getUsuarioSolucionador(solucionadorAlertasSeguranca);
	}

	public void validacaoUsuarioFestaRelatorio(int codFesta, int codUsuario) {
		festaService.validarFestaExistente(codFesta);
		festaService.validarUsuarioFesta(codUsuario, codFesta);
//		grupoService.validarPermissaoUsuarioGrupo(codFesta, codUsuario, TipoPermissao.VISURELA.getCodigo());

	}

}
