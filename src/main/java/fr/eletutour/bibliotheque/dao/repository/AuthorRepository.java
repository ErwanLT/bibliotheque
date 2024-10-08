package fr.eletutour.bibliotheque.dao.repository;

import fr.eletutour.bibliotheque.dao.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
