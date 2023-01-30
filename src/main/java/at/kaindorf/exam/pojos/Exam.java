package at.kaindorf.exam.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3>Created by IntelliJ IDEA.</h3><br>
 * <b>Project:</b> Exa_104_SpringRest_JPA_ExamDB<br>
 * <b>User:</b> Simon Schoeggler<br>
 * <b>Date:</b> 25. Jänner 2023<br>
 * <b>Time:</b> 09:21<br>
 */

@Entity
@Getter
@Setter
public class Exam {
    @Id
    private Long examId;
    @Column(name = "dateofexam")
    private LocalDate dateOfExam;
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "student")
    @JsonIgnore
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject")
    private Subject subject;
}
