package email.notification;

import java.io.IOException;

import email.notification.EmailSender;

public class Test {
	public static void main(String[] args) throws IOException {
		EmailSender sender = new EmailSender();
		sender.sender();
	}
}