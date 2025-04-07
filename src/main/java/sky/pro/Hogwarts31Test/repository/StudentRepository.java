package sky.pro.Hogwarts31Test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sky.pro.Hogwarts31Test.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findByAgeBetween(int min, int max);

    boolean existsById(Long id);

    @Query(value = "SELECT COUNT(*) FROM Student", nativeQuery = true)
    long countAllStudents();

    @Query(value = "SELECT AVG(age) FROM Student", nativeQuery = true)
    Double getAverageAgeStudent();

    @Query(value = "SELECT * FROM Student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> findLastFiveStudents();
}
