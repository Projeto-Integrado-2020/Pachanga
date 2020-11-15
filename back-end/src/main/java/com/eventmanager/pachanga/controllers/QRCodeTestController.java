package com.eventmanager.pachanga.controllers;

import java.awt.image.BufferedImage;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eventmanager.pachanga.utils.EmailMensagem;
import com.eventmanager.pachanga.utils.QRCodeManager;

@Controller
@RequestMapping("/QRCode")
@CrossOrigin
public class QRCodeTestController {
	
		@Bean
		public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
			return new BufferedImageHttpMessageConverter();
		}
	
		@GetMapping(path ="/test")
		public ResponseEntity<BufferedImage> cadastro(@RequestParam(required = true) String barcode) throws Exception {
			  BufferedImage image = QRCodeManager.generateQRCodeImage(barcode);	
			  EmailMensagem email = new EmailMensagem();
			  email.enviarEmailQRCodeTest();
			  return ResponseEntity.ok(image);
		}
		
		

}
