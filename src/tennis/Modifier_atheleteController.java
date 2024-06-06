/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tennis;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Modifier_atheleteController implements Initializable {
    
    private ObservableList<tennise> atheleteList;
    private tennise selectedAthelete;
      public void initializeData(ObservableList<tennise> atheleteList, tennise selectedAthelete) {
        this.atheleteList = atheleteList;
        this.selectedAthelete = selectedAthelete;
        populateFields(selectedAthelete);}
      private void populateFields(tennise t) {
    get_id_jouer.setText(String.valueOf(t.getid()));
    get_nom_jouer.setText(t.getNom());
    get_prenom_jouer.setText(t.getPrenom());
    get_age_jouer.setText(String.valueOf(t.getAge()));
    get_nom_equipe_jouer.setText(t.getNomEquipe());
}

    @FXML
    private TextField get_id_jouer;
    @FXML
    private TextField get_nom_jouer;
    @FXML
    private TextField get_prenom_jouer;
    @FXML
    private TextField get_age_jouer;
    @FXML
    private TextField get_nom_equipe_jouer;
    
    
    @FXML
    private Button btn_modifier;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void annuler(ActionEvent event) {

    }
    
    @FXML
    private void btn_modifier(ActionEvent event) {
        String id = get_id_jouer.getText();
        String nom = get_nom_jouer.getText();
        String prenom = get_prenom_jouer.getText();
        String age = get_age_jouer.getText();
        String nomEquipe = get_nom_equipe_jouer.getText();

        try (Connection connection = complexe_sportif.database.getConnect()) {
            String query = "UPDATE tennis SET nom=?, prenom=?, age=?, nom_equipe=?  WHERE id=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, nom);
                statement.setString(2, prenom);
                statement.setString(3, age);
                statement.setString(4, nomEquipe);
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

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private Button btn_ajouter;
    @FXML
private void btn_ajouter(ActionEvent event) {
    String id = get_id_jouer.getText();
    String nom = get_nom_jouer.getText();
    String prenom = get_prenom_jouer.getText();
    String age = get_age_jouer.getText();
    String nomEquipe = get_nom_equipe_jouer.getText();

    try (Connection connection = complexe_sportif.database.getConnect()) {
        String query = "INSERT INTO athelete (nom, prenom, age, nom_equipe,  id) VALUES (  ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nom);
            statement.setString(2, prenom);
            statement.setString(3, age);
            statement.setString(4, nomEquipe);
            statement.setString(6, id);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Insert successful!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Insert failed!");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while updating the database.");
    }
}

}
