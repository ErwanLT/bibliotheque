package fr.eletutour.bibliotheque.dao.repository;

import fr.eletutour.bibliotheque.dao.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, Long> {
}
