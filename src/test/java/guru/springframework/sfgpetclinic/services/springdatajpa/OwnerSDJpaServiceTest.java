package guru.springframework.sfgpetclinic.services.springdatajpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OwnerSDJpaServiceTest {

    private OwnerSDJpaService testedService;

    @BeforeEach
    void setUp() {
        testedService = new OwnerSDJpaService(null, null, null);
    }

    @Test
    @Disabled
    void findByLastName() {
        testedService.findByLastName("Buck");
    }


    @Test
    void findAllByLastNameLike() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
    }
}