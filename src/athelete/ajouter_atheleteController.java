package athelete;

import javafx.scene.control.Alert;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ajouter_atheleteController implements Initializable {
    
     @FXML
    private TextField get_id_jouer;
    @FXML
    private TextField get_nom_jouer;
    @FXML
    private TextField get_prenom_jouer;
    @FXML
    private TextField get_num_mailoot_jouer;
    @FXML
    private TextField get_age_jouer;
    @FXML
    private TextField get_nom_equipe_jouer;
    @FXML
    private ComboBox<String> get_fililer_jouer;
    @FXML
    private ComboBox<String> get_poste_jouer;
    @FXML
    private ComboBox<String> get_capitaine_jouer;
     @FXML
    private Button annuler; 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> filliere = FXCollections.observableArrayList("foot", "basket", "tennis");
        get_fililer_jouer.setItems(filliere);

            ObservableList<String> posteOptions = FXCollections.observableArrayList(" defenseur","gardien","pivot","regiseur" ,"attaquant");
        get_poste_jouer.setItems(posteOptions);

        ObservableList<String> capitaineOptions = FXCollections.observableArrayList("1", "0");
        get_capitaine_jouer.setItems(capitaineOptions);
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
    private Button btn_ajouter;
    
    @FXML
private void btn_ajouter(ActionEvent event) {
    String id = get_id_jouer.getText();
    String nom = get_nom_jouer.getText();
    String prenom = get_prenom_jouer.getText();
    String numMaillot = get_num_mailoot_jouer.getText();
    String age = get_age_jouer.getText();
    String nomEquipe = get_nom_equipe_jouer.getText();
    String filliere = get_fililer_jouer.getSelectionModel().getSelectedItem();
    String poste = get_poste_jouer.getSelectionModel().getSelectedItem();
    String capitaine = get_capitaine_jouer.getSelectionModel().getSelectedItem();

    try (Connection connection = complexe_sportif.database.getConnect()) {
        String query = "INSERT INTO athelete (nom, prenom, filliere, num_maillot, age, nom_equipe, capitaine, poste, id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nom);
            statement.setString(2, prenom);
            statement.setString(3, filliere);
            statement.setString(4, numMaillot);
            statement.setString(5, age);
            statement.setString(6, nomEquipe);
            statement.setString(7, capitaine);
            statement.setString(8, poste);
            statement.setString(9, id);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Insert successful!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Insert failed!");
            }  Stage stage = (Stage) btn_ajouter.getScene().getWindow();
        stage.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while updating the database.");
    }
}

}
