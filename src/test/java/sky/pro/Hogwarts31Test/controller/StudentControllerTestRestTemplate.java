package sky.pro.Hogwarts31Test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import sky.pro.Hogwarts31Test.model.Student;
import sky.pro.Hogwarts31Test.service.StudentService;

import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTestRestTemplate {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

   //private Student student1;
    //private Student student2;


    @BeforeEach
    void setUp() {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("John Doe");
        student1.setAge(20);

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Jane Smith");
        student2.setAge(22);
    }

    @Test
    @DisplayName("API тест должен вернуть 'WebApp is working'")
    void testApi_shouldReturnWebAppIsWorking() throws Exception {
        MvcResult result = mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo("WebApp is working");
    }

    @Test
    @DisplayName("Добавление студента должно вернуть ID добавленного студента")
    void add_shouldAddStudentAndReturnId() throws Exception {
        Student student1 = new Student();
        when(studentService.add(any(Student.class))).thenReturn(1L);

        MvcResult result = mockMvc.perform(post("/student/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student1)))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo("1");
    }

    @Test
    @DisplayName("Обновление студента должно вернуть обновленного студента")
    void update_shouldUpdateStudentAndReturnUpdatedStudent() throws Exception {
        Student student1 = new Student();
        when(studentService.update(1L, student1)).thenReturn(student1);

        MvcResult result = mockMvc.perform(put("/student/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student1)))
                .andExpect(status().isOk())
                .andReturn();

        Student actualStudent = objectMapper.readValue(result.getResponse().getContentAsString(), Student.class);
        assertThat(actualStudent).isEqualTo(student1);
    }

    @Test
    @DisplayName("Удаление студента по ID должно вернуть удаленного студента")
    void deleteById_shouldDeleteStudentAndReturnDeletedStudent() throws Exception {
        Student student1 = new Student();
        when(studentService.deleteById(1L)).thenReturn(student1);

        MvcResult result = mockMvc.perform(delete("/student/1/remove"))
                .andExpect(status().isOk())
                .andReturn();

        Student actualStudent = objectMapper.readValue(result.getResponse().getContentAsString(), Student.class);
        assertThat(actualStudent).isEqualTo(student1);
    }

    @Test
    @DisplayName("Поиск студента по ID должен вернуть студента")
    void findById_shouldReturnStudentById() throws Exception {
        Student student1 = new Student();
        when(studentService.findById(1L)).thenReturn(student1);

        MvcResult result = mockMvc.perform(get("/student/1/get"))
                .andExpect(status().isOk())
                .andReturn();

        Student actualStudent = objectMapper.readValue(result.getResponse().getContentAsString(), Student.class);
        assertThat(actualStudent).isEqualTo(student1);
    }

    @Test
    @DisplayName("Получение всех студентов должно вернуть список студентов")
    void FindAll_shouldReturnAllStudents() throws Exception {
        Student student1 = new Student();
        Student student2 = new Student();
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        when(studentService.FindAll()).thenReturn(students);

        MvcResult result = mockMvc.perform(get("/student/get/all"))
                .andExpect(status().isOk())
                .andReturn();

        List<Student> actualStudents = objectMapper.readValue(result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Student.class));
        assertThat(actualStudents).containsExactly(student1, student2);
    }

    @Test
    @DisplayName("Поиск студентов по возрасту должен вернуть список студентов")
    void FindByAge_shouldReturnStudentsByAge() throws Exception {
        Student student1 = new Student();
        List<Student> students = new ArrayList<>();
        students.add(student1);
        when(studentService.FindByAge(20)).thenReturn(students);

        MvcResult result = mockMvc.perform(get("/student/get/by-age?age=20"))
                .andExpect(status().isOk())
                .andReturn();

        List<Student> actualStudents = objectMapper.readValue(result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Student.class));
        assertThat(actualStudents).containsExactly(student1);
    }

    @Test
    @DisplayName("Поиск студентов по возрасту в диапазоне должен вернуть список студентов")
    void findByAgeBetween_shouldReturnStudentsByAgeBetween() throws Exception {
        Student student1 = new Student();
        Student student2 = new Student();
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        when(studentService.findByAgeBetween(20, 22)).thenReturn(students);

        MvcResult result = mockMvc.perform(get("/student/get/by-age-between?min=20&max=22"))
                .andExpect(status().isOk())
                .andReturn();

        List<Student> actualStudents = objectMapper.readValue(result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Student.class));
        assertThat(actualStudents).containsExactly(student1, student2);
    }
}

