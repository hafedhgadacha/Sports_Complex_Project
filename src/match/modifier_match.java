/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package match;

import arbitr.arbitre;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import equipement.Equipement;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
public class modifier_match implements Initializable {

    @FXML
    private TextField get_id_match;
    @FXML
    private TextField get_1_match;
    @FXML
    private TextField get_2_match;
    @FXML
    private Button annuler;
    @FXML
    private Button btn_modifier;
    @FXML
    private TextField get_arbitr_nom;
    @FXML
    private TextField get_resultat;
      private ObservableList<match> matchlist;
    public match selectmatch;

    /**
     * Initializes the controller class.
     */
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
    @FXML
    private Label tot;
    @FXML
    private TextField set_prix_latch;
   

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

         tableequipementutilisee.getItems().clear();
        fetchDataFromMySQL();
    }
private void btnSupprimer(ActionEvent event, Equipement selectedEquipement) {

          if (selectedEquipement != null) {
              System.out.println("euyyyyyyyyyyyyyyy");
              EquipementList.remove(selectedEquipement);
               deleteEquipementFromDatabase(selectedEquipement);
          }else{
              System.out.println("laaaaaaaaaaaaa");
          }
      }


private void deleteEquipementFromDatabase(Equipement selectedEquipement) {
               String a=String.valueOf(selectedEquipement.getequipement_id());
               int n=Integer.parseInt(String.valueOf(selectedEquipement.nbrUtiliseeProperty()));
              String id =String.valueOf(selectedEquipement.getequipement_id_match());
              String t =String.valueOf(selectedEquipement.getTypeMateriel());
    try (java.sql.Connection connection = complexe_sportif.database.getConnect()) {
        String query = "DELETE FROM Equipementmatch WHERE equipement_id_match = ? and id=?";
        try (java.sql.PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.setString(2, a);
            statement.executeUpdate();
            tableequipementutilisee.getItems().clear();
        fetchDataFromMySQL();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    try (java.sql.Connection connection = complexe_sportif.database.getConnect()) {

            String query = "SELECT nbrEnStock FROM equipement WHERE typeMateriel=?";
        try (java.sql.PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, t);
            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) { 
                    int nbre = res.getInt("nbrEnStock");
                    nbre += n;
                 
                             String nbre1 = String.valueOf(nbre);
                                 String   query1 = "UPDATE equipement SET  nbrEnStock=? WHERE typeMateriel=?";
                                       try ( java.sql.PreparedStatement stat = connection.prepareStatement(query1)) {
                                           stat.setString(1, nbre1);
                                           stat.setString(2, t);
                                           int rowsUpdated = stat.executeUpdate();
                                           if (rowsUpdated > 0) {
                                             showAlert(Alert.AlertType.INFORMATION, "Success", "Delete successful!");
                                           } else {
                                                       showAlert(Alert.AlertType.ERROR, "Error", "Delete failed!");
                                                   }
                                               }
                                           }} 
             Stage stage = (Stage) btn_modifier.getScene().getWindow();
        stage.close();}
                 }catch (SQLException e) {
                                               e.printStackTrace();
                                               showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while updating the database.");
                       }
}


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

private void openDetailsPage(Equipement selectedEquipement) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modifier_equipement_utilsee.fxml"));
        Parent root = loader.load();
        selectedEquipement.setequipement_id_match(selectmatch.getid());
                System.out.print(selectedEquipement.toString());

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
           

        // Update the table view after modification
        tableequipementutilisee.getItems().clear();
        fetchDataFromMySQL();
    } catch (IOException e) {
        e.printStackTrace();
    }
}



