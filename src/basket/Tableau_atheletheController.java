/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package basket;


/**
 * FXML Controller class
 *
 * @author MSI PC
*/
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Tableau_atheletheController implements Initializable {

    @FXML
    private TextField chercher;
    @FXML
    private TableColumn<tennise, Integer> id;
    @FXML
    private TableColumn<tennise, String> nom;
    @FXML
    private TableColumn<tennise, String> prenom;
 
    @FXML
    private TableColumn<tennise, Integer> age;
    @FXML
    private TableColumn<tennise, String> nom_equipe;
    @FXML

    private final ObservableList<tennise> atheleteList = FXCollections.observableArrayList();
   
    
    @FXML
    private TableView<tennise> tableView;
  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        prenom.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        age.setCellValueFactory(cellData -> cellData.getValue().ageProperty().asObject());
        nom_equipe.setCellValueFactory(cellData -> cellData.getValue().nomEquipeProperty());
        tableView.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && !tableView.getSelectionModel().isEmpty()) {
            // Get the selected item
            tennise selectedAthelete = tableView.getSelectionModel().getSelectedItem();

            // Open a new page or pop-up with the details of the selected item
            openDetailsPage(selectedAthelete);
        }
    });
        tableView.setItems(atheleteList);
         
        fetchDataFromMySQL();
    }
private void openDetailsPage(tennise selectedAthelete) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Modifier_athelete.fxml"));
        Parent root = loader.load();

        basket.Modifier_atheleteController modifierController = loader.getController();
        modifierController.initializeData(atheleteList, selectedAthelete);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Modifier Joueur");
        stage.setScene(new Scene(root));
        stage.showAndWait();

        // Update the table view after modification
 tableView.getItems().clear();
        fetchDataFromMySQL();    } catch (IOException e) {
        e.printStackTrace();
    }
}





    private void fetchDataFromMySQL() {
        String url = "jdbc:mysql://complexe_sportif";
        String user = "root";
        String password = "";

        try (Connection connection = complexe_sportif.database.getConnect()) {
            String query = "SELECT * FROM athelete";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int id1 = resultSet.getInt("id");
                    String nom1 = resultSet.getString("nom");
                    String prenom1 = resultSet.getString("prenom");
                    int age1 = resultSet.getInt("age");
                    String nom_equipe1 = resultSet.getString("nom_equipe");

                    tennise athelete = new tennise(id1, nom1, prenom1,  age1, nom_equipe1);
                    atheleteList.add(athelete);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  

    @FXML
    private void chercher(ActionEvent event) {
        String searchText = chercher.getText().toLowerCase();

        ObservableList<tennise> filteredList = FXCollections.observableArrayList();

        for (tennise athelete : atheleteList) {
            if (String.valueOf(athelete.getid()).contains(searchText) ||
                athelete.getNom().toLowerCase().contains(searchText) ||
                athelete.getPrenom().toLowerCase().contains(searchText) ||
                String.valueOf(athelete.getAge()).contains(searchText) ||
                athelete.getNomEquipe().toLowerCase().contains(searchText) ){
                filteredList.add(athelete);
            }
        }

        tableView.setItems(filteredList);
    }
}
