package pi.turathai.turathaibackend.Controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = "*")
public class ImageUploadController {

    private final String UPLOAD_DIR = "src/main/resources/static/assets/images/users/";

    @PostMapping()
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("image") MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("error", "File is empty");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            // Create a unique filename
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            // Save the file
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(path.getParent());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            response.put("fileName", fileName);
            response.put("url", "/assets/images/users/" + fileName);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.put("error", "Upload failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}