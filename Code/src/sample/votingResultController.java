package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class votingResultController {

    @FXML
    private TableColumn<ResultCandidate , Integer> votesTB;
    @FXML
    private TableView<ResultCandidate> votingTB;
    @FXML
    private TableColumn<ResultCandidate , Integer> votingTBNo;
    @FXML
    private TableColumn<ResultCandidate, String> votingTBName;
    @FXML
    private TableColumn votingTBVote;

    /**
     * take the records in result table
     * take names of the candidates from candidate table
     *
     */
    @FXML
    private void VotingResults() throws SQLException {
        // get result set of records in result table
        ResultSet results = loadResults();

        List<Integer> electionNos = new ArrayList<>();

        ObservableList<ResultCandidate> obCandidate = FXCollections.observableArrayList();

        //get each record in result set
        while (results.next()) {
            // get name of the candidate whoes election no is in the result set
            ResultSet candidateData = loadCandidateResultData(results.getInt(1));

            //get name from the result set of the loadCandidateResultData()
            while (candidateData.next()) {

                //creating new ResultCandidate and adding it to the obCandidate ObservableList
                obCandidate.add(new ResultCandidate(results.getInt(1), candidateData.getString(1), results.getInt(2)));
                //formatting label color and width according to no of votes taken
                Label lbl = obCandidate.get(obCandidate.size() - 1).getLabel();
                lbl.setPrefWidth(results.getInt(2) * 10);
                lbl.setStyle("-fx-background-color:red;");
                lbl.setPrefHeight(23.0);
                //printing vote details in commandline
                System.out.print(results.getInt(1) + "        -  " + candidateData.getString(1) + "                                -  " + results.getInt(2) + "  | ");
                for (int i = 0; i < results.getInt(2); i++) {
                    System.out.print("***");
                }
                System.out.println();

                //adding election nos of candidates whose votes > 0
                electionNos.add(results.getInt(1));
            }


        }

        //get the result set with names of the candidates from loadZeroVotesCandidate() method
        ResultSet zeroCandidates = loadZeroVotesCandidate(electionNos);
        while (zeroCandidates.next()) {
            //creating new ResultCandidate and adding it to the obCandidate ObservableList
            obCandidate.add(new ResultCandidate(zeroCandidates.getInt(1), zeroCandidates.getString(2), 0));

            //formatting label width to zero
            Label lbl = obCandidate.get(obCandidate.size() - 1).getLabel();
            lbl.setPrefWidth(0);
            System.out.println(zeroCandidates.getInt(1) + "        -  " + zeroCandidates.getString(2) + "                                -  " + 0 + "  | ");

        }

        //connecting the table columns with variables in ResultCandidate
        votingTBNo.setCellValueFactory(new PropertyValueFactory<>("ElectionNo"));
        votingTBName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        votesTB.setCellValueFactory(new PropertyValueFactory<>("Votes"));
        votingTBVote.setCellValueFactory(new PropertyValueFactory<>("Label"));

        //setting data to table
        votingTB.setItems(obCandidate);

    }

    /**
     * Create the connection with the database calling jdbcConnect2() in JDSCON class
     * execute the query to get all the records in result table
     * @return result set 'rs'
     */
    public ResultSet loadResults() {
        JDSCON loadCanData = new JDSCON();
        Connection con = loadCanData.jdbcConnect2();

        String query = "SELECT * FROM `result`";


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
     * Create the connection with the database calling jdbcConnect2() in JDSCON class
     * execute the query to get the name of the candidate whoes election no is not in the electionNo list
     * @return result set 'rs'
     */
    public ResultSet loadZeroVotesCandidate(List<Integer> electioNo) {
        JDSCON loadCanData = new JDSCON();
        Connection con = loadCanData.jdbcConnect2();

        String query = "SELECT `election_no` , `Name` FROM `candidate` WHERE `election_no` NOT IN (";
        for (int i = 0;i<electioNo.size();i++) {
            if(i < electioNo.size()-1) {
                query = query + electioNo.get(i) + ",";
            }else {
                query = query + electioNo.get(i) + ")";
            }
        }

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
     * Create the connection with the database calling jdbcConnect2() in JDSCON class
     * execute the query to get the name of the candidate whoes election no = electionNO
     * @return result set 'rs'
     */
    public ResultSet loadCandidateResultData(int electioNo) {
        JDSCON loadCanData = new JDSCON();
        Connection con = loadCanData.jdbcConnect2();

        String query = "SELECT candidate.`Name` FROM `candidate` where election_no ="+electioNo;

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
}
