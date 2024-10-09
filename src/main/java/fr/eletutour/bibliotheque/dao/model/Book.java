package fr.eletutour.bibliotheque.dao.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String shelfmark; // for book classification

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany
    @JoinTable(
        name = "book_genre",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "type_id")
    private BookType type;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    @Lob
    private byte[] image;

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
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
}