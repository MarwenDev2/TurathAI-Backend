package pi.turathai.turathaibackend.Services;
import pi.turathai.turathaibackend.Entites.*;

import java.util.List;
import java.util.Optional;
public interface IBusinessService {
    List<Business> getAllBusinesses();
    Business getBusinessById(Long id);
    Business createBusiness(Business business);
    Business updateBusiness(Long id, Business business);
    void deleteBusiness(Long id);
}
