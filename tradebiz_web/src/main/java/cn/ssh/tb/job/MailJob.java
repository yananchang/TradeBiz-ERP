package cn.ssh.tb.job;

import java.util.Date;

/**
 * 定义了一个任务类
 * @author Yanan Chang
 *
 */
public class MailJob {

	public void send() throws Exception{
		System.out.println("Job executed..."+ new Date());
	}
}
