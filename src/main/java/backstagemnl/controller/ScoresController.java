package backstagemnl.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backstagemnl.entity.JudgeScores;
import backstagemnl.repository.JudgeScoreRepository;

@RestController
@RequestMapping("/judgescore")
public class ScoresController {

	@Autowired
	private JudgeScoreRepository judgeScoreRepository;

	String classname = this.getClass().getName();

	@PostMapping("/addScore")
	public Object addScore(@RequestBody Map<String, Object> params) {
		Map<String, Object> resp = new HashMap<String, Object>();
		List<JudgeScores> existingJudgeScore;
		try {
			String candidateNumber = ((String) params.get("candidate_number"));
			String criteriaNumber = ((String) params.get("criteria_number"));
			String candidateScore = ((String) params.get("candidate_score"));
			String judgeId = ((String) params.get("judge_id"));
			String successMessage = "";

			existingJudgeScore = judgeScoreRepository.getExistingScore(candidateNumber, criteriaNumber, judgeId);

			if (null == existingJudgeScore || existingJudgeScore.size() == 0) {
				JudgeScores judgeScore = new JudgeScores();
				judgeScore.setCandidateNumber(candidateNumber);
				judgeScore.setCandidateScore(candidateScore);
				judgeScore.setCriteriaNumber(criteriaNumber);
				judgeScore.setJudgeId(judgeId);
				judgeScore.setLastModifiedDatetime(new Date());
				judgeScoreRepository.save(judgeScore);
				successMessage = "Succesfully added score for candidate " + candidateNumber;
				resp.put("result", "success");
				resp.put("successMessage", successMessage);

			} else {
				int verifyUpdatecount = judgeScoreRepository.updateJudgeScore(candidateScore, candidateNumber,
						criteriaNumber, judgeId);
				if (verifyUpdatecount != 0) {
					judgeScoreRepository.flush();
					successMessage = "Succesfully updated score for candidate " + candidateNumber;
					resp.put("result", "success");
					resp.put("successMessage", successMessage);
				} else {
					resp.put("result", "fail");
					resp.put("failMessage", classname + "Error encountered during Judge Score update, try again.");
				}

			}

		} catch (Exception e) {
			resp.put("result", "fail");
			resp.put("failMessage", "Error encountered while adding judge score : " + e.getMessage());
		}
		return resp;
	}

	@PostMapping("/deleteScore")
	public Object deleteScore(@RequestBody Map<String, Object> params) {
		Map<String, Object> resp = new HashMap<String, Object>();

		try {
			String candidateNumber = ((String) params.get("candidate_number"));
			String criteriaNumber = ((String) params.get("criteria_number"));
			String judgeId = ((String) params.get("judge_id"));

			judgeScoreRepository.deleteExistingScore(candidateNumber, criteriaNumber, judgeId);
			judgeScoreRepository.flush();
			resp.put("result", "success");
			resp.put("successMessage",
					"Succesfully deleted score of candidate " + candidateNumber + " for criteria " + criteriaNumber);

		} catch (Exception e) {
			resp.put("result", "fail");
			resp.put("failMessage", "Error encountered while deleting judge score : " + e.getMessage());
		}
		return resp;
	}

	@GetMapping("/getAverageScores")
	public Object getAverageScores() {
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
		double distinctJudgeCount = (double) judgeScoreRepository.getDistinctJudgeCount();
		List<String> distinctCandidateNumber = judgeScoreRepository.getDistinctCandidates();
		for (String candidateNumber : distinctCandidateNumber) {
			List<String> candidateScores = judgeScoreRepository.getCandidateScores(candidateNumber);
			double totalScore = 0;
			double averageScore = 0;
			for (String candidateScore : candidateScores) {
				totalScore += Double.parseDouble(candidateScore);
			}
			averageScore = totalScore/distinctJudgeCount;
			resp.put(candidateNumber, averageScore);
		}
		}catch (Exception e) {
			resp.put("result", "fail");
			resp.put("failMessage", "Error encountered while getting Average Scores : " + e.getMessage());
		}
		System.out.println(resp);
		return resp;
	}

	@GetMapping("/getAllScores")
	public Object getAllScores() {
		Map<String, Object> resp = new HashMap<String, Object>();
		try {
			List<JudgeScores> judgeScoresList = judgeScoreRepository.getScoreList();
			System.out.println(classname + " : judgeScoresList = " + judgeScoresList.toString());
			resp.put("entity", judgeScoresList);
			resp.put("result", "success");
		} catch (Exception e) {
			resp.put("result", "fail");
			resp.put("failMessage", "Error encountered while getting judge scores list : " + e.getMessage());
		}
		System.out.println(classname + " : response = " + resp.toString());
		return resp;
	}

}