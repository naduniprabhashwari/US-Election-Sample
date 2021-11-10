package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * controller for edit.fxml
 */
public class EditCandidate {

    @FXML
    private Button bntloaddata;
    @FXML
    private Button bntsearch;
    @FXML
    private Hyperlink backLogin;

    @FXML
    private GridPane gridPanePersonal;
    @FXML
    private GridPane gridPaneParty;
    @FXML
    private ComboBox<String> ComboCandidate;
    @FXML
    private Button anotherCandidate;
    @FXML
    private ComboBox<String> ComboField;
    @FXML
    private Button anotherField;
    @FXML
    private GridPane gridPane;
    @FXML
    private Button bntUpdate;

    /**
     * @button "Search"(Method will execute)
     * get all candidate ids in the candidate table in the database
     * add all ids to a observable list
     * @return ObservableList = ob
     */
    public ObservableList<String> getCandidateIDs(){
        ObservableList<String> ob = FXCollections.observableArrayList();
        JDSCON connecton = new JDSCON();
        Connection connect = connecton.jdbcConnect2();

        String quary = "SELECT `candidate_id` FROM candidate";

        Statement st;
        ResultSet rs;

        try {
            st = connect.createStatement();
            rs = st.executeQuery(quary);
            while(rs.next()){
                ob.add(rs.getString(1));
            }
        }catch (Exception e){
            e.getCause();
        }
        return ob;
    }

    /**
     * get Observable List of candidate ids from getCandidateIDs()
     * add the list as items of the Combo box
     * call assignList()
     */
    public void showcandidateIDs(){
        ObservableList<String> ob = getCandidateIDs();
        ComboCandidate.setItems(ob);
        assignList();
        bntsearch.setPrefWidth(267);
    }

