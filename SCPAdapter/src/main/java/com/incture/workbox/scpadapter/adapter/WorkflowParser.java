package com.incture.workbox.scpadapter.adapter;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import com.incture.workbox.scpadapter.events.ProcessEventsDo;
import com.incture.workbox.scpadapter.events.TaskEventsDo;
import com.incture.workbox.scpadapter.events.TaskOwnersDo;
import com.incture.workbox.scpadapter.events.TaskOwnersDoPK;
import com.incture.workbox.scpadapter.objects.Process;
import com.incture.workbox.scpadapter.objects.RestResponse;
import com.incture.workbox.scpadapter.objects.Task;
import com.incture.workbox.scpadapter.objects.UserDetails;
import com.incture.workbox.scpadapter.objects.WorkFlowParseEventResponse;
import com.incture.workbox.scpadapter.objects.WorkFlowParseResponse;
import com.incture.workbox.scpadapter.util.RestUtil;
import com.incture.workbox.scpadapter.util.ServicesUtil;
import com.incture.workbox.scpadapter.util.WorkFlowResponseType;
import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import com.jsoniter.spi.TypeLiteral;

public class WorkflowParser {

	private String baseUrl;
	private String[] oAuthToken;

	private Map<String, UserDetails> userDetails;
	private Map<String, List<String>> groupDetails;

	public WorkflowParser() {
		super();
	}

	public WorkflowParser(String baseUrl, String[] oAuthToken) {
		super();
		this.baseUrl = baseUrl;
		this.oAuthToken = oAuthToken;
	}

	public WorkflowParser(String baseUrl, String[] oAuthToken, Map<String, UserDetails> userDetails,
			Map<String, List<String>> groupDetails) {
		super();
		this.baseUrl = baseUrl;
		this.oAuthToken = oAuthToken;
		this.userDetails = userDetails;
		this.groupDetails = groupDetails;
	}

	public String getInstances(WorkFlowResponseType responseType) {
		switch (responseType) {
		case CUSTOM_RESPONSE:
			return getCustomResponseInstances();
		case EVENT_RESPONSE:
			return getEventResponseInstances();
		default:
			return getEventResponseInstances();
		}
	}

