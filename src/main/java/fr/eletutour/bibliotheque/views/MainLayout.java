package fr.eletutour.bibliotheque.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import fr.eletutour.bibliotheque.views.worflow.AddBookWorkflow;

public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Ma bibliothèque");
        logo.addClickListener(click -> UI.getCurrent().navigate(""));
        HorizontalLayout header = new HorizontalLayout(
                new DrawerToggle(),
                logo
        );
        addToNavbar(header);
    }

    private void createDrawer() {
        SideNav sideNav = new SideNav();

        SideNavItem authorNavItem = new SideNavItem("Auteurs", AuthorListView.class, VaadinIcon.USER.create());
        SideNavItem bookNavItem = new SideNavItem("Livres", BookListView.class, VaadinIcon.BOOK.create());
        SideNavItem bookTypeNavItem = new SideNavItem("Type", BookTypeListView.class, VaadinIcon.MAGIC.create());
        SideNavItem genreNaveItem = new SideNavItem("Genre", GenreListView.class, VaadinIcon.PAINTBRUSH.create());

        SideNavItem addBookNavItem = new SideNavItem("Ajouter livre", AddBookWorkflow.class, VaadinIcon.PLUS.create());

        sideNav.addItem(authorNavItem);
        sideNav.addItem(bookNavItem);
        sideNav.addItem(bookTypeNavItem);
        sideNav.addItem(genreNaveItem);
        sideNav.addItem(addBookNavItem);

        // Ajoute le SideNav au Drawer
        addToDrawer(sideNav);
    }
}
