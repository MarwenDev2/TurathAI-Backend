package pi.turathai.turathaibackend.Services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.Entites.Business;
import pi.turathai.turathaibackend.Repositories.BusinessRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessServiceImp implements IBusinessService{

    private final BusinessRepository businessRepository;

    @Override
    public List<Business> getAllBusinesses() {
        return businessRepository.findAll();
    }

    @Override
    public Business getBusinessById(Long id) {
        return businessRepository.findById(id).orElseThrow(() -> new RuntimeException("Business not found"));
    }

    @Override
    public Business createBusiness(Business business) {
        return businessRepository.save(business);
    }

    @Override
    public Business updateBusiness(Long id, Business updatedBusiness) {
        Business existing = getBusinessById(id);
        existing.setName(updatedBusiness.getName());
        existing.setType(updatedBusiness.getType());
        existing.setLatitude(updatedBusiness.getLatitude());
        existing.setLongitude(updatedBusiness.getLongitude());
        existing.setContact(updatedBusiness.getContact());
        existing.setHeritageSite(updatedBusiness.getHeritageSite());
        existing.setImages(updatedBusiness.getImages());
        return businessRepository.save(existing);
    }

    @Override
    public void deleteBusiness(Long id) {
        businessRepository.deleteById(id);
    }
}
