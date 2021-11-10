package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class votingStartController implements Initializable {
    @FXML
    private Button bntstart;
    @FXML
    private Button bntstop;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Button> votingButtons = new ArrayList<>();
        votingButtons.add(bntstart);
        votingButtons.add(bntstop);

        JDSCON startingstatus = new JDSCON();
        Connection con = startingstatus.jdbcConnect2();

        String query = "Select * from voting";

        Statement st;
        ResultSet rs;
        int i = 0;
        try{
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                if (rs.getInt(2) == 1) {
                    votingButtons.get(i).setDisable(false);
                } else {
                    votingButtons.get(i).setDisable(true);

                }
                i++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    /**
     * @button  "Start" (This function is executed by clicking)
     * will popup a massage to confirm the action
     * Check weather candidate and voter tables in database are empty
     * If not , will start voting
     * start button will disable and stop button will show and update status in voting table
     * id = 1 , status will 1
     * id = 2, status will 0
     * Will disable ADD,EDIT,VIEW and DELETE buttons
     * If database is empty, popup a warning alert
     */
    public void startVoting() throws SQLException, IOException {
        JDSCON checkcan = new JDSCON();
        Connection con = checkcan.jdbcConnect2();

        String queryCan = "Select candidate_id from candidate";
        String queryVot = "Select voter_id from voter";

        Statement stC;
        Statement stV;
        ResultSet rsC = null;
        ResultSet rsV = null;

        try{
            stC = con.createStatement();
            rsC = stC.executeQuery(queryCan);
            stV = con.createStatement();
            rsV = stV.executeQuery(queryVot);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Alert a;
        if((rsV != null) && (rsC != null)){
            a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText("Start Voting");
            Optional<ButtonType> result = a.showAndWait();
            if(result.get() == ButtonType.OK) {

                bntstop.setDisable(false);
                bntstart.setDisable(true);

                updateVoting(0,1);

                Parent rootAdminCandidate = FXMLLoader.load(getClass().getResource("admin_options_candidate.fxml"));
                Stage stageAdminOptionCandidate = new Stage();
                Scene sceneAdminOptionCandidate = new Scene(rootAdminCandidate, 825, 600);
                stageAdminOptionCandidate.setScene(sceneAdminOptionCandidate);
                stageAdminOptionCandidate.setTitle("Admin Options");
                stageAdminOptionCandidate.resizableProperty().setValue(false);
                stageAdminOptionCandidate.show();

                Stage primaryStage = (Stage) bntstart.getScene().getWindow();
                primaryStage.close();
            }
        }else {
            a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Candidates or Voters are empty");
            a.showAndWait();
        }


    }

    /**
     * @button  "Stop" (This function is executed by clicking)
     * will popup a massage to confirm the action
     * stop button will disable and start button will show and update status in voting table
     * id = 1, status will 0
     * id = 2, status will 1
     * Will undisable ADD,EDIT,VIEW and DELETE buttons
     * Will show window with voting results for all candidates
     */
    public void stopVoting() throws SQLException, IOException {

        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Stop Voting");
        Optional<ButtonType> result = a.showAndWait();

        if(result.get() == ButtonType.OK) {

            bntstop.setDisable(true);
            bntstart.setDisable(false);

            updateVoting(1,0);

            Parent rootAdminCandidate = FXMLLoader.load(getClass().getResource("admin_options_candidate.fxml"));
            Stage stageAdminOptionCandidate = new Stage();
            Scene sceneAdminOptionCandidate = new Scene(rootAdminCandidate, 825, 600);
            stageAdminOptionCandidate.setScene(sceneAdminOptionCandidate);
            stageAdminOptionCandidate.setTitle("Admin Options");
            stageAdminOptionCandidate.resizableProperty().setValue(false);
            stageAdminOptionCandidate.show();

            Parent rootAddCan = FXMLLoader.load(getClass().getResource("VotingResults.fxml"));
            Stage stageAddCan = new Stage();
            Scene sceneAddCan = new Scene(rootAddCan,1160, 879);
            stageAddCan.setScene(sceneAddCan);
            stageAddCan.setTitle("Results");
            stageAddCan.resizableProperty().setValue(false);
            stageAddCan.show();

            Stage primaryStage = (Stage) bntstart.getScene().getWindow();
            primaryStage.close();
        }


    }

    /**
     * update data in voting table according to the action taken by admin(Start voting/Stop voting)
     */
    public void updateVoting(int start, int stop) throws SQLException {
        JDSCON updatevotings = new JDSCON();
        Connection con = updatevotings.jdbcConnect2();

        String queryUpdateSt = "UPDATE `voting` SET `status` = ? WHERE `ID` =1";
        String queryUpdateSp = "UPDATE `voting` SET `status` = ? WHERE `ID` =2";

        PreparedStatement prstart = con.prepareStatement(queryUpdateSt);
        PreparedStatement prstop = con.prepareStatement(queryUpdateSp);

        Statement ststatus;
        ResultSet rsstatus = null;

        try {
            prstart.setInt(1,start);
            prstop.setInt(1,stop);
            prstart.executeUpdate();
            prstop.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
