package pi.turathai.turathaibackend.DTO;

public class UserPreferencesDTO {
    private String preferenceCategories;
    private String travelStyles;
    private String budgetRange;
    private String languagePreferences;
    private Long userId;

    public String getPreferenceCategories() {
        return preferenceCategories;
    }

    public void setPreferenceCategories(String preferenceCategories) {
        this.preferenceCategories = preferenceCategories;
    }

    public String getTravelStyles() {
        return travelStyles;
    }

    public void setTravelStyles(String travelStyles) {
        this.travelStyles = travelStyles;
    }

    public String getBudgetRange() {
        return budgetRange;
    }

    public void setBudgetRange(String budgetRange) {
        this.budgetRange = budgetRange;
    }

    public String getLanguagePreferences() {
        return languagePreferences;
    }

    public void setLanguagePreferences(String languagePreferences) {
        this.languagePreferences = languagePreferences;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}