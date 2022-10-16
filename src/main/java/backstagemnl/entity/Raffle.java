package backstagemnl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Raffle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	@Column(name = "raffle_id")
	private String raffleId;
	
	@Column(name = "employee_id")
	private String employeeId;

	
	@Column(name = "employee_name")
	private String employeeName;
	
	@Column(name = "prize_number")
	private Integer prizeNumber;
	
	
	@Column(name = "prize_description")
	private String prizeDescription;
	
	@Column(name = "raffle_date")
	private Date raffleDate;
	
	@Column(name = "verification_date")
	private Date verificationDate;

	
}
