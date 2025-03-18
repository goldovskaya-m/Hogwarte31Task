package sky.pro.Hogwarts31Test.controller;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.*;
import sky.pro.Hogwarts31Test.model.Student;
import sky.pro.Hogwarts31Test.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")

public class StudentController {
    @GetMapping
    public String testApi() {return "WebApp is working";}
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/add")
    public Long add(@RequestBody Student student) {
        return studentService.add( student);
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
}
