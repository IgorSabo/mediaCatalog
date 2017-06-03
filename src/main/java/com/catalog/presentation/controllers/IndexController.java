package com.catalog.presentation.controllers;

import java.security.Principal;

import com.github.cage.Cage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.catalog.AppConstants;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
//import rs.gecko.app.business.util.Unfinished;

@Controller
public class IndexController {

	@Autowired
	Cage cage;

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping(value = AppConstants.PATH_LOGIN, method = RequestMethod.GET)
	private String login(HttpServletRequest req) {
		Cage cage = new Cage();
		final String token = cage.getTokenGenerator().next();

		HttpSession session = req.getSession(true);
		session.setAttribute("captchaToken", token);
		//markTokenUsed(session, false);

		return "login";
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	private String index() {
		System.out.println("Ulazak u kontroler za /index");
		return "index";
	}

//	@RequestMapping(value = "/")
//	public ModelAndView handleRequest() throws Exception  {
//
//		ModelAndView model=new ModelAndView("index");
//		return model;
//	}



	@RequestMapping(value = AppConstants.PATH_LOGOUT, method = RequestMethod.GET)
	private String logout() {

		return "login";
	}
	
	//@Unfinished("just for testing purpose")
	@RequestMapping(value = "/video", method = RequestMethod.GET)
	private String videoTest() {
		return "video";
	}
	


}
