package email.notification;

import java.util.Properties;
import java.util.Scanner;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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

		System.out.println("Please enter the path of the file that will be attached (skip if no attachment is required): ");
		String attachmentFile = scanner.nextLine();

		System.out.println("Please enter the attachment file name that will be appeared (skip if no attachment is required, default name will be attachment.txt): ");
		String attachmentName = scanner.nextLine();

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

			String file = attachmentFile;
			String fileName = "attachment.txt";
			if(attachmentName!=null && !attachmentName.equals("")) {
				fileName = attachmentName;
			}

			if(file != null && !file.equals("")) {
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				Multipart multipart = new MimeMultipart();
				DataSource source = new FileDataSource(file);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(fileName);
				multipart.addBodyPart(messageBodyPart);
				message.setContent(multipart);
			}

			Transport.send(message);
		} catch (AddressException e) {
			throw new RuntimeException("Malformed email address: ", e);
		} catch (MessagingException e) {
			throw new RuntimeException("Error Sending Email", e);
		}
	}

}