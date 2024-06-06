/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package arbitr;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Modifier_arbitreController implements Initializable {
    
    private ObservableList<arbitre> atheleteList;
    private arbitre selectedAthelete;
    
    @FXML
     private Button btn_annuler_modif_jouer;
    @FXML
    private Label type;
    
      public void initializeData(ObservableList<arbitre> atheleteList, arbitre selectedAthelete) {
        this.atheleteList = atheleteList;
        this.selectedAthelete = selectedAthelete;
        populateFields(selectedAthelete);}
      private void populateFields(arbitre t) {
    get_id_arbitre.setText(String.valueOf(t.getid()));
    get_nom_arbitre.setText(t.getNom());
    get_prenom_arbitre.setText(t.getPrenom());
    get_age_arbitre.setText(String.valueOf(t.getAge()));
    get_type.setText(t.getType());
}

    @FXML
    private TextField get_id_arbitre;
    @FXML
    private TextField get_nom_arbitre;
    @FXML
    private TextField get_prenom_arbitre;
    @FXML
    private TextField get_age_arbitre;
    @FXML
    private TextField get_type;
    
    
    @FXML
    private Button btn_modifier;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btn_annuler_modif_jouer(ActionEvent event) {
 Stage stage = (Stage) btn_annuler_modif_jouer.getScene().getWindow();
        stage.close();
    }
     

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

   

      
    @FXML
    private void btn_modifier(ActionEvent event) {
         String id = get_id_arbitre.getText();
        String nom = get_nom_arbitre.getText();
        String prenom = get_prenom_arbitre.getText();
        String age = get_age_arbitre.getText();
        String type = get_type.getText();

        try (Connection connection = complexe_sportif.database.getConnect()) {
            String query = "UPDATE arbitre SET nom=?, prenom=?, age=?, type=?  WHERE id=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, nom);
                statement.setString(2, prenom);
                statement.setString(3, age);
                statement.setString(4, type);
                statement.setString(5, id);

                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Update successful!");
                 int index = atheleteList.indexOf(selectedAthelete);
        atheleteList.set(index, selectedAthelete);

        // Close the stage or dialog
        Stage stage = (Stage) btn_modifier.getScene().getWindow();
        stage.close();} else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Update failed!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while updating the database.");
        }
        
        
    }
 

   

}
