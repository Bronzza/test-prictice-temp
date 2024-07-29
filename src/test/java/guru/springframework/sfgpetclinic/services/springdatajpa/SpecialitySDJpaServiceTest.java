package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    public static final long LONG_ID = 1L;
    @Mock
    SpecialtyRepository mockRepository;

    @InjectMocks
    SpecialitySDJpaService serviceUnderTest;

    @Test
    void deleteById() {
        serviceUnderTest.deleteById(LONG_ID);
        verify(mockRepository).deleteById(LONG_ID);
    }

    @Test
    @DisplayName("BDD delete by Id")
    void deleteByIdBdd() {
        //given
        //when
        serviceUnderTest.deleteById(LONG_ID);
        then(mockRepository).should(times(1)).deleteById(LONG_ID);

    }

    @Test
    void deleteTest() {
        Speciality specialityToDelete = new Speciality();
        serviceUnderTest.delete(specialityToDelete);
        serviceUnderTest.delete(specialityToDelete);
        verify(mockRepository, times(2)).delete(specialityToDelete);
    }

    @Test
    @DisplayName("BDD delete test")
    void deleteTestBDD() {
//        given
//        when
        Speciality specialityToDelete = new Speciality();
        serviceUnderTest.delete(specialityToDelete);
        serviceUnderTest.delete(specialityToDelete);
//        then

        then(mockRepository).should(times(2)).delete(specialityToDelete);
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
    @DisplayName("BDD find by Id BDD")
    void findByIdTestBDD() {
//        given
        Speciality speciality = new Speciality();
        given(mockRepository.findById(LONG_ID)).willReturn(Optional.of(speciality));
//        when
        Speciality result = serviceUnderTest.findById(LONG_ID);
//        then
        assertNotNull(result);
        assertEquals(speciality, result);
        then(mockRepository).should().findById(LONG_ID);
    }

    @Test
    void findByIdTest() {
        Speciality speciality = new Speciality();
        when(mockRepository.findById(1L)).thenReturn(Optional.of(speciality));

        Speciality result = serviceUnderTest.findById(LONG_ID);
        assertNotNull(result);
        assertEquals(speciality, result);
        verify(mockRepository).findById(LONG_ID);
    }

    @Test
    void findByIdBddTest() {
        Speciality speciality = new Speciality();
        given(mockRepository.findById(LONG_ID)).willReturn(Optional.of(speciality));

        Speciality result = serviceUnderTest.findById(LONG_ID);
        assertNotNull(result);
        assertEquals(speciality, result);
        then(mockRepository).should().findById(anyLong());
        then(mockRepository).should(times(1)).findById(anyLong());
        then(mockRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    @DisplayName("Exception intercepter mockito")
    void exceptionTesta() {
        doThrow(new RuntimeException()).when(mockRepository).findById(LONG_ID);
//        given(mockRepository.findById(LONG_ID)).willThrow(new RuntimeException("Testing purposes"));

//        Speciality result = serviceUnderTest.findById(LONG_ID);

        assertThrows(RuntimeException.class, () -> mockRepository.findById(LONG_ID), "Exception wasn't thrown");
    }

    @Test
    @DisplayName("Exception intercepter mockito")
    void exceptionTest() {

        given(mockRepository.findById(LONG_ID)).willThrow(new RuntimeException("Testing purposes"));

//        Speciality result = serviceUnderTest.findById(LONG_ID);

        assertThrows(RuntimeException.class, () -> mockRepository.findById(LONG_ID), "Exception wasn't thrown");
        then(mockRepository).should().findById(LONG_ID);
    }

    @Test
    @DisplayName("Lambda param matchers mochito")
    void argsMatchersMockito() {

        final String description = "DESCRIPTION";
        Speciality speciality = new Speciality();
        speciality.setDescription(description);
        speciality.setId(LONG_ID);
        given(mockRepository.save(argThat(argument -> description.equals(argument.getDescription()))))
                .willReturn(speciality);
//        when
        Speciality result = serviceUnderTest.save(speciality);
//        then
        then(mockRepository).should().save(speciality);
        assertEquals(speciality, result);
    }



}