package at.kaindorf.exam.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <h3>Created by IntelliJ IDEA.</h3><br>
 * <b>Project:</b> Exa_104_SpringRest_JPA_ExamDB<br>
 * <b>User:</b> Simon Schoeggler<br>
 * <b>Date:</b> 25. Jänner 2023<br>
 * <b>Time:</b> 09:22<br>
 */

@Entity
@Getter
@Setter
public class Subject {
    @Id
    private Long subjectId;
    @Column(length = 100)
    private String longname;
    @Column(length = 10)
    private String shortname;
    @Column(nullable = false)
    private boolean written;

    @OneToMany(mappedBy = "subject")
    @JsonIgnore
    private List<Exam> exams;
}
