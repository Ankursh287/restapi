package com.qa.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class RestClient {

	// 1. Get Method without Headers
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault(); // creating HTTP client
		HttpGet httpGet = new HttpGet(url); // HTTP GET request
		CloseableHttpResponse closableHttpResponse = httpClient.execute(httpGet); // hit the GET URL (Complete)
		return closableHttpResponse;
	}

	// 2. Get Method with Headers
	public CloseableHttpResponse get(String url, HashMap<String, String> headerMap)
			throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault(); // creating HTTP client
		HttpGet httpGet = new HttpGet(url); // HTTP get request

		//For Headers
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			httpGet.addHeader(entry.getKey(), entry.getValue());
		}

		CloseableHttpResponse closableHttpResponse = httpClient.execute(httpGet); // hit the GET URL (Complete)
		return closableHttpResponse;
	}

	// 3. Post Method
	public CloseableHttpResponse post(String url, String entityString, HashMap<String, String> headerMap) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault(); // creating HTTP client
		HttpPost httpPost = new HttpPost(url); // HTTP POST request
		httpPost.setEntity(new StringEntity(entityString));
		
		//For Headers
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			httpPost.addHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse closableHttpResponse = httpClient.execute(httpPost); // hit the POST URL (Complete) 
		return closableHttpResponse;
	}
}