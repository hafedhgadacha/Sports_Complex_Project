/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package match;

import athelete.Modifier_atheleteController;
import equipement.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author MSI PC
 */
public class tableau_matchecontroller implements Initializable {

    @FXML
    private TextField chercher;
    @FXML
    private TableView<match> tableViewmatch;
    @FXML
    private TableColumn<match, Integer> id;
    @FXML
    private TableColumn<match, String> equipe1;
    @FXML
    private TableColumn<match, String> resultat;
    @FXML
    private TableColumn<match, String> equipe2;
    @FXML
    private TableColumn<match, String> arbitre;
    private final ObservableList<match> atheleteList = FXCollections.observableArrayList();
    @FXML
    private Button btn_ajouter;

       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
     equipe1.setCellValueFactory(cellData -> cellData.getValue().nomEquipe1Property());
     equipe2.setCellValueFactory(cellData -> cellData.getValue().nomEquipe2Property());
     arbitre.setCellValueFactory(cellData -> cellData.getValue().arbitreProperty());
     resultat.setCellValueFactory(cellData -> cellData.getValue().resultatProperty());
    tableViewmatch.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && !tableViewmatch.getSelectionModel().isEmpty()) {
             match selectedAthelete = tableViewmatch.getSelectionModel().getSelectedItem();
             
            openDetailsPage(selectedAthelete,"modifier_match");
        }
    });
          TableColumn<match, Void> colAction = new TableColumn<>("supprimer");
              colAction.setCellFactory(param -> new TableCell<>() {
                                                private final javafx.scene.control.Button deleteButton = new javafx.scene.control.Button("Supprimer");

                                                @Override
                                                protected void updateItem(Void item, boolean empty) {
                                                    super.updateItem(item, empty);
                                                    if (empty || getTableRow().getItem() == null) {
                                                        setGraphic(null);
                                                    } else {
                                                        match selectedAthelete = (match) getTableRow().getItem();
                                                         deleteButton.getStyleClass().add("deleteButton");
                                                        deleteButton.setOnAction(event -> btnSupprimer(event, selectedAthelete));
                                                        setGraphic(deleteButton);
                                                    }
                                                }
                                            });


                 tableViewmatch.getColumns().add(colAction);
                tableViewmatch.setItems(atheleteList);
                fetchDataFromMySQL();
    }    
 private void fetchDataFromMySQL() {
        String url = "jdbc:mysql://complexe_sportif";
        String user = "root";
        String password = "";

        try (Connection connection = complexe_sportif.database.getConnect()) {
            String query = "SELECT * FROM matches";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int id1 = resultSet.getInt("id");
                    String eq1= resultSet.getString("nomEquipe1");
                    String eq2= resultSet.getString("nomEquipe2");
                    String arbitre1= resultSet.getString("arbitre");
                    String resultat1= resultSet.getString("resultat");
                    match m = new match(id1,eq1,eq2,arbitre1,resultat1);
                            atheleteList.add(m);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
       
private void btnSupprimer(ActionEvent event, match selectedAthelete) {

          if (selectedAthelete != null) {
              System.out.println("euyyyyyyyyyyyyyyy");
              atheleteList.remove(selectedAthelete);
              deletearbitreFromDatabase(selectedAthelete.getid());
          }else{
              System.out.println("laaaaaaaaaaaaa");
          }
      }


private void deletearbitreFromDatabase(int athleteId) {
   

    try (Connection connection = complexe_sportif.database.getConnect()) {
        String query = "DELETE FROM matches WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, athleteId);
            statement.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
private void openDetailsPage(match selectedAthelete,String s) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(s+".fxml"));
        Parent root = loader.load();
        
        
        
        modifier_match modifierController = loader.getController();
        modifierController.initializeData(atheleteList, selectedAthelete);
        

            Stage stage = new Stage();
         root.setOnMousePressed((MouseEvent eventa) -> {
            x = eventa.getSceneX();
            y = eventa.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent eventa) -> {
            stage.setX(eventa.getScreenX() - x);
            stage.setY(eventa.getScreenY() - y);
            stage.setOpacity(.8);
        });

        root.setOnMouseReleased((MouseEvent eventa) -> {
            stage.setOpacity(1);
        });

        stage.initStyle(StageStyle.TRANSPARENT);          
        stage.setScene(new Scene(root));
        stage.showAndWait();
           

  tableViewmatch.getItems().clear();
        fetchDataFromMySQL();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
 private double x=0;
        private double y=0;
    @FXML
        private void btn_ajouter(ActionEvent event) throws IOException {  
            Parent root = FXMLLoader.load(getClass().getResource("ajouter_match.fxml"));
                   Stage stage = new Stage();
         root.setOnMousePressed((MouseEvent eventa) -> {
            x = eventa.getSceneX();
            y = eventa.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent eventa) -> {
            stage.setX(eventa.getScreenX() - x);
            stage.setY(eventa.getScreenY() - y);
            stage.setOpacity(.8);
        });

        root.setOnMouseReleased((MouseEvent eventa) -> {
            stage.setOpacity(1);
        });

        stage.initStyle(StageStyle.TRANSPARENT);          
        stage.setScene(new Scene(root));
        stage.showAndWait();
           

            tableViewmatch.getItems().clear();
        fetchDataFromMySQL();
        }


        @FXML
        private void chercher(ActionEvent event) {
    String matchIdText = chercher.getText().trim();

    if (matchIdText.isEmpty()) {
        tableViewmatch.setItems(atheleteList);
    } else {
        try {
            int matchId = Integer.parseInt(matchIdText);

            ObservableList<match> filteredList = FXCollections.observableArrayList();
            for (match m : atheleteList) {
                if (m.getid() == matchId) {
                    filteredList.add(m);
                }
            }
            tableViewmatch.setItems(filteredList);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
    }
    

