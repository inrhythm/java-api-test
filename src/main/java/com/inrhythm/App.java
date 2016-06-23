package com.inrhythm;

import java.io.File;
import java.io.FileWriter;
import java.io.Reader;

import com.inrhythm.pojo.InRhythmResponse;
import com.inrhythm.service.IHttpService;
import com.inrhythm.service.IJsonService;
import com.inrhythm.service.impl.HttpService;
import com.inrhythm.service.impl.JsonService;

public class App {

	private static final String URL = "http://jsonplaceholder.typicode.com/posts";
	
	private final IJsonService jsonService = new JsonService();
	private final IHttpService httpService = new HttpService();
	
	public static void main(String[] args) throws Exception {
		System.out.println("STARTED");
		
		App app = new App();
		InRhythmResponse response = app.getResponse(URL);
		app.persist(response);
		
		System.out.println("FINISHED");

	}

	private InRhythmResponse getResponse(String url) throws Exception {
		Reader httpStreamReader = httpService.createInputStreamReader(url);
		InRhythmResponse response = jsonService.parseJson(httpStreamReader);
		return response;
	}

	private void persist(InRhythmResponse response) throws Exception {
		File file = new File("inrhythm.json");
		FileWriter fw = new FileWriter(file);
		try {
			jsonService.persistJson(response, fw);
		} finally {
			fw.close();
		}
	}
}
