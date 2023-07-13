package gui.reclamation;

import entite.Reclamation;
import service.ReclamationService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;

public class UserReclamationController {
    @FXML
    private TableView<Reclamation> table_view;
    @FXML
    private TableColumn<Reclamation, String> col_Email;
    @FXML
    private TableColumn<Reclamation, Date> col_date;
    @FXML
    private TableColumn<Reclamation, String> description;
    @FXML
    private TableColumn<Reclamation, String> col_Statut;
    @FXML
    private TableColumn<Reclamation, Void> col_Action;

    private ReclamationService reclamationService;

    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private TableColumn<Reclamation, String> col_Categorie;
    @FXML
    private Button refreshButton;

    @FXML
    private TableColumn<Reclamation, String> col_Reponse;

    @FXML
    private PieChart pieChart;

    @FXML
    private Label detailsLabel;

    public UserReclamationController() {
        reclamationService = new ReclamationService();
    }

    private ObservableList<PieChart.Data> pieChartData;

    public void initialize() {
        // Set cell value factories for table columns
        col_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_Statut.setCellValueFactory(new PropertyValueFactory<>("statut"));
        col_Categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        col_Reponse.setCellValueFactory(new PropertyValueFactory<>("Reponse"));

        categoryComboBox.getItems().addAll("Club", "Terrain", "Matériel", "Toutes");
        categoryComboBox.setOnAction(event -> filterByCategory());

        refreshButton.setOnAction(event -> refreshData());

        // Set up action column
        col_Action.setCellFactory(column -> new ButtonCell());

        // Set default value for the "Statut" column
        col_Statut.setCellFactory(column -> new TableCell<Reclamation, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.isEmpty()) {
                    setText(null);
                } else {
                    setText(item);
                }
                setAlignment(Pos.CENTER);
            }

            @Override
            public void updateIndex(int index) {
                super.updateIndex(index);
                if (index < 0 || index >= getTableView().getItems().size()) {
                    setText(null);
                } else {
                    Reclamation reclamation = getTableView().getItems().get(index);
                    if (reclamation == null || reclamation.getDescription() == null || reclamation.getDescription().isEmpty()) {
                        setText(null);
                    } else {
                        setText(reclamation.getStatut());
                    }
                }
            }
        });

        // Enable row selection and handle double-click event
        table_view.setRowFactory(tv -> {
            TableRow<Reclamation> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Reclamation selectedReclamation = row.getItem();
                    showDetails(selectedReclamation);
                }
            });
            return row;
        });

        // Populate table with data
        List<Reclamation> reclamationList = reclamationService.readAll();
        table_view.setItems(FXCollections.observableArrayList(reclamationList));

        // Créer une liste observable pour les données du graphique camembert
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Récupérer la liste de réclamations depuis le service
        reclamationList = reclamationService.readAll();

        // Créer une carte de fréquence pour les catégories de réclamations
        Map<String, Integer> categoryFrequencyMap = new HashMap<>();

        // Calculer la fréquence des catégories de réclamations
        for (Reclamation reclamation : reclamationList) {
            String category = reclamation.getCategorie();
            categoryFrequencyMap.put(category, categoryFrequencyMap.getOrDefault(category, 0) + 1);
        }

        // Ajouter les données du graphique camembert
        for (Map.Entry<String, Integer> entry : categoryFrequencyMap.entrySet()) {
            String category = entry.getKey();
            int frequency = entry.getValue();
            pieChartData.add(new PieChart.Data(category, frequency));
        }

        // Configurer le graphique camembert avec les données
        pieChart.setData(pieChartData);

        // Configurer un écouteur pour la sélection de la statistique dans le graphique camembert
        pieChart.getData().forEach(data -> {
            data.getNode().setOnMouseClicked(event -> {
                String category = data.getName();
                int frequency = (int) data.getPieValue();
                String details = category + ": " + frequency + " réclamations";
                detailsLabel.setText(details);
            });
        });
    }

    private void showDetails(Reclamation reclamation) {
        DetailsDialog dialog = new DetailsDialog(reclamation);
        dialog.showAndWait();
    }

    private void filterByCategory() {
        String selectedCategory = categoryComboBox.getValue();

        if (selectedCategory == null || selectedCategory.isEmpty() || selectedCategory.equals("Toutes")) {
            // Afficher toutes les réclamations si aucune catégorie n'est sélectionnée ou si "Toutes" est sélectionnée
            List<Reclamation> reclamationList = reclamationService.readAll();
            table_view.setItems(FXCollections.observableArrayList(reclamationList));
        } else {
            // Filtrer les réclamations en fonction de la catégorie sélectionnée
            List<Reclamation> filteredList = table_view.getItems().stream()
                    .filter(reclamation -> reclamation.getCategorie().equals(selectedCategory))
                    .collect(Collectors.toList());
            table_view.setItems(FXCollections.observableArrayList(filteredList));
        }
    }

    private void refreshData() {
        table_view.getItems().clear();
        List<Reclamation> reclamationList = reclamationService.readAll();
        table_view.getItems().addAll(reclamationList);

        // Mettre à jour le graphique camembert avec les nouvelles données
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Calculer la fréquence des catégories de réclamations
        Map<String, Integer> categoryFrequencyMap = new HashMap<>();
        for (Reclamation reclamation : reclamationList) {
            String category = reclamation.getCategorie();
            categoryFrequencyMap.put(category, categoryFrequencyMap.getOrDefault(category, 0) + 1);
        }

        // Ajouter les données misesà jour au graphique camembert
        for (Map.Entry<String, Integer> entry : categoryFrequencyMap.entrySet()) {
            String category = entry.getKey();
            int frequency = entry.getValue();
            pieChartData.add(new PieChart.Data(category, frequency));
        }

        // Mettre à jour les données du graphique camembert
        pieChart.setData(pieChartData);
    }

    private class ButtonCell extends TableCell<Reclamation, Void> {

        private final Button updateButton = new Button("Réponse");
        private final Button deleteButton = new Button("Supprimer");

        ButtonCell() {
            updateButton.setStyle("-fx-background-color: #378b29; -fx-text-fill: white;");
            deleteButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white;");

            updateButton.setOnAction(event -> {
                Reclamation reclamation = getTableView().getItems().get(getIndex());
                UpdateDialog dialog = new UpdateDialog(reclamation);
                dialog.showAndWait();
                if (dialog.isSuccess()) {
                    reclamationService.update(reclamation);
                    List<Reclamation> reclamationList = reclamationService.readAll();
                    table_view.getItems().clear();
                    table_view.getItems().addAll(reclamationList);
                    System.out.println("Réclamation mise à jour avec succès !");
                }
            });

            deleteButton.setOnAction(event -> {
                Reclamation reclamation = getTableView().getItems().get(getIndex());
                DeleteDialog dialog = new DeleteDialog(reclamation);
                dialog.showAndWait();
                if (dialog.isConfirmed()) {
                    reclamationService.delete(reclamation.getId());
                    getTableView().getItems().remove(reclamation);
                    System.out.println("Réclamation supprimée avec succès !");
                }
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                HBox buttonBox = new HBox(5);
                buttonBox.setAlignment(Pos.CENTER);
                buttonBox.getChildren().addAll(updateButton, deleteButton);
                setGraphic(buttonBox);
            }
        }
    }
}
