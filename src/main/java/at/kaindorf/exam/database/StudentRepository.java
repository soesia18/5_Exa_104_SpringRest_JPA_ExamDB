package at.kaindorf.exam.database;

import at.kaindorf.exam.pojos.Classname;
import at.kaindorf.exam.pojos.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findStudentsByClassname_ClassIdOrderByLastname (Long classnameId);
}