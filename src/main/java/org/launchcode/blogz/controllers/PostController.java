package org.launchcode.blogz.controllers;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController extends AbstractController {

	@RequestMapping(value = "/blog/newpost", method = RequestMethod.GET)
	public String newPostForm() {
		return "newpost";
	}
	
	@RequestMapping(value = "/blog/newpost", method = RequestMethod.POST)
	public String newPost(HttpServletRequest request, Model model) {
		
		// TODO - implement newPost
		
		//get request parameters
		String title=request.getParameter("title");
		String body=request.getParameter("body");
		//validate parameters
		if (body.length() > 255) {
			model.addAttribute("error", "Maximum number of characters in the body is 255.");
			model.addAttribute("title", title);
			model.addAttribute("body", body);
			return "newpost";
		}
		if (title.equals("") || body.equals("")) {
			model.addAttribute("error", "Please provide a title and a body.");
			model.addAttribute("title", title);
			model.addAttribute("body", body);
			return "newpost";
		}
		//if valid, create new Post
		HttpSession thisSession=request.getSession();
		User thisUser = this.getUserFromSession(thisSession);
		Post newPost = new Post(title, body, thisUser);
		postDao.save(newPost);
		//if not valid, send them back to the form with error message
		
		return "redirect:" + thisUser.getUsername() + "/" + newPost.getUid(); // TODO - this redirect should go to the new post's page  		
	}
	
	//handles requests like /blog/chris/5
	@RequestMapping(value = "/blog/{username}/{uid}", method = RequestMethod.GET)
	public String singlePost(@PathVariable String username, @PathVariable int uid, Model model) {
		
		// TODO - implement singlePost
		
		//get the given post
		Post p = postDao.findByUid(uid);
		if (p== null) {
			
			model.addAttribute("error", "There is no post with id " + uid);
			return "404";	
		}
		
		User author = p.getAuthor();
		if (!author.getUsername().equals(username)) {
			model.addAttribute("error", "There is no post with id " + uid + " by user " + username );
			return "404";
		}
		//pass the post into the template
		model.addAttribute("post", p);
		return "post";
	}
	
	@RequestMapping(value = "/blog/{username}", method = RequestMethod.GET)
	public String userPosts(@PathVariable String username, Model model) {
		
		// TODO - implement userPosts
		
		//get all of the user's posts
		User u = userDao.findByUsername(username);
		List<Post> posts = postDao.findByAuthor(u);
		Collections.reverse(posts);
		//pass the posts into the template
		// like    model.addAttribute("name, someObj) or some listofPosts);
		model.addAttribute("posts", posts);
		return "blog";
	}
	
}