	@SuppressWarnings("unchecked")
	private String getEventResponseInstances() {

		WorkFlowParseEventResponse workFlowParseResponse = null;

		List<TaskEventsDo> tasks = null;
		TaskEventsDo task = null;

		List<TaskOwnersDo> owners = null;
		TaskOwnersDo owner = null;

		List<ProcessEventsDo> processes = null;
		ProcessEventsDo process = null;

		List<Task> taskList = getInstances(baseUrl
				+ "task-instances?status=READY&status=RESERVED&status=CANCELED&status=COMPLETED&$expand=attributes",
				Task.class);
		List<Process> processList = getInstances(
				baseUrl + "workflow-instances?status=RUNNING&status=ERRONEOUS&status=CANCELED&status=COMPLETED",
				Process.class);

		tasks = new ArrayList<TaskEventsDo>();
		owners = new ArrayList<TaskOwnersDo>();
		processes = new ArrayList<ProcessEventsDo>();

		UserDetails processorDetails = null;
		UserDetails ownerDetails = null;
		UserDetails startedByDetails = null;

		if (!ServicesUtil.isEmpty(taskList) && taskList.size() > 0) {
			for (Task parseTask : taskList) {
				if (!ServicesUtil.isEmpty(parseTask)) {

					task = new TaskEventsDo();

					task.setEventId(parseTask.getId());
					task.setProcessId(parseTask.getWorkflowInstanceId());
					task.setProcessName(parseTask.getWorkflowDefinitionId());
					task.setCreatedAt(ServicesUtil.getFromStringToDate(parseTask.getCreatedAt()));
					task.setDescription(parseTask.getDescription());
					task.setCurrentProcessor(parseTask.getProcessor());

					if (!ServicesUtil.isEmpty(task.getCurrentProcessor())) {
						processorDetails = getUserDetails(task.getCurrentProcessor());
						if (!ServicesUtil.isEmpty(processorDetails)) {
							task.setCurrentProcessorDisplayName(processorDetails.getDisplayName());
						}
					}

					task.setSubject(parseTask.getSubject());
					task.setStatus(parseTask.getStatus());
					task.setName(parseTask.getDefinitionId());
					task.setPriority(parseTask.getPriority());
					task.setCompletedAt(ServicesUtil.isEmpty(parseTask.getCompletedAt()) ? null
							: ServicesUtil.getFromStringToDate(parseTask.getCompletedAt()));

					if (ServicesUtil.isEmpty(parseTask.getDueDate())) {
						task.setCompletionDeadLine(new Date(task.getCreatedAt().getTime() + (1000 * 60 * 60 * 24 * 5)));
						task.setSlaDueDate(new Date(task.getCreatedAt().getTime() + (1000 * 60 * 60 * 24 * 5)));
					} else {
						task.setCompletionDeadLine(ServicesUtil.getFromStringToDate(parseTask.getDueDate()));
						task.setSlaDueDate(ServicesUtil.getFromStringToDate(parseTask.getDueDate()));
					}

					task.setOrigin("SCP");
					tasks.add(task);

					if (!ServicesUtil.isEmpty(task.getCurrentProcessor())) {
						owner = new TaskOwnersDo();
						owner.setTaskOwnersDoPK(new TaskOwnersDoPK(task.getEventId(), task.getCurrentProcessor()));
						owner.setIsProcessed(true);
						if (!ServicesUtil.isEmpty(owner) && !ServicesUtil.isEmpty(owner.getTaskOwnersDoPK()))
							ownerDetails = getUserDetails(owner.getTaskOwnersDoPK().getTaskOwner());
						if (!ServicesUtil.isEmpty(ownerDetails)) {
							owner.setOwnerEmail(ownerDetails.getEmailId());
							owner.setTaskOwnerDisplayName(ownerDetails.getDisplayName());
						}
						owners.add(owner);
					}

					List<String> recepients = parseTask.getRecipientUsers();
					List<String> recepientGroups = parseTask.getRecipientGroups();
					for (String group : recepientGroups) {
						List<String> recipientUserOfGroup = getRecipientUserOfGroup((String) group);
						if(!ServicesUtil.isEmpty(recipientUserOfGroup))
							recepients.addAll(recipientUserOfGroup);
					}

					for (String recepient : recepients) {
						owner = new TaskOwnersDo();
						owner.setTaskOwnersDoPK(new TaskOwnersDoPK(task.getEventId(), recepient));
						owner.setIsProcessed(false);
						if (!ServicesUtil.isEmpty(owner) && !ServicesUtil.isEmpty(owner.getTaskOwnersDoPK()))
							ownerDetails = getUserDetails(owner.getTaskOwnersDoPK().getTaskOwner());
						if (!ServicesUtil.isEmpty(ownerDetails)) {
							owner.setOwnerEmail(ownerDetails.getEmailId());
							owner.setTaskOwnerDisplayName(ownerDetails.getDisplayName());
						}
						owners.add(owner);
					}

				}
			}
		}
		
		if (!ServicesUtil.isEmpty(processList) && processList.size() > 0) {
			for (Process parseProcess : processList) {
				if (!ServicesUtil.isEmpty(parseProcess)) {

					process = new ProcessEventsDo();

					process.setProcessId(parseProcess.getId());

					process.setName(parseProcess.getDefinitionId());
					process.setSubject(parseProcess.getSubject());
					process.setStatus(parseProcess.getStatus());

					process.setRequestId(parseProcess.getBusinessKey());
					process.setStartedAt(ServicesUtil.isEmpty(parseProcess.getStartedAt()) ? null
							: ServicesUtil.getFromStringToDate(parseProcess.getStartedAt()));
					process.setCompletedAt(ServicesUtil.isEmpty(parseProcess.getCompletedAt()) ? null
							: ServicesUtil.getFromStringToDate(parseProcess.getCompletedAt()));
					process.setStartedBy(parseProcess.getStartedBy());
					startedByDetails = getUserDetails(process.getStartedBy());
					if (!ServicesUtil.isEmpty(startedByDetails)) {
						process.setStartedByDisplayName(startedByDetails.getDisplayName());
					}
					processes.add(process);
				}
			}
		}

		workFlowParseResponse = new WorkFlowParseEventResponse(tasks, processes, owners, 0);
		return JsonStream.serialize(workFlowParseResponse);
	}

	@SuppressWarnings("unchecked")
	private String getCustomResponseInstances() {
		WorkFlowParseResponse workFlowParseResponse = null;
		List<Task> tasks = getInstances(baseUrl
				+ "task-instances?status=READY&status=RESERVED&status=CANCELED&status=COMPLETED&$expand=attributes",
				Task.class);
		List<Process> processes = getInstances(
				baseUrl + "workflow-instances?status=RUNNING&status=ERRONEOUS&status=CANCELED&status=COMPLETED",
				Process.class);
		workFlowParseResponse = new WorkFlowParseResponse(tasks, processes, 0);
		return JsonStream.serialize(workFlowParseResponse);
	}

