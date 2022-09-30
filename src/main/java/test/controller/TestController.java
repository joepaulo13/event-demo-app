package test.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import test.entity.EventAtendee;
import test.repository.TestRepository;

@RestController
@RequestMapping("/testApp")
public class TestController {
	
	@Autowired
	private TestRepository testRepository;


	@PostMapping("/addUser")
	public Object addUser(@RequestParam Map<String,Object> params) {
		Map <String,Object> resp = new HashMap<String,Object>();
		EventAtendee eventAtendee = new EventAtendee();
		System.out.println(params.toString());
		eventAtendee.setEmployeeId((String) params.get("employeeId"));
		eventAtendee.setEmployeeName((String) params.get("employeeName"));
		eventAtendee.setDepartment((String) params.get("department"));
		eventAtendee.setEmail((String) params.get("email"));
		eventAtendee.setRegistrationDate(new Date());
		eventAtendee.setVerifiedGuest(false);
		try {
		testRepository.save(eventAtendee);
		resp.put("result", "success");
		resp.put("entity", eventAtendee);
		}
		catch (Exception e) {
			e.printStackTrace();
			resp.put("result", "fail");
			resp.put("failMessage", "Error encountered during table insert : "+e.getMessage());
		}
		resp.put("qrKey", encrypString(eventAtendee.getEmployeeName()));
		System.out.println(resp.toString());
		return resp;
	}
	
	@GetMapping("/getUser")
	public Object getUser(String employeeId,@RequestParam(required = false) String arg2) {
		EventAtendee eventAtendee= testRepository.findByEmployeeId(employeeId);
		System.out.println("arg2 = "+arg2);
		System.out.println("EventAtendee details = "+eventAtendee.toString());
		return eventAtendee;
	}
	
	
	@GetMapping("/testApi")
	public String index() {
		List<EventAtendee> testTable = testRepository.findAll();
		testTable.forEach(System.out :: println);
		return testTable.toString();
	}
	
	public String encrypString(String encryptString) {
		String seed = "encryptionPassword";
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(seed);
		try {
		encryptString = encryptor.encrypt(encryptString);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return encryptString;
	}
	
	
	

}