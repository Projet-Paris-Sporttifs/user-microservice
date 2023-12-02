package psp.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import psp.user.payload.response.MessageResponse;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public ResponseEntity<MessageResponse> testService() {
        return ResponseEntity.ok(new MessageResponse("User microservice works"));
    }
}
