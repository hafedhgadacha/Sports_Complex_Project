
package arbitr;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Tableau_arbitreController implements Initializable {

    @FXML
    private TextField chercher;
    @FXML
    private TableColumn<arbitre, Integer> id;
    @FXML
    private TableColumn<arbitre, String> nom;
    @FXML
    private TableColumn<arbitre, String> prenom;
    @FXML
    private TableColumn<arbitre, Integer> age;
    @FXML
    private TableColumn<arbitre, String> type;
    
    private final ObservableList<arbitre> atheleteList = FXCollections.observableArrayList();
   
    
    @FXML
    private TableView<arbitre> tableView;
    @FXML
    private Button ajouter;
    private double x=0;
        private double y=0;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        prenom.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        age.setCellValueFactory(cellData -> cellData.getValue().ageProperty().asObject());
        type.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        tableView.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && !tableView.getSelectionModel().isEmpty()) {
            arbitre selectedAthelete = tableView.getSelectionModel().getSelectedItem();

            openDetailsPage(selectedAthelete);
        }
    });
          TableColumn<arbitre, Void> colAction = new TableColumn<>("supprimer");


          colAction.setCellFactory(param -> new TableCell<>() {
                private final javafx.scene.control.Button deleteButton = new javafx.scene.control.Button("Supprimer");
                      
                        @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || getTableRow().getItem() == null) {
                        setGraphic(null);
                    } else {
                        arbitre selectedAthelete = (arbitre) getTableRow().getItem();

                           deleteButton.getStyleClass().add("deleteButton");
                      deleteButton.setOnAction(event -> btnSupprimer( event, selectedAthelete));
                        setGraphic(deleteButton);
                    }
                }
            });


    tableView.getColumns().add(colAction);
                tableView.setItems(atheleteList);

         
        try {
                         tableView.getItems().clear();

            fetchDataFromMySQL();
        } catch (problemconnextionarbitre ex) {
            System.out.println(ex);
            Logger.getLogger(Tableau_arbitreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
private void btnSupprimer( ActionEvent event, arbitre selectedAthelete) {
              

          if (selectedAthelete != null) {
              System.out.println("euyyyyyyyyyyyyyyy");
              atheleteList.remove(selectedAthelete);

              // Remove from database
              deletearbitreFromDatabase(selectedAthelete.getid());
          }else{
              System.out.println("laaaaaaaaaaaaa");
          }
      }


private void deletearbitreFromDatabase(int athleteId) {
   

    try (Connection connection = complexe_sportif.database.getConnect()) {
        String query = "DELETE FROM arbitre WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, athleteId);
            statement.executeUpdate();
             tableView.getItems().clear();
        fetchDataFromMySQL();
        } catch (problemconnextionarbitre ex) {
            Logger.getLogger(Tableau_arbitreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately, e.g., show an error message
    }
}


private void openDetailsPage(arbitre selectedAthelete) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modifier_arbitre.fxml"));
        Parent root = loader.load();

        arbitr.Modifier_arbitreController modifierController = loader.getController();
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
        tableView.getItems().clear();
        fetchDataFromMySQL();
    } catch (IOException e) {
        e.printStackTrace();
    }   catch (problemconnextionarbitre ex) {
            Logger.getLogger(Tableau_arbitreController.class.getName()).log(Level.SEVERE, null, ex);
        }
}








////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////



    private void fetchDataFromMySQL() throws problemconnextionarbitre {
       

        try (Connection connection = complexe_sportif.database.getConnect()) {
            String query = "SELECT * FROM arbitre";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int id1 = resultSet.getInt("id");
                    String nom1 = resultSet.getString("nom");
                    String prenom1 = resultSet.getString("prenom");
                    int age1 = resultSet.getInt("age");
                    String type1 = resultSet.getString("type");

                    arbitre athelete = new arbitre(id1, nom1, prenom1, type1 ,age1 );
                    atheleteList.add(athelete);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
     private void ajouter(ActionEvent event) throws IOException, problemconnextionarbitre {
           Parent root = FXMLLoader.load(getClass().getResource("ajouter_arbitre.fxml"));
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
           
        tableView.getItems().clear();
        fetchDataFromMySQL();
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////

  
    @FXML
    private void chercher(ActionEvent event) {
    String searchText = chercher.getText().toLowerCase();

    List<arbitre> filteredList = atheleteList.stream()
            .filter(athelete -> String.valueOf(athelete.getid()).contains(searchText) ||
                                athelete.getNom().toLowerCase().contains(searchText) ||
                                athelete.getPrenom().toLowerCase().contains(searchText) ||
                                String.valueOf(athelete.getAge()).contains(searchText) ||
                                athelete.getType().toLowerCase().contains(searchText))
            .collect(Collectors.toList());
    
    Collections.sort(filteredList, Comparator.comparing(arbitre::getNom));

    tableView.setItems(FXCollections.observableArrayList(filteredList));
    
}

}
