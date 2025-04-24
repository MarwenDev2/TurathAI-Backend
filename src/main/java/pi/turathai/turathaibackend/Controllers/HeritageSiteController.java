package pi.turathai.turathaibackend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.DTO.HeritageSiteDTO;
import pi.turathai.turathaibackend.Entites.HeritageSite;
import pi.turathai.turathaibackend.Repositories.HeritageSiteRepo;
import pi.turathai.turathaibackend.Services.HeritageSiteService;

import java.util.List;


@RestController
@RequestMapping("/Sites")
@CrossOrigin(origins = "*")
public class HeritageSiteController {

    @Autowired
    private HeritageSiteService heritageSiteService;

    @PostMapping("/addSite")
    public HeritageSiteDTO addSite(@RequestBody HeritageSiteDTO dto) {
        return heritageSiteService.addFromDTO(dto);
    }

    @PutMapping("/updateSite")
    public HeritageSiteDTO updateSite(@RequestBody HeritageSiteDTO dto) {
        return heritageSiteService.updateFromDTO(dto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSite(@PathVariable long id) {
        heritageSiteService.remove(id);
    }

    @GetMapping("/get/{id}")
    public HeritageSiteDTO getSiteById(@PathVariable long id) {
        return heritageSiteService.getByID(id);
    }

    @GetMapping("/gett/{id}")
    public HeritageSite gettSite(@PathVariable long id)
    {
        return heritageSiteService.getSite(id);
    }

    @GetMapping("/all")
    public List<HeritageSiteDTO> getAllSites() {
        return heritageSiteService.getAll();
    }

    @GetMapping("/count")
    public long getSiteCount() {
        return heritageSiteService.countSites();
    }
}
