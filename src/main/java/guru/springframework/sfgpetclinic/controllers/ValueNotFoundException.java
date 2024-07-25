package guru.springframework.sfgpetclinic.controllers;

public class ValueNotFoundException extends RuntimeException {

    public ValueNotFoundException(String fieldName) {
        super("Value 0f: " + fieldName + " not found");
    }

    public ValueNotFoundException(String fieldName, Throwable cause) {
        super("Value 0f: " + fieldName + " not found", cause);
    }

}
