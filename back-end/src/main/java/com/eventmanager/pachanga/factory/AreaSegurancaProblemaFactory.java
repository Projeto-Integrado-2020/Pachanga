package com.eventmanager.pachanga.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

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

	public static AreaSegurancaProblemaTO getAreaSegurancaProblemaTO(AreaSegurancaProblema problemaSeguranca) {
		int codUsuarioResolv = 0;
		if(problemaSeguranca.getCodUsuarioResolv() != null) codUsuarioResolv = problemaSeguranca.getCodUsuarioResolv().getCodUsuario();
		return AreaSegurancaProblemaTOBuilder.getInstance()
									     .codAreaSeguranca(problemaSeguranca.getArea().getCodArea())
									     .codFesta(problemaSeguranca.getFesta().getCodFesta())
									     .codProblema(problemaSeguranca.getProblema().getCodProblema())
									     .codUsuarioEmissor(problemaSeguranca.getCodUsuarioEmissor().getCodUsuario())
									     .codUsuarioResolv(codUsuarioResolv)
									     .descProblema(problemaSeguranca.getDescProblema())
									     .horarioFim(problemaSeguranca.getHorarioFim())
									     .horarioInicio(problemaSeguranca.getHorarioInicio())
									     .statusProblema(problemaSeguranca.getStatusProblema())
									     .build();
	}			

	public static AreaSegurancaProblema getProblemaSeguranca(AreaSegurancaProblemaTO problemaSegurancaTO, Festa festa, AreaSeguranca areaSeguranca, Problema problema, Usuario usuarioEmissor,  Usuario usuarioResolv) {
		return AreaSegurancaProblemaBuilder.getInstance()
									     .areaSeguranca(areaSeguranca) 
									     .festa(festa)
									     .problema(problema)
									     .usuarioEmissor(usuarioEmissor)
									     .usuarioResolv(usuarioResolv)
									     .descProblema(problemaSegurancaTO.getDescProblema())
									     .horarioFim(problemaSegurancaTO.getHorarioFim())
									     .horarioInicio(problemaSegurancaTO.getHorarioInicio())
									     .statusProblema(problemaSegurancaTO.getStatusProblema())
									     .build();
	}
	
	public static List<AreaSegurancaProblemaTO> getProblemasSegurancaTO(List<AreaSegurancaProblema> problemasSeguranca) {
		List<AreaSegurancaProblemaTO> retorno = new ArrayList<>();
		for(AreaSegurancaProblema problemaSeguranca : problemasSeguranca) {
			retorno.add(getAreaSegurancaProblemaTO(problemaSeguranca));
		}
		return retorno;
	}
}
