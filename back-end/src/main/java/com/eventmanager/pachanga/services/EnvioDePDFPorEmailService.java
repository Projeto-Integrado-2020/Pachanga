package com.eventmanager.pachanga.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Usuario;
import com.eventmanager.pachanga.dtos.PDFPorEmailTO;
import com.eventmanager.pachanga.errors.ValidacaoException;
import com.eventmanager.pachanga.repositories.FestaRepository;
import com.eventmanager.pachanga.repositories.UsuarioRepository;
import com.eventmanager.pachanga.tipo.TipoPermissao;
import com.eventmanager.pachanga.utils.EmailMensagem;


@Service
@Transactional
public class EnvioDePDFPorEmailService {
	
	@Autowired
	private EmailMensagem emailMensagem;
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private FestaRepository festaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	public boolean enviarRelatorio(PDFPorEmailTO pdfPorEmailTO, int codUsuario,  int codFesta) {
		grupoService.validarPermissaoUsuarioGrupo(codFesta, codUsuario, TipoPermissao.EXPOREPO.getCodigo());
		Festa festa = festaRepository.findByCodFesta(codFesta);
		Usuario usuario = usuarioRepository.findById(codUsuario);
		File pdf = byteArrayToPDF(base64ToByteArray(pdfPorEmailTO.getBase64Pdf()));
		emailMensagem.enviarPDFRelatorio(pdfPorEmailTO.getListaDeEmails(), pdf, usuario, festa);
		pdf.delete();
		return true;
	}

	private byte[] base64ToByteArray(String encode) {
		return Base64.getDecoder().decode(encode);
	}
	
	private File byteArrayToPDF(byte[] byteArray) {
		String filename = "relatorio.pdf";
		File pdf = new File(filename);
		OutputStream out;
		
		try {
			out = new FileOutputStream(filename);
			out.write(byteArray);
			out.close();
		} catch (FileNotFoundException e) {
			pdf.delete();
			throw new ValidacaoException("ENCODEPR"); // problema no encode
		} catch (IOException e) {
			pdf.delete();
			throw new ValidacaoException("ENCODEPR"); // problema no encode
		}
		
		return pdf;
	}
}

