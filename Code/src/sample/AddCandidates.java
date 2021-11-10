package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AddCandidates {
    @FXML
    private TextField txtname;
    @FXML
    private TextField txtNIC;
    @FXML
    private TextField txtTelNo;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtAddressNo;
    @FXML
    private TextField txtStreet;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtState;
    @FXML
    private TextField txtPostCode;

    @FXML
    private Button bntNext;

    @FXML
    private Label lblname;
    @FXML
    private Label lblNIC;
    @FXML
    private Label lblTelNo;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblAddressNo;
    @FXML
    private Label lblStreet;
    @FXML
    private Label lblCity;
    @FXML
    private Label lblState;
    @FXML
    private Label lblPostCode;

    @FXML
    private Hyperlink backLogin;

    @FXML
    private DatePicker dateDOB;
    @FXML
    private RadioButton radCitizen;
    @FXML
    private TextField txtPartyName;
    @FXML
    private TextField txtPartyNumber;
    @FXML
    private TextField txtColorName;
    @FXML
    private ColorPicker colorPartyColor;
    @FXML
    private TextField txtSymbolLink;
    @FXML
    private RadioButton radDeposit;


    @FXML
    private Label lblDOB;
    @FXML
    private Label lblCitizen;
    @FXML
    private Label lblPartyName;
    @FXML
    private Label lblPartyNumber;
    @FXML
    private Label lblPartyColor;
    @FXML
    private Label lblSymbolLink;
    @FXML
    private Label lblDeposit;

    @FXML
    private Button bntSubmit;

    static String name ;
    static String NIC;
    static String Tel;
    static String email;
    static String addressNo;
    static String Street;
    static String State;
    static String city;
    static String postCode;
    static String DOB;
    static String Citizenship;
    static String PartyName;
    static String ColorName;
    static String PartyNumber;
    static String SymbolLink;
    static String Deposit;

    /**
     * validate all the inputs in 1st add candidate window
     * @return boolean: if at least one of validation failed - false ; all correct - true
     */
    public boolean addpart1Validate() {
        // making all labels null
        lblname.setText(null);
        lblNIC.setText(null);
        lblTelNo.setText(null);
        lblEmail.setText(null);
        lblAddressNo.setText(null);
        lblStreet.setText(null);
        lblState.setText(null);
        lblCity.setText(null);
        lblPostCode.setText(null);

        List<Boolean> fine = new ArrayList<>();
        fine.add(true);

        // check any of textfields are empty
        fine.add(NullValuevalidate(txtname,lblname,"Name is Empty","txt"));
        fine.add(NullValuevalidate(txtNIC,lblNIC,"NIC is Empty","txt"));
        fine.add(NullValuevalidate(txtEmail,lblEmail,"Email is Empty","txt"));
        fine.add(NullValuevalidate(txtTelNo,lblTelNo,"Telephone number is Empty","txt"));
        fine.add(NullValuevalidate(txtAddressNo,lblAddressNo,"Address is Empty","txt"));
        fine.add(NullValuevalidate(txtCity,lblCity,"City is Empty","txt"));
        fine.add(NullValuevalidate(txtPostCode,lblPostCode,"PostCode is Empty","txt"));
        fine.add(NullValuevalidate(txtState,lblState,"State is Empty","txt"));
        if(fine.contains(false)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fill empty fields");
            alert.showAndWait();
            return false;
        }

        //check limit of telephone no, NIC and PostCode
        fine.add(limitset(txtTelNo, 10, lblTelNo));
        if(fine.contains(false)){
            return false;
        }
        fine.add(limitset(txtNIC, 11, lblNIC));
        if(fine.contains(false)){
            return false;
        }
        fine.add(limitset(txtPostCode, 6, lblPostCode));
        if(fine.contains(false)){
            return false;
        }

        //check the type of telephone number and postcode
        fine.add(typecheck(txtTelNo, lblTelNo));
        if(fine.contains(false)){
            return false;
        }
        fine.add(typecheck(txtPostCode, lblPostCode));

        if(fine.contains(false)){
            return false;
        }else{
            return true;
        }
    }

    /**
     * @button "Next"(method will execute)
     * get all inputs after validating
     * assign all the inputs to variables
     * and loading add candidate 2nd window
     */
    public void addPart1() throws IOException {
        boolean fine = addpart1Validate();
        if(fine) {

            try {
                name = txtname.getText();
                NIC = txtNIC.getText();
                Tel = txtTelNo.getText();
                email = txtEmail.getText();
                addressNo = txtAddressNo.getText();
                Street = txtStreet.getText();
                State = txtState.getText();
                city = txtCity.getText();
                postCode = txtPostCode.getText();
            } catch (Exception e) {
                e.getCause();
            }
        }

        if (fine) {
            Parent rootAdd2Can = FXMLLoader.load(getClass().getResource("add2.fxml"));
            Stage stageAdd2Can = new Stage();
            Scene sceneAdd2Can = new Scene(rootAdd2Can, 1160, 879);
            stageAdd2Can.setScene(sceneAdd2Can);
            stageAdd2Can.setTitle("Add Candidates");
            stageAdd2Can.resizableProperty().setValue(false);
            stageAdd2Can.show();

            Stage primaryStage = (Stage) bntNext.getScene().getWindow();
            primaryStage.close();
        }
    }

    /**
     * validate all the inputs in 2nd add candidate window
     * @return boolean: if at least one of validation failed - false ; all correct - true
     */
    public boolean addpart2Validate() throws SQLException {
        // making all labels null
        lblDOB.setText("");
        lblPartyColor.setText("");
        lblDeposit.setText("");
        lblCitizen.setText("");
        lblSymbolLink.setText("");
        lblPartyName.setText("");
        lblPartyNumber.setText("");
        List<Boolean> fine = new ArrayList<>();
        fine.add(true);


        // check any of textfields are empty
        fine.add(NullValuevalidate(txtPartyName,lblPartyName,"Party Name is Empty","txt"));
        fine.add(NullValuevalidate(txtPartyNumber,lblPartyNumber,"Party Number is Empty","txt"));
        fine.add(NullValuevalidate(txtSymbolLink,lblSymbolLink,"Symbole Link is Empty","txt"));
        fine.add(NullValuevalidate(radCitizen,lblCitizen,"Citizenship is Empty","rad"));
        fine.add(NullValuevalidate(radDeposit,lblDeposit,"Deposit is Empty","rad"));
        fine.add(NullValuevalidate(colorPartyColor,lblPartyColor,"Color is not selected","col"));
        fine.add(NullValuevalidate(dateDOB,lblDOB,"Date of birth is not selected","date"));
        fine.add(NullValuevalidate(txtColorName,lblPartyColor,"Street is Empty","txt"));
        if(fine.contains(false)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fill empty fields");
            alert.showAndWait();
            return false;
        }

        //check the type of party number(election number)
        fine.add(typecheck(txtPartyNumber,lblPartyNumber));
        //check weather election no is no used from the database
        if(!(fine.contains(false))) {
            boolean check = checkELectionNo(Integer.parseInt(txtPartyNumber.getText()),lblPartyNumber);
            fine.add(check);
            System.out.println(false);
        }

        if(fine.contains(false)){
            return false;
        }else{
            return true;
        }
    }

    /**
     *
     * @param number
     * @param lbl
     * @return boolean
     * @throws SQLException
     * check weather number is not in the candidate table already
     */
    public boolean checkELectionNo(int number, Label lbl) throws SQLException {
        JDSCON checknumber = new JDSCON();
        Connection con = checknumber.jdbcConnect2();

        String query = "SELECT `election_no` FROM `candidate`";
        System.out.println(query);

        ResultSet rs = null;
        Statement st;

        try{
            st = con.createStatement();
            rs = st.executeQuery(query);
            System.out.println(rs.getRow());
            while (rs.next()) {

                System.out.println(rs.getInt(1));
                if (rs.getInt(1) == number) {
                    System.out.println("duplicate");
                    lbl.setText("*");
                    System.out.println(false);
                    return false;

                } else {
                    System.out.println(true);

                }
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }


    /**
     * @button "Submit"(method will execute)
     * get all inputs after validating in 2nd add candidate window
     * assign all the inputs to variables
     * and insert all data to database
     * if it success load admin option window
     * @throws IOException
     * @throws SQLException
     */
    public void submitData() throws IOException, SQLException {
        boolean fine = addpart2Validate();
        JDSCON insertcommand = new JDSCON();

        if (fine) {
            // get connection status with database
            boolean flag = insertcommand.jdbcConnect();

            // assigning variables with user inputs in 2nd add candidate window
            try {
                DOB = dateDOB.getValue().toString();
                Citizenship = radCitizen.getText();
                PartyName = txtPartyName.getText();
                ColorName = txtColorName.getText() + "-"+ colorPartyColor.getValue();
                PartyNumber = txtPartyNumber.getText();
                SymbolLink = txtSymbolLink.getText();
                Deposit = radDeposit.getText();
            } catch (Exception e) {
                e.getCause();
            }

            // creating the sql query and insert
            String query = "INSERT INTO candidate(`Name`, `NIC`, `tele_no`, `email`, `addressNo`, `street`, `city`, `state`, `poscode`, `DOB`, `citizenship`, `Partyname`, `colour`, `election_no`, `sign`, `deposit`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

            String Datalist[] = {name, NIC, Tel, email, addressNo, Street, city, State, postCode, DOB, Citizenship, PartyName, ColorName, PartyNumber, SymbolLink, Deposit};
            System.out.println(Arrays.toString(Datalist));
            try {
                int rows = insertcommand.executeSQLinsert(query, Datalist);

                // show confirmation alert with candidate id, which just created
                showConfirmAlert(Integer.parseInt(PartyNumber));

                System.out.println(rows);
                Parent rootAdminCandidate = FXMLLoader.load(getClass().getResource("admin_options_candidate.fxml"));
                Stage stageAdminOptionCandidate = new Stage();
                Scene sceneAdminOptionCandidate = new Scene(rootAdminCandidate, 825, 600);
                stageAdminOptionCandidate.setScene(sceneAdminOptionCandidate);
                stageAdminOptionCandidate.setTitle("Admin Options");
                stageAdminOptionCandidate.resizableProperty().setValue(false);
                stageAdminOptionCandidate.show();


                Stage primaryStage2 = (Stage) bntSubmit.getScene().getWindow();
                primaryStage2.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * get the candidate id which auto_matically created
     * when inserting the data
     * show alert with candidate id
     * @param election_no
     */
    public void showConfirmAlert(int election_no){

        JDSCON insertcommand = new JDSCON();
        Connection con = insertcommand.jdbcConnect2();

        String getIdQuery = "Select candidate_id from candidate where election_no = "+election_no;

        System.out.println(getIdQuery);

        ResultSet rs;
        Statement st;
        int id = 0;

        try{
            st = con.createStatement();
            rs = st.executeQuery(getIdQuery);
            while(rs.next()) {
                id = rs.getInt(1);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Data Added Successfully.\n" +
                "Candidate - "+name+"\n" +
                "Candidate ID - "+id);
        a.showAndWait();



    }

    /**
     * check whether node is empty
     * @return boolean; if empty - false ; if not empty - true
     */
    public boolean NullValuevalidate(Node node, Label label,String text,String NodeType){
        boolean empty = true;

        if(NodeType == "txt"){
            TextField txt = (TextField) node;
            if (txt.getText().isEmpty()){
                label.setText("*");
                System.out.println(text);
                empty = false;
            }
        }
        if (NodeType == "rad") {
            System.out.println("rad in");
            RadioButton rad;
            rad = (RadioButton) node;
            if (!(rad.isSelected())) {
                label.setText("*");
                System.out.println(text);
                empty = false;
            }
        }

        if (NodeType == "col") {
            ColorPicker col = (ColorPicker) node;
            if (col.isPressed()) {
                label.setText("*");
                System.out.println(text);
                empty = false;
            }
        }

        if (NodeType == "date") {
            DatePicker date = (DatePicker) node;
            if (date.getValue() == null) {
                label.setText("*");
                System.out.println(text);
                empty = false;
            }
        }


        return empty;
    }

    /**
     * check whether data input in textfied is in correct type
     * @return boolean; if correct - true ; if not correct - false
     */
    public boolean typecheck(TextField field, Label label){
        boolean fine = true;
        try{
            long integers = Long.parseLong(field.getText());
        }catch (Exception e){
            fine = false;
            System.out.println("Integer should input"+ label.getText());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(field.getText()+" is incorrect\nTry again with numbers");
            alert.showAndWait();
            e.getCause();
            label.setText("*");
        }
        return fine;
    }

    /**
     * check whether data input in textfied's length is correct
     * @return boolean; if correct - true ; if not correct - false
     */
    public boolean limitset(@NotNull TextField field, int maxlength , Label label){
        boolean fine = true;
        if (!(field.getText().length() == maxlength)){
            fine = false;
            System.out.println("range not valid");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(field.getText()+" is not in correct range\nTry again");
            alert.showAndWait();
            label.setText("*");
            System.out.println(field.getText());
        }
        return fine;

    }

    /**
     * @button "Back" (This function is executed by clicking)
     * Load 1st add candidate window
     */
    public void backpart1() throws IOException {

        Parent rootAddCan = FXMLLoader.load(getClass().getResource("add.fxml"));
        Stage stageAddCan = new Stage();
        Scene sceneAddCan = new Scene(rootAddCan,1160, 879);
        stageAddCan.setScene(sceneAddCan);
        stageAddCan.setTitle("Add Candidates");
        stageAddCan.resizableProperty().setValue(false);
        stageAddCan.show();

        Stage primaryStage2 = (Stage) bntSubmit.getScene().getWindow();
        primaryStage2.close();

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


        Stage primaryStage = (Stage) bntNext.getScene().getWindow();
        primaryStage.close();
    }
}

