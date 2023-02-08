package at.kaindorf.exam.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * <h3>Created by IntelliJ IDEA.</h3><br>
 * <b>Project:</b> Exa_104_SpringRest_JPA_ExamDB<br>
 * <b>User:</b> Simon Schoeggler<br>
 * <b>Date:</b> 06. Februar 2023<br>
 * <b>Time:</b> 12:33<br>
 */

@Getter
@Setter
public class CreationExam {
    private Long examId;
    private Long studentId;
    private Long subjectId;
    private LocalDate dateOfExam;
    private Integer duration;
}
