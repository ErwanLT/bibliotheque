package fr.eletutour.bibliotheque.views.worflow;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.eletutour.bibliotheque.dto.BookDTO;
import fr.eletutour.bibliotheque.service.*;
import fr.eletutour.bibliotheque.views.BookListView;
import fr.eletutour.bibliotheque.views.MainLayout;
import fr.eletutour.bibliotheque.views.worflow.steps.AuthorSelectionStep;
import fr.eletutour.bibliotheque.views.worflow.steps.BookInfoStep;
import fr.eletutour.bibliotheque.views.worflow.steps.BookTypeSelectionStep;

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

        setSizeFull();

        // Initialisation des étapes
        initSteps();

        // Affichage de la première étape
        showCurrentStep();
    }

    private void initSteps() {
        steps.add(new AuthorSelectionStep(book, this::nextStep, authorService));
        steps.add(new BookTypeSelectionStep(book, this::nextStep, this::previousStep, bookTypeService));
        steps.add(new BookInfoStep(book, this::finishWizard, this::previousStep, genreService, seriesService));
    }

    // Création de l'indicateur visuel en forme de flèches connectées
    private void showStepIndicator() {
        HorizontalLayout stepIndicator = new HorizontalLayout();
        stepIndicator.setWidthFull();
        stepIndicator.addClassName("step-indicator");

        String[] stepsLabels = { "Auteur", "Type", "Informations générale" };

        for (int i = 0; i < stepsLabels.length; i++) {
            Span step = new Span(stepsLabels[i]);
            step.addClassName("step");
            if (i == currentStepIndex) {
                step.addClassName("active"); // Colorer l'étape active
            }
            stepIndicator.add(step);
        }

        // Ajouter l'indicateur en haut de l'écran
        add(stepIndicator);
    }

    // Affichage de l'étape actuelle avec l'indicateur de progression
    private void showCurrentStep() {
        removeAll(); // Vider la vue actuelle
        showStepIndicator(); // Mettre à jour l'indicateur d'étape
        add(steps.get(currentStepIndex)); // Afficher l'étape actuelle
    }

    // Méthode pour passer à l'étape suivante
    private void nextStep() {
        if (currentStepIndex < steps.size() - 1) {
            currentStepIndex++; // Avancer à l'étape suivante
            showCurrentStep();
        }
    }

    // Méthode pour revenir à l'étape précédente
    private void previousStep() {
        if (currentStepIndex > 0) { // Vérifier si on peut reculer
            currentStepIndex--; // Revenir à l'étape précédente
            showCurrentStep(); // Afficher l'étape actuelle
        }
    }

    // Finaliser le processus et enregistrer le livre
    private void finishWizard() {
        bookService.saveNewBook(book);
        Notification success = Notification.show("Livre ajouté avec succès!");
        success.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        UI.getCurrent().navigate(BookListView.class);
    }
}
