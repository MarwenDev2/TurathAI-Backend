package pi.turathai.turathaibackend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Entites.HeritageSite;
import pi.turathai.turathaibackend.Services.HeritageSiteService;

import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/Sites")
public class HeritageSiteController {


    @Autowired
    HeritageSiteService heritageSiteService ;


    @PostMapping("/addSite")
    public HeritageSite addSite(@RequestBody HeritageSite heritageSite)
    {
        return heritageSiteService.add(heritageSite);
    }

    @PutMapping("/updateSite")
    public HeritageSite updateSite(@RequestBody HeritageSite heritageSite)
    {
        return heritageSiteService.update(heritageSite);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSite(@PathVariable long id)
    {
        heritageSiteService.remove(id);
    }

    @GetMapping("/get/{id}")
    public HeritageSite getSiteById(@PathVariable long id) {
        return heritageSiteService.getByID(id);
    }

    @GetMapping("/all")
    public List<HeritageSite> getAllSites()
    {
        return heritageSiteService.getAll();
    }

}
