package fr.eletutour.bibliotheque.config.exceptions;

public class GenreException extends Exception {
    private final String message;

    public GenreException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
