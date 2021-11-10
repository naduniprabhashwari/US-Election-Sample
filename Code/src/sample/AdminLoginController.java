package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * controller for admin_login.fxml
 */
public class AdminLoginController {

    @FXML
    private Button btnlogin;

    @FXML
    private TextField txtusername;

    @FXML
    private PasswordField password;

    @FXML
    private Label lbluserName;

    @FXML
    private Label lblpassword;

    /**
     * @button btnlogin (This function will run by clicking)
     * Get the user username and passwords from textfields
     * IF empty two labels will show.
     * Check weather username and passwords are correct from the database "admins" table
     * If valid open admin option window
     * If not show an alert and let user to enter again.
     */
    public void login() throws IOException, SQLException {
        lbluserName.setText(null); // label which show when username is empty
        lblpassword.setText(null); // label which show when password is empty

        // checks weather textfiels are empty or not
        boolean empty = true;
        if (txtusername.getText().isEmpty()) {
            lbluserName.setText("Enter Username");
            System.out.println("Enter Username");
            empty = false;
        }
        if (password.getText().isEmpty()) {
            lblpassword.setText("Enter Password");
            System.out.println("Enter Password");
            empty = false;
        }
        // if textfiels are empty this statement will prevent executing query
        if (!(empty)) {
            return;
        }

        // Getting username and password
        String user = txtusername.getText();
        String pass = password.getText();

        // building the connection with database and execute query
        JDSCON connect = new JDSCON();
        Connection con = connect.jdbcConnect2();

        // searching the record where username and password equal to user inputs in the database
        String query = "SELECT * FROM admins where username = '" + user + "' and pass_word = '" + pass + "'";

        Statement st;
        ResultSet rs;

        int rows = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                rows++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // if true, open the admin option window
        if (rows == 1) {
            Parent rootAdminCandidate = FXMLLoader.load(getClass().getResource("admin_options_candidate.fxml"));
            Stage stageAdminOptionCandidate = new Stage();
            Scene sceneAdminOptionCandidate = new Scene(rootAdminCandidate, 825, 600);
            stageAdminOptionCandidate.setScene(sceneAdminOptionCandidate);
            stageAdminOptionCandidate.setTitle("Admin Options");
            stageAdminOptionCandidate.resizableProperty().setValue(false);
            stageAdminOptionCandidate.show();


            Stage primaryStage = (Stage) btnlogin.getScene().getWindow();
            primaryStage.close();
        }
        // if false, popup an alert
        else {
            System.out.println("wrong username and password");
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Wrong username or password.\nEnter correct username and password.");
            a.showAndWait();
            txtusername.setText(null);
            password.clear();
        }
    }

    /**
     * @lable "Back to select login type" (This function will run by clicking)
     * load Main Login page
     */
    public void backToMain() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        Stage Stage = new Stage();
        Stage.setScene(new Scene(root, 1100, 600));
        Stage.setTitle("Main Login");
        Stage.resizableProperty().setValue(false);
        Stage.show();

        Stage primaryStage = (Stage) btnlogin.getScene().getWindow();
        primaryStage.close();
    }



}

