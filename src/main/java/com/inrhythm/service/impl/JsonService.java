package com.inrhythm.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.inrhythm.pojo.InRhythmResponse;
import com.inrhythm.pojo.Posts;
import com.inrhythm.service.IJsonService;

public class JsonService implements IJsonService{
	private static final Logger LOG = Logger.getLogger(JsonService.class.getName());
	
	public InRhythmResponse parseJson(String url) throws Exception {
		
		//1 get conn by url and get returned data
		String returnedData = getURLData(url);
		
		//2 parse returned data and get jsonArry
		JSONArray jsonArray = null;
		if (returnedData != null && !returnedData.isEmpty()){
			jsonArray = (JSONArray) new JSONParser().parse(returnedData);
		} else {
			LOG.warning("The returned URL data is null");
		}
		
		//3 get the final result
		InRhythmResponse rst = null;
		if (jsonArray != null){
			rst = getFinal(jsonArray);
		} else {
			LOG.warning("The jsonArray to ");
		}
		return rst;
	}

	private String getURLData(String url) {
		if (url == null || url.isEmpty()) {
			LOG.warning("the input for the getURLData() is null or empty");
			return null;
		}
		StringBuilder sb = new StringBuilder();
		try {
			URL addUrl = new URL(url);  //MalformedURLException 
			HttpURLConnection conn = (HttpURLConnection) addUrl.openConnection();
			if (conn != null) {
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				conn.addRequestProperty("User-Agent", "Mozilla/4.0");
				// need to add this property cause this is command line application, otherwise 403 Error
			}
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			// assume the data is't too large to be loaded, otherwise it should be read part by part
			String string;
			while ((string = br.readLine()) != null) {
				sb.append(string);
			}
			br.close();
			conn.disconnect();
		} catch (MalformedURLException e) { 
			LOG.log(Level.SEVERE, "Exception occurs when instante URL obj ", e);
			e.printStackTrace();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "IOException occurs when instante HTTPURLConnection, setRequestMethod, getResponseCode or read stream", e);
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	private InRhythmResponse getFinal(JSONArray jsonArray) {
		if (jsonArray == null || jsonArray.size() == 0){
			LOG.warning("the input for getFinal() is null or empty");
			return null;
		}
		Set<Integer> uniqueUserId = new HashSet<>();
		List<Posts> posts = new ArrayList<>();
		if (jsonArray.size() <= 3){
			LOG.info("the size() of JSON array <= 3 so there is no 4th item to be changed");
		}
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jobj = (JSONObject) jsonArray.get(i);
			int userId = ((Long) (jobj.get("userId"))).intValue();
			uniqueUserId.add(userId);

			Posts post = new Posts();
			post.setUserId(userId);
			post.setId(((Long) (jobj.get("id"))).intValue());
			if (i == 3) {//   change the 4th item 
				post.setTitle("InRhythm");
				post.setBody("InRhythm");
			} else {
				post.setTitle((String) jobj.get("title"));
				post.setBody((String) jobj.get("body"));
			}
			posts.add(post);
		}
		
		InRhythmResponse rst = new InRhythmResponse();
		rst.setUserCount(uniqueUserId.size());
		rst.setPosts(posts);
		return rst;
	}
}
