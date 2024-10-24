package fr.eletutour.bibliotheque.views;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;
import fr.eletutour.bibliotheque.service.AuthorService;
import fr.eletutour.bibliotheque.service.BookService;
import fr.eletutour.bibliotheque.service.BookTypeService;
import fr.eletutour.bibliotheque.service.GenreService;

@Route(value = "", layout = MainLayout.class)
public class MainView extends VerticalLayout {

    private final BookService bookService;
    private final AuthorService authorService;
    private final BookTypeService bookTypeService;
    private final GenreService genreService;

    public MainView(BookService bookService, AuthorService authorService, BookTypeService bookTypeService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.bookTypeService = bookTypeService;
        this.genreService = genreService;

        setSizeFull();

        HorizontalLayout titleLayout = new HorizontalLayout();
        titleLayout.setWidthFull();
        Div title = new Div();
        title.setSizeFull();
        title.getStyle().setTextAlign(Style.TextAlign.CENTER);
        title.add(new H1("Informations Générales"));
        titleLayout.add(title);
        add(titleLayout);

        // Premier layout horizontal avec les informations sur les auteurs et les livres
        HorizontalLayout infoLayout = new HorizontalLayout();
        infoLayout.setWidthFull();
        VerticalLayout authorLayout = configureAuthorLayout();
        VerticalLayout bookLayout = configureBookLayout();
        VerticalLayout typeLayout = configureTypeLayout();
        VerticalLayout genreLayout = configureGenreLayout();
        infoLayout.add(authorLayout, bookLayout, typeLayout, genreLayout);

        HorizontalLayout statLayout = new HorizontalLayout();
        statLayout.setWidthFull();
        VerticalLayout stats = configureStatisticsLayout();
        statLayout.add(stats);

        add(infoLayout, statLayout);
    }

    private VerticalLayout configureGenreLayout() {
        VerticalLayout genreLayout = new VerticalLayout();
        sectionTitle("Genres", genreLayout);
        var genres = genreService.findAll();
        Span spanGenre = new Span(String.format("Nous avons %s genre(s) référencé(s)", genres.size()));
        genreLayout.add(spanGenre);
        if(!genres.isEmpty()){
            UnorderedList typeList = new UnorderedList();
            genres.forEach(t -> typeList.add(new ListItem(t.getName())));
            genreLayout.add(typeList);
        }
        return genreLayout;
    }

    private VerticalLayout configureTypeLayout() {
        VerticalLayout typeLayout = new VerticalLayout();
        sectionTitle("Types", typeLayout);
        var types = bookTypeService.findAll();
        Span spanType = new Span(String.format("Nous avons %s type(s) de livre(s) référencé(s)", types.size()));
        typeLayout.add(spanType);
        if(!types.isEmpty()){
            UnorderedList typeList = new UnorderedList();
            types.forEach(t -> typeList.add(new ListItem(t.getName())));
            typeLayout.add(typeList);
        }
        return typeLayout;
    }

    private VerticalLayout configureBookLayout() {
        VerticalLayout bookLayout = new VerticalLayout();
        sectionTitle("Livres", bookLayout);
        var books = bookService.findAll();
        Span spanBooks = new Span(String.format("Nous avons %s ouvrage(s) référencé(s)", books.size()));
        bookLayout.add(spanBooks);
        return bookLayout;
    }

    private VerticalLayout configureAuthorLayout() {
        VerticalLayout authorLayout = new VerticalLayout();
        sectionTitle("Auteurs", authorLayout);
        var authors = authorService.findAll();
        Span spanAuthor = new Span(String.format("Nous avons %s auteur(s) référencé(s)", authors.size()));
        authorLayout.add(spanAuthor);
        return authorLayout;
    }

    private VerticalLayout configureStatisticsLayout() {
        VerticalLayout statisticsLayout = new VerticalLayout();
        sectionTitle("Statistiques", statisticsLayout);

        var mostCommonType = bookService.findMostCommonBookType();
        var mostCommonGenre = bookService.findMostCommonGenre();

        Span spanTypeStats = new Span(String.format("Type le plus fréquent : %s", mostCommonType));
        Span spanGenreStats = new Span(String.format("Genre le plus fréquent : %s", mostCommonGenre));

        statisticsLayout.add(spanTypeStats, spanGenreStats);
        return statisticsLayout;
    }


    private void sectionTitle(String title, VerticalLayout layout) {
        layout.add(new H2(title));
        layout.getStyle().set("border-style", "dashed");
    }
}

