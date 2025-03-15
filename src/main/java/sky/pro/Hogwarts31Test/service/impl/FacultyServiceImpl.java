package sky.pro.Hogwarts31Test.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sky.pro.Hogwarts31Test.model.Faculty;
import sky.pro.Hogwarts31Test.model.Student;
import sky.pro.Hogwarts31Test.model.exception.FacultyNotFoundException;
import sky.pro.Hogwarts31Test.repository.FacultyRepository;
import sky.pro.Hogwarts31Test.service.FacultyService;

import java.util.Collection;
import java.util.List;

@Transactional
@Service
public  class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public long add(Faculty faculty) {
        System.out.println(faculty);
        if (faculty.getId() != null && facultyRepository.existsById(faculty.getId())) {
            throw new RuntimeException("Факультет уже существует");
        }

        return facultyRepository.save(faculty).getId();

    }

    @Override
    public Faculty update(Long id, Faculty faculty) {
        checkFacultyExist(id);
        faculty.setId(id);
        return facultyRepository.save(faculty);
    }


    @Override
    public void deleteById(Long id) {
        checkFacultyExist(id);
        facultyRepository.deleteById(id);
    }

    @Override
    public Faculty findById(Long id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException(id));
    }

    @Override
    public Collection<Faculty> findAll() {
        return  (facultyRepository.findAll());
    }

    @Override
    public Collection<Faculty> findByColor(String color) {
        return facultyRepository.findByColor(color);
    }

    @Override
    public Collection<Faculty> findByColorOrName(String color, String name) {
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name);

    }

    @Override
    public Collection<Student> findByAgeBetween(Long id) {
        return List.of();
    }

    private void checkFacultyExist(Long id) {
        if (!facultyRepository.existsById(id)) {
            throw new FacultyNotFoundException(id);
        }
    }
}

