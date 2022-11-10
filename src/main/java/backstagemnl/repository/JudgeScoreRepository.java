package backstagemnl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import backstagemnl.entity.JudgeScores;

public interface JudgeScoreRepository extends JpaRepository<JudgeScores, Integer> {

	@Query(nativeQuery = true, value = "select * from judge_score where candidate_number = :candidate_number and criteria_number = :criteria_number and judge_id = :judge_id")
	List<JudgeScores> getExistingScore(@Param("candidate_number") String candidateNumber,
			@Param("criteria_number") String criteriaNumber, @Param("judge_id") String judgeId);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(nativeQuery = true, value = "delete from judge_score where candidate_number = :candidate_number and criteria_number = :criteria_number and judge_id = :judge_id")
	void deleteExistingScore(@Param("candidate_number") String candidateNumber,
			@Param("criteria_number") String criteriaNumber, @Param("judge_id") String judgeId);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(nativeQuery = true, value = "update judge_score set candidate_score = :candidate_score "
			+ " where candidate_number = :candidate_number and criteria_number = :criteria_number and judge_id = :judge_id")
	int updateJudgeScore(@Param("candidate_score") String candidateScore,
			@Param("candidate_number") String candidateNumber, @Param("criteria_number") String criteriaNumber,
			@Param("judge_id") String judgeId);

	@Query(nativeQuery = true, value = "select * from judge_score order by candidate_number,criteria_number asc")
	List<JudgeScores> getScoreList();
}
