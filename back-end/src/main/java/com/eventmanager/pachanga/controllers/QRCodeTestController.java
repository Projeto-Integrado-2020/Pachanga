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

import com.eventmanager.pachanga.domains.Festa;
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
			  Festa festa = new Festa();
			  festa.setNomeFesta("Tal");
			  EmailMensagem email = new EmailMensagem();
			  //email.enviarEmailQRCode("andrey-lacerda@hotmail.com", "1234567891234", festa);
			  //email.enviarEmailQRCode("guga.72@hotmail.com", "1234567891234", festa);
			  //email.enviarEmailQRCode("opedrofreitas@gmail.com", "1234567891234", festa);
			  return ResponseEntity.ok(image);
		}
		
		

}
