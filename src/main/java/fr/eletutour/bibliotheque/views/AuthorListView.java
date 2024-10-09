package fr.eletutour.bibliotheque.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "authors", layout = MainLayout.class)
@PageTitle("Auteurs")
public class AuthorListView extends VerticalLayout {
}
