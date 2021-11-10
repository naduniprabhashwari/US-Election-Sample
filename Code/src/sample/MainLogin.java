package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * controller for MainPage.fxml
 */
public class MainLogin implements Initializable {

    @FXML
    private Button voter;
    @FXML
    private Button admin;

    /**
     * @button "Admin" (This function will execute)
     * Load admin's login page
     */
    public void AdminLogin() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("admin_login.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 1100, 600);
        stage.setScene(scene);
        stage.setTitle("Admin Login");
        stage.resizableProperty().setValue(false);
        stage.show();

        Stage primaryStage = (Stage) admin.getScene().getWindow();
        primaryStage.close();
    }

    /**
     * @button "Voter" (This function will execute)
     * Load voter's login page
     */
    public void VoterLogin() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("voters_login.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 1100, 600);
        stage.setScene(scene);
        stage.setTitle("Voter Login");
        stage.resizableProperty().setValue(false);
        stage.show();

        Stage primaryStage = (Stage) admin.getScene().getWindow();
        primaryStage.close();
    }


    /**
     * set voter button disable if voting didn't start
     * set voter button undisable if voting started
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        JDSCON startingstatus = new JDSCON();
        Connection con = startingstatus.jdbcConnect2();

        String query = "Select * from voting";

        Statement st;
        ResultSet rs ;

        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                voter.setDisable(rs.getInt(2) != 0);
            }else {
                System.out.println("Voting didn't start yet");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
