package com.blog.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.blog.model.Blog;
import com.blog.model.User;
import com.blog.service.BlogService;
import com.blog.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	BlogService blogService;
	
	@PostMapping ("/saveBlog")
	public String saveBlog(Blog b, @RequestParam("file") MultipartFile file ) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			b.setAuthor(auth.getName());
			
			//Date now = new Date();
			//DateFormat df=new SimpleDateFormat(pattern:"MM-DD-YYYY");
			
			//b.setDatecreated(df.format(now);
			
			b.setImg(file.getBytes());
		} catch (IOException e) {
			// TO DO Auto-generated catch block
			e.printStackTrace();
		}  
		blogService.saveBlog(b);
		return "redirect:/viewBlogs";
	}
	
	@GetMapping("admin/blogs/delete/{id}")
	public String deleteblog(@PathVariable(name="id")int id) {
		blogService.deleteBlog(id);
		return "redirect:/viewBlogs";
	}
	
	@GetMapping("admin/postedBlogs/edit/{id}")
	public String editBlog(@PathVariable(value="id")int id, Model model) {
		model.addAttribute("Blog",blogService.getBlog(id));
		return "Addform";
	}
	
	public List<Blog> getblog(){
		  return(List<Blog>)blogService.getBlog();
	  }
	
	@GetMapping ("/viewBlogs")
	public String displayBlog(Model m) {
		List<Blog> blogs = new ArrayList<>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		blogService.getBlog().forEach(blogs::add);
		m.addAttribute("blogs",blogs);
		m.addAttribute("Title", "Posted Blogs");
		m.addAttribute("isHome", false);
		m.addAttribute("isAddPost", false);
		m.addAttribute("isViewBlogs", true);
		m.addAttribute("user",auth.getName());
		
		return "postedBlogs";
	}
	
	@GetMapping("/addpost")
	public String blogForm(Model model)
	{
		model.addAttribute("Title", "Add Post");
		model.addAttribute("Blog", new Blog());
		
		model.addAttribute("isHome", false);
		model.addAttribute("isAddPost", true);
		model.addAttribute("isViewBlogs", false);
		return "Addform";
	}
		
	@GetMapping("/admin/home")
	public String displayblog(Model m) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		m.addAttribute("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		m.addAttribute("adminMessage","Content Available Only for Users with Admin Role");
		m.addAttribute("Title", "Home");
		m.addAttribute("isHome", true);
		m.addAttribute("isAddPost", false);
		m.addAttribute("isViewBlogs", false);
		
		return "home";
	}	
	@GetMapping("/login")
	public String login(Model m) {
		return "login";
	}
		
//	@GetMapping("/page")
//	public String displaypage(Model m) {
//		return "page";
//	}
	
	@GetMapping("/")
	public String homepage(Model m) {
		return "page";
	}

	@GetMapping(value="/image/{id}",produces= MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getImage(@PathVariable("id")int id) throws IOException{
		
		Optional<Blog> blog =blogService.getBlog(id);
		
		byte[]imageContent=blog.get().getImg();
		
		final HttpHeaders headers= new HttpHeaders();
		
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<byte[]>(imageContent,headers,HttpStatus.OK);
	}
	
	@GetMapping("/search")
	 public String searchBlog(@RequestParam("search") String title,Model m) {
		List<Blog> blogs= blogService.SearchBlog(title);
		m.addAttribute("blogs",blogs);
		return "postedBlogs";
	}	
	
	@RequestMapping("/time")
	public String time() {
		return "home";
	}
	
	@GetMapping ("/qoutes")
	public String displayQoutes(Model m) {
		return "qoutes";
	}
	
}
