package com.eventmanager.pachanga.utils;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.eventmanager.pachanga.domains.DadoBancario;

public class GeracaoArquivoRemessa {
	
	private GeracaoArquivoRemessa() {
	}

	private static final String CODIGO_BANCO = "341";
	private static final String NUMERO_INSCRICAO = "12345678901234";
	private static final String AGENCIA = "1234";
	private static final String CONTA = "12345";
	private static final String DAC = "1";
	private static final String NOME_EMPRESA = "EVENTMANAGER                  ";
	private static final String NOME_BANCO = "BANCO ITAU SA                 ";
	private static final String NUM_INSC = "123456789012345";
	private static final String LOGRADOURO = "1234567890123456789012345678901234567890";
	private static final String BAIRRO = "123456789012345";
	private static final String CEP = "12345";
	private static final String SUSEP_CEP = "123";
	private static final String CIDADE = "SAO PAULO      ";
	private static final String UF = "SP";
	
	private static final String VALOR_ZERADO = "000000000,00";

	public static String criacaoHeaderLote() {
		return criacaoHeaderArquivo();
	}

	public static String criacaoHeaderArquivo() {
		DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("DDMMAAAA");
		LocalDateTime dataAtual = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
		String dataCriacao = formatterData.format(dataAtual);

		DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HHMMSS");
		String horarioCriacao = formatterHora.format(dataAtual);

		StringBuilder header = new StringBuilder();
		header.append(CODIGO_BANCO);
		header.append(adicionarMesmosCaracteres(4, "0"));
		header.append(adicionarMesmosCaracteres(1, "0"));
		header.append(adicionarMesmosCaracteres(9, " "));
		header.append("2");
		header.append(NUMERO_INSCRICAO);
		header.append(adicionarMesmosCaracteres(20, " "));
		header.append(adicionarMesmosCaracteres(1, "0"));
		header.append(AGENCIA);
		header.append(adicionarMesmosCaracteres(1, " "));
		header.append(adicionarMesmosCaracteres(7, "0"));
		header.append(CONTA);
		header.append(adicionarMesmosCaracteres(1, " "));
		header.append(DAC);
		header.append(NOME_EMPRESA);
		header.append(NOME_BANCO);
		header.append(adicionarMesmosCaracteres(10, " "));
		header.append("1");
		header.append(dataCriacao);
		header.append(horarioCriacao);
		header.append("000001");
		header.append("040");
		header.append(adicionarMesmosCaracteres(5, "0"));
		header.append(adicionarMesmosCaracteres(54, " "));
		header.append(adicionarMesmosCaracteres(3, "0"));
		header.append(adicionarMesmosCaracteres(12, " "));
		return header.toString();
	}

	public static String adicionarMesmosCaracteres(int contador, String letra) {
		return IntStream.range(0, contador).mapToObj(i -> letra).collect(Collectors.joining(""));
	}

