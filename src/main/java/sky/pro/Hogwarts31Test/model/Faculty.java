package sky.pro.Hogwarts31Test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "faculty")

public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;

    //@OneToMany(mappedBy = "faculty", fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "faculty", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Student> students;


    public Faculty(String color, String name) {

        this.color = color;
        this.name = name;
    }

    public Faculty(Long id, String color, String name, List<Student> student) {
        this.id = id;
        this.color = color;
        this.name = name;
        this.students = student;
    }


    public Faculty() {
    }

    public List<Student> getStudents() {
        return students;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Faculty faculty) )return false;
        return Objects.equals(id, faculty.id) && Objects.equals(name, faculty.name) &&
                Objects.equals(color, faculty.color);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, name, color);
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
