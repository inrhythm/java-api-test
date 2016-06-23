package com.inrhythm.service;

import java.io.Reader;

public interface IHttpService {
	
	/**
	 * Opens an Input Stream Reader from the given URL.
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	Reader createInputStreamReader(String url) throws Exception;
}
