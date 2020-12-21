package com.eventmanager.pachanga.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.domains.Ingresso;
import com.eventmanager.pachanga.domains.Lote;
import com.eventmanager.pachanga.pdfbox.BaseTable;
import com.eventmanager.pachanga.pdfbox.Cell;
import com.eventmanager.pachanga.pdfbox.Row;

public class PdfConviteManager {
	
	public static final String pdfPath = "src/main/resources/arquivos/pdf/";
	public static final String qrCodePath = "src/main/resources/arquivos/png/";
	
	//public static final Color roxo = Color.getHSBColor(276, 79, 61);
	//public static final Color magenta = Color.getHSBColor(336, 79, 61);
	
	public static final Color roxo = Color.RED;
	public static final Color magenta = Color.BLUE;
	
	public static File GerarPDF(List<Ingresso> ingressos) {
	
		PDDocument pdf = new PDDocument();
		String path = null;
	    	
	    try {	
	    	for(Ingresso ingresso : ingressos){
		    	Festa festa = ingresso.getFesta();
		    	
		    	PDPage page = new PDPage();
			    pdf.addPage(page);
			    PDPageContentStream content = new PDPageContentStream(pdf, page);
		    	
		    	addToTitlePachanga(page, content, pdf);
		    	addToTitleUrl(page, content, pdf);
		    	AddDadosFesta(page, pdf, festa);
		    	AddDadosIngresso(page, pdf, ingresso);
		    	AddDadosParticipante(page, pdf, ingresso);
		    	inserirQRCode(page, content, pdf, ingresso);
		    	
		    	content.close();
	    	}	
	    	path = pdfPath + ingressos.get(0).getCodIngresso() + ".pdf";
	    	pdf.save(path);
	    	pdf.close();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    
	    if(path != null) {
	    	return new File(path);
	    }
	    return null;
	    	
	 }
	    
	 private static void addToTitlePachanga(PDPage page, PDPageContentStream content, PDDocument pdf) {
		 try {
			 String title = "Pachanga";
	         PDFont font = PDType1Font.HELVETICA_BOLD;  
	         int fontSize = 20;
             PDRectangle mediaBox = page.getMediaBox();
	         float titleWidth = font.getStringWidth(title) / 1000 * fontSize;
	         float startX = (mediaBox.getWidth() - titleWidth) / 2;
	         float startY = 740;
	         
	         content.setFont(font, fontSize);
	         content.beginText();
	         content.setNonStrokingColor(magenta);
	         content.newLineAtOffset(startX, startY);
	         content.showText(title);
	         content.endText(); 
	       } catch (IOException e){
	    	   e.printStackTrace();
	       }
	   }
	   
	  private static void addToTitleUrl(PDPage page, PDPageContentStream content, PDDocument pdf) {
		   try {
	           String title = "https://pachanga.herokuapp.com/";
	           PDFont font = PDType1Font.HELVETICA_BOLD;
	           int fontSize = 10;
	           PDRectangle mediaBox = page.getMediaBox();
	           float titleWidth = font.getStringWidth(title) / 1000 * fontSize;

	           float startX = (mediaBox.getWidth() - titleWidth) / 2;
	           float startY = 720;

	           content.setFont(font, fontSize);
	           content.beginText();
	           content.setNonStrokingColor(Color.BLACK);
	           content.newLineAtOffset(startX, startY);
	           content.showText(title);
	           content.endText();
	           
	       } catch (IOException e){
	    	   e.printStackTrace();
	       }
	   }
	   
	  private static void AddDadosFesta(PDPage page, PDDocument doc, Festa festa) throws IOException {
		   float margin = 60;
		   float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
		   float yStart = 700;
		   float bottomMargin = 70;
		   float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
		   boolean drawContent = true;
		  
		   
		   BaseTable table = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, doc, page, true, drawContent);
		   Row<PDPage> headerRow = table.createRow(15f);
		   headerRow.setHeight(50);
		   Cell<PDPage> cell = headerRow.createCell(100, " " + festa.getNomeFesta(), 25);
		   cell.setFont(PDType1Font.HELVETICA_BOLD);
		   cell.setTextColor(Color.BLUE);
		   table.addHeaderRow(headerRow);
		   
		   Row<PDPage> row = table.createRow(10f);
		   cell = row.createCell(100, "  Horário da festa: " 
		                              + prettyPrint(festa.getHorarioInicioFesta()) 
		                              + " - " 
		                              + prettyPrint(festa.getHorarioFimFesta()) , 10);
		   
		   row = table.createRow(10f);
		   cell = row.createCell(100, "  Local: " + festa.getCodEnderecoFesta() , 10);	
		   table.draw();   
		   
	   }
	   
