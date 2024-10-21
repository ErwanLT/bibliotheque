package fr.eletutour.bibliotheque.service;

import fr.eletutour.bibliotheque.dao.model.Book;
import fr.eletutour.bibliotheque.dao.model.BookType;
import fr.eletutour.bibliotheque.dao.model.Genre;
import fr.eletutour.bibliotheque.dao.repository.BookRepository;
import fr.eletutour.bibliotheque.dto.BookDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        String type = book.getType().getName();
        String auteur = book.getAuthor().getLastName();

        // Prendre les 4 premières lettres du nom de l'auteur
        String auteurPart = auteur.substring(0, Math.min(4, auteur.length())).toUpperCase();

        StringBuilder cote = new StringBuilder();

        // Règle pour les types avec 2 caractères ou moins
        if (type.length() <= 2) {
            cote.append(type.toUpperCase());
        }
        // Règle pour les types avec plus de 3 caractères
        else if (type.length() > 3) {
            cote.append(type.substring(0, 1).toUpperCase());
        }

        // Ajouter les 4 premières lettres du nom de l'auteur
        cote.append(auteurPart);

        // Si le livre fait partie d'une série, utiliser 5 lettres du titre de la série
        if (book.getSeries() != null) {
            String seriePart = book.getSeries().getName();
            seriePart = seriePart.substring(0, Math.min(5, seriePart.length())).toUpperCase();
            cote.append(seriePart);

            // Ajouter le numéro de tome si applicable
            if (book.getVolumeNumber() != null) {
                cote.append(book.getVolumeNumber());
            }
        }
        // Si le livre ne fait pas partie d'une série, utiliser 5 lettres du titre du livre
        else {
            String bookTitlePart = book.getTitle();
            bookTitlePart = bookTitlePart.substring(0, Math.min(5, bookTitlePart.length())).toUpperCase();
            cote.append(bookTitlePart);
        }

        return cote.toString();
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
        book.setImage(bookDTO.getImage());

        bookRepository.save(book);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public String findMostCommonBookType() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .collect(Collectors.groupingBy(Book::getType, Collectors.counting()))  // Compter les occurrences
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())  // Trouver le max
                .map(Map.Entry::getKey)
                .map(BookType::getName)// Récupérer le type
                .orElse("");  // Si pas trouvé, retourner null
    }

    public String findMostCommonGenre() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .flatMap(book -> book.getGenres().stream())  // Aplatir le Set<Genre> en un Stream<Genre>
                .collect(Collectors.groupingBy(genre -> genre, Collectors.counting()))  // Compter les occurrences
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())  // Trouver le genre le plus fréquent
                .map(Map.Entry::getKey)
                .map(Genre::getName)// Récupérer le genre
                .orElse("");  // Si pas trouvé, retourner null
    }
}
