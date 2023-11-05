package psp.user.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MessageResponse<T> {

    private String message;

    private T data;
}
