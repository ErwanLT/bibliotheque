package fr.eletutour.bibliotheque.service;

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

    public void save(BookType bookType) {
        if (bookTypeRepository.existsByNameIgnoreCase(bookType.getName())) {
            throw new IllegalArgumentException("This book type already exists.");
        }
        bookTypeRepository.save(bookType);
    }
}
