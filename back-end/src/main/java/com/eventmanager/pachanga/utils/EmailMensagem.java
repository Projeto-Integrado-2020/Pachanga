package com.eventmanager.pachanga.utils;

import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

import com.eventmanager.pachanga.domains.Festa;
import com.eventmanager.pachanga.errors.ValidacaoException;

@Component(value = "emailMensagem")
public class EmailMensagem {
	
	public void enviarEmail(String email, String nomeGrupo, Festa festa) {
		Properties props = new Properties();
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.socketFactory.port", "465");
	    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.port", "465");
	    props.put("mail.smtp.ssl.checkserveridentity", "true");
	 
	    Session session = Session.getInstance(props,
	      new javax.mail.Authenticator() {
	    	@Override
	           protected PasswordAuthentication getPasswordAuthentication() 
	           {
	                 return new PasswordAuthentication("eventmanager72@gmail.com","11443322");
	           }
	      });
	 
	    /** Ativa Debug para sessão */
	    session.setDebug(true);
	 
	    try {
	 
	      Message message = new MimeMessage(session);
	      message.setFrom(new InternetAddress("eventmanager72@gmail.com")); 
	      //Remetente
	 
	      Address[] toUser = InternetAddress //Destinatário(s)
	                 .parse(email);  
	 
	      message.setRecipients(Message.RecipientType.TO, toUser);
	      message.setSubject("Enviando email com JavaMail");//Assunto
	      
	      StringBuilder bodyEmail = new StringBuilder();
	      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	      bodyEmail.append("<h1><strong>Pachanga tem uma festa para voc&ecirc;!</strong></h1>\r\n" + 
		      		"\r\n" + 
		      		"<p>Voc&ecirc; foi convidado para participar de uma festa, como membro coladorador. Segue detalhes da festa:</p>\r\n" + 
		      		"\r\n" + 
		      		"<ul>\r\n" + 
		      		"    <li>Festa: "+ festa.getNomeFesta() +"; </li>\r\n" + 
		      		"    <li>Fun&ccedil;&atilde;o: "+nomeGrupo +"; </li>\r\n" + 
		      		"    <li>Data: "+ festa.getHorarioInicioFesta().format(formatter) +";</li>\r\n" + 
		      		"    <li>Local: "+ festa.getCodEnderecoFesta() +".</li>\r\n" + 
		      		"</ul>\r\n" + 
		      		"\r\n" + 
		      		"<p>Para aceitar ou recusar, basta se logar/cadastrar na aplica&ccedil;&atilde;o (clicando <a href=\"https://pachanga.herokuapp.com/\" target=\"_blank\">aqui</a>),&nbsp;acessar as notifica&ccedil;&otilde;es, e fazer a sua escolha.</p>\r\n" + 
		      		"\r\n" + 
		      		"<p>Esperamos&nbsp;que aproveite a festa!</p>\r\n" + 
		      		"\r\n" + 
		      		"<p><strong>Equipe Pachanga</strong></p>");
	      
	      message.setContent(bodyEmail.toString(), "text/html");
	      Transport.send(message);
	 	 
	     } catch (MessagingException e) {
	        throw new ValidacaoException(e.getMessage());
	    }
	}

}
