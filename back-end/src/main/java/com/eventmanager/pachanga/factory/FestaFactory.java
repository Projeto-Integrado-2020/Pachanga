package com.eventmanager.pachanga.factory;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.eventmanager.pachanga.builder.FestaBuilder;
import com.eventmanager.pachanga.builder.FestaTOBuilder;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.dtos.CategoriaTO;
import com.eventmanager.pachanga.dtos.ConvidadoTO;
import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.dtos.UsuarioTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.utils.CompressorBytes;

@Component(value = "festaFactory")
public class FestaFactory {

	@Autowired
	private CompressorBytes compressorBytes;

	private FestaFactory() {
	}

	public FestaTO getFestaTO(Festa festa, List<UsuarioTO> usuarios, boolean enviarQuantidade,
			CategoriaTO categoriaPrimaria, CategoriaTO categoriaSecundaria, List<ConvidadoTO> convidados) {
		FestaTOBuilder festaToBuilder = FestaTOBuilder.getInstance().codEnderecoFesta(festa.getCodEnderecoFesta())
				.codFesta(festa.getCodFesta()).descOrganizador(festa.getDescOrganizador())
				.descricaoFesta(festa.getDescricaoFesta()).horarioFimFesta(festa.getHorarioFimFesta())
				.horarioFimFestaReal(festa.getHorarioFimFestaReal()).horarioInicioFesta(festa.getHorarioInicioFesta())
				.nomeFesta(festa.getNomeFesta()).organizador(festa.getOrganizador()).statusFesta(festa.getStatusFesta())
				.categoriaPrimaria(categoriaPrimaria).categoriaSecundaria(categoriaSecundaria)
				.imagem(festa.getImagem() == null ? null : compressorBytes.decompressByteArray(festa.getImagem()));
		if (usuarios != null) {
			if (enviarQuantidade) {
				festaToBuilder.quantidadeParticipantes(usuarios.size() + convidados.size());
			} else {
				festaToBuilder.usuarios(usuarios).convidados(convidados);
			}
		}
		return festaToBuilder.build();
	}

	public Festa getFesta(FestaTO festaTo, MultipartFile imagem) {
		try {
			return FestaBuilder.getInstance().codEnderecoFesta(festaTo.getCodEnderecoFesta())
					.codFesta(festaTo.getCodFesta()).descOrganizador(festaTo.getDescOrganizador())
					.descricaoFesta(festaTo.getDescricaoFesta()).horarioFimFesta(festaTo.getHorarioFimFesta())
					.horarioInicioFesta(festaTo.getHorarioInicioFesta()).nomeFesta(festaTo.getNomeFesta())
					.organizador(festaTo.getOrganizador()).statusFesta(festaTo.getStatusFesta())
					.imagem(imagem == null ? null : compressorBytes.compressByteArray(imagem.getBytes())).build();
		} catch (IOException e) {
			throw new ValidacaoException(e.getMessage());
		}
	}

	public FestaTO getFestaTO(Festa festa, List<UsuarioTO> usuarios, boolean enviarQuantidade,
			CategoriaTO categoriaPrimaria, CategoriaTO categoriaSecundaria, List<ConvidadoTO> convidados, int idUser) {
		FestaTO festaTo = getFestaTO(festa, usuarios, enviarQuantidade, categoriaPrimaria, categoriaSecundaria,
				convidados);
		festaTo.setIsOrganizador(festa.isOrganizador(idUser));
		return festaTo;
	}

}
