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
@Table(name = "judge_score")
public class JudgeScores {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "candidate_number")
	private String candidateNumber;

	@Column(name = "criteria_number")
	private String criteriaNumber;

	@Column(name = "candidate_score")
	private String candidateScore;

	@Column(name = "last_modified_datetime")
	private Date lastModifiedDatetime;
	
	@Column(name = "judge_id")
	private String judgeId;

}
