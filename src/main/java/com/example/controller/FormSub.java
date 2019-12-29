package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.People;
import com.example.model.User;
import com.example.service.MailService;
import com.example.service.PeopleService;
import com.example.service.QRgenetorService;
import com.example.service.StorageService;
import com.example.service.UserService;

@Controller
public class FormSub {
	
	@Autowired
	StorageService storageService;
	List<String> files = new ArrayList<String>();
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private QRgenetorService qrcodeService;
	
	@Autowired
	private PeopleService peopleService;
	
	
	@RequestMapping(value={"/welcome1"}, method = RequestMethod.GET)
    public String getHomePage(ModelMap model) {
        return "welcome";
    }
	//1
	@RequestMapping(value="/welcome", method = RequestMethod.GET)
	public ModelAndView welcome(){
		ModelAndView modelAndView = new ModelAndView();
		People people = new People();
		modelAndView.addObject("people", people);
		modelAndView.setViewName("welcome");
		return modelAndView;
	}
	//2
	@RequestMapping(value = "/welcome", method = RequestMethod.POST)
	public ModelAndView createNewPeople(Model model,@RequestParam("file") MultipartFile file, @Valid People people, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		
		People peopleExistsEmail = peopleService.findUserByNic(people.getNic());
		if (peopleExistsEmail != null) {
			bindingResult
					.rejectValue("nic", "error.people",
							"There is already a person registered with the nic provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("welcome");
		} else {
			peopleService.savePeople(people);
			modelAndView.addObject("successMessage", "People has been registered successfully");
			modelAndView.addObject("people", new People());
			System.out.println("id ha");
			try {
	//for testing			//mailService.sendEmail("khalid.bin.huda@hotmail.com", "Thank You For Registration For the Event  \n your Nic: " + people.getNic());
				mailService.sendEmail(people.getEmail(), "Thank You For Registration For the Event  \n your Nic: " + people.getNic());
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//dile
			storageService.store(file);
			model.addAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
			files.add(file.getOriginalFilename());
			
			
			
			//storageService.init();
			modelAndView.setViewName("welcome");
			
		}
		return modelAndView;
	}
	
	

	
	
	
	
	
	
	
	

}
