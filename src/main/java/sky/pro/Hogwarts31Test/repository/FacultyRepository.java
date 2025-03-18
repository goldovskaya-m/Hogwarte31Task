package sky.pro.Hogwarts31Test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.Hogwarts31Test.model.Faculty;

import java.util.Collection;
import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    List<Faculty> findByColor(String color);

    Collection<Faculty> findByColorIgnoreCaseOrNameIgnoreCase(String color, String name);

}
