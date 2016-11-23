package email.notification;

import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import email.notification.EmailConfiguration;;

public class EmailSender {
	final static String config = EmailConfiguration.getInstance().getConfigPath();
	private Scanner scanner;

	public void sender() throws RuntimeException {

		scanner = new Scanner(System.in);

		System.out.println("Please enter the destination email address: ");
		String destination = scanner.nextLine();

		System.out.println("Please enter your subject: ");
		String subject = scanner.nextLine();

		System.out.println("Please enter your content: ");
		String content = scanner.nextLine();

		final Properties props = EmailConfiguration.getInstance().getConfigProperties();

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(props.getProperty("mail.smtp.username"),
						props.getProperty("mail.smtp.password"));
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress((String) props.get("mail.smtp.username")));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destination));
			message.setSubject(subject);
			message.setText(content);

			Transport.send(message);
		} catch (AddressException e) {
			throw new RuntimeException("Malformed email address: ", e);
		} catch (MessagingException e) {
			throw new RuntimeException("Error Sending Email");
		}
	}

}