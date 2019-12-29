package com.example.controller;

import com.example.model.Entry;
import com.example.model.Member;
import com.example.model.People;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.User;
import com.example.repository.PeopleRepository;
import com.example.service.EntryService;
import com.example.service.PeopleService;
import com.example.service.UserService;



@Controller
public class LoginController {
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
	 Entry entry  ;
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private PeopleService peopleService;
	
	
	@Autowired
	private EntryService entryService;
	
	// private Path rootLocation = Paths.get("k");
	 private static final String OUTPUT_FILE = "";
	

	 //firstPage
	 @RequestMapping(value={"/",}, method = RequestMethod.GET)
		public ModelAndView firstPage(){
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("page");
			return modelAndView;
		}
		
	 
	@RequestMapping(value={"/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");
			
		}
		return modelAndView;
	}
	
	
	
	@RequestMapping(value="/addmember", method = RequestMethod.GET)
	public ModelAndView createNewMember(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("addmember");
		return modelAndView;
	}
	
	@RequestMapping(value = "/addmember", method = RequestMethod.POST)
	public ModelAndView createNewMember(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("addmember");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("addmember");
			
		}
		return modelAndView;
	}
	
	
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/admin/checker", method = RequestMethod.GET)
	public ModelAndView checker(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/checker");
		return modelAndView;
		
	}
	
	@RequestMapping(value="/admin/about", method = RequestMethod.GET)
	public ModelAndView about(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/about");
		return modelAndView;
		
	}
	
	@RequestMapping(value="/admin/del", method = RequestMethod.GET)
	public ModelAndView del(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/del");
		entryService.delete();
		return modelAndView;
		
	}
	
	@RequestMapping(value="/admin/del1", method = RequestMethod.GET)
	public ModelAndView del1(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/del1");
		peopleService.delete();
		return modelAndView;
		
	}
	
	
	@RequestMapping(value="/admin/member", method = RequestMethod.GET)
	public String member(Model model){
		

			String a = peopleService.findBy().toString();
		System.out.println("Len : " + a.length()+ "     p");
		System.out.println("i am here" + a.toString());
		
		List<People> pel = peopleService.findBy();
		System.out.println("op len: "+ pel.size()+ " now "  + pel);
	
		model.addAttribute("pel",pel);
		//System.out.println(model);
		return "admin/member";
		//return modelAndView;
		
	}
	
	
	@RequestMapping(value="/admin/Attendees", method = RequestMethod.GET)
	public String attendees(Model model){
		
		
			String a = entryService.findBy().toString();
		System.out.println("Len : " + a.length()+ "     p");
		System.out.println("i am here" + a.toString());
		
		List<Entry> pel = entryService.findBy();
		System.out.println("op len: "+ pel.size()+ " now "  + pel);
	
		model.addAttribute("pel",pel);
		//System.out.println(model);
		return "admin/Attendees";
		//return modelAndView;
		
	}
	
	
	
	@RequestMapping(value="/admin/pyexe", method = RequestMethod.GET)
	public ModelAndView pyexe(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/pyexe");
		//entryService.delete();
		 Set<String> attenceList = new TreeSet();
		String s = null;
		try {
			
			Process p = Runtime.getRuntime().exec("python3 fastscan.py");
            
            BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println("Java Here ::  " + s);
                
               
                String l = s.replaceAll("\\p{P}", "");
                
                
                if(attenceList.contains(l)) {
                	System.out.println("Already added ");
                	
                }else {
                	 attenceList.add(l);
               	  
               	  String name = s +" ";
                     String dates = dateFormat.format(date) + " ";
                     Entry e1 = new Entry(name,dates);
                    // entry.setDate("dateFormat.format(date)");
                     //entry.setName(s);
                     
                     
                     entryService.saveEntry(e1);
                	  
                }
                
                
                
              
                
                
               
               
                
            }
            
            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            
            //System.exit(0);
			///
			
			
			
		} catch (IOException e) {
			 System.out.println("exception happened - here's what I know: ");
	            e.printStackTrace();
	            System.exit(-1);
			e.printStackTrace();
		}
		
		return modelAndView;
		
	}
	@RequestMapping(value="/admin/pyexetrain", method = RequestMethod.GET)
	public ModelAndView pyexetrain(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/pyexetrain");
		String s = null;
		try {
			/**System.out.println("i am trying");
			//Process p = Runtime.getRuntime().exec("python yourapp.py");
			Process p = Runtime.getRuntime().exec("ps -ef");
			System.out.println(p + "\n" +  p.toString());
			System.out.println("Done it");
			System.out.println(p.getOutputStream().toString());*/
			
			
			///
			
			
			// run the Unix "ps -ef" command
            // using the Runtime exec method:
            Process p = Runtime.getRuntime().exec("python3 train.py");
            
            BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            
            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            
            //System.exit(0);
			///
			
			
			
		} catch (IOException e) {
			 System.out.println("exception happened - here's what I know: ");
	            e.printStackTrace();
	            System.exit(-1);
			e.printStackTrace();
		}
		
		return modelAndView;
		
	}
	
	
	@RequestMapping(value="/admin/pyexeQr", method = RequestMethod.GET)
	public ModelAndView pyexeQrcode(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/pyexeQr");
		
		 Set<String> attenceList1 = new TreeSet();
		String s = null;
		try {
			
			Process p = Runtime.getRuntime().exec("python3 qrCodeRecongnizer.py");
            
            BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
            	System.out.println("Java Here ::  " + s);
                
                
                String l = s.replaceAll("\\p{P}", "");
                
                
                if(attenceList1.contains(l)) {
                	System.out.println("Already added ");
                	
                }else {
                	 attenceList1.add(l);
               	  
               	  String name = l +" ";
                     String dates = dateFormat.format(date) + " ";
                     Entry e1 = new Entry(name,dates);
                    // entry.setDate("dateFormat.format(date)");
                     //entry.setName(s);
                     
                     
                     entryService.saveEntry(e1);
                
                
                
                
            }}
            
            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            
            //System.exit(0);
			///
			
			
			
		} catch (IOException e) {
			 System.out.println("exception happened - here's what I know: ");
	            e.printStackTrace();
	            System.exit(-1);
			e.printStackTrace();
		}
		
		return modelAndView;
		
	}

	
	
	
	
	}
	


