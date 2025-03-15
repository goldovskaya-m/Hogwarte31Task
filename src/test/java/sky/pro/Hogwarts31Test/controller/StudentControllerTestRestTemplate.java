package sky.pro.Hogwarts31Test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import sky.pro.Hogwarts31Test.model.Faculty;
import sky.pro.Hogwarts31Test.model.Student;
import sky.pro.Hogwarts31Test.service.impl.StudentServiceImpl;

import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTestRestTemplate {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private StudentController studentController;
    @MockBean
    private StudentServiceImpl studentService;
    private Student student;
    private Faculty faculty;
    //private final Faker faker = new Faker();
    // @Test
    // @DisplayName("корректно добавляет студента в базу данных")
    //void testApi() {
    //test
    //testRestTemplate.postForEntity("http://localhost:" + port + "/student/add",)

    //check
    // }
    @BeforeEach
    public void setUp() {
        long studentId = 1L;
        student = new Student();
        student.setId(studentId);
        student.setName("Oleg");
        student.setAge(18);
        //student.setFaculty(faculty);

        long facultyId = 1L;
        faculty = new Faculty();
        faculty.setId(facultyId);
        faculty.setName("Java");
        faculty.setColor("Black");
    }
    @Test
    @DisplayName("Тест на добавление студента")
    public void addStudentTest() throws Exception {
        when(studentService.add(any(Student.class))).thenReturn(student.getId());

        ObjectMapper objectMapper = new ObjectMapper();
        String studentJson = objectMapper.writeValueAsString(student);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(studentJson, headers);

        ResponseEntity<Student> response = testRestTemplate
                .exchange("http://localhost:" + port + "/student/add"
                , HttpMethod.POST,
                        request,
                        Student.class);
        //assertThat(response.getStatusCode().value()).isEqualTo(200);
        //assertThat(response.getBody()).isNotNull();
        assertThat()

    }


//    @Test
//    void add() {
//        Student student = new Student(faker.harryPotter().character(), nextInt());
//        ResponseEntity<Student> response = testRestTemplate.postForEntity(configureURL("/student/add"),
//                student,
//                Student.class);
//
//        //check
//        assertThat(response).isNotNull();
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        //ТУТ ЛОМАЕТСЯ!!!!!!!
//        assertThat(response.getBody()).isNotNull();
//        Student body = response.getBody();
//        //assertThat(body).isEqualToIgnoringGivenFields(student,"id");
//        //System.out.println(response);
//        assertThat(body).usingRecursiveComparison()
//                .ignoringFields("id")
//                .isEqualTo(student);
//    }
//
//    private String configureURL(String path) {
//        return "http://localhost:%d%s".formatted(port, path);
//    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findByAge() {
    }

    @Test
    void findByAgeBetween() {
    }
}
