package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * controller for view.fxml
 */
public class ViewController implements Initializable {
    @FXML
    private Hyperlink hypVoters;
    @FXML
    private Hyperlink hypDELETE;
    @FXML
    private Hyperlink hypEDIT;
    @FXML
    private Hyperlink hypADD;
    @FXML
    private Hyperlink backLogin;

    @FXML
    private Button bntloadData;
    @FXML
    private TableColumn<Candidate , String > PRID;
    @FXML
    private TableView<Candidate> cantable;
    @FXML
    private TableColumn<Candidate, String> PRNIC;
    @FXML
    private TableColumn<Candidate, String> PRname;
    @FXML
    private TableColumn<Candidate, String> PRTel;
    @FXML
    private TableColumn<Candidate, String> PRemail;
    @FXML
    private TableColumn<Candidate, String> PRaddress;
    @FXML
    private TableColumn<Candidate, String> PRstreet;
    @FXML
    private TableColumn<Candidate, String> PRcity;
    @FXML
    private TableColumn<Candidate, String> PRstate;
    @FXML
    private TableColumn<Candidate, Integer> PRpostcode;
    @FXML
    private TableColumn<Candidate, Date> PRDOB;
    @FXML
    private TableColumn<Candidate, String> PRcitizen;
    @FXML
    private TableColumn<Candidate, String> partyName;
    @FXML
    private TableColumn<Candidate, String> Partycolor;
    @FXML
    private TableColumn<Candidate, String> PartyNumber;
    @FXML
    private TableColumn<Candidate, String> PartySymbole;
    @FXML
    private TableColumn<Candidate, String> PartyDeposit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        JDSCON startingstatus = new JDSCON();
        Connection con = startingstatus.jdbcConnect2();

        String query = "SELECT * FROM `voting` LIMIT 1";

        Statement st;
        ResultSet rs;
        try{
            st = con.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs);
                if (rs.getInt(2) == 0) {
                    hypADD.setDisable(true);
                    hypEDIT.setDisable(true);
                    hypDELETE.setDisable(true);
                }else {
                    hypVoters.setDisable(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @button "Load Data"
     * load date from database
     * set data to table
     */
    public void loadDataTable(){
        JDSCON tableview = new JDSCON();

        ObservableList<Candidate> oblist;

        oblist = tableview.loadDataTable();

        PRID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        PRname.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PRNIC.setCellValueFactory(new PropertyValueFactory<>("NIC"));
        PRTel.setCellValueFactory(new PropertyValueFactory<>("TelNo"));
        PRemail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        PRaddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        PRstreet.setCellValueFactory(new PropertyValueFactory<>("Street"));
        PRcity.setCellValueFactory(new PropertyValueFactory<>("City"));
        PRstate.setCellValueFactory(new PropertyValueFactory<>("State"));
        PRpostcode.setCellValueFactory(new PropertyValueFactory<>("Postcode"));
        PRDOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        PRcitizen.setCellValueFactory(new PropertyValueFactory<>("Citizen"));
        partyName.setCellValueFactory(new PropertyValueFactory<>("Pname"));
        Partycolor.setCellValueFactory(new PropertyValueFactory<>("Pcolor"));
        PartyNumber.setCellValueFactory(new PropertyValueFactory<>("Pnumber"));
        PartySymbole.setCellValueFactory(new PropertyValueFactory<>("Psymbol"));
        PartyDeposit.setCellValueFactory(new PropertyValueFactory<>("Deposit"));

        try {
            cantable.setItems(oblist);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(cantable);
        }
    }

    /**
     * @button "<- back to login" (This function is executed by clicking)
     * Load admin login window
     */
    public void backToLogin() throws IOException {
        Parent rootAddCan = FXMLLoader.load(getClass().getResource("admin_login.fxml"));
        Stage stageAddCan = new Stage();
        Scene sceneAddCan = new Scene(rootAddCan,1100, 600);
        stageAddCan.setScene(sceneAddCan);
        stageAddCan.setTitle("Login");
        stageAddCan.resizableProperty().setValue(false);
        stageAddCan.show();

        Stage primaryStage = (Stage) backLogin.getScene().getWindow();
        primaryStage.close();
    }

    /**
     * @button "ADD" (This function is executed by clicking)
     * Load 1st add candidate window
     */
    public void addCan() throws IOException{
        Parent rootAddCan = FXMLLoader.load(getClass().getResource("add.fxml"));
        Stage stageAddCan = new Stage();
        Scene sceneAddCan = new Scene(rootAddCan,1160, 879);
        stageAddCan.setScene(sceneAddCan);
        stageAddCan.setTitle("Add Candidates");
        stageAddCan.resizableProperty().setValue(false);
        stageAddCan.show();

        Stage primaryStage = (Stage) backLogin.getScene().getWindow();
        primaryStage.close();
    }

    /**
     * @button "EDIT" (This function is executed by clicking)
     * Load edit/update candidate window
     */
    public void editCan() throws IOException{
        Parent rootEditCan = FXMLLoader.load(getClass().getResource("edit.fxml"));
        Stage stageEditCan = new Stage();
        Scene sceneEditCan = new Scene(rootEditCan,1160, 879);
        stageEditCan.setScene(sceneEditCan);
        stageEditCan.setTitle("Edit Candidates");
        stageEditCan.resizableProperty().setValue(false);
        stageEditCan.show();

        Stage primaryStage = (Stage) backLogin.getScene().getWindow();
        primaryStage.close();
    }

    /**
     * @button "DELETE" (This function is executed by clicking)
     * Load delete candidate window
     */
    public void deleteCan() throws IOException{
        Parent rootDeleteCan = FXMLLoader.load(getClass().getResource("delete.fxml"));
        Stage stageDeleteCan = new Stage();
        Scene sceneDeleteCan = new Scene(rootDeleteCan,1906, 873);
        stageDeleteCan.setScene(sceneDeleteCan);
        stageDeleteCan.setTitle("Delete Candidates");
        stageDeleteCan.resizableProperty().setValue(false);
        stageDeleteCan.show();

        Stage primaryStage = (Stage) backLogin.getScene().getWindow();
        primaryStage.close();
    }

    /**
     * @HyperLink Voters
     * load voter's login page
     */
    public void Voters() throws IOException {
        Parent rootAdminCandidate = FXMLLoader.load(getClass().getResource("voters_login.fxml"));
        Stage stageAdminOptionCandidate = new Stage();
        Scene sceneAdminOptionCandidate = new Scene(rootAdminCandidate,1100,600);
        stageAdminOptionCandidate.setScene(sceneAdminOptionCandidate);
        stageAdminOptionCandidate.setTitle("Voters Login");
        stageAdminOptionCandidate.resizableProperty().setValue(false);
        stageAdminOptionCandidate.show();


        Stage primaryStage = (Stage) bntloadData.getScene().getWindow();
        primaryStage.close();
    }

    /**
     * @button "Candidates" (This function is executed by clicking)
     * Load admin option window
     */
    public void adminOptions() throws IOException {
        Parent rootAdminCandidate = FXMLLoader.load(getClass().getResource("admin_options_candidate.fxml"));
        Stage stageAdminOptionCandidate = new Stage();
        Scene sceneAdminOptionCandidate = new Scene(rootAdminCandidate,825,600);
        stageAdminOptionCandidate.setScene(sceneAdminOptionCandidate);
        stageAdminOptionCandidate.setTitle("Admin Options");
        stageAdminOptionCandidate.resizableProperty().setValue(false);
        stageAdminOptionCandidate.show();


        Stage primaryStage = (Stage) bntloadData.getScene().getWindow();
        primaryStage.close();
    }


}
