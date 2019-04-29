package com.incture.workbox.scpadapter.objects;

import java.util.List;

public class WorkFlowParseResponse implements WorkFlowResponse {

	public WorkFlowParseResponse() {
	}

	public WorkFlowParseResponse(List<Task> tasks, List<Process> processes, int resultCount) {
		this.tasks = tasks;
		this.processes = processes;
		this.resultCount = resultCount;
	}

	private List<Task> tasks;
	private List<Process> processes;
	private int resultCount;

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public List<Process> getProcesses() {
		return processes;
	}

	public void setProcesses(List<Process> processes) {
		this.processes = processes;
	}

	public int getResultCount() {
		return resultCount;
	}

	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}

	@Override
	public String toString() {
		return "WorkFlowParseResponse [tasks=" + tasks + ", processes=" + processes + ", resultCount=" + resultCount
				+ "]";
	}

}