private void fetchDataFromMySQL() {
    String idmastr = get_id_match.getText();

    // Check if idmastr is not empty
    if (!idmastr.isEmpty()) {
        try {
            int idma = Integer.parseInt(idmastr);
            try (java.sql.Connection connection = complexe_sportif.database.getConnect()) {
                String query = "SELECT id, typeMateriel, nbrUtilisee, prix FROM Equipementmatch WHERE equipement_id_match = ?";
                try (java.sql.PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, idma);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            int equipement_id1 = resultSet.getInt("id");
                            String typeMaterie2 = resultSet.getString("typeMateriel");
                            int nbrUtilisee = resultSet.getInt("nbrUtilisee");
                            float prix1 = resultSet.getFloat("prix");
                            Equipement e = new Equipement(equipement_id1, typeMaterie2, nbrUtilisee, prix1, idma);
                            EquipementList.add(e);
                            
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            // Handle the case where idmastr is not a valid number
            e.printStackTrace();
        }
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
} 

    
    
    

    // TODO
        
 //public match selectedAthelete;
   
public void initializeData(ObservableList<match> matchlist, match selectmatch) {
    this.matchlist = matchlist;

    this.selectmatch = selectmatch;
    populateFields(selectmatch);
    

}

private void populateFields(match t) {
    get_id_match.setText(String.valueOf(t.getid()));
    chercher.setText(String.valueOf(t.getid()));
    get_1_match.setText(t.getNomEquipe1());
    get_2_match.setText(t.getNomEquipe2());
    get_arbitr_nom.setText(String.valueOf(t.getArbitre()));
    get_resultat.setText(t.getResultat());
}
    
    private void fetchDataFromMySQL1() {
        try (java.sql.Connection connection = complexe_sportif.database.getConnect()) {
            String query = "SELECT nom, prenom, type FROM arbitre";
            try (java.sql.PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String nom1 = resultSet.getString("nom");
                    String prenom1 = resultSet.getString("prenom");
                    String type1 = resultSet.getString("type");

                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    @FXML
    private void annuler(ActionEvent event) {
        Stage stage = (Stage) annuler.getScene().getWindow();
        stage.close();
    }

private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
}



    @FXML
    private void btn_modifier(ActionEvent event) {
          String idMatch = get_id_match.getText();
            String nomEquipe1 = get_1_match.getText();
            String nomEquipe2 = get_2_match.getText();
            String arbitre = get_arbitr_nom.getText();
            String resultat = get_resultat.getText();
            
        try (java.sql.Connection connection = complexe_sportif.database.getConnect()) {
            String query = "UPDATE matches SET  nomEquipe1=?, nomEquipe2=?, arbitre=? ,resultat=?  WHERE id=?";
            try (java.sql.PreparedStatement insertMatchStatement = connection.prepareStatement(query)) {
                insertMatchStatement.setString(1, nomEquipe1);
                        insertMatchStatement.setString(2, nomEquipe2);
                        insertMatchStatement.setString(3, arbitre);
                        insertMatchStatement.setString(4, resultat);
                        insertMatchStatement.setString(5, idMatch);

                int rowsUpdated = insertMatchStatement.executeUpdate();

                if (rowsUpdated > 0) {
                        showAlert("successfully","Match update successfully!");
                 int index = matchlist.indexOf(selectmatch);
        matchlist.set(index, selectmatch);

        // Close the stage or dialog
        Stage stage = (Stage) btn_modifier.getScene().getWindow();
        stage.close();} else { showAlert("ERROR", "Update failed!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
showAlert("ERROR", "Update failed!");        }
    }

    ////////////////////// 
    public float getprixeuipement()
    {return Float.parseFloat(set_prix_latch.getText());}
    
    ////////////////////////////////


    @FXML
    private void total(ActionEvent event) {
         float tote=Float.parseFloat(set_prix_latch.getText());
        String idmastr = get_id_match.getText();
        if (!idmastr.isEmpty()) {
        try {
            int idma = Integer.parseInt(idmastr);
            try (java.sql.Connection connection = complexe_sportif.database.getConnect()) {
                String query = "SELECT  nbrUtilisee, prix FROM Equipementmatch WHERE equipement_id_match = ?";
                try (java.sql.PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, idma);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            
                            int nbrUtilisee = resultSet.getInt("nbrUtilisee");
                            float prix1 = resultSet.getFloat("prix");
                            tote=tote+prix1*nbrUtilisee;
                            
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            // Handle the case where idmastr is not a valid number
            e.printStackTrace();
        }
    }
        tot.setText(String.valueOf(tote));
 
}

    }