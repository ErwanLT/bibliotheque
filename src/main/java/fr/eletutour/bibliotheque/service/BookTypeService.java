package fr.eletutour.bibliotheque.service;

import fr.eletutour.bibliotheque.config.exceptions.BookTypeException;
import fr.eletutour.bibliotheque.dao.model.BookType;
import fr.eletutour.bibliotheque.dao.repository.BookTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookTypeService {

    private final BookTypeRepository bookTypeRepository;

    public BookTypeService(BookTypeRepository bookTypeRepository) {
        this.bookTypeRepository = bookTypeRepository;
    }

    public List<BookType> findAll() {
        return bookTypeRepository.findAll();
    }

    public void save(BookType bookType) throws BookTypeException {
        if (bookTypeRepository.existsByNameIgnoreCase(bookType.getName())) {
            throw new BookTypeException("Ce type existe déjà.");
        }
        bookTypeRepository.save(bookType);
    }
}
