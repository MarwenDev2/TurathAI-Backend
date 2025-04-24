package pi.turathai.turathaibackend.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pi.turathai.turathaibackend.DTO.HeritageSiteDTO;
import pi.turathai.turathaibackend.Entites.Category;
import pi.turathai.turathaibackend.Entites.HeritageSite;
import pi.turathai.turathaibackend.Entites.Image;
import pi.turathai.turathaibackend.Repositories.CategoryRepo;
import pi.turathai.turathaibackend.Repositories.ImageRepo;
import pi.turathai.turathaibackend.Services.IHeritageSite;
import pi.turathai.turathaibackend.Repositories.HeritageSiteRepo;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HeritageSiteService implements IHeritageSite {

    private final HeritageSiteRepo heritageSiteRepo;
    private final CategoryRepo categoryRepo;

     @Autowired
     ImageRepo imageRepo ;

    @Autowired
    public HeritageSiteService(HeritageSiteRepo heritageSiteRepo, CategoryRepo categoryRepo , ImageRepo imageRepo) {
        this.heritageSiteRepo = heritageSiteRepo;
        this.categoryRepo = categoryRepo;
        this.imageRepo = imageRepo ;
    }




    @Override
    public HeritageSiteDTO addFromDTO(HeritageSiteDTO dto) {
        HeritageSite site = new HeritageSite();
        site.setName(dto.getName());
        site.setLocation(dto.getLocation());
        site.setDescription(dto.getDescription());
        site.setHistoricalSignificance(dto.getHistoricalSignificance());
        site.setPopularityScore(dto.getPopularityScore());

        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + dto.getCategoryId()));
        site.setCategory(category);

        if (dto.getImageIds() != null) {
            Set<Image> images = dto.getImageIds().stream()
                    .map(id -> imageRepo.findById(id)
                            .orElseThrow(() -> new RuntimeException("Image not found with ID: " + id)))
                    .collect(Collectors.toSet());
            site.setImages(images);
        }

        HeritageSite saved = heritageSiteRepo.save(site);
        return convertToDTO(saved);
    }

    @Override
    public HeritageSiteDTO updateFromDTO(HeritageSiteDTO dto) {
        HeritageSite site = heritageSiteRepo.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Heritage Site not found with ID: " + dto.getId()));

        site.setName(dto.getName());
        site.setLocation(dto.getLocation());
        site.setDescription(dto.getDescription());
        site.setHistoricalSignificance(dto.getHistoricalSignificance());
        site.setPopularityScore(dto.getPopularityScore());

        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + dto.getCategoryId()));
        site.setCategory(category);

        if (dto.getImageIds() != null) {
            Set<Image> images = dto.getImageIds().stream()
                    .map(id -> imageRepo.findById(id)
                            .orElseThrow(() -> new RuntimeException("Image not found with ID: " + id)))
                    .collect(Collectors.toSet());
            site.setImages(images);
        } else {
            site.setImages(Collections.emptySet());
        }

        return convertToDTO(heritageSiteRepo.save(site));
    }

    @Override
    public void remove(long id) {
        heritageSiteRepo.deleteById(id);
    }

    private HeritageSiteDTO convertToDTO(HeritageSite site) {
        HeritageSiteDTO dto = new HeritageSiteDTO();
        dto.setId(site.getId());
        dto.setName(site.getName());
        dto.setLocation(site.getLocation());
        dto.setDescription(site.getDescription());
        dto.setHistoricalSignificance(site.getHistoricalSignificance());
        dto.setPopularityScore(site.getPopularityScore());
        dto.setCategoryId(site.getCategory().getId());

        if (site.getImages() != null) {
            Set<Long> imageIds = site.getImages().stream()
                    .map(Image::getId)
                    .collect(Collectors.toSet());
            dto.setImageIds(imageIds);
        }
        return dto;
    }


    @Override
    public HeritageSiteDTO getByID(long id) {
        return heritageSiteRepo.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public HeritageSite getSite(Long id)
    {
        return heritageSiteRepo.findById(id).orElse(null);
    }

    @Override
    public List<HeritageSiteDTO> getAll() {
        return heritageSiteRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public long countSites() {
        return heritageSiteRepo.count();
    }


}
