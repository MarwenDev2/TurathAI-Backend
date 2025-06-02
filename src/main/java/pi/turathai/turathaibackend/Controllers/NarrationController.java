package pi.turathai.turathaibackend.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pi.turathai.turathaibackend.Entites.Event;
import pi.turathai.turathaibackend.Entites.User;
import pi.turathai.turathaibackend.Services.IEventsService;
import pi.turathai.turathaibackend.Services.IUserService;
import java.util.Map;

@RestController
@RequestMapping("/api/narration")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NarrationController {

    private final IUserService userService;
    private final IEventsService eventService;
    private final RestTemplate restTemplate;

    private static final Map<String, String> LANGUAGE_MAP = Map.of(
            "english", "en",
            "french", "fr",
            "german", "de",
            "arabic", "ar",
            "spanish", "es"
    );

    @GetMapping("/event/{eventId}/audio")
    public ResponseEntity<byte[]> getEventAudio(
            @PathVariable Long eventId,
            @RequestHeader("X-User-Email") String userEmail) {

        try {
            User user = userService.findUserByEmail(userEmail);
            if (user == null) {
                return ResponseEntity.badRequest().body("User not found".getBytes());
            }

            Event event = eventService.getEventById(eventId);
            if (event == null) {
                return ResponseEntity.badRequest().body("Event not found".getBytes());
            }

            String userSpokenLanguage = user.getSpokenLanguage().toLowerCase();
            String targetLanguageCode = LANGUAGE_MAP.getOrDefault(userSpokenLanguage, "en");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 1. Translate event description
            String translateUrl = "http://localhost:8001/translate";
            String translationRequestJson = String.format(
                    "{\"text\":\"%s\",\"target_language\":\"%s\"}",
                    event.getDescription().replace("\"", "\\\""),
                    targetLanguageCode // <- sending the language code to translation
            );
            HttpEntity<String> translationEntity = new HttpEntity<>(translationRequestJson, headers);
            ResponseEntity<String> translationResponse = restTemplate.postForEntity(
                    translateUrl, translationEntity, String.class);

            if (!translationResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.internalServerError()
                        .body(("Translation service error").getBytes());
            }

            String translatedText = new org.json.JSONObject(translationResponse.getBody())
                    .getString("translated_text");

            // 2. Generate voice
            String ttsUrl = "http://localhost:8001/generate-voice";
            String ttsRequestJson = String.format(
                    "{\"text\":\"%s\",\"language\":\"%s\"}",
                    translatedText.replace("\"", "\\\""),
                    userSpokenLanguage // <- sending the LANGUAGE NAME not code to voice generation
            );
            HttpEntity<String> ttsEntity = new HttpEntity<>(ttsRequestJson, headers);
            ResponseEntity<byte[]> ttsResponse = restTemplate.postForEntity(
                    ttsUrl, ttsEntity, byte[].class);

            if (!ttsResponse.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.internalServerError()
                        .body(("Voice generation service error").getBytes());
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf("audio/mpeg"))
                    .body(ttsResponse.getBody());

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(("Error: " + e.getMessage()).getBytes());
        }
    }
}
