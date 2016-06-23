package com.inrhythm;

import java.io.FileReader;
import java.io.FileWriter;

import org.junit.Test;

import com.inrhythm.pojo.InRhythmResponse;
import com.inrhythm.pojo.Post;
import com.inrhythm.service.IHttpService;
import com.inrhythm.service.IJsonService;
import com.inrhythm.service.impl.HttpService;
import com.inrhythm.service.impl.JsonService;

import junit.framework.TestCase;

public class AppTest extends TestCase {

	@Test
	public void testPostModify() throws Exception {
		// given
		String url = "http://jsonplaceholder.typicode.com/posts";
		JsonService service = new JsonService();
		HttpService httpService = new HttpService();

		// when
		InRhythmResponse response = service.parseJson(httpService.createInputStreamReader(url));

		// then
		Post fourthPost = response.getPosts().get(3);
		assertTrue(fourthPost.getBody().equalsIgnoreCase("inrhythm")
				&& fourthPost.getTitle().equalsIgnoreCase("inrhythm"));

	}

	@Test
	public void testUserCount() throws Exception {
		// given
		String url = "http://jsonplaceholder.typicode.com/posts";
		JsonService service = new JsonService();
		HttpService httpService = new HttpService();
		
		// when
		InRhythmResponse response = service.parseJson(httpService.createInputStreamReader(url));
		
		// then
		assertTrue(response.getUserCount() == 10);
	}

	@Test
	public void testPersistence() throws Exception {
		// given
		String url = "http://jsonplaceholder.typicode.com/posts";
		IJsonService service = new JsonService();
		IHttpService httpService = new HttpService();
		
		// when
		InRhythmResponse response = service.parseJson(httpService.createInputStreamReader(url));
		service.persistJson(response, new FileWriter("inrhythm.json"));
		response = service.parseJson(new FileReader("inrhythm.json"));
		
		// then
		Post fourthPost = response.getPosts().get(3);
		assertTrue(response.getUserCount() == 10);
		assertTrue(fourthPost.getBody().equalsIgnoreCase("inrhythm")
				&& fourthPost.getTitle().equalsIgnoreCase("inrhythm"));

	}
}
