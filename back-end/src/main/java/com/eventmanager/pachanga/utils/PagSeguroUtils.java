package com.eventmanager.pachanga.utils;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.dtos.InfoPagamentoBoletoTO;
import com.eventmanager.pachanga.errors.ValidacaoException;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import okhttp3.MediaType;

public class PagSeguroUtils {

	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	public static final String CREDENTIAL = "E8F72599309B4733A2084C38BF60866C";

	public static String criacaoBoleto(InfoPagamentoBoletoTO infoPagamento, float preco, String codBoleto, Festa festa) {

		LocalDateTime dataVencimento = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
		dataVencimento = dataVencimento.plusDays(3);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		HttpResponse<String> response = Unirest.post("https://sandbox.api.pagseguro.com/charges")
				.header("Authorization", CREDENTIAL).header("Content-Type", "application/json")
				.header("x-api-version", "4.0").header("x-idempotency-key", "")
				.body("{\r\n" + "  \"reference_id\": \"" + codBoleto + "\",\r\n"
						+ "  \"description\": \"Ingresso para a festa " + festa.getNomeFesta() + "\",\r\n"
						+ "  \"amount\": {\r\n" + "    \"value\":" + Float.toString(preco).replace(".", "") + "0"
						+ ",\r\n" + "    \"currency\": \"BRL\"\r\n" + "  },\r\n" + "  \"payment_method\": {\r\n"
						+ "    \"type\": \"BOLETO\",\r\n" + "    \"boleto\": {\r\n" + "      \"due_date\": \""
						+ dataVencimento.format(formatter) + "\",\r\n" + "      \"instruction_lines\": {\r\n"
						+ "        \"line_1\": \"Pagamento processado para DESC Fatura\",\r\n"
						+ "        \"line_2\": \"Via PagSeguro\"\r\n" + "      },\r\n" + "      \"holder\": {\r\n"
						+ "        \"name\": \"" + infoPagamento.getNome() + "\",\r\n" + "        \"tax_id\": \""
						+ infoPagamento.getCpf() + "\",\r\n" + "        \"email\": \"" + infoPagamento.getEmail()
						+ "\",\r\n" + "        \"address\": {\r\n" + "          \"country\": \""
						+ infoPagamento.getPais() + "\",\r\n" + "          \"region\": \"" + infoPagamento.getEstado()
						+ "\",\r\n" + "          \"region_code\": \"" + infoPagamento.getEstado() + "\",\r\n"
						+ "          \"city\": \"" + infoPagamento.getCidade() + "\",\r\n"
						+ "          \"postal_code\": \"" + infoPagamento.getCep() + "\",\r\n"
						+ "          \"street\": \"" + infoPagamento.getRua() + "\",\r\n" + "          \"number\": \""
						+ infoPagamento.getNumero() + "\",\r\n" + "          \"locality\": \""
						+ infoPagamento.getBairro() + "\"\r\n" + "        }\r\n" + "      }\r\n" + "    }\r\n"
						+ "  },\r\n" + "  \"notification_urls\": [\r\n"
						+ "    \"https://pachanga-back-end.herokuapp.com/ingresso/updateStatusCompra?codBoleto="+codBoleto+"\"\r\n"
						+ "  ]\r\n" + "}")
				.asString();
		
		JSONObject obj = new JSONObject(response.getBody());
		String urlBoletoPdf = obj.getJSONArray("links").getJSONObject(0).getString("href");
		
		if (response.getStatus() != 201) {
			throw new ValidacaoException(response.getBody());
		}
		return urlBoletoPdf;
	}

	public static int retornoDadosTransacao(String notificationCode) {
		// "https://ws.sandbox.pagseguro.uol.com.br/v3/transactions/notifications/"+notificationCode+"?email=guga.72@hotmail.com&token="+CREDENTIAL+""
		HttpResponse<String> response = Unirest.get(
				"https://ws.sandbox.pagseguro.uol.com.br/v3/transactions/notifications/2C019F5E0E630E635347740A4FA5E63D7DB9?email=andrey-lacerda@hotmail.com&token=3B95AB63B9A94FEFAD7E95349B62FFAA")
				.asString();
		
		DocumentBuilderFactory fabrica = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        int status = 0;
		try {
			builder = fabrica.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(response.getBody())));
			
			NodeList statusPagamento = document.getElementsByTagName("status");
			
			Element element= (Element) statusPagamento.item(0);
			status = Integer.parseInt(element.getTextContent());
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
		
	}

}
