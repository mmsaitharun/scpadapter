package com.incture.workbox.scpadapter.events;

public class TaskOwnersDo implements BaseEventInterface {

	public TaskOwnersDo() {
		super();
	}

	public TaskOwnersDo(TaskOwnersDoPK taskOwnersDoPK) {
		this.taskOwnersDoPK = taskOwnersDoPK;
	}

	public TaskOwnersDo(TaskOwnersDoPK taskOwnersDoPK, Boolean isProcessed) {
		this.taskOwnersDoPK = taskOwnersDoPK;
		this.isProcessed = isProcessed;
	}

	public TaskOwnersDo(TaskOwnersDoPK taskOwnersDoPK, Boolean isProcessed, Boolean enRoute) {
		this.taskOwnersDoPK = taskOwnersDoPK;
		this.isProcessed = isProcessed;
		this.enRoute = enRoute;
	}

	private TaskOwnersDoPK taskOwnersDoPK;

	private Boolean isProcessed;
	private Boolean isSubstituted;
	private Boolean enRoute;
	private String taskOwnerDisplayName;
	private String ownerEmail;

	public TaskOwnersDoPK getTaskOwnersDoPK() {
		return taskOwnersDoPK;
	}

	public void setTaskOwnersDoPK(TaskOwnersDoPK taskOwnersDoPK) {
		this.taskOwnersDoPK = taskOwnersDoPK;
	}

	public Boolean getIsProcessed() {
		return isProcessed;
	}

	public void setIsProcessed(Boolean isProcessed) {
		this.isProcessed = isProcessed;
	}

	public Boolean getIsSubstituted() {
		return isSubstituted;
	}

	public void setIsSubstituted(Boolean isSubstituted) {
		this.isSubstituted = isSubstituted;
	}

	public Boolean getEnRoute() {
		return enRoute;
	}

	public void setEnRoute(Boolean enRoute) {
		this.enRoute = enRoute;
	}

	public String getTaskOwnerDisplayName() {
		return taskOwnerDisplayName;
	}

	public void setTaskOwnerDisplayName(String taskOwnerDisplayName) {
		this.taskOwnerDisplayName = taskOwnerDisplayName;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	@Override
	public String toString() {
		return "TaskOwnersDo [taskOwnersDoPK=" + taskOwnersDoPK + ", isProcessed=" + isProcessed + ", isSubstituted="
				+ isSubstituted + ", enRoute=" + enRoute + ", taskOwnerDisplayName=" + taskOwnerDisplayName
				+ ", ownerEmail=" + ownerEmail + "]";
	}

}
