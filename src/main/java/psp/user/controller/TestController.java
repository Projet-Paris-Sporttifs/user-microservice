package psp.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import psp.user.payload.response.MessageResponse;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RestTemplate rt;

    @GetMapping
    public ResponseEntity<MessageResponse> testService() {
        return ResponseEntity.ok(new MessageResponse("User microservice works"));
    }

    @GetMapping("sports-data")
    public ResponseEntity<MessageResponse> testSportsDataService() {
        final MessageResponse response =
                rt.exchange("http://sports-data-service/v1/sports-data/test", HttpMethod.GET, null, MessageResponse.class).getBody();

        return ResponseEntity.ok(response);
    }
}
