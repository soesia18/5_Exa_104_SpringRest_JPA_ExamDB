package at.kaindorf.exam.database;

import at.kaindorf.exam.pojos.Classname;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassnameRepository extends JpaRepository<Classname, Long> {
    List<Classname> findAllByOrderByClassname();
}