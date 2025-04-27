package pi.turathai.turathaibackend.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.DTO.BusinessDTO;
import pi.turathai.turathaibackend.Entites.Business;
import pi.turathai.turathaibackend.Entites.HeritageSite;
import pi.turathai.turathaibackend.Entites.Image;
import pi.turathai.turathaibackend.Repositories.BusinessRepository;
import pi.turathai.turathaibackend.Repositories.HeritageSiteRepo;
import pi.turathai.turathaibackend.Repositories.ImageRepo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BusinessServiceImp implements IBusinessService {

    private final BusinessRepository businessRepository;
    private final HeritageSiteRepo heritageSiteRepository;
    private final ImageRepo imageRepository;

    @Override
    public List<Business> getAllBusinesses() {
        return businessRepository.findAll();
    }

    @Override
    public Business getBusinessById(Long id) {
        return businessRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Business not found"));
    }

    @Override
    public Business createBusiness(BusinessDTO businessDTO) {
        Business business = new Business();
        mapBusinessDTOToEntity(businessDTO, business);
        return businessRepository.save(business);
    }

    @Override
    public Business updateBusiness(Long id, BusinessDTO businessDTO) {
        Business existingBusiness = getBusinessById(id);
        mapBusinessDTOToEntity(businessDTO, existingBusiness);
        return businessRepository.save(existingBusiness);
    }

    @Override
    public void deleteBusiness(Long id) {
        businessRepository.deleteById(id);
    }

    private void mapBusinessDTOToEntity(BusinessDTO dto, Business entity) {
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setContact(dto.getContact());

        // Set HeritageSite
        if (dto.getHeritageSiteId() != null) {
            HeritageSite site = heritageSiteRepository.findById(dto.getHeritageSiteId())
                    .orElseThrow(() -> new RuntimeException("Heritage site not found"));
            entity.setHeritageSite(site);
        } else {
            entity.setHeritageSite(null);
        }

        // Set Images
        if (dto.getImageIds() != null && !dto.getImageIds().isEmpty()) {
            Set<Image> images = new HashSet<>(imageRepository.findAllById(dto.getImageIds()));
            entity.setImages(images);
        } else {
            entity.setImages(null);
        }
    }

    @Override
    public List<Business> getBusinessesBySiteId(Long siteId) {
        return businessRepository.findByHeritageSiteId(siteId);
    }
}