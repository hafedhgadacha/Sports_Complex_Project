/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package match;
import javafx.collections.ObservableList;
import arbitr.arbitre;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MSI PC
 */
public class ajouter_match implements Initializable {

    @FXML
    private TextField get_id_match;
    @FXML
    private TextField get_1_match;
    @FXML
    private TextField get_2_match;
    @FXML
    private Button annuler;
    @FXML
    private TextField get_arbitr_nom;
    @FXML
    private TableColumn<arbitre, String> prenom;
    @FXML
    private TableColumn<arbitre, String> nom;
    @FXML
    private TableColumn<arbitre, String> type;
    private final ObservableList<arbitre> arbitreList = FXCollections.observableArrayList();
    @FXML
    private TextField get_resultat;
    @FXML
    private TableView<arbitre> tableView;
   
    
    @FXML
    private Button btn_ajouter;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    nom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
    prenom.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
    type.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
    
    tableView.setItems(arbitreList);
    fetchDataFromMySQL();
    }    

    private void fetchDataFromMySQL() {
        try (java.sql.Connection connection = complexe_sportif.database.getConnect()) {
            String query = "SELECT nom, prenom, type FROM arbitre";
            try (java.sql.PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String nom1 = resultSet.getString("nom");
                    String prenom1 = resultSet.getString("prenom");
                    String type1 = resultSet.getString("type");

                    arbitre athelete = new arbitre( nom1, prenom1, type1 );
                    arbitreList.add(athelete);
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
        private void btn_ajouter(ActionEvent event) {
            String idMatch = get_id_match.getText();
            String nomEquipe1 = get_1_match.getText();
            String nomEquipe2 = get_2_match.getText();
            String arbitre = get_arbitr_nom.getText();
            String resultat = get_resultat.getText();
            if (idMatch.isEmpty() || nomEquipe1.isEmpty() || nomEquipe2.isEmpty() || arbitre.isEmpty() ) {
               showAlert("Error", "All fields must be filled in.");
                return;
            }

            try (Connection connection = (Connection) complexe_sportif.database.getConnect()) {
                String insertMatchQuery = "INSERT INTO matches ( nomEquipe1, nomEquipe2, arbitre,resultat,id) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement insertMatchStatement = (PreparedStatement) connection.prepareStatement(insertMatchQuery)) {
                        insertMatchStatement.setString(1, nomEquipe1);
                        insertMatchStatement.setString(2, nomEquipe2);
                        insertMatchStatement.setString(3, arbitre);
                        insertMatchStatement.setString(4, resultat);
                        insertMatchStatement.setString(5, idMatch);
                        
                        int rowsAffected = insertMatchStatement.executeUpdate();
                        if (rowsAffected > 0) {
                        showAlert("successfully","Match added successfully!");
                        } else {
                       showAlert("Error", "Failed to add match.");
                        }
                         Stage stage = (Stage) btn_ajouter.getScene().getWindow();
        stage.close();
                    }
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                    }

}
