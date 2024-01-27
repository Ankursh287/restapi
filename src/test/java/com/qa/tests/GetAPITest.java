package com.qa.tests;

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
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetAPITest extends TestBase {
	TestBase testBase;
	RestClient restClient = new RestClient();
	String url;
	CloseableHttpResponse closableHttpResponse;

	@BeforeMethod
	public void setUp() {
		testBase = new TestBase();
		String serviceURL = prop.getProperty("URL");
		String apiURL = prop.getProperty("getServiceURL");
		url = serviceURL + apiURL;
	}

	@Test(priority=0)
	public void getAPITest() throws ClientProtocolException, IOException {
		closableHttpResponse = restClient.get(url);
		// a. Getting Status Code
		int statusCode = closableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code is " + statusCode);

		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status Code is not 200");

		// b. JSON String
		String responseString = EntityUtils.toString(closableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API ----->" + responseJson);
		
		//Single value assertion:
		//per_page:
		String perPageValue = TestUtil.getValuesByJPath(responseJson,"/per_page");
		System.out.println("Value of per page is -->" +perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 6);

		//getting value from JSON Array
		String lastName = TestUtil.getValuesByJPath(responseJson, "/data[0]/last_name"); 
		String id = TestUtil.getValuesByJPath(responseJson, "/data[0]/id"); 
		String avatar = TestUtil.getValuesByJPath(responseJson, "/data[0]/avatar"); 
		String firstName = TestUtil.getValuesByJPath(responseJson, "/data[0]/first_name"); 
		
		System.out.println("Last Name is -->" + lastName);
		System.out.println("ID is -->" + id);
		System.out.println("Avatar is -->" + avatar);
		System.out.println("First Name is -->" + firstName);
		
		// c. All Headers
		Header[] headerArray = closableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for (Header header : headerArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers Array --->" + allHeaders);
	}
	
	@Test(priority=1)
	public void getAPITestWithHeaders() throws ClientProtocolException, IOException {
		
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
//		headerMap.put("username", "test");
//		headerMap.put("password", "test123");
		
		closableHttpResponse = restClient.get(url,headerMap);
		// a. Getting Status Code
		int statusCode = closableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code is " + statusCode);

		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status Code is not 200");

		// b. JSON String
		String responseString = EntityUtils.toString(closableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("Response JSON from API ----->" + responseJson);
		
		//Single value assertion:
		//per_page:
		String perPageValue = TestUtil.getValuesByJPath(responseJson,"/per_page");
		System.out.println("Value of per page is -->" +perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 6);

		//getting value from JSON Array
		String lastName = TestUtil.getValuesByJPath(responseJson, "/data[0]/last_name"); 
		String id = TestUtil.getValuesByJPath(responseJson, "/data[0]/id"); 
		String avatar = TestUtil.getValuesByJPath(responseJson, "/data[0]/avatar"); 
		String firstName = TestUtil.getValuesByJPath(responseJson, "/data[0]/first_name"); 
		
		System.out.println("Last Name is -->" + lastName);
		System.out.println("ID is -->" + id);
		System.out.println("Avatar is -->" + avatar);
		System.out.println("First Name is -->" + firstName);
		
		// c. All Headers
		Header[] headerArray = closableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for (Header header : headerArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers Array --->" + allHeaders);
	}
}