package at.kaindorf.exam.database;

import at.kaindorf.exam.pojos.Classname;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassnameRepository extends JpaRepository<Classname, Long> {
}