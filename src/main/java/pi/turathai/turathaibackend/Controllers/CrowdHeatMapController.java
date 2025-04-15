package pi.turathai.turathaibackend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.Entites.CrowdHeatMap;
import pi.turathai.turathaibackend.Services.ICrowdHeatMapService;

import java.util.List;

@RestController
@RequestMapping("/crowd-heatmaps")
public class CrowdHeatMapController {

    @Autowired
    private ICrowdHeatMapService crowdHeatMapService;

    @PostMapping("/add")
    public CrowdHeatMap addCrowdHeatMap(@RequestBody CrowdHeatMap crowdHeatMap) {
        return crowdHeatMapService.add(crowdHeatMap);
    }

    @PutMapping("/update")
    public CrowdHeatMap updateCrowdHeatMap(@RequestBody CrowdHeatMap crowdHeatMap) {
        return crowdHeatMapService.update(crowdHeatMap);
    }

    @DeleteMapping("/remove/{id}")
    public void removeCrowdHeatMap(@PathVariable long id) {
        crowdHeatMapService.remove(id);
    }

    @GetMapping("/get/{id}")
    public CrowdHeatMap getCrowdHeatMapById(@PathVariable long id) {
        return crowdHeatMapService.getById(id);
    }

    @GetMapping("/all")
    public List<CrowdHeatMap> getAllCrowdHeatMaps() {
        return crowdHeatMapService.getAll();
    }
}