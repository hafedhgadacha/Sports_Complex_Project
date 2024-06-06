/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package complexe_sportif;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import athelete.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DashbordController implements Initializable {

    @FXML
    private Button logout;
    @FXML
    private Button home;
    @FXML
    private Button Match;
    @FXML
    private Button athletes;
    @FXML
    private Button Arbitre;
    @FXML
    private Button Cantact;
    @FXML
    private Button close;
    @FXML
    private VBox sidebare;
    @FXML
    private BorderPane centre;
    @FXML
    private AnchorPane athelte_scene;
    @FXML
    private Button equipement;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCenterContent("/home/home.fxml");
    }    
 private double x=0;
    private double y=0;
    
    @FXML
    private void logout(ActionEvent event)throws Exception {
     openNewPag("FXML");
    }


    @FXML
    private void equipement(ActionEvent event) {
                loadCenterContent("/equipement/liste_equipement_en_stock.fxml");
    }

    @FXML
    private void Match(ActionEvent event) {
                        loadCenterContent("/match/tablau_match.fxml");
    }
    @FXML
    private void athletes(ActionEvent event) {
                loadCenterContent("/athelete/tableau_athelete.fxml");
    }
    @FXML
    private void Arbitre(ActionEvent event) {
        loadCenterContent("/arbitr/tableau_arbitre.fxml");
    }
   
    @FXML
    private void Cantact(ActionEvent event) {        
        loadCenterContent("/athelete/contact.fxml");
    }
    @FXML
    private void close(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void home(ActionEvent event) {
        loadCenterContent("/home/home.fxml");
    }

    private void loadCenterContent(String fxmlFile) {
    try {
        System.out.println("Loading: " + fxmlFile);
        Parent page = FXMLLoader.load(getClass().getResource(fxmlFile));
        centre.setCenter(page);
                System.out.println("Loading: " + fxmlFile);
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    private void openNewPag(String s) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(s + ".fxml"));
        Scene scene = new Scene(root, 850, 550);
        Stage stage = new Stage();
        Stage mainStage = (Stage) sidebare.getScene().getWindow();
        mainStage.hide();
        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
            stage.setOpacity(.8);
        });

        root.setOnMouseReleased((MouseEvent event) -> {
            stage.setOpacity(1);
        });

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void moin(MouseEvent event) {
       
    }

    
}
