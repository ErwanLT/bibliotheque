package fr.eletutour.bibliotheque.dao.repository;

import fr.eletutour.bibliotheque.dao.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
