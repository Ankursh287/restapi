package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;
import com.qa.util.TestUtil;

public class PostAPITest extends TestBase {
	TestBase testBase;
	RestClient restClient;
	String url;
	CloseableHttpResponse closableHttpResponse;

	@BeforeMethod
	public void setUp() {
		testBase = new TestBase();
		String serviceURL = prop.getProperty("URL");
		String apiURL = prop.getProperty("postServiceURL");
		url = serviceURL + apiURL;
	}

	@Test
	public void postAPITest() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
//		headerMap.put("username", "test");
//		headerMap.put("password", "test123");

		// Jackson API - Used for Marshelling/UnMarshelling
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		Users users = new Users("morpheus", "leader");//Expected Users Object

		// Object to JSON File
		mapper.writeValue(
				new File("C:\\Users\\ankur\\eclipse-workspace\\restapi\\src\\main\\java\\com\\qa\\data\\users.js"),
				users);

		// Object to JSON in String
		String usersJsonString = mapper.writeValueAsString(users);
		System.out.println(usersJsonString);

		closableHttpResponse = restClient.post(url, usersJsonString, headerMap);
		// a. Getting Status Code
		int statusCode = closableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code is " + statusCode);

		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_201, "Status Code is not 201");

		// b. JSON String
		String responseString = EntityUtils.toString(closableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API ----->" + responseJson);

		Users usersResObj = mapper.readValue(responseString, Users.class);//Actual Users Object
		System.out.println(usersResObj);
		
		Assert.assertTrue(users.getName().equals(usersResObj.getName()));
		Assert.assertTrue(users.getJob().equals(usersResObj.getJob()));
		System.out.println(usersResObj.getId());
		System.out.println(usersResObj.getCreatedAt());
		 

	}
}
