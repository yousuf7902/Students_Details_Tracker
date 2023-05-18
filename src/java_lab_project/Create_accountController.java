/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_lab_project;

import java.sql.DriverManager;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author yousu
 */
public class Create_accountController implements Initializable {
    @FXML
    private TextField fullname_signup;
    @FXML
    private TextField username_signup;
    @FXML
    private TextField email_signup;
    @FXML
    private PasswordField pass_signup;
    @FXML
    private Button create_btn;

    //Database Connection
    private Connection connect;
    private PreparedStatement statement;
    private ResultSet result;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void create(ActionEvent event){
        connect = database.connectDb();
        
        try{
            String sql = "INSERT INTO data VALUES(?,?,?,?)";
            statement = (PreparedStatement)connect.prepareStatement(sql);
            statement.setString(1, fullname_signup.getText());
            statement.setString(2, username_signup.getText());
            statement.setString(3,email_signup.getText());
            statement.setString(4, pass_signup.getText());
            statement.execute();
            
            JOptionPane.showMessageDialog(null, "Successfully Create New Account!", "System Massage",JOptionPane.INFORMATION_MESSAGE);
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    @FXML
    private void login_page(ActionEvent event) throws IOException {
        create_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene=new Scene(root);
                
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle("Sign In Your Account");
        stage.show();
    }
    
    
    public void exit_create_account(){
        System.exit(0);
    }
}
