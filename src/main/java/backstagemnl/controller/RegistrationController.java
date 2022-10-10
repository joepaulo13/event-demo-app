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
import net.bytebuddy.asm.Advice.This;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

	@Autowired
	private RegistrationRepository registrationRepository;

	@Value("${jasyp.decryption.seed}")
	private String seed;

	@PostMapping("/addUser")
	public Object addUser(@RequestBody Map<String, Object> params) {
		Map<String, Object> resp = new HashMap<String, Object>();
		Atendee atendee = new Atendee();
		System.out.println(this.getClass().getName()+" : "+params.toString());
		try {
			atendee.setEmployeeId((String) params.get("employeeId"));
			atendee.setEmployeeName((String) params.get("employeeName"));
			atendee.setDepartment((String) params.get("department"));
			atendee.setEmail((String) params.get("email"));
			atendee.setRegistrationDate(new Date());
			atendee.setVerifiedGuest(false);
			registrationRepository.save(atendee);
			resp.put("result", "success");
			resp.put("entity", atendee);
		} catch (Exception e) {
			e.printStackTrace();
			resp.put("result", "fail");
			resp.put("failMessage", "Error encountered during table insert : " + e.getMessage());
		}
		resp.put("qrKey", encryptString(atendee.getEmployeeId()));
		System.out.println(resp.toString());
		return resp;
	}

	@GetMapping("/getUser")
	public Object getUser(String qrKey) {
		Map<String, Object> resp = new HashMap<String, Object>();
		if (null==qrKey || qrKey.length()==0) {
			System.out.println(this.getClass().getName()+" : qrKey field is blank");
			resp.put("result", "fail");
			resp.put("failMessage", "qrKey field is blank");
			return resp;
		}
		
		String encryptedemployeeId = qrKey;
		String employeeId = decryptString(encryptedemployeeId);
		Atendee atendee = registrationRepository.findByEmployeeId(employeeId);
		System.out.println(this.getClass().getName()+" : Atendee details = " + atendee.toString());
		return atendee;
	}

	@GetMapping("/testApi")
	public String index() {
		List<Atendee> testTable = registrationRepository.findAll();
		testTable.forEach(System.out::println);
		return testTable.toString();
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