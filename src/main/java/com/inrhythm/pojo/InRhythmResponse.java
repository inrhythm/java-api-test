package com.inrhythm.pojo;

import java.util.List;

public class InRhythmResponse {

	private int userCount;
	private List<Posts> posts;

	public List<Posts> getPosts() {
		return posts;
	}

	public void setPosts(List<Posts> posts) {
		this.posts = posts;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

}
