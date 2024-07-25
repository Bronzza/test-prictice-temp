package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository mockRepository;

    @InjectMocks
    SpecialitySDJpaService serviceUnderTest;

    @Test
    void deleteById() {
        serviceUnderTest.deleteById(1L);
        verify(mockRepository).deleteById(1L);
    }

    @Test
    void deleteTest() {
        Speciality specialityToDelete = new Speciality();
        serviceUnderTest.delete(specialityToDelete);
        serviceUnderTest.delete(specialityToDelete);
        verify(mockRepository, times(2)).delete(specialityToDelete);
    }

    @Test
    void testArgumentMatcher(){
        serviceUnderTest.delete(new Speciality());
        serviceUnderTest.delete(new Speciality());
        serviceUnderTest.delete(new Speciality());
        verify(mockRepository, times(3)).delete(any(Speciality.class));
    }

    @Test
    void deleteAtLeastAndNever() {
        Speciality specialityToDelete = new Speciality();
        serviceUnderTest.delete(specialityToDelete);
        serviceUnderTest.delete(specialityToDelete);
        verify(mockRepository, atLeast(2)).delete(specialityToDelete);
        verify(mockRepository, never()).delete(new Speciality());
    }

    @Test
    void findByIdTest() {
        Speciality speciality = new Speciality();
        when(mockRepository.findById(1L)).thenReturn(Optional.of(speciality));

        Speciality result = serviceUnderTest.findById(1L);
        assertNotNull(result);
        assertEquals(speciality, result);
        verify(mockRepository).findById(1L);
    }


}