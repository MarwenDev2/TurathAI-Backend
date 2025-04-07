package pi.turathai.turathaibackend.Interfaces;

import pi.turathai.turathaibackend.Entites.HeritageSite;

import java.util.List;

public interface IHeritageSite {

        HeritageSite add(HeritageSite heritageSite);
        HeritageSite update(HeritageSite heritageSite);
        void remove(long id);
         HeritageSite getByID(long id);
         List<HeritageSite> getAll();


}
