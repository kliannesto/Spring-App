package com.blog.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.blog.model.Blog;


public interface BlogRepository extends CrudRepository<Blog,Integer>{

	public List<Blog> findByTitleContaining(String title);
}
