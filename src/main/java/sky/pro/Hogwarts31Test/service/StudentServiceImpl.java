package sky.pro.Hogwarts31Test.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sky.pro.Hogwarts31Test.model.Student;
import sky.pro.Hogwarts31Test.exception.StudentNotFoundException;
import sky.pro.Hogwarts31Test.repository.StudentRepository;

import java.util.Collection;

@Transactional
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Long add(Student student) {

        if (studentRepository.existsById(student.getId())){
            throw new RuntimeException(("Студент уже существует"));}
        return studentRepository.save(student).getId();
    }

    @Override
    public Student update(Long id, Student student) {
        checkStudentExist(id);
        student.setId(id);
        return studentRepository.save(student);
    }

    @Override
    public Student deleteById(Long id) {
        checkStudentExist(id);
        // return repository.remove(id);
        studentRepository.deleteById(id);
        return null;

    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    public Collection<Student> FindAll() {
        return (studentRepository.findAll());

    }

    @Override
    public Collection<Student> FindByAge(int age) {


        return studentRepository.findAll().stream()
                .filter(student -> student.getAge() == age).toList();
    }

    @Override
    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Long checkStudentExist(Long id) {
        checkStudentExist(id);
        {
            if (!studentRepository.existsById(id)) {
                throw new StudentNotFoundException(id);
            } else {
                return id;
            }

        }

    }

    @Override
    public long countAllStudents() {
        return studentRepository.countAllStudents();
    }

    @Override
    public Double getAverageAgeStudent() {
        return studentRepository.getAverageAgeStudent();
    }

    @Override
    @Transactional
    public Page<Student> fiveLastStudents() {
        Pageable pageable = PageRequest.of(0, 5);
        return studentRepository.findLastFiveStudents(pageable);
    }
}
