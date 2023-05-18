/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_lab_project;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static jdk.nashorn.internal.objects.Global.getDate;

/**
 * FXML Controller class
 *
 * @author yousu
 */
public class DashboardController implements Initializable {
    @FXML
    private AnchorPane home_forms;
    @FXML
    private Label home_totalStudents;
    @FXML
    private Label home_MaleStudents;
    @FXML
    private BarChart<?,?> home_totalStudentChart;
    @FXML
    private BarChart<?, ?> home_MalelStudentChart;
    @FXML
    private BarChart<?, ?> home_FemalelStudentChart;
    @FXML
    private AnchorPane addStudent_form;
    @FXML
    private TextField addStudents_search;
    @FXML
    private TableView<studentData> addStudents_tableview;
    @FXML
    private TableColumn<studentData, String> addStudents_col_studentid;
    @FXML
    private TableColumn<studentData, String> addStudents_col_year;
    @FXML
    private TableColumn<studentData, String> addStudents_col_fullname;
    @FXML
    private TableColumn<studentData, String> addStudents_col_gender;
    @FXML
    private TableColumn<studentData, String> addStudents_col_email;
    @FXML
    private TextField addStudents_studentid;
    @FXML
    private ComboBox<String> addStudents_year;
    @FXML
    private ComboBox<String> addStudents_gender;
    @FXML
    private TextField addStudents_fullname;
    @FXML
    private TextField addStudents_email;
    @FXML
    private Button addStudents_deletebtn;
    @FXML
    private Button addStudents_addbtn;
    @FXML
    private Button addStudents_updatebtn;
    @FXML
    private Button home_btn;
    @FXML
    private Button addStudents_btn;
    @FXML
    private Button logOut_btn;


    /**
     * Initializes the controller class.
     * @param event
     */
   
    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    @FXML
    private Label userName;
    @FXML
    private Button addStudents_clearbtn;
    @FXML
    private Label home_FemaleStudents;
    
    public void homeDisplayTotalStudents() {

        String sql = "SELECT COUNT(id) FROM student";

        connect = database.connectDb();

        int count = 0;

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                count = result.getInt("COUNT(id)");
            }

            home_totalStudents.setText(String.valueOf(count));
            
