package at.kaindorf.exam.api;

import at.kaindorf.exam.data.ExamDTO;
import at.kaindorf.exam.database.ExamRepository;
import at.kaindorf.exam.database.StudentRepository;
import at.kaindorf.exam.database.SubjectRepository;
import at.kaindorf.exam.pojos.Exam;
import at.kaindorf.exam.pojos.Student;
import at.kaindorf.exam.pojos.Subject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Field;
import java.net.URI;
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
@RequestMapping(value = "/exam", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ExamController {

    private final ExamRepository examRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;

    public ExamController(ExamRepository examRepository, SubjectRepository subjectRepository, StudentRepository studentRepository) {
        this.examRepository = examRepository;
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<List<Exam>> getExamsFromStudent(@PathVariable Long id) {
        List<Exam> exams = examRepository.findExamsByStudent_StudentIdOrderByDateOfExam(id);
        return exams.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(exams);
    }

    @PostMapping("/add")
    public ResponseEntity<Exam> addExam(@RequestBody ExamDTO creationExam) {
        Exam exam = getExamFromExamDTO(creationExam);

        if (examRepository.existsById(exam.getExamId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        examRepository.save(exam);

        URI location = ServletUriComponentsBuilder.fromPath("/exam")
                .path("/{id}")
                .buildAndExpand(exam.getExamId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/delete/{examId}")
    public ResponseEntity<Exam> deleteExam(@PathVariable Long examId) {
        Optional<Exam> optExam = examRepository.findById(examId);

        if (optExam.isPresent()) {
            Exam exam = optExam.get();
            examRepository.delete(exam);
            return ResponseEntity.ok(exam);
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping
    public ResponseEntity<Exam> updateExam(@RequestBody ExamDTO creationPatch) {
        Optional<Exam> optExam = examRepository.findById(creationPatch.getExamId());
        Exam patch = getExamFromExamDTO(creationPatch);

        if (optExam.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            Exam exam = optExam.get();
            for (Field field: Exam.class.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = ReflectionUtils.getField(field, patch);
                if (value != null && !value.toString().trim().isEmpty()) {
                    ReflectionUtils.setField(field, exam, value);
                }
            }
            examRepository.save(exam);
            return ResponseEntity.ok(exam);
        } catch (Exception e) {
            log.error("Error while updating exam: ", e);
            return ResponseEntity.badRequest().build();
        }
    }

    private Exam getExamFromExamDTO(ExamDTO examDTO) {
        Exam exam = new Exam();
        exam.setExamId(examDTO.getExamId());
        exam.setDateOfExam(examDTO.getDateOfExam());
        exam.setDuration(examDTO.getDuration());

        Student student = studentRepository.findById(examDTO.getStudentId()).orElse(null);
        Subject subject = subjectRepository.findById(examDTO.getSubjectId()).orElse(null);

        //studentRepository.getReferenceById(examDTO.getStudentId());
        //examRepository.getMaxExamId() + 1;

        exam.setStudent(student);
        exam.setSubject(subject);
        return exam;
    }
}
