package com.inrhythm;

import org.junit.Test;

import com.inrhythm.pojo.InRhythmResponse;
import com.inrhythm.pojo.Posts;
import com.inrhythm.service.impl.JsonService;

import junit.framework.TestCase;

public class AppTest extends TestCase {

	@Test
	public void testPostModify() {

		String url = "http://jsonplaceholder.typicode.com/posts";
 
		JsonService service = new JsonService();

		InRhythmResponse response = null;
		try {
			response = service.parseJson(url);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int postCount = 0;
		for (Posts post : response.getPosts()) {

			if (postCount == 3) {
				assertTrue(post.getBody().equalsIgnoreCase("inrhythm") && post.getTitle().equalsIgnoreCase("inrhythm"));
			}
			postCount = postCount + 1;
		}
	}

	@Test
	public void testUserCount() {

		String url = "http://jsonplaceholder.typicode.com/posts";

		JsonService service = new JsonService();

		InRhythmResponse response = null;
		try {
			response = service.parseJson(url);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertTrue(response.getUserCount() == 10);

	}
}
