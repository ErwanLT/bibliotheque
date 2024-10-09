package fr.eletutour.bibliotheque.service;

import fr.eletutour.bibliotheque.dao.model.Series;
import fr.eletutour.bibliotheque.dao.repository.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesService {

    private final SeriesRepository seriesRepository;


    public SeriesService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    public List<Series> findAll() {
        return seriesRepository.findAll();
    }

    public void save(Series series) {
        seriesRepository.save(series);
    }
}
