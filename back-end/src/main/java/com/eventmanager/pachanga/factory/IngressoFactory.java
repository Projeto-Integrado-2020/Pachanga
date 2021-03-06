package com.eventmanager.pachanga.factory;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.builder.IngressoBuilder;
import com.eventmanager.pachanga.builder.IngressoTOBuilder;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Ingresso;
import com.eventmanager.pachanga.domains.Lote;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.IngressoTO;

@Component(value = "ingressoFactory")
public class IngressoFactory {

	public Ingresso getIngresso(IngressoTO ingressoTO, Usuario usuario, Festa festa, Lote lote) {
		return IngressoBuilder.getInstance().codIngresso(ingressoTO.getCodIngresso()).lote(lote).festa(festa)
				.usuario(usuario).statusIngresso(ingressoTO.getStatusIngresso()).preco(ingressoTO.getPreco())
				.statusCompra(ingressoTO.getStatusCompra()).dataCheckin(ingressoTO.getDataCheckin())
				.dataCompra(ingressoTO.getDataCompra()).emailTitular(ingressoTO.getEmailTitular())
				.nomeTitular(ingressoTO.getNomeTitular()).urlBoleto(ingressoTO.getUrlBoleto()).build();
	}

	public IngressoTO getIngressoTO(Ingresso ingresso, String codBoleto) {
		return IngressoTOBuilder.getInstance().codIngresso(ingresso.getCodIngresso())
				.lote(ingresso.getLote().getCodLote()).statusIngresso(ingresso.getStatusIngresso())
				.preco(ingresso.getPreco()).statusCompra(ingresso.getStatusCompra())
				.emailTitular(ingresso.getEmailTitular()).nomeTitular(ingresso.getNomeTitular())
				.dataCompra(ingresso.getDataCompra()).dataCheckin(ingresso.getDataCheckin()).codBoleto(codBoleto)
				.urlBoleto(ingresso.getUrlBoleto()).nomeLote(ingresso.getLote().getNomeLote()).build();
	}

}
