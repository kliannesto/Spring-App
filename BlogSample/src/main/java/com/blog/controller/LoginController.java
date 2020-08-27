package com.blog.controller;

import javax.validation.Valid;

import org.apache.tomcat.util.modeler.modules.ModelerSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.blog.model.User;
import com.blog.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/registration")
	public String registration(Model m){
	
		User user = new User();
		m.addAttribute("user", user);
		return "registration";
	}
	
	@PostMapping("/registration")
	public String createNewUser(@Valid User user, BindingResult bindingResult,Model m) {
		
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			return "registration";
		} else {
			userService.saveUser(user);
			m.addAttribute("successMessage", "User has been registered successfully!");
			m.addAttribute("user", new User());	
		}
		return "registration";
	}
	
	

	

}
