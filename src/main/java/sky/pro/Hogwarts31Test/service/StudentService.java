package sky.pro.Hogwarts31Test.service;

import org.springframework.data.domain.Page;
import sky.pro.Hogwarts31Test.model.Student;

import java.util.Collection;

public interface StudentService {

    Long add(Student student);
    Student update(Long id, Student faculty);
    Student deleteById(Long id);

    Student findById(Long id);

    Collection<Student> FindAll();

    Collection<Student> FindByAge(int age);

    public Collection<Student> findByAgeBetween(int min, int max);

    Long checkStudentExist(Long id);

    long countAllStudents();

    Double getAverageAgeStudent();

    Page<Student> fiveLastStudents();

    void printParallel();

    void printSynchronized();
}
