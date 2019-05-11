package org.app.messaging.entity;

import java.sql.Timestamp;

public class MessageInfo {
	private Integer id;
	private String info;
	private String msgOwner;
	private Boolean isPublic;
	private Timestamp createdTS;
	private Timestamp updatedTS;
	private String agent;
	private String comment;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getMsgOwner() {
		return msgOwner;
	}
	public void setMsgOwner(String msgOwner) {
		this.msgOwner = msgOwner;
	}
	public Boolean getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}
	public Timestamp getCreatedTS() {
		return createdTS;
	}
	public void setCreatedTS(Timestamp createdTS) {
		this.createdTS = createdTS;
	}
	public Timestamp getUpdatedTS() {
		return updatedTS;
	}
	public void setUpdatedTS(Timestamp updatedTS) {
		this.updatedTS = updatedTS;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "MessageInfo [id=" + id + ", info=" + info + ", msgOwner=" + msgOwner + ", isPublic=" + isPublic
				+ ", createdTS=" + createdTS + ", updatedTS=" + updatedTS + ", agent=" + agent + ", comment=" + comment
				+ "]";
	}

}
