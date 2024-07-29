package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;

public class BindingResultImpl implements BindingResult {
    @Override
    public void rejectValue(String lastName, String notFound, String not_found) {

    }

    @Override
    public boolean hasErrors() {
        return false;
    }
}
