package com.eventmanager.pachanga.factory;

import java.util.ArrayList;
import java.util.List;

import com.eventmanager.pachanga.builder.PermissaoBuilder;
import com.eventmanager.pachanga.builder.PermissaoTOBuilder;
import com.eventmanager.pachanga.domains.Permissao;
import com.eventmanager.pachanga.dtos.PermissaoTO;

public class PermissaoFactory {
	private PermissaoFactory() {
	}
	
	public static Permissao getPermissao(PermissaoTO permissaoTO) {
		return PermissaoBuilder.getInstance()
				.codPermissao(permissaoTO.getCodPermissao())
				.descPermissao(permissaoTO.getDescPermissao())
				.tipPermissao(permissaoTO.getTipPermissao())
				.build();
	}
	
	public static PermissaoTO getPermissaoTO(Permissao permissao) {
		return PermissaoTOBuilder.getInstance()
				.codPermissao(permissao.getCodPermissao())
				.descPermissao(permissao.getDescPermissao())
				.tipPermissao(permissao.getTipPermissao())
				.build();
	}
	
	public static List<PermissaoTO> getPermissoesTO(List<Permissao> permissoes) {
		List<PermissaoTO> retorno = new ArrayList<>();
		for(Permissao permissao : permissoes) {
			retorno.add(getPermissaoTO(permissao));
		}
		return retorno;
	}
	
}
