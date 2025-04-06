package sky.pro.Hogwarts31Test.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.*;
import sky.pro.Hogwarts31Test.model.Student;
import sky.pro.Hogwarts31Test.service.StudentService;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@RestController
@RequestMapping("/student")

public class StudentController {
    @GetMapping
    public String testApi() {
        return "WebApp is working";
    }

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/add")
    public Long add(@RequestBody Student student) {
        return studentService.add(student);
    }

    @PutMapping("/{id}/update")
    public Student update(@PathVariable("id") Long id,
                          @RequestBody Student student) {
        return studentService.update(id, student);
    }

    @DeleteMapping("/{id}/remove")
    public Student deleteById(@PathVariable("id") Long id) {
        return studentService.deleteById(id);
    }

    @GetMapping("/{id}/get")
    public Student findById(@PathVariable("id") Long id) {
        return studentService.findById(id);
    }

    @GetMapping("/get/all")
    public Collection<Student> FindAll() {
        return studentService.FindAll();
    }

    @GetMapping("/get/by-age")
    public Collection<Student> FindByAge(@RequestParam("age") int age) {
        return studentService.FindByAge(age);
    }

    @GetMapping("/get/by-age-between")
    public Collection<Student> findByAgeBetween(@RequestParam("min") int min,
                                                @RequestParam("max") int max) {
        return studentService.findByAgeBetween(min, max);

    }

    @GetMapping("/count")
    public ResponseEntity<Long> countStudents() {
        return ResponseEntity.ok(studentService.countAllStudents());
    }

    @GetMapping("/average-age")
    public ResponseEntity<Double> averageAge() {
        return ResponseEntity.ok(studentService.getAverageAgeStudent());
    }

    @GetMapping("/last-five")
    @Operation(summary = "Получение последних пяти студентов по идентификатор")
    public ResponseEntity<Page<Student>> fiveLastStudents() {
        return ResponseEntity.ok(studentService.fiveLastStudents());
    }

    @GetMapping("/namesStartingWithA")
    @Operation(summary = "список студентов в формате JSON, где имя начинается на А по алфавиту")
    public List<Student> getStudentNamesStartingWithA() {
        return studentService.FindAll()
                .stream()
                .filter(student -> student.getName().toLowerCase().startsWith("а"))
                .peek(student -> student.setName(student.getName().toUpperCase()))
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());
    }

    @GetMapping("/averageAge")
    @Operation(summary = "средний возраст всех студентов")
    public double getAverageStudentAge() {
        return studentService.FindAll()
                .stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }

    @GetMapping("/sumParallel")
    public long calculateSumParallel() {
        return LongStream.rangeClosed(1, 1_000_000)
                .parallel()
                .sum();
    }

    @GetMapping("/print-synchronized")
    public void printSynchronized() {
        studentService.printSynchronized();
    }
}