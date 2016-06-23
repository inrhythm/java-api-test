package com.inrhythm.service.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.client.ClientProtocolException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.inrhythm.pojo.InRhythmResponse;
import com.inrhythm.pojo.Post;
import com.inrhythm.service.IJsonService;

/**
 * Default implementation of IJsonService.
 * 
 * Jackson's Streaming API has been used instead of standard ObjectMapper. This
 * helps in efficient memory utilization in case the JSON response is extremely
 * huge.
 * 
 * @author sumit
 *
 */
public class JsonService implements IJsonService {

	private List<Post> parseRawJson(Reader reader) throws ClientProtocolException, IOException {
		JsonParser jp = new JsonFactory().createParser(reader);
		List<Post> posts = null;
		Post post = null;
		JsonToken token = null;
		while ((token = jp.nextToken()) != JsonToken.END_ARRAY) {
			if (token == JsonToken.START_ARRAY) {
				posts = new ArrayList<Post>();
			} else if (token == JsonToken.START_OBJECT) {
				post = new Post();
			} else if (token == JsonToken.END_OBJECT) {
				posts.add(post);
				post = null;
			} else {
				String field = jp.getCurrentName();
				jp.nextToken(); // move to value
				if ("userId".equals(field)) {
					post.setUserId(jp.getIntValue());
				} else if ("id".equals(field)) {
					post.setId(jp.getIntValue());
				} else if ("title".equals(field)) {
					post.setTitle(jp.getValueAsString());
				} else if ("body".equals(field)) {
					post.setBody(jp.getValueAsString());
				}
			}

		}
		return posts;

	}

	public InRhythmResponse parseJson(Reader reader) throws Exception {
		try {
			List<Post> posts = parseRawJson(reader);
			Set<Integer> uniqueUserId = new HashSet<Integer>();
			for (Post post : posts) {
				uniqueUserId.add(post.getUserId());
			}
			modifyFourthPost(posts);
			InRhythmResponse response = new InRhythmResponse();
			response.setPosts(posts);
			response.setUserCount(uniqueUserId.size());
			return response;
		} finally {
			reader.close();
		}
	}

	private void modifyFourthPost(List<Post> posts) {
		Post thirdPost = posts.get(3);
		thirdPost.setTitle("InRhythm");
		thirdPost.setBody("InRhythm");
	}

	public void persistJson(InRhythmResponse response, Writer writer) throws Exception {
		JsonFactory f = new JsonFactory();
		JsonGenerator jg = f.createGenerator(writer);
		jg.writeStartArray();
		for (Post post : response.getPosts()) {
			jg.writeStartObject();
			jg.writeNumberField("userId", post.getUserId());
			jg.writeNumberField("id", post.getId());
			jg.writeStringField("title", post.getTitle());
			jg.writeStringField("body", post.getBody());
			jg.writeEndObject();
		}
		jg.writeEndArray();
		jg.close();
	}

}
