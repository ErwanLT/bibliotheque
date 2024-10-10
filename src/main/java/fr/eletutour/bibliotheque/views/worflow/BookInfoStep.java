package fr.eletutour.bibliotheque.views.worflow;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import fr.eletutour.bibliotheque.config.exceptions.GenreException;
import fr.eletutour.bibliotheque.dao.model.Book;
import fr.eletutour.bibliotheque.dao.model.Genre;
import fr.eletutour.bibliotheque.dao.model.Series;
import fr.eletutour.bibliotheque.dto.BookDTO;
import fr.eletutour.bibliotheque.service.GenreService;
import fr.eletutour.bibliotheque.service.SeriesService;

import java.io.IOException;


public class BookInfoStep extends VerticalLayout {

    public BookInfoStep(BookDTO book, Runnable onNext, Runnable onPrevious, GenreService genreService, SeriesService seriesService) {

        // Champs pour le titre du livre
        TextField titleField = new TextField("Titre");
        titleField.setValue(book.getTitle() != null ? book.getTitle() : "");

        // ComboBox pour sélectionner les genres
        MultiSelectComboBox<Genre> genreComboBox = new MultiSelectComboBox<>("Genre");
        genreComboBox.setItems(genreService.findAll());
        genreComboBox.setItemLabelGenerator(Genre::getName);

        // Bouton pour ajouter un nouveau genre via une pop-up
        Button addNewGenreButton = new Button("Ajouter Genre", event -> openAddGenreDialog(genreComboBox, genreService));

        // Checkbox pour indiquer si le livre fait partie d'une série
        Checkbox isPartOfSeriesCheckbox = new Checkbox("Fait partie d'une série");
        isPartOfSeriesCheckbox.setValue(book.getSeries() != null);

        // ComboBox pour sélectionner une série existante
        ComboBox<Series> seriesComboBox = new ComboBox<>("Sélectionner Série");
        seriesComboBox.setItems(seriesService.findAll());
        seriesComboBox.setItemLabelGenerator(Series::getName);
        seriesComboBox.setEnabled(book.getSeries() != null);

        // Ajout du numéro de tome si la série est sélectionnée
        TextField volumeNumberField = new TextField("Numéro de Tome");
        volumeNumberField.setVisible(book.getSeries() != null && book.getVolumeNumber() != null);
        volumeNumberField.setValue(book.getVolumeNumber() != null ? book.getVolumeNumber().toString() : "");

        // Pop-up pour ajouter une nouvelle série
        Button addNewSeriesButton = new Button("Ajouter Série", e -> openAddSeriesDialog(seriesComboBox, seriesService));
        addNewSeriesButton.setVisible(false);

        // Activer/désactiver la ComboBox de série et le champ numéro de tome en fonction de la checkbox
        isPartOfSeriesCheckbox.addValueChangeListener(event -> {
            seriesComboBox.setEnabled(event.getValue());
            addNewSeriesButton.setVisible(event.getValue());
            volumeNumberField.setVisible(event.getValue());
            if (!event.getValue()) {
                seriesComboBox.clear();
                volumeNumberField.clear();
            }
        });

        Upload upload = new Upload();
        MemoryBuffer buffer = new MemoryBuffer();
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        upload.setMaxFileSize(2 * 1024 * 1024); // 2 MB
        upload.setMaxFiles(1);
        upload.setReceiver(buffer);
        upload.addSucceededListener(event -> {
            try {
                book.setImage(buffer.getInputStream().readAllBytes());
                Notification.show("Photo téléchargée avec succès!");
            } catch (IOException e) {
                Notification error = Notification.show("Problème rencontré lors du téléchargement de la photo.");
                error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        // Layout vertical pour le reste des champs
        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setSpacing(true);
        formLayout.add(titleField, genreComboBox, addNewGenreButton, isPartOfSeriesCheckbox, seriesComboBox, addNewSeriesButton, volumeNumberField, upload);

        // Boutons de navigation
        Button nextButton = new Button("Fin", e -> {
            if (titleField.isEmpty()) {
                Notification error = Notification.show("Veuillez renseigner le titre du livre.");
                error.addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            book.setTitle(titleField.getValue());
            book.setGenres(genreComboBox.getSelectedItems());
            if (isPartOfSeriesCheckbox.getValue()) {
                if (seriesComboBox.getValue() == null) {
                    Notification error = Notification.show("Veuillez sélectionner une série.");
                    error.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    return;
                }
                book.setSeries(seriesComboBox.getValue());

                if (volumeNumberField.isEmpty()) {
                    Notification error = Notification.show("Veuillez renseigner le numéro de tome.");
                    error.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    return;
                }
                book.setVolumeNumber(Integer.parseInt(volumeNumberField.getValue()));
            }
            onNext.run();
        });

        Button previousButton = new Button("Précédent", e -> onPrevious.run());

        HorizontalLayout navigationButtons = new HorizontalLayout(previousButton, nextButton);

        // Ajout des éléments dans la page
        add(formLayout, navigationButtons);
    }

    private void openAddGenreDialog(MultiSelectComboBox<Genre> genreComboBox, GenreService genreService){
        Dialog dialog = new Dialog();

        TextField newGenreNameField = new TextField("Nom du Genre");
        Button saveGenreButton = new Button("Sauvegarder", ev -> {
            String genreName = newGenreNameField.getValue();
            if (!genreName.isEmpty()) {
                Genre newGenre = new Genre(genreName);
                try {
                    genreService.save(newGenre);
                    genreComboBox.setItems(genreService.findAll());  // Mise à jour de la liste des genres
                    genreComboBox.select(newGenre);  // Sélection du nouveau genre
                    Notification success = Notification.show("Genre ajouté avec succès!");
                    success.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    dialog.close();
                } catch (GenreException e) {
                    Notification error = Notification.show(e.getMessage());
                    error.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            } else {
                Notification error = Notification.show("Le nom du genre est requis.");
                error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        // Bouton pour fermer la pop-up
        Button cancelButton = new Button("Annuler", e -> dialog.close());

        // Ajout des champs et des boutons dans le dialog
        VerticalLayout dialogLayout = new VerticalLayout(newGenreNameField, saveGenreButton, cancelButton);
        dialog.add(dialogLayout);
        dialog.open();
    }

    private void openAddSeriesDialog(ComboBox<Series> seriesComboBox, SeriesService seriesService){
        Dialog addSeriesDialog = new Dialog();
        TextField newSeriesNameField = new TextField("Nom de la Série");
        Button saveSeriesButton = new Button("Sauvegarder", ev -> {
            String seriesName = newSeriesNameField.getValue();
            if (!seriesName.isEmpty()) {
                Series newSeries = new Series(seriesName);
                seriesService.save(newSeries);
                seriesComboBox.setItems(seriesService.findAll()); // Mise à jour des séries
                seriesComboBox.setValue(newSeries); // Sélection de la nouvelle série
                Notification success = Notification.show("Série ajoutée avec succès!");
                success.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                addSeriesDialog.close();
            } else {
                Notification error = Notification.show("Le nom de la série est requis.");
                error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        Button cancelButton = new Button("Annuler", e -> addSeriesDialog.close());

        VerticalLayout dialogLayout = new VerticalLayout(newSeriesNameField, saveSeriesButton, cancelButton);
        addSeriesDialog.add(dialogLayout);
        addSeriesDialog.open();
    }

}
