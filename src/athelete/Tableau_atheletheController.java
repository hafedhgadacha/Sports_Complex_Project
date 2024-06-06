package athelete;
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
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Tableau_atheletheController implements Initializable {

    @FXML
    private TextField chercher;
    @FXML
    private TableColumn<Athelete, Integer> id;
    @FXML
    private TableColumn<Athelete, String> nom;
    @FXML
    private TableColumn<Athelete, String> prenom;
 
    @FXML
    private TableColumn<Athelete, Integer> num_maillot;
    @FXML
    private TableColumn<Athelete, Integer> age;
    @FXML
    private TableColumn<Athelete, String> nom_equipe;
    @FXML
    private TableColumn<Athelete, String> capitaine;

    private final ObservableList<Athelete> atheleteList = FXCollections.observableArrayList();
   
    @FXML
    private TableColumn<Athelete, String> filliere;
    @FXML
    private TableColumn<Athelete, String> poste;
    @FXML
    private TableView<Athelete> tableView;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        prenom.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        filliere.setCellValueFactory(cellData -> cellData.getValue().filliereProperty());
        num_maillot.setCellValueFactory(cellData -> cellData.getValue().numMaillotProperty().asObject());
        age.setCellValueFactory(cellData -> cellData.getValue().ageProperty().asObject());
        nom_equipe.setCellValueFactory(cellData -> cellData.getValue().nomEquipeProperty());
        capitaine.setCellValueFactory(cellData -> cellData.getValue().capitaineProperty());
        poste.setCellValueFactory(cellData -> cellData.getValue().posteProperty());
        tableView.setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2 && !tableView.getSelectionModel().isEmpty()) {
                            // Get the selected item
                            Athelete selectedAthelete = tableView.getSelectionModel().getSelectedItem();

                            // Open a new page or pop-up with the details of the selected item
                            openDetailsPage(selectedAthelete);
                        }
                    });
         TableColumn<Athelete, Void> colAction = new TableColumn<>("supprimer");

    // Create a cell value factory to set the button in each row
  colAction.setCellFactory(param -> new TableCell<>() {
    private final javafx.scene.control.Button deleteButton = new javafx.scene.control.Button("Supprimer");

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || getTableRow().getItem() == null) {
            setGraphic(null);
        } else {
            Athelete selectedAthelete = (Athelete) getTableRow().getItem();

            deleteButton.getStyleClass().add("deleteButton");
            deleteButton.setOnAction(event -> btnSupprimer(event, selectedAthelete));
            setGraphic(deleteButton);
        }
    }
});


    tableView.getColumns().add(colAction);
        tableView.setItems(atheleteList);
         
        fetchDataFromMySQL();
    }
    
    
private void openDetailsPage(Athelete selectedAthelete) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Modifier_athelete.fxml"));
        Parent root = loader.load();

        Modifier_atheleteController modifierController = loader.getController();
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
    }
}





    private void fetchDataFromMySQL() {
                try (Connection connection = complexe_sportif.database.getConnect()) {
            String query = "SELECT * FROM athelete";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int id1 = resultSet.getInt("id");
                    String nom1 = resultSet.getString("nom");
                    String prenom1 = resultSet.getString("prenom");
                    String filliere1 = resultSet.getString("filliere");
                    int num_maillot1 = resultSet.getInt("num_maillot");
                    int age1 = resultSet.getInt("age");
                    String nom_equipe1 = resultSet.getString("nom_equipe");
                    String capitaine1 = resultSet.getString("capitaine");
                     String poste1 = resultSet.getString("poste");

                    Athelete athelete = new Athelete(id1, nom1, prenom1, filliere1,poste1, num_maillot1, age1, nom_equipe1, capitaine1);
                    atheleteList.add(athelete);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  

private void btnSupprimer(ActionEvent event, Athelete selectedAthelete) {

          if (selectedAthelete != null) {
              System.out.println("euyyyyyyyyyyyyyyy");
              atheleteList.remove(selectedAthelete);

              // Remove from database
              deleteAtheleteFromDatabase(selectedAthelete.getid());
          }else{
              System.out.println("laaaaaaaaaaaaa");
          }
      }


private void deleteAtheleteFromDatabase(int athleteId) {
   

    try (Connection connection = complexe_sportif.database.getConnect()) {
        String query = "DELETE FROM athelete WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, athleteId);
            statement.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately, e.g., show an error message
    }
}
 private double x=0;
        private double y=0;

    @FXML
    private void btn_ajouter(ActionEvent event) throws IOException {
           Parent root = FXMLLoader.load(getClass().getResource("ajouter_athelete.fxml"));
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
     @FXML
private void chercher(ActionEvent event) {
    String ida = chercher.getText().trim();

    // If the ID is empty, show all athletes
    if (ida.isEmpty()) {
        tableView.setItems(atheleteList);
    } else {
        try {
            int searchId = Integer.parseInt(ida);

            // Filter athletes based on the ID
            ObservableList<Athelete> filteredList = FXCollections.observableArrayList();
            for (Athelete athlete : atheleteList) {
                if (athlete.getid() == searchId) {
                    filteredList.add(athlete);
                }
            }
            tableView.setItems(filteredList);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Handle the case where the entered text is not a valid integer
        }
    }
}


}
