package com.eventmanager.pachanga.factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.eventmanager.pachanga.builder.AreaSegurancaProblemaBuilder;
import com.eventmanager.pachanga.builder.AreaSegurancaProblemaTOBuilder;
import com.eventmanager.pachanga.domains.AreaSeguranca;
import com.eventmanager.pachanga.domains.AreaSegurancaProblema;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Problema;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.AreaSegurancaProblemaTO;

@Component(value = "areaSegurancaProblemaFactory")
public class AreaSegurancaProblemaFactory {
	private AreaSegurancaProblemaFactory() {
	}

	public AreaSegurancaProblemaTO getAreaSegurancaProblemaTO(AreaSegurancaProblema problemaSeguranca,
			boolean retornoImagem) {
		int codUsuarioResolv = 0;
		if (problemaSeguranca.getCodUsuarioResolv() != null)
			codUsuarioResolv = problemaSeguranca.getCodUsuarioResolv().getCodUsuario();
		return AreaSegurancaProblemaTOBuilder.getInstance().codAreaProblema(problemaSeguranca.getCodAreaProblema())
				.codAreaSeguranca(problemaSeguranca.getArea().getCodArea())
				.codFesta(problemaSeguranca.getFesta().getCodFesta())
				.codProblema(problemaSeguranca.getProblema().getCodProblema())
				.codUsuarioEmissor(problemaSeguranca.getCodUsuarioEmissor().getCodUsuario())
				.codUsuarioResolv(codUsuarioResolv).descProblemaEmissor(problemaSeguranca.getDescProblema())
				.descProblema(problemaSeguranca.getProblema().getDescProblema())
				.horarioFim(problemaSeguranca.getHorarioFim()).horarioInicio(problemaSeguranca.getHorarioInicio())
				.statusProblema(problemaSeguranca.getStatusProblema())
				.observacaoSolucao(problemaSeguranca.getObservacaoSolucao())
				.imagemProblema(problemaSeguranca.getImagemProblema() == null || !retornoImagem ? null
						: problemaSeguranca.getImagemProblema())
				.build();
	}

	public AreaSegurancaProblema getProblemaSeguranca(AreaSegurancaProblemaTO problemaSegurancaTO, MultipartFile imagem,
			Festa festa, AreaSeguranca areaSeguranca, Problema problema, Usuario usuarioEmissor, Usuario usuarioResolv)
			throws IOException {
		return AreaSegurancaProblemaBuilder.getInstance().areaSeguranca(areaSeguranca).festa(festa).problema(problema)
				.usuarioEmissor(usuarioEmissor).usuarioResolv(usuarioResolv)
				.descProblema(problemaSegurancaTO.getDescProblema()).horarioFim(problemaSegurancaTO.getHorarioFim())
				.horarioInicio(problemaSegurancaTO.getHorarioInicio())
				.statusProblema(problemaSegurancaTO.getStatusProblema())
				.observacaoSolucao(problemaSegurancaTO.getObservacaoSolucao())
				.imagemProblema(imagem == null ? null : imagem.getBytes()).build();
	}

	public List<AreaSegurancaProblemaTO> getProblemasSegurancaTO(List<AreaSegurancaProblema> problemasSeguranca) {
		List<AreaSegurancaProblemaTO> retorno = new ArrayList<>();
		for (AreaSegurancaProblema problemaSeguranca : problemasSeguranca) {
			retorno.add(getAreaSegurancaProblemaTO(problemaSeguranca, false));
		}
		return retorno;
	}
}
