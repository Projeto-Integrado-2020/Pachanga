package com.eventmanager.pachanga.services;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventmanager.pachanga.dtos.RelatorioAreaSegurancaTO;
import com.eventmanager.pachanga.factory.RelatorioAreaSegurancaTOFactory;
import com.eventmanager.pachanga.repositories.AreaSegurancaProblemaRepository;
import com.eventmanager.pachanga.repositories.AreaSegurancaRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
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
	private AreaSegurancaProblemaRepository areaSegurancaProblemaRepository;

	@Autowired
	private AreaSegurancaRepository areaSegurancaRepository;

	@Autowired
	private RelatorioAreaSegurancaTOFactory relatorioAreaSegurancaTOFactory;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public RelatorioAreaSegurancaTO relatorioProblemasArea(int codFesta, int codUsuario) {
		this.validacaoUsuarioFestaRelatorio(codFesta, codUsuario);
		
		Map<String, Integer> problemasArea = new LinkedHashMap<>();
		areaSegurancaRepository.findAllAreasByCodFesta(codFesta).stream().forEach(a -> {
			int quantidadeProblemas = areaSegurancaProblemaRepository.findQuantidadeProblemasByCodArea(a.getCodArea());
			problemasArea.put(a.getNomeArea(), quantidadeProblemas);
		});

		return relatorioAreaSegurancaTOFactory.getProblemasArea(problemasArea);
	}

	public RelatorioAreaSegurancaTO relatorioChamadasUsuario(int codFesta, int codUsuario) {
		this.validacaoUsuarioFestaRelatorio(codFesta, codUsuario);

		Map<String, Map<Integer,Integer>> chamadasEmitidasFuncionario = new LinkedHashMap<>();
		Map<Integer,Integer> chamadasEnganoFinalizadas = new LinkedHashMap<>();
		usuarioRepository.findByIdFesta(codFesta).stream().forEach(u -> {
			int chamadasEmitidasFinalizadas = areaSegurancaProblemaRepository
					.findQuantidadeProblemasEmitidosByUsuario(u.getCodUsuario(), TipoStatusProblema.FINALIZADO.getValor(), codFesta);
			int chamadasEmitidasEngano = areaSegurancaProblemaRepository
					.findQuantidadeProblemasEmitidosByUsuario(u.getCodUsuario(), TipoStatusProblema.ENGANO.getValor(), codFesta);
			chamadasEnganoFinalizadas.put(chamadasEmitidasFinalizadas, chamadasEmitidasEngano);
			chamadasEmitidasFuncionario.put(u.getNomeUser(), chamadasEnganoFinalizadas);
		});
		return relatorioAreaSegurancaTOFactory.getChamadasProblema(chamadasEmitidasFuncionario);
	}

	public RelatorioAreaSegurancaTO relatorioUsuarioSolucionador(int codFesta, int codUsuario) {
		this.validacaoUsuarioFestaRelatorio(codFesta, codUsuario);
		
		Map<String, Integer> solucionadorAlertasSeguranca = new LinkedHashMap<>();
		int quantidadeProblemasTotal = areaSegurancaProblemaRepository.countProblemasFesta(codFesta);
		usuarioRepository.findByIdFesta(codFesta).stream().forEach(u -> {
			int chamadasResolvidas = areaSegurancaProblemaRepository.findQuantidadeChamadasResolvidasByUsuario(u.getCodUsuario());
			solucionadorAlertasSeguranca.put(u.getNomeUser(), (chamadasResolvidas/quantidadeProblemasTotal) * 100);
		});
		return relatorioAreaSegurancaTOFactory.getUsuarioSolucionador(solucionadorAlertasSeguranca);
	}
	
	public void validacaoUsuarioFestaRelatorio(int codFesta, int codUsuario) {
		festaService.validarFestaExistente(codFesta);
		festaService.validarUsuarioFesta(codUsuario, codFesta);
		grupoService.validarPermissaoUsuarioGrupo(codFesta, codUsuario, TipoPermissao.VISURELA.getCodigo());// trocar a
																											// permissao
	}

}
