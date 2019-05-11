package org.app.messaging.entity;

public class UserInfo {
	private Integer id;
	private String extId;
	private String name;
	private String email;
	private String mobile;
	private String password;
	private Boolean isActive;
	private String createdTS;
	private String updatedTS;
	private String agent;
	private String comment;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getExtId() {
		return extId;
	}
	public void setExtId(String extId) {
		this.extId = extId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getCreatedTS() {
		return createdTS;
	}
	public void setCreatedTS(String createdTS) {
		this.createdTS = createdTS;
	}
	public String getUpdatedTS() {
		return updatedTS;
	}
	public void setUpdatedTS(String updatedTS) {
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
		return "UserInfo [id=" + id + ", extId=" + extId + ", name=" + name + ", email=" + email + ", mobile=" + mobile
				+ ", password=" + password + ", isActive=" + isActive + ", createdTS=" + createdTS + ", updatedTS="
				+ updatedTS + ", agent=" + agent + ", comment=" + comment + "]";
	}
}
