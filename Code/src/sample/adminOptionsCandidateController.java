package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * controller for admin_options_candidates.fxml, VotingResults.fxml
 */
public class adminOptionsCandidateController implements Initializable {


    @FXML
    private Hyperlink Hbacktologin;

    @FXML
    private Hyperlink hypVoters;

    @FXML
    private Button btnadd;
    @FXML
    private Button btnedit;
    @FXML
    private Button btnview;
    @FXML
    private Button btndelete;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JDSCON startingstatus = new JDSCON();
        Connection con = startingstatus.jdbcConnect2();

        String query = "Select * from voting limit 1";

        Statement st;
        ResultSet rs;
        int i = 0;
        try{
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                if (rs.getInt(2) == 0) {
                    btnadd.setDisable(true);
                    btnedit.setDisable(true);
                    btndelete.setDisable(true);
                }else {
                    hypVoters.setDisable(true);
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * @button "ADD" (This function is executed by clicking)
     * Load add Candidates window
     */
    public void addCan() throws IOException{
        Parent rootAddCan = FXMLLoader.load(getClass().getResource("add.fxml"));
        Stage stageAddCan = new Stage();
        Scene sceneAddCan = new Scene(rootAddCan,1160, 879);
        stageAddCan.setScene(sceneAddCan);
        stageAddCan.setTitle("Add Candidates");
        stageAddCan.resizableProperty().setValue(false);
        stageAddCan.show();

        Stage primaryStage = (Stage) Hbacktologin.getScene().getWindow();
        primaryStage.close();
    }

    /**
     * @button "EDIT" (This function is executed by clicking)
     * Load edit/update Candidates window
     */
    public void editCan() throws IOException{
        Parent rootEditCan = FXMLLoader.load(getClass().getResource("edit.fxml"));
        Stage stageEditCan = new Stage();
        Scene sceneEditCan = new Scene(rootEditCan,1160, 879);
        stageEditCan.setScene(sceneEditCan);
        stageEditCan.setTitle("Edit Candidates");
        stageEditCan.resizableProperty().setValue(false);
        stageEditCan.show();

        Stage primaryStage = (Stage) Hbacktologin.getScene().getWindow();
        primaryStage.close();
    }

    /**
     * @button "VIEW" (This function is executed by clicking)
     * Load view Candidates window
     */
    public void viewCan() throws IOException{
        Parent rootViewCan = FXMLLoader.load(getClass().getResource("view.fxml"));
        Stage stageViewCan = new Stage();
        Scene sceneViewCan = new Scene(rootViewCan,1906, 879);
        stageViewCan.setScene(sceneViewCan);
        stageViewCan.setTitle("View Candidates");
        stageViewCan.resizableProperty().setValue(false);
        stageViewCan.show();

        Stage primaryStage = (Stage) Hbacktologin.getScene().getWindow();
        primaryStage.close();
    }

    /**
     * @button "DELETE" (This function is executed by clicking)
     * Load delete Candidates window
     */
    public void deleteCan() throws IOException{
        Parent rootDeleteCan = FXMLLoader.load(getClass().getResource("delete.fxml"));
        Stage stageDeleteCan = new Stage();
        Scene sceneDeleteCan = new Scene(rootDeleteCan,1906, 873);
        stageDeleteCan.setScene(sceneDeleteCan);
        stageDeleteCan.setTitle("Delete Candidates");
        stageDeleteCan.resizableProperty().setValue(false);
        stageDeleteCan.show();

        Stage primaryStage = (Stage) Hbacktologin.getScene().getWindow();
        primaryStage.close();
    }

    /**
     * @HyperLink "<- back to login" (This function is executed by clicking)
     * Load admin's login window
     */
    public void backToLogin() throws IOException {
        Parent rootAdminLogin = FXMLLoader.load(getClass().getResource("admin_login.fxml"));
        Stage stageAdminLogin = new Stage();
        Scene sceneAdminLogin = new Scene(rootAdminLogin,1100, 600);
        stageAdminLogin.setScene(sceneAdminLogin);
        stageAdminLogin.setTitle("Admin Login");
        stageAdminLogin.resizableProperty().setValue(false);
        stageAdminLogin.show();

        Stage primaryStage = (Stage) Hbacktologin.getScene().getWindow();
        primaryStage.close();
    }

    /**
     * @HyperLink Voters
     * load voter's login page
     */
    public void Voters() throws IOException {
        Parent rootVoterCandidate = FXMLLoader.load(getClass().getResource("voters_login.fxml"));
        Stage stageVoterOptionCandidate = new Stage();
        Scene sceneVoterOptionCandidate = new Scene(rootVoterCandidate,1100,600);
        stageVoterOptionCandidate.setScene(sceneVoterOptionCandidate);
        stageVoterOptionCandidate.setTitle("Voters Login");
        stageVoterOptionCandidate.resizableProperty().setValue(false);
        stageVoterOptionCandidate.show();


        Stage primaryStage = (Stage) btnadd.getScene().getWindow();
        primaryStage.close();
    }

    /**
     * @button "Control Voting" (This function is executed by clicking)
     * Load voting controll window
     */
    public void voting() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("votingStart.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root,1100, 600);
        stage.setScene(scene);
        stage.setTitle("Voting Control");
        stage.resizableProperty().setValue(false);
        stage.show();

        Stage primaryStage = (Stage) Hbacktologin.getScene().getWindow();
        primaryStage.close();


    }

}