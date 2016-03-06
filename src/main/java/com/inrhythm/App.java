package com.inrhythm;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import com.google.gson.Gson;
import com.inrhythm.pojo.InRhythmResponse;
import com.inrhythm.service.impl.JsonService;

public class App {
	public static void main(String[] args) {
	
		String url = "http://jsonplaceholder.typicode.com/posts";	

		System.out.println("STARTED");
		
		JsonService service = new JsonService();

		InRhythmResponse response = null;
		try {
			response = service.parseJson(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Gson gson = new Gson();
		try(Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream("inrhythm.json"), "utf-8"))){
			gson.toJson(response, writer);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		System.out.println("FINISHED");

	}
}
