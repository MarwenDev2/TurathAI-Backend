package pi.turathai.turathaibackend.Services;

import pi.turathai.turathaibackend.DTO.BusinessDTO;
import pi.turathai.turathaibackend.Entites.Business;
import java.util.List;

public interface IBusinessService {
    List<Business> getAllBusinesses();
    Business getBusinessById(Long id);
    Business createBusiness(BusinessDTO businessDTO);
    Business updateBusiness(Long id, BusinessDTO businessDTO);
    void deleteBusiness(Long id);

    List<Business> getBusinessesBySiteId(Long siteId);
}