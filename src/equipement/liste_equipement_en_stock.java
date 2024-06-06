/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package equipement;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author MSI PC
 */
public class liste_equipement_en_stock implements Initializable {

     @FXML
     private TextField chercher;
    @FXML
    private TableColumn<Equipement, String> typeMateriel;
    
    @FXML
    private TableColumn<Equipement, Float> prix;
    
    @FXML
    private TableColumn<Equipement, Integer> nbrEnStock;
    
    private final ObservableList<Equipement> EquipementList = FXCollections.observableArrayList();
   
    
    @FXML
    private TableView<Equipement> tableequipementstock;
    @FXML
    private Button ajouter;
    @FXML
    private TableColumn<Equipement, Integer> id;
   

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         id.setCellValueFactory(cellData -> cellData.getValue().equipement_idProperty().asObject());
        typeMateriel.setCellValueFactory(cellData -> cellData.getValue().typeMaterielProperty());
        nbrEnStock.setCellValueFactory(cellData -> cellData.getValue().nbrEnStockProperty().asObject());
        prix.setCellValueFactory(cellData -> cellData.getValue().PrixProperty().asObject());
        tableequipementstock.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && !tableequipementstock.getSelectionModel().isEmpty()) {
            Equipement selectedEquipement = tableequipementstock.getSelectionModel().getSelectedItem();

            openDetailsPage(selectedEquipement);
        }
    });
          TableColumn<Equipement, Void> colAction = new TableColumn<>("supprimer");

               colAction.setCellFactory(param -> new TableCell<>() {
                private final javafx.scene.control.Button deleteButton = new javafx.scene.control.Button("Supprimer");

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || getTableRow().getItem() == null) {
                        setGraphic(null);
                    } else {
                        Equipement selectedAthelete = (Equipement) getTableRow().getItem();
                           deleteButton.getStyleClass().add("deleteButton");
                         deleteButton.setOnAction(event -> btnSupprimer(event, selectedAthelete));
                        setGraphic(deleteButton);
                    }
                }
            });


    tableequipementstock.getColumns().add(colAction);
    tableequipementstock.setItems(EquipementList);

         
        fetchDataFromMySQL();
    }
private void btnSupprimer(ActionEvent event, Equipement selectedEquipement) {

          if (selectedEquipement != null) {
              System.out.println("euyyyyyyyyyyyyyyy");
              EquipementList.remove(selectedEquipement);

              // Remove from database
              deleteEquipementFromDatabase(selectedEquipement.getTypeMateriel());
          }else{
              System.out.println("laaaaaaaaaaaaa");
          }
      }


private void deleteEquipementFromDatabase(String typeMateriel) {
   

    try (Connection connection = complexe_sportif.database.getConnect()) {
        String query = "DELETE FROM equipement WHERE typeMateriel = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, typeMateriel);
            statement.executeUpdate();
            tableequipementstock.getItems().clear();
        fetchDataFromMySQL();
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately, e.g., show an error message
    }
}


private double x=0;
    private double y=0;
    
    private void openDetailsPage(Equipement selectedEquipement) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modifier_equipement_stock.fxml"));
        Parent root = loader.load();

        equipement.Modifier_equipementstock modifierController = loader.getController();
        modifierController.initializeData(EquipementList, selectedEquipement);

        Stage stage = new Stage();
         root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
            stage.setOpacity(.8);
        });

        root.setOnMouseReleased((MouseEvent event) -> {
            stage.setOpacity(1);
        });

        stage.initStyle(StageStyle.TRANSPARENT);          
        stage.setScene(new Scene(root));
        stage.showAndWait();

         tableequipementstock.getItems().clear();
        fetchDataFromMySQL();
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    private void fetchDataFromMySQL() {
       

        try (Connection connection = complexe_sportif.database.getConnect()) {
            String query = "SELECT equipement_id,typeMateriel,nbrEnStock,prix FROM Equipement";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
    

                while (resultSet.next()) {
                    String typeMateriel1 = resultSet.getString("typeMateriel");
                    int nbrEnStock1 = resultSet.getInt("nbrEnStock");
                    int id1 = resultSet.getInt("equipement_id");
                    float prix1 = resultSet.getFloat("prix");

                    Equipement e = new Equipement(id1,typeMateriel1, nbrEnStock1, prix1);
                    EquipementList.add(e);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  

    @FXML
        private void chercher(ActionEvent event) {
            String searchText = chercher.getText().toLowerCase();

            ObservableList<Equipement> filteredList = FXCollections.observableArrayList();

            for (Equipement equipement : EquipementList) {
                if (equipement.getTypeMateriel().toLowerCase().contains(searchText) ||
                    String.valueOf(equipement.getNbrEnStock()).contains(searchText) ||
                    String.valueOf(equipement.getNbrUtilisee()).contains(searchText) ||
                    String.valueOf(equipement.getPrix()).contains(searchText)) {
                    filteredList.add(equipement);
                }
            }

            tableequipementstock.setItems(filteredList);
}

    @FXML
     private void ajouter(ActionEvent event) throws IOException {
           Parent root = FXMLLoader.load(getClass().getResource("ajouter_stock.fxml"));
            Stage stage = new Stage();
         root.setOnMousePressed((MouseEvent eventa) -> {
            x = eventa.getSceneX();
            y = eventa.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent eventa) -> {
            stage.setX(eventa.getScreenX() - x);
            stage.setY(eventa.getScreenY() - y);
            stage.setOpacity(.8);
        });

        root.setOnMouseReleased((MouseEvent eventa) -> {
            stage.setOpacity(1);
        });

        stage.initStyle(StageStyle.TRANSPARENT);          
        stage.setScene(new Scene(root));
        stage.showAndWait();
           
        tableequipementstock.getItems().clear();
        fetchDataFromMySQL();
}    }