package pi.turathai.turathaibackend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Entites.Image;
import pi.turathai.turathaibackend.Repositories.ImageRepo;

import java.util.List;

@RestController
@RequestMapping("/images")
@CrossOrigin(origins = "http://localhost:4200")
public class ImageController {

    @Autowired
    ImageRepo imageRepo ;

    @PostMapping("/upload")
    public Image uploadImage(@RequestBody Image image) {
        return imageRepo.save(image);
    }

    @GetMapping("/all")
    public List<Image> getAllImages() {
        return imageRepo.findAll();
    }
}
