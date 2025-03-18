package sky.pro.Hogwarts31Test.service.impl;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sky.pro.Hogwarts31Test.model.Faculty;
import sky.pro.Hogwarts31Test.service.FacultyService;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static sky.pro.Hogwarts31Test.service.TestConstantsFaculty.TEST_FACULTY;
import static sky.pro.Hogwarts31Test.service.TestConstantsFaculty.TEST_FACULTY_2;

@Transactional
@SpringBootTest

public class FacultyServiceImplTest {
    @Autowired
    private FacultyService facultyService;

    @Test
    void add() {
        //test
        long expected = facultyService.add(TEST_FACULTY);

        //check
        assertThat(expected).isOne();
        Faculty actual = facultyService.findById(expected);
        assertThat(actual).isEqualTo(TEST_FACULTY);
    }

    @Test
    void update() {
        long targetId = TEST_FACULTY.getId();
        facultyService.add(TEST_FACULTY);
        //test
        Faculty oldFaculty = facultyService.update(targetId, TEST_FACULTY_2);

        //check
        assertThat(oldFaculty).isEqualTo(TEST_FACULTY);
        Faculty actual = facultyService.findById(targetId);
        assertThat(actual).isEqualTo( TEST_FACULTY_2);
    }

    @Test
    void deleteById() {
        long targetId = TEST_FACULTY.getId();
        facultyService.add(TEST_FACULTY);

        //test
        facultyService.deleteById(targetId);

        //check
        //assertThat(deletedFaculty).isEqualTo(TEST_FACULTY);
        Faculty actual = facultyService.findById(targetId);
        assertThat(actual).isNull();
    }

    @Test
    void findById() {
        long targetId = TEST_FACULTY.getId();
        facultyService.add(TEST_FACULTY);
        //test
        Faculty actual = facultyService.findById(targetId);
        //check
        assertThat(actual).isEqualTo(TEST_FACULTY);

    }

    @Test
    void findAll() {
        facultyService.add(TEST_FACULTY);
        facultyService.add(TEST_FACULTY_2);
        List<Faculty> expected = List.of(TEST_FACULTY, TEST_FACULTY_2);

        //test
        Collection<Faculty> actual = facultyService.findAll();

        //check
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);

    }

    @Test
    void findByColor() {
        facultyService.add(TEST_FACULTY);

        //test
        Collection<Faculty> actual = facultyService.findByColor(TEST_FACULTY.getColor());

        //check
        assertThat(actual).containsExactly(TEST_FACULTY);
    }

}
