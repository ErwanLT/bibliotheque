package fr.eletutour.bibliotheque.views;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import fr.eletutour.bibliotheque.dao.model.Book;
import fr.eletutour.bibliotheque.service.BookService;

import java.io.ByteArrayInputStream;

@Route(value = "books", layout = MainLayout.class)
@PageTitle("Livres")
public class BookListView extends VerticalLayout {

    private final BookService bookService;

    public BookListView(BookService bookService){
        this.bookService = bookService;
        setSizeFull();
        configureGrid();

    }

    private void configureGrid() {
        Grid<Book> bookGrid = new Grid<>(Book.class);
        bookGrid.setSizeFull();
        bookGrid.removeAllColumns();

        bookGrid.addColumn(Book::getTitle).setHeader("Titre");
        bookGrid.addColumn(b -> b.getAuthor().getName()).setHeader("Auteur");
        bookGrid.addColumn(b -> b.getType().getName()).setHeader("Type");
        bookGrid.addComponentColumn(b -> {
            UnorderedList listType = new UnorderedList();
            b.getGenres().forEach(g -> listType.add(new ListItem(g.getName())));
            return listType;
        }).setHeader("Genre");
        bookGrid.addComponentColumn(b -> {
            if(b.getImage() != null){
                Image img = createImageFromBytes(b.getImage(), b.getTitle());
                img.setHeight(150, Unit.PIXELS);
                img.setWidth(150, Unit.PIXELS);
                return img;
            } else {
                return new Span("NA");
            }
        }).setHeader("Couverture");
        bookGrid.addColumn(Book::getShelfmark).setHeader("Cote");
        bookGrid.addColumn(b -> b.getSeries().getName()).setHeader("Série");
        bookGrid.addColumn(Book::getVolumeNumber).setHeader("Volume");


        bookGrid.setItems(bookService.findAll());
        bookGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        add(bookGrid);
    }

    public static Image createImageFromBytes(byte[] imageBytes, String altText) {
        StreamResource resource = new StreamResource("image", () -> new ByteArrayInputStream(imageBytes));
        return new Image(resource, altText);
    }
}
