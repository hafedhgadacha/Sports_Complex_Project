/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package home;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
 
/**
 * FXML Controller class
 *
 * @author MSI PC
 */
public class HomeController implements Initializable {

    @FXML
    private AnchorPane centre;
    @FXML
    private AnchorPane home_scene;
    @FXML
    private Button btn_b;
    @FXML
    private Button btn_t;
    @FXML
    private Button btn_f1;
    @FXML
    private Button btn_f2;
    @FXML
    private Button btn_f3;
    @FXML
    private BorderPane border;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                loadCenterContent("/planning/planning.fxml");

        // TODO
    }    

    @FXML
    private void btn_b(ActionEvent event) {
    loadCenterContent("/planning/planning_basket.fxml");
    } @FXML
    private void btn_t(ActionEvent event) {
    loadCenterContent("/planning/planning_tennis.fxml");
    }

    @FXML
    private void btn_f1(ActionEvent event) {
        loadCenterContent("/planning/planning.fxml");
    }

    @FXML
    private void btn_f2(ActionEvent event) {
                loadCenterContent("/planning/planning_1.fxml");

    }

    @FXML
    private void btn_f3(ActionEvent event) {
        loadCenterContent("/planning/planning_2.fxml");
    }
 
   private void loadCenterContent(String fxmlFile) {
    try {
        System.out.println("Loading: " + fxmlFile);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent page = loader.load();

        // Assuming you want to fill both width and height
         

        border.setCenter(page);
        System.out.println("Loaded: " + fxmlFile);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
