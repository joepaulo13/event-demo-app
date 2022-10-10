package backstagemnl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import backstagemnl.entity.Atendee;

public interface RegistrationRepository extends JpaRepository<Atendee, Integer> {


	Atendee findByEmployeeId(String employeeId);
}
