package pi.turathai.turathaibackend.Services;

import pi.turathai.turathaibackend.Entites.Itinery;

import java.util.List;

public interface IItineryService {
    Itinery add(Itinery itinery);
    Itinery update(Itinery itinery);
    void remove(long id);
    Itinery getById(long id);
    List<Itinery> getAll();
}