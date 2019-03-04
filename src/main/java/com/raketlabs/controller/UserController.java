package com.raketlabs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.raketlabs.form.UserForm;
import com.raketlabs.model.UserInfo;
import com.raketlabs.service.UserService;
import com.raketlabs.validator.SignUpValidator;



@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	SignUpValidator signUpValidator;

	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView model = new ModelAndView("user/list");
		model.addObject("list", userService.list());

		return model;
	}

	@RequestMapping(value = "/changePass/{username}", method = RequestMethod.GET)
	public ModelAndView changePass(@PathVariable("username") String username) {
		ModelAndView model = new ModelAndView("user/change_pass");
		
		UserInfo user = userService.findUserByUsername(username).get();
		model.addObject("user", user);

		return model;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("user") UserInfo user, RedirectAttributes redirectAttributes) {
		
		userService.update(user.getUserName(), user.getPassword());
		
		ModelAndView model = new ModelAndView("redirect:/");
		redirectAttributes.addFlashAttribute("success", "Your password has been changed successfully!");
		return model;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signup() {
		
		ModelAndView model = new ModelAndView("user/signup");
		model.addObject("userForm", new UserForm());

		return model;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@ModelAttribute("userForm") UserForm userForm, BindingResult result,
			RedirectAttributes redirectAttributes) {

		signUpValidator.validate(userForm, result);

		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("error", "Unable to create account.");
			return "redirect:/user/signup";
		} else {
			userService.add(userForm.getUsername(), userForm.getPassword());
			redirectAttributes.addFlashAttribute("success", "Your account has been created successfully!");
			return "redirect:/login";
		}
	}

}