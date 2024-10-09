package fr.eletutour.bibliotheque.config.exceptions;

public class BookTypeException extends Exception {

    private final String message;

    public BookTypeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
