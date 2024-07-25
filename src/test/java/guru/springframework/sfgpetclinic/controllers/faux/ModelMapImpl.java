package guru.springframework.sfgpetclinic.controllers.faux;

import guru.springframework.sfgpetclinic.fauxspring.Model;

import java.util.HashMap;
import java.util.Map;

public class ModelMapImpl implements Model {

   private Map map =  new HashMap<String, Object>();

    @Override
    public void addAttribute(String key, Object o) {
        map.put(key, o);
    }

    @Override
    public void addAttribute(Object o) {

    }

    public Map getMap() {
        return map;
    }
}
