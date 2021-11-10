package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class JDSCON {
    static Connection con = null;
    static Statement stat;
    static ResultSet rsl = null;
    static ResultSet rsi = null;
    static ResultSet rst = null;
    private ObservableList<Candidate> canOB;

    public boolean jdbcConnect(){
        String conn = "jdbc:mysql://localhost:3306/uselection";
        String username = "root";
        String password = "";


        try {
            con = DriverManager.getConnection(conn , username , password);
            return true;
        } catch (SQLException e) {
            System.out.println("Oops,database connecting error ");
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * bulild the connection with the "uselection" database
     * @return connection
     */
    public Connection jdbcConnect2(){
        String conn = "jdbc:mysql://localhost:3306/uselection";
        String username = "root";
        String password = "";


        try {
            con = DriverManager.getConnection(conn , username , password);
            return con;
        } catch (SQLException e) {
            System.out.println("Oops,database connecting error ");
            e.printStackTrace();
            return null;
        }

    }


    public int executeSQLinsert(String query, String [] dataList){

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            {

                preparedStatement.setString(1, dataList[0]);
                preparedStatement.setString(2, dataList[1]);
                preparedStatement.setLong(3, Long.parseLong(dataList[2]));
                preparedStatement.setString(4, dataList[3]);
                preparedStatement.setString(5, dataList[4]);
                preparedStatement.setString(6, dataList[5]);
                preparedStatement.setString(7, dataList[6]);
                preparedStatement.setString(8, dataList[7]);
                preparedStatement.setInt(9, Integer.parseInt(dataList[8]));
                preparedStatement.setDate(10, Date.valueOf(dataList[9]));
                preparedStatement.setBoolean(11, Boolean.parseBoolean(dataList[10]));
                preparedStatement.setString(12, dataList[11]);
                preparedStatement.setString(13, dataList[12]);
                preparedStatement.setInt(14, Integer.parseInt(dataList[13]));
                preparedStatement.setString(15, dataList[14]);
                preparedStatement.setBoolean(16, Boolean.parseBoolean(dataList[15]));
            }

            System.out.println(preparedStatement+"ps");
            int i = preparedStatement.executeUpdate();

            return i;

        }catch (SQLException e){
            System.out.println("error");
            System.out.println(e.getMessage());

        }
        return 0;

    }

    /**
     * load all candidate data in candidate table in database
     * set all data to an Observable list
     * @return observable list
     */
    public ObservableList<Candidate> loadDataTable() {
        //List<Candidate> candidatelist;

        try {
            canOB = FXCollections.observableArrayList();
            rst = con.createStatement().executeQuery("SELECT * FROM `candidate`");
            while (rst.next()){
                canOB.add(new Candidate(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6),
                        rst.getString(7),
                        rst.getString(8),
                        rst.getString(9),
                        rst.getString(10),
                        rst.getString(11),
                        rst.getString(12),
                        rst.getString(13),
                        rst.getString(14),
                        rst.getString(15),
                        rst.getString(16),
                        rst.getString(17)
                        ));


            }
            return canOB;

        }catch (SQLException e){
            e.getCause();
        }
        return null;
    }

}
