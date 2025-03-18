package sky.pro.Hogwarts31Test.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import sky.pro.Hogwarts31Test.model.Avatar;
import sky.pro.Hogwarts31Test.model.Student;
import sky.pro.Hogwarts31Test.repository.AvatarRepository;
import sky.pro.Hogwarts31Test.repository.StudentRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;

import static java.nio.file.Files.delete;
import static java.nio.file.Files.walk;
import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static sky.pro.Hogwarts31Test.service.TestConstantsFaculty.TEST_FACULTY;

@ExtendWith(MockitoExtension.class)
class AvatarServiceImplTest {
    private static final String TEST_PATH = "src/test/image";
    @Mock
    AvatarRepository avatarRepository;
    // AvatarRepository avatarRepository = Mockito.mock(AvatarRepository.class); тоже самое без @мок

    @Mock
    StudentRepository studentRepository;
    // StudentRepository studentRepository = Mockito.mock(StudentRepository.class); тоже самое без @мок

    //@InjectMocks
    // AvatarServiceImpl avatarService; тоже самое без @мок
    AvatarServiceImpl avatarService = new AvatarServiceImpl(
            avatarRepository,
            studentRepository,
            Path.of(TEST_PATH)
    );


    private MultipartFile multipartFile;
    private boolean actualId;

    @AfterEach
    public void setDown() throws IOException {

        walk(Path.of(TEST_PATH))
                .peek(System.out::println)
                .sorted(Comparator.reverseOrder())
                .forEach(x -> {
                    try {
                        delete(x);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }


    @Test
    void uploadAvatar() throws IOException {
        Student student = new Student("Oleg", 20, TEST_FACULTY);
        Avatar avatar = new Avatar();
        avatar.setId(nextLong(1, 1000));

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(avatarRepository.save(any(Avatar.class))).thenReturn(avatar);

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                new byte[0]
        );
        //test
        long actualId = avatarService.uploadAvatar(1, multipartFile);

        //check
        assertThat(actualId).isEqualTo(avatar.getId());
        assertThat(walk(Path.of(TEST_PATH)).hashCode());
    }

    @Test
    void getAvatarFromDb () {
    }

    @Test
    void getAvatarFromLocal () {
    }
}
