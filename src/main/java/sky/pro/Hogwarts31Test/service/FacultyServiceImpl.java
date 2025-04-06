package sky.pro.Hogwarts31Test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sky.pro.Hogwarts31Test.model.Faculty;
import sky.pro.Hogwarts31Test.model.Student;
import sky.pro.Hogwarts31Test.exception.FacultyNotFoundException;
import sky.pro.Hogwarts31Test.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;

@Transactional
@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public long add(Faculty faculty) {
        logger.info("Вызван метод создания факультета");
        logger.error("Факультет уже существует");
        if (faculty.getId() != null && facultyRepository.existsById(faculty.getId())) {
            throw new RuntimeException("Факультет уже существует");
        }
        return facultyRepository.save(faculty).getId();
    }

    @Override
    public Faculty update(Long id, Faculty faculty) {
        logger.info("Вызван метод изменения факультета");
        checkFacultyExist(id);
        faculty.setId(id);
        return facultyRepository.save(faculty);
    }


    @Override
    public void deleteById(Long id) {
        logger.info("Вызван метод удаления факультета");
        checkFacultyExist(id);
        facultyRepository.deleteById(id);
    }

    @Override
    public Faculty findById(Long id) {
        logger.info("Вызван метод поиска факультета");
        return facultyRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException(id));
    }

    @Override
    public Collection<Faculty> findAll() {
        logger.info("Вызван метод поиска всех факультетов");
        return (facultyRepository.findAll());
    }

    @Override
    public Collection<Faculty> findByColor(String color) {
        logger.info("Вызван метод поиска факультета по цвету");
        return facultyRepository.findByColor(color);
    }

    @Override
    public Collection<Faculty> findByColorOrName(String color, String name) {
        logger.info("Вызван метод поиска факультета по цвету или имени");
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name);

    }

    @Override
    public Collection<Student> findByAgeBetween(Long id) {
        logger.info("Вызван метод поиска студентов факультета по возрасту");
        return List.of();
    }

    private void checkFacultyExist(Long id) {
        logger.info("Вызван метод проверки наличия факультета");
        if (!facultyRepository.existsById(id)) {
            logger.error("Факультет не найден");
            throw new FacultyNotFoundException(id);
        }
    }
}

