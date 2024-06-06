/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package equipement;
import match.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import shared.SharedDataModel;

/**
 * FXML Controller class
 *
 * @author MSI PC
 */
public class ajouter_utilsee implements Initializable {

    @FXML
    private TextField get_typeMateriel;
    @FXML
    private TextField get_nbrUtilisee;
    @FXML
    private TextField get_prix;
    @FXML
    private Button annuler;
    @FXML
    private Button btn_ajouterstock;
    @FXML
    private TextField get_equipement_id;
    @FXML
    private TextField get_match_id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
   @FXML
    private void annuler(ActionEvent event) {
 Stage stage = (Stage) annuler.getScene().getWindow();
        stage.close();
    }
    
    
@FXML
    private void btn_ajouterstock(ActionEvent event) throws SQLException {
    String typeMateriel = get_typeMateriel.getText();
    String nbrUtilisee = get_nbrUtilisee.getText();
    String id = get_equipement_id.getText();
    String prix = get_prix.getText();
       String idmastr =        get_match_id.getText();
        System.out.print(idmastr);

           try (Connection connection = complexe_sportif.database.getConnect()) {
    String querySelect = "SELECT nbrEnStock FROM equipement WHERE typeMateriel=?";
    try (PreparedStatement selectStatement = connection.prepareStatement(querySelect)) {
        selectStatement.setString(1, typeMateriel);

        try (ResultSet res = selectStatement.executeQuery()) {
            if (res.next()) {
                int nbre = res.getInt("nbrEnStock");
                nbre -= Integer.parseInt(get_nbrUtilisee.getText());

                if (nbre < 0) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Update failed! Because of the stock");
                } else {
                    String nbre1 = String.valueOf(nbre);

                    String queryUpdate = "UPDATE equipement SET nbrEnStock=? WHERE typeMateriel=?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(queryUpdate)) {
                        updateStatement.setString(1, nbre1);
                        updateStatement.setString(2, typeMateriel);

                        int rowsUpdated = updateStatement.executeUpdate();

                        if (rowsUpdated > 0) {
                            showAlert(Alert.AlertType.INFORMATION, "Success", "Update successful!");

                            String queryInsert = "INSERT INTO Equipementmatch (equipement_id_match, id, nbrUtilisee, prix, typeMateriel) VALUES (?, ?, ?, ?, ?)";
                            try (PreparedStatement insertStatement = connection.prepareStatement(queryInsert)) {
                                insertStatement.setString(1, idmastr);
                                insertStatement.setString(2, id);
                                insertStatement.setString(3, nbrUtilisee);
                                insertStatement.setString(4, prix);
                                insertStatement.setString(5, typeMateriel);

                                int rowsUpdatedInserte = insertStatement.executeUpdate();

                                if (rowsUpdatedInserte > 0) {
                                    showAlert(Alert.AlertType.INFORMATION, "Success", "Insert successful!");
                                } else {
                                    showAlert(Alert.AlertType.ERROR, "Error", "Insert failed!");
                                }Stage stage = (Stage) btn_ajouterstock.getScene().getWindow();
        stage.close();
                            }
                        }
                    }
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "No matching records found in equipement table.");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while updating the database.");
    }
}
}}

    
            
        
    

