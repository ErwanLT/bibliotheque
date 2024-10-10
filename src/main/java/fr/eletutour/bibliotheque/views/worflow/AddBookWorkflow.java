package fr.eletutour.bibliotheque.views.worflow;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.eletutour.bibliotheque.dao.model.Book;
import fr.eletutour.bibliotheque.dto.BookDTO;
import fr.eletutour.bibliotheque.service.*;
import fr.eletutour.bibliotheque.views.BookListView;
import fr.eletutour.bibliotheque.views.MainLayout;

import java.util.ArrayList;
import java.util.List;

@Route(value = "add-book", layout = MainLayout.class)
@PageTitle("Ajouter livre")
public class AddBookWorkflow extends VerticalLayout {

    private final BookDTO book = new BookDTO();
    private final BookService bookService;
    private final AuthorService authorService;
    private final BookTypeService bookTypeService;
    private final GenreService genreService;
    private final SeriesService seriesService;
    private int currentStepIndex = 0; // Pour suivre l'étape actuelle
    private final List<Component> steps = new ArrayList<>(); // Liste pour stocker les étapes


    public AddBookWorkflow(BookService bookService, AuthorService authorService, BookTypeService bookTypeService, GenreService genreService, SeriesService seriesService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.bookTypeService = bookTypeService;
        this.genreService = genreService;
        this.seriesService = seriesService;

        // Initialisation des étapes
        initSteps();

        // Affichage de la première étape
        showCurrentStep();
    }

    private void initSteps() {
        steps.add(new AuthorSelectionStep(book, this::nextStep, authorService));
        steps.add(new BookTypeSelectionStep(book, this::nextStep, this::previousStep, bookTypeService)); // Exemple d'étape pour le type de livre
        steps.add(new BookInfoStep(book, this::finishWizard, this::previousStep, genreService, seriesService)); // Exemple d'étape pour les infos du livre
    }

    private void showCurrentStep() {
        removeAll(); // Vider la vue actuelle
        add(steps.get(currentStepIndex)); // Afficher l'étape actuelle
    }

    private void nextStep() {
        if (currentStepIndex < steps.size() - 1) {
            currentStepIndex++; // Avancer à l'étape suivante
            showCurrentStep();
        }
    }

    private void previousStep() {
        if (currentStepIndex > 0) { // Vérifier si on peut reculer
            currentStepIndex--; // Avancer à l'étape précédente
            showCurrentStep(); // Afficher l'étape actuelle
        }
    }

    private void finishWizard() {
        bookService.saveNewBook(book);
        Notification success =  Notification.show("Livre ajouter avec succès!");
        success.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        UI.getCurrent().navigate(BookListView.class);
    }
}
