package com.incture.workbox.scpadapter.objects;

import java.util.List;

import com.incture.workbox.scpadapter.events.ProcessEventsDo;
import com.incture.workbox.scpadapter.events.TaskEventsDo;
import com.incture.workbox.scpadapter.events.TaskOwnersDo;

public class WorkFlowParseEventResponse implements WorkFlowResponse {

	public WorkFlowParseEventResponse() {
	}

	public WorkFlowParseEventResponse(List<TaskEventsDo> tasks, List<ProcessEventsDo> processes, List<TaskOwnersDo> owners, int resultCount) {
		this.tasks = tasks;
		this.processes = processes;
		this.resultCount = resultCount;
		this.owners = owners;
	}

	private List<TaskEventsDo> tasks;
	private List<ProcessEventsDo> processes;
	private List<TaskOwnersDo> owners;
	private int resultCount;

	public List<TaskEventsDo> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskEventsDo> tasks) {
		this.tasks = tasks;
	}

	public List<ProcessEventsDo> getProcesses() {
		return processes;
	}

	public void setProcesses(List<ProcessEventsDo> processes) {
		this.processes = processes;
	}

	public List<TaskOwnersDo> getOwners() {
		return owners;
	}

	public void setOwners(List<TaskOwnersDo> owners) {
		this.owners = owners;
	}

	public int getResultCount() {
		return resultCount;
	}

	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}

	@Override
	public String toString() {
		return "WorkFlowParseEventResponse [tasks=" + tasks + ", processes=" + processes + ", owners=" + owners
				+ ", resultCount=" + resultCount + "]";
	}

}
