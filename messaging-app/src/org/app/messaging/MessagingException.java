package org.app.messaging;

public class MessagingException extends Exception {
	private String errorMsg;
	public MessagingException(String msg) {
		super(msg);
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	@Override
	public String toString() {
		return "MessagingException [errorMsg=" + errorMsg + "]";
	}

}
