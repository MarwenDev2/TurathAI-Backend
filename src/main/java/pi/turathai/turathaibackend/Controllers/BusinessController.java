package pi.turathai.turathaibackend.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pi.turathai.turathaibackend.DTO.BusinessDTO;
import pi.turathai.turathaibackend.Entites.Business;
import pi.turathai.turathaibackend.Services.IBusinessService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/businesses")
@RequiredArgsConstructor
public class BusinessController {

    private final IBusinessService businessService;

    @GetMapping
    public List<Business> getAllBusinesses() {
        return businessService.getAllBusinesses();
    }

    @GetMapping("/{id}")
    public Business getBusinessById(@PathVariable Long id) {
        return businessService.getBusinessById(id);
    }

    @PostMapping
    public Business createBusiness(@RequestBody BusinessDTO businessDTO) {
        return businessService.createBusiness(businessDTO);
    }

    @PutMapping("/{id}")
    public Business updateBusiness(@PathVariable Long id, @RequestBody BusinessDTO businessDTO) {
        return businessService.updateBusiness(id, businessDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBusiness(@PathVariable Long id) {
        businessService.deleteBusiness(id);
    }

    @GetMapping("/by-site/{siteId}")
    public List<Business> getBusinessesBySiteId(@PathVariable Long siteId) {
        return businessService.getBusinessesBySiteId(siteId);
    }
}