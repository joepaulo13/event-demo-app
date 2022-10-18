package backstagemnl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import backstagemnl.entity.Atendee;
import backstagemnl.entity.RaffleWinner;

public interface RaffleRepository extends JpaRepository<RaffleWinner, Integer> {
	
	//TODO : void addRaffleWinner();
	//TODO : List<Raffle> getRaffleWinners();
	
	List<RaffleWinner> findByEmployeeId(String employeeId);

}
