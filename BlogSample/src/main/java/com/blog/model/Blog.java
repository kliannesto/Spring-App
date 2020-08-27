package com.blog.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Blog {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private int id;
	private String title;
	private String body;
	private String author;
	private Date datepublished;

	@Lob
	private byte[]img;

	public Blog() {
		
	}


	public Blog(int id,String title, String body, String author, Date datepublished,byte[]img) {
		super();
		this.id=id;
		this.title = title;
		this.body = body;
		this.author = author;
		this.datepublished = datepublished;
		this.img=img;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id=id;
	}

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getBody() {
		return body;
	}


	public void setBody(String body) {
		this.body = body;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public Date getDatepublished() {
		return datepublished;
	}


	public void setDatepublished(Date datepublished) {
		this.datepublished = datepublished;

	}
	public byte[] getImg() {
		return img;
	}



	public void setImg(byte[] img) {
		this.img = img;
	}

}
