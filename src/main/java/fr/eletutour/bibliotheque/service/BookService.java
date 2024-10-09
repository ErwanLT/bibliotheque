package fr.eletutour.bibliotheque.service;

import fr.eletutour.bibliotheque.dao.model.Book;
import fr.eletutour.bibliotheque.dao.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public void saveNewBook(Book book) {

        book.setShelfmark(calculateShelfmark(book));

        //bookRepository.save(book);
    }

    public String calculateShelfmark(Book book) {
        String authorName = book.getAuthor().getLastName().toUpperCase().substring(0, Math.min(4, book.getAuthor().getLastName().length()));
        String type = book.getType().getName();
        String shelfmark;
        // Calcul de la cote en fonction du type
        if (type.length() <= 2) {
            shelfmark = type + authorName;
        } else {
            shelfmark = type.substring(0, 1).toUpperCase() + authorName;
        }
        // Ajout du numéro de tome si le livre fait partie d'une série
        if (book.getSeries() != null && book.getVolumeNumber() != null) {
            shelfmark += " " + book.getVolumeNumber();
        }
        return shelfmark;
    }
}
