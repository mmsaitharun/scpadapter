package com.incture.workbox.scpadapter.events;

import java.util.Date;

public class ProcessEventsDo implements BaseEventInterface {

	public ProcessEventsDo() {
		super();
	}

	private String processId;
	private String name;
	private String subject;
	private String status;
	private String startedBy;
	private Date startedAt;
	private Date completedAt;
	private String requestId;
	private String startedByDisplayName;

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartedBy() {
		return startedBy;
	}

	public void setStartedBy(String startedBy) {
		this.startedBy = startedBy;
	}

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}

	public Date getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(Date completedAt) {
		this.completedAt = completedAt;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getStartedByDisplayName() {
		return startedByDisplayName;
	}

	public void setStartedByDisplayName(String startedByDisplayName) {
		this.startedByDisplayName = startedByDisplayName;
	}

	@Override
	public String toString() {
		return "ProcessEventsDo [processId=" + processId + ", name=" + name + ", subject=" + subject + ", status="
				+ status + ", startedBy=" + startedBy + ", startedAt=" + startedAt + ", completedAt=" + completedAt
				+ ", requestId=" + requestId + ", startedByDisplayName=" + startedByDisplayName + "]";
	}

}
