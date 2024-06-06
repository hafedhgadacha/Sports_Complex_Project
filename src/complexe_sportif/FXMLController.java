package complexe_sportif;
import java.io.IOException;
import java.sql.Connection;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLController implements Initializable {

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    @FXML
    private Button close;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: Initialization code here
    }
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet  result;
    
    
    @FXML
    public void loginadmin(){
        String sql="Select * from admin where name = ? and password = ?";
        
                connect=database.getConnect();
                try{
                prepare= connect.prepareStatement(sql);
                prepare.setString(1,name.getText());
                prepare.setString(2,password.getText());
                result=prepare.executeQuery();
                Alert alert;
                if (name.getText().isEmpty()||password.getText().isEmpty())
                {
                        alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setContentText("Invalid User or Password !");
                        alert.showAndWait();
                    }else {
                       if(result.next()){
                            alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information msg");
                            alert.setHeaderText(null);
                            alert.setContentText("good");
                            login.getScene().getWindow().hide();
                            Parent root = FXMLLoader.load(getClass().getResource("dashbord.fxml"));
                            Scene scene = new Scene(root, 1000, 600);
                            Stage stage = new Stage();


                            root.setOnMousePressed((MouseEvent event)->{
                            x=event.getSceneX();
                            y=event.getSceneY();});  

                        root.setOnMouseDragged((MouseEvent event)->{
                            stage.setX(event.getScreenX()-x);
                            stage.setY(event.getScreenY()-y);
                            stage.setOpacity(.8);
                        });

                            root.setOnMouseReleased((MouseEvent  event)->{
                            stage.setOpacity(1);
                            });

                            stage.setTitle("Complexe Sportif"); 
                            stage.initStyle(StageStyle.TRANSPARENT);
                            stage.setScene(scene);
                            stage.show();

                     }else{
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Warning");
                            alert.setContentText("wrong/passsss");
                            alert.showAndWait();
                            }
                        }
                     }catch (Exception e) {
                        e.printStackTrace();
        
                    }}
    
    
    public void handleAction(ActionEvent event) throws IOException {
        String username = name.getText();
        String enteredPassword = password.getText();

        if (authenticate(username, enteredPassword)) {
           
            openNewPage();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login Failed");
            alert.setContentText("Invalid User or Password!");
            alert.showAndWait();
        }
    }

    @FXML
    private void login(KeyEvent event) {
        // Handle login via keyboard if needed
    }

    @FXML
    public void close() {
        System.exit(0);
    }
private double x=0;
private double y =0;
    private void openNewPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("dashbord.fxml"));
            Scene scene = new Scene(root, 1000, 600);
            Stage stage = new Stage();

            
            root.setOnMousePressed((MouseEvent event)->{
            x=event.getSceneX();
            y=event.getSceneY();});  
        
        root.setOnMouseDragged((MouseEvent event)->{
            stage.setX(event.getScreenX()-x);
            stage.setY(event.getScreenY()-y);
            stage.setOpacity(.8);
        });
        
            root.setOnMouseReleased((MouseEvent  event)->{
            stage.setOpacity(1);
            });
            
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        
        
    }

    // Replace this method with your actual authentication logic
    private boolean authenticate(String username, String password) {
        return username.equals("user") && password.equals("user");
    }
}
