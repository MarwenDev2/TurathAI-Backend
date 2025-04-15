package pi.turathai.turathaibackend.Services;

import pi.turathai.turathaibackend.Entites.Stop;

import java.util.List;

public interface IStopService {
    Stop add(Stop stop);
    Stop update(Stop stop);
    void remove(long id);
    Stop getById(long id);
    List<Stop> getAll();
}
