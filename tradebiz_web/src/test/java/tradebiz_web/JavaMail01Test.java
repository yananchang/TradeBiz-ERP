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

public class JavaMail01Test {

	@Test
	public void testJavaMail() throws Exception {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.126.com");  //指定邮件的发送服务器地址
		props.put("mail.smtp.auth", "true");    //服务器是否要验证用户的身份信息

		Session session = Session.getInstance(props);  //得到session
		session.setDebug(true);   //代表启用debug模式, 可以在控制台输出smtp协议应答的过程
		
		//创建一个MimeMessage格式的邮件
		MimeMessage message = new MimeMessage(session);
		//设置发送者
		Address fromAddress = new InternetAddress("rango@126.com"); //邮件地址
		message.setFrom(fromAddress);  //设置发送的邮件地址
		
		//设置接收者
		Address toAddress = new InternetAddress("394129483@qq.com"); //邮件地址
		message.setRecipient(RecipientType.TO, toAddress);   //设置接收者的地址
		
		//设置邮件的主题
		message.setSubject("Hi,this is a test email");
		//设置邮件的内容
		message.setText("test mail, send with Java code");
		
		//保存邮件
		message.saveChanges();
		
		//得到发送邮件的火箭
		Transport transport = session.getTransport("smtp");
		
		//火箭连接到服务器上
		transport.connect("smtp.126.com", "rango@126.com", "sorry");
		
		//火箭点火, 发送
		transport.sendMessage(message, message.getAllRecipients());
		
		
		//关闭通道
		transport.close();
		
	}
}
