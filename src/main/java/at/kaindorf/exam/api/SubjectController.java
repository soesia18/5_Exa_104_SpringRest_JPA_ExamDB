package at.kaindorf.exam.api;

import at.kaindorf.exam.database.SubjectRepository;
import at.kaindorf.exam.pojos.Subject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <h3>Created by IntelliJ IDEA.</h3><br>
 * <b>Project:</b> Exa_104_SpringRest_JPA_ExamDB<br>
 * <b>User:</b> Simon Schoeggler<br>
 * <b>Date:</b> 25. Jänner 2023<br>
 * <b>Time:</b> 09:18<br>
 */

@RestController
@Slf4j
@RequestMapping(value = "/subject", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class SubjectController {
    private final SubjectRepository subjectRepository;

    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectById (@PathVariable Long id) {
        return ResponseEntity.of(subjectRepository.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Subject>> getAllSubjects () {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(subjects);
    }
}
