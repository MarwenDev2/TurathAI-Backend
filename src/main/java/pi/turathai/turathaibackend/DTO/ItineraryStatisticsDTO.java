// Create this file: src/main/java/pi/turathai/turathaibackend/DTOs/ItineraryStatisticsDTO.java
package pi.turathai.turathaibackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItineraryStatisticsDTO {
    private long totalCount;
    private long lowBudgetCount;
    private double totalBudget;
    private double averageBudget;
    private double minBudget;
    private double maxBudget;
}