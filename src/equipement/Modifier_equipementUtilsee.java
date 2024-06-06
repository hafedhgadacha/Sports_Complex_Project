/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package equipement;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import shared.SharedDataModel;

/**
 * FXML Controller class
 *
 * @author MSI PC


/**
 * FXML Controller class
 *
 * @author MSI PC
 */
public class Modifier_equipementUtilsee implements Initializable {
    
    
    private ObservableList<Equipement> Equipementlist;
    private Equipement selectedEquipement;
    @FXML
    private Button btn_modifier;
    @FXML
    private TextField get_typeMateriel;
    @FXML
    private TextField get_prix;
     @FXML
    private Button annuler;
    @FXML
    private TextField get_equipement_id;     
    @FXML
    private TextField get_nbrutilsee;
    @FXML
    private TextField match_id;
    @FXML
    private void annuler(ActionEvent event) {
 Stage stage = (Stage) annuler.getScene().getWindow();
        stage.close();
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
   


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
  public void initializeData(ObservableList<Equipement> Equipementlist, Equipement selectedEquipement) {
        this.Equipementlist = Equipementlist;
        this.selectedEquipement = selectedEquipement;
        populateFields(selectedEquipement);}
  
  private int oldnbreutilsee;
      private void populateFields(Equipement t) {
    match_id.setText(String.valueOf(t.getequipement_id_match()));
    get_typeMateriel.setText(t.getTypeMateriel());
    get_nbrutilsee.setText(String.valueOf(t.getNbrUtilisee()));
    get_equipement_id.setText(String.valueOf(t.getequipement_id()));
    get_prix.setText(String.valueOf(t.getPrix()));
    oldnbreutilsee=t.getNbrUtilisee();
    
    
}
   
 @FXML
        private void btn_modifier(ActionEvent event) throws SQLException {
          String prix = get_prix.getText();
          String id = get_equipement_id.getText();
        String typeMateriel = get_typeMateriel.getText();
        String nbrUtilisee1 = get_nbrutilsee.getText();
        String id1= match_id.getText() ;
        int newnbr=Integer.parseInt(nbrUtilisee1);
        if (oldnbreutilsee>newnbr){
                   try (Connection connection = complexe_sportif.database.getConnect()) {
                                        oldnbreutilsee-=newnbr;
                                 String query = "SELECT nbrEnStock FROM equipement WHERE typeMateriel=?";
                                try (PreparedStatement statementex = connection.prepareStatement(query)) {
                                    statementex.setString(1, get_typeMateriel.getText());
                                    try (ResultSet res = statementex.executeQuery()) {
                                        if (res.next()) { 
                                            int nbre = res.getInt("nbrEnStock");
                                            nbre += newnbr;
                                             String nbre1 = String.valueOf(nbre);
                                    String     query2 = "UPDATE Equipement SET  nbrEnStock=? WHERE typeMateriel=?";
                                        try ( PreparedStatement stat = connection.prepareStatement(query2)) {
                                            stat.setString(1, nbre1);
                                            stat.setString(2, typeMateriel);
                                            int rowsUpdated = stat.executeUpdate();

                                            if (rowsUpdated > 0) {
                                                showAlert(Alert.AlertType.INFORMATION, "Success", "Equipement Update successful!");
                                          String         query3 = "UPDATE Equipementmatch SET  nbrUtilisee=?,prix=?,typeMateriel=? ,id=? WHERE equipement_id_match=? ";
                                        try ( PreparedStatement state = connection.prepareStatement(query3)) {
                                                            state.setString(1, nbrUtilisee1);
                                                            state.setString(2, prix);
                                                            state.setString(3, typeMateriel);
                                                            state.setString(4, id);
                                                            state.setString(5, id1);
                                                            int rowsUpdatede = state.executeUpdate();
                                                            if (rowsUpdatede > 0) {
                                                                showAlert(Alert.AlertType.INFORMATION, "Success", "Equipementmatch Update successful!");
                                                                int index = Equipementlist.indexOf(selectedEquipement);
                                                                Equipementlist.set(index, selectedEquipement);
                                                                Stage stage = (Stage) btn_modifier.getScene().getWindow();
                                                                stage.close();} else {
                                                                showAlert(Alert.AlertType.ERROR, "Error", "Update failed!");
                                                            }
                                                        }

                                    Stage stage = (Stage) btn_modifier.getScene().getWindow();
                                    stage.close();}
               
    }
                                        }
                                    }}}catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while updating the database.");
        }
       
        
        }
        else if(oldnbreutilsee==newnbr){
              try (Connection connection = complexe_sportif.database.getConnect()) {
            String query = "UPDATE Equipementmatch SET  nbrUtilisee=?,prix=?,typeMateriel=? ,id=? WHERE equipement_id_match=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1,nbrUtilisee1);
                statement.setString(2, prix);
                statement.setString(3, typeMateriel);
                statement.setString(4, id);
                statement.setString(5, id1);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Update successful!");
                 int index = Equipementlist.indexOf(selectedEquipement);
        Equipementlist.set(index, selectedEquipement);
        Stage stage = (Stage) btn_modifier.getScene().getWindow();
        stage.close();} else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Update failed!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while updating the database.");
        }
        
        
    
            
        } else{
             try (Connection connection = complexe_sportif.database.getConnect()) {

            String query = "SELECT nbrEnStock FROM equipement WHERE typeMateriel=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, get_typeMateriel.getText());
            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) { 
                    int nbre = res.getInt("nbrEnStock");
                    nbre -= newnbr;
                 if(nbre<0){
                                         showAlert(Alert.AlertType.ERROR, "Error", "Update failed!becuase of the stock");
                 }         else{
                             String nbre1 = String.valueOf(nbre);
                                 String   query1 = "UPDATE equipement SET  nbrEnStock=?,prix=? WHERE typeMateriel=?";
                                       try ( PreparedStatement stat = connection.prepareStatement(query1)) {
                                           stat.setString(1, nbre1);
                                           stat.setString(2, prix);
                                           stat.setString(3, typeMateriel);
                                           int rowsUpdated = stat.executeUpdate();

                                           if (rowsUpdated > 0) {
                                               showAlert(Alert.AlertType.INFORMATION, "Success", "Update successful!");
                                         String query2 = "UPDATE Equipementmatch SET  nbrUtilisee=?,prix=?,typeMateriel=?  WHERE id=? and equipement_id_match=? ";
                                               try (PreparedStatement statement1 = connection.prepareStatement(query2)) {
                                                   statement1.setString(1, nbrUtilisee1);
                                                   statement1.setString(2, prix);
                                                   statement1.setString(3, typeMateriel);
                                                   statement1.setString(4, id);
                                                   statement1.setString(5, id1);
                                                   int rowsUpdated1 = statement1.executeUpdate();
                                                   if (rowsUpdated1 > 0) {
                                                       showAlert(Alert.AlertType.INFORMATION, "Success", "Update successful!");
                                                    int index = Equipementlist.indexOf(selectedEquipement);
                                           Equipementlist.set(index, selectedEquipement);
                                           Stage stage = (Stage) btn_modifier.getScene().getWindow();
                                           stage.close();} else {
                                                       showAlert(Alert.AlertType.ERROR, "Error", "Update failed!");
                                                   }
                                               }
                                           }} 


            }
    
    
      
                 }}}
            }catch (SQLException e) {
                                               e.printStackTrace();
                                               showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while updating the database.");
                       }
        }
        }
}
