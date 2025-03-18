package sky.pro.Hogwarts31Test.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Аватар не найден")
public class AvatarNotFoundException extends RuntimeException {
}
