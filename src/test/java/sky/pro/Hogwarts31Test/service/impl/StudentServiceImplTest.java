package sky.pro.Hogwarts31Test.service.impl;

import org.junit.jupiter.api.Test;
import sky.pro.Hogwarts31Test.model.Student;
import sky.pro.Hogwarts31Test.service.TestConstantsStudent;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StudentServiceImplTest {
    private final StudentServiceImpl studentService = new StudentServiceImpl(null) {

        @Override
        public Collection<Student> findByAgeBetween(int min, int max) {
            return super.findByAgeBetween(min, max);
        }

    };


    @Test
    void add() {
        //test
        long expected = studentService.add(TestConstantsStudent.TEST_STUDENT_2);

        //check
        assertThat(expected).isIn();
        Student actual = studentService.findById(expected);
        assertThat(actual).isEqualTo(TestConstantsStudent.TEST_STUDENT);
    }

    @Test
    void update() {
        long targetId = TestConstantsStudent.TEST_STUDENT.getId();
        studentService.add(TestConstantsStudent.TEST_STUDENT);
        //test
        Student oldFaculty = studentService.update(targetId, TestConstantsStudent.TEST_STUDENT_2);

        //check
        assertThat(oldFaculty).isEqualTo(TestConstantsStudent.TEST_STUDENT);
        Student actual = studentService.findById(targetId);
        assertThat(actual).isEqualTo(TestConstantsStudent.TEST_STUDENT_2);
    }

    @Test
    void deleteById() {
        long targetId = TestConstantsStudent.TEST_STUDENT.getId();
        studentService.add(TestConstantsStudent.TEST_STUDENT);

        //test
        studentService.deleteById(targetId);

        //check
        Student actual = studentService.findById(targetId);
        assertThat(actual).isNull();
    }

    @Test
    void findById() {
        long targetId = TestConstantsStudent.TEST_STUDENT.getId();
        studentService.add(TestConstantsStudent.TEST_STUDENT);

        //test
        Student actual = studentService.findById(targetId);
        //check
        assertThat(actual).isEqualTo(TestConstantsStudent.TEST_STUDENT);

    }

    @Test
    void findAll() {
        studentService.add(TestConstantsStudent.TEST_STUDENT);
        studentService.add(TestConstantsStudent.TEST_STUDENT_2);
        List<Student> expected = List.of(TestConstantsStudent.TEST_STUDENT, TestConstantsStudent.TEST_STUDENT_2);

        //test
        Collection<Student> actual = studentService.FindAll();

        //check
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);

    }

    @Test
    void findByAge() {
        studentService.add(TestConstantsStudent.TEST_STUDENT);

        //test
        Collection<Student> actual = studentService.FindAll();

        //check
        assertThat(actual).containsExactly(TestConstantsStudent.TEST_STUDENT);
    }

    @Test
    void findByAgeBetween() {
        //studentService.add(TEST_STUDENT);

        //test
        //Collection<Faculty> actual = studentService.findByAgeBetween(min, max);

        //check
        //assertThat(actual).containsExactly();


    }
}
