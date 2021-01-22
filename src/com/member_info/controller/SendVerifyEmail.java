package com.member_info.controller;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class SendVerifyEmail {
	public void sendEmail(String email, String msg, String name) {
		Properties properties=new Properties();
		
		//設定傳輸協定為smtp
		properties.setProperty("mail.transport.protocol", "SMTP");
		
		//Gamil的SMTP主機
		properties.setProperty("mail.host", "smtp.gmail.com");
		
		//SSL
		properties.setProperty("mail.smtp.ssl.enable", "true");
		
		//使用auth驗證
		properties.setProperty("mail.smtp.auth", "true");
		
		
		//google身分驗證(傳送方)
		Authenticator authenticator=new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				//帳號及密碼
				return new PasswordAuthentication("tapebetea102g2", "tea102g2");
			}
		};
		
		//取得一個session,利用上方的設定及Authenticator
		Session session=Session.getInstance(properties, authenticator);
		
		//設置寄件者
		Message message=new MimeMessage(session);
		
		try {
			//以下設置要寄件的內容、對方EMAIL等資料
			message.setFrom(new InternetAddress("tapebetea102g2@gmail.com"));
			message.setRecipient(RecipientType.TO, new InternetAddress(email));
			message.setSubject(name+",您好-Tape驗證信");
			message.setContent(msg, "text/html;charset=UTF-8");
			Transport.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
