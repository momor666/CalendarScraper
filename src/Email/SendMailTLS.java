package Email;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import util.ConfigurationUtil;

public class SendMailTLS {
	private static String messageBody = "";
	 private static  String username;
	 private static  String  password; 
	 private static String toEmail;
	 private static String fromEmail;
	
	 public static boolean email_enabled = true;
	
	public static void sendMail(String s) {
		
			ConfigurationUtil config = new ConfigurationUtil();
		
			Properties props = new Properties();
				username = config.getEmail_from();
				password = config.getEmail_password();
				fromEmail = config.getEmail_from();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.port", "587");
		
			
			
			
			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,
									password);
						}
					});

			try {
		        MimeMessage message = new MimeMessage(session);

		         // Set From: header field of the header.
		         message.setFrom(new InternetAddress(fromEmail));

		         // Set To: header field of the header.
		         message.addRecipients(Message.RecipientType.TO,
							InternetAddress.parse(config.getEmail_to()));
		         				
		         
		         
		         message.setSubject(s);

		         // Create the message part 
		         BodyPart messageBodyPart = new MimeBodyPart();

		         // Fill the message
//		         messageBodyPart.setText("Failed Parsing. Software Version:" + WebText.VERSION);
		         messageBodyPart.setText("Please visit "+ config.getWeb());
		         
		         // Create a multipar message
		         Multipart multipart = new MimeMultipart();

		         // Set text message part
		         multipart.addBodyPart(messageBodyPart);

		        
		         // Send the complete message parts
		         message.setContent(multipart);

		         // Send message
		         Transport.send(message);
		         
//		         return true;
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}