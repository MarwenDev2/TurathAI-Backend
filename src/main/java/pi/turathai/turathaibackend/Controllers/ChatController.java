package pi.turathai.turathaibackend.Controllers;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final String CHAT_SERVICE_URL = "http://localhost:5001/chat";

    @PostMapping
    public ResponseEntity<Map> chat(@RequestBody Map<String, String> request) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                CHAT_SERVICE_URL,
                HttpMethod.POST,
                entity,
                Map.class
        );

        return ResponseEntity.ok(response.getBody());
    }
}
