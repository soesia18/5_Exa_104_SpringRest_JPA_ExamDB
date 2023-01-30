package at.kaindorf.exam.api;

import at.kaindorf.exam.database.ClassnameRepository;
import at.kaindorf.exam.pojos.Classname;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping(value = "/classname", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ClassnameController {

    private final ClassnameRepository classnameRepository;

    public ClassnameController(ClassnameRepository classnameRepository) {
        this.classnameRepository = classnameRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Classname>> getAllClassnames() {
        log.debug("REST request to get all Classnames");
        List<Classname> classnames = classnameRepository.findAll(Sort.by("classname"));
        return classnames.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(classnames);
    }
}
