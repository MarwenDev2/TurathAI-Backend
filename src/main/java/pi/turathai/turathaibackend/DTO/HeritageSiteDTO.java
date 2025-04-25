package pi.turathai.turathaibackend.DTO;

import lombok.Data;

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

}
