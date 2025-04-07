package sky.pro.Hogwarts31Test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import sky.pro.Hogwarts31Test.model.Faculty;
import sky.pro.Hogwarts31Test.service.FacultyService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @Autowired
    private ObjectMapper objectMapper;

    //private Faculty faculty1;
    //private Faculty faculty2;

    @BeforeEach
    void setUpp() {
        Faculty faculty1 = new Faculty();
        faculty1.setId(1L);
        faculty1.setName("Gryffindor");
        faculty1.setColor("Red");

        Faculty faculty2 = new Faculty();
        faculty2.setId(2L);
        faculty2.setName("Raven_claw");
        faculty2.setColor("Blue");
    }

    @Test
    @DisplayName("API тест должен вернуть WebApp is working")
    void testApi_shouldReturnWebAppIsWorking() throws Exception {
        MvcResult result = mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo("WebApp is working");
    }

    @Test
    @DisplayName("Поиск факультета по ID должен вернуть факультет")
    void findById_shouldReturnFacultyById() throws Exception {
        Faculty faculty1 = new Faculty();
        when(facultyService.findById(1L)).thenReturn(faculty1);

        MvcResult result = mockMvc.perform(get("/faculty/1/get"))
                .andExpect(status().isOk())
                .andReturn();

        Faculty actualFaculty = objectMapper.readValue(result.getResponse().getContentAsString(), Faculty.class);
        assertThat(actualFaculty).isEqualTo(faculty1);
    }

    @Test
    @DisplayName("Получение всех факультетов должно вернуть список факультетов")
    void FindAll_shouldReturnAllFaculty() throws Exception {
        Faculty faculty1 = new Faculty();
        Faculty faculty2 = new Faculty();
        Collection<Faculty> faculty = new ArrayList<>();
        faculty.add(faculty1);
        faculty.add(faculty2);
        when(facultyService.findAll()).thenReturn(faculty);

        MvcResult result = mockMvc.perform(get("/faculty/get/all"))
                .andExpect(status().isOk())
                .andReturn();

        List<Faculty> actualFaculty = objectMapper.readValue(result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Faculty.class));
        assertThat(actualFaculty).containsExactly(faculty1, faculty2);
    }
}


