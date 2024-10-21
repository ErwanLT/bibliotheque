package fr.eletutour.bibliotheque.views;

import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.eletutour.bibliotheque.service.BookTypeService;

@Route(value = "book-type", layout = MainLayout.class)
@PageTitle("Type de livre")
public class BookTypeListView extends VerticalLayout {

    private final BookTypeService bookTypeService;

    public BookTypeListView(BookTypeService bookTypeService) {
        this.bookTypeService = bookTypeService;

        UnorderedList bookTypeList = new UnorderedList();
        bookTypeService.findAll().forEach(bk -> bookTypeList.add(new ListItem(bk.getName())));

        add(bookTypeList);
    }
}