	  private static void AddDadosIngresso(PDPage page, PDDocument doc, Ingresso ingresso) throws IOException {
		   Lote lote = ingresso.getLote();
		   float margin = 60;
		   float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
		   float yStart = 590;
		   float leght = (float) 55.00;
		   float bottomMargin = 70;
		   float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
		   boolean drawContent = true;
		  
		   
		   BaseTable table = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, doc, page, true, drawContent);
		   Row<PDPage> headerRow = table.createRow(15f);
		   headerRow.setHeight(30);
		   Cell<PDPage> cell = headerRow.createCell(leght, " Ingresso", 15, true, false);
		   
		   cell.setFont(PDType1Font.HELVETICA_BOLD);
		   cell.setTextColor(roxo);
		   table.addHeaderRow(headerRow);
		   
		   Row<PDPage> row = table.createRow(10f);
		   row.setHeight(30);
		   cell = row.createCell(leght, "  Lote: " + lote.getNomeLote() , 10, false, false);
		   
		   row = table.createRow(10f);
		   row.setHeight(30);
		   cell = row.createCell(leght, "  Valor pago: " + prettyPrint(lote.getPreco()) , 10, false, false);
		   
		   row = table.createRow(10f);
		   cell = row.createCell(leght, "                             "
		   		+ "           Comprado dia " + prettyPrint(ingresso.getDataCompra()) , 10, false, true);
		   
		   table.draw();   
		   
	   }
	   
	   private static void AddDadosParticipante(PDPage page, PDDocument doc, Ingresso ingresso) throws IOException {
		   float margin = 60;
		   float leght = (float) 55.00;
		   float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
		   float yStart = 460;
		   float bottomMargin = 70;
		   float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
		   boolean drawContent = true;
		  
		   
		   BaseTable table = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, doc, page, true, drawContent);
		   
		   Row<PDPage> headerRow = table.createRow(15f);
		   headerRow.setHeight(30);
		   Cell<PDPage> cell = headerRow.createCell(leght, " Participante", 15, true, false);
		   cell.setFont(PDType1Font.HELVETICA_BOLD);
		   cell.setTextColor(roxo);
		   table.addHeaderRow(headerRow);
		   
		   Row<PDPage> row = table.createRow(10f);
		   row.setHeight(30);
		   cell = row.createCell(leght, "  Nome: " + ingresso.getNomeTitular(), 10, false, false);
		   
		   row = table.createRow(10f);
		   row.setHeight(30);
		   cell = row.createCell(leght, "  E-mail: " + ingresso.getEmailTitular() , 10, false, true);	
		   table.draw();   
		   
	   }
	   
	   private static void inserirQRCode(PDPage page, PDPageContentStream content, PDDocument pdf, Ingresso ingresso) throws IOException {   
	       //escreve texto abaixo do QRCode
		   String title = ingresso.getCodIngresso();
	       PDFont font = PDType1Font.HELVETICA_BOLD;
	       int fontSize = 10;

	       float startX = 430;
	       float startY = 365;
	       
	       content.setFont(font, fontSize);
	       content.beginText();
	       content.setNonStrokingColor(Color.BLACK);
	       content.newLineAtOffset(startX, startY);
	       content.showText(title);
	       content.endText();
	       
	       //insere QRCode
	       BufferedImage bufferedImage;
	       File file = new File(qrCodePath + ingresso.getCodIngresso() + ".png");
	       try {
	    	   bufferedImage = QRCodeManager.generateQRCodeImage(ingresso.getCodIngresso());
	    	   ImageIO.write(bufferedImage, "png", file);
			   PDImageXObject pdImage = PDImageXObject.createFromFileByContent(file, pdf);
			   content.drawImage(pdImage, 340, 380, 220, 220);
	       } catch (Exception e) {
			   e.printStackTrace();
	       } finally {
			   file.delete();
	       }
	   }
	   
	   private static String prettyPrint(LocalDateTime dataHora) {
		   if(dataHora != null) {
			   String dia = (dataHora.getDayOfMonth() < 10) ? "0" + dataHora.getDayOfMonth() : Integer.toString(dataHora.getDayOfMonth());
			   String mes = (dataHora.getMonthValue() < 10) ? "0" + dataHora.getMonthValue() : Integer.toString(dataHora.getMonthValue());
		       int ano = dataHora.getYear();
		       String hora = (dataHora.getHour() < 10) ? "0" + dataHora.getHour() : Integer.toString(dataHora.getHour());
		       String minuto = (dataHora.getMinute() < 10) ? "0" + dataHora.getMinute() : Integer.toString(dataHora.getMinute());
		   
		   	   return dia + "/" + mes + "/" + ano + ", " + hora + ":" + minuto;
		    }
		   return "";
	   }
	   
	   private static String prettyPrint(float preco) {
		   DecimalFormat decimalFormat = new DecimalFormat("#.00");
	       String numberAsString = decimalFormat.format(preco);
		   return numberAsString;
	   }
	  
}


