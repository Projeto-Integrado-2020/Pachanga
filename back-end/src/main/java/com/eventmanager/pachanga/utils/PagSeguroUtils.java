package com.eventmanager.pachanga.utils;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.dtos.InfoPagamentoBoletoTO;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class PagSeguroUtils {

	private static String codigo;

	public static void criacaoBoleto(InfoPagamentoBoletoTO infoPagamento, float preco, String codBoleto, Festa festa) {

		HttpResponse<String> response = Unirest.post("https://sandbox.api.pagseguro.com/charges")
		  .header("Authorization", "FC6E0C59A56D4C37A3C0DBCB719CAE84")
		  .header("Content-Type", "application/json")
		  .header("x-api-version", "4.0")
		  .header("x-idempotency-key", "")
		  .body("{\r\n"
		  		+ "  \"reference_id\": \"ex-00001\",\r\n"
		  		+ "  \"description\": \"Ingresso para a festa \" "+ festa.getNomeFesta() + ",\r\n"
		  		+ "  \"amount\": {\r\n"
		  		+ "    \"value\": 1000,\r\n"
		  		+ "    \"currency\": \"BRL\"\r\n"
		  		+ "  },\r\n"
		  		+ "  \"payment_method\": {\r\n"
		  		+ "    \"type\": \"BOLETO\",\r\n"
		  		+ "    \"boleto\": {\r\n"
		  		+ "      \"due_date\": \"2020-12-11\",\r\n"
		  		+ "      \"instruction_lines\": {\r\n"
		  		+ "        \"line_1\": \"Pagamento processado para DESC Fatura\",\r\n"
		  		+ "        \"line_2\": \"Via PagSeguro\"\r\n"
		  		+ "      },\r\n"
		  		+ "      \"holder\": {\r\n"
		  		+ "        \"name\": "+ infoPagamento.getNome() +",\r\n"
		  		+ "        \"tax_id\": "+ infoPagamento.getNome() +",\r\n"
		  		+ "        \"email\": \"jose@email.com\",\r\n"
		  		+ "        \"address\": {\r\n"
		  		+ "          \"country\": "+ infoPagamento.getPais()+",\r\n"
		  		+ "          \"region\": "+ infoPagamento.getEstado()+",\r\n"
		  		+ "          \"region_code\": "+ infoPagamento.getEstado()+",\r\n"
		  		+ "          \"city\": "+ infoPagamento.getCidade()+",\r\n"
		  		+ "          \"postal_code\": "+ infoPagamento.getCep()+",\r\n"
		  		+ "          \"street\": "+ infoPagamento.getRua()+",\r\n"
		  		+ "          \"number\": "+ infoPagamento.getNumero()+",\r\n"
		  		+ "          \"locality\": "+ infoPagamento.getBairro()+"\r\n"
		  		+ "        }\r\n"
		  		+ "      }\r\n"
		  		+ "    }\r\n"
		  		+ "  },\r\n"
		  		+ "  \"notification_urls\": [\r\n"
		  		+ "    \"https://yourserver.com/nas_ecommerce/277be731-3b7c-4dac-8c4e-4c3f4a1fdc46/\"\r\n"
		  		+ "  ]\r\n"
		  		+ "}")
		  .asString();

		System.out.println(response);
	}

}
