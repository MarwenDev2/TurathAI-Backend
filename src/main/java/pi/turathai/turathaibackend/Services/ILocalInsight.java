package pi.turathai.turathaibackend.Services;

import pi.turathai.turathaibackend.Entites.LocalInsight;

import java.util.List;
import java.util.Optional;

public interface ILocalInsight {


    List<LocalInsight> getAllLocalInsights();
    Optional<LocalInsight> getLocalInsightById(Long id);
    LocalInsight saveLocalInsight(LocalInsight localInsight);
    void deleteLocalInsight(Long id);
    public void sendConfirmationEmail(LocalInsight insight) ;

}
