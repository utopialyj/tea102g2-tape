package com.post_photo.model;

import java.util.Arrays;

public class Post_PhotoVO implements java.io.Serializable{
	private String photo_uid;
	private byte[] photo;
	private String post_code;
	private Integer photo_sts;
	
	
	
	@Override
	public String toString() {
		return "Post_PhotoVO [photo_uid=" + photo_uid + ", photo=" + Arrays.toString(photo) + ", post_code=" + post_code
				+ ", photo_sts=" + photo_sts + "]";
	}
	public String getPhoto_uid() {
		return photo_uid;
	}
	public void setPhoto_uid(String photo_uid) {
		this.photo_uid = photo_uid;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public String getPost_code() {
		return post_code;
	}
	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}
	public Integer getPhoto_sts() {
		return photo_sts;
	}
	public void setPhoto_sts(Integer photo_sts) {
		this.photo_sts = photo_sts;
	}
	
	
}
