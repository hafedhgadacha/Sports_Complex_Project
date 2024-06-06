package equipement;
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

/**
 * FXML Controller class
 *
 * @author MSI PC
 */
public class ajouter_equipementstock implements Initializable {



    @FXML
    private Button annuler;
    
    

    @FXML
    private TextField get_typeMateriel;
    @FXML
    private TextField get_nbrEnStock;
    @FXML
    private TextField get_prix;
    @FXML
    private Button btn_ajouterstock;
    @FXML
    private TextField get_equipement_id;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
    private void btn_ajouterstock(ActionEvent event) {
    String typeMateriel = get_typeMateriel.getText();
    String nbrEnStock = get_nbrEnStock.getText();
    String id = get_equipement_id.getText();
    String prix = get_prix.getText();

    try (Connection connection = complexe_sportif.database.getConnect()) {
        String query = "INSERT INTO Equipement ( equipement_id,nbrEnStock, prix,typeMateriel) VALUES ( ?,  ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.setString(2, nbrEnStock);
            statement.setString(3, prix);
            statement.setString(4, typeMateriel);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Insert successful!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Insert failed!");
            }
        }Stage stage = (Stage) btn_ajouterstock.getScene().getWindow();
        stage.close();
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while updating the database.");
    }
    }

}