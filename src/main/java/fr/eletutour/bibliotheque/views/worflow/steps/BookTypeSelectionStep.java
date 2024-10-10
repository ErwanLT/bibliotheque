package fr.eletutour.bibliotheque.views.worflow.steps;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import fr.eletutour.bibliotheque.config.exceptions.BookTypeException;
import fr.eletutour.bibliotheque.dao.model.BookType;
import fr.eletutour.bibliotheque.dto.BookDTO;
import fr.eletutour.bibliotheque.service.BookTypeService;

public class BookTypeSelectionStep extends VerticalLayout {

    private final BookTypeService bookTypeService;

    public BookTypeSelectionStep(BookDTO book, Runnable onNext, Runnable onPrevious, BookTypeService bookTypeService) {
        this.bookTypeService = bookTypeService;
        setSizeFull();

        // ComboBox pour sélectionner un type de livre existant
        ComboBox<BookType> bookTypeComboBox = new ComboBox<>("Choisir Type");
        bookTypeComboBox.setItems(bookTypeService.findAll()); // Chargement des types de livres
        bookTypeComboBox.setItemLabelGenerator(BookType::getName); // Méthode pour afficher le nom du type

        // Bouton pour ajouter un nouveau type de livre
        Button addBookTypeButton = new Button("Ajouter un nouveau type", event -> openAddBookTypeDialog(bookTypeComboBox));

        add(bookTypeComboBox, addBookTypeButton);

        // Bouton pour passer à l'étape suivante
        Button nextButton = new Button("Suivant", e -> {
            if (bookTypeComboBox.getValue() != null) {
                book.setType(bookTypeComboBox.getValue()); // Assignation du type au livre
                onNext.run(); // Passage à l'étape suivante
            } else {
                Notification error = Notification.show("Sélectionnez un type de livre.");
                error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        // Bouton pour revenir à l'étape précédente
        Button previousButton = new Button("Précédent", e -> {
            onPrevious.run(); // Action pour revenir à l'étape précédente
        });

        HorizontalLayout buttonLayout = new HorizontalLayout(previousButton, nextButton);
        add(buttonLayout); // Ajout du layout de boutons au contenu
    }

    private void openAddBookTypeDialog(ComboBox<BookType> bookTypeComboBox) {
        Dialog dialog = new Dialog();

        // Champ pour le nouveau type de livre
        TextField newBookTypeField = new TextField("Nouveau Type");
        Button saveNewBookTypeButton = new Button("Ajouter Type", e -> {
            String newTypeName = newBookTypeField.getValue();
            if (!newTypeName.isEmpty()) {
                BookType newBookType = new BookType(newTypeName);
                try {
                    bookTypeService.save(newBookType);
                    bookTypeComboBox.setItems(bookTypeService.findAll()); // Mise à jour de la liste des types
                    bookTypeComboBox.setValue(newBookType); // Sélection automatique du nouveau type

                    Notification success = Notification.show("Type ajouté avec succès!");
                    success.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    dialog.close(); // Sauvegarde en base
                } catch (BookTypeException ex) {
                    Notification error = Notification.show(ex.getMessage());
                    error.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            } else {
                Notification error = Notification.show("Renseignez un type.");
                error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        // Bouton pour fermer la pop-up
        Button cancelButton = new Button("Annuler", e -> dialog.close());

        // Ajout des champs et des boutons dans le dialog
        VerticalLayout dialogLayout = new VerticalLayout(newBookTypeField, saveNewBookTypeButton, cancelButton);
        dialog.add(dialogLayout);
        dialog.open(); // Ouvrir le dialog
    }
}
