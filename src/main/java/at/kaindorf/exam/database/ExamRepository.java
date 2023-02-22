package at.kaindorf.exam.database;

import at.kaindorf.exam.pojos.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findExamsByStudent_StudentIdOrderByDateOfExam (Long studentId);

    @Query("SELECT MAX(e.examId) FROM Exam e")
    Long getMaxExamId();
}