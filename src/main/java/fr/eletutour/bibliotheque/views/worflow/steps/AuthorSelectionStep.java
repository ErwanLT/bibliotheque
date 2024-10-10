package fr.eletutour.bibliotheque.views.worflow.steps;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import fr.eletutour.bibliotheque.dao.model.Author;
import fr.eletutour.bibliotheque.dto.BookDTO;
import fr.eletutour.bibliotheque.service.AuthorService;

public class AuthorSelectionStep extends VerticalLayout {

    private final AuthorService authorService;

    public AuthorSelectionStep(BookDTO book, Runnable onNext, AuthorService authorService) {
        this.authorService = authorService;
        setSizeFull();

        // ComboBox pour sélectionner un auteur existant
        ComboBox<Author> authorComboBox = new ComboBox<>("Choix auteur");
        authorComboBox.setItems(authorService.findAll()); // Chargement des auteurs
        authorComboBox.setItemLabelGenerator(Author::getName);

        // Bouton pour ajouter un nouvel auteur
        Button addAuthorButton = new Button("Ajouter un nouvel auteur", event -> openAddAuthorDialog(authorComboBox));

        add(authorComboBox, addAuthorButton);

        // Bouton pour passer à l'étape suivante
        Button nextButton = new Button("Suivant", e -> {
            if (authorComboBox.getValue() != null) {
                book.setAuthor(authorComboBox.getValue()); // Assignation de l'auteur au livre
                onNext.run(); // Passage à l'étape suivante
            } else {
                Notification error = Notification.show("Sélectionnez un auteur ou ajoutez-en un");
                error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        // Ajout du bouton suivant au contenu
        add(nextButton); // Ajout du contenu à la classe principale
    }

    private void openAddAuthorDialog(ComboBox<Author> authorComboBox) {
        Dialog dialog = new Dialog();

        dialog.add(new H1("Création d'un auteur"));

        // Champs pour le prénom et le nom de famille
        TextField firstnameField = new TextField("Prénom");
        firstnameField.setWidthFull();
        TextField lastnameField = new TextField("Nom de famille");
        lastnameField.setWidthFull();
        Button saveNewAuthorButton = new Button("Sauvegarder", e -> {
            if (!firstnameField.isEmpty() && !lastnameField.isEmpty()) {
                Author newAuthor = new Author(firstnameField.getValue(), lastnameField.getValue());
                authorService.save(newAuthor); // Sauvegarde en base
                authorComboBox.setItems(authorService.findAll()); // Mise à jour de la liste des auteurs
                authorComboBox.setValue(newAuthor); // Sélection automatique du nouvel auteur

                Notification success = Notification.show("Auteur ajouté avec succès");
                success.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                dialog.close(); // Fermer la pop-up après sauvegarde
            } else {
                Notification error = Notification.show("Renseignez tous les champs avant de sauvegarder");
                error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        saveNewAuthorButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_SUCCESS);

        // Bouton pour fermer la pop-up
        Button cancelButton = new Button("Annuler", e -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_ERROR);
        HorizontalLayout boutonLayout = new HorizontalLayout(cancelButton, saveNewAuthorButton);
        boutonLayout.setWidthFull();

        // Ajout des champs et des boutons dans le dialog
        VerticalLayout dialogLayout = new VerticalLayout(firstnameField, lastnameField, boutonLayout);
        dialog.add(dialogLayout);
        dialog.open(); // Ouvrir le dialog
    }
}
