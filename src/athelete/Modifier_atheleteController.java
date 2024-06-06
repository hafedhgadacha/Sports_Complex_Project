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

public class Modifier_atheleteController implements Initializable {
    
    private ObservableList<Athelete> atheleteList;
    private Athelete selectedAthelete;
    

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
    private Button btn_annuler_modif_jouer;
    @FXML
    private Button btn_modifier;
  public void initializeData(ObservableList<Athelete> atheleteList, Athelete selectedAthelete) {
        this.atheleteList = atheleteList;
        this.selectedAthelete = selectedAthelete;
        populateFields(selectedAthelete);}
      private void populateFields(Athelete athelete) {
          
    get_id_jouer.setText(String.valueOf(athelete.getid()));
    get_nom_jouer.setText(athelete.getNom());
    get_prenom_jouer.setText(athelete.getPrenom());
    get_num_mailoot_jouer.setText(String.valueOf(athelete.getNumMaillot()));
    get_age_jouer.setText(String.valueOf(athelete.getAge()));
    get_nom_equipe_jouer.setText(athelete.getNomEquipe());
    get_fililer_jouer.getSelectionModel().select(athelete.getfilliere());
    get_poste_jouer.getSelectionModel().select(athelete.getposte());
    get_capitaine_jouer.getSelectionModel().select(athelete.isCapitaine());
}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> filliere = FXCollections.observableArrayList("foot", "basket", "tennis");
        get_fililer_jouer.setItems(filliere);

        ObservableList<String> posteOptions = FXCollections.observableArrayList("Position 1", "Position 2");
        get_poste_jouer.setItems(posteOptions);

        ObservableList<String> capitaineOptions = FXCollections.observableArrayList("1", "0");
        get_capitaine_jouer.setItems(capitaineOptions);
    }

    @FXML
    private void annuler(ActionEvent event) {

         Stage stage = (Stage) btn_annuler_modif_jouer.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void btn_modifier(ActionEvent event) {
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
            String query = "UPDATE athelete SET nom=?, prenom=?, filliere=?, num_maillot=?, age=?, nom_equipe=?,capitaine=?, poste=?  WHERE id=?";
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

     
}
