package org.app.messaging.entity;

import java.util.List;

public class UserMessages {
	
	private String extId;
	private List<MessageInfo> messages;
	public String getExtId() {
		return extId;
	}
	public void setExtId(String extId) {
		this.extId = extId;
	}
	public List<MessageInfo> getMessages() {
		return messages;
	}
	public void setMessages(List<MessageInfo> messages) {
		this.messages = messages;
	}
	@Override
	public String toString() {
		return "UserMessages [extId=" + extId + ", messages=" + messages + "]";
	}

}
