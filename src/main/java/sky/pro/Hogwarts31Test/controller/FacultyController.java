package sky.pro.Hogwarts31Test.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import sky.pro.Hogwarts31Test.model.Faculty;
import sky.pro.Hogwarts31Test.service.FacultyService;


import java.util.Collection;
import java.util.Comparator;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    @GetMapping
    public String testApi() {
        return "WebApp is working";
    }

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping("/add")
    public long add(@RequestBody Faculty faculty) {
        return facultyService.add(faculty);
    }

    @PutMapping("/{id}/update")
    public Faculty update(@PathVariable("id") Long id,
                          @RequestBody Faculty faculty) {
        return facultyService.update(id, faculty);
    }

    @DeleteMapping("/{id}/remove")
    public void deleteById(@PathVariable("id") Long id) {
        facultyService.deleteById(id);
    }

    @GetMapping("/{id}/get")
    public Faculty findById(@PathVariable("id") Long id) {
        return facultyService.findById(id);
    }

    @GetMapping("/{id}/add")
    public Faculty existById(@PathVariable("id") Long id) {
        return facultyService.findById(id);
    }

    @GetMapping("/get/all")
    public Collection<Faculty> FindAll() {
        return facultyService.findAll();

    }

    @GetMapping("/get/by-color")
    public Collection<Faculty> findByColor(@RequestParam("color") String color) {
        return facultyService.findByColor(color);

    }

    @GetMapping("/get/by-color-or-name")
    public Collection<Faculty> findByColorOrNameIgnoreCase(@RequestParam(value = "color",
            required = false) String color,
                                                           @RequestParam(value = "name",
                                                                   required = false) String name) {
        return facultyService.findByColorOrName(color, name);

    }

    @GetMapping("/longestName")
    @Operation(summary = "возвращает самое длинное название факультета")
    public String getLongestFacultyName() {
        return facultyService.findAll()
                .stream()
                .max(Comparator.comparingInt(faculty -> faculty.getName().length()))
                .map(Faculty::getName)
                .orElse("");
    }
}
