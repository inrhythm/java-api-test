package com.inrhythm.service.impl;

import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.inrhythm.service.IHttpService;

/**
 * Default implementation of IHttpService.
 * 
 * Apache's HTTP Client is used to make HTTP GET request.
 * 
 * @author sumit
 *
 */
public class HttpService implements IHttpService {

	public Reader createInputStreamReader(String url) throws Exception {
		
		// default http client
		HttpClient client = HttpClientBuilder.create().build();

		// Http GET request
		HttpGet request = new HttpGet(url);

		// Get a handle to response
		HttpResponse response = client.execute(request);

		// Open stream
		return new InputStreamReader(response.getEntity().getContent());
	}
}
