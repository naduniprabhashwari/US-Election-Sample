package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VoterLoginController {


    @FXML
    private Button btnlogin;
    @FXML
    private Label lblvoterid;
    @FXML
    private Label lblvoterNIC;
    @FXML
    private JFXTextField txtvoterID;
    @FXML
    private JFXTextField txtVoterNIC;

    /**
     * @button btnlogin (This function will run by clicking)
     * Get the voter's id and nic from textfields
     * IF empty, two labels will show.
     * Check weather id and nic are correct from the database "voter" table
     * If valid open voting ballot  window
     * If not show an alert and let voter to enter again.
     */
    public void login() throws IOException, SQLException {
        lblvoterid.setText(null); // label which show when id is empty
        lblvoterNIC.setText(null); // label which show when NIC is empty

        // checks weather textfiels are empty or not
        boolean empty = true;
        if (txtvoterID.getText().isEmpty()) {
            lblvoterid.setText("Enter ID");
            System.out.println("Enter ID");
            empty = false;
        }
        if (txtVoterNIC.getText().isEmpty()) {
            lblvoterNIC.setText("Enter NIC");
            System.out.println("Enter NIC");
            empty = false;
        }
        // if textfiels are empty this statement will prevent executing query
        if (!(empty)) {
            return;
        }

        // Getting username and password
        String id = txtvoterID.getText();
        String NIC = txtVoterNIC.getText();

        // building the connection with database and execute query
        JDSCON connect = new JDSCON();
        Connection con = connect.jdbcConnect2();

        // searching the record where id and NIC equal to user inputs in the database
        String query = "SELECT * FROM voter WHERE voter_id = " + id + " AND NIC = '" + NIC + "'";

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
            // check weather voter has voted
            if(voted(id) == 0) {
                updateVotedStatus(id);
                Parent rootAdminCandidate = FXMLLoader.load(getClass().getResource("votingBallots.fxml"));
                Stage stageAdminOptionCandidate = new Stage();
                Scene sceneAdminOptionCandidate = new Scene(rootAdminCandidate, 1160, 879);
                stageAdminOptionCandidate.setScene(sceneAdminOptionCandidate);
                stageAdminOptionCandidate.setTitle("Voting Ballots");
                stageAdminOptionCandidate.initStyle(StageStyle.UNDECORATED);
                stageAdminOptionCandidate.show();




                Stage primaryStage = (Stage) btnlogin.getScene().getWindow();
                primaryStage.close();
            }else {
                System.out.println("Voter already voted");
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText(id+" already voted\nCan not vote again.\n Thank you");
                a.showAndWait();

                Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
                Stage MainPageStage = new Stage();
                Scene sc  = new Scene(root, 1100, 600);
                MainPageStage.setScene(sc);
                MainPageStage.setTitle("Admin Login");
                MainPageStage.resizableProperty().setValue(false);
                MainPageStage.show();

                Stage primaryStage = (Stage) btnlogin.getScene().getWindow();
                primaryStage.close();
            }
        } else {
            System.out.println("wrong ID or NIC");
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Wrong ID or NIC.\nEnter correct ID and NIC.");
            a.showAndWait();
            txtvoterID.setText(null);
            txtVoterNIC.setText(null);
        }
    }

    /**
     * check weather voter voted already
     * @return
     * @param id
     */
    public int voted(String id){
        // building the connection with database and execute query
        JDSCON connect = new JDSCON();
        Connection con = connect.jdbcConnect2();

        // create sql query
        String query = "SELECT `voted` FROM voter WHERE `voter_id` ="+id;

        Statement st;
        ResultSet rs;


        try {
            st = con.createStatement();
            rs = st.executeQuery(query);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        return 2;
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

    /**
     * update voter voted status in the table
     * @return
     */
    public int updateVotedStatus(String id) {
            // building the connection with database and execute query
            JDSCON connect = new JDSCON();
            Connection con = connect.jdbcConnect2();

            // create sql query
            String query = "UPDATE `uselection`.`voter` SET `voted` = '1' WHERE `voter`.`voter_id` =" + id;

            Statement st;
            int rows = 0;


            try {
                st = con.createStatement();
                rows = st.executeUpdate(query);
                System.out.println("suc");
                return rows;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


            return rows;
    }


}
