package sky.pro.Hogwarts31Test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends RuntimeException{
    private final long id;

    public StudentNotFoundException(long id) {
        super("Student not found by id: [%s]".formatted(id));
        this.id = id;
    }

    public long getId() {
        return id;
    }
}

