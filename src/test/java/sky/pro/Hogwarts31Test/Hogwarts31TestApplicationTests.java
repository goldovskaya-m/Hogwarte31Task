package sky.pro.Hogwarts31Test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import sky.pro.Hogwarts31Test.controller.AvatarController;
import sky.pro.Hogwarts31Test.controller.FacultyController;
import sky.pro.Hogwarts31Test.controller.StudentController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Hogwarts31TestApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private AvatarController avatarController;

	@Autowired
	private StudentController studentController;

	@Autowired
	private FacultyController facultyController;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	@DisplayName("проверка на загрузку контекста: бины, контролеры, сервесы, репозитории, строки и прочее")
	public void contextLoads() throws Exception {
		Assertions.assertThat(avatarController).isNotNull();
		Assertions.assertThat(studentController).isNotNull();
		Assertions.assertThat(facultyController).isNotNull();
	}
}