    /**
     * get candidate data from the database from the id
     * which selected by the user on combo box
     * @return Observable List = ob
     * @throws SQLException
     */
    public ObservableList<String> getDataforSelectedID() throws SQLException {
        String id = null;

        // get selected value from the combo box
        if(ComboCandidate.getValue() != null) {
            id = ComboCandidate.getValue();

            ObservableList<String> ob = FXCollections.observableArrayList();
            JDSCON connecton = new JDSCON();
            Connection connect = connecton.jdbcConnect2();

            // create the sql query
            String quary = "SELECT * FROM `candidate` WHERE `candidate_id` = ?";

            PreparedStatement preparedStatement = connect.prepareStatement(quary, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); {
                preparedStatement.setString(1, id);}

            ResultSet rs;

            // execute the query
            try {
                rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    // add all data to a observable list
                    ObservableList<String> obsub = FXCollections.observableArrayList();
                    for(int i = 1; i<=17;i++){
                        obsub.add(rs.getString(i));
                    }
                    ob.addAll(obsub);
                }
                return ob;
            } catch (SQLException e) {
                e.getCause();
            }
        }
        return null;
    }

    /**
     *  take the returned observable list from getDataforSelectedID()
     *  make two sublists from the list with personal detail and party details
     *  add sublist data to the textfield according to the user input of the combofield(Personal/Party)
     * @throws SQLException
     */
    public void sublisting() throws SQLException {
        String slValue;

        // get returned observable list
        ObservableList<String> ob = getDataforSelectedID();

        List<Node> nodeob = new ArrayList<>();
        List<String> sublist = new ArrayList<>();

        // consider the selected value from ComboField by user
        if(ComboField.getValue() != null){
            // get the value of ComboField to a variable
            slValue = ComboField.getValue();

            // check the value equal to 'Personal' or 'Party'
            if(slValue.equals("Personal")){

                //get a sublist from ob(ObservableList) which include personal data
                sublist =  ob.subList(0,12);

                // visible gridpane which have only output personal data
                gridPanePersonal.setVisible(true);

                // take the textfields from the gridpane
                nodeob =  gridPanePersonal.getChildren().subList(9,18);

                // set sublist data to tetfields
                for (int i=0;i<nodeob.size();i++){
                    TextField tn = (TextField) nodeob.get(i);
                    tn.setText(sublist.get(i+1));
                }
            }else{
                //get a sublist from ob(ObservableList) which include party data
                sublist = ob.subList(12,17);

                // visible gridpane which have only output party data
                gridPaneParty.setVisible(true);

                // take the textfields from the gridpane
                nodeob =  gridPaneParty.getChildren().subList(4,8);

                // set sublist data to tetfields
                for (int i=0;i<nodeob.size();i++){
                    TextField tn = (TextField) nodeob.get(i);
                    tn.setText(sublist.get(i));
                }
            }
        }
    }

    /**
     * @button "Update"
     * get the edited data from text fields and validate
     * update validated data to the table
     * @throws SQLException
     */
    public void updateData() throws SQLException {
        // make lables in the grid unvisible
        List<Node> PersonalLabels = gridPanePersonal.getChildren().subList(18,27);
        for(Node nodelbl : PersonalLabels){
            Label lbl = (Label) nodelbl;
            lbl.setVisible(false);
        }

        List<Node> PartyLabels = gridPaneParty.getChildren().subList(8,12);
        for(Node nodelbl : PartyLabels){
            Label lbl = (Label) nodelbl;
            lbl.setVisible(false);
        }

        // make the connection with the database
        JDSCON updateCOnnect = new JDSCON();
        Connection connect = updateCOnnect.jdbcConnect2();

        PreparedStatement preparedStatement = null;
        List<Boolean> validate = new ArrayList<>();

        List<Node> nodes;
        String[] columnsPersonal = new String[9];
        String[] columnsParty = new String[4];

        // check which grid is visible(Personal data grid / Party data grid)
        if(gridPaneParty.isVisible()){
            // get textfieds from the  personal data grid
            nodes = gridPaneParty.getChildren().subList(4,8);

            // get the text in the textfield
            for(int i = 0;i<nodes.size();i++){
                TextField tndata = (TextField) nodes.get(i);
                columnsParty[i] = tndata.getText();
                // check the text is in correct form add boolean value to validate list
                validate.add(validateParty(columnsParty[i], i));
            }

            // check validate list contains false
            if (!(validate.contains(false))) {
                // create the update sql statment
                String query = "UPDATE `candidate` SET `Partyname` = ?, `colour` = ?, `election_no` = ?, `sign` = ? WHERE `candidate_id` =?";
                preparedStatement = connect.prepareStatement(query);
                {
                    preparedStatement.setString(1, columnsParty[0]);
                    preparedStatement.setString(2, columnsParty[1]);
                    preparedStatement.setString(3, columnsParty[2]);
                    preparedStatement.setString(4, columnsParty[3]);
                    preparedStatement.setString(5, ComboCandidate.getValue());
                }
            }
        }
        else if(gridPanePersonal.isVisible()) {
            // get textfieds from the  party data grid
            nodes = gridPanePersonal.getChildren().subList(9, 18);
            System.out.println(nodes);

            // get the text in the textfield
            for (int i = 0; i < nodes.size(); i++) {
                TextField tndata = (TextField) nodes.get(i);
                columnsPersonal[i] = tndata.getText();
                validate.add(validatePersonal(columnsPersonal[i], i));
            }
            // check validate list contains false
            if (!(validate.contains(false))){
                // create the update sql statment
                String query = "UPDATE `candidate` SET `Name` = ?, `NIC` = ?, `tele_no` = ?, `email` = ?,`addressNo` = ?, `street` = ?, `city` = ?, `state` = ?, `poscode` = ? WHERE `candidate_id` = ?";
                preparedStatement = connect.prepareStatement(query);
                {
                    preparedStatement.setString(1, columnsPersonal[0]);
                    preparedStatement.setString(2, columnsPersonal[1]);
                    preparedStatement.setLong(3, Long.parseLong(columnsPersonal[2]));
                    preparedStatement.setString(4, columnsPersonal[3]);
                    preparedStatement.setString(5, columnsPersonal[4]);
                    preparedStatement.setString(6, columnsPersonal[5]);
                    preparedStatement.setString(7, columnsPersonal[6]);
                    preparedStatement.setString(8, columnsPersonal[7]);
                    preparedStatement.setString(9, columnsPersonal[8]);
                    preparedStatement.setString(10, ComboCandidate.getValue());
                }

            }
        }
        // check validate list contains false
        if(!(validate.contains(false))) {
            if (preparedStatement != null) {
                // ask user to confirm to update
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setContentText("Confirm Update the candidate");
                a.showAndWait();

                // if user click ok
                if(a.getResult() == ButtonType.OK) {

                    try {
                        // update the prepared query
                        preparedStatement.executeUpdate();
                        System.out.println("upadate success");
                        // popup confim alert
                        a = new Alert(Alert.AlertType.INFORMATION);
                        a.setContentText("Candidate data updated Successfully");
                        a.showAndWait();
                        ComboField.getSelectionModel().clearSelection();
                        ComboCandidate.getSelectionModel().clearSelection();

                    } catch (Exception e) {
                        System.out.println("update fail");
                        System.out.println(e.getMessage());
                    }
                }
            }
            gridPanePersonal.setVisible(false);
            gridPaneParty.setVisible(false);
        }
    }

    /**
     * create connection with the database
     * get all election number in the candidate table except editing candidate
     * check weather number is equal to any of election number which in the result set
     * if equal return false
     * @param number
     * @param id
     * @return boolean
     */
    public boolean checkELectionNo(int number, int id){
        JDSCON checknumber = new JDSCON();
        Connection con = checknumber.jdbcConnect2();

        String query = "SELECT `election_no` FROM `candidate` WHERE `candidate_id` not in("+id+")";

        boolean value = true;
        ResultSet rs;
        Statement st;

        try{
            st = con.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()){
                if(rs.getInt(1) == number){
                    value = false;
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Enterd election number is already used\nPlease enter different election number");
                    a.showAndWait();
                    return value;
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return value;
    }

    /**
     * check weather data edited in party grid is in correct form
     * check text is empty or not
     * check nic, Telephone number and postcode are integers
     * check the number of characters in telephone number and postcode is correct
     * @param s
     * @param i
     * @return bool
     */
    private boolean validatePersonal(String s, int i ) {
        List<Node> PersonalLabels = gridPanePersonal.getChildren().subList(18,27);

        Label lb = (Label) PersonalLabels.get(i);

        // check s is empty
        if (!(s.isEmpty())) {
            // check number of characters in NIC is correct
            if (i == 1) {
                if (!(s.length() == 11)) {
                    lb.setVisible(true);
                }
            }// check number of characters in telephone number is correct and it is an integer value
            else if (i == 2) {
                if (!(s.length() == 10)) {
                    lb.setVisible(true);
                }
                try {
                    Long tel = Long.parseLong(s);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    lb.setVisible(true);
                }
            }// check number of characters in postcode is correct and it is an integer value
            else if (i == 8) {
                if (!(s.length() == 6)) {
                    lb.setVisible(true);
                }
                try {
                    int tel = Integer.parseInt(s);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    lb.setVisible(true);
                }
            }

        } else {
            lb.setVisible(true);
        }
        return !lb.isVisible();
    }

    /**
     * check weather data edited in party grid is in correct form
     * check text is empty or not
     * check election number is an integer an used before
     * @param s
     * @param i
     * @return bool
     */
    private boolean validateParty(String s, int i ) {
        List<Node> PartyLabels = gridPaneParty.getChildren().subList(8,12);

        Label lb = (Label) PartyLabels.get(i);

        // check s in textfield is empty
        if(!(s.isEmpty())){
            // if s is the election number
            if(i == 2){
                try {
                    // try to convert the election no to integer
                    int number = Integer.parseInt(s);
                    // if election no is an integer , check weather it is used already
                    if(!(checkELectionNo(Integer.parseInt(s),Integer.parseInt(ComboCandidate.getValue())))){
                        // if used set label visible
                        lb.setVisible(true);
                    }
                }catch (Exception e){
                    // if election no is not an integer
                    System.out.println(e.getMessage());
                    // set label visible
                    lb.setVisible(true);
                }
            }
        }else {
            // if s is empty, set label visible
            lb.setVisible(true);
        }
        return !lb.isVisible();
    }

    /**
     * add observables list to a combo box with
     * string values
     */
    public void assignList(){
        ObservableList<String> obtext = FXCollections.observableArrayList("Personal", "Party");
        ComboField.setItems(obtext);
    }


    /**
     * @button "<- back to login" (This function is executed by clicking)
     * Load 1st add candidate window
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


        Stage primaryStage = (Stage) bntUpdate.getScene().getWindow();
        primaryStage.close();
    }
}
