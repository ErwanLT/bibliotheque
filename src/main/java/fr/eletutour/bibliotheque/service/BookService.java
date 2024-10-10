package fr.eletutour.bibliotheque.service;

import fr.eletutour.bibliotheque.dao.model.*;
import fr.eletutour.bibliotheque.dao.repository.BookRepository;
import fr.eletutour.bibliotheque.dto.BookDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public void saveNewBook(BookDTO bookDTO) {
        Book book = saveIncompleteBook(bookDTO);
        finalizeBook(bookDTO, book);
    }

    public String calculateShelfmark(BookDTO book) {
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

    public Book saveIncompleteBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setShelfmark(calculateShelfmark(bookDTO));
        return bookRepository.save(book);
    }

    public void finalizeBook(BookDTO bookDTO, Book book) {
        book.setAuthor(bookDTO.getAuthor());
        book.setGenres(bookDTO.getGenres());
        book.setType(bookDTO.getType());
        book.setSeries(bookDTO.getSeries());
        book.setVolumeNumber(bookDTO.getVolumeNumber());

        bookRepository.save(book);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
