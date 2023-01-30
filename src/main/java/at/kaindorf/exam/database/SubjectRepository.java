package at.kaindorf.exam.database;

import at.kaindorf.exam.pojos.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}