package com.eventmanager.pachanga.services;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.DadoBancario;
import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Ingresso;
import com.eventmanager.pachanga.repositories.DadoBancarioRepository;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.IngressoRepository;
import com.eventmanager.pachanga.utils.GeracaoArquivoRemessa;

@Service
@Transactional
public class ArquivosBancoService {

	@Autowired
	private DadoBancarioRepository dadoBancarioRepository;

	@Autowired
	private FestaRepository festaRepository;

	@Autowired
	private IngressoRepository ingressoRepository;
	
	@Autowired
	private NotificacaoService notificacaoService;

	private static final String QUEBRA_LINHA = "\r\n";

	public void criacaoRemessa() throws IOException {
		int quantidadeTotal = 0;
		int quantidadeTotalLote = 0;
		float valorTotal = 0;
		float valorDesconto = 0;
		FileWriter arq = new FileWriter("remessaItau.RRM");
		
		LocalDateTime dataAtual = notificacaoService.getDataAtual();

		arq.write(GeracaoArquivoRemessa.criacaoHeaderArquivo(dataAtual) + QUEBRA_LINHA);

		List<Festa> festas = festaRepository.findFestasAcabadas();
		

		for (int i = 0; i < festas.size(); i++) {

			DadoBancario dado = dadoBancarioRepository.findDadosBancariosFesta(festas.get(i).getCodFesta());

			if (dado != null) {

				for (Ingresso ingresso : ingressoRepository.findIngressosFesta(festas.get(i).getCodFesta())) {
					float preco = ingresso.getPreco();
					if (preco < 25f && preco != 0) {
						valorDesconto += 1.25f;
					} else if (preco > 25f) {
						valorDesconto += (5 * preco) / 100;
					}
					valorTotal += preco;
				}

				if (valorTotal != 0) {

					valorTotal -= valorDesconto;

					arq.write(GeracaoArquivoRemessa.criacaoHeaderLote(dataAtual) + QUEBRA_LINHA);

					arq.write(GeracaoArquivoRemessa.criacaoSegmentoP(i + 1, valorTotal, dado, dataAtual) + QUEBRA_LINHA);
					arq.write(
							GeracaoArquivoRemessa.criacaoSegmentoQ(i + 1, festas.get(i).getOrganizador().toUpperCase())
									+ QUEBRA_LINHA);

					arq.write(GeracaoArquivoRemessa.criacaoTrailerLote(quantidadeTotal, valorTotal) + QUEBRA_LINHA);

					quantidadeTotal += 1;
				}
			}

		}
		arq.write(GeracaoArquivoRemessa.criacaoTrailerArquivo(quantidadeTotalLote) + QUEBRA_LINHA);
		arq.close();
	}

}
