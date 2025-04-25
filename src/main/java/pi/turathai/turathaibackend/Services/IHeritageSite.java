package pi.turathai.turathaibackend.Services;

import pi.turathai.turathaibackend.DTO.HeritageSiteDTO;
import pi.turathai.turathaibackend.Entites.HeritageSite;

import java.util.List;

public interface IHeritageSite {

    HeritageSiteDTO addFromDTO(HeritageSiteDTO dto);
    HeritageSiteDTO updateFromDTO(HeritageSiteDTO dto);
    void remove(long id);
    HeritageSiteDTO getByID(long id);
    List<HeritageSiteDTO> getAll();


}
