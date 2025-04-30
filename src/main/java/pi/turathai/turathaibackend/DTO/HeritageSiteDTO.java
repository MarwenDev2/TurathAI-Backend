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
    private String expectedPopularity;

    public HeritageSiteDTO(){}

    public HeritageSiteDTO(HeritageSite site , double averageRating)
    {
        this.site = site ;
        this.averageRating = averageRating ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHistoricalSignificance() {
        return historicalSignificance;
    }

    public void setHistoricalSignificance(String historicalSignificance) {
        this.historicalSignificance = historicalSignificance;
    }

    public int getPopularityScore() {
        return popularityScore;
    }

    public void setPopularityScore(int popularityScore) {
        this.popularityScore = popularityScore;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Set<Long> getImageIds() {
        return imageIds;
    }

    public void setImageIds(Set<Long> imageIds) {
        this.imageIds = imageIds;
    }

    public HeritageSite getSite() {
        return site;
    }

    public void setSite(HeritageSite site) {
        this.site = site;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}