            home_totalStudentChart.getData().clear();
            XYChart.Series chart = new XYChart.Series();
            chart.getData().add(new XYChart.Data("Studetns",count));
            home_totalStudentChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

}
    
    public void homeDisplayMaleStudent() {

        String sql = "SELECT COUNT(id) FROM student WHERE gender = 'Male'";

        connect = database.connectDb();

        try {
            int countM = 0;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countM = result.getInt("COUNT(id)");
            }
            home_MaleStudents.setText(String.valueOf(countM));
            
            home_MalelStudentChart.getData().clear();
            XYChart.Series chart = new XYChart.Series();
            chart.getData().add(new XYChart.Data("Male Studetns",countM));
            home_MalelStudentChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void homeDisplayFemaleStudent() {

        String sql = "SELECT COUNT(id) FROM student WHERE gender = 'Female'";

        connect = database.connectDb();

        try {
            int countF = 0;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countF = result.getInt("COUNT(id)");
            }

            home_FemaleStudents.setText(String.valueOf(countF));
            
            home_FemalelStudentChart.getData().clear();
            XYChart.Series chart = new XYChart.Series();
            chart.getData().add(new XYChart.Data("Female Studetns",countF));
            home_FemalelStudentChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    

    public ObservableList<studentData> addStudentsListData(){
        ObservableList<studentData> listStudents = FXCollections.observableArrayList();
        
        String sql="SELECT * FROM student";
        
        connect=database.connectDb();
        
        try{
            
            studentData studentD;
            prepare = connect.prepareStatement(sql);
            result=prepare.executeQuery();
            
            while(result.next()){
                studentD=new studentData(
                        result.getInt("id"),
                        result.getString("year"),
                        result.getString("fullName"),
                        result.getString("gender"),
                        result.getString("email")
                );
                
                listStudents.add(studentD);
            }
            
        }
        catch(Exception e){e.printStackTrace();}
        return listStudents;
    }
    
    private ObservableList<studentData> addStudentsListD;
    
    public void addStudentsShowListData(){
        addStudentsListD=addStudentsListData();
        
        addStudents_col_studentid.setCellValueFactory(new PropertyValueFactory<>("id"));
        addStudents_col_year.setCellValueFactory(new PropertyValueFactory<>("year"));
        addStudents_col_fullname.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        addStudents_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addStudents_col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        addStudents_tableview.setItems(addStudentsListD);
        
    }
    
    @FXML
    public void addStudentsSelect(){
        studentData studentD = addStudents_tableview.getSelectionModel().getSelectedItem();
        int num = addStudents_tableview.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addStudents_studentid.setText(String.valueOf(studentD.getId()));
        addStudents_fullname.setText(studentD.getFullName());
        addStudents_email.setText(studentD.getEmail());
        
    }
    
    private String[] yearList = {"First Year", "Second Year", "Third Year", "Fourth Year"};

    @FXML
    public void addStudentsYearList() {

        List<String> yearL = new ArrayList<>();

        for (String data : yearList) {
            yearL.add(data);
        }

        ObservableList ObList = FXCollections.observableArrayList(yearL);
        addStudents_year.setItems(ObList);

    }
    
    private String[] genderList = {"Male", "Female", "Others"};

    @FXML
    public void addStudentsGenderList() {
        List<String> genderL = new ArrayList<>();

        for (String data : genderList) {
            genderL.add(data);
        }

        ObservableList ObList = FXCollections.observableArrayList(genderL);
        addStudents_gender.setItems(ObList);
    }
    
    @FXML
    public void addStudentsClear() {
        addStudents_studentid.setText("");
        addStudents_year.getSelectionModel().clearSelection();
        addStudents_fullname.setText("");
        addStudents_gender.getSelectionModel().clearSelection();
        addStudents_email.setText("");
    }
    

    @FXML
    public void addStudentsAdd() {

        String insertData = "INSERT INTO student "
                + "(id,year,fullName,gender,email) "
                + "VALUES(?,?,?,?,?)";

        connect = database.connectDb();

        try {
            Alert alert;

            if (addStudents_studentid.getText().isEmpty()
                    || addStudents_year.getSelectionModel().getSelectedItem() == null
                    || addStudents_gender.getSelectionModel().getSelectedItem() == null
                    || addStudents_fullname.getText().isEmpty()
                    || addStudents_email.getText().isEmpty()
                    ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait(); 
                
                addStudentsShowListData();
            } else {
                
                // CHECK IF THE STUDENTNUMBER IS ALREADY EXIST
                String checkData = "SELECT id FROM student WHERE id = '"
                        + addStudents_studentid.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Student #" + addStudents_studentid.getText() + " was already exist!");
                    alert.showAndWait();
                } else {
                    
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, addStudents_studentid.getText());
                    prepare.setString(2, (String) addStudents_year.getSelectionModel().getSelectedItem());
                    prepare.setString(3, addStudents_fullname.getText());
                    prepare.setString(4, (String) addStudents_gender.getSelectionModel().getSelectedItem());
                    prepare.setString(5, addStudents_email.getText());
                    

                    
                    prepare.executeUpdate();
                    
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    
                    // TO UPDATE THE TABLEVIEW
                    addStudentsShowListData();
                    // TO CLEAR THE FIELDS
                    addStudentsClear();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    @FXML
    public void addStudentsUpdate() {


        String updateData = "UPDATE student SET "
                + "year = '" + addStudents_year.getSelectionModel().getSelectedItem()
                + "', fullName = '" + addStudents_fullname.getText()
                + "', gender = '" + addStudents_gender.getSelectionModel().getSelectedItem()
                +"', email= '"+addStudents_email.getText()+"' WHERE id = '"
                + addStudents_studentid.getText() + "'";

        connect = database.connectDb();

        try {
            Alert alert;
            if (addStudents_studentid.getText().isEmpty()
                    || addStudents_year.getSelectionModel().getSelectedItem() == null
                    || addStudents_fullname.getText().isEmpty()
                    || addStudents_gender.getSelectionModel().getSelectedItem() == null
                    || addStudents_email.getText().isEmpty()) {
                
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Student #" + addStudents_studentid.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    
                    statement = connect.createStatement();
                    statement.executeUpdate(updateData);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // TO UPDATE THE TABLEVIEW
                    addStudentsShowListData();
                    // TO CLEAR THE FIELDS
                    addStudentsClear();

                } else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
     public void addStudentsDelete() {

        String deleteData = "DELETE FROM student WHERE id = '"
                + addStudents_studentid.getText() + "'";

        connect = database.connectDb();

        try {
            Alert alert;
             if (addStudents_studentid.getText().isEmpty()
                    || addStudents_year.getSelectionModel().getSelectedItem() == null
                    || addStudents_gender.getSelectionModel().getSelectedItem() == null
                    || addStudents_fullname.getText().isEmpty()
                    || addStudents_email.getText().isEmpty()
                    ){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Student #" + addStudents_studentid.getText() + "?");

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {

                    statement = connect.createStatement();
                    statement.executeUpdate(deleteData);

                    String checkData = "SELECT id FROM student "
                            + "WHERE id = '" + addStudents_studentid.getText() + "'";

                    prepare = connect.prepareStatement(checkData);
                    result = prepare.executeQuery();

                    // IF THE STUDENT NUMBER IS EXIST THEN PROCEED TO DELETE
                    if (result.next()) {
                        String deleteD = "DELETE FROM student WHERE "
                                + "id = '" + addStudents_studentid.getText() + "'";

                        statement = connect.createStatement();
                        statement.executeUpdate(deleteD);

                    }// IF NOT THEN NVM

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    // TO UPDATE THE TABLEVIEW
                    addStudentsShowListData();
                    // TO CLEAR THE FIELDS
                    addStudentsClear();

                } else {
                    return;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    @FXML
     public void showUserName(){
         userName.setText(getData.userName);
     }
    
    boolean track=false;
    
    @FXML
    private void switchForm(ActionEvent event) {
        
        if(event.getSource() == home_btn && track==true){
            track=false;
            home_forms.setVisible(true);
            addStudent_form.setVisible(false);
            homeDisplayTotalStudents();
            homeDisplayMaleStudent();
            homeDisplayFemaleStudent();
  
        }
        else if(event.getSource()== addStudents_btn && track==false){
            track=true;
            addStudent_form.setVisible(true);
            home_forms.setVisible(false);
            
            addStudentsShowListData();
            addStudentsYearList();
            addStudentsGenderList();
        }
    }
    

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        logOut_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene=new Scene(root);
                
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle("Login into your account...");
        stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showUserName();
        homeDisplayMaleStudent();
        homeDisplayFemaleStudent();
        homeDisplayTotalStudents();
        addStudentsShowListData();
        addStudentsYearList();
        addStudentsGenderList();
    } 
    
}
