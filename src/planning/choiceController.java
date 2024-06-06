/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package planning;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MSI PC
 */
public class choiceController implements Initializable {
        
        
        
@FXML
private TextField ajouter_idmatch;
  private int b;
  private String text;
    @FXML
    private Button ajouter_reservation;
    @FXML
    private Button ajouter_match;
 public  void set(int b, String text) {
this.b=b;
this.text=text;

    }
 

@FXML
private void ajouter_match(ActionEvent event) {
     String b1="1";
        text=ajouter_idmatch.getText();  
            try (java.sql.Connection connection = complexe_sportif.database.getConnect()) {

                                   String   query1 = "UPDATE choice SET  num=? , text=? where bo=?";
                                       try ( java.sql.PreparedStatement stat = connection.prepareStatement(query1)) {
                                           stat.setString(1,b1);
                                           stat.setString(2, text);
                                           stat.setString(3, "3");
             stat.executeUpdate();

        Stage stage = (Stage) ajouter_match.getScene().getWindow();
        stage.close();                   }
                       
}   catch (SQLException ex) {
        Logger.getLogger(choiceController.class.getName()).log(Level.SEVERE, null, ex);
    }

}


 @FXML
private void ajouter_reservation(ActionEvent event) {
   
         
        text=ajouter_idmatch.getText();  
            try (java.sql.Connection connection = complexe_sportif.database.getConnect()) {

                                  String   query1 = "UPDATE choice SET  num=? , text=? where bo=?";
                                       try ( java.sql.PreparedStatement stat = connection.prepareStatement(query1)) {
                                           stat.setString(1,"2");
                                           stat.setString(2, text);
                                           stat.setString(3, "3");
              stat.executeUpdate();

                                               }
          Stage stage = (Stage) ajouter_reservation.getScene().getWindow();
        stage.close();
}   catch (SQLException ex) {
        Logger.getLogger(choiceController.class.getName()).log(Level.SEVERE, null, ex);
    }
}
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }  
}