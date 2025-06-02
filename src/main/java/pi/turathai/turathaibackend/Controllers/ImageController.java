package pi.turathai.turathaibackend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pi.turathai.turathaibackend.Entites.Image;
import org.springframework.core.io.Resource;
import pi.turathai.turathaibackend.Repositories.ImageRepo;
import pi.turathai.turathaibackend.Services.FileStorageService;
import pi.turathai.turathaibackend.Services.LocalInsightService;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private FileStorageService storageService;
    @Autowired
    private LocalInsightService localInsightService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Image uploadImage(@RequestParam("file") MultipartFile file) {
        String filename = storageService.storeFile(file);

        Image image = new Image();
        image.setName(file.getOriginalFilename());
        image.setUrl(filename);
        image.setType(file.getContentType());

        return imageRepo.save(image);
    }

    @PostMapping(value = "/video-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadVideo(@RequestParam("file") MultipartFile file) {
        if (!file.getContentType().startsWith("video/")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Only video files are allowed"));
        }

        String filename = storageService.storeFile(file);
        return ResponseEntity.ok(Map.of(
                "url", filename,
                "originalName", file.getOriginalFilename()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable Long id) {
        Image image = imageRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        Resource file = storageService.loadFile(image.getUrl());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("/video/{id}")
    public ResponseEntity<Resource> getVideo(@PathVariable Long id) {
        String video = localInsightService.getLocalInsightById(id).get().getVideoURL();
        Resource file = storageService.loadFile(video);

        return ResponseEntity.ok()
                .contentType(MediaTypeFactory.getMediaType(file).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(file);
    }


    @GetMapping("/all")
    public List<Image> getAllImages() {
        return imageRepo.findAll();
    }
}