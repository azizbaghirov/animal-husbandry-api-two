package az.eagro.animalhusbandry.business;

public class AccessForbiddenException extends RuntimeException {

    public AccessForbiddenException() {
        super("Access forbidden");
    }
}
