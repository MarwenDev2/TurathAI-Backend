package pi.turathai.turathaibackend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pi.turathai.turathaibackend.Entites.Image;
import org.springframework.core.io.Resource;
import pi.turathai.turathaibackend.Repositories.ImageRepo;
import pi.turathai.turathaibackend.Services.FileStorageService;

import java.util.List;

@RestController
@RequestMapping("/images")
@CrossOrigin(origins = "*")
public class ImageController {

    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private FileStorageService storageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Image uploadImage(@RequestParam("file") MultipartFile file) {
        String filename = storageService.storeFile(file);

        Image image = new Image();
        image.setName(file.getOriginalFilename());
        image.setUrl(filename);
        image.setType(file.getContentType());

        return imageRepo.save(image);
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

    @GetMapping("/all")
    public List<Image> getAllImages() {
        return imageRepo.findAll();
    }
}