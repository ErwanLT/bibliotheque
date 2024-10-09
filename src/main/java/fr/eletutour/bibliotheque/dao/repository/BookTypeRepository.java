package fr.eletutour.bibliotheque.dao.repository;

import fr.eletutour.bibliotheque.dao.model.BookType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookTypeRepository extends JpaRepository<BookType, Long> {
    boolean existsByNameIgnoreCase(String name);
}
