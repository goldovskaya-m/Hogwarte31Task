package sky.pro.Hogwarts31Test.service;

import sky.pro.Hogwarts31Test.model.Faculty;
import sky.pro.Hogwarts31Test.model.Student;

import java.util.Collection;

public interface FacultyService {
    long add(Faculty faculty);

    Faculty update(Long id, Faculty faculty);

    void deleteById(Long id);

    Faculty findById(Long id);

    Collection<Faculty> findAll();

    Collection<Faculty> findByColor(String color);

    Collection<Faculty> findByColorOrName(String color, String name);

    Collection<Student> findByAgeBetween(Long id);

}
