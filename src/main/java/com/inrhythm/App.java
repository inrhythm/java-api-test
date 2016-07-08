package com.inrhythm;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;

import com.inrhythm.pojo.InRhythmResponse;
import com.inrhythm.pojo.Posts;
import com.inrhythm.service.impl.JsonService;

public class App {
	
	private static final Logger LOG = Logger.getLogger(App.class.getName());
	
	public static void main(String[] args) throws Exception {
		
		//String url = "http://jsonplaceholder.typicode.com/posts";// get from properties file
		System.out.println("STARTED");
 
		Properties prop = new Properties();
		InputStream configInput = null;
		String url = null;
		String dest = null;
		try {
			configInput = new FileInputStream("inrhythm.properties");
			prop.load(configInput);
			url = prop.getProperty("url");
			dest = prop.getProperty("dest");
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Error in loading configuration", e);
			e.printStackTrace();
		}
		
		JsonService service = new JsonService();	
		//Could use Spring to manage. Cause this is focus on Web API usage and basic I/O, so direct use "New" operator, same reason for other "New"
		
		InRhythmResponse rst = service.parseJson(url);	// 1 get the data from the url and modify data
		if (rst != null) {								
			output(rst, dest);			// 2 output file with InRhythmResponse rst
		} else {
			LOG.warning("the InRhythmResponse object is null");
		}
		System.out.println("FINISHED");
	}
	
	public static void output(InRhythmResponse rst, String dest){
		if (rst == null){
			LOG.warning("the input for output() is null");
			return;
		}
		List<Posts> posts = rst.getPosts();
		JSONArray arr = new JSONArray();
		for (Posts post: posts){
			Map obj = new LinkedHashMap();
			//use Map to keep the properties sequence of Posts. Raw type of Map can avoid the type casting
			obj.put("userId", post.getUserId());
			obj.put("id", post.getId());
			obj.put("title", post.getTitle());
			obj.put("body", post.getBody());
			arr.add(obj);
		}
		
		try {
			FileWriter file = new FileWriter(dest);//the location of file save in the properties file
			file.write(arr.toJSONString());
			file.flush();
			file.close();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Exception occurs when write out the inrhythm.json", e);
			e.printStackTrace();
		}
	}
}
