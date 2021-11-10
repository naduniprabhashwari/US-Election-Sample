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
import java.util.ResourceBundle;

public class VotingBallot{


    @FXML
    private Button bntFinishVoting;
    @FXML
    private TableView<BallotCandidate> votingTB;
    @FXML
    private TableColumn<BallotCandidate, String> votingTBName;
    @FXML
    private TableColumn<BallotCandidate , Integer> votingTBNo;
    @FXML
    private TableColumn votingTBVote;

    @FXML
    private ObservableList<BallotCandidate> obCandidate;



    /**
     * get election number and name of the all candidates in database
     * @return result set
     */
    public ResultSet loadCandidateData(){
        JDSCON loadCanData = new JDSCON();
        Connection con = loadCanData.jdbcConnect2();

        String query = "SELECT election_no, Name FROM candidate";

        ResultSet rs = null;
        Statement st;

        try {
            st = con.createStatement();
            rs = st.executeQuery(query);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }

    /**
     * get result set from loadCandidateData()
     * show result sent in the table
     * with radio button to vote in every row
     * @throws SQLException
     */
    public void showBallot() throws SQLException {
        // get result set of candidates
        ResultSet rs = loadCandidateData();
        obCandidate = FXCollections.observableArrayList();
        ToggleGroup radgroup = new ToggleGroup();
        List<RadioButton> radioButtonList = new ArrayList<>();

        while (rs.next()){
            // adding result set candidates to observable list
            obCandidate.add( new BallotCandidate(rs.getInt(1),rs.getString(2)));
            // grouping all radio buttons created in each row to one toggle group
            obCandidate.get(obCandidate.size()-1).getVote().setToggleGroup(radgroup);
        }
        //setting columns
        votingTBNo.setCellValueFactory(new PropertyValueFactory<>("Id"));
        votingTBName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        votingTBVote.setCellValueFactory(new PropertyValueFactory<>("Vote"));
        // set observable list to tableview
        votingTB.setItems(obCandidate);

    }

    /**
     * get selected radio button from the table
     * @return BallotCandidate
     */
    public BallotCandidate getSelectedRows(){
        BallotCandidate selectedCandidate = null;

        for (BallotCandidate ballotCanData: obCandidate) {
            if(ballotCanData.getVote().isSelected()){
                selectedCandidate = ballotCanData;
            }
        }
        return selectedCandidate;
    }

    /**
     * get the selectedCandidate from getSelectedRows()
     * get the old number of voting of the candidate from getvotings()
     * if votings are zero new vote is inserted to the result table
     * if votings are not zero vote is updated in the result table
     * @throws SQLException
     */
    public void StoreVotes() throws SQLException {
        JDSCON storedata = new JDSCON();
        Connection con = storedata.jdbcConnect2();

        BallotCandidate selectedCandidate = getSelectedRows();
        PreparedStatement psInsertResult;

        Long votes = getvotings(selectedCandidate.getId());
        if(votes == 1){
            psInsertResult = con.prepareStatement("INSERT INTO `uselection`.`Result` (`candidate_id`, `No_of_votes`) VALUES (?, ?)");
            {
                psInsertResult.setInt(1,selectedCandidate.getId());
                psInsertResult.setLong(2,votes);
            }
        }else {
            psInsertResult = con.prepareStatement("UPDATE `uselection`.`result` SET `No_of_votes` = ? WHERE `result`.`candidate_id` =?");
            {
                psInsertResult.setLong(1,votes);
                psInsertResult.setInt(2,selectedCandidate.getId());
            }
        }

        ResultSet rs;
        Statement st;

        try{
            int i = psInsertResult.executeUpdate();
            System.out.println(i);
            if(i == 1){

                VoterLoginController vlc = new VoterLoginController();
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setContentText("Vote Stored Successfully\n Thank you!");
                a.showAndWait();


                Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
                Stage Stage = new Stage();
                Stage.setScene(new Scene(root, 1100, 600));
                Stage.setTitle("Main Login");
                Stage.show();

                Stage primary = (Stage) bntFinishVoting.getScene().getWindow();
                primary.close();
            }

        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * get number of vptes from result table for id
     * add the voting to votings variable
     * @param id
     * @return votings
     */
    public Long getvotings(int id){
        String query = "SELECT `No_of_votes` FROM `result` WHERE `candidate_id` ="+id;

        JDSCON connect = new JDSCON();
        Connection con = connect.jdbcConnect2();

        Statement st;
        ResultSet rs;

        long votings = 1;
        try{
            st = con.createStatement();
            rs = st.executeQuery(query);
            if(rs != null) {
                while (rs.next()) {
                    votings = votings + rs.getInt(1);
                }
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return votings;
    }



}
