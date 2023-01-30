package at.kaindorf.exam.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * <h3>Created by IntelliJ IDEA.</h3><br>
 * <b>Project:</b> Exa_104_SpringRest_JPA_ExamDB<br>
 * <b>User:</b> Simon Schoeggler<br>
 * <b>Date:</b> 25. Jänner 2023<br>
 * <b>Time:</b> 09:18<br>
 */

@Entity
@Getter
@Setter
public class Classname {
    @Id
    private Long classId;
    @Column(length = 10)
    private String classname;

    @OneToMany(mappedBy = "classname")
    private List<Student> students = new ArrayList<>();
}
