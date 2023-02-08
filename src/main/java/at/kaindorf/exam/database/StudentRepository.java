package at.kaindorf.exam.database;

import at.kaindorf.exam.pojos.Classname;
import at.kaindorf.exam.pojos.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StudentRepository extends PagingAndSortingRepository<Student, Long>, JpaRepository<Student, Long> {
    List<Student> findStudentsByClassname_ClassIdOrderByLastname (Long classnameId);
    Page<Student> findAllByClassname_ClassId(Long classId, Pageable pageable);
}