	@SuppressWarnings("rawtypes")
	private List getInstances(String requestUrl, Class clazz) {

		int instancesCount = -1;
		Object responseObject = null;
		HttpResponse httpResponse = null;
		RestResponse restResponse = null;
		List<Task> taskArray = null;
		List<Task> taskArraySkip = null;

		List<Process> processArray = null;
		List<Process> processArraySkip = null;

		int taskArraySize = 0;
		int processArraySize = 0;

		if (!ServicesUtil.isEmpty(requestUrl)) {
			requestUrl += "&$top=1000&$inlinecount=allpages";
			restResponse = RestUtil.invokeRestService(requestUrl, null, "GET", "application/json", false, null, null,
					null, oAuthToken[0], oAuthToken[1]);
		}

		responseObject = restResponse.getResponseObject();
		httpResponse = restResponse.getHttpResponse();
		if (!ServicesUtil.isEmpty(httpResponse)) {
			for (Header header : httpResponse.getAllHeaders()) {
				if (header.getName().equalsIgnoreCase("X-Total-Count")) {
					instancesCount = Integer.parseInt(header.getValue());
				}
			}
		} else if (!ServicesUtil.isEmpty(restResponse.getUrlConnection())
				&& restResponse.getResponseCode() == HttpURLConnection.HTTP_OK) {
			instancesCount = Integer.parseInt(restResponse.getUrlConnection().getHeaderField("X-Total-Count"));
		}
		if (clazz.equals(Task.class)) {
			taskArray = ServicesUtil.isEmpty(responseObject) ? null
					: JsonIterator.deserialize(responseObject.toString(), new TypeLiteral<List<Task>>() {
					});
			if (!ServicesUtil.isEmpty(taskArray))
				taskArraySize = taskArray.size();
			if (instancesCount > taskArraySize) {
				int skip = 1000;
				for (int k = 1; k < instancesCount / skip; k++) {
					requestUrl += "&$skip=" + (skip * k);
					restResponse = RestUtil.invokeRestService(requestUrl, null, "GET", "application/json", false, null,
							null, null, oAuthToken[0], oAuthToken[1]);
					responseObject = restResponse.getResponseObject();
					taskArraySkip = ServicesUtil.isEmpty(responseObject) ? null
							: JsonIterator.deserialize(responseObject.toString(), new TypeLiteral<List<Task>>() {
							});
					if (!ServicesUtil.isEmpty(taskArray) && taskArraySize > 0) {
						taskArray.addAll(taskArraySkip);
					} else {
						taskArray = taskArraySkip;
					}
				}
			}
			return taskArray;
		} else if (clazz.equals(Process.class)) {

			processArray = ServicesUtil.isEmpty(responseObject) ? null
					: JsonIterator.deserialize(responseObject.toString(), new TypeLiteral<List<Process>>() {
					});
			if (!ServicesUtil.isEmpty(processArray))
				processArraySize = processArray.size();
			if (instancesCount > processArraySize) {
				int skip = 1000;
				for (int k = 1; k < instancesCount / skip; k++) {
					requestUrl += "&$skip=" + (skip * k);
					restResponse = RestUtil.invokeRestService(requestUrl, null, "GET", "application/json", false, null,
							null, null, oAuthToken[0], oAuthToken[1]);

					responseObject = restResponse.getResponseObject();
					processArraySkip = ServicesUtil.isEmpty(responseObject) ? null
							: JsonIterator.deserialize(responseObject.toString(), new TypeLiteral<List<Process>>() {
							});
					if (!ServicesUtil.isEmpty(processArray) && processArraySize > 0) {
						processArray.addAll(processArraySkip);
					} else {
						processArray = processArraySkip;
					}
				}
			}
			return processArray;
		}
		return null;

	}

	private UserDetails getUserDetails(String userId) {
		UserDetails user = null;
		if (!ServicesUtil.isEmpty(userDetails)) {
			user = userDetails.get(userId);
		}
		return user;
	}

	private List<String> getRecipientUserOfGroup(String group) {
		List<String> groups = null;
		if (!ServicesUtil.isEmpty(groupDetails)) {
			groups = groupDetails.get(group);
		}
		return groups;
	}

}
