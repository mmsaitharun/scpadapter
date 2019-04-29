package com.incture.workbox.scpadapter.events;

import java.util.Date;

public class TaskEventsDo implements BaseEventInterface {

	public TaskEventsDo() {
		super();
	}

	public TaskEventsDo(String eventId) {
		this.eventId = eventId;
	}

	public TaskEventsDo(String eventId, String status, String currentProcessor) {
		this.eventId = eventId;
		this.status = status;
		this.currentProcessor = currentProcessor;
	}

	private String eventId;
	private String processId;
	private String description;
	private String subject;
	private String name;
	private String status;
	private String currentProcessor;
	private String priority;
	private Date createdAt;
	private Date completedAt;
	private Date completionDeadLine;
	private String currentProcessorDisplayName;
	private String processName;
	private String statusFlag;
	private String taskMode;
	private String taskType;
	private Date forwardedAt;
	private String forwardedBy;
	private String url;
	private String origin;
	private Date slaDueDate;
	private Date updatedAt;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCurrentProcessor() {
		return currentProcessor;
	}

	public void setCurrentProcessor(String currentProcessor) {
		this.currentProcessor = currentProcessor;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(Date completedAt) {
		this.completedAt = completedAt;
	}

	public Date getCompletionDeadLine() {
		return completionDeadLine;
	}

	public void setCompletionDeadLine(Date completionDeadLine) {
		this.completionDeadLine = completionDeadLine;
	}

	public String getCurrentProcessorDisplayName() {
		return currentProcessorDisplayName;
	}

	public void setCurrentProcessorDisplayName(String currentProcessorDisplayName) {
		this.currentProcessorDisplayName = currentProcessorDisplayName;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	public String getTaskMode() {
		return taskMode;
	}

	public void setTaskMode(String taskMode) {
		this.taskMode = taskMode;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public Date getForwardedAt() {
		return forwardedAt;
	}

	public void setForwardedAt(Date forwardedAt) {
		this.forwardedAt = forwardedAt;
	}

	public String getForwardedBy() {
		return forwardedBy;
	}

	public void setForwardedBy(String forwardedBy) {
		this.forwardedBy = forwardedBy;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Date getSlaDueDate() {
		return slaDueDate;
	}

	public void setSlaDueDate(Date slaDueDate) {
		this.slaDueDate = slaDueDate;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "TaskEventsDo [eventId=" + eventId + ", processId=" + processId + ", status=" + status
				+ ", currentProcessor=" + currentProcessor + ", createdAt=" + createdAt + ", completedAt=" + completedAt
				+ ", currentProcessorDisplayName=" + currentProcessorDisplayName + ", processName=" + processName + "]";
	}

}
