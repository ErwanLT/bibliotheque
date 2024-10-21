package fr.eletutour.bibliotheque.views;

import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.eletutour.bibliotheque.service.GenreService;

@Route(value = "genres", layout = MainLayout.class)
@PageTitle("Genre")
public class GenreListView extends VerticalLayout {

    private final GenreService genresService;

    public GenreListView(GenreService genresService) {
        this.genresService = genresService;
        setSizeFull();

        UnorderedList genreList = new UnorderedList();
        genresService.findAll().forEach(
                g -> genreList.add(new ListItem(g.getName()))
        );
        add(genreList);
    }
}
