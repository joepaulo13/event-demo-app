package backstagemnl.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import backstagemnl.entity.Atendee;

public interface RegistrationRepository extends JpaRepository<Atendee, Integer> {


	List<Atendee> findByEmployeeId(String employeeId);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(nativeQuery = true,
			value = "update atendee_table set verification_date = :verificationDate, verified_guest = true where "
					+ "employee_id = :employeeId")
	int verifyAtendee(
			@Param("employeeId") String employeeId,
			@Param("verificationDate") Date verificationDate);
	
	
	@Query(nativeQuery = true,
			value = "select * from atendee_table where verified_guest = true and employee_id = :employeeId order by verification_date desc limit 1")
	Atendee postverifyAtendee(
			@Param("employeeId") String employeeId);
}
