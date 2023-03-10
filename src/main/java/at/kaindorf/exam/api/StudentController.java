package at.kaindorf.exam.api;

import at.kaindorf.exam.database.ClassnameRepository;
import at.kaindorf.exam.database.StudentRepository;
import at.kaindorf.exam.pojos.Classname;
import at.kaindorf.exam.pojos.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * <h3>Created by IntelliJ IDEA.</h3><br>
 * <b>Project:</b> Exa_104_SpringRest_JPA_ExamDB<br>
 * <b>User:</b> Simon Schoeggler<br>
 * <b>Date:</b> 25. Jänner 2023<br>
 * <b>Time:</b> 09:18<br>
 */

@RestController
@Slf4j
@RequestMapping(value = "/student", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class StudentController {
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Student>> getStudentsFromClass(@PathVariable Long id) {
        List<Student> students =
                studentRepository.findStudentsByClassname_ClassIdOrderByLastname(id);
        return students.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(students);
    }

    @GetMapping
    public ResponseEntity<Iterable<Student>> getAllStudents(@RequestParam(value = "classId") Long classId,
                                                        @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                                        @RequestParam(value = "pageSize", defaultValue = "0") int pageSize) {

        Pageable page = PageRequest.of(pageNo, pageSize, Sort.by("lastname").descending());
        return ResponseEntity.of(Optional.of(studentRepository.findAllByClassname_ClassId(classId, page)));
    }
}
