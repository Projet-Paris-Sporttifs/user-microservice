package psp.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
@NoArgsConstructor
@AllArgsConstructor
public class UserNotFoundException extends RuntimeException {

    private String fieldName;

    private String fieldValue;

}
