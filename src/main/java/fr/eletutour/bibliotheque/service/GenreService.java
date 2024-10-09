package fr.eletutour.bibliotheque.service;

import fr.eletutour.bibliotheque.config.exceptions.GenreException;
import fr.eletutour.bibliotheque.dao.model.Genre;
import fr.eletutour.bibliotheque.dao.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;


    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public void save(Genre genre) throws GenreException {
        if (genreRepository.existsByNameIgnoreCase(genre.getName())) {
            throw new GenreException("Ce genre existe déjà.");
        }
        genreRepository.save(genre);
    }
}
