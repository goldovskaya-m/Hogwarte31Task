package sky.pro.Hogwarts31Test.controller;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import sky.pro.Hogwarts31Test.model.Student;

import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private final Faker faker = new Faker();

    @Test
    @DisplayName("корректно добавляет студента в базу данных")
    void testApi() {
        //test
        //testRestTemplate.postForEntity("http://localhost:" + port + "/student/add",)

        //check
    }

    @Test
    void add() {
        Student student = new Student(faker.harryPotter().character(), nextInt());
        //test

        //ResponseEntity<Student> response = testRestTemplate.postForEntity("http://localhost:" + port + "/student/add",
        //student,
        // Student.class);
        ResponseEntity<Student> response = testRestTemplate.postForEntity(configureURL("/student/add"),
                student,
                Student.class);


        //check
        assertThat(response).isNotNull();
        //assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //ТУТ ЛОМАЕТСЯ!!!!!!!
        assertThat(response.getBody()).isNotNull();
        Student body = response.getBody();
        //assertThat(body).isEqualToIgnoringGivenFields(student,"id");
        //System.out.println(response);
        assertThat(body).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(student);
    }

    private String configureURL(String path) {
        return "http://localhost:%d%s".formatted(port, path);
    }

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
