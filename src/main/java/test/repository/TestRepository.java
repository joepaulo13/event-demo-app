package test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import test.entity.EventAtendee;

public interface TestRepository extends JpaRepository<EventAtendee, Integer> {


	EventAtendee findByEmployeeId(String employeeId);
}
