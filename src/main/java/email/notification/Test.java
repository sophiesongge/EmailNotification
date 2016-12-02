package email.notification;

import email.notification.EmailSender;

public class Test{
	public static void main(String[] args){
		EmailSender sender = new EmailSender();
		sender.sender();		
	}
}