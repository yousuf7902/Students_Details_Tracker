package java_lab_project;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.sql.DriverManager;
import static java_lab_project.getData.userName;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


public class FXMLDocumentController implements Initializable {
    
    private Label label;
    
    @FXML
    private TextField username_sign_in;
    @FXML
    private ImageView bg_image;
    
    
    //Database Connection
    private Connection connect;
    private PreparedStatement statement;
    private ResultSet result;
    
    @FXML
    private PasswordField password_sign_in;
    @FXML
    private Button login_btn;
    @FXML
    private Hyperlink create_account_page;
    
    
    
    
    @FXML
    public void login(ActionEvent event){
        connect = database.connectDb();
        
        try{
            String sql="SELECT * FROM data WHERE username = ? and password = ?";
            statement = (PreparedStatement) connect.prepareStatement(sql);
            statement.setString(1, username_sign_in.getText());
            statement.setString(2, password_sign_in.getText());
            
            result = statement.executeQuery();
            
            if(result.next()){
                
                getData.userName=username_sign_in.getText();
                
                JOptionPane.showMessageDialog(null, "Successfully Login", "System Massege", JOptionPane.INFORMATION_MESSAGE);
                
                login_btn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                Scene scene=new Scene(root);
                
                Stage stage= new Stage();
                stage.setScene(scene);
                stage.setTitle("Welcome To Dashboard");
                stage.show();
            }
            else{
                JOptionPane.showMessageDialog(null, "Wrong Username / Password!", "System Massege", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    @FXML
    public void exit(){
        System.exit(0);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void create_account_page(ActionEvent event) throws IOException {
        login_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("create_account.fxml"));
        Scene scene=new Scene(root);
                
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle("Create Your Account");
        stage.show();
    }
    
}
