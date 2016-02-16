package com.inrhythm.service;

import com.inrhythm.pojo.InRhythmResponse;

public interface IJsonService {

	public InRhythmResponse parseJson(String url) throws Exception;

}
