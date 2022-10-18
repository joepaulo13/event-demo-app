package backstagemnl.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backstagemnl.entity.Atendee;
import backstagemnl.entity.RaffleWinner;
import backstagemnl.repository.RaffleRepository;
import backstagemnl.repository.RegistrationRepository;



@RestController
@RequestMapping("/raffle")
public class RaffleController {
	
	@Autowired
	private RegistrationRepository registrationRepository;

	@Autowired
	private RaffleRepository raffleRepository;
	
	String classname = this.getClass().getName();

	@PostMapping("/addWinner")
	public Object addWinner(@RequestBody Map<String, Object> params) {
		Map<String, Object> resp = new HashMap<String, Object>();
		RaffleWinner raffleWinner = new RaffleWinner();
		System.out.println(classname + " : " + params.toString());

		try {
			String employeeID = (String) params.get("employeeId");
			List<Atendee> registeredAtendee = registrationRepository.findByEmployeeId(employeeID);
			if (registeredAtendee.isEmpty()) {
				resp.put("result", "fail");
				resp.put("failMessage", "Employee "+employeeID+" is not registered");
				return resp;
			}
			else {
				List<RaffleWinner> existingRaffleWinner = raffleRepository.findByEmployeeId(employeeID);
				if (!existingRaffleWinner.isEmpty()) {
					resp.put("result", "fail");
					resp.put("failMessage", "Employee "+employeeID+" is already a raffle winner");
				}
				else {
					raffleWinner.setRaffleId((String) params.get("raffleID"));
					raffleWinner.setEmployeeId(employeeID);
					raffleWinner.setEmployeeName(registeredAtendee.get(0).getEmployeeName());
					raffleWinner.setPrizeNumber((Integer) params.get("prizeNumber"));
					raffleWinner.setPrizeDescription((String) params.get("prizeDescription"));
					raffleWinner.setRaffleDate(new Date());
					raffleRepository.save(raffleWinner);
					resp.put("result", "success");
					resp.put("entity", raffleWinner);
				}
			}
			}
		catch (Exception e) {
			resp.put("result", "fail");
			resp.put("failMessage", "Error encountered while adding a raffle winner : " + e.getMessage());
		}
		System.out.println(classname + " : response = " + resp.toString());
		return resp;
	}
	
	@GetMapping("/getEmployeeList")
	public Object getEmployeeList() {
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
		List<Atendee> atendeeList = registrationRepository.findAll();
		System.out.println(classname + " : employeelist = " + atendeeList.toString());
		resp.put("employeelist", atendeeList);
		resp.put("result", "success");
		}
		catch (Exception e) {
			resp.put("result", "fail");
			resp.put("failMessage", "Error encountered while getting employeelist : " + e.getMessage());
		}
		System.out.println(classname + " : response = " + resp.toString());
		return resp;
	}
	
	@GetMapping("/getWinnerList")
	public Object getWinnerList() {
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
		List<RaffleWinner> raffleWinnerList = raffleRepository.findAll();
		System.out.println(classname + " : raffleWinnerList = " + raffleWinnerList.toString());
		resp.put("raffleWinnerList", raffleWinnerList);
		resp.put("result", "success");
		}
		catch (Exception e) {
			resp.put("result", "fail");
			resp.put("failMessage", "Error encountered while getting raffleWinnerList : " + e.getMessage());
		}
		System.out.println(classname + " : response = " + resp.toString());
		return resp;
	}

}
