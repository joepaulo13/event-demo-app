package test.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public Object addUser(@RequestParam Map<String,String> params) {
		Map <String,Object> resp = new HashMap<String,Object>();
		EventAtendee eventAtendee = new EventAtendee();
		eventAtendee.setEmployeeId(params.get("employeeId"));
		eventAtendee.setEmployeeName(params.get("employeeName"));
		eventAtendee.setDepartment(params.get("department"));
		eventAtendee.setEmail(params.get("email"));
		eventAtendee.setRegistrationDate(new Date());
		eventAtendee.setVerifiedGuest(false);
		testRepository.save(eventAtendee);
		System.out.println(eventAtendee.toString()+ " - added");
		resp.put("result", "success");
		resp.put("entity", eventAtendee);
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
	
	

}