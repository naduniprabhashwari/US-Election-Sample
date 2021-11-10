package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DeleteCandidate {

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
     * delete selected row from table and
     * call method getDataforSelectedID() to delete selected data from database
     * @throws SQLException
     */
    public void deleteSelectedRow() throws SQLException {
        int selectedRow = cantable.getSelectionModel().getSelectedIndex();
        if (selectedRow >= 0) {
            if(DeleteDataforSelectedID()) {
                cantable.getItems().remove(selectedRow);
            }
        } else {
            // Nothing selected.
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("No Selection");
            a.setHeaderText("No Person Selected");
            a.setContentText("Please select a person in the table.");
            a.showAndWait();
        }
    }

    /**
     * delete selected data from database
     * @return
     * @throws SQLException
     */
    public boolean DeleteDataforSelectedID() throws SQLException {
        Candidate selectedCandidate = cantable.getSelectionModel().getSelectedItem();
        if(selectedCandidate != null){
            int id = Integer.parseInt(selectedCandidate.getID());
            String quary = "delete from candidate where `candidate_id`="+id;

            JDSCON connecton = new JDSCON();
            Connection connect = connecton.jdbcConnect2();
            Statement st;
            ResultSet rs;

            try {
                st = connect.createStatement();
                int result = st.executeUpdate(quary);
                boolean resutlD = deleteResult(Integer.parseInt(selectedCandidate.getPnumber()));
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setHeaderText(result+" Candidate Deleted Successful");
                a.setContentText("Deleted Candidate ID- "+id);
                a.showAndWait();
                return resutlD;
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    /**
     * delete candidate from from result table
     * @return
     */
    public boolean deleteResult(int id){
        String query = "delete from result where `candidate_id`="+id;

        JDSCON connecton = new JDSCON();
        Connection connect = connecton.jdbcConnect2();
        Statement st;
        ResultSet rs;

        try {
            st = connect.createStatement();
            int result = st.executeUpdate(query);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
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
     * @button "VIEW" (This function is executed by clicking)
     * Load view candidate window
     */
    public void viewCan() throws IOException{
        Parent rootViewCan = FXMLLoader.load(getClass().getResource("view.fxml"));
        Stage stageViewCan = new Stage();
        Scene sceneViewCan = new Scene(rootViewCan,1906, 879);
        stageViewCan.setScene(sceneViewCan);
        stageViewCan.setTitle("View Candidates");
        stageViewCan.resizableProperty().setValue(false);
        stageViewCan.show();

        Stage primaryStage = (Stage) backLogin.getScene().getWindow();
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
