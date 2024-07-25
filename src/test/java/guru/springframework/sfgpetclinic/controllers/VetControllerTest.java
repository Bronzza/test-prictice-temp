package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.controllers.faux.ModelMapImpl;
import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.SpecialtyService;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.map.SpecialityMapService;
import guru.springframework.sfgpetclinic.services.map.VetMapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VetControllerTest implements ControllerTests{

    private VetController vetController;
    private VetService service;
    private SpecialtyService specialtyService;

    @BeforeEach
    void setUp() {
        specialtyService = new SpecialityMapService();
        service = new VetMapService(specialtyService);
        vetController = new VetController(service);

        Speciality dog = new Speciality("Dog Vet");
        Speciality cat = new Speciality("Cat Vet");

        service.save(new Vet(1L, "John", "Doe", Set.of(dog, cat)));
        service.save(new Vet(2L, "Jack", "Bar", Set.of(cat)));
    }

    @Test
    void listVets() {
        ModelMapImpl modelMap = new ModelMapImpl();
        String result = vetController.listVets(modelMap);

        assertThat("vets/index").isEqualTo(result);
        assertThat(((HashSet)modelMap.getMap().get("vets")).size()).isEqualTo(2);
    }
}