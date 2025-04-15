package pi.turathai.turathaibackend.services;

import pi.turathai.turathaibackend.Entites.CrowdHeatMap;

import java.util.List;

public interface ICrowdHeatMapService {
    CrowdHeatMap add(CrowdHeatMap crowdHeatMap);
    CrowdHeatMap update(CrowdHeatMap crowdHeatMap);
    void remove(long id);
    CrowdHeatMap getById(long id);
    List<CrowdHeatMap> getAll();
}