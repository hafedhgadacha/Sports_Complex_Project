/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package planning;



import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import match.match;
import match.modifier_match;

public class Planning_basketController implements Initializable {

    private long lastClickTime = 0;
    @FXML
    public GridPane gp;
    
                    public int b;
                    public String text;
                    public   void set(int b, String text) {
                    this.b=b;this.text=text;}
   
  @Override
    public void initialize(URL url, ResourceBundle rb) {
        
           for (int row = 1; row < 8; row++) {
            for (int col = 1; col < 8; col++) {
                Button button = new Button(); // Créer un bouton pour chaque cellule du GridPane
                button.setPrefSize(77, 25); // Définir la taille du bouton
                 int currentRow = row;
                int currentCol = col;
                button.setOnAction(event -> handleButtonClick(currentRow, currentCol, button));
                 this.gp.add( button, currentCol, currentRow);
            }}    fetchDataForPlanning();

    }
         public void fetchDataForPlanning() {
    try (Connection connection = complexe_sportif.database.getConnect()) {
        String query = "SELECT * FROM planningbasket WHERE lineof = 1";
        try (PreparedStatement selectStatement = connection.prepareStatement(query);
             ResultSet resultSet = selectStatement.executeQuery()) {

            while (resultSet.next()) {
                for (int row = 1; row < 8; row++) {
                    for (int col = 1; col < 8; col++) {
                        String colrow="row" + row + "_col" + col;
                        String cellData = resultSet.getString("row" + row + "_col" + col);
                        Button button = getButton(row, col);
                        button.setText(cellData);
                        setButtonStyle(button, cellData,colrow);
                    }
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private Button getButton(int row, int col) {
    for (Node node : gp.getChildren()) {
        if (node instanceof Button && GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
            return (Button) node;
        }
    }
    return null;
}
private void setButtonStyle(Button button, String cellData,String colrow) {
     if (cellData == null || cellData.trim().isEmpty()) {
        button.setStyle("-fx-background-color:#3498db;");
    } else {
        try (Connection connection = complexe_sportif.database.getConnect()) {
            String querySelect = "SELECT " + colrow + " FROM planningbasket WHERE lineof = 2";
            try (PreparedStatement selectStatement = connection.prepareStatement(querySelect);
                 ResultSet res = selectStatement.executeQuery()) {

                if (res.next()) {
                     String a = res.getString(colrow);
                     if ("1".equals(a)) {
                        button.setStyle("-fx-background-color:#009900;-fx-font-size: 12px; -fx-opacity: 1.5;-fx-text-fill: black;-fx-font-weight: bold;");
                    } else {
                        button.setStyle("-fx-background-color:red; -fx-font-size: 12px;-fx-opacity: 1.5;-fx-text-fill: black;-fx-font-weight: bold;");
                    }
                }  
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanningController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}



      public void handleButtonClick(int row, int col, Button button) {
      long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime < 500) {
                                            try (java.sql.Connection connection = complexe_sportif.database.getConnect()) {
                                    String query = "UPDATE planningbasket SET  row"+row+"_col"+col+"=? ";
                                    try (java.sql.PreparedStatement insertMatchStatement = connection.prepareStatement(query)) {
                                        insertMatchStatement.setString(1, "");
                                        int rowsUpdated = insertMatchStatement.executeUpdate();
                                        
                                        if (rowsUpdated > 0) {
                                                showAlert(Alert.AlertType.INFORMATION,"update","successfully");                                         
                                        }
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();}
            button.setText("");
            button.setStyle(" -fx-background-color:#3498db; " );
         } else {              
            openDetailsPage();
                    try (Connection connection = complexe_sportif.database.getConnect()) {
                        String querySelect = "SELECT num, text FROM choice WHERE bo=?";
                        try (PreparedStatement selectStatement = connection.prepareStatement(querySelect)) {
                            selectStatement.setString(1, "3");
                            try (ResultSet res = selectStatement.executeQuery()) {
                                if (res.next()) {
                                    b = res.getInt("num");
                                   String B= String.valueOf(b);
                                    text = res.getString("text");

                                    String query1 = "UPDATE planningbasket SET row" + row + "_col" + col + "=? WHERE lineof=1";
                                    try (PreparedStatement updateStatement = connection.prepareStatement(query1)) {
                                        updateStatement.setString(1, text);
                                         updateStatement.executeUpdate();
                                                String query5 = "UPDATE planningbasket SET row" + row + "_col" + col + "=? WHERE lineof=2";
                                          try (PreparedStatement updateStatement2 = connection.prepareStatement(query5)) {
                                              updateStatement2.setString(1, B);
                                               updateStatement2.executeUpdate();

                                         
                                         
                                    }
                                }
                            }
                        }
                    }} catch (SQLException e) {
                        e.printStackTrace();
}

if (b == 1) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/match/ajouter_match.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("choice plan");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    } catch (IOException e) {
        e.printStackTrace();
    }

    button.setStyle("-fx-background-color:#009900;-fx-font-size: 12px; -fx-opacity: 1.5;-fx-text-fill: black;-fx-font-weight: bold;");
    button.setText(text);
} else {
    button.setStyle("-fx-background-color:red; -fx-font-size: 12px;-fx-opacity: 1.5;-fx-text-fill: black;-fx-font-weight: bold;");
    button.setText(text);
}

lastClickTime = currentTime;
    }      
      }    
     
       
private void openDetailsPage() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("choice.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("choice");
        stage.setScene(new Scene(root));
        stage.showAndWait();

    } catch (IOException e) {
        e.printStackTrace();
    }
}
 private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
 
