package backstagemnl.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/raffle")
public class RaffleController {

	@PostMapping("/addWinner")
	public Object addWinner(@RequestBody Map<String, Object> params) {
		Map<String, Object> resp = new HashMap<String, Object>();

		return resp;
	}
	
	@GetMapping("/getWinnerList")
	public Object getWinnerList(@RequestBody Map<String, Object> params) {
		Map<String, Object> resp = new HashMap<String, Object>();
		
		return resp;
	}

}
