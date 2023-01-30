package at.kaindorf.exam.database;

import at.kaindorf.exam.pojos.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findExamsByStudent_StudentIdOrderByDateOfExam (Long studentId);
}