package sky.pro.Hogwarts31Test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.Hogwarts31Test.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findByAgeBetween(int min, int max);
    boolean existsById(Long id);

}
