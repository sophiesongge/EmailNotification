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

class EmailConfiguration{
	private String path;
	
	private static EmailConfiguration configuration;
	
	private EmailConfiguration(){
		path = "config.properties";
	}
	
	public static synchronized EmailConfiguration getInstance(){
		if(configuration == null){
			configuration = new EmailConfiguration();
		}
		return configuration;
	}
	
	public String getConfigPath(){
		return path;
	}
}

public class EmailSender{
	final static String config = EmailConfiguration.getInstance().getConfigPath();
	private Scanner scanner;
	
	public void sender() throws IOException{
		InputStream in = new FileInputStream(config);
		
		scanner = new Scanner(System.in);
		
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
		EmailSender sender = new EmailSender();
		sender.sender();
	}
}