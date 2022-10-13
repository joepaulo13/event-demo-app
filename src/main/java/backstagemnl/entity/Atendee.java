package backstagemnl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "atendee_table")
public class Atendee {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "employee_id")
	private String employeeId;
	
	@Column(name = "raffle_key")
	private String raffleKey;
	
	@Column(name = "employee_name")
	private String employeeName;
	
	@Column(name = "department")
	private String department;
	
	@Column(name = "email_address")
	private String email;
	
	@Column(name = "verified_guest")
	private Boolean verifiedGuest;
	
	@Column(name = "verified_by")
	private String verifiedBy;
	
	@Column(name = "registration_date")
	private Date registrationDate;
	
	@Column(name = "verification_date")
	private Date verificationDate;
	
}
