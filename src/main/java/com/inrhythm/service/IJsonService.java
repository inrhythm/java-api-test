package com.inrhythm.service;

import java.io.Reader;
import java.io.Writer;

import com.inrhythm.pojo.InRhythmResponse;

public interface IJsonService {

	/**
	 * Parse the JSON from the given reader. This reader could be a URL input
	 * stream reader or a File reader or any other custom reader.
	 * 
	 * Note: The caller is responsible for managing the reader (opening and
	 * closing it).
	 * 
	 * @param reader
	 * @return
	 * @throws Exception
	 */
	InRhythmResponse parseJson(Reader reader) throws Exception;

	/**
	 * Serialize the posts as JSON on the given Writer. The writer could either
	 * write to a file, or write to an output stream (say as part of POST
	 * request to an external URL).
	 * 
	 * Note: The caller is responsible for managing the writer (opening and
	 * closing it).
	 * 
	 * @param response
	 * @throws Exception
	 */
	void persistJson(InRhythmResponse response, Writer writer) throws Exception;

}
