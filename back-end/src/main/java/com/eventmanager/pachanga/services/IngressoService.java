package com.eventmanager.pachanga.services;

import java.util.ArrayList;
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
import com.eventmanager.pachanga.tipo.TipoPagamentoBoleto;
import com.eventmanager.pachanga.tipo.TipoStatusCompra;
import com.eventmanager.pachanga.tipo.TipoStatusIngresso;
import com.eventmanager.pachanga.utils.EmailMensagem;
import com.eventmanager.pachanga.utils.HashBuilder;

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

	public List<IngressoTO> addListaIngresso(List<IngressoTO> ingressosTO) {
		String codBoleto = null;
		
		List<IngressoTO> ingressosResposta = new ArrayList<>();
		for (IngressoTO ingressoTO : ingressosTO) {
			IngressoTO ingresso = this.addIngresso(ingressoTO, codBoleto);
			if(codBoleto == null) {
				codBoleto = ingresso.getCodBoleto();
			}
			ingressosResposta.add(ingresso);
		}
		return ingressosResposta;
	}

	public IngressoTO addIngresso(IngressoTO ingressoTO, String variosBoletos) {
		String codBoleto = null;

		Festa festa = festaService.validarFestaExistente(ingressoTO.getFesta().getCodFesta());
		Usuario usuario = usuarioService.validarUsuario(ingressoTO.getCodUsuario());
		Lote lote = loteService.validarLoteExistente(ingressoTO.getCodLote());

		Ingresso ingresso = ingressoFactory.getIngresso(ingressoTO, usuario, festa, lote);
		ingresso.setDataCompra(notificacaoService.getDataAtual());

		this.gerarCodigosIngresso(ingresso, false, 10);// geração código ingresso

		if (variosBoletos == null && ingressoTO.getBoleto().booleanValue()) {
			codBoleto = this.gerarCodigosIngresso(ingresso, true, 30);
			variosBoletos = codBoleto;
		} else if (variosBoletos != null){
			ingresso.setCodBoleto(HashBuilder.gerarCodigoHasheado(variosBoletos));
		}

		if (TipoStatusCompra.PAGO.getDescricao().equals(ingressoTO.getStatusCompra())) {
			this.criacaoEmailIngresso(ingresso, festa);
		}
		ingressoRepository.save(ingresso);

		return ingressoFactory.getIngressoTO(ingresso, variosBoletos == null ? codBoleto : variosBoletos);
	}

	private String gerarCodigosIngresso(Ingresso ingresso, boolean boleto, int limite) {
		String codigo = "";
		boolean sairLoop = false;
		while (true) {
			codigo = this.geracaoCodigo(limite);

			if (!boleto && ingressoRepository.findIngressoByCodIngresso(codigo) == null) {
				ingresso.setCodIngresso(codigo);
				sairLoop = true;
			} else if (boleto && !this.compararCodBoletosFesta(codigo, ingresso)) {
				ingresso.setCodBoleto(HashBuilder.gerarCodigoHasheado(codigo));
				sairLoop = true;
			}

			if (sairLoop) {
				break;
			}
		}
		return codigo;
	}

	private boolean compararCodBoletosFesta(String codigo, Ingresso ingresso) {
		boolean mesmoValorIngresso = false;
		List<Ingresso> ingressos = ingressoRepository
				.findIngressosEmProcessoBoletoFesta(ingresso.getFesta().getCodFesta());
		for (Ingresso ingressoExistente : ingressos) {
			if (HashBuilder.compararCodigos(codigo, ingressoExistente.getCodBoleto())) {
				mesmoValorIngresso = true;
			}
		}
		return mesmoValorIngresso;
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

	public void updateStatusCompra(String codBoleto, String statusPagamento) {

		List<Ingresso> ingressos = ingressoRepository.findIngressosEmProcessoBoleto();

		ingressos.stream().forEach(i -> {
			if (HashBuilder.compararCodigos(codBoleto, i.getCodBoleto())) {
				if (TipoPagamentoBoleto.DECLINED.getDescricao().equals(statusPagamento)) {
					ingressoRepository.delete(i);
				} else if (TipoPagamentoBoleto.PAID.getDescricao().equals(statusPagamento)) {
					i.setStatusCompra(TipoStatusCompra.PAGO.getDescricao());
					this.criacaoEmailIngresso(i, i.getFesta());
				}
			}

		});

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
			throw new ValidacaoException("INGRNFOU");// ingresso não encontrado
		}
		return ingresso;
	}

	private void criacaoEmailIngresso(Ingresso ingresso, Festa festa) {
		EmailMensagem emailMessage = new EmailMensagem();
		emailMessage.enviarEmailQRCode(ingresso.getUsuario().getEmail(), ingresso.getCodIngresso(), festa);
	}

	private String geracaoCodigo(int limit) {
		return new Random().ints(48, 122 + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(limit)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

	}

}
