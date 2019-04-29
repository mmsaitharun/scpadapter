package com.incture.workbox.scpadapter.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.incture.workbox.scpadapter.objects.RestResponse;
import com.sap.core.connectivity.api.authentication.AuthenticationHeader;
import com.sap.core.connectivity.api.authentication.AuthenticationHeaderProvider;

public class RestUtil {

	/**
	 * @param requestURL
	 * @param samlHeaderKey - null
	 * @param entity - input entity for POST call
	 * @param method - POST/GET - AdapterContants.HTTP_METHOD_POST / AdapterContants.HTTP_METHOD_GET / AdapterContants.HTTP_METHOD_PATCH
	 * @param contentType - Content Type - AdapterContants.APPLICATION_JSON / AdapterContants.APPLICATION_XML etc.
	 * @param isSaml - true if trying to do SSO using SAML2.0
	 * @param xCsrfToken - "Fetch", if need to do a PATCH/POST operation and x-csrf-token is required
	 * @param userId - userId for Basic Authentication
	 * @param password - password for Basic Authentication
	 * @param accessToken - Access token for other types of Authentications 
	 * @param tokenType - Access token for other types of Authentications - Bearer/Basic etc.
	 * @return RestResponse with returnObject, responseCode, httpResponse
	 */
	public static RestResponse invokeRestService(String requestURL, String entity, String method,
			String contentType, Boolean isSaml, String xCsrfToken, String userId, String password, String accessToken,
			String tokenType) {

		RestResponse restResponse = null;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpRequestBase httpRequestBase = null;
		HttpResponse httpResponse = null;
		StringEntity input = null;
		String json = null;
		JSONObject obj = null;
		JSONArray array = null;
		Object returnJson = null;

		AuthenticationHeader appToAppSSOHeader = null;
		if (requestURL != null) {
			restResponse = new RestResponse();
			if (isSaml) {
				if (ServicesUtil.isEmpty(appToAppSSOHeader)) {
					appToAppSSOHeader = refreshAppToAppSSOHeader(requestURL);
				}
			}

			try {
				if (xCsrfToken != null && xCsrfToken.equalsIgnoreCase("fetch")) {
					httpRequestBase = new HttpGet(requestURL);
					httpRequestBase.setHeader("x-csrf-token", xCsrfToken);
					if (appToAppSSOHeader != null) {
						httpRequestBase.addHeader(appToAppSSOHeader.getName(), appToAppSSOHeader.getValue());
					}
					httpResponse = httpClient.execute(httpRequestBase);
					Header[] headers = httpResponse.getAllHeaders();
					for (Header header : headers) {
						if (header.getName().equalsIgnoreCase("x-csrf-token")) {
							xCsrfToken = header.getValue();
						}
					}
				}
			} catch (IOException e) {
				System.err.println("[Workbox][SCPAdapter][RestUtil]Exception : " + e.getMessage());
			}

			if (method.equalsIgnoreCase(AdapterContants.HTTP_METHOD_GET)) {
				httpRequestBase = new HttpGet(requestURL);
			} else if (method.equalsIgnoreCase(AdapterContants.HTTP_METHOD_POST)) {
				httpRequestBase = new HttpPost(requestURL);
				if (!ServicesUtil.isEmpty(entity)) {
					try {
						input = new StringEntity(entity);
						input.setContentType(contentType);
					} catch (UnsupportedEncodingException e) {
						System.err.println("[Workbox][SCPAdapter][RestUtil]Input UnsupportedEncodingException : "+e.getMessage());
					}
					((HttpPost) httpRequestBase).setEntity(input);
				}
			} else if (method.equalsIgnoreCase(AdapterContants.HTTP_METHOD_PATCH)) {
				httpRequestBase = new HttpPatch(requestURL);
				if (!ServicesUtil.isEmpty(entity)) {
					try {
						input = new StringEntity(entity);
						input.setContentType(contentType);
					} catch (UnsupportedEncodingException e) {
						System.err.println("[Workbox][SCPAdapter][RestUtil]Input UnsupportedEncodingException : "+e.getMessage());
					}
					((HttpPatch) httpRequestBase).setEntity(input);
				}
			}
			if (appToAppSSOHeader != null) {
				httpRequestBase.addHeader(appToAppSSOHeader.getName(), appToAppSSOHeader.getValue());
			}
			if (!ServicesUtil.isEmpty(xCsrfToken) && !xCsrfToken.equalsIgnoreCase("fetch"))
				httpRequestBase.addHeader("x-csrf-token", xCsrfToken);
			httpRequestBase.addHeader("accept", contentType);
			if (!ServicesUtil.isEmpty(userId) && !ServicesUtil.isEmpty(password)) {
				httpRequestBase.addHeader("Authorization", ServicesUtil.getBasicAuth(userId, password));
			}

			if (!ServicesUtil.isEmpty(accessToken) && !ServicesUtil.isEmpty(tokenType)
					&& ServicesUtil.isEmpty(userId)) {
				httpRequestBase.addHeader("Authorization", ServicesUtil.getAuthorization(accessToken, tokenType));
			}
			try {
				httpResponse = httpClient.execute(httpRequestBase);
				if(!ServicesUtil.isEmpty(httpResponse) && !ServicesUtil.isEmpty(httpResponse.getEntity())) {
					json = EntityUtils.toString(httpResponse.getEntity());
					try {
						if (!ServicesUtil.isEmpty(json)) {
							returnJson = new Object[2];
							if (json.charAt(0) == '{') {
								obj = new JSONObject(json);
								returnJson = obj;
							} else if (json.charAt(0) == '[') {
								array = new JSONArray(json);
								returnJson = array;
							}
							restResponse.setResponseObject(returnJson);
						}
					} catch (JSONException e) {
						System.err.println("[Workbox][SCPAdapter][RestUtil]JSONException : " + e + "JSON Object : " + json);
					}
				}
				restResponse.setHttpResponse(httpResponse);
				restResponse.setResponseCode(httpResponse.getStatusLine().getStatusCode());
				httpClient.close();
			} catch (IOException e) {
				System.err.println("[Workbox][SCPAdapter][RestUtil]IOException : " + e);
			}
		}
		
		//System.err.println("RestUtil.callRestService() Response :"+restResponse);
		return restResponse;
	}

	private static AuthenticationHeader refreshAppToAppSSOHeader(String requestURL) {
		Context ctx;
		AuthenticationHeader appToAppSSOHeader = null;
		AuthenticationHeaderProvider authHeaderProvider;
		try {
			ctx = new InitialContext();
			authHeaderProvider = (AuthenticationHeaderProvider) ctx.lookup("java:comp/env/AuthHeaderProvider");
			appToAppSSOHeader = authHeaderProvider.getAppToAppSSOHeader(requestURL);
		} catch (Exception ex) {
			System.err.println("[Workbox][SCPAdapter][RestUtil][refreshAppToAppSSOHeader]Exception while fetching auth Header Provider : " + ex.getMessage());
		}
		return appToAppSSOHeader;
	}

}