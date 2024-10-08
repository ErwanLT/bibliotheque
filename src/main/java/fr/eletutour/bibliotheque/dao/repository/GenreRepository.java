package fr.eletutour.bibliotheque.dao.repository;

import fr.eletutour.bibliotheque.dao.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
