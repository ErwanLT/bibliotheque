package fr.eletutour.bibliotheque.dto;

import fr.eletutour.bibliotheque.dao.model.Author;
import fr.eletutour.bibliotheque.dao.model.BookType;
import fr.eletutour.bibliotheque.dao.model.Genre;
import fr.eletutour.bibliotheque.dao.model.Series;

import java.util.HashSet;
import java.util.Set;

public class BookDTO {
    private String title;

    private String shelfmark; // for book classification

    private Author author;

    private Set<Genre> genres = new HashSet<>();

    private BookType type;

    private Series series;

    private byte[] image;

    private Integer volumeNumber;

    public BookDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShelfmark() {
        return shelfmark;
    }

    public void setShelfmark(String shelfmark) {
        this.shelfmark = shelfmark;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public BookType getType() {
        return type;
    }

    public void setType(BookType type) {
        this.type = type;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Integer getVolumeNumber() {
        return volumeNumber;
    }

    public void setVolumeNumber(Integer volumeNumber) {
        this.volumeNumber = volumeNumber;
    }
}
