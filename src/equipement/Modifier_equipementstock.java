/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package equipement;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MSI PC
 */
public class Modifier_equipementstock implements Initializable {
    
    
    private ObservableList<Equipement> Equipementlist;
    private Equipement selectedEquipement;
    @FXML
    private Button btn_modifier;
    @FXML
    private TextField get_typeMateriel;
    @FXML
    private TextField get_nbrEnStock;
    @FXML
    private TextField get_prix;
     @FXML
    private Button annuler;
    @FXML
    private TextField get_equipement_id;
     
       @FXML
    private void annuler(ActionEvent event) {
 Stage stage = (Stage) annuler.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML
        private void btn_modifier(ActionEvent event) {
          String prix = get_prix.getText();
          String id = get_equipement_id.getText();
        String typeMateriel = get_typeMateriel.getText();
        String nbrEnStock = get_nbrEnStock.getText();

        try (Connection connection = complexe_sportif.database.getConnect()) {
            String query = "UPDATE Equipement SET  nbrEnStock=?,prix=?  WHERE typeMateriel=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, nbrEnStock);
                statement.setString(2, prix);
                statement.setString(3, typeMateriel);
                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Update successful!");
                 int index = Equipementlist.indexOf(selectedEquipement);
        Equipementlist.set(index, selectedEquipement);
        Stage stage = (Stage) btn_modifier.getScene().getWindow();
        stage.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Update failed!");
                }
            }
        } catch (SQLException e) {
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
  public void initializeData(ObservableList<Equipement> Equipementlist, Equipement selectedEquipement) {
        this.Equipementlist = Equipementlist;
        this.selectedEquipement = selectedEquipement;
        populateFields(selectedEquipement);}
  
  
      private void populateFields(Equipement t) {
    get_typeMateriel.setText(t.getTypeMateriel());
    get_nbrEnStock.setText(String.valueOf(t.getNbrEnStock()));
    get_equipement_id.setText(String.valueOf(t.getequipement_id()));
    get_prix.setText(String.valueOf(t.getPrix()));
    
}  }