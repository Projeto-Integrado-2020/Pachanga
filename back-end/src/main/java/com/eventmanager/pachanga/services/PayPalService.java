package com.eventmanager.pachanga.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braintreepayments.http.HttpResponse;
import com.eventmanager.pachanga.dtos.IngressoTO;
import com.eventmanager.pachanga.dtos.InsercaoIngresso;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.Capture;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.paypal.orders.OrderActionRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.PurchaseUnit;

@Service
@Transactional
public class PayPalService {

	@Autowired
	private IngressoService ingressoService;

	private PayPalEnvironment environment = new PayPalEnvironment.Sandbox(
			"AUxyHzPBLdUiT3YoqNfrdqrKrI7PU3mJAjt35B3cL4B8otyw1V6w_A3V4JQa08V2NM12obeNm2CKnH_G",
			"EOokQ3Nw0RdFDDRXkyPAfvZwfzD8qKuOAC32KY2o0n00cVDgcu0FVJGD3S8KOMniCpzIfKFgd7EY9YGg");

	PayPalHttpClient client = new PayPalHttpClient(environment);

	public PayPalHttpClient client() {
		return this.client;
	}

	public List<IngressoTO> authorizeOrder(String orderId, InsercaoIngresso ingressosInsercao, boolean debug) throws IOException {
		List<IngressoTO> ingressos = ingressoService.addListaIngresso(ingressosInsercao);
		OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
		request.requestBody(buildRequestBody());
		//3. Call PayPal to capture an order
		HttpResponse<Order> response = client().execute(request);
		//4. Save the capture ID to your database. Implement logic to save capture to your database for future reference.
		if (debug) {
			System.out.println("Status Code: " + response.statusCode());
			System.out.println("Status: " + response.result().status());
			System.out.println("Order ID: " + response.result().id());
			System.out.println("Links: ");
			for (LinkDescription link : response.result().links()) {
				System.out.println("\t" + link.rel() + ": " + link.href());
			}
			System.out.println("Capture ids:");
			for (PurchaseUnit purchaseUnit : response.result().purchaseUnits()) {
				for (Capture capture : purchaseUnit.payments().captures()) {
					System.out.println("\t" + capture.id());
				}
			}
		}
		return ingressos;
	}

	private OrderActionRequest buildRequestBody() {
		return new OrderActionRequest();
	}

}
