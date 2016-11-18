package EmailNotification.EmailNotification;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender{
	final static String config = "config.properties";
	
	public static void sender() throws IOException{
		InputStream in = new FileInputStream(config);
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Please enter the destination email address: ");
		String destination = scanner.nextLine();
		
		System.out.println("Please enter your subject: ");
		String subject = scanner.nextLine();
		
		System.out.println("Please enter your content: ");
		String content = scanner.nextLine();
		
		final Properties props = new Properties();
		props.load(in);
		
		Session session = Session.getInstance(props, new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(props.getProperty("mail.smtp.username"), props.getProperty("mail.smtp.password"));
			}
		});
		
		try{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress((String) props.get("mail.smtp.username")));
			message.setRecipients(Message.RecipientType.TO, 
					InternetAddress.parse(destination));
			message.setSubject(subject);
			message.setText(content);
			
			Transport.send(message);
		}catch(MessagingException e){
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) throws IOException{
		sender();
	}
}