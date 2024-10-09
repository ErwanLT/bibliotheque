package fr.eletutour.bibliotheque.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "books", layout = MainLayout.class)
@PageTitle("Livres")
public class BookListView extends VerticalLayout {
}
