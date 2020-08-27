package com.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.model.Blog;
import com.blog.repository.BlogRepository;

@Service
public class BlogService {
	@Autowired
	BlogRepository blogrepository;
	
	public void saveBlog(Blog b) {
		blogrepository.save(b);
	}
	public List<Blog> getBlog(){
		return (List<Blog>) blogrepository.findAll();
	}
	
	public Optional<Blog> getBlog(int id){
		return blogrepository.findById(id);
	}
	public void deleteBlog(int id) {
		blogrepository.deleteById(id);
	}
	public List<Blog> SearchBlog(String title){
		return blogrepository.findByTitleContaining(title);
	}
	public List<Blog> getDetails(){
		return (List<Blog>) blogrepository.findAll();
	}
}

