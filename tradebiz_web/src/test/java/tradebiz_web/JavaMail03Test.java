package tradebiz_web;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class JavaMail03Test {

	@Test
	public void testJavaMail() throws Exception {

		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext-mail.xml");
		JavaMailSender sender = (JavaMailSender) ac.getBean("mailSender");   //得到邮件的发送对象, 专门用于邮件发送
		
		//设置简单邮件对象的属性
		
		//发送邮件
//		sender.send(message);
	}
}
