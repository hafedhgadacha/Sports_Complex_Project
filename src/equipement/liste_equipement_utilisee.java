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
import match.match;
import match.modifier_match;
import match.tableau_matchecontroller;
import shared.SharedDataModel;

/**
 * FXML Controller class
 *
 * @author MSI PC
 */
public class liste_equipement_utilisee implements Initializable {

    @FXML
    private Button ajouter;
    @FXML
    private TextField chercher;
    @FXML
    private TableColumn<Equipement, Integer> equipement_id;
    @FXML
    private TableColumn<Equipement, String> typeMateriel;
    
    @FXML
    private TableColumn<Equipement, Float> prix;
    
    @FXML
    private TableColumn<Equipement, Integer> nbr_utilisee;
    @FXML
    private TableView<Equipement> tableequipementutilisee;

    
    private final ObservableList<Equipement> EquipementList = FXCollections.observableArrayList();
   
    
        
private SharedDataModel sharedDataModel;

    public void setSharedDataModel(SharedDataModel sharedDataModel) {
        this.sharedDataModel = sharedDataModel;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
         equipement_id.setCellValueFactory(cellData -> cellData.getValue().equipement_idProperty().asObject());
        typeMateriel.setCellValueFactory(cellData -> cellData.getValue().typeMaterielProperty());
        nbr_utilisee.setCellValueFactory(cellData -> cellData.getValue().nbrUtiliseeProperty().asObject());
        prix.setCellValueFactory(cellData -> cellData.getValue().PrixProperty().asObject());
        tableequipementutilisee.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && !tableequipementutilisee.getSelectionModel().isEmpty()) {
            Equipement selectedEquipement = tableequipementutilisee.getSelectionModel().getSelectedItem();

            openDetailsPage(selectedEquipement);
        }
    });
          TableColumn<Equipement, Void> colAction = new TableColumn<>("supprimer");

                // Create a cell value factory to set the button in each row
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


    tableequipementutilisee.getColumns().add(colAction);
    tableequipementutilisee.setItems(EquipementList);

         
        fetchDataFromMySQL();
    }
private void btnSupprimer(ActionEvent event, Equipement selectedEquipement) {

          if (selectedEquipement != null) {
              System.out.println("euyyyyyyyyyyyyyyy");
              EquipementList.remove(selectedEquipement);

              int a=selectedEquipement.getequipement_id();
              String b =String.valueOf(a);
              deleteEquipementFromDatabase(b);
          }else{
              System.out.println("laaaaaaaaaaaaa");
          }
      }


private void deleteEquipementFromDatabase(String id) {
   

    try (Connection connection = complexe_sportif.database.getConnect()) {
        String query = "DELETE FROM Equipementmatch WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.executeUpdate();
            tableequipementutilisee.getItems().clear();
        fetchDataFromMySQL();
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately, e.g., show an error message
    }
}



private void openDetailsPage(Equipement selectedEquipement) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modifier_equipement_utilsee.fxml"));
        Parent root = loader.load();

        equipement.Modifier_equipementUtilsee modifierController = loader.getController();
        modifierController.initializeData(EquipementList, selectedEquipement);
        
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
           
        tableequipementutilisee.getItems().clear();
        fetchDataFromMySQL();
    } catch (IOException e) {
        e.printStackTrace();
    }
}




    private void fetchDataFromMySQL() {
       
        String idmastr = sharedDataModel.getSharedText();
        int idma =Integer.parseInt(idmastr);
        try (Connection connection = complexe_sportif.database.getConnect()) {
           String query = "SELECT id, typeMateriel, nbrUtilisee, prix FROM Equipementmatch WHERE equipement_id_match = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, idmastr);  
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int equipement_id1 = resultSet.getInt("id");
                        String typeMaterie2 = resultSet.getString("typeMateriel");
                        int nbrUtilisee = resultSet.getInt("nbrUtilisee");
                        float prix1 = resultSet.getFloat("prix");
                         Equipement e = new Equipement(equipement_id1,typeMaterie2,nbrUtilisee,prix1,idma);
                            EquipementList.add(e);
                    }
                }
            }
                // Handle the exception as needed
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
                    String.valueOf(equipement.getequipement_id_match()).contains(searchText) ||
                    String.valueOf(equipement.getNbrUtilisee()).contains(searchText) ||
                    String.valueOf(equipement.getPrix()).contains(searchText)) {
                    filteredList.add(equipement);
                }
            }

            tableequipementutilisee.setItems(filteredList);
}

        private double x=0;
        private double y=0;
    @FXML
     private void ajouter(ActionEvent event) throws IOException {
           Parent root = FXMLLoader.load(getClass().getResource("ajouter_utilse.fxml"));
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
           
           
        tableequipementutilisee.getItems().clear();
        fetchDataFromMySQL();
} }