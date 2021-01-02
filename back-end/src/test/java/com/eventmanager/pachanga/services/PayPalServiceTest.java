package com.eventmanager.pachanga.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.test.context.junit4.SpringRunner;

import com.braintreepayments.http.HttpResponse;
import com.eventmanager.pachanga.dtos.FestaTO;
import com.eventmanager.pachanga.dtos.IngressoTO;
import com.eventmanager.pachanga.dtos.InsercaoIngresso;
import com.eventmanager.pachanga.securingweb.JwtAuthenticationEntryPoint;
import com.eventmanager.pachanga.securingweb.JwtTokenUtil;
import com.eventmanager.pachanga.securingweb.JwtUserDetailsService;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.Capture;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.paypal.orders.PaymentCollection;
import com.paypal.orders.PurchaseUnit;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PayPalService.class)
public class PayPalServiceTest {
	
	@Autowired
	private PayPalService paypalService;
	
	@MockBean
	private IngressoService ingressoService;

	@MockBean
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@MockBean
	private JwtUserDetailsService defaultJwtUserDetailsService;

	@MockBean
	private JwtTokenUtil defaultJwtTokenUtil;

	@MockBean
	private JwtAuthenticationEntryPoint defaultJwtAuthenticationEntryPoint;
	
	private List<IngressoTO> ingressosToTeste() {
		IngressoTO ingresso = new IngressoTO();
		FestaTO festa = new FestaTO();
		festa.setCodFesta(1);
		ingresso.setFesta(festa);
		ingresso.setCodUsuario(2);
		ingresso.setCodLote(1);
		ingresso.setStatusIngresso("U");
		ingresso.setStatusCompra("P");
		ingresso.setNomeTitular("Teste");
		ingresso.setEmailTitular("teste@teste.com");
		ingresso.setCodIngresso("codIngresso");
		List<IngressoTO> ingressos = new ArrayList<IngressoTO>();
		ingressos.add(ingresso);
		return ingressos;
	}
	
	private Order orderTest() {
		Order order = new Order();
		order.status("OK");
		order.id("Id Teste");
		
		List<LinkDescription> links = new ArrayList<LinkDescription>();
		LinkDescription link = new LinkDescription();
		link.href("href");
		link.rel("rel");
		links.add(link);
		
		order.links(links);
		List<PurchaseUnit> purchaseUnits = new ArrayList<PurchaseUnit>();
		PurchaseUnit purchaseUnit = new PurchaseUnit();
		PaymentCollection paymentCollection = new PaymentCollection();
		List<Capture> captures = new ArrayList<Capture>();
		captures.add(new Capture().id("id"));
		paymentCollection.captures(captures);
		purchaseUnit.payments(paymentCollection);
		purchaseUnits.add(purchaseUnit);
		order.purchaseUnits(purchaseUnits);
		
		return order;
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	void authorizeOrder() throws IOException {
		paypalService.client = Mockito.mock(PayPalHttpClient.class);
		Mockito.when(ingressoService.addListaIngresso(Mockito.any())).thenReturn(ingressosToTeste());
		HttpResponse<Object> response = Mockito.mock(HttpResponse.class);
		Mockito.when(response.result()).thenReturn(orderTest());
		Mockito.when(paypalService.client().execute(Mockito.any())).thenReturn(response);
		
		InsercaoIngresso insercao = new InsercaoIngresso();
		insercao.setListaIngresso(ingressosToTeste());
		List<IngressoTO> ingressos = paypalService.authorizeOrder(Mockito.anyString(), insercao, true);
	
		assertEquals(ingressos.size(), ingressosToTeste().size());
	}

}
