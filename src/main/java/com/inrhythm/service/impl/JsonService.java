package com.inrhythm.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashSet;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inrhythm.pojo.InRhythmResponse;
import com.inrhythm.pojo.Posts;
import com.inrhythm.service.IJsonService;

public class JsonService implements IJsonService{

	public InRhythmResponse parseJson(String url) throws Exception {
		Gson gson = new Gson();
		Type postsType = new TypeToken<List<Posts>>() {}.getType();
		List<Posts> posts;
		try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL(url).openStream()))){
			posts = gson.fromJson(in, postsType);
		}
		int postCount = 0;
		HashSet<Integer> uids = new HashSet<>();
		for(Posts p: posts){
			uids.add(p.getUserId());
			if (postCount == 3) {
				p.setBody("InRhythm"); 
				p.setTitle("InRhythm");
			}
			postCount = postCount + 1;
		}
		InRhythmResponse responce = new InRhythmResponse();
		responce.setUserCount(uids.size());
		responce.setPosts(posts);
		return responce;
	}

}
