package fr.eletutour.bibliotheque.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "book-type", layout = MainLayout.class)
@PageTitle("Type de livre")
public class BookTypeListView extends VerticalLayout {
}
