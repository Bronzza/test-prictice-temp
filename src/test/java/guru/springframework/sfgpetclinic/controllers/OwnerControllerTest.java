package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    public static final long ID = 5L;
    public static final String TEST_NAME = "Name";
    public static final String TEST_SURNAME = "Surname";
    public static final String SUCCESS_REDIRECT = "redirect:/owners/" + ID;
    public static final String WILD_CARDS = "%";
    public static final String REDIRECT_OWNER_VIEW = "redirect:/owners/";
    public static final String MULTIPLE_OWNERS_REDIRECT = "owners/ownersList";
    public static final String NO_OWNERS_FOUND_REDIRECT = "owners/findOwners";
    public static final String VIEW_NOT_MATCHED = "Result view not matched";
    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private OwnerController controllerUnderTest;

    @Mock
    BindingResult mockBindingResult;

    @Captor
    ArgumentCaptor<String> anotationBasedCaptor;

    @Mock
    Model mockModel;

    @ParameterizedTest
    @MethodSource("bindingResultNoErrors")
    @DisplayName("Owner controller creation form happy path param test")
    void initCreationFormPositiveParam(Owner owner, BindingResult bindingResult) {
        given(ownerService.save(owner)).willReturn(owner);

        String redirect_result = controllerUnderTest.processCreationForm(owner, bindingResult);

        assertNotNull(redirect_result);
        assertEquals(SUCCESS_REDIRECT, redirect_result);
        then(ownerService).should(times(1)).save(any(Owner.class));
    }

    @ParameterizedTest
    @MethodSource("bindingResultHasErrors")
    @DisplayName("Owner controller creation form unhappy path param test")
    void initCreationFormNegativeParam(Owner owner, BindingResult bindingResult) {
        String result = controllerUnderTest.processCreationForm(owner, bindingResult);

        then(ownerService).shouldHaveNoInteractions();
        assertEquals(OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM, result);
    }

    @Test
    @DisplayName("Owner controller creation form unhappy path")
    void initCreationFormNegative() {
        given(mockBindingResult.hasErrors()).willReturn(true);
        String result = controllerUnderTest.processCreationForm(createTestOwner(), mockBindingResult);

        then(ownerService).shouldHaveNoInteractions();
        assertEquals(OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM, result);
    }

    private static Stream<Arguments> bindingResultNoErrors() {
        return Stream.of(Arguments.of(createTestOwner(), new BindingResult() {
            @Override
            public void rejectValue(String lastName, String notFound, String not_found) {

            }

            @Override
            public boolean hasErrors() {
                return false;
            }
        }));
    }

    private static Owner createTestOwner() {
        return new Owner(ID, TEST_NAME, TEST_SURNAME);
    }

    private static String getWildCardSurname() {
        return WILD_CARDS + TEST_SURNAME + WILD_CARDS;
    }

    private static Stream<Arguments> bindingResultHasErrors() {
        return Stream.of(Arguments.of(createTestOwner(), new BindingResult() {
            @Override
            public void rejectValue(String lastName, String notFound, String not_found) {
            }

            @Override
            public boolean hasErrors() {
                return true;
            }
        }));
    }

    @Test
    @DisplayName("Owner controller creation form happy")
    void initCreationFormPositive() {
        Owner testOwner = createTestOwner();
        given(mockBindingResult.hasErrors()).willReturn(false);
        given(ownerService.save(testOwner)).willReturn(testOwner);

        String redirect_result = controllerUnderTest.processCreationForm(testOwner, mockBindingResult);

        assertNotNull(redirect_result);
        assertEquals(SUCCESS_REDIRECT, redirect_result);
        then(ownerService).should(times(1)).save(any(Owner.class));
    }

    @Nested
    class FindFormTest {

        @BeforeEach
        void setUp() {
            given(ownerService.findAllByLastNameLike(anotationBasedCaptor.capture()))
                    .willAnswer(invocation -> {
                        Owner testOwner = createTestOwner();
                        ArrayList<Owner> serviceReturn = new ArrayList<>();
                        String argument = invocation.getArgument(0);

                        if (getWildCardSurname().equals(argument)) {
                            serviceReturn.add(testOwner);
                            return serviceReturn;
                        } else if ((WILD_CARDS + WILD_CARDS).equals(argument)) {
                            return Collections.emptyList();
                        } else {
                            serviceReturn.add(testOwner);
                            serviceReturn.add(testOwner);
                            return serviceReturn;
                        }
                    });
        }

        @Test
        @DisplayName("Mockito answer test, one owner found")
        void processFindFormAnswerTest() {
            //given
            Owner testOwner = createTestOwner();
            //when
            String view = controllerUnderTest.processFindForm(testOwner, mockBindingResult, mockModel);
            //then
            assertEquals(getWildCardSurname(), anotationBasedCaptor.getValue());
            assertEquals(REDIRECT_OWNER_VIEW + ID, view, () -> VIEW_NOT_MATCHED);
            verifyNoInteractions(mockModel);
        }

        @Test
        @DisplayName("Mockito answer test, multiple owner found")
        void processFindFormAnswerMultipleTest() {
            //given
            InOrder inOrder = inOrder(ownerService, mockModel);
            //when
            String view = controllerUnderTest.processFindForm(new Owner(1L, "SomeName", "SomeSurname"), mockBindingResult, mockModel);
            verifyNoMoreInteractions(ownerService);
            //then
            assertEquals(MULTIPLE_OWNERS_REDIRECT, view, () -> VIEW_NOT_MATCHED);
            inOrder.verify(mockModel, times(1)).addAttribute(anyString(), anyList());
        }

        @Test
        @DisplayName("Mockito answer test, no owner found")
        void processFindFormAnswerNotFoundTest() {
            //given
            //when
            String view = controllerUnderTest.processFindForm(new Owner(1L, "", ""), mockBindingResult, mockModel);
            verifyNoMoreInteractions(ownerService);
            //then
            assertEquals(NO_OWNERS_FOUND_REDIRECT, view, () -> VIEW_NOT_MATCHED);
            verifyNoInteractions(mockModel);
        }

        @Test
        @DisplayName("Mockito answer, no owner found null test")
        void processFindFormAnswerNotFoundNullTest() {
            //given
            //when
            String view = controllerUnderTest.processFindForm(new Owner(1L, null, null), mockBindingResult, mockModel);
            //then
            assertEquals(NO_OWNERS_FOUND_REDIRECT, view, () -> VIEW_NOT_MATCHED);
            verifyNoInteractions(mockModel);
        }

        @Test
        @DisplayName("Mockito argument annotation based capture test")
        void processFindFormAnnotTest() {
            //given
            //when
            String view = controllerUnderTest.processFindForm(createTestOwner(), mockBindingResult, null);
            //then
            assertEquals(getWildCardSurname(), anotationBasedCaptor.getValue());
            assertEquals(REDIRECT_OWNER_VIEW + ID, view, () -> VIEW_NOT_MATCHED);
            verifyNoInteractions(mockModel);
        }


    }
}