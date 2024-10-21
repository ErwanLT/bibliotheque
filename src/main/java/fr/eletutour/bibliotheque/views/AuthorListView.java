package fr.eletutour.bibliotheque.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.eletutour.bibliotheque.dao.model.Author;
import fr.eletutour.bibliotheque.service.AuthorService;

@Route(value = "authors", layout = MainLayout.class)
@PageTitle("Auteurs")
public class AuthorListView extends VerticalLayout {

    private final AuthorService authorService;

    public AuthorListView(AuthorService authorService) {
        this.authorService = authorService;
        setSizeFull();
        configureGrid();
    }

    private void configureGrid() {
        Grid<Author> authorGrid = new Grid<>(Author.class);
        authorGrid.setSizeFull();
        authorGrid.removeAllColumns();

        authorGrid.addColumn(Author::getLastName).setHeader("Nom").setSortable(true);
        authorGrid.addColumn(Author::getFirstName).setHeader("Prenom");
        authorGrid.addColumn(author -> author.getBooks().size()).setHeader("Nombre de livre");
        authorGrid.setItems(authorService.findAll());
        authorGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        add(authorGrid);
    }
}
