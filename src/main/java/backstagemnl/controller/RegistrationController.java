package backstagemnl.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backstagemnl.entity.Atendee;
import backstagemnl.repository.RegistrationRepository;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

	@Autowired
	private RegistrationRepository registrationRepository;

	@Value("${jasypt.decryption.seed}")
	private String seed;

	String classname = this.getClass().getName();

	@PostMapping("/addUser")
	public Object addUser(@RequestBody Map<String, Object> params) {
		Map<String, Object> resp = new HashMap<String, Object>();
		Atendee atendee = new Atendee();
		System.out.println(classname + " : " + params.toString());
		try {
			atendee.setEmployeeId((String) params.get("employeeId"));
			atendee.setEmployeeName((String) params.get("employeeName"));
			atendee.setDepartment((String) params.get("department"));
			atendee.setEmail((String) params.get("email"));
			atendee.setRegistrationDate(new Date());
			atendee.setVerifiedGuest(false);
			atendee.setRaffleKey("UBQ".concat((String) params.get("employeeId")));
			registrationRepository.save(atendee);
			resp.put("result", "success");
			resp.put("entity", atendee);
			resp.put("qrKey", encryptString(atendee.getEmployeeId()));
		} catch (Exception e) {
			e.printStackTrace();
			resp.put("result", "fail");
			resp.put("failMessage", "Error encountered during table insert : " + e.getMessage());
		}
		System.out.println(classname + " : response = " + resp.toString());
		return resp;
	}

	@PostMapping("/validateUser")
	public Object validateUser(@RequestBody Map<String, Object> params) {
		String qrKey = (String) params.get("qrKey");
		System.out.println(classname + " : qrkey inputted = " + qrKey);
		Map<String, Object> resp = new HashMap<String, Object>();
		if (null == qrKey || qrKey.isEmpty()) {
			System.out.println(classname + " : qrKey field is blank");
			resp.put("result", "fail");
			resp.put("failMessage", "qrKey field is blank");
			return resp;
		}
		try {
			String encryptedemployeeId = qrKey;
			String employeeId = decryptString(encryptedemployeeId);
			List<Atendee> atendee = registrationRepository.findByEmployeeId(employeeId);
			System.out.println(classname + " : Atendee details = " + atendee.toString());
			if (null == atendee || atendee.size() != 1) {
				resp.put("result", "fail");
				resp.put("failMessage", "Employee details are not included in the registered employee masterlist");
			} else {
				int verify_count = registrationRepository.verifyAtendee(employeeId, new Date());
				if (verify_count==1) {
					registrationRepository.flush();
					resp.put("result", "success");
					resp.put("entity", registrationRepository.findByEmployeeId(employeeId).get(0));
				} else {
					resp.put("result", "fail");
					resp.put("failMessage",classname + "Error encountered during atendee validation,try again.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.put("result", "fail");
			resp.put("failMessage", "Error encountered during atendee validation : " + e.getMessage());
		}
		System.out.println(classname + " : response = " + resp.toString());
		return resp;
	}

	@GetMapping("/testApi")
	public Object index() {
		Map<String, Object> resp = new HashMap<String, Object>();
		List<Atendee> atendeeList = registrationRepository.findAll();
		atendeeList.forEach(System.out::println);
		resp.put("atendeeList", atendeeList);
		System.out.println(classname + " : response = " + resp.toString());
		return resp;
	}

	public String encryptString(String encryptString) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(seed);
		try {
			encryptString = encryptor.encrypt(encryptString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptString;
	}

	public String decryptString(String decryptString) {
		StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
		decryptor.setPassword(seed);
		try {
			decryptString = decryptor.decrypt(decryptString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptString;
	}

}