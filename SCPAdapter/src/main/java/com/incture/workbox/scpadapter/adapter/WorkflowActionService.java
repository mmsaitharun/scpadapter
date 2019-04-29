package com.incture.workbox.scpadapter.adapter;

import com.incture.workbox.scpadapter.objects.RestResponse;
import com.incture.workbox.scpadapter.util.AdapterContants;
import com.incture.workbox.scpadapter.util.RestUtil;
import com.incture.workbox.scpadapter.util.ServicesUtil;

public class WorkflowActionService {

	private String baseUrl;
	private String[] oAuthToken;

	public WorkflowActionService() {
		super();
	}

	public WorkflowActionService(String baseUrl, String[] oAuthToken) {
		super();
		this.baseUrl = baseUrl;
		this.oAuthToken = oAuthToken;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String[] getoAuthToken() {
		return oAuthToken;
	}

	public void setoAuthToken(String[] oAuthToken) {
		this.oAuthToken = oAuthToken;
	}

	public String doExternalAction(String relativeUrl, String content, String method, boolean isSAML) {
		RestResponse restResponse = RestUtil.invokeRestService(baseUrl + relativeUrl, content, method,
				AdapterContants.APPLICATION_JSON, isSAML, null, null, null, oAuthToken[0], oAuthToken[1]);
		String response = null;
		if (!ServicesUtil.isEmpty(restResponse) && !ServicesUtil.isEmpty(restResponse.getResponseObject())) {
			response = (String) restResponse.getResponseObject();
		}
		return response;
	}

}
