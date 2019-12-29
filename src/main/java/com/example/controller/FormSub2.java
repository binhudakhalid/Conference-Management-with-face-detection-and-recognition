package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.People;
import com.example.model.User;
import com.example.service.MailService;
import com.example.service.PeopleService;
import com.example.service.QRgenetorService;
import com.example.service.StorageService;
import com.example.service.UserService;
import com.google.zxing.WriterException;

@Controller
public class FormSub2 {
	String filenameQr;
	
	List<String> files = new ArrayList<String>();
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private QRgenetorService qrcodeService;
	
	@Autowired
	private PeopleService peopleService;
	
	
	@RequestMapping(value="/g", method = RequestMethod.GET)
	public ModelAndView welcomeG(){
		ModelAndView modelAndView = new ModelAndView();
		People people = new People();
		modelAndView.addObject("people", people);
		modelAndView.setViewName("welcomeqrd");
       

         String QR_CODE_IMAGE_PATH = "./";
         
		try {
			qrcodeService.generateQRCodeImage("pop my first QR Code is s sgvsdfvsdf", 350, 350, QR_CODE_IMAGE_PATH + "ode.png");
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return modelAndView;
	}
	

	//1
	@RequestMapping(value="/welcomeqr", method = RequestMethod.GET)
	public ModelAndView welcome(){
		ModelAndView modelAndView = new ModelAndView();
		People people = new People();
		modelAndView.addObject("people", people);
		modelAndView.setViewName("welcomeqr");
		
		return modelAndView;
	}
	//2
	@RequestMapping(value = "/welcomeqr", method = RequestMethod.POST)
	public ModelAndView createNewPeople(Model model, @Valid People people, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		
		File serverFile ;
		
		People peopleExistsEmail = peopleService.findUserByNic(people.getNic());
		System.out.println("Name " + people.getName() + " Nic"  +people.getNic());
		if (peopleExistsEmail != null) {
			bindingResult
					.rejectValue("nic", "error.people",
							"There is already a person registered with the nic provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("welcomeqr");
		} else {
			peopleService.savePeople(people);
			modelAndView.addObject("successMessage", "People has been registered successfully");
			modelAndView.addObject("people", new People());
			String QR_CODE_IMAGE_PATH = "./src/main/resources/static/images/";
			
			try {
				 SimpleDateFormat sdf = new SimpleDateFormat("MM:mm:ss");
			     filenameQr = people.getName() +sdf.format(System.currentTimeMillis()) + ".png" ;
				
				qrcodeService.generateQRCodeImage(people.getName() + " " +people.getLastName() + " " + people.getNic(), 350, 350, QR_CODE_IMAGE_PATH + filenameQr);
				
			//System.out.println("me "  +qrcodeService.getQRCodeImageByteArray("hello", 350, 350));
				
				
				
				// serverFile = new File(QR_CODE_IMAGE_PATH + filenameQr);

			     //Files.readAllBytes(serverFile.toPath());
				System.out.println("HE" + QR_CODE_IMAGE_PATH + filenameQr);
				
			
			} catch (WriterException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			
			
			System.out.println("id ha");
			try {
			mailService.sendEmail(people.getEmail(), "Thank You For Registration For the Event  \n your Nic: " + people.getNic());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		//	modelAndView.addObject("message",filenameQr);
			
			//String filenameQr1 = "/images/"+filenameQr;
			String filenameQr1 = filenameQr;
			
			model.addAttribute("message",filenameQr1);
			//System.out.println("filename"+ filenameQr1);
			
			modelAndView.setViewName("welcomeqr");
			//model.(Files.readAllBytes(serverFile.toPath()));
			
			
			
		}
		
		return modelAndView;
	
	
	
	}
	
	//3
	@RequestMapping(value = "/welcomeqr/image/{imageName}")
	@ResponseBody
	public byte[] getImage(@PathVariable(value = "imageName") String imageName) throws IOException {

	 //   File serverFile = new File("./home/user/uploads/" + imageName + ".jpg");
		File serverFile = new File("./src/main/resources/static/images/" + imageName + ".png");
	    return Files.readAllBytes(serverFile.toPath());
	}
	

	
	
	
	 	
	
	
	
	

}
