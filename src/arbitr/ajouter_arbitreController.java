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

public class ajouter_arbitreController implements Initializable {
    
    
    @FXML
    private Button annuler;
    @FXML
        private Button btn_ajouter;
      

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
    
    
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void annuler(ActionEvent event) {
 Stage stage = (Stage) annuler.getScene().getWindow();
        stage.close();
    }
     

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

   

    @FXML
    private void btn_ajouter(ActionEvent event) {
    String id = get_id_arbitre.getText();
    String nom = get_nom_arbitre.getText();
    String prenom = get_prenom_arbitre.getText();
    String age = get_age_arbitre.getText();
    String type = get_type.getText();

    try (Connection connection = complexe_sportif.database.getConnect()) {
        String query = "INSERT INTO arbitre (nom, prenom, age, type,id) VALUES (  ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nom);
            statement.setString(2, prenom);
            statement.setString(3, age);
            statement.setString(4, type);
            statement.setString(5, id);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Insert successful!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Insert failed!");
            }
        } Stage stage = (Stage) btn_ajouter.getScene().getWindow();
        stage.close();
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while updating the database.");
    }
    }
 
      

   

}
