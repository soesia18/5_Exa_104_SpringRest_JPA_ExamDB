package at.kaindorf.exam.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * <h3>Created by IntelliJ IDEA.</h3><br>
 * <b>Project:</b> Exa_104_SpringRest_JPA_ExamDB<br>
 * <b>User:</b> Simon Schoeggler<br>
 * <b>Date:</b> 25. Jänner 2023<br>
 * <b>Time:</b> 09:19<br>
 */

@Entity
@Getter
@Setter
@ToString
public class Student {
    @Id
    private Long studentId;
    @Column(length = 80)
    private String firstname;
    @Column(length = 80)
    private String lastname;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "classname")
    private Classname classname;

    @OneToMany(mappedBy = "student")
    private List<Exam> exams = new ArrayList<>();
}