	public static String criacaoSegmentoP(int contador, float valorBoleto, DadoBancario dadoBancario) {
		DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("DDMMAAAA");
		DateTimeFormatter formatterDataComBarra = DateTimeFormatter.ofPattern("DD/MM/AAAA");

		LocalDateTime dataAtual = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));

		LocalDateTime dataVencimento = dataAtual.plusDays(5l);
		LocalDateTime dataLimite = dataAtual.plusDays(30l);

		String dataCriacao = formatterData.format(dataAtual);
		String dataCriacaoVencimento = formatterData.format(dataVencimento);

		StringBuilder segmentoP = new StringBuilder();
		segmentoP.append(dadoBancario.getCodBanco());// numero do banco do cliente
		segmentoP.append(adicionarMesmosCaracteres(5 - Integer.toString(contador).length(), "0") + contador);
		segmentoP.append("3");
		segmentoP.append(adicionarMesmosCaracteres(4, "0") + contador);
		segmentoP.append("P");
		segmentoP.append(adicionarMesmosCaracteres(1, " "));
		segmentoP.append("01");// ver qual o código correto
		segmentoP.append(adicionarMesmosCaracteres(1, "0"));
		segmentoP.append(dadoBancario.getCodAgencia());// agencia
		segmentoP.append(adicionarMesmosCaracteres(1, " "));
		segmentoP.append(adicionarMesmosCaracteres(7, "0"));
		segmentoP.append(dadoBancario.getCodConta());// conta do cara
		segmentoP.append(adicionarMesmosCaracteres(1, " "));
		segmentoP.append("1");// digito do cara
		segmentoP.append("109");// codigo 5
		segmentoP.append("00000000");
		segmentoP.append(adicionarMesmosCaracteres(1, "0"));
		segmentoP.append(adicionarMesmosCaracteres(8, " "));
		segmentoP.append(adicionarMesmosCaracteres(5, "0"));
		segmentoP.append("1234567890");// codigo 7
		segmentoP.append(adicionarMesmosCaracteres(5, " "));
		segmentoP.append(dataCriacaoVencimento);// codigo 8
		segmentoP.append(valorBoleto);// codigo 9
		segmentoP.append("00000");// codigo 10
		segmentoP.append("1");// digito de conferencia agencia cobradora
		segmentoP.append("17");// codigo 11
		segmentoP.append("A");
		segmentoP.append(dataCriacao);
		segmentoP.append(adicionarMesmosCaracteres(1, "0"));
		segmentoP.append(formatterDataComBarra.format(dataVencimento));// código 12
		segmentoP.append("0000000000000,00");// código 13
		segmentoP.append(adicionarMesmosCaracteres(1, "0"));
		segmentoP.append(formatterData.format(dataLimite));
		segmentoP.append(VALOR_ZERADO);// código 14
		segmentoP.append(VALOR_ZERADO);// código 15
		segmentoP.append(VALOR_ZERADO);// código 16
		segmentoP.append(adicionarMesmosCaracteres(25, "0"));// código 17
		segmentoP.append("0");// códgio 18
		segmentoP.append("00");// códgio 18
		segmentoP.append("0");// código 19
		segmentoP.append("00");// código 19 sei lá
		segmentoP.append(adicionarMesmosCaracteres(13, "0"));
		segmentoP.append(adicionarMesmosCaracteres(1, " "));
		return segmentoP.toString();
	}

	public static String criacaoSegmentoQ(int contador, String nomeSacador) {
		StringBuilder segmentoQ = new StringBuilder();
		segmentoQ.append(CODIGO_BANCO);
		segmentoQ.append(contador);
		segmentoQ.append("3");
		segmentoQ.append(adicionarMesmosCaracteres(5 - Integer.toString(contador).length(), "0") + contador);
		segmentoQ.append("Q");
		segmentoQ.append(adicionarMesmosCaracteres(1, " "));
		segmentoQ.append("01");// ver se está correto
		segmentoQ.append("2");
		segmentoQ.append(NUM_INSC);
		segmentoQ.append(NOME_EMPRESA);
		segmentoQ.append(adicionarMesmosCaracteres(10, " "));
		segmentoQ.append(LOGRADOURO);
		segmentoQ.append(BAIRRO);
		segmentoQ.append(CEP);
		segmentoQ.append(SUSEP_CEP);
		segmentoQ.append(CIDADE);
		segmentoQ.append(UF);
		segmentoQ.append("1");
		segmentoQ.append(NUMERO_INSCRICAO);
		segmentoQ.append(nomeSacador + adicionarMesmosCaracteres(30 - nomeSacador.length(), " "));
		segmentoQ.append(adicionarMesmosCaracteres(10, " "));
		segmentoQ.append(adicionarMesmosCaracteres(3, "0"));
		segmentoQ.append(adicionarMesmosCaracteres(28, " "));
		return segmentoQ.toString();
	}

	public static String criacaoTrailerLote(int quantidadeTotal, float valorTotal) {
		DecimalFormat formatter = new DecimalFormat(VALOR_ZERADO);
		
		StringBuilder trailerLote = new StringBuilder();
		trailerLote.append(CODIGO_BANCO);
		trailerLote.append("00001");// ver como vai ser essa bagaça
		trailerLote.append(adicionarMesmosCaracteres(9, " "));
		trailerLote.append(adicionarMesmosCaracteres(9, " "));
		trailerLote.append(
				adicionarMesmosCaracteres(6 - Integer.toString(quantidadeTotal).length(), "0") + quantidadeTotal);
		trailerLote.append(
				adicionarMesmosCaracteres(6 - Integer.toString(quantidadeTotal).length(), "0") + quantidadeTotal);
		trailerLote.append(formatter.format(valorTotal));
		trailerLote.append(
				adicionarMesmosCaracteres(6 - Integer.toString(quantidadeTotal).length(), "0") + quantidadeTotal);
		trailerLote.append(formatter.format(valorTotal));
		trailerLote.append(adicionarMesmosCaracteres(46, "0"));
		trailerLote.append("00000001");
		trailerLote.append(adicionarMesmosCaracteres(117, " "));
		return trailerLote.toString();
	}
	
	public static String criacaoTrailerArquivo(int quantidadeTotalLote) {
		StringBuilder trailerArquivo = new StringBuilder();
		trailerArquivo.append("9999");
		trailerArquivo.append("9");
		trailerArquivo.append(adicionarMesmosCaracteres(9, " "));
		trailerArquivo.append(
				adicionarMesmosCaracteres(6 - Integer.toString(quantidadeTotalLote).length(), "0") + quantidadeTotalLote);
		trailerArquivo.append(
				adicionarMesmosCaracteres(6 - Integer.toString(quantidadeTotalLote).length(), "0") + quantidadeTotalLote);
		trailerArquivo.append(adicionarMesmosCaracteres(6, "0"));
		trailerArquivo.append(adicionarMesmosCaracteres(205, " "));
		return trailerArquivo.toString();
	}

}