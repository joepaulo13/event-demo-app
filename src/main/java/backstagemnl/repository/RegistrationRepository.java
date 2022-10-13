package backstagemnl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backstagemnl.entity.Atendee;

public interface RegistrationRepository extends JpaRepository<Atendee, Integer> {


	List<Atendee> findByEmployeeId(String employeeId);
	//TODO : add update atendee validation date code
}
