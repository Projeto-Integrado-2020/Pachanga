package com.eventmanager.pachanga.controllers;	

import java.io.IOException;	

import org.springframework.beans.factory.annotation.Autowired;	
import org.springframework.http.ResponseEntity;	
import org.springframework.stereotype.Controller;	
import org.springframework.web.bind.annotation.CrossOrigin;	
import org.springframework.web.bind.annotation.PostMapping;	
import org.springframework.web.bind.annotation.RequestBody;	
import org.springframework.web.bind.annotation.RequestMapping;	
import org.springframework.web.bind.annotation.RequestParam;	
import org.springframework.web.bind.annotation.ResponseBody;	

import com.eventmanager.pachanga.dtos.InsercaoIngresso;	
import com.eventmanager.pachanga.errors.ValidacaoException;	
import com.eventmanager.pachanga.services.PayPalService;	

@Controller	
@RequestMapping("/paypal")	
@CrossOrigin	
public class PayPalController {	

	@Autowired	
	private PayPalService paypalService;	

	@ResponseBody	
	@PostMapping(path = "/authorizeOrder")	
	public ResponseEntity<Object> authorizeOrder(@RequestParam(required = true) String orderId,	
			@RequestBody InsercaoIngresso insercaoIngresso) throws IOException {	
		try {	
			return ResponseEntity.ok(paypalService.authorizeOrder(orderId, insercaoIngresso, true));	
		} catch (ValidacaoException e) {	
			return ResponseEntity.status(400).body("{\"error\": \""+ e.getMessage() + "\"}");	
		}	
	}	

}