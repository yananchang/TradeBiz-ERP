package cn.ssh.tb.exception;

public class SysException extends Exception {

	private String message;

	public SysException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
	
}
