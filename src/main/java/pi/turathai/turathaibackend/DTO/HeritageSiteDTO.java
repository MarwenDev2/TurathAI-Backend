package pi.turathai.turathaibackend.DTO;

import lombok.Data;
import pi.turathai.turathaibackend.Entites.HeritageSite;

import java.util.List;
import java.util.Set;

@Data
public class HeritageSiteDTO {
    private Long id;
    private String name ;
    private String location ;
    private String description ;
    private String historicalSignificance ;
    private int popularityScore ;
    private Long categoryId; // change from categoryName to categoryId
    private Set<Long> imageIds;

    private HeritageSite site;
    private double averageRating;

    public HeritageSiteDTO(){}

    public HeritageSiteDTO(HeritageSite site , double averageRating)
    {
        this.site = site ;
        this.averageRating = averageRating ;
    }

}
