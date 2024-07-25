package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    public static final long SINGLE_VISIT_ID = 1L;
    public static final long ANOTHER_VISIT_ID = 2L;

    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    private VisitSDJpaService serviceUnderTest;

    @ParameterizedTest
    @MethodSource("initMultipleVisitList")
    void findAll(List<Visit> visits) {
        when(visitRepository.findAll()).thenReturn(visits);

        Set<Visit> all = serviceUnderTest.findAll();
        assertNotNull(all);
        verify(visitRepository, times(1)).findAll();
    }

    private static Stream<Arguments> initMultipleVisitList() {
        List<Visit> visits = new ArrayList<>();
        visits.add(new Visit(SINGLE_VISIT_ID, LocalDate.now()));
        visits.add(new Visit(ANOTHER_VISIT_ID, LocalDate.now()));
        return Stream.of(Arguments.of(visits));
    }

    private static Stream<Arguments> initSingleVisit() {
        Visit visit = new Visit(SINGLE_VISIT_ID, LocalDate.now());
        return Stream.of(Arguments.of(visit));
    }

    @ParameterizedTest
    @MethodSource("initSingleVisit")
    void findById(Visit visit) {
        when(visitRepository.findById(SINGLE_VISIT_ID)).thenReturn(Optional.of(visit));

        Visit result = serviceUnderTest.findById(SINGLE_VISIT_ID);
        verify(visitRepository).findById(SINGLE_VISIT_ID);
        assertNotNull(result);
        assertEquals(visit, result);
    }

    @ParameterizedTest
    @MethodSource("initSingleVisit")
    void save(Visit visit) {
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);

        Visit result = serviceUnderTest.save(visit);
        verify(visitRepository).save(visit);
        assertNotNull(result);
        assertEquals(visit, result);
    }

    @Test
    void delete() {
       serviceUnderTest.delete(new Visit());
       verify(visitRepository).delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        serviceUnderTest.deleteById(SINGLE_VISIT_ID);
        verify(visitRepository).deleteById(anyLong());
    }
}