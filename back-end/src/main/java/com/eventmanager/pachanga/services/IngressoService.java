package com.eventmanager.pachanga.services;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Ingresso;
import com.eventmanager.pachanga.domains.Lote;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.IngressoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.factory.IngressoFactory;
import com.eventmanager.pachanga.repositories.IngressoRepository;
import com.eventmanager.pachanga.tipo.TipoStatusCompra;
import com.eventmanager.pachanga.tipo.TipoStatusIngresso;
import com.eventmanager.pachanga.utils.EmailMensagem;

@Service
@Transactional
public class IngressoService {

	@Autowired
	private IngressoRepository ingressoRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private FestaService festaService;

	@Autowired
	private IngressoFactory ingressoFactory;

	@Autowired
	private LoteService loteService;

	@Autowired
	private NotificacaoService notificacaoService;

	public List<Ingresso> getIngressosUser(int codUsuario) {
		usuarioService.validarUsuario(codUsuario);
		return ingressoRepository.findIngressosUser(codUsuario);
	}

	public List<Ingresso> getIngressosFesta(int codFesta) {
		festaService.validarFestaExistente(codFesta);
		return ingressoRepository.findIngressosFesta(codFesta);
	}

	public Ingresso addIngresso(IngressoTO ingressoTO) {

		Festa festa = festaService.validarFestaExistente(ingressoTO.getFesta().getCodFesta());
		Usuario usuario = usuarioService.validarUsuario(ingressoTO.getCodUsuario());
		Lote lote = loteService.validarLoteExistente(ingressoTO.getCodLote());

		Ingresso ingresso = ingressoFactory.getIngresso(ingressoTO, usuario, festa, lote);
		ingresso.setDataCompra(notificacaoService.getDataAtual());

		while (true) {
			ingresso.setCodIngresso(new Random().ints(48, 122 + 1)
					.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(10)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString());

			if (ingressoRepository.findIngressoByCodIngresso(ingresso.getCodIngresso()) == null) {
				break;
			}
		}

		if (TipoStatusCompra.PAGO.getDescricao().equals(ingressoTO.getStatusCompra())) {
			this.criacaoEmailIngresso(ingresso, festa);
		}
		ingressoRepository.save(ingresso);
		return ingresso;
	}

	public Ingresso updateCheckin(IngressoTO ingressoTO) {
		Ingresso ingresso = this.validarIngressoExistente(ingressoTO.getCodIngresso());
		this.validarStatusIngresso(ingressoTO.getStatusIngresso());
		ingresso.setStatusCompra(ingressoTO.getStatusCompra());
		if (ingressoTO.getDataCheckin() == null) {
			throw new ValidacaoException("CHECDATN");// data do check-in não informada
		}
		ingresso.setDataCheckin(ingressoTO.getDataCheckin());
		ingressoRepository.save(ingresso);
		return ingresso;
	}

	public Ingresso updateStatusCompra(IngressoTO ingressoTO) {
		this.validarStatusCompra(ingressoTO.getStatusCompra());
		Ingresso ingresso = this.validarIngressoExistente(ingressoTO.getCodIngresso());
		Festa festa = festaService.validarFestaExistente(ingressoTO.getFesta().getCodFesta());

		ingresso.setStatusCompra(ingressoTO.getStatusCompra());

		if (TipoStatusCompra.PAGO.getDescricao().equals(ingressoTO.getStatusCompra())) {
			this.criacaoEmailIngresso(ingresso, festa);
		}
		return ingresso;
	}

	private void validarStatusCompra(String statusCompra) {
		if (TipoStatusCompra.COMPRADO.getDescricao().equals(statusCompra)
				&& TipoStatusCompra.PAGO.getDescricao().equals(statusCompra)) {
			throw new ValidacaoException("STACIINC");// status de compra do ingresso incorreto
		}
	}

	private void validarStatusIngresso(String statusCompra) {
		if (TipoStatusIngresso.CHECKED.getDescricao().equals(statusCompra)
				&& TipoStatusIngresso.UNCHECKED.getDescricao().equals(statusCompra)) {
			throw new ValidacaoException("STAIIINC");// status do ingresso do ingresso incorreto
		}
	}

	public Ingresso validarIngressoExistente(String codIngresso) {
		Ingresso ingresso = ingressoRepository.findIngressoByCodIngresso(codIngresso);
		if (ingresso == null) {
			throw new ValidacaoException("INGRNFOU");
		}
		return ingresso;
	}

	private void criacaoEmailIngresso(Ingresso ingresso, Festa festa) {
		EmailMensagem emailMessage = new EmailMensagem();
		emailMessage.enviarEmailQRCode(ingresso.getUsuario().getEmail(), ingresso.getCodIngresso(), festa);
	}

}
