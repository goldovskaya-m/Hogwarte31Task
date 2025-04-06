package sky.pro.Hogwarts31Test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sky.pro.Hogwarts31Test.model.Student;
import sky.pro.Hogwarts31Test.exception.StudentNotFoundException;
import sky.pro.Hogwarts31Test.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Transactional
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Long add(Student student) {
        logger.info("Вызван метод создания объекта студент");
        if (studentRepository.existsById(student.getId())) {
            logger.error("Студент уже существует");
            throw new RuntimeException("Студент уже существует");
        }
        return studentRepository.save(student).getId();
    }

    @Override
    public Student update(Long id, Student student) {
        logger.info("Вызван метод обновления объекта студента с id {}", id);
        checkStudentExist(id);
        student.setId(id);
        return studentRepository.save(student);
    }

    @Override
    public Student deleteById(Long id) {
        logger.info("Вызван метод удаления объекта студент");
        checkStudentExist(id);
        studentRepository.deleteById(id);
        return null;

    }

    @Override
    public Student findById(Long id) {
        logger.info("Вызван метод поиска объекта студент");
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    public Collection<Student> FindAll() {
        logger.info("Вызван метод поиска всех объектов студент");
        return (studentRepository.findAll());

    }

    @Override
    public Collection<Student> FindByAge(int age) {
        logger.info("Вызван метод поиска объекта студент по возрасту");
        return studentRepository.findAll().stream()
                .filter(student -> student.getAge() == age).toList();
    }

    @Override
    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.info("Вызван метод поиска объектов студент по возрасту в промежутке мин-макс");
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Long checkStudentExist(Long id) {
        logger.info("Вызван метод проверки наличия объекта студент");
        checkStudentExist(id);
        {
            if (!studentRepository.existsById(id)) {
                logger.error("Студент не найден");
                throw new StudentNotFoundException(id);
            } else {
                return id;
            }

        }
    }

    @Override
    public long countAllStudents() {
        logger.info("Вызван метод подсчета объектов студент");
        return studentRepository.countAllStudents();
    }

    @Override
    public Double getAverageAgeStudent() {
        logger.info("Вызван метод подсчета среднего возраста объектов студент");
        return studentRepository.getAverageAgeStudent();
    }

    @Override
    public Page<Student> fiveLastStudents() {
        logger.info("Вызван метод поиска последних пяти объектов студент");
        Pageable pageable = PageRequest.of(0, 5);
        return studentRepository.findLastFiveStudents(pageable);
    }

    @Override
    public void printParallel() {
        List<Student> students = studentRepository.findAll().stream()
                .limit(6)
                .toList();

        System.out.println(students.get(0));
        System.out.println(students.get(1));

        Thread parallell1 = new Thread(() -> {
            System.out.println(students.get(2));
            System.out.println(students.get(3));
        });

        Thread parallell2 = new Thread(() -> {
            System.out.println(students.get(4));
            System.out.println(students.get(5));
        });

        parallell1.start();
        parallell2.start();
    }

    @Override
    public void printSynchronized() {
        List<Student> listSynchronized = studentRepository.findAll().stream()
                .limit(6)
                .toList();

        System.out.println(listSynchronized.get(0));
        System.out.println(listSynchronized.get(1));

        Thread t1 = new Thread(() -> {
            printSynchronizedStudent(listSynchronized, 2, 3);
        });

        Thread t2 = new Thread(() -> {
            printSynchronizedStudent(listSynchronized, 4, 5);
        });

        t1.start();
        t2.start();
    }

    private void printSynchronizedStudent(List<Student> listSynchronized, int a, int b) {
        synchronized (this) {
            System.out.println(listSynchronized.get(a));
            System.out.println(listSynchronized.get(b));
        }
    }
}